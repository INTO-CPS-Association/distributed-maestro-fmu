import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.zip.ZipException;

import org.intocps.fmi.FmiInvalidNativeStateException;
import org.intocps.fmi.FmuInvocationException;
import org.intocps.fmi.IFmiComponent;
import org.intocps.fmi.IFmu;
import org.intocps.fmi.IFmuCallback;
import org.intocps.fmi.jnifmuapi.Factory;

public class DFmu extends UnicastRemoteObject implements IFmu {
	
	IFmu instance;
	
	public DFmu(File file, String name) throws IOException, FmuInvocationException, RemoteException
	{
		
		if (file.isFile())
		{
			instance = Factory.create(file);

		} else
		{
			instance = Factory.createFromDirectory(file);
		}
	}
	
	@Override
	public void load() throws FmuInvocationException
	{
		instance.load();
	}

	@Override
	public void unLoad() throws FmiInvalidNativeStateException
	{
		instance.unLoad();
	}

	@Override
	public String getVersion() throws FmiInvalidNativeStateException
	{
		return instance.getVersion();
	}

	@Override
	public String getTypesPlatform() throws FmiInvalidNativeStateException
	{
		return instance.getTypesPlatform();
	}
	
	@Override
	public InputStream getModelDescription() throws ZipException, IOException
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
	public boolean isValid()
	{
		return instance.isValid();
	}
}
