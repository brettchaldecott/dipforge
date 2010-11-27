@echo off
set JAVA_HOME=$JDKPath
set EXTRA="$INSTALL_PATH/lib/jacorb.jar;$INSTALL_PATH/lib/log4j-1.2.13.jar;$INSTALL_PATH/lib/logkit-1.2.jar;$INSTALL_PATH/lib/CoadunationClient.jar;$INSTALL_PATH/clientlib/MessageServiceClient.jar;$INSTALL_PATH/tools/MessageServiceCommandLineTool.jar;$INSTALL_PATH/clientlib/avalon-framework-4.1.5.jar;"
set JAVA="$JAVA_HOME/bin/java"
set JAVA_OPTS=-Djava.security.policy=="$INSTALL_PATH/etc/server.policy"
set JAVA_OPTS=%JAVA_OPTS% -Djava.security.manager

%JAVA% %JAVA_OPTS% -Xmx128M -cp %EXTRA% com.rift.coad.commandline.messageservicecommandlinetool.Main %*