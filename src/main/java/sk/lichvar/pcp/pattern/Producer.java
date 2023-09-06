package sk.lichvar.pcp.pattern;

import lombok.AllArgsConstructor;
import sk.lichvar.pcp.commands.Command;
import sk.lichvar.pcp.queue.CommandQueue;

import java.util.Queue;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
public class Producer implements Runnable {

	private final CommandQueue queue;

	private final Queue<Command> inputCommands;

	@Override
	public void run() {
		try {
			// while producer is running (could be stopped on consumer exception)
			// and there are still unprocessed input commands
			while (queue.producerRunning && !inputCommands.isEmpty()) {
				// get next command
				Command command = inputCommands.poll();
				while (queue.producerRunning
						&& !queue.offer(command, 100, TimeUnit.MILLISECONDS)) {
					// and offer command to queue until accepted
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// setting producer as finished allows consumer thread to end once it consumes all commands
		queue.producerFinished();
	}
}
