package sk.lichvar.pcp.queue;

import sk.lichvar.pcp.commands.Command;
import sk.lichvar.pcp.producer.Producer;

import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * {@link BlockingQueue} queue where {@link Producer} put {@link Command}s
 * and {@link sk.lichvar.pcp.consumer.Consumer} polls them.
 */
public class CommandQueue extends ArrayBlockingQueue<Command> {

	/**
	 * State of producer thread (true if producing)
	 */
	public volatile boolean producerRunning = true;

	public CommandQueue(int capacity) {
		super(capacity);
	}

	public CommandQueue(int capacity, boolean fair) {
		super(capacity, fair);
	}

	public CommandQueue(int capacity, boolean fair, Collection<? extends Command> c) {
		super(capacity, fair, c);
	}

	/**
	 * Change state of producer after production ends
	 */
	public void producerFinished() {
		producerRunning = false;
	}
}
