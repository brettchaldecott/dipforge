#!/usr/bin/env bash

# setup the java path
JAVA_PATH="Replace with JDK home"/bin/java

if [ -f $JAVA_PATH ]
then
   export JAVA=$JAVA_PATH
else
   export JAVA=`which java`
fi

# setup tools path
TOOLS="Replace with JDK home"/lib/tools.jar

if [ -f $TOOLS ]
then
   export COAD_LIB_DIRS="Replace with Coadunation install dir"/lib:$TOOLS
else
   export COAD_LIB_DIRS="Replace with Coadunation install dir"/lib
fi

# Extra vars
export EXTRA=""
JAVA_OPTS="-Dcoad.config=com.rift.coad.lib.configuration.xml.XMLConfigurationFactory"
JAVA_OPTS="${JAVA_OPTS} -Dxml.config.path="Replace with Coadunation install dir"/etc/config.xml"
JAVA_OPTS="${JAVA_OPTS} -DLog.File="Replace with Coadunation install dir"/etc/log4j.properties"
JAVA_OPTS="${JAVA_OPTS} -Djava.security.policy=="Replace with Coadunation install dir"/etc/server.policy"
JAVA_OPTS="${JAVA_OPTS} -Djava.security.manager"
JAVA_OPTS="${JAVA_OPTS} -Dsptmail.data.directory="Replace with Coadunation install dir"/var/webmail"
export JAVA_OPTS="${JAVA_OPTS} -Djava.rmi.server.RMIClassLoaderSpi=com.rift.coad.RemoteClassLoaderSpi"
export CURRENT_DIR="Replace with Coadunation install dir" 
    
# run
echo ${JAVA} ${JAVA_OPTS} -Xmx512M -XX:PermSize=64M -XX:MaxPermSize=128M -jar "Replace with Coadunation install dir"/sbin/CoadunationBase.jar
${JAVA} ${JAVA_OPTS} -Xmx512M -XX:PermSize=64M -XX:MaxPermSize=128M -jar "Replace with Coadunation install dir"/sbin/CoadunationBase.jar 2> /dev/null

