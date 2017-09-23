@echo off
set JAVA_HOME="$JDKPath"
set EXTRA=""
set JAVA="$JAVA_HOME/bin/java"
set JAVA_OPTS=-Dcoad.config=com.rift.coad.lib.configuration.xml.XMLConfigurationFactory
set JAVA_OPTS=%JAVA_OPTS% -Dxml.config.path="$INSTALL_PATH/etc/config.xml"
set JAVA_OPTS=%JAVA_OPTS% -DLog.File="$INSTALL_PATH/etc/log4j.properties"
set JAVA_OPTS=%JAVA_OPTS% -Djava.security.policy=="$INSTALL_PATH/etc/server.policy"
set JAVA_OPTS=%JAVA_OPTS% -Djava.security.manager
set JAVA_OPTS=%JAVA_OPTS% -Dsptmail.data.directory="$INSTALL_PATH/var/spt"
set JAVA_OPTS=%JAVA_OPTS% -Djava.rmi.server.RMIClassLoaderSpi=com.rift.coad.RemoteClassLoaderSpi
set CURRENT_DIR=./
set COAD_LIB_DIRS=$INSTALL_PATH/lib;$JDKPath/lib/tools.jar;
                                                                                                                     
echo %JAVA% %JAVA_OPTS% -Xmx512M -XX:PermSize=64M -XX:MaxPermSize=128M -cp %EXTRA% -jar "$INSTALL_PATH/sbin/CoadunationBase.jar"
%JAVA% %JAVA_OPTS% -Xmx512M -XX:PermSize=64M -XX:MaxPermSize=128M -cp %EXTRA% -jar "$INSTALL_PATH/sbin/CoadunationBase.jar"
