# Producer-consumer pattern solution

Solves this assigned task:
<p>
Create program in Java language that will process commands from FIFO queue using Producer –
Consumer pattern.<br/>
Supported commands are the following:
<ul>
<li><b>Add</b> - adds a user into a database</li>
<li><b>PrintAll</b> – prints all users into standard output</li>
<li><b>DeleteAll</b> – deletes all users from database</li>
</ul>

<p>
User is defined as database table SUSERS with columns (USER_ID, USER_GUID, USER_NAME)
Demonstrate program on the following sequence (using main method or test):
</p>
<pre>
Add (1, &quot;a1&quot;, &quot;Robert&quot;)
Add (2, &quot;a2&quot;, &quot;Martin&quot;)
PrintAll
DeleteAll
PrintAll
</pre>
<p>
Show your ability to unit test code on at least one class.<br/>
Goal of this exercise is to show Java language and JDK know-how, OOP principles, clean code
understanding, concurrent programming knowledge, unit testing experience.<br/>
Please do not use Spring framework in this exercise. Embedded database is sufficient.

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