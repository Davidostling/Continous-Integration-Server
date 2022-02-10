Continuous Integration is a simple Continuious Integration (CI) server that allows for automatic
compilation, testing and notification based upon a specified github action.

<!-- ADD MORE IN DEPTH PARAGRAPH DESCRIBING PROJECT -->

# Running the project
<!-- 
DESCRIBE THE STANDARD WAY OF COMPILING AND TESTING
mvn test for example 
-->

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

<!--# Build-list
GIVE URL FOR BUILD LIST
-->

# Statement of contribution
Johan:
Vilma:
Zehao:
Sebaztian: I programmed the runCommand, mavenCompile and mavenTest functions including the documentation of those functions. I also created the base structure for the README documentation.
David:

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
