# Compile & run on Java17

`--add-opens java.base/java.util=ALL-UNNAMED`

# Opened ports

`ps -eo pid,args | grep aeron | column -t | cut -d' ' -f1 | xargs -I _ lsof -Pan -p _ -i`

On the NAME column: The local host name or IP number is followed by a colon (':'), the port, ''->'', and the two-part remote address

# Subs

### 0.0.0.0:0

Opens a random port

```
COMMAND   PID USER   FD   TYPE DEVICE SIZE/OFF NODE NAME
java    41949    j   15u  IPv4 634789      0t0  UDP *:42530 
```

### 0.0.0.0:12345

```
COMMAND   PID USER   FD   TYPE DEVICE SIZE/OFF NODE NAME
java    48971    j   15u  IPv4 709157      0t0  UDP *:12345
```

# Pubs

### 127.0.0.100:12345

```
COMMAND   PID USER   FD   TYPE DEVICE SIZE/OFF NODE NAME
java    45965    j   15u  IPv4 680483      0t0  UDP 127.0.0.1:47792->127.0.0.100:12345
``` 

# Local UDP connect ?

for pub **127.0.0.1:12345**
- gets connected to sub **127.0.0.1:12345**
- doesn't get connected to sub **0.0.0.0:0**
- doesn't get connected to sub **127.0.0.1:0**

pub **127.0.0.1:whatever_port_was_randomly_assigned** when doing a catch-all sub:

```
Exception in thread "[sender,receiver,driver-conductor]" java.lang.NoClassDefFoundError: io/aeron/logbuffer/FrameDescriptor
	at io.aeron.driver.media.UdpChannelTransport.isValidFrame(UdpChannelTransport.java:372)
	at io.aeron.driver.media.DataTransportPoller.poll(DataTransportPoller.java:211)
	at io.aeron.driver.media.DataTransportPoller.pollTransports(DataTransportPoller.java:93)
	at io.aeron.driver.Receiver.doWork(Receiver.java:104)
	at org.agrona.concurrent.CompositeAgent.doWork(CompositeAgent.java:120)
	at org.agrona.concurrent.AgentRunner.doWork(AgentRunner.java:304)
	at org.agrona.concurrent.AgentRunner.workLoop(AgentRunner.java:296)
	at org.agrona.concurrent.AgentRunner.run(AgentRunner.java:162)
	at java.base/java.lang.Thread.run(Thread.java:829)
Caused by: java.lang.ClassNotFoundException: io.aeron.logbuffer.FrameDescriptor
	at java.base/jdk.internal.loader.BuiltinClassLoader.loadClass(BuiltinClassLoader.java:581)
	at java.base/jdk.internal.loader.ClassLoaders$AppClassLoader.loadClass(ClassLoaders.java:178)
	at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:522)
	... 9 more
```
then
```
1644300891904 Exception:
java.lang.NoClassDefFoundError: io/aeron/exceptions/AeronException$Category
	at io.aeron.exceptions.DriverTimeoutException.<init>(DriverTimeoutException.java:30)
	at io.aeron.ClientConductor.checkLiveness(ClientConductor.java:1340)
	at io.aeron.ClientConductor.checkTimeouts(ClientConductor.java:1308)
	at io.aeron.ClientConductor.service(ClientConductor.java:1214)
	at io.aeron.ClientConductor.doWork(ClientConductor.java:201)
	at org.agrona.concurrent.AgentRunner.doWork(AgentRunner.java:304)
	at org.agrona.concurrent.AgentRunner.workLoop(AgentRunner.java:296)
	at org.agrona.concurrent.AgentRunner.run(AgentRunner.java:162)
	at java.base/java.lang.Thread.run(Thread.java:829)
Caused by: java.lang.ClassNotFoundException: io.aeron.exceptions.AeronException$Category
	at java.base/jdk.internal.loader.BuiltinClassLoader.loadClass(BuiltinClassLoader.java:581)
	at java.base/jdk.internal.loader.ClassLoaders$AppClassLoader.loadClass(ClassLoaders.java:178)
	at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:522)
	... 9 more
Exception in thread "aeron-client-conductor" java.lang.NoClassDefFoundError: io/aeron/exceptions/AeronException$Category
	at io.aeron.exceptions.DriverTimeoutException.<init>(DriverTimeoutException.java:30)
	at io.aeron.ClientConductor.checkLiveness(ClientConductor.java:1340)
	at io.aeron.ClientConductor.checkTimeouts(ClientConductor.java:1308)
	at io.aeron.ClientConductor.service(ClientConductor.java:1214)
	at io.aeron.ClientConductor.doWork(ClientConductor.java:201)
	at org.agrona.concurrent.AgentRunner.doWork(AgentRunner.java:304)
	at org.agrona.concurrent.AgentRunner.workLoop(AgentRunner.java:296)
	at org.agrona.concurrent.AgentRunner.run(AgentRunner.java:162)
	at java.base/java.lang.Thread.run(Thread.java:829)
Caused by: java.lang.ClassNotFoundException: io.aeron.exceptions.AeronException$Category
	at java.base/jdk.internal.loader.BuiltinClassLoader.loadClass(BuiltinClassLoader.java:581)
	at java.base/jdk.internal.loader.ClassLoaders$AppClassLoader.loadClass(ClassLoaders.java:178)
	at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:522)
	... 9 more
```