![Jive SDK - Java - Jersey - Architecture](https://github.com/jivesoftware/jive-sdk-java-jersey/blob/master/sdk-diagram.png?raw=true)

This document is a work in progress, so please ignore the clutter


#### Pre-Requisites

1. Install [Git](http://git-scm.com/book/en/Getting-Started-Installing-Git) (needed for cloning and contributing)
2. Install [Maven 3.0.5](http://maven.apache.org/download.cgi#Maven_3.0.5) - (This [document](https://community.jivesoftware.com/docs/DOC-3528), while outdated may help.)
  * Issue with project build in Maven 3.1+, so recommended to use this version exactly for now.
3. Install [Java 7 SDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html) (see [help center](http://java.com/en/download/help/index_installing.xml) for assistance)

>**Note:** Some users have found issues with Maven 3.1.0, as we look into those reports, please use 3.0.5)

#### Getting Started
This SDK is more of a boiler plate application that gets your up and running and FAST.

>Before you get started, make sure your default Java Runtime is Java 7. 
You can execute **java -version** to confirm.   (see Mac Commands below for possible help)

To get started, simply perform the following steps:

1. **git clone https://github.com/jivesoftware/jive-sdk-java-jersey.git**
2. **cd jive-sdk-java-jersey; mvn install**
3. **cd jive-addon; mvn -DclientUrl=http://your.resolvable.host.name -Dport=8090 clean jetty:run**

Congrats, your Jive Add-On Service is should now be running!  Now we just need to link a Jive Instance to it!  Here's how you do that!

* **Upload ZIP file** generated for you in **jive-sdk-java-jersey/jive-addon/extension-jive-addon-example.zip** in the Add-On Services > Upload Packages section of your Jive Instance (must have admin privileges)
* **Note** If you do not want your meta.json and definition.json files re-generated each time you start the service, simply remove **clean** from the goals declaration

At this point, you client service is running with a Jive Instance connected to it.  Be sure to check the [Jive Developer Community](https://community.jivesoftware.com/community/developer) for more updates about this project.  We look forward to your feedback and contributions.

---

#### Java Environment Tip & Tricks

##### Mac Tricks
To switch your Terminal Java Runtime back to Java 7, use:
>export JAVA_HOME=\`/usr/libexec/java_home -v 1.7\`

To switch your Terminal Java Runtime back to Java 6, use:
>export JAVA_HOME=\`/usr/libexec/java_home -v 1.6\`

##### Windows Tricks
TODO:


##### Linux (Ubuntu/CentOS/RHEL/SUSE/etc..) Tricks
TODO: 

---

#### Deployment Instructions

**Instructions for deploying on Tomcat**
* TODO - Instructions

**Instructions for deploying on JBoss**
1. **git clone https://github.com/jivesoftware/jive-sdk-java-jersey.git**
2. **cd jive-sdk-java-jersey; mvn install**
3. **cd jive-addon; mvn clean package**
4. **cp target/jive-sdk-jersey-example.war /your/jboss/deployments/directory**

**Instructions for deploying on Weblogic**
* TODO - Confirm Compatibility
* TODO - Instructions

**Instructions for deploying on Websphere**
* TODO - Confirm Compatibility
* TODO - Instructions

**Instructions for deploying to Heroku**
* TODO:  See: [Procfile](/jivesoftware/jive-sdk-java-jersey/blob/master/jive-addon/Procfile)
* TODO - Instructions

---

#### Need:
- Support for Activity Stream Tiles
- Support for Jive API Client (Generic API Tool)
- Support for Jive Analytics Client
- Support ESF Storage Events and Proxy Discussions to Add-On
- Unit Tests that can be run to insure that services haven't changed between versions

#### Ideas:
- Better definition.json generation support
- Decoupling Events to @Stateless + @Asynchronous EJB w/OpenEJB - http://tomee.apache.org/
- Implementing More Future Driven Calls
- Continue to look for ways to improve/speed-up development:
  http://www.benoitschweblin.com/2013/03/run-jetty-in-maven-life-cycle.html
