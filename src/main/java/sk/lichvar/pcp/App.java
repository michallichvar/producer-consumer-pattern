package sk.lichvar.pcp;

import sk.lichvar.pcp.commands.AddCommand;
import sk.lichvar.pcp.commands.Command;
import sk.lichvar.pcp.commands.DeleteAllCommand;
import sk.lichvar.pcp.commands.PrintAllCommand;
import sk.lichvar.pcp.pattern.Consumer;
import sk.lichvar.pcp.pattern.Producer;
import sk.lichvar.pcp.queue.CommandQueue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Solves this assigned task:
 * <p>
 * Create program in Java language that will process commands from FIFO queue using Producer –
 * Consumer pattern.<br/>
 * Supported commands are the following:
 * <ul>
 * <li><b>Add</b> - adds a user into a database</li>
 * <li><b>PrintAll</b> – prints all users into standard output</li>
 * <li><b>DeleteAll</b> – deletes all users from database</li>
 * </ul>
 * </p>
 * <p>
 * User is defined as database table SUSERS with columns (USER_ID, USER_GUID, USER_NAME)
 * Demonstrate program on the following sequence (using main method or test):
 * </p>
 * <pre>
 * Add (1, &quot;a1&quot;, &quot;Robert&quot;)
 * Add (2, &quot;a2&quot;, &quot;Martin&quot;)
 * PrintAll
 * DeleteAll
 * PrintAll
 * </pre>
 * <p>
 * Show your ability to unit test code on at least one class.<br/>
 * Goal of this exercise is to show Java language and JDK know-how, OOP principles, clean code
 * understanding, concurrent programming knowledge, unit testing experience.<br/>
 * Please do not use Spring framework in this exercise. Embedded database is sufficient.
 * </p>
 */
public class App {

	private static CommandQueue queue;
	private static Thread consumerThread;
	private static Thread producerThread;

	public static void main(String[] args) {
		queue = new CommandQueue(50);

		consumerThread = new Thread(new Consumer(queue));
		consumerThread.start();

		// non tread safe queue for input commands (as they are all processed in one thread)
		Queue<Command> inputCommands = Stream.of(
				new AddCommand(1, "a1", "Robert"),
				new AddCommand(2, "a2", "Martin"),
				new PrintAllCommand(),
				new DeleteAllCommand(),
				new PrintAllCommand()
		).collect(Collectors.toCollection(LinkedList::new));

		producerThread = new Thread(new Producer(queue, inputCommands));
		producerThread.start();
	}
}
