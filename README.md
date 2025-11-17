# Producer-consumer pattern solution

<p>
Demonstrates processing of commands from input file using Producer – Consumer pattern.<br/>
</p>
<p>
Supported commands are the following:
<ul>
<li><b>Add</b> - adds a user into a database</li>
<li><b>PrintAll</b> – prints all users into standard output</li>
<li><b>DeleteAll</b> – deletes all users from database</li>
</ul>
</p>

<p>
Program loads input resource file (input.cmd) containing following commands:
</p>
<pre>
Add (1, &quot;a1&quot;, &quot;Robert&quot;)
Add (2, &quot;a2&quot;, &quot;Martin&quot;)
PrintAll
DeleteAll
PrintAll
</pre>
<p>
    Program starts Producer and Consumer as separate threads.
    Producer parses input resource file into Commands, that are send (offered) to CommandQueue.
    Consumer polls Commands from  CommandQueue and executes them.
</p>
<p>
Spring framework is not used. </br>
Embedded database has a database table SUSERS with columns (USER_ID, USER_GUID, USER_NAME), representing Users.
</p>
## Requirements

For building and running the project you need:

- [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven 3](https://maven.apache.org)


## Compile
```
mvn compile
```

## Test
```
mvn test
```