package sk.lichvar.pcp.services;

import java.util.List;

public interface UserService {

	void insert(User user);

	List<User> listAll();

	int deleteAll();
}
