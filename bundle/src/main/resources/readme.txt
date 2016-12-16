This is the distributed FMU plugin bundle

* daemon

This is the external program that hosts a remote interface for the FMU. This is what you need to run on the platform external to the COE

* distribution-master

This is the COE plugin for distribition, it provides a custom factory for instantiating remote FMUs. It is enabled by adding this jar to the class path of the COE and specifying this property: ` -Dcoe.fmu.custom.factory=org.intocps.orchestration.coe.distribution.DistributedFmuFactory`

The FMU URI used in the configuration should look similar to this:

Normal local URI used by the COE `file:///my/path/to/model.fmu`

The remote URI should look like: `rmi://<remote-ip>/FMU#file:///my/path/to/model.fmu` note that the path given it local to the COE it will upload the FMU to the daemon.


* dcoe

This is a pre-packaged COE instance which includes the distributed FMU plugin

It is started as:

```
java -Dcoe.fmu.custom.factory=org.intocps.orchestration.coe.distribution.DistributedFmuFactory -jar dcoe-*jar-with-dependencies.jar
```
