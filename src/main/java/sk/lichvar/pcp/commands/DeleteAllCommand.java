package sk.lichvar.pcp.commands;

import sk.lichvar.pcp.services.User;
import sk.lichvar.pcp.services.UserService;
import sk.lichvar.pcp.services.UserServiceImpl;

/**
 * Represents command DeleteAll.
 * Removes all {@link User}s from database.
 */
public class DeleteAllCommand implements Command {

	private UserService userService = UserServiceImpl.getInstance();

	@Override
	public void execute() {
		userService.deleteAll();
	}
}
