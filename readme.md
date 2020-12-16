# Distributed FMU Loader

This is an extension to the FMI interpreter included in Maestro (https://github.com/INTO-CPS-Association/maestro)

It works by starting a daemon on the remote system which then through Java RMI serves as a remote FMU proxy. Maestro is then started with the distribution loader in the classpath allowing the interpreter to understand this custom loader.

The mabl syntax for the remote loader (named `JavaRmiRemoteFMU2Loader`) is as follows.

```
load("JavaRmiRemoteFMU2Loader",  "<local fmu path>", "<guid>" , "<remote uri>");
```

example:

```
FMI2 fmu = load("JavaRmiRemoteFMU2Loader",  "/path/to/my.fmu", "{cfc65592-9ece-4563-9705-1581b6e7071c}" , "rmi://localhost:999/FMU");

```

# Notes

* http://www.javaworld.com/article/2076234/soa/get-smart-with-proxies-and-rmi.html
* RMI description 
http://www.cs.man.ac.uk/~fellowsd/work/usingRMI.html

# Release

```bash
mvn -Dmaven.repo.local=repository release:clean
mvn -Dmaven.repo.local=repository release:prepare -DreleaseVersion=${RELEASE_VER} -DdevelopmentVersion=${NEW_DEV_VER}
mvn -Dmaven.repo.local=repository release:perform
```
