@echo off
set JAVA_HOME="C:\Program Files\Java\jdk1.5.0_06\"
set EXTRA=""
set JAVA="C:\Program Files\Java\jdk1.5.0_06\bin\java"
set JAVA_OPTS=-Dcoad.config=com.rift.coad.lib.configuration.xml.XMLConfigurationFactory
set JAVA_OPTS=%JAVA_OPTS% -Dxml.config.path="C:\coadunation-dev\coadunation_0_99\CoadunationBase\config.xml"
set JAVA_OPTS=%JAVA_OPTS% -DLog.File="C:\coadunation-dev\coadunation_0_99\CoadunationBase\log4j.properties"
set JAVA_OPTS=%JAVA_OPTS% -Djava.security.policy==.\server.policy
set JAVA_OPTS=%JAVA_OPTS% -Djava.security.manager
set CURRENT_DIR=.\
set COAD_LIB_DIRS=%CURRENT_DIR%..\CoadunationLib\dist;C:\coadunation\lib;C:\Program Files\Java\jdk1.5.0_06\lib\tools.jar;
                                                                                                                     
echo %JAVA% %JAVA_OPTS% -Xmx128M -cp %EXTRA% -jar dist/CoadunationBase.jar
%JAVA% %JAVA_OPTS% -Xmx128M -cp %EXTRA% -jar dist/CoadunationBase.jar
