@echo off
set JAVA_HOME="/usr/lib/jvm/java-6-openjdk"
set EXTRA=""
set JAVA="/usr/lib/jvm/java-6-openjdk/jre/bin/java"
set JAVA_OPTS=-Dcoad.config=com.rift.coad.lib.configuration.xml.XMLConfigurationFactory
set JAVA_OPTS=%JAVA_OPTS% -Dxml.config.path="/home/brettc/Documents/external/CoadunationOS/etc/config.xml"
set JAVA_OPTS=%JAVA_OPTS% -DLog.File="/home/brettc/Documents/external/CoadunationOS/etc/log4j.properties"
set JAVA_OPTS=%JAVA_OPTS% -Djava.security.policy=="/home/brettc/Documents/external/CoadunationOS/etc/server.policy"
set JAVA_OPTS=%JAVA_OPTS% -Djava.security.manager
set JAVA_OPTS=%JAVA_OPTS% -Dsptmail.data.directory="/home/brettc/Documents/external/CoadunationOS/var/spt"
set JAVA_OPTS=%JAVA_OPTS% -Duser.home="/home/brettc/Documents/external/CoadunationOS/var/home"
set JAVA_OPTS=%JAVA_OPTS% -Dbase.dir="/home/brettc/Documents/external/CoadunationOS"
set JAVA_OPTS=%JAVA_OPTS% -Djava.rmi.server.RMIClassLoaderSpi=com.rift.coad.RemoteClassLoaderSpi
set CURRENT_DIR=./
set COAD_LIB_DIRS=/home/brettc/Documents/external/CoadunationOS/lib;/usr/lib/jvm/java-6-openjdk/lib/tools.jar;

echo %JAVA% %JAVA_OPTS% -Xmx768M -XX:PermSize=128M -XX:MaxPermSize=256M -cp %EXTRA% -jar "/home/brettc/Documents/external/CoadunationOS/sbin/CoadunationBase.jar"
%JAVA% %JAVA_OPTS% -Xmx768M -XX:PermSize=128M -XX:MaxPermSize=256M -cp %EXTRA% -jar "/home/brettc/Documents/external/CoadunationOS/sbin/CoadunationBase.jar"
