package org.intocps.orchestration.coe.distribution;

import org.apache.commons.io.IOUtils;
import org.intocps.fmi.FmuInvocationException;
import org.intocps.fmi.FmuMissingLibraryException;
import org.intocps.fmi.IFmu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.rmi.Naming;
import java.util.HashMap;

public class DistributionMaster {

    final static Logger logger = LoggerFactory.getLogger(DistributionMaster.class);
    private static DistributionMaster instance;
    private HashMap<String, IDaemon> mapOfStubs = new HashMap<String, IDaemon>();

    private DistributionMaster() {
    }

    public static synchronized DistributionMaster getInstance() {
        if (instance == null) {
            instance = new DistributionMaster();
        }
        return instance;
    }

    public IDaemon connectToRemote(URI remote_path) throws Exception {
        String path = "";
        try {
            path = remote_path.toString();
            path = path.substring(0, path.indexOf('#'));
            if (mapOfStubs.containsKey(path)) {
                return mapOfStubs.get(path);
            }

            logger.debug("Trying to lookup on: " + path);

            IDaemon stub = (IDaemon) Naming.lookup(path);
            mapOfStubs.put(path, stub);
            return stub;
        } catch (java.rmi.ConnectException e) {
            logger.error("Could not find daemon on endpoint: '" + path + "'. {}", e.getMessage());
            throw e;
        } catch (ClassCastException e) {

            throw new Exception("Found RMI endpoint but of the wrong type: " + remote_path + ". " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Could not find daemon on endpoint: '" + path + "'", e);
            throw e;
        }
    }

    public IFmu uploadFmu(String remote_path, File file) {
        IDaemon stub = mapOfStubs.get(remote_path);
        IRemoteFmu distFmu = null;
        try {
            distFmu = stub.uploadFmu(IOUtils.toByteArray(new FileInputStream(file)), file.getName());

        } catch (IOException | FmuInvocationException | FmuMissingLibraryException e) {
            e.printStackTrace();
        }
        return new FmuRemoteProxy(distFmu);
    }

}
