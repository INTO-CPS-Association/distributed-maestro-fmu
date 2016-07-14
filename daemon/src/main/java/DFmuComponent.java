import org.intocps.fmi.Fmi2Status;
import org.intocps.fmi.Fmi2StatusKind;
import org.intocps.fmi.FmiInvalidNativeStateException;
import org.intocps.fmi.FmuInvocationException;
import org.intocps.fmi.FmuResult;
import org.intocps.fmi.IFmiComponent;
import org.intocps.fmi.IFmiComponentState;
import org.intocps.fmi.IFmu;
import org.intocps.fmi.InvalidParameterException;

public class DFmuComponent implements IFmiComponent {

	@Override
	public IFmu getFmu() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Fmi2Status setDebugLogging(boolean loggingOn, String[] categories) throws FmuInvocationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Fmi2Status setupExperiment(boolean toleranceDefined, double tolerance, double startTime,
			boolean stopTimeDefined, double stopTime) throws FmuInvocationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Fmi2Status enterInitializationMode() throws FmuInvocationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Fmi2Status exitInitializationMode() throws FmuInvocationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Fmi2Status reset() throws FmuInvocationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Fmi2Status setRealInputDerivatives(long[] scalarValueIndices, int order, double[] derivatives)
			throws FmuInvocationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FmuResult<double[]> getRealOutputDerivatives(long[] scalarValueIndices, int order)
			throws FmuInvocationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FmuResult<double[]> getDirectionalDerivative(long[] vUnknownRef, long[] vKnownRef, double[] dvKnown)
			throws FmuInvocationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Fmi2Status doStep(double currentCommunicationPoint, double communicationStepSize,
			boolean noSetFMUStatePriorToCurrentPoint) throws FmuInvocationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FmuResult<double[]> getReal(long[] scalarValueIndices) throws FmuInvocationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FmuResult<int[]> getInteger(long[] scalarValueIndices) throws FmuInvocationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FmuResult<boolean[]> getBooleans(long[] scalarValueIndices) throws FmuInvocationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FmuResult<String[]> getStrings(long[] scalarValueIndices) throws FmuInvocationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Fmi2Status setBooleans(long[] scalarValueIndices, boolean[] values)
			throws InvalidParameterException, FmiInvalidNativeStateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Fmi2Status setReals(long[] scalarValueIndices, double[] values)
			throws InvalidParameterException, FmiInvalidNativeStateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Fmi2Status setIntegers(long[] scalarValueIndices, int[] values)
			throws InvalidParameterException, FmiInvalidNativeStateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Fmi2Status setStrings(long[] scalarValueIndices, String[] values)
			throws InvalidParameterException, FmiInvalidNativeStateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FmuResult<Boolean> getBooleanStatus(Fmi2StatusKind kind) throws FmuInvocationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FmuResult<Fmi2Status> getStatus(Fmi2StatusKind kind) throws FmuInvocationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FmuResult<Integer> getIntegerStatus(Fmi2StatusKind kind) throws FmuInvocationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FmuResult<Double> getRealStatus(Fmi2StatusKind kind) throws FmuInvocationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FmuResult<String> getStringStatus(Fmi2StatusKind kind) throws FmuInvocationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Fmi2Status terminate() throws FmuInvocationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void freeInstance() throws FmuInvocationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FmuResult<IFmiComponentState> getState() throws FmuInvocationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Fmi2Status setState(IFmiComponentState state) throws FmuInvocationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Fmi2Status freeState(IFmiComponentState state) throws FmuInvocationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FmuResult<Double> getMaxStepSize() throws FmiInvalidNativeStateException {
		// TODO Auto-generated method stub
		return null;
	}

}
