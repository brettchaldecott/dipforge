#!/usr/bin/env bash

export EXTRA="'Replace with Coadunation install directory'/lib/jacorb.jar:'Replace with Coadunation install directory'/lib/log4j-1.2.13.jar:'Replace with Coadunation install directory'/lib/logkit-1.2.jar:'Replace with Coadunation install directory'/lib/CoadunationClient.jar:'Replace with Coadunation install directory'/clientlib/JythonDaemonClient.jar:'Replace with Coadunation install directory'/tools/JythonDaemonCommandLineTool.jar:'Replace with Coadunation install directory'/clientlib/avalon-framework-4.1.5.jar:"
export JAVA='Replace with Java JDK path'/bin/java
JAVA_OPTS=-Djava.security.policy=="'Replace with Coadunation install directory'/etc/server.policy"
JAVA_OPTS="${JAVA_OPTS} -Djava.security.manager"
export JAVA_OPTS=${JAVA_OPTS}

${JAVA} ${JAVA_OPTS} -Xmx128M -cp ${EXTRA} com.rift.coad.commandline.jythondaemoncommandlinetool.Main $*
