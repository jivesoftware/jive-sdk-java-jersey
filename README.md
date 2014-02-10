See:  For possible changes to deploy on Oracle/Weblogic
https://jersey.java.net/documentation/latest/deployment.html#deployment.appservers

Need:
- Unit Tests that can be run to insure that services haven't changed between versions

Maven Setup Requirements:
Installing Maven - Link to JC Documentation Already in flight
What to add to settings.xml --- can I put all repos in the parent pom.xml?

Requirements:
Java 7

Mac Commands That Help:
export JAVA_HOME=`/usr/libexec/java_home -v 1.6`
export JAVA_HOME=`/usr/libexec/java_home -v 1.7`


Instructions:
Install mvn clean install
Run/Debug:  mvn jetty:run (maybe pass in host/post configs via command-line)??

Instructions for deploygin to Heroku
Instructions for deploying on Tomcat
Instructions for deploying on JBoss
Instructions for deploying on Oracle BEA (compatible)?

Ideas:

- Heroku Build Pack for easier deploy to Heroku
- Maven Plugin to manipulate src/main/extension ... use a CLI class to read properties and generate JSON ... auto-zip contents with timestamp
- Decoupling Events to @Stateless + @Asynchronous EJB w/OpenEJB - http://tomee.apache.org/
- Implementing More Future Driven Calls
