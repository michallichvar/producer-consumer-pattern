package sk.lichvar.pcp.commands;

import sk.lichvar.pcp.services.User;
import sk.lichvar.pcp.services.UserService;
import sk.lichvar.pcp.services.UserServiceImpl;

/**
 * Represents command PrintAll.
 * Prints all {@link User}s from database to standard output.
 */
public class PrintAllCommand implements Command {

	private UserService userService = UserServiceImpl.getInstance();

	@Override
	public void execute() {
		userService.listAll().forEach(user -> System.out.println(user));
	}
}
