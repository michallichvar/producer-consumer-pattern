package sk.lichvar.pcp.commands;

/**
 * Superclass for command, facades Runnable.run as execute().
 */
abstract public class Command implements Runnable {

	@Override
	public void run() {
		execute();
	}

	abstract public void execute();
}
