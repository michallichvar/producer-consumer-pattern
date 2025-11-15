package sk.lichvar.pcp.commands;

/**
 * Superclass for command, facades Runnable.run as execute().
 */
public interface Command {

	void execute();
}
