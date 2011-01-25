#!/usr/bin/env bash

# setup the java path
JAVA_PATH=/usr/lib/jvm/java-6-openjdk/bin/java

if [ -f $JAVA_PATH ]
then
   export JAVA=$JAVA_PATH
else
   export JAVA=`which java`
fi

# setup tools path
TOOLS=/usr/lib/jvm/java-6-openjdk/lib/tools.jar

if [ -f $TOOLS ]
then
   export COAD_LIB_DIRS=/home/brettc/Documents/external/CoadunationOS/lib:$TOOLS
else
   export COAD_LIB_DIRS=/home/brettc/Documents/external/CoadunationOS/lib
fi

# Extra vars
export EXTRA=""
JAVA_OPTS="-Dcoad.config=com.rift.coad.lib.configuration.xml.XMLConfigurationFactory"
JAVA_OPTS="${JAVA_OPTS} -Dxml.config.path=/home/brettc/Documents/external/CoadunationOS/etc/config.xml"
JAVA_OPTS="${JAVA_OPTS} -DLog.File=/home/brettc/Documents/external/CoadunationOS/etc/log4j.properties"
JAVA_OPTS="${JAVA_OPTS} -Djava.security.policy==/home/brettc/Documents/external/CoadunationOS/etc/server.policy"
JAVA_OPTS="${JAVA_OPTS} -Djava.security.manager"
JAVA_OPTS="${JAVA_OPTS} -Dsptmail.data.directory=/home/brettc/Documents/external/CoadunationOS/var/spt"
JAVA_OPTS="${JAVA_OPTS} -Duser.home=/home/brettc/Documents/external/CoadunationOS/var/home"
JAVA_OPTS="${JAVA_OPTS} -Dbase.dir=/home/brettc/Documents/external/CoadunationOS"
export JAVA_OPTS="${JAVA_OPTS} -Djava.rmi.server.RMIClassLoaderSpi=com.rift.coad.RemoteClassLoaderSpi"
export CURRENT_DIR=/home/brettc/Documents/external/CoadunationOS

# set the ulimit
if [ `uname` == 'Linux' ]; then
   if [ `ulimit -n` -le 1024 ]; then
      echo "Configuring the ulimit on linux"
      ulimit -n 10000
   else
      echo "The ulimit is already set to ulimited"
   fi
elif [ `uname` == 'SunOS' ]; then
   echo "Configuring the soft and hard ulimit on Solaris"
   ulimit -Sn 10000
   ulimit -Hn 15000
elif [ `uname` == 'Darwin' ]; then
   echo "Configuring the soft and hard ulimit on Darwin"
   ulimit -n 10000
else
   echo "Unknown system type file limit not configured"
fi


# run
echo ${JAVA} ${JAVA_OPTS} -Xmx768M -XX:PermSize=128M -XX:MaxPermSize=256M -jar /home/brettc/Documents/external/CoadunationOS/sbin/CoadunationBase.jar
${JAVA} ${JAVA_OPTS} -Xmx768M -XX:PermSize=128M -XX:MaxPermSize=256M -jar /home/brettc/Documents/external/CoadunationOS/sbin/CoadunationBase.jar 2> /dev/null
