package org.intocps.orchestration.coe.distribution;

import java.rmi.Remote;
import java.rmi.RemoteException;

import org.intocps.fmi.Fmi2Status;
import org.intocps.fmi.Fmi2StatusKind;
import org.intocps.fmi.FmiInvalidNativeStateException;
import org.intocps.fmi.FmuInvocationException;
import org.intocps.fmi.FmuResult;
import org.intocps.fmi.InvalidParameterException;

public interface IRemoteFmuComponent extends Remote
{

	// public abstract IRemoteFmu getFmu();

	public abstract Fmi2Status setDebugLogging(boolean loggingOn,
			String[] categories) throws FmuInvocationException, RemoteException;

	public abstract Fmi2Status setupExperiment(boolean toleranceDefined,
			double tolerance, double startTime, boolean stopTimeDefined,
			double stopTime) throws FmuInvocationException, RemoteException;

	public abstract Fmi2Status enterInitializationMode()
			throws FmuInvocationException, RemoteException;

	public abstract Fmi2Status exitInitializationMode()
			throws FmuInvocationException, RemoteException;

	public abstract Fmi2Status reset() throws FmuInvocationException,
			RemoteException;

	Fmi2Status setRealInputDerivatives(long[] scalarValueIndices, int order,
			double[] derivatives) throws FmuInvocationException,
			RemoteException;

	FmuResult<double[]> getRealOutputDerivatives(long[] scalarValueIndices,
			int order) throws FmuInvocationException, RemoteException;

	public FmuResult<double[]> getDirectionalDerivative(long[] vUnknownRef,
			long[] vKnownRef, double[] dvKnown) throws FmuInvocationException,
			RemoteException;

	public abstract Fmi2Status doStep(double currentCommunicationPoint,
			double communicationStepSize,
			boolean noSetFMUStatePriorToCurrentPoint)
			throws FmuInvocationException, RemoteException;

	// public abstract Fmi2Status cancelStep();

	/**
	 * Obtains the values of the selected scalar values
	 * 
	 * @param scalarValueIndices
	 */
	public abstract FmuResult<double[]> getReal(long[] scalarValueIndices)
			throws FmuInvocationException, RemoteException;

	public abstract FmuResult<int[]> getInteger(long[] scalarValueIndices)
			throws FmuInvocationException, RemoteException;

	/**
	 * Obtains the values of the selected scalar values
	 * 
	 * @param scalarValueIndices
	 */
	public abstract FmuResult<boolean[]> getBooleans(long[] scalarValueIndices)
			throws FmuInvocationException, RemoteException;

	public abstract FmuResult<String[]> getStrings(long[] scalarValueIndices)
			throws FmuInvocationException, RemoteException;

	public abstract Fmi2Status setBooleans(long[] scalarValueIndices,
			boolean[] values) throws InvalidParameterException,
			FmiInvalidNativeStateException, RemoteException;

	public abstract Fmi2Status setReals(long[] scalarValueIndices,
			double[] values) throws InvalidParameterException,
			FmiInvalidNativeStateException, RemoteException;

	public abstract Fmi2Status setIntegers(long[] scalarValueIndices,
			int[] values) throws InvalidParameterException,
			FmiInvalidNativeStateException, RemoteException;

	public abstract Fmi2Status setStrings(long[] scalarValueIndices,
			String[] values) throws InvalidParameterException,
			FmiInvalidNativeStateException, RemoteException;

	public abstract FmuResult<Boolean> getBooleanStatus(Fmi2StatusKind kind)
			throws FmuInvocationException, RemoteException;

	public abstract FmuResult<Fmi2Status> getStatus(Fmi2StatusKind kind)
			throws FmuInvocationException, RemoteException;

	public abstract FmuResult<Integer> getIntegerStatus(Fmi2StatusKind kind)
			throws FmuInvocationException, RemoteException;

	public abstract FmuResult<Double> getRealStatus(Fmi2StatusKind kind)
			throws FmuInvocationException, RemoteException;

	public abstract FmuResult<String> getStringStatus(Fmi2StatusKind kind)
			throws FmuInvocationException, RemoteException;

	public abstract Fmi2Status terminate() throws FmuInvocationException,
			RemoteException;

	public abstract void freeInstance() throws FmuInvocationException,
			RemoteException;

	// public abstract FmuResult<IFmiComponentState> getState()
	// throws FmuInvocationException;
	//
	// public abstract Fmi2Status setState(IFmiComponentState state)
	// throws FmuInvocationException;
	//
	// public abstract Fmi2Status freeState(IFmiComponentState state)
	// throws FmuInvocationException;

	public abstract boolean isValid() throws RemoteException;

	// INTO-CPS extension
	/**
	 * Extension method suggested in Broman et al
	 * 
	 * @return the step max size
	 * @throws FmiInvalidNativeStateException
	 */
	public abstract FmuResult<Double> getMaxStepSize()
			throws FmiInvalidNativeStateException, RemoteException;
}
