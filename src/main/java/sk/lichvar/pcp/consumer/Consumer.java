package sk.lichvar.pcp.consumer;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.lichvar.pcp.commands.Command;
import sk.lichvar.pcp.queue.CommandQueue;

import java.util.concurrent.TimeUnit;

/**
 * Consume commands from queue and execute them.
 * Consuming is done by method {@link java.util.concurrent.BlockingQueue#poll} with 100ms timeout.
 *
 */
@AllArgsConstructor
public class Consumer implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

	private final CommandQueue queue;

	@Override
	public void run() {
		try {
			while(queue.producerRunning || !queue.isEmpty()) {
				// consume and execute command
				Command command = queue.poll(100, TimeUnit.MILLISECONDS);
				if (command != null) {
					try {
						command.execute();
					} catch (Throwable e) {
						// when error during processing, log and continue to next command
						LOGGER.error("Exception while processing command: " + command.toString(), e);
					}
				}
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			LOGGER.warn("Consumer was interrupted when polling command.", e);
		}
		// tell producer to finish itself
		queue.producerFinished();
	}
}
