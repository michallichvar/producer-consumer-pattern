package sk.lichvar.pcp.commands;

import org.hibernate.Session;
import sk.lichvar.pcp.hibernate.HibernateUtils;
import sk.lichvar.pcp.model.User;

/**
 * Represents command PrintAll.
 * Prints all {@link User}s from database to standard output.
 */
public class PrintAllCommand extends Command {

	@Override
	public void execute() {
		try (Session session = HibernateUtils.getSessionFactory().openSession()) {
			session.createQuery("SELECT u FROM User u", User.class).getResultStream()
					.forEach(user -> System.out.println(user));
		}
	}
}
