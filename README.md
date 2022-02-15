Continuous Integration is a simple Continuious Integration (CI) server that allows for automatic
compilation, testing and notification based upon a specified github action.

<!-- ADD MORE IN DEPTH PARAGRAPH DESCRIBING PROJECT -->
Continuous Integration is a CI server based upon the [smallest-java-ci](https://github.com/KTH-DD2480/smallest-java-ci) project, that will after recieveing a JSON package from a github webhook trigger will use jgit to access git functionality. Something that is used to clone the repository for the build that triggered the webhook. After which the server then compiles and runs the tests that the build comes with using maven. A notification will then be sent to the user that is linked action that triggered the webhook that includes information of the result of the compilation and testing of the build. 

This CI server requires that the projects it is used with is compatible with maven (java projects with a maven pom.xml file) and is meant to be triggered through the github webhooks service, with the content type being application/json.

# Running the project
<!-- 
DESCRIBE THE STANDARD WAY OF COMPILING AND TESTING
mvn test for example 
-->
Both compiling and testing this project is done using maven in the base directory
```mvn compile```
```mvn test```

Once compiled, to run the project the following maven command should be executed
```mvn exec:java -Dexec.mainClass="group22.ci.ContinuousIntegrationServer"```

A server is required for the project to work. Something that can be set up using any machine and ngrok or a similar application after the maven execute command. The instructions for which can be found in the [smallest-java-ci](https://github.com/KTH-DD2480/smallest-java-ci) project by following the steps after the compilation stage (With the dependencies and compilation of the project alreayd being handled through maven). The port used for the server set up with ngrok should be the same as the one set in [row 230](https://github.com/Group22DD2480/Continuous-Integration/blob/ef5448618eb132c656ad4b6bccd4c71c117e0ec2/src/main/java/group22/ci/ContinuousIntegrationServer.java#L230) in the ContinuousIntegrationServer.java file. Which is set as port 8022 as a base. If another port is to be used for the server the linked row should be updated to represent the new port used.

The instructions for setting up the github webhook can similarly be found in the [smallest-java-ci](https://github.com/KTH-DD2480/smallest-java-ci) project. With the only difference being that when creating the webhook the content type should be set to application/json and the event that triggers the webhook should remain as push only for this project to work as intended. With other setting not being tested in the current version of the project.

Once that is done, any push to the project the webhook is set up on should be handled by the server and a notification to the affected user should be sent out with th results of compiling and testing the affected build. Something that holds for as long as the server is run. 

# Compilation and Testing
<!--
DESCRIBE HOW COMPILATION HAS BEEN IMPLEMENTED AND UNIT TESTED

DESCRIBE HOW TEST EXECUTION HAS BEEN IMPLEMENTED AND UNIT TESTED
-->
Both compilation and testing of the build that has triggered the webhook is done by running the two maven commands through the java RunTime enviroment. Which allows for command line commands to be run from a java application. After which what would have been printed in the console instead gets stored by the application. With the desired information from such being returned by the funktions as an MavenResult instance.

Unit testing of this application was done through creating demo projects that are a simple hello_world project with a single test case. With the different demo projects including one with a compilation error, one with a test that fails and one that both compiles properly and the testing is cleared. With the path for each demo project being given to the appropriate maven function after which the results of whcih is then asserted as either true or false depending on the results of the maven command.

# Notification
<!--
DESCRIBE HOW NOTIFICATION HAS BEEN IMPLEMENTED AND UNIT TESTED
-->
The notification of the results is handled using a javax mail server which will once prompted send an email to a specified user (the user connected to the affected build) with a message describing the results of compiling and testing the affected build. A specific gmail address was created to be set as the sender of the notification email to allow for easier access and authentication when multiple people should be allowed access to the sender email.

<!--# Build-list
GIVE URL FOR BUILD LIST
-->

# Statement of contribution
- Johan:
- Vilma:
- Zehao:
- Sebaztian: I programmed the runCommand, mavenCompile and mavenTest functions including the documentation of those functions. I also created the base structure for the README documentation including parts of the introduction as well as the documentation on how to set up and run the project including how the different sections was implemented.
- David:

Skeleton for "The smallest Java Continuous Integration server for Github"
- Sophie H Ye
- Martin Monperrus
- Ason Strandberg

Programmers and testers:
- Johan Ekberg
- Vilma Jalava
- Zehao Jiang
- Sebaztian Öjebrant
- David Östling
