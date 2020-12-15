#!/bin/bash
set -e
maestro=../../../../dmaestro/target/dmaestro-0.0.7-SNAPSHOT-jar-with-dependencies.jar
daemon=../../../../daemon/target/daemon-0.0.7-SNAPSHOT-jar-with-dependencies.jar

loaderModule=../../../../distribution-maestro-loader/src/main/resources/org/intocps/maestro/distributed/JavaRmiRemoteFMU2Loader.mabl


echo "Running local sim"

java -jar $maestro -v -i LoadLocalFmu.mabl > local.log

truncate -s 0 verdict.txt
if grep --quiet "LoggerValue - OK 9" local.log; then
  echo OK
  echo LOCAL OK >> verdict.txt
else
  echo FAIL
  echo LOCAL FAIL >> verdict.txt
fi



echo "Running remote sim"

#trap kill -9 $(cat daemon.pid) EXIT

trap "trap - SIGTERM && kill -- -$$" SIGINT SIGTERM EXIT

java -jar $daemon -ip4 -host localhost &


java -jar $maestro -v -i LoadRemoteFmu.mabl $loaderModule > remote.log


if grep --quiet "LoggerValue - OK 9" remote.log; then
  echo OK
  echo REMOTE OK >> verdict.txt
else
  echo FAIL
  echo REMOTE FAIL >> verdict.txt
fi



# do other stuff
#kill $FOO_PID