#!/bin/sh
# Maven Wrapper
# Set JAVA_HOME
[ -z "$JAVA_HOME" ] && JAVA_HOME="C:/Program Files/Microsoft/jdk-21.0.11.10-hotspot"
[ ! -f "$JAVA_HOME/bin/java" ] && JAVA_HOME="F:/IntelliJ IDEA 2025.3.2/jbr"

# Find the project base dir
PRG="$0"
while [ -h "$PRG" ]; do
    ls=`ls -ld "$PRG"`
    link=`expr "$ls" : '.*-> \(.*\)$'`
    if expr "$link" : '/.*' > /dev/null; then
        PRG="$link"
    else
        PRG=`dirname "$PRG"`/"$link"
    fi
done
SAVED="`pwd`"
cd "`dirname \"$PRG\"`/" >/dev/null
APP_HOME="`pwd -P`"
cd "$SAVED" >/dev/null

# Determine default JVM options
if [ -z "$MAVEN_OPTS" ]; then
    MAVEN_OPTS="-Xmx1024m"
fi

WRAPPER_LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain

exec "$JAVA_HOME/bin/java" $MAVEN_OPTS \
    -classpath "$APP_HOME/.mvn/wrapper/maven-wrapper.jar" \
    "-Dmaven.home=$MAVEN_HOME" \
    "-Dmaven.multiModuleProjectDirectory=$MAVEN_PROJECTBASEDIR" \
    ${MAVEN_OPTS} \
    $WRAPPER_LAUNCHER "$@"
