package org.intocps.orchestration.coe.distribution.daemon.server;

import org.apache.commons.io.IOUtils;
import org.intocps.fmi.*;
import org.intocps.fmi.jnifmuapi.Factory;
import org.intocps.orchestration.coe.distribution.IRemoteFmu;
import org.intocps.orchestration.coe.distribution.IRemoteFmuComponent;

import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.zip.ZipException;

public class RemoteFmu extends UnicastRemoteObject implements IRemoteFmu {

    /**
     *
     */
    private static final long serialVersionUID = 6768389929226421212L;
    IFmu instance;

    public RemoteFmu(File file) throws IOException, FmuInvocationException, RemoteException, FmuMissingLibraryException {

        if (file.isFile()) {
            instance = Factory.create(file);

        } else {
            instance = Factory.createFromDirectory(file);
        }
        instance.load();
    }

    @Override
    public void load() throws FmuInvocationException, FmuMissingLibraryException {
        instance.load();
    }

    @Override
    public void unLoad() throws FmiInvalidNativeStateException {
        instance.unLoad();
    }

    @Override
    public String getVersion() throws FmiInvalidNativeStateException {
        return instance.getVersion();
    }

    @Override
    public String getTypesPlatform() throws FmiInvalidNativeStateException {
        return instance.getTypesPlatform();
    }

    @Override
    public byte[] getModelDescription() throws ZipException, IOException {
        return IOUtils.toByteArray(instance.getModelDescription());
    }

    @Override
    public IRemoteFmuComponent instantiate(String guid, String name, boolean visible, boolean loggingOn,
            final IRemoteFmuCallback callback) throws XPathExpressionException, FmiInvalidNativeStateException, RemoteException {
        /*
         * IFmiComponent component; try { component = instance.instantiate(guid, name, visible, loggingOn, callback); }
         * catch (XPathExpressionException e) { e.printStackTrace(); return null; } catch
         * (FmiInvalidNativeStateException e) { e.printStackTrace(); return null; } try { return new
         * DFmiComponent(component); } catch (RemoteException e) { e.printStackTrace(); return null; }
         */

        IFmuCallback localCallback = null;

        if (callback != null) {
            localCallback = new IFmuCallback() {

                @Override
                public void stepFinished(Fmi2Status status) {
                    try {
                        callback.stepFinished(status);
                    } catch (RemoteException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                @Override
                public void log(String instanceName, Fmi2Status status, String category, String message) {
                    try {
                        callback.log(instanceName, status, category, message);
                    } catch (RemoteException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            };
        }

        IFmiComponent comp = instance.instantiate(guid, name, visible, loggingOn, localCallback);

        return new RemoteFmiComponent(this, comp);
    }

    @Override
    public boolean isValid() {
        return instance.isValid();
    }
}
