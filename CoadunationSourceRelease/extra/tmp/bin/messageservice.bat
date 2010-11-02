@echo off
set JAVA_HOME='Replace with Java JDK path'
set EXTRA="'Replace with Coadunation install directory'/lib/jacorb.jar;'Replace with Coadunation install directory'/lib/log4j-1.2.13.jar;'Replace with Coadunation install directory'/lib/logkit-1.2.jar'Replace with Coadunation install directory'/lib/CoadunationClient.jar;'Replace with Coadunation install directory'/clientlib/MessageServiceClient.jar;'Replace with Coadunation install directory'/tools/MessageServiceCommandLineTool.jar;'Replace with Coadunation install directory'/clientlib/avalon-framework-4.1.5.jar;"
set JAVA='Replace with Java runtime enviroment path'
set JAVA_OPTS=-Djava.security.policy=="'Replace with Coadunation install directory'/etc/server.policy"
set JAVA_OPTS=%JAVA_OPTS% -Djava.security.manager

%JAVA% %JAVA_OPTS% -Xmx128M -cp %EXTRA% com.rift.coad.commandline.messageservicecommandlinetool.Main %*