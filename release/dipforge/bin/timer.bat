@echo off
set JAVA_HOME=/usr/lib/jvm/java-6-openjdk
set EXTRA="/home/brettc/Documents/external/CoadunationOS/lib/jacorb.jar;/home/brettc/Documents/external/CoadunationOS/lib/log4j-1.2.13.jar;/home/brettc/Documents/external/CoadunationOS/lib/logkit-1.2.jar;/home/brettc/Documents/external/CoadunationOS/lib/CoadunationClient.jar;/home/brettc/Documents/external/CoadunationOS\clientlib\TimerClient.jar;/home/brettc/Documents/external/CoadunationOS/tools/TimerCommandLineTool.jar;/home/brettc/Documents/external/CoadunationOS/clientlib/avalon-framework-4.1.5.jar;"
set JAVA="/usr/lib/jvm/java-6-openjdk/jre/bin/java"
set JAVA_OPTS=-Djava.security.policy=="/home/brettc/Documents/external/CoadunationOS/etc/server.policy"
set JAVA_OPTS=%JAVA_OPTS% -Djava.security.manager

%JAVA% %JAVA_OPTS% -Xmx128M -cp %EXTRA% com.rift.coad.commandline.timercommandlinetool.Main %*