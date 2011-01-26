#!/usr/bin/env bash

# setup the java path
JAVA_PATH=/usr/lib/jvm/java-6-openjdk/bin/java
DIPFORGE_HOME=/home/brettc/Documents/external/github/dipforge.git/release/dipforge/

if [ -f $JAVA_PATH ]
then
   export JAVA=$JAVA_PATH;
else
   export JAVA=`which java`;
fi

export EXTRA="${DIPFORGE_HOME}/lib/jacorb.jar:${DIPFORGE_HOME}/lib/log4j-1.2.13.jar:${DIPFORGE_HOME}/lib/logkit-1.2.jar:${DIPFORGE_HOME}/lib/CoadunationClient.jar:${DIPFORGE_HOME}/clientlib/DeploymentDaemonClient.jar:${DIPFORGE_HOME}/tools/DeploymentDaemonCommandLineTool.jar:${DIPFORGE_HOME}/clientlib/avalon-framework-4.1.5.jar:"
JAVA_OPTS=-Djava.security.policy=="${DIPFORGE_HOME}/etc/server.policy"
JAVA_OPTS="${JAVA_OPTS} -Djava.security.manager"
export JAVA_OPTS=${JAVA_OPTS}

${JAVA} ${JAVA_OPTS} -Xmx128M -cp ${EXTRA} com.rift.coad.commandline.deploymentdaemoncommandlinetool.Main $*
