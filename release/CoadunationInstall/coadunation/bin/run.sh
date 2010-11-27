#!/usr/bin/env bash

# setup the java path
JAVA_PATH=$JDKPath/bin/java

if [ -f $JAVA_PATH ]
then
   export JAVA=$JAVA_PATH
else
   export JAVA=`which java`
fi

# setup tools path
TOOLS=$JDKPath/lib/tools.jar

if [ -f $TOOLS ]
then
   export COAD_LIB_DIRS=$INSTALL_PATH/lib:$TOOLS
else
   export COAD_LIB_DIRS=$INSTALL_PATH/lib
fi

# Extra vars
export EXTRA=""
JAVA_OPTS="-Dcoad.config=com.rift.coad.lib.configuration.xml.XMLConfigurationFactory"
JAVA_OPTS="${JAVA_OPTS} -Dxml.config.path=$INSTALL_PATH/etc/config.xml"
JAVA_OPTS="${JAVA_OPTS} -DLog.File=$INSTALL_PATH/etc/log4j.properties"
JAVA_OPTS="${JAVA_OPTS} -Djava.security.policy==$INSTALL_PATH/etc/server.policy"
JAVA_OPTS="${JAVA_OPTS} -Djava.security.manager"
JAVA_OPTS="${JAVA_OPTS} -Dsptmail.data.directory=$INSTALL_PATH/var/spt"
export JAVA_OPTS="${JAVA_OPTS} -Djava.rmi.server.RMIClassLoaderSpi=com.rift.coad.RemoteClassLoaderSpi"
export CURRENT_DIR=$INSTALL_PATH

# run
echo ${JAVA} ${JAVA_OPTS} -Xmx512M -XX:PermSize=64M -XX:MaxPermSize=128M -jar $INSTALL_PATH/sbin/CoadunationBase.jar
${JAVA} ${JAVA_OPTS} -Xmx512M -XX:PermSize=64M -XX:MaxPermSize=128M -jar $INSTALL_PATH/sbin/CoadunationBase.jar 2> /dev/null
