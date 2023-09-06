package sk.lichvar.pcp.pattern;

import lombok.AllArgsConstructor;
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
						System.err.println("Exception while processing command: " + e.getMessage());
						e.printStackTrace();
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// tell producer to finish itself
		queue.producerFinished();
	}
}
