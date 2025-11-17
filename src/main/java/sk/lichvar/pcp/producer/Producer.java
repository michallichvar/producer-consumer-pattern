package sk.lichvar.pcp.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.lichvar.pcp.queue.CommandQueue;

import java.util.concurrent.TimeUnit;

/**
 * A Runnable implementation that produces and offers commands to a shared queue.
 *
 * This class reads input from a resource, parses each line into a command,
 * and attempts to offer it to the queue. If the queue is full or not accepting
 * new commands, this process will block until the queue is empty or accepts
 * another command.
 */
public class Producer implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

	/**
	 * The name of the resource containing input commands.
	 */
	private final String inputResourceName;

	/**
	 * The shared CommandQueue instance that this producer will offer commands to.
	 */
	private final CommandQueue queue;

	/**
	 * Constructs a new Producer instance with the given input resource name and command queue.
	 *
	 * @param inputResourceName the name of the resource containing input commands
	 * @param queue              the shared CommandQueue instance
	 */
	public Producer(String inputResourceName, CommandQueue queue) {
		this.inputResourceName = inputResourceName;
		this.queue = queue;
	}

	@Override
	public void run() {
		try {
			ResourceLoader.getLinesFromResource(inputResourceName).stream()
					.takeWhile(s -> queue.producerRunning)
					.map(commandLine -> CommandParser.parseStream(commandLine))
					.forEach(command -> {
						try {
							while (queue.producerRunning
									&& !queue.offer(command, 100, TimeUnit.MILLISECONDS)) {
								// offer command to queue until accepted
							}
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
							LOGGER.warn("Producer was interrupted when offering command.", e);
						}
					});
		} catch (RuntimeException e) {
			LOGGER.error("Error in producer.", e);
		}
		// setting producer as finished allows consumer thread to end once it consumes all commands
		queue.producerFinished();
	}
}
