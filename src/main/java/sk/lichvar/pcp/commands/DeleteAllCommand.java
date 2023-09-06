package sk.lichvar.pcp.commands;

import org.hibernate.Session;
import org.hibernate.Transaction;
import sk.lichvar.pcp.hibernate.HibernateUtils;
import sk.lichvar.pcp.model.User;

/**
 * Represents command DeleteAll.
 * Removes all {@link User}s from database.
 */
public class DeleteAllCommand extends Command {

	@Override
	public void execute() {
		try (Session session = HibernateUtils.getSessionFactory().openSession()) {
			Transaction tx = session.beginTransaction();
			session.createQuery("DELETE FROM User u").executeUpdate();
			tx.commit();
		}
	}
}
