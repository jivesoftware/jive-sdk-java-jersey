See:  For possible changes to deploy on Oracle/Weblogic
https://jersey.java.net/documentation/latest/deployment.html#deployment.appservers

Need:
- Unit Tests that can be run to insure that services haven't changed between versions


Ideas:

- Heroku Build Pack for easier deploy to Heroku
- Maven Plugin to manipulate src/main/extension ... use a CLI class to read properties and generate JSON ... auto-zip contents with timestamp
- Decoupling Events to @Stateless + @Asynchronous EJB w/OpenEJB - http://tomee.apache.org/
- Implementing More Future Driven Calls
