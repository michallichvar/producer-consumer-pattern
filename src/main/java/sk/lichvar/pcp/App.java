package sk.lichvar.pcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.lichvar.pcp.commands.Command;
import sk.lichvar.pcp.consumer.Consumer;
import sk.lichvar.pcp.producer.Producer;
import sk.lichvar.pcp.queue.CommandQueue;

/**
 * <p>
 * Demonstrates processing of commands from input file using Producer – Consumer pattern.<br/>
 * Supported commands are the following:
 * <ul>
 * <li><b>Add</b> - adds a user into a database</li>
 * <li><b>PrintAll</b> – prints all users into standard output</li>
 * <li><b>DeleteAll</b> – deletes all users from database</li>
 * </ul>
 * </p>
 * <p>
 * Program loads input resource file (input.cmd) containing following commands:
 * </p>
 * <pre>
 * Add (1, &quot;a1&quot;, &quot;Robert&quot;)
 * Add (2, &quot;a2&quot;, &quot;Martin&quot;)
 * PrintAll
 * DeleteAll
 * PrintAll
 * </pre>
 * <p>
 *     Program starts {@link Producer} and {@link Consumer} as separate threads.
 *     Producer parses input resource file into {@link Command}s, that are send (offered) to {@link CommandQueue}.
 *     Consumer polls {@link Command}s from {@link  CommandQueue} and executes them.
 * </p>
 * <p>
 * Spring framework is not used. </br>
 * Embedded database has a database table SUSERS with columns (USER_ID, USER_GUID, USER_NAME), representing Users.
 * </p>
 */
public class App {

	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

	private static CommandQueue queue;
	private static Thread consumerThread;
	private static Thread producerThread;

	public static void main(String[] args) {
		queue = new CommandQueue(50);

		producerThread = new Thread(new Producer("/input.cmd", queue));
		producerThread.start();

		consumerThread = new Thread(new Consumer(queue));
		consumerThread.start();

		joinProducerThread();
		joinConsumerThread();
	}

	private static void joinProducerThread() {
		try {
			producerThread.join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			LOGGER.warn("Main thread interrupted while waiting for producer thread to finish.");
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage(), e);
			}
		}
	}

	private static void joinConsumerThread() {
		try {
			consumerThread.join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			LOGGER.warn("Main thread interrupted while waiting for consumer thread to finish.");
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e.getMessage(), e);
			}
		}
	}
}
