![Jive SDK - Java - Jersey - Architecture](https://github.com/jivesoftware/jive-sdk-java-jersey/blob/master/sdk-diagram.png?raw=true)

This document is a work in progress, so please ignore the clutter


#### Pre-Requisites

1. Install [Git](http://git-scm.com/book/en/Getting-Started-Installing-Git) (needed for cloning and contributing)
2. Install [Maven](http://maven.apache.org/download.cgi) - (This [document](https://community.jivesoftware.com/docs/DOC-3528), while outdated may help)
3. Install [Java 7](http://java.com/en/download/index.jsp) (see [help center](http://java.com/en/download/help/index_installing.xml) for assistance)

#### Getting Started
This SDK is more of a boiler plate application that gets your up and running and FAST.  To get started, simply 

1. **git clone https://github.com/jivesoftware/jive-sdk-java-jersey.git**
2. **cd jive-sdk-java-jersey; mvn clean install**
  * note: see: [pom.xml](https://github.com/jivesoftware/jive-sdk-java-jersey/blob/master/jive-addon/pom.xml#L19 ) to configure host / port information before running this step
  * Make sure that your Java Runtime is Java 7, and not an earlier version. (see Mac Commands below for possible help)
5. **cd jive-addon; mvn jetty:run**
  * Make sure that your Java Runtime is Java 7, and not an earlier version. (see Mac Commands below for possible help)

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
- Unit Tests that can be run to insure that services haven't changed between versions

#### Ideas:
- Better definition.json generation support
- Decoupling Events to @Stateless + @Asynchronous EJB w/OpenEJB - http://tomee.apache.org/
- Implementing More Future Driven Calls
