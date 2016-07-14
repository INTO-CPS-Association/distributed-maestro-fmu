import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.zip.ZipException;

import javax.xml.xpath.XPathExpressionException;

import org.intocps.fmi.FmiInvalidNativeStateException;
import org.intocps.fmi.FmuInvocationException;
import org.intocps.fmi.IFmiComponent;
import org.intocps.fmi.IFmu;
import org.intocps.fmi.IFmuCallback;

public class DFmu implements IFmu , Serializable{
	
	@Override
	public void load() throws FmuInvocationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IFmiComponent instantiate(String guid, String name, boolean visible, boolean loggingOn,
			IFmuCallback callback) throws XPathExpressionException, FmiInvalidNativeStateException {
		
		return null;
	}

	@Override
	public void unLoad() throws FmiInvalidNativeStateException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getVersion() throws FmiInvalidNativeStateException {
		// Test
		return "2.0";
	}

	@Override
	public String getTypesPlatform() throws FmiInvalidNativeStateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getModelDescription() throws ZipException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

}
