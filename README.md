Continuous Integration is a simple Continuious Integration (CI) server that allows for automatic
compilation, testing and notification based upon a specified github action.

<!-- ADD MORE IN DEPTH PARAGRAPH DESCRIBING PROJECT -->
Continuous Integration is a CI server based upon the [smallest-java-ci](https://github.com/KTH-DD2480/smallest-java-ci) project, that will after recieveing a JSON package from a github webhook trigger will use jgit to access git functionality. Something that is used to clone the repository for the build that triggered the webhook. After which the server then compiles and runs the tests that the build comes with using maven. A notification will then be sent to the user that is linked action that triggered the webhook that includes information of the result of the compilation and testing of the build. 

This CI server requires that the projects it is used with is compatible with maven (java projects with a maven pom.xml file) and is meant to be triggered through the github webhooks service, with the content type being application/json.

# Essence Standard
The team is most definitely “seeded” (as per the first stage) and the know-how needed to grow the team is indeed in place. We know what has to be done in order to get everyone and everything together. Each and every one of the group members are equally committed to the tasks at hand and we always seem to engage in each task with equal mindsets (as per the “formed” stage). As of now, we believe that we are in the “collaborating” stage as there are still some hick-ups at times in our work which disables us from entering the “performing stage”. As of this lab, we have worked together as a unit for sure but sometimes we get desynchronized which has resulted in some effectiveness deficiencies. To put it shortly, in order to reach the next stage we should try to work more on syncing up our work. Each time someone in the group pushes something all team members should always (unless it’s a small bug fix perhaps) be informed of what has been done and what it enables the group to do from there.

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

Note that the server needs to be run on windows cause we’re using cmd.exe to run maven commands.

# Compilation and Testing
<!--
DESCRIBE HOW COMPILATION HAS BEEN IMPLEMENTED AND UNIT TESTED

DESCRIBE HOW TEST EXECUTION HAS BEEN IMPLEMENTED AND UNIT TESTED
-->
Both compilation and testing of the build that has triggered the webhook is done by running the two maven commands through the java RunTime enviroment. Which allows for command line commands to be run from a java application. After which what would have been printed in the console instead gets stored by the application. With the desired information from such being returned by the funktions as an MavenResult instance.

Unit testing of this application was done through creating demo projects that are a simple hello_world project with a single test case. With the different demo projects including one with a compilation error, one with a test that fails and one that both compiles properly and the testing is cleared. With the path for each demo project being given to the appropriate maven function after which the results of whcih is then asserted as either true or false depending on the results of the maven command.

# The Store Function
In order to store payloads we implemented a store function where the CI server keeps the history of the past builds. This history persists even if the server is rebooted. Each build is given a unique URL, that is accessible to get the build information (commit identifier, build date, build logs). One URL exists to list all builds (see below).

URL: http://localhost:8080/

# Notification
<!--
DESCRIBE HOW NOTIFICATION HAS BEEN IMPLEMENTED AND UNIT TESTED
-->
The notification of the results is handled using a javax mail server which will once prompted send an email to a specified user (the user connected to the affected build) with a message describing the results of compiling and testing the affected build. A specific gmail address was created to be set as the sender of the notification email to allow for easier access and authentication when multiple people should be allowed access to the sender email, by disabling the 2-step verification and allowing less-secure app usage in the mail setting. We set up a test that successfully send the email and another one failed because of invalid email address. 

<!--# Build-list
GIVE URL FOR BUILD LIST
-->

# Statement of contribution

- Johan: I set up repo to work properly with webhooks. I created the maven project together with David and got the server to run. Me and David also set up the retrievment of the payload and saving the valid information. I also helped Vilma with setting up the cloning of the repo. Me and David set up the three build tests to compile and run its tests. I also made some bug fixes to make the server work properly.
- Sebaztian: I programmed the runCommand, mavenCompile and mavenTest functions including the documentation of those functions. I also created the base structure for the README documentation including parts of the introduction as well as the documentation on how to set up and run the project including how the different sections was implemented.
- David: Assisted Johan with getting the skeleton to work properly as well as retrieving the payload and parsing it down so it could be used for later testing (this way we could determine who commited the latest push and e.g. who to send an email to). Also worked on the compile- and unit tests.
- Zehao: I am in charge of the email notification part. When giving a string message, it could send the notification to the person. Worked on the mail port and set up server email address.
- Vilma: Co-authored the implementation of Core CI feature - cloning of the repo so that the remote branch where a change has been made is cloned when our server is triggered by webhook. Also worked on the documentation alongside Sebaztian. 

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
