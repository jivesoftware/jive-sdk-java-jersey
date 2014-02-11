![Jive SDK - Java - Jersey - Architecture](https://github.com/jivesoftware/jive-sdk-java-jersey/blob/master/sdk-diagram.png?raw=true)

This document is a work in progress, so please ignore the clutter


#### Pre-Requisites

1. Install [Git](http://git-scm.com/book/en/Getting-Started-Installing-Git) (needed for cloning and contributing)
2. Install [Maven](http://maven.apache.org/download.cgi) - (This [document](https://community.jivesoftware.com/docs/DOC-3528), while outdated may help)
3. Install [Java 7](http://java.com/en/download/index.jsp) (see [help center](http://java.com/en/download/help/index_installing.xml) for assistance)

#### Getting Started
This SDK is more of a boiler plate application that gets your up and running and FAST.

>Before you get started, make sure your default Java Runtime is Java 7. 
You can execute **java -version** to confirm.   (see Mac Commands below for possible help)

To get started, simply perform the following steps:

1. **git clone https://github.com/jivesoftware/jive-sdk-java-jersey.git**
2. **cd jive-sdk-java-jersey; mvn clean install**
3. **cd jive-addon; mvn -DclientUrl=http://your.resolvable.host.name -Dport=8090 jetty:run**

Congrats, your Jive Add-On Service is should now be running!  Now we just need to link a Jive Instance to it!  Here's how you do that!

* **Upload ZIP file** generated for you in **jive-sdk-java-jersey/jive-addon/extension-jive-addon-example.zip** in the Add-On Services > Upload Packages section of your Jive Instance (must have admin privileges)

At this point, you client service is running with a Jive Instance connected to it.  Be sure to check the [Jive Developer Community](https://community.jivesoftware.com/community/developer) for more updates about this project.  We look forward to your feedback and contributions.

---

#### Mac Help

To switch your terminal Java Runtime back to Java 7, use:
>export JAVA_HOME=\`/usr/libexec/java_home -v 1.7\`

To switch your terminal Java Runtime back to Java 6, use:
>export JAVA_HOME=\`/usr/libexec/java_home -v 1.6\`

Instructions for deploying to Heroku
Instructions for deploying on Tomcat
Instructions for deploying on JBoss
Instructions for deploying on Oracle BEA (compatible)?

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
