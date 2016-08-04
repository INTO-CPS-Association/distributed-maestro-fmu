package org.intocps.orchestration.coe.distribution;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.zip.ZipException;

import javax.xml.xpath.XPathExpressionException;

import org.intocps.fmi.Fmi2Status;
import org.intocps.fmi.FmiInvalidNativeStateException;
import org.intocps.fmi.FmuInvocationException;
import org.intocps.fmi.IFmiComponent;
import org.intocps.fmi.IFmu;
import org.intocps.fmi.IFmuCallback;
import org.intocps.orchestration.coe.distribution.IRemoteFmu.IRemoteFmuCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FmuRemoteProxy implements IFmu
{
	final static Logger logger = LoggerFactory.getLogger(FmuRemoteProxy.class);

	public static class FmuCallbackRemoteProxy extends UnicastRemoteObject
			implements IRemoteFmuCallback
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 2747286841662224378L;

		protected FmuCallbackRemoteProxy() throws RemoteException
		{
			super();
		}

		IFmuCallback callback;

		public IFmuCallback getCallback()
		{
			return callback;
		}

		public void setCallback(IFmuCallback callback)
		{
			this.callback = callback;
		}

		@Override
		public void log(String instanceName, Fmi2Status status,
				String category, String message) throws RemoteException
		{
			callback.log(instanceName, status, category, message);
		}

		@Override
		public void stepFinished(Fmi2Status status) throws RemoteException
		{
			callback.stepFinished(status);
		}

	}

	IRemoteFmu remote;

	public FmuRemoteProxy(IRemoteFmu fmu)
	{
		this.remote = fmu;
	}

	@Override
	public InputStream getModelDescription() throws ZipException, IOException
	{
		return remote.getModelDescription();
	}

	@Override
	public String getTypesPlatform() throws FmiInvalidNativeStateException
	{
		try
		{
			return remote.getTypesPlatform();
		} catch (RemoteException e)
		{
			logger.error("RemoteException", e);
		}
		return null;
	}

	@Override
	public String getVersion() throws FmiInvalidNativeStateException
	{
		try
		{
			return remote.getVersion();
		} catch (RemoteException e)
		{
			logger.error("RemoteException", e);
		}
		return null;
	}

	@Override
	public IFmiComponent instantiate(String guid, String name, boolean visible,
			boolean loggingOn, IFmuCallback callback)
			throws XPathExpressionException, FmiInvalidNativeStateException
	{
		try
		{

			IRemoteFmuCallback remoteCallback = null;

			if (callback != null)
			{
				remoteCallback = new FmuCallbackRemoteProxy();
				((FmuCallbackRemoteProxy) remoteCallback).setCallback(callback);
			}

			IRemoteFmuComponent remoteComp = remote.instantiate(guid, name, visible, loggingOn, remoteCallback);

			return new ComponentRemoteProxy(this, remoteComp);
		} catch (RemoteException e)
		{
			logger.error("RemoteException", e);
		}
		return null;
	}

	@Override
	public boolean isValid()
	{
		try
		{
			return remote.isValid();
		} catch (RemoteException e)
		{
			logger.error("RemoteException", e);
		}
		return false;
	}

	@Override
	public void load() throws FmuInvocationException
	{
		try
		{
			remote.load();
		} catch (RemoteException e)
		{
			logger.error("RemoteException", e);
		}
	}

	@Override
	public void unLoad() throws FmiInvalidNativeStateException
	{
		try
		{
			remote.unLoad();
		} catch (RemoteException e)
		{
			logger.error("RemoteException", e);
		}
	}

}
