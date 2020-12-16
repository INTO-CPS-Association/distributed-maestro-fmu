package org.intocps.maestro.distributed;

import com.spencerwi.either.Either;
import org.apache.commons.io.IOUtils;
import org.intocps.fmi.FmuInvocationException;
import org.intocps.fmi.IFmu;
import org.intocps.fmi.jnifmuapi.Factory;
import org.intocps.fmi.jnifmuapi.FmiUtil;
import org.intocps.maestro.interpreter.Fmi2Interpreter;
import org.intocps.maestro.interpreter.api.IValueLifecycleHandler;
import org.intocps.maestro.interpreter.values.StringValue;
import org.intocps.maestro.interpreter.values.Value;
import org.intocps.maestro.interpreter.values.fmi.FmuValue;
import org.intocps.orchestration.coe.distribution.DistributionMaster;
import org.intocps.orchestration.coe.distribution.FmuRemoteProxy;
import org.intocps.orchestration.coe.distribution.IDaemon;
import org.intocps.orchestration.coe.distribution.IRemoteFmu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.intocps.maestro.interpreter.values.Value.checkArgLength;

@IValueLifecycleHandler.ValueLifecycle(name = "JavaRmiRemoteFMU2Loader")
public class JavaRmiRemoteLifecycleHandler implements IValueLifecycleHandler {

    final static Logger logger = LoggerFactory.getLogger(JavaRmiRemoteLifecycleHandler.class);
    final private File workingDirectory;

    public JavaRmiRemoteLifecycleHandler(File workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    public static boolean hasUriRmiSchema(URI uri) {
        return uri.getScheme() != null && uri.getScheme().equals("rmi");
    }

    public static IDaemon connect(URI uri, String path) throws Exception {
        if (hasUriRmiSchema(uri)) {
            // TODO check if the current FMI can be accepted by this daemon

            // rmi://localhost/fmu#file://some/path/to/fmu.fmu
            logger.debug("Connecting to daemon {}", uri);
            URI adjusted = URI.create(uri + "#");
            IDaemon stub = DistributionMaster.getInstance().connectToRemote(adjusted);

            return stub;

        }
        return null;

    }

    //FIXME should not be here but in the FMUUtils class
    public static String getModelName(InputStream modelDescription) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(modelDescription);
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile("fmiModelDescription/@modelName");
            NodeList list = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            if (list != null) {
                Node n = list.item(0);
                return n.getNodeValue();
            }
        } catch (SAXException | IOException | XPathExpressionException | ParserConfigurationException var9) {
            logger.error("Unable to parse model description", var9);
        }

        return null;
    }

    @Override
    public Either<Exception, Value> instantiate(List<Value> list) {
        /*args:
        <local fmu path> /path/to/fmu
        <remote uri> rmi://localhost:999
        <remote path> /remote/system/path
        */
        List<Value> args = list.stream().map(Value::deref).collect(Collectors.toList());

        checkArgLength(args, 3);

        String[] argNames = new String[]{"path", "url", "remotePath"};
        for (int i = 0; i < args.size(); i++) {
            if (!(args.get(i) instanceof StringValue)) {
                return Either.left(new IllegalArgumentException("argument '" + argNames[i] + "' must be a string"));
            }
        }

        String path = ((StringValue) args.get(0)).getValue();
        String guid = ((StringValue) args.get(1)).getValue();
        String uri = ((StringValue) args.get(2)).getValue();

        try {
            if (!isSupportedByRemote(path, uri)) {
                return Either.left(new Exception("Unsupported platform by remote"));
            }
        } catch (Exception e) {
            return Either.left(e);
        }

        try {
            IFmu remoteFmuProxy = removeCreate(path, URI.create(uri));
            return Either.right(new FmuValue(Fmi2Interpreter.createFmuMembers(workingDirectory, guid, remoteFmuProxy), remoteFmuProxy));
        } catch (Exception e) {
            return Either.left(e);
        }
    }

    private IFmu removeCreate(String path, URI uri) throws Exception {

        IDaemon stub = connect(uri, path);
        if (stub != null) {
            String fmuName = path.substring(path.lastIndexOf('/') + 1);
            IRemoteFmu fmu = stub.uploadFmu(IOUtils.toByteArray(new FileInputStream(path)), fmuName);
            return new FmuRemoteProxy(fmu);
        }
        return null;
    }

    private boolean isSupportedByRemote(String path, String uri) throws Exception {
        return accept(URI.create(uri), path);
    }

    @Override
    public void destroy(Value value) {

    }

    @Override
    public InputStream getMablModule() {
        return null;
    }

    public boolean accept(URI uri, String path) throws Exception {
        if (!hasUriRmiSchema(uri)) {
            return false;
        }

        IDaemon stub = null;
        try {
            stub = connect(uri, path);
        } catch (Exception e1) {
            // logger.error("Distributed simulation not possible for FMU: '"+uri+"' defaulting normal behavior. This may lead to load issues due to the usage of a URI containing rmi:");
            throw e1;
        }
        if (stub != null) {
            try {
                Map<String, String> daemonConfig = stub.getDaemonConfiguration();

                if (daemonConfig != null) {
                    try {
                        File fmuFile = new File(path);
                        IFmu fmu = Factory.create(fmuFile);
                        File daemonLibrary = FmiUtil.generateLibraryFileFromPlatform(daemonConfig.get("os.name"), daemonConfig.get("os.arch"),
                                FmiUtil.getModelIdentifier(fmu.getModelDescription()), new File("."));

                        String libraryZipName = daemonLibrary.getPath().substring(2);


                        List<String> files = Collections.synchronizedList(new Vector<>());
                        try (ZipFile zipFile = new ZipFile(fmuFile);) {
                            zipFile.stream().map(ZipEntry::getName).collect(Collectors.toCollection(() -> files));
                        }

                        if (files.contains(libraryZipName)) {
                            return true;
                        } else {
                            //lets try with revovery
                            if (fmuFile.getName().contains(".")) {
                                String alternateName = fmuFile.getName().substring(0, fmuFile.getName().indexOf('.'));

                                daemonLibrary = FmiUtil
                                        .generateLibraryFileFromPlatform(daemonConfig.get("os.name"), daemonConfig.get("os.arch"), alternateName,
                                                new File("."));

                                libraryZipName = daemonLibrary.getPath().substring(2);
                                if (files.contains(libraryZipName)) {
                                    logger.warn(
                                            "Fmu library name is wrong but near match found. For lib: " + libraryZipName + " matched on " + libraryZipName
                                                    .toLowerCase());
                                    return true;

                                }
                            }

                            logger.error("Daemon at {} does not support the specified FMU, wrong OS+Arch.", uri);
                            return false;
                        }

                    } catch (IOException | FmuInvocationException e) {
                        logger.error("Communication error while determining of the fmu is supported", e);
                        return false;
                    }
                }

                return false;

            } catch (RemoteException e) {
                logger.error("faild to connect to daemon and obtain config", e);
            }

        }
        return false;
    }


}
