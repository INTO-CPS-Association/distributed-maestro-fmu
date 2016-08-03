package org.intocps.orchestration.coe.distribution;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.zip.ZipException;

import javax.xml.xpath.XPathExpressionException;

import org.intocps.fmi.FmiInvalidNativeStateException;
import org.intocps.fmi.FmuInvocationException;
import org.intocps.fmi.IFmiComponent;
import org.intocps.fmi.IFmu;
import org.intocps.fmi.IFmuCallback;

public class FmuRemoteProxy implements IFmu
{
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
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			IRemoteFmuComponent remoteComp = remote.instantiate(guid, name, visible, loggingOn, callback);
			
			return new ComponentRemoteProxy(this,remoteComp);
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
