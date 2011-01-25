#!/usr/bin/env bash

# setup the java path
JAVA_PATH=/usr/lib/jvm/java-6-openjdk/bin/java

if [ -f $JAVA_PATH ]
then
   export JAVA=$JAVA_PATH;
else
   export JAVA=`which java`;
fi
export EXTRA="/home/brettc/Documents/external/CoadunationOS/lib/jacorb.jar:/home/brettc/Documents/external/CoadunationOS/lib/log4j-1.2.13.jar:/home/brettc/Documents/external/CoadunationOS/lib/logkit-1.2.jar:/home/brettc/Documents/external/CoadunationOS/lib/CoadunationClient.jar:/home/brettc/Documents/external/CoadunationOS/clientlib/TimerClient.jar:/home/brettc/Documents/external/CoadunationOS/tools/TimerCommandLineTool.jar:/home/brettc/Documents/external/CoadunationOS/clientlib/avalon-framework-4.1.5.jar:"
JAVA_OPTS=-Djava.security.policy=="/home/brettc/Documents/external/CoadunationOS/etc/server.policy"
JAVA_OPTS="${JAVA_OPTS} -Djava.security.manager"
export JAVA_OPTS=${JAVA_OPTS}

${JAVA} ${JAVA_OPTS} -Xmx128M -cp ${EXTRA} com.rift.coad.commandline.timercommandlinetool.Main $*
