package sk.lichvar.pcp.commands.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * An enumeration of supported commands.
 *
 * Each command has a unique string representation that can be used to identify it.
 */
public enum ECommand {

	/**
	 * Adds the user to the database.
	 */
	ADD("Add"),
	/**
	 * Prints all users from the database.
	 */
	PRINT_ALL("PrintAll"),
	/**
	 * Deletes all users from the database.
	 */
	DELETE_ALL("DeleteAll");

	private final String command;

	ECommand(String command) {
		this.command = command;
	}

	/**
	 * Returns the string representation of this command.
	 *
	 * @return the command string
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * Finds an enum value by command line.
	 *
	 * This method returns an Optional containing the matching enum value, or an empty Optional if no match is found.
	 *
	 * @param commandLine the command string to search for
	 * @return an Optional containing the matching enum value, or an empty Optional if no match is found
	 */
	public static Optional<ECommand> findBy(String commandLine) {
		return Arrays.stream(ECommand.values())
				.filter(command -> commandLine.startsWith(command.getCommand()))
				.findFirst();
	}
}
