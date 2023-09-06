package sk.lichvar.pcp;

import org.junit.jupiter.api.Test;
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

import static org.mockito.Mockito.*;

/**
 * Several tests, but all of them can be evaluated just by personally checking the console output
 * <ul>
 *     <li>{@link #testAssignedTaskSolution} solution for assigned task</li>
 *     <li>{@link #testQueueFull} tests scenario when queue is shorter then number of input commands</li>
 *     <li>{@link #testConsumerProcessingException} tests scenario when processing command in consumer throws Exception</li>
 * </ul>
 */
public class AppTest {

	private void test(int capacity, Queue<Command> inputCommands) throws InterruptedException {
		CommandQueue queue = new CommandQueue(capacity);

		Thread consumerThread = new Thread(new Consumer(queue));
		consumerThread.start();

		Thread producerThread = new Thread(new Producer(queue, inputCommands));
		producerThread.start();

		consumerThread.join();
	}

	/**
	 * Runs assigned task solution
	 * @throws InterruptedException
	 */
	@Test
	public void testAssignedTaskSolution() throws InterruptedException {
		// non tread safe queue for input commands (as they are all processed in one thread)
		Queue<Command> inputCommands = Stream.of(
				new AddCommand(1, "a1", "Robert"),
				new AddCommand(2, "a2", "Martin"),
				new PrintAllCommand(),
				new DeleteAllCommand(),
				new PrintAllCommand()
		).collect(Collectors.toCollection(LinkedList::new));

		test(50, inputCommands);
	}

	/**
	 * Tests producer waiting scenario
	 * @throws InterruptedException
	 */
	@Test
	public void testQueueFull() throws InterruptedException {
		// non tread safe queue for input commands (as they are all processed in one thread)
		Queue<Command> inputCommands = Stream.of(
				new AddCommand(1, "a1", "Robert"),
				new AddCommand(2, "a2", "Martin"),
				new AddCommand(3, "a3", "Juraj"),
				new AddCommand(4, "a4", "Peter"),
				new AddCommand(5, "a5", "Vasil"),
				new AddCommand(6, "a6", "George"),
				new AddCommand(7, "a7", "Samara"),
				new AddCommand(8, "a8", "Tamara"),
				new PrintAllCommand(),
				new DeleteAllCommand(),
				new PrintAllCommand()
		).collect(Collectors.toCollection(LinkedList::new));

		test(5, inputCommands);
	}

	/**
	 * Tests consumer processing exception
	 * @throws InterruptedException
	 */
	@Test
	public void testConsumerProcessingException() throws InterruptedException {
		AddCommand addCommand = mock(AddCommand.class);
		doThrow(new RuntimeException("Cannot connect to DB")).when(addCommand).execute();

		// non tread safe queue for input commands (as they are all processed in one thread)
		Queue<Command> inputCommands = Stream.of(
				new AddCommand(1, "a1", "Robert"),
				addCommand,
				new PrintAllCommand(),
				new DeleteAllCommand(),
				new PrintAllCommand()
		).collect(Collectors.toCollection(LinkedList::new));

		test(5, inputCommands);
	}
}
