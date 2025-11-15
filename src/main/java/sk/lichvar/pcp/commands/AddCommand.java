package sk.lichvar.pcp.commands;

import sk.lichvar.pcp.services.User;
import sk.lichvar.pcp.services.UserService;
import sk.lichvar.pcp.services.UserServiceImpl;

/**
 * Represents command Add(id, guid, userName).
 * Adds {@link User} to database.
 */
public class AddCommand implements Command {

	private UserService userService = UserServiceImpl.getInstance();

	private Integer id;
	private String guid;
	private String userName;

	public AddCommand(Integer id, String guid, String userName) {
		this.id = id;
		this.guid = guid;
		this.userName = userName;
	}

	@Override
	public void execute() {
		userService.insert(new User(id, guid, userName));
	}
}
