#!/bin/bash

export EXTRA=""
export JAVA=/usr/jdk1.5.0_06/bin/java
JAVA_OPTS="-Dcoad.config=com.rift.coad.lib.configuration.xml.XMLConfigurationFactory"
JAVA_OPTS="${JAVA_OPTS} -Dxml.config.path=/home/brett/development/coadunation/etc/config.xml"
JAVA_OPTS="${JAVA_OPTS} -DLog.File=/home/brett/development/coadunation/etc/log4j.properties"
JAVA_OPTS="${JAVA_OPTS} -Djava.security.policy==/home/brett/development/coadunation/etc/server.policy"
JAVA_OPTS="${JAVA_OPTS} -Djava.security.manager"
export JAVA_OPTS="${JAVA_OPTS} -Djava.rmi.server.RMIClassLoaderSpi=com.rift.coad.RemoteClassLoaderSpi"
export CURRENT_DIR=/home/brett/development/coadunation/
export COAD_LIB_DIRS=/home/brett/development/coadunation/lib:/usr/jdk1.5.0_06/lib/tools.jar


echo ${JAVA} ${JAVA_OPTS} -Xmx128M -jar /home/brett/development/coadunation/sbin/CoadunationBase.jar
${JAVA} ${JAVA_OPTS} -Xmx128M -jar /home/brett/development/coadunation/sbin/CoadunationBase.jar 2> /dev/null
