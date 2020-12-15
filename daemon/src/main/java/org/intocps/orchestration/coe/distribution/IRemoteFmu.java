package org.intocps.orchestration.coe.distribution;

import org.intocps.fmi.Fmi2Status;
import org.intocps.fmi.FmiInvalidNativeStateException;
import org.intocps.fmi.FmuInvocationException;
import org.intocps.fmi.FmuMissingLibraryException;

import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoteFmu extends Remote {
    public abstract void load() throws FmuInvocationException, RemoteException, FmuMissingLibraryException;

    /**
     * Creates an instance of the FMU
     *
     * @param name
     * @param visible
     * @param loggingOn
     * @return an FMI component
     * @throws XPathExpressionException
     * @throws FmiInvalidNativeStateException
     */
    public abstract IRemoteFmuComponent instantiate(String guid, String name, boolean visible, boolean loggingOn,
            IRemoteFmuCallback callback) throws XPathExpressionException, FmiInvalidNativeStateException, RemoteException;

    public abstract void unLoad() throws FmiInvalidNativeStateException, RemoteException;

    public abstract String getVersion() throws FmiInvalidNativeStateException, RemoteException;

    public abstract String getTypesPlatform() throws FmiInvalidNativeStateException, RemoteException;

    public abstract byte[] getModelDescription() throws IOException, RemoteException;

    public abstract boolean isValid() throws RemoteException;

    public interface IRemoteFmuCallback extends Remote// , IFmuCallback
    {
        abstract void log(String instanceName, Fmi2Status status, String category, String message) throws RemoteException;

        abstract void stepFinished(Fmi2Status status) throws RemoteException;
    }

}
