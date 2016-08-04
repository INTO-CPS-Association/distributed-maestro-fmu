package org.intocps.orchestration.coe.distribution;

import java.rmi.RemoteException;

import org.intocps.fmi.Fmi2Status;
import org.intocps.fmi.Fmi2StatusKind;
import org.intocps.fmi.FmiInvalidNativeStateException;
import org.intocps.fmi.FmuInvocationException;
import org.intocps.fmi.FmuResult;
import org.intocps.fmi.IFmiComponent;
import org.intocps.fmi.IFmiComponentState;
import org.intocps.fmi.IFmu;
import org.intocps.fmi.InvalidParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComponentRemoteProxy implements IFmiComponent
{
	final static Logger logger = LoggerFactory.getLogger(ComponentRemoteProxy.class);
	final IFmu fmu;
	final IRemoteFmuComponent comp;

	public ComponentRemoteProxy(IFmu fmu, IRemoteFmuComponent comp)
	{
		this.fmu = fmu;
		this.comp = comp;
	}

	@Override
	public Fmi2Status doStep(double arg0, double arg1, boolean arg2)
			throws FmuInvocationException
	{
		try
		{
			return comp.doStep(arg0, arg1, arg2);
		} catch (RemoteException e)
		{
			logger.error("RemoteException", e);
		}
		return null;
	}

	@Override
	public Fmi2Status enterInitializationMode() throws FmuInvocationException
	{
		try
		{
			return comp.enterInitializationMode();
		} catch (RemoteException e)
		{
			logger.error("RemoteException", e);
		}
		return null;
	}

	@Override
	public Fmi2Status exitInitializationMode() throws FmuInvocationException
	{
		try
		{
			return comp.exitInitializationMode();
		} catch (RemoteException e)
		{
			logger.error("RemoteException", e);
		}
		return null;
	}

	@Override
	public void freeInstance() throws FmuInvocationException
	{
		try
		{
			comp.freeInstance();
		} catch (RemoteException e)
		{
			logger.error("RemoteException", e);
		}
	}

	@Override
	public Fmi2Status freeState(IFmiComponentState arg0)
			throws FmuInvocationException
	{
		// try
		// {
		// return comp.fr
		// } catch (RemoteException e)
		// {
		// logger.error("RemoteException", e);
		// }
		return null;
	}

	@Override
	public FmuResult<Boolean> getBooleanStatus(Fmi2StatusKind arg0)
			throws FmuInvocationException
	{
		try
		{
			return comp.getBooleanStatus(arg0);
		} catch (RemoteException e)
		{
			logger.error("RemoteException", e);
		}
		return null;
	}

	@Override
	public FmuResult<boolean[]> getBooleans(long[] arg0)
			throws FmuInvocationException
	{
		try
		{
			return comp.getBooleans(arg0);
		} catch (RemoteException e)
		{
			logger.error("RemoteException", e);
		}
		return null;
	}

	@Override
	public FmuResult<double[]> getDirectionalDerivative(long[] vUnknownRef,
			long[] vKnownRef, double[] dvKnown) throws FmuInvocationException
	{
		try
		{
			return comp.getDirectionalDerivative(vUnknownRef, vKnownRef, dvKnown);
		} catch (RemoteException e)
		{
			logger.error("RemoteException", e);
		}
		return null;
	}

	@Override
	public IFmu getFmu()
	{
		return fmu;
	}

	@Override
	public FmuResult<int[]> getInteger(long[] scalarValueIndices)
			throws FmuInvocationException
	{
		try
		{
			return comp.getInteger(scalarValueIndices);
		} catch (RemoteException e)
		{
			logger.error("RemoteException", e);
		}
		return null;
	}

	@Override
	public FmuResult<Integer> getIntegerStatus(Fmi2StatusKind arg0)
			throws FmuInvocationException
	{
		try
		{
			return comp.getIntegerStatus(arg0);
		} catch (RemoteException e)
		{
			logger.error("RemoteException", e);
		}
		return null;
	}

	@Override
	public FmuResult<Double> getMaxStepSize()
			throws FmiInvalidNativeStateException
	{
		try
		{
			return comp.getMaxStepSize();
		} catch (RemoteException e)
		{
			logger.error("RemoteException", e);
		}
		return null;
	}

	@Override
	public FmuResult<double[]> getReal(long[] scalarValueIndices)
			throws FmuInvocationException
	{
		try
		{
			return comp.getReal(scalarValueIndices);
		} catch (RemoteException e)
		{
			logger.error("RemoteException", e);
		}
		return null;
	}

	@Override
	public FmuResult<double[]> getRealOutputDerivatives(
			long[] scalarValueIndices, int order) throws FmuInvocationException
	{
		try
		{
			return comp.getRealOutputDerivatives(scalarValueIndices, order);
		} catch (RemoteException e)
		{
			logger.error("RemoteException", e);
		}
		return null;
	}

	@Override
	public FmuResult<Double> getRealStatus(Fmi2StatusKind kind)
			throws FmuInvocationException
	{
		try
		{
			return comp.getRealStatus(kind);
		} catch (RemoteException e)
		{
			logger.error("RemoteException", e);
		}
		return null;
	}

	@Override
	public FmuResult<IFmiComponentState> getState()
			throws FmuInvocationException
	{
		// try
		// {
		// return comp
		// } catch (RemoteException e)
		// {
		// logger.error("RemoteException", e);
		// }
		return null;
	}

	@Override
	public FmuResult<Fmi2Status> getStatus(Fmi2StatusKind kind)
			throws FmuInvocationException
	{
		try
		{
			return comp.getStatus(kind);
		} catch (RemoteException e)
		{
			logger.error("RemoteException", e);
		}
		return null;
	}

	@Override
	public FmuResult<String> getStringStatus(Fmi2StatusKind kind)
			throws FmuInvocationException
	{
		try
		{
			return comp.getStringStatus(kind);
		} catch (RemoteException e)
		{
			logger.error("RemoteException", e);
		}
		return null;
	}

	@Override
	public FmuResult<String[]> getStrings(long[] scalarValueIndices)
			throws FmuInvocationException
	{
		try
		{
			return comp.getStrings(scalarValueIndices);
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
			return comp.isValid();
		} catch (RemoteException e)
		{
			logger.error("RemoteException", e);
		}
		return false;
	}

	@Override
	public Fmi2Status reset() throws FmuInvocationException
	{
		try
		{
			return comp.reset();
		} catch (RemoteException e)
		{
			logger.error("RemoteException", e);
		}
		return null;
	}

	@Override
	public Fmi2Status setBooleans(long[] scalarValueIndices, boolean[] values)
			throws InvalidParameterException, FmiInvalidNativeStateException
	{
		try
		{
			return comp.setBooleans(scalarValueIndices, values);
		} catch (RemoteException e)
		{
			logger.error("RemoteException", e);
		}
		return null;
	}

	@Override
	public Fmi2Status setDebugLogging(boolean loggingOn, String[] categories)
			throws FmuInvocationException
	{
		try
		{
			return comp.setDebugLogging(loggingOn, categories);
		} catch (RemoteException e)
		{
			logger.error("RemoteException", e);
		}
		return null;
	}

	@Override
	public Fmi2Status setIntegers(long[] scalarValueIndices, int[] values)
			throws InvalidParameterException, FmiInvalidNativeStateException
	{
		try
		{
			return comp.setIntegers(scalarValueIndices, values);
		} catch (RemoteException e)
		{
			logger.error("RemoteException", e);
		}
		return null;
	}

	@Override
	public Fmi2Status setRealInputDerivatives(long[] scalarValueIndices,
			int order, double[] derivatives) throws FmuInvocationException
	{
		try
		{
			return comp.setRealInputDerivatives(scalarValueIndices, order, derivatives);
		} catch (RemoteException e)
		{
			logger.error("RemoteException", e);
		}
		return null;
	}

	@Override
	public Fmi2Status setReals(long[] scalarValueIndices, double[] values)
			throws InvalidParameterException, FmiInvalidNativeStateException
	{
		try
		{
			return comp.setReals(scalarValueIndices, values);
		} catch (RemoteException e)
		{
			logger.error("RemoteException", e);
		}
		return null;
	}

	@Override
	public Fmi2Status setState(IFmiComponentState arg0)
			throws FmuInvocationException
	{
		// try
		// {
		// return comp
		// } catch (RemoteException e)
		// {
		// logger.error("RemoteException", e);
		// }
		return null;
	}

	@Override
	public Fmi2Status setStrings(long[] scalarValueIndices, String[] values)
			throws InvalidParameterException, FmiInvalidNativeStateException
	{
		try
		{
			return comp.setStrings(scalarValueIndices, values);
		} catch (RemoteException e)
		{
			logger.error("RemoteException", e);
		}
		return null;
	}

	@Override
	public Fmi2Status setupExperiment(boolean toleranceDefined,
			double tolerance, double startTime, boolean stopTimeDefined,
			double stopTime) throws FmuInvocationException
	{
		try
		{
			return comp.setupExperiment(toleranceDefined, tolerance, startTime, stopTimeDefined, stopTime);
		} catch (RemoteException e)
		{
			logger.error("RemoteException", e);
		}
		return null;
	}

	@Override
	public Fmi2Status terminate() throws FmuInvocationException
	{
		try
		{
			return comp.terminate();
		} catch (RemoteException e)
		{
			logger.error("RemoteException", e);
		}
		return null;
	}

}
