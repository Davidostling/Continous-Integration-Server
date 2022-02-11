# Continuous Integration (CI)

Implementation of a Continuous Integration server built from the skeleton code [smallest-java-ci](https://github.com/KTH-DD2480/smallest-java-ci). The CI contains the core features of continuous integration; each time the GitHub webhook registers a push event to the server, the push is compiled and tested by the CI-server, alongside the resuklts of the tests and the status of the commit is set and a notification is sent out by email. The CI server also stores the history of all previous builds alongside all build details. 


## Installation


### Setting up maven

todo

### Setting up server

todo

### Compiling, Testing, and Running

todo

## Statement of contributions

Johan: I set up repo to work properly with webhooks. I created the maven project together with David and got the server to run. Me and David also set up the retrievment of the payload and saving the valid information. I also helped Vilma with setting up the cloning of the repo. Me and David set up the three build tests to compile and run its tests. I also made some bug fixes to make the server work properly.

Sebaztian: 

David: Assisted Johan with getting the skeleton to work properly as well as retrieving the payload and parsing it down so it could be used for later testing (this way we could determine who commited the latest push and e.g. who to send an email to). Also worked on the compile- and unit tests.

Zehao: I am in charge of the email notification part. When giving a string message, it could send the notification to the person. Worked on the mail port and set up server email address.

Vilma: 
