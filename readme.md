# Distributed COE


# Inspiration

http://www.javaworld.com/article/2076234/soa/get-smart-with-proxies-and-rmi.html


# Release

```bash
mvn -Dmaven.repo.local=repository release:clean
mvn -Dmaven.repo.local=repository release:prepare -DreleaseVersion=${RELEASE_VER} -DdevelopmentVersion=${NEW_DEV_VER}
mvn -Dmaven.repo.local=repository release:perform
```
