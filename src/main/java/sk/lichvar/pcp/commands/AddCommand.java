package sk.lichvar.pcp.commands;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sk.lichvar.pcp.hibernate.HibernateUtils;
import sk.lichvar.pcp.model.User;

/**
 * Represents command Add(id, guid, userName).
 * Adds {@link User} to database.
 */
@AllArgsConstructor
public class AddCommand extends Command {

	private Integer id;
	private String guid;
	private String userName;

	@Override
	public void execute() {
		Transaction tx = null;
		try (Session session = HibernateUtils.getSessionFactory().openSession()) {
			tx = session.beginTransaction();
			session.save(new User(id, guid, userName));
			tx.commit();
		} catch (Throwable t) {
			System.err.println("Rollbacking because of " + t.getMessage());
			t.printStackTrace();
			if (tx != null) {
				try {
					tx.rollback();
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
	}
}
