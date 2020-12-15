package org.intocps.orchestration.coe.distribution;

import org.intocps.fmi.Fmi2Status;
import org.intocps.fmi.FmuResult;

import java.io.Serializable;

public class RemoteFmuResult<T> implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 6574179974286234411L;
    final public T result;
    final public Fmi2Status status;

    public RemoteFmuResult(Fmi2Status status, T result) {
        this.result = result;
        this.status = status;
    }

    public RemoteFmuResult() {
        this(null, null);
    }


    public RemoteFmuResult(FmuResult<T> result) {
        this(result.status, result.result);
    }

    public static <T> RemoteFmuResult<T> wrap(FmuResult<T> result) {
        return new RemoteFmuResult<T>(result);
    }

    public FmuResult<T> unwrap() {
        return new FmuResult<T>(status, result);
    }


}
