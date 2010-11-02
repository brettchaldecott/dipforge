@echo off
set JAVA_HOME='Replace with Java JDK path'
set EXTRA=""
set JAVA='Replace with Java runtime enviroment path'
set JAVA_OPTS=-Dcoad.config=com.rift.coad.lib.configuration.xml.XMLConfigurationFactory
set JAVA_OPTS=%JAVA_OPTS% -Dxml.config.path="'Replace with Coadunation install directory'/etc/config.xml"
set JAVA_OPTS=%JAVA_OPTS% -DLog.File="'Replace with Coadunation install directory'/etc/log4j.properties"
set JAVA_OPTS=%JAVA_OPTS% -Djava.security.policy=="'Replace with Coadunation install directory'/etc/server.policy"
set JAVA_OPTS=%JAVA_OPTS% -Djava.security.manager
set JAVA_OPTS=%JAVA_OPTS% -Djava.rmi.server.RMIClassLoaderSpi=com.rift.coad.RemoteClassLoaderSpi
set JAVA_OPTS=%JAVA_OPTS% -Dsptmail.data.directory='Replace with Coadunation install directory'/var/webmail
set CURRENT_DIR=./
set COAD_LIB_DIRS='Replace with Coadunation install directory'/lib;'Replace with Java JDK path'/lib/tools.jar;
                                                                                                                     
echo %JAVA% %JAVA_OPTS% -Xmx512M -XX:PermSize=64M -XX:MaxPermSize=128M -cp %EXTRA% -jar "'Replace with Coadunation install directory'/sbin/CoadunationBase.jar"
%JAVA% %JAVA_OPTS% -Xmx512M -XX:PermSize=64M -XX:MaxPermSize=128M -cp %EXTRA% -jar "'Replace with Coadunation install directory'/sbin/CoadunationBase.jar"
