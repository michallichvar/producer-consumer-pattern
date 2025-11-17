package sk.lichvar.pcp.producer;

import sk.lichvar.pcp.commands.AddCommand;
import sk.lichvar.pcp.commands.Command;
import sk.lichvar.pcp.commands.DeleteAllCommand;
import sk.lichvar.pcp.commands.PrintAllCommand;
import sk.lichvar.pcp.commands.enums.ECommand;

import java.text.ParseException;
import java.util.Optional;

/**
 * This class is responsible for parsing commands from a string.
 */
public class CommandParser {

	/**
	 * Parses a command from a string and returns the parsed {@link Command}.
	 *
	 * @param commandLine The command to parse.
	 * @return The parsed {@link Command}.
	 * @throws RuntimeException If the command cannot be parsed.
	 */
	public static Command parseStream(String commandLine) throws RuntimeException {
		try {
			return parse(commandLine);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Parses a command from a string and returns the parsed {@link Command}.
	 *
	 * @param commandLine The command to parse.
	 * @return The parsed {@link Command}.
	 * @throws ParseException If the command cannot be parsed.
	 */
	public static Command parse(String commandLine) throws ParseException {
		Optional<ECommand> eCommand = ECommand.findBy(commandLine);

		if (eCommand.isEmpty())
			throw new ParseException("Unknown command: " + commandLine, 0);
		else {
			return switch (eCommand.get()) {
				case ADD -> parseAddCommand(commandLine);
				case PRINT_ALL -> new PrintAllCommand();
				case DELETE_ALL -> new DeleteAllCommand();
			};
		}
	}

	/**
	 * Parses an AddCommand from a string and returns the parsed {@link AddCommand}.
	 *
	 * @param commandLine The command to parse.
	 * @return The parsed {@link AddCommand}.
	 * @throws ParseException If the command cannot be parsed.
	 */
	private static Command parseAddCommand(String commandLine) throws ParseException {
		String[] strgs = commandLine.split("\\(");
		if (strgs.length != 2) {
			throw new ParseException("Parsed command does not have attributes: " + commandLine, commandLine.length());
		} else {
			String[] parameters = strgs[1].split(",");
			if (parameters.length != 3) {
				throw new ParseException("Wrong count of attributes in parsed command: " + commandLine, strgs[0].length());
			} else {
				Integer id = Integer.parseInt(parameters[0].trim());
				String guid = parameters[1].trim().replaceAll("\"", "");
				String name = parameters[2].trim().replaceAll("\"", "");
				return new AddCommand(id, guid, name);
			}
		}
	}

	/**
	 * Removes whitespaces from a string.
	 *
	 * @param commandLine The string to remove whitespaces from.
	 * @return The string without whitespaces.
	 */
	private static String removeWhitespaces(String commandLine) {
		return commandLine.replaceAll(" ", "");
	}
}
