@echo off
set JAVA_HOME=/usr/lib/jvm/java-6-openjdk
set DIPFORGE_HOME=/home/brettc/Documents/external/github/dipforge.git/release/dipforge/
set EXTRA="%DIPFORGE_HOME%/lib/jacorb.jar;%DIPFORGE_HOME%/lib/log4j-1.2.13.jar;%DIPFORGE_HOME%/lib/logkit-1.2.jar;%DIPFORGE_HOME%/lib/CoadunationClient.jar;%DIPFORGE_HOME%/clientlib/MessageServiceClient.jar;%DIPFORGE_HOME%/tools/MessageServiceCommandLineTool.jar;%DIPFORGE_HOME%/clientlib/avalon-framework-4.1.5.jar;"
set JAVA="/usr/lib/jvm/java-6-openjdk/jre/bin/java"
set JAVA_OPTS=-Djava.security.policy=="%DIPFORGE_HOME%/etc/server.policy"
set JAVA_OPTS=%JAVA_OPTS% -Djava.security.manager

%JAVA% %JAVA_OPTS% -Xmx128M -cp %EXTRA% com.rift.coad.commandline.messageservicecommandlinetool.Main %*
