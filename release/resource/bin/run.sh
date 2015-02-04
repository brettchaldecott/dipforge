#!/usr/bin/env bash

JDK_JAVA_PATH=$JDKPath
JAVA_PATH=$JDKPath
export JDK_JAVA_PATH JAVA_PATH

if [ -d $JDK_JAVA_PATH ]
then
   echo "Using JDK_JAVA_PATH [$JDK_JAVA_PATH]"
else
   echo "JDK_JAVA_PATH must be set correctly"
   exit -1
fi

# setup the java path
JAVA_PATH=$JDK_JAVA_PATH/bin/java

if [ -f $JAVA_PATH ]
then
   export JAVA=$JAVA_PATH
else
   export JAVA=`which java`
fi

# setup tools path
TOOLS=$JDK_JAVA_PATH/lib/tools.jar
DIPFORGE_HOME=$INSTALL_PATH
export DIPFORGE_HOME
VERSION=$APP_VER
export VERSION

if [ -f $TOOLS ]
then
   export COAD_LIB_DIRS=$DIPFORGE_HOME/lib:${TOOLS}
else
   export COAD_LIB_DIRS=$DIPFORGE_HOME/lib
fi

# Extra vars
export EXTRA=""
JAVA_OPTS="-Dcoad.config=com.rift.coad.lib.configuration.xml.XMLConfigurationFactory"
JAVA_OPTS="$JAVA_OPTS -Dxml.config.path=$DIPFORGE_HOME/etc/config.xml"
JAVA_OPTS="$JAVA_OPTS -DLog.File=$DIPFORGE_HOME/etc/log4j.properties"
JAVA_OPTS="$JAVA_OPTS -Djava.security.policy==$DIPFORGE_HOME/etc/server.policy"
JAVA_OPTS="$JAVA_OPTS -Djava.security.manager"
JAVA_OPTS="$JAVA_OPTS -Dsptmail.data.directory=$DIPFORGE_HOME/var/spt"
JAVA_OPTS="$JAVA_OPTS -Duser.home=$DIPFORGE_HOME/var/home"
JAVA_OPTS="$JAVA_OPTS -Dbase.dir=$DIPFORGE_HOME"
export JAVA_OPTS="$JAVA_OPTS -Djava.rmi.server.RMIClassLoaderSpi=com.rift.coad.RemoteClassLoaderSpi"
export CURRENT_DIR=$DIPFORGE_HOME

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
echo $JAVA $JAVA_OPTS -Xmx2048M -XX:PermSize=128M -XX:MaxPermSize=512M -jar $DIPFORGE_HOME/sbin/CoadunationBase-$VERSION.jar
$JAVA $JAVA_OPTS -Xmx2048M -XX:PermSize=128M -XX:MaxPermSize=512M -jar $DIPFORGE_HOME/sbin/CoadunationBase-$VERSION.jar
