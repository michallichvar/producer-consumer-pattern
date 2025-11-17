package sk.lichvar.pcp;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.lichvar.pcp.consumer.Consumer;
import sk.lichvar.pcp.producer.Producer;
import sk.lichvar.pcp.queue.CommandQueue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Several test scenarios
 * <ul>
 *     <li>{@link #testInputFileProcessed} positive scenario, tests system output for printed users.</li>
 *     <li>{@link #testResourceFileNotFound} negative scenario, tests system error for logger message: "Error in producer."</li>
 *     <li>{@link #testResourceFileEmpty} positive scenario, tests system output does not contain printed users.</li>
 *     <li>{@link #testAddCommandMalformed} negative scenario, tests system error for logger message: "Error in producer." and "NumberFormatException"</li>
 *     <li>{@link #testWrongCommand} negative scenario, tests system error for logger message: "Error in producer." and wrong command named "UpdateAll"</li>
 * </ul>
 */
public class AppTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(AppTest.class);

	private final ByteArrayOutputStream spyOut = new ByteArrayOutputStream();
	private final ByteArrayOutputStream spyErr = new ByteArrayOutputStream();

	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;

	private static CommandQueue queue;
	private static Thread consumerThread;
	private static Thread producerThread;

	@BeforeEach
	public void setUpStreams() {
		System.setOut(new PrintStream(spyOut));
		System.setErr(new PrintStream(spyErr));
	}

	@AfterEach
	public void restoreStreams() {
		System.setOut(originalOut);
		System.setErr(originalErr);
	}

	@Test
	public void testInputFileProcessed() {
		queue = new CommandQueue(50);

		producerThread = new Thread(new Producer("/input.cmd", queue));
		producerThread.start();

		consumerThread = new Thread(new Consumer(queue));
		consumerThread.start();

		joinProducerThread();
		joinConsumerThread();

		Assertions.assertThat(spyOut.toString())
				.contains("User[userId=1,userGuid=a1,userName=Robert)]")
				.contains("User[userId=2,userGuid=a2,userName=Martin)]");
	}

	@Test
	public void testResourceFileNotFound() {
		queue = new CommandQueue(50);

		producerThread = new Thread(new Producer("/nonexistent.cmd", queue));
		producerThread.start();

		joinProducerThread();

		Assertions.assertThat(spyErr.toString())
						.contains("Error in producer.")
						.contains("nonexistent.cmd");
	}

	@Test
	public void testResourceFileEmpty() {
		queue = new CommandQueue(50);

		producerThread = new Thread(new Producer("/tests/empty.cmd", queue));
		producerThread.start();

		consumerThread = new Thread(new Consumer(queue));
		consumerThread.start();

		joinProducerThread();
		joinConsumerThread();

		Assertions.assertThat(spyOut.toString())
				.doesNotContain("User[");
	}

	@Test
	public void testAddCommandMalformed() {
		queue = new CommandQueue(50);

		producerThread = new Thread(new Producer("/tests/add_command_malformed.cmd", queue));
		producerThread.start();

		joinProducerThread();

		Assertions.assertThat(spyErr.toString())
				.contains("Error in producer.")
				.contains("java.lang.NumberFormatException: For input string: \"\"1\"\"");
	}

	@Test
	public void testWrongCommand() {
		queue = new CommandQueue(50);

		producerThread = new Thread(new Producer("/tests/wrong_command.cmd", queue));
		producerThread.start();

		joinProducerThread();

		Assertions.assertThat(spyErr.toString())
				.contains("Error in producer.")
				.contains("java.text.ParseException: Unknown command: UpdateAll");
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
