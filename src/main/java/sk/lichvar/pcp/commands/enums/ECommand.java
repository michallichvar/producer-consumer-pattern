package sk.lichvar.pcp.commands.enums;

import java.util.Arrays;
import java.util.Optional;

public enum ECommand {

	ADD("Add"),
	PRINT_ALL("PrintAll"),
	DELETE_ALL("DeleteAll");

	private final String command;

	ECommand(String command) {
		this.command = command;
	}

	public String getCommand() {
		return command;
	}

	public static Optional<ECommand> findBy(String commandLine) {
		return Arrays.stream(ECommand.values())
				.filter(command -> commandLine.startsWith(command.getCommand()))
				.findFirst();
	}
}
