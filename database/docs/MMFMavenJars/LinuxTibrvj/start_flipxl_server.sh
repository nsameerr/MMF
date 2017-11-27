export PATH=/usr/tibco/tibrv/bin:$PATH
export LD_LIBRARY_PATH=/usr/tibco/tibrv/lib:$LD_LIBRARY_PATH
export CLASSPATH=/usr/tibco/tibrv/lib/tibrvnative.jar:$CLASSPATH
cd /home/glassfish4/glassfish/bin/
./asadmin start-domain
echo $PATH
echo $LD_LIBRARY_PATH
echo $CLASSPATH