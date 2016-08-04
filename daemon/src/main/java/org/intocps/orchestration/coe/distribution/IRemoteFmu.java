package org.intocps.orchestration.coe.distribution;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.zip.ZipException;

import javax.xml.xpath.XPathExpressionException;

import org.intocps.fmi.Fmi2Status;
import org.intocps.fmi.FmiInvalidNativeStateException;
import org.intocps.fmi.FmuInvocationException;

public interface IRemoteFmu extends Remote
{
	public interface IRemoteFmuCallback extends Remote// , IFmuCallback
	{
		abstract void log(String instanceName, Fmi2Status status,
				String category, String message) throws RemoteException;

		abstract void stepFinished(Fmi2Status status) throws RemoteException;
	}

	public abstract void load() throws FmuInvocationException, RemoteException;

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
	public abstract IRemoteFmuComponent instantiate(String guid, String name,
			boolean visible, boolean loggingOn, IRemoteFmuCallback callback)
			throws XPathExpressionException, FmiInvalidNativeStateException,
			RemoteException;

	public abstract void unLoad() throws FmiInvalidNativeStateException,
			RemoteException;

	public abstract String getVersion() throws FmiInvalidNativeStateException,
			RemoteException;

	public abstract String getTypesPlatform()
			throws FmiInvalidNativeStateException, RemoteException;

	public abstract InputStream getModelDescription() throws ZipException,
			IOException, RemoteException;

	public abstract boolean isValid() throws RemoteException;

}
