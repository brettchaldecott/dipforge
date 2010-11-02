@echo off
set JAVA_HOME=C:\Program Files\Java\jdk1.5.0_06
set JAVA_OPTS=-Dfile.reference.ant.jar=../lib/ant.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.ant-1.6.5.jar=../lib/ant.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.antlr-2.7.6.jar=../lib/antlr-2.7.6.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.avalon-framework-4.1.5.jar=../lib/avalon-framework-4.1.5.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.axis-ant.jar=../lib/axis-ant.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.axis.jar=../lib/axis.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.backport-util-concurrent.jar=../lib/backport-util-concurrent.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.commons-cli-1.0.jar=../lib/commons-cli-1.0.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.commons-collections-3.2.jar=../lib/commons-collections-3.2.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.commons-dbcp-1.2.1.jar=../lib/commons-dbcp-1.2.1.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.commons-discovery-0.2.jar=../lib/commons-discovery-0.2.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.commons-pool-1.3.jar=../lib/commons-pool-1.3.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.commons-logging-1.0.4.jar=../lib/commons-logging-1.0.4.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.connector-1_5.jar=../lib/connector-1_5.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.cosnaming.jar=../lib/cosnaming.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.howl.jar=../lib/howl.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.irmi.jar=../lib/irmi.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.jacorb.jar=../lib/jacorb.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.httpcore-4.0-beta1.jar=../lib/httpcore-4.0-beta1.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.httpcore-nio-4.0-beta1.jar=../lib/httpcore-nio-4.0-beta1.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.jaxrpc.jar=../lib/jaxrpc.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.jotm.jar=../lib/jotm.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.jotm_iiop_stubs.jar=../lib/jotm_iiop_stubs.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.jta-spec1_0_1.jar=../lib/jta-spec1_0_1.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.jts1_0.jar=../lib/jts1_0.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.log4j-1.2.13.jar=../lib/log4j-1.2.13.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.log4j-1.2.13.jar-1=../lib/log4j-1.2.13.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.logkit-1.2.jar=../lib/logkit-1.2.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.mysql-connector-java-3.1.12-bin.jar=../lib/mysql-connector-java-5.0.7-bin.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.objectweb-datasource.jar=../lib/objectweb-datasource.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.ow_carol-all.jar=../lib/ow_carol-all.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.ow_carol.jar=../lib/ow_carol.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.ow_carol_iiop_delegate.jar=../lib/ow_carol_iiop_delegate.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.providerutil.jar=../lib/providerutil.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.saaj.jar=../lib/saaj.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.wsdl4j-1.5.1.jar=../lib/wsdl4j-1.5.1.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.xapool.jar=../lib/xapool.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.xstream-1.1.3.jar=../lib/xstream-1.1.3.jar
set JAVA_OPTS=%JAVA_OPTS% -Dlibs.junit.classpath=/data01/documents/libraries/coadunation/junit4.1/junit-4.1.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.hsqldb.jar=../lib/hsqldb.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.0010-HsqlDBEngineDaemon.jar=../0010-HsqlDBEngineDaemon/dist/0010-HsqlDBEngineDaemon.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.HsqlDBEngineClient.jar=../HsqlDBEngineClient/dist/HsqlDBEngineClient.jar
set JAVA_OPTS=%JAVA_OPTS% -Djythonlib.lib.dir=../jython-windows
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.jython.jar=../lib/jython.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.antlr-2.7.6rc1.jar=../lib/antlr-2.7.6.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.asm.jar=../lib/asm.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.c3p0-0.9.0.jar=../lib/c3p0-0.9.1.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.cglib-2.1.3.jar=../lib/hibernate-cglib-repack-2.1_3.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.dom4j-1.6.1.jar=../lib/dom4j-1.6.1.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.javassist-3.4.GA.jar=../lib/javassist-3.4.GA.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.slf4j-api-1.5.5.jar=../lib/slf4j-api-1.5.5.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.slf4j-simple-1.5.5.jar=../lib/slf4j-simple-1.5.5.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.commons-collections-3.1.jar=../lib/commons-collections-3.1.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.jta-1.1.jar=../lib/commons-collections-3.1.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.ehcache-1.1.jar=../lib/ehcache-1.2.3.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.hibernate3.jar=../lib/hibernate3.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.hibernate3.jar-1=../lib/hibernate3.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.dnsjava-2.0.6.jar=../lib/dnsjava-2.0.6.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.javamaildir-0.6.jar=../lib/javamaildir-0.6.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.activation.jar=../lib/activation.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.mail.jar=../lib/mail.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.pop3.jar=../lib/pop3.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.webmail.war=../lib/webmail.war
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.0050-Timer.jar=../0050-Timer/dist/0050-Timer.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.TimerClient.jar=../TimerClient/dist/TimerClient.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.TimerCommandLineTool.jar=../TimerCommandLineTool/dist/TimerCommandLineTool.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.0040-ServiceBroker.jar=../0040-ServiceBroker/dist/0040-ServiceBroker.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.0040-MessageService.jar=../0040-MessageService/dist/0040-MessageService.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.MessageServiceClient.jar=../MessageServiceClient/dist/MessageServiceClient.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.CoadunationUtil.jar=../CoadunationUtil/dist/CoadunationUtil.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.ServiceBrokerClient.jar=../ServiceBrokerClient/dist/ServiceBrokerClient.jar
set JAVA_OPTS=%JAVA_OPTS% -Dreference.ServiceBrokerClient.jar=../ServiceBrokerClient/dist/ServiceBrokerClient.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.ServiceBrokerCommandLineTool.jar=../ServiceBrokerCommandLineTool/dist/ServiceBrokerCommandLineTool.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.0100-JythonDaemon.jar=../0100-JythonDaemon/dist/0100-JythonDaemon.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.JythonDaemonClient.jar=../JythonDaemonClient/dist/JythonDaemonClient.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.JythonDaemonCommandLineTool.jar=../JythonDaemonCommandLineTool/dist/JythonDaemonCommandLineTool.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.0101-DeploymentDaemon.jar=../0101-DeploymentDaemon/dist/0101-DeploymentDaemon.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.DeploymentDaemonClient.jar=../DeploymentDaemonClient/dist/DeploymentDaemonClient.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.DeploymentDaemonCommandLineTool.jar=../DeploymentDaemonCommandLineTool/dist/DeploymentDaemonCommandLineTool.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.CoadunationHibernate.jar=../CoadunationHibernate/dist/CoadunationHibernate.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.jta.jar=../lib/jta-spec1_0_1.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.CoadunationBase.jar=../CoadunationBase/dist/CoadunationBase.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.CoadunationClient.jar=../CoadunationClient/dist/CoadunationClient.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.CoadunationLib.jar=../CoadunationLib/dist/CoadunationLib.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.log4j-1.2.11.jar=../lib/log4j-1.2.13.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.CoadunationCommon.jar=../CoadunationCommon/dist/CoadunationCommon.jar
set JAVA_OPTS=%JAVA_OPTS% -Dproject.CoadunationBase=../CoadunationBase
set JAVA_OPTS=%JAVA_OPTS% -Dproject.CoadunationAnnotations=../CoadunationAnnotations
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.CoadunationAnnotations.jar=../CoadunationAnnotations/dist/CoadunationAnnotations.jar
set JAVA_OPTS=%JAVA_OPTS% -Dproject.ServiceBrokerClient=../ServiceBrokerClient
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.rome-1.0RC1.jar=../lib/rome-1.0RC1.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.jaxen-jdom.jar=../lib/jaxen-jdom.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.xalan.jar=../lib/xalan.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.xml-apis.jar=../lib/xml-apis.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.jaxen-core.jar=../lib/jaxen-core.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.saxpath.jar=../lib/saxpath.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.xerces.jar=../lib/xerces.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.jdom.jar=../lib/jdom.jar
set JAVA_OPTS=%JAVA_OPTS% -Dproject.EventServerClient=../EventServerClient
set JAVA_OPTS=%JAVA_OPTS% -Dproject.RSSReaderClient=../RSSReaderClient
set JAVA_OPTS=%JAVA_OPTS% -Dtemp.bin.dir=../tmp/bin
set JAVA_OPTS=%JAVA_OPTS% -Dtemp.etc.dir=../tmp/etc
set JAVA_OPTS=%JAVA_OPTS% -Dtemp.tomcat.dir=../tmp/var/tomcat
set JAVA_OPTS=%JAVA_OPTS% -Dproject.reference.1004-SampleMessageServices=../1004-SampleMessageServices
set JAVA_OPTS=%JAVA_OPTS% -Dproject.MessageServiceClient=../MessageServiceClient
set JAVA_OPTS=%JAVA_OPTS% -Dreference.MessageServiceClient.jar=../MessageServiceClient/dist/MessageServiceClient.jar
set JAVA_OPTS=%JAVA_OPTS% -Dreference.MessageServiceCommandLineTool.jar=../MessageServiceCommandLineTool/dist/MessageServiceCommandLineTool.jar
set JAVA_OPTS=%JAVA_OPTS% -Dreference.DNSServerClient.jar=../DNSServerClient/dist/DNSServerClient.jar
set JAVA_OPTS=%JAVA_OPTS% -Dreference.EmailServerClient.jar=../EmailServerClient/dist/EmailServerClient.jar
set JAVA_OPTS=%JAVA_OPTS% -Dreference.CoadunationRDBAuth.jar=../CoadunationRDBAuth/dist/CoadunationRDBAuth.jar
set JAVA_OPTS=%JAVA_OPTS% -Dreference.RDBUserManagerClient.jar=../RDBUserManagerClient/dist/RDBUserManagerClient.jar
set JAVA_OPTS=%JAVA_OPTS% -Dreference.0020-RDBUserManager.jar=../0020-RDBUserManager/dist/0020-RDBUserManager.jar
set TOMCAT_OPTS=-Dfile.reference.CoadunationLib.jar=../CoadunationLib/dist/CoadunationLib.jar
set TOMCAT_OPTS=%TOMCAT_OPTS% -Dproject.CoadunationBase=../CoadunationBase
set TOMCAT_OPTS=%TOMCAT_OPTS% -Dproject.CoadunationAnnotations=../CoadunationAnnotations
set TOMCAT_OPTS=%TOMCAT_OPTS% -Dfile.reference.CoadunationClient.jar=../CoadunationClient/dist/CoadunationClient.jar
set TOMCAT_OPTS=%TOMCAT_OPTS% -Dfile.reference.CoadunationCommon.jar=../CoadunationClient/dist/CoadunationCommon.jar
set TOMCAT_OPTS=%TOMCAT_OPTS% -Dfile.reference.CoadunationSecurity.jar=../CoadunationSecurity/dist/CoadunationSecurity.jar
set TOMCAT_OPTS=%TOMCAT_OPTS% -Dfile.reference.TomcatClient.jar=../TomcatClient/dist/TomcatClient.jar
set TOMCAT_OPTS=%TOMCAT_OPTS% -Dfile.reference.CoadunationAnnotations.jar=../CoadunationAnnotations/dist/CoadunationAnnotations.jar
set TOMCAT_OPTS=%TOMCAT_OPTS% -Dfile.reference.log4j-1.2.13.jar=../lib/log4j-1.2.13.jar
set TOMCAT_OPTS=%TOMCAT_OPTS% -Dfile.reference.servlet-api.jar=../lib/servlet-api.jar
set TOMCAT_OPTS=%TOMCAT_OPTS% -Dfile.reference.tomcat-coyote.jar=../lib/tomcat-coyote.jar
set TOMCAT_OPTS=%TOMCAT_OPTS% -Dfile.reference.annotations-api.jar=../lib/annotations-api.jar
set TOMCAT_OPTS=%TOMCAT_OPTS% -Dfile.reference.catalina.jar=../lib/catalina.jar
set TOMCAT_OPTS=%TOMCAT_OPTS% -Dfile.reference.catalina-ant.jar=../lib/catalina-ant.jar
set TOMCAT_OPTS=%TOMCAT_OPTS% -Dfile.reference.catalina-ha.jar=../lib/catalina-ha.jar
set TOMCAT_OPTS=%TOMCAT_OPTS% -Dfile.reference.catalina-tribes.jar=../lib/catalina-tribes.jar
set TOMCAT_OPTS=%TOMCAT_OPTS% -Dfile.reference.el-api.jar=../lib/el-api.jar
set TOMCAT_OPTS=%TOMCAT_OPTS% -Dfile.reference.jasper-el.jar=../lib/jasper-el.jar
set TOMCAT_OPTS=%TOMCAT_OPTS% -Dfile.reference.jasper-jdt.jar=../lib/jasper-jdt.jar
set TOMCAT_OPTS=%TOMCAT_OPTS% -Dfile.reference.jasper.jar=../lib/jasper.jar
set TOMCAT_OPTS=%TOMCAT_OPTS% -Dfile.reference.jsp-api.jar=../lib/jsp-api.jar
set TOMCAT_OPTS=%TOMCAT_OPTS% -Dfile.reference.tomcat-dbcp.jar=../lib/tomcat-dbcp.jar
set TOMCAT_OPTS=%TOMCAT_OPTS% -Dfile.reference.tomcat-i18n-es.jar=../lib/tomcat-i18n-es.jar
set TOMCAT_OPTS=%TOMCAT_OPTS% -Dfile.reference.tomcat-i18n-fr.jar=../lib/tomcat-i18n-fr.jar
set TOMCAT_OPTS=%TOMCAT_OPTS% -Dfile.reference.tomcat-i18n-ja.jar=../lib/tomcat-i18n-ja.jar
set TOMCAT_OPTS=%TOMCAT_OPTS% -Dfile.reference.tomcat-juli-adapters.jar=../lib/tomcat-juli-adapters.jar
set TOMCAT_OPTS=%TOMCAT_OPTS% -Dfile.reference.tomcat-juli.jar=../lib/tomcat-juli.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.CoadunationSecurity.jar=../CoadunationSecurity/dist/CoadunationSecurity.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.TomcatClient.jar=../TomcatClient/dist/TomcatClient.jar
set JAVA_OPTS=%JAVA_OPTS% -Dfile.reference.0060-Tomcat.jar=../0060-Tomcat/dist/0060-Tomcat.jar
set JAVA_OPTS=%JAVA_OPTS% -Dproject.CoadunationDesktop=../CoadunationDesktop

call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\CoadunationBase\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\CoadunationAnnotations\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\CoadunationClient\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\CoadunationSecurity\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\CoadunationLib\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\CoadunationCommon\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\HsqlDBEngineClient\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\0010-HsqlDBEngineDaemon\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\CoadunationHibernate\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\TimerClient\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\0050-Timer\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\ServiceBrokerClient\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\MessageServiceClient\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\0040-MessageService\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\MessageServiceCommandLineTool\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\CoadunationUtil\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\0040-ServiceBroker\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\ServiceBrokerCommandLineTool\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\JythonDaemonClient\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\JythonDaemonCommandLineTool\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\0100-JythonDaemon\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\DeploymentDaemonClient\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\0101-DeploymentDaemon\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\DeploymentDaemonCommandLineTool\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\TimerCommandLineTool\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\TomcatClient\build.xml jar
call ..\ant\bin\ant.bat %TOMCAT_OPTS% -f ..\0060-Tomcat\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\DNSServerClient\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\0005-DNSServer\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\EmailServerClient\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\0045-EmailServer\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\CoadunationRDBAuth\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\RDBUserManagerClient\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\0020-RDBUserManager\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\DesktopServerClient\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\EventServerClient\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\RSSReaderClient\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\0050-DesktopServer\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\0050-EventServer\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f ..\0102-RSSReader\build.xml jar
call ..\ant\bin\ant.bat %JAVA_OPTS% -f build.xml
