package org.intocps.orchestration.coe.distribution.daemon.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.intocps.fmi.Fmi2Status;
import org.intocps.fmi.Fmi2StatusKind;
import org.intocps.fmi.FmiInvalidNativeStateException;
import org.intocps.fmi.FmuInvocationException;
import org.intocps.fmi.FmuResult;
import org.intocps.fmi.IFmiComponent;
import org.intocps.fmi.InvalidParameterException;
import org.intocps.orchestration.coe.distribution.IRemoteFmu;
import org.intocps.orchestration.coe.distribution.IRemoteFmuComponent;

public class RemoteFmiComponent extends UnicastRemoteObject implements
		IRemoteFmuComponent
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6005283667500177273L;
	final IFmiComponent instance;
	final IRemoteFmu fmu;

	public RemoteFmiComponent(IRemoteFmu fmu,IFmiComponent component) throws RemoteException
	{
		this.fmu = fmu;
		this.instance = component;
	}

	@Override
	public Fmi2Status setDebugLogging(boolean loggingOn, String[] categories)
			throws FmuInvocationException
	{
		return instance.setDebugLogging(loggingOn, categories);
	}

	@Override
	public Fmi2Status setupExperiment(boolean toleranceDefined,
			double tolerance, double startTime, boolean stopTimeDefined,
			double stopTime) throws FmuInvocationException
	{
		return instance.setupExperiment(toleranceDefined, tolerance, startTime, stopTimeDefined, stopTime);
	}

	@Override
	public Fmi2Status enterInitializationMode() throws FmuInvocationException
	{
		return instance.enterInitializationMode();
	}

	@Override
	public Fmi2Status exitInitializationMode() throws FmuInvocationException
	{
		return instance.exitInitializationMode();
	}

	@Override
	public Fmi2Status reset() throws FmuInvocationException
	{
		return instance.reset();
	}

	@Override
	public Fmi2Status setRealInputDerivatives(long[] scalarValueIndices,
			int order, double[] derivatives) throws FmuInvocationException
	{
		return instance.setRealInputDerivatives(scalarValueIndices, order, derivatives);
	}

	@Override
	public FmuResult<double[]> getRealOutputDerivatives(
			long[] scalarValueIndices, int order) throws FmuInvocationException
	{
		return instance.getRealOutputDerivatives(scalarValueIndices, order);
	}

	@Override
	public FmuResult<double[]> getDirectionalDerivative(long[] vUnknownRef,
			long[] vKnownRef, double[] dvKnown) throws FmuInvocationException
	{
		return instance.getDirectionalDerivative(vUnknownRef, vKnownRef, dvKnown);
	}

	@Override
	public Fmi2Status doStep(double currentCommunicationPoint,
			double communicationStepSize,
			boolean noSetFMUStatePriorToCurrentPoint)
			throws FmuInvocationException
	{
		return instance.doStep(currentCommunicationPoint, communicationStepSize, noSetFMUStatePriorToCurrentPoint);
	}

	@Override
	public FmuResult<double[]> getReal(long[] scalarValueIndices)
			throws FmuInvocationException
	{
		return instance.getReal(scalarValueIndices);
	}

	@Override
	public FmuResult<int[]> getInteger(long[] scalarValueIndices)
			throws FmuInvocationException
	{
		return instance.getInteger(scalarValueIndices);
	}

	@Override
	public FmuResult<boolean[]> getBooleans(long[] scalarValueIndices)
			throws FmuInvocationException
	{
		return instance.getBooleans(scalarValueIndices);
	}

	@Override
	public FmuResult<String[]> getStrings(long[] scalarValueIndices)
			throws FmuInvocationException
	{
		return instance.getStrings(scalarValueIndices);
	}

	@Override
	public Fmi2Status setBooleans(long[] scalarValueIndices, boolean[] values)
			throws InvalidParameterException, FmiInvalidNativeStateException
	{
		return instance.setBooleans(scalarValueIndices, values);
	}

	@Override
	public Fmi2Status setReals(long[] scalarValueIndices, double[] values)
			throws InvalidParameterException, FmiInvalidNativeStateException
	{
		return instance.setReals(scalarValueIndices, values);
	}

	@Override
	public Fmi2Status setIntegers(long[] scalarValueIndices, int[] values)
			throws InvalidParameterException, FmiInvalidNativeStateException
	{
		return instance.setIntegers(scalarValueIndices, values);
	}

	@Override
	public Fmi2Status setStrings(long[] scalarValueIndices, String[] values)
			throws InvalidParameterException, FmiInvalidNativeStateException
	{
		return instance.setStrings(scalarValueIndices, values);
	}

	@Override
	public FmuResult<Boolean> getBooleanStatus(Fmi2StatusKind kind)
			throws FmuInvocationException
	{
		return instance.getBooleanStatus(kind);
	}

	@Override
	public FmuResult<Fmi2Status> getStatus(Fmi2StatusKind kind)
			throws FmuInvocationException
	{
		return instance.getStatus(kind);
	}

	@Override
	public FmuResult<Integer> getIntegerStatus(Fmi2StatusKind kind)
			throws FmuInvocationException
	{
		return instance.getIntegerStatus(kind);
	}

	@Override
	public FmuResult<Double> getRealStatus(Fmi2StatusKind kind)
			throws FmuInvocationException
	{
		return instance.getRealStatus(kind);
	}

	@Override
	public FmuResult<String> getStringStatus(Fmi2StatusKind kind)
			throws FmuInvocationException
	{
		return instance.getStringStatus(kind);
	}

	@Override
	public Fmi2Status terminate() throws FmuInvocationException
	{
		return instance.terminate();
	}

	@Override
	public void freeInstance() throws FmuInvocationException
	{
		instance.freeInstance();
	}

//	@Override
//	public FmuResult<IFmiComponentState> getState()
//			throws FmuInvocationException
//	{
//		return instance.getState();
//	}
//
//	@Override
//	public Fmi2Status setState(IFmiComponentState state)
//			throws FmuInvocationException
//	{
//		return instance.setState(state);
//	}
//
//	@Override
//	public Fmi2Status freeState(IFmiComponentState state)
//			throws FmuInvocationException
//	{
//		return instance.freeState(state);
//	}

	@Override
	public boolean isValid()
	{
		return instance.isValid();
	}

	@Override
	public FmuResult<Double> getMaxStepSize()
			throws FmiInvalidNativeStateException
	{
		return instance.getMaxStepSize();
	}

//	@Override
//	public IRemoteFmu getFmu()
//	{
//		return fmu;
//	}

}
