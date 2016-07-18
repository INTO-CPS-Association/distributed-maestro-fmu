import java.io.IOException;
import java.io.InputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.zip.ZipException;

import javax.xml.xpath.XPathExpressionException;

import org.intocps.fmi.FmiInvalidNativeStateException;
import org.intocps.fmi.FmuInvocationException;
import org.intocps.fmi.IFmiComponent;
import org.intocps.fmi.IFmuCallback;

public interface IDFmu extends Remote
{
	public abstract void load() throws FmuInvocationException, RemoteException;

	/**
	 * Creates an instance of the FMU
	 * 
	 * @param name
	 * @param visible
	 * @param loggingOn
	 * @return
	 * @throws XPathExpressionException
	 * @throws FmiInvalidNativeStateException 
	 */
	public abstract IFmiComponent instantiate(String guid, String name,
			boolean visible, boolean loggingOn, IFmuCallback callback)
			throws XPathExpressionException, FmiInvalidNativeStateException, RemoteException;

	public abstract void unLoad() throws FmiInvalidNativeStateException, RemoteException;

	public abstract String getVersion() throws FmiInvalidNativeStateException, RemoteException;

	public abstract String getTypesPlatform() throws FmiInvalidNativeStateException, RemoteException;
	
	public abstract InputStream getModelDescription() throws ZipException, IOException, RemoteException;
	
	public abstract boolean isValid() throws RemoteException;

}
