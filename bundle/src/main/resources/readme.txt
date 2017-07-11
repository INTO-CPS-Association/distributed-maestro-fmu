This is the distributed FMU plugin bundle

* daemon

This is the external program that hosts a remote interface for the FMU. This is what you need to run on the platform external to the COE

* distribution-master

This is the COE plugin for distribution, it provides a custom factory for instantiating remote FMUs. It is enabled by adding this jar to the class path of the COE and specifying this property: ` -Dcoe.fmu.custom.factory=org.intocps.orchestration.coe.distribution.DistributedFmuFactory`

The FMU URI used in the configuration should look similar to this:

Normal local URI used by the COE `file:///my/path/to/model.fmu`

The remote URI should look like: `rmi://<remote-ip>/FMU#file:///my/path/to/model.fmu` note that the path given it local to the COE it will upload the FMU to the daemon.

It must be activated using: -Dcoe.fmu.custom.factory=org.intocps.orchestration.coe.distribution.DistributedFmuFactory

* dcoe

This is a pre-packaged COE instance which includes the distributed FMU plugin

----------------------------- USAGE -------------------------------------------
The daemon can be started with:

```
java -jar daemon-*-jar-with-dependencies.jar -ip4 -host <host-ip>
```

The arguments are optional use --help for a complete list.


Running the simulation with the COE:

Option 1: Use the pre-bundled d(istributed)coe

It is started as:

```
java -Dcoe.fmu.custom.factory=org.intocps.orchestration.coe.distribution.DistributedFmuFactory -jar dcoe-*jar-with-dependencies.jar
```

Option 2: One-shot mode where the COE reads the JSON config directly from file

```
java -Dcoe.fmu.custom.factory=org.intocps.orchestration.coe.distribution.DistributedFmuFactory -jar dcoe-*jar-with-dependencies.jar --oneshot --config <path-to-config-json> --starttime 0 --endtime 1
```

Option 3: Manually add the distribution-master to the classpath of the COE note that if the native FMI library in the COE is incompatible with the distribution-master then a runtime RMI exception will be raised.

```
java -Dcoe.fmu.custom.factory=org.intocps.orchestration.coe.distribution.DistributedFmuFactory  -cp coe.jar:distribution-master-*.jar org.intocps.orchestration.coe.CoeMain --oneshot --config <path-to-config-json> --starttime 0 --endtime 1
```

