import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.zip.ZipException;

import javax.xml.xpath.XPathExpressionException;

import org.intocps.fmi.FmiInvalidNativeStateException;
import org.intocps.fmi.FmuInvocationException;
import org.intocps.fmi.IFmiComponent;
import org.intocps.fmi.IFmu;
import org.intocps.fmi.IFmuCallback;
import org.intocps.fmi.jnifmuapi.Factory;

public class DFmu extends UnicastRemoteObject implements IFmu {
	
	IFmu instance;
	
	public DFmu(File file, String name) throws RemoteException
	{
		
		if (file.isFile())
		{
			try {
				instance = Factory.create(file);
			} catch (IOException | FmuInvocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else
		{
			try {
				instance = Factory.createFromDirectory(file);
			} catch (IOException | FmuInvocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	@Override
	public void load() throws FmuInvocationException
	{
		instance.load();
	}

	@Override
	public void unLoad()
	{
		try {
			instance.unLoad();
		} catch (FmiInvalidNativeStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public String getVersion()
	{
		try {
			return instance.getVersion();
		} catch (FmiInvalidNativeStateException e) {
			e.printStackTrace();
			return "error";
		}
	}

	@Override
	public String getTypesPlatform() throws FmiInvalidNativeStateException
	{
		return instance.getTypesPlatform();
	}

	@Override
	public InputStream getModelDescription() throws ZipException , IOException
	{
		return instance.getModelDescription();
	}
	
	interface ICallback
	{
		void log(String name, byte status, String category, String message);
		
		void stepFinished(byte fmuStatus);
	}

	@Override
	public IFmiComponent instantiate(String guid, String name, boolean visible, boolean loggingOn,
			final IFmuCallback callback){
		/*	
		IFmiComponent component;
		try {
			component = instance.instantiate(guid, name, visible, loggingOn, callback);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
			return null;
		} catch (FmiInvalidNativeStateException e) {
			e.printStackTrace();
			return null;
		}
		
		try {
			return new DFmiComponent(component);
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
*/
		return null;
	}
	
	@Override
	public boolean isValid()  {
			return instance.isValid();
	}
}
