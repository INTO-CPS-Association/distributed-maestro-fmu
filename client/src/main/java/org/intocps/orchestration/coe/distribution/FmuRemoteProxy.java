package org.intocps.orchestration.coe.distribution;

import org.intocps.fmi.*;
import org.intocps.orchestration.coe.distribution.IRemoteFmu.IRemoteFmuCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.xpath.XPathExpressionException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class FmuRemoteProxy implements IFmu {
    final static Logger logger = LoggerFactory.getLogger(FmuRemoteProxy.class);
    IRemoteFmu remote;

    public FmuRemoteProxy(IRemoteFmu fmu) {
        this.remote = fmu;
    }

    @Override
    public InputStream getModelDescription() throws IOException {
        return new ByteArrayInputStream(remote.getModelDescription());
    }

    @Override
    public String getTypesPlatform() throws FmiInvalidNativeStateException {
        try {
            return remote.getTypesPlatform();
        } catch (RemoteException e) {
            logger.error("RemoteException", e);
        }
        return null;
    }

    @Override
    public String getVersion() throws FmiInvalidNativeStateException {
        try {
            return remote.getVersion();
        } catch (RemoteException e) {
            logger.error("RemoteException", e);
        }
        return null;
    }

    @Override
    public IFmiComponent instantiate(String guid, String name, boolean visible, boolean loggingOn,
            IFmuCallback callback) throws XPathExpressionException, FmiInvalidNativeStateException {
        try {

            IRemoteFmuCallback remoteCallback = null;

            if (callback != null) {
                remoteCallback = new FmuCallbackRemoteProxy();
                ((FmuCallbackRemoteProxy) remoteCallback).setCallback(callback);
            }

            IRemoteFmuComponent remoteComp = remote.instantiate(guid, name, visible, loggingOn, remoteCallback);

            return new ComponentRemoteProxy(this, remoteComp, remoteCallback);
        } catch (RemoteException e) {
            logger.error("RemoteException", e);
        }
        return null;
    }

    @Override
    public boolean isValid() {
        try {
            return remote.isValid();
        } catch (RemoteException e) {
            logger.error("RemoteException", e);
        }
        return false;
    }

    @Override
    public void load() throws FmuInvocationException {
        try {
            remote.load();
        } catch (RemoteException | FmuMissingLibraryException e) {
            logger.error("RemoteException", e);
        }
    }

    @Override
    public void unLoad() throws FmiInvalidNativeStateException {
        try {
            remote.unLoad();
        } catch (RemoteException e) {
            logger.error("RemoteException", e);
        }
    }

    public static class FmuCallbackRemoteProxy extends UnicastRemoteObject implements IRemoteFmuCallback {

        /**
         *
         */
        private static final long serialVersionUID = 2747286841662224378L;
        IFmuCallback callback;

        protected FmuCallbackRemoteProxy() throws RemoteException {
            super();
        }

        public IFmuCallback getCallback() {
            return callback;
        }

        public void setCallback(IFmuCallback callback) {
            this.callback = callback;
        }

        @Override
        public void log(String instanceName, Fmi2Status status, String category, String message) throws RemoteException {
            callback.log(instanceName, status, category, message);
        }

        @Override
        public void stepFinished(Fmi2Status status) throws RemoteException {
            callback.stepFinished(status);
        }

    }

}
