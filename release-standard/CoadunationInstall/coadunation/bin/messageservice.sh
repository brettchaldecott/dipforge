#!/usr/bin/env bash

# setup the java path
JAVA_PATH=$JDKPath/bin/java

if [ -f $JAVA_PATH ]
then
   export JAVA=$JAVA_PATH;
else
   export JAVA=`which java`;
fi

export EXTRA="$INSTALL_PATH/lib/jacorb.jar:$INSTALL_PATH/lib/log4j-1.2.13.jar:$INSTALL_PATH/lib/logkit-1.2.jar:$INSTALL_PATH/lib/CoadunationClient.jar:$INSTALL_PATH/clientlib/MessageServiceClient.jar:$INSTALL_PATH/tools/MessageServiceCommandLineTool.jar:$INSTALL_PATH/clientlib/avalon-framework-4.1.5.jar:"
JAVA_OPTS=-Djava.security.policy=="$INSTALL_PATH/etc/server.policy"
JAVA_OPTS="${JAVA_OPTS} -Djava.security.manager"
export JAVA_OPTS=${JAVA_OPTS}

${JAVA} ${JAVA_OPTS} -Xmx128M -cp ${EXTRA} com.rift.coad.commandline.messageservicecommandlinetool.Main $*
