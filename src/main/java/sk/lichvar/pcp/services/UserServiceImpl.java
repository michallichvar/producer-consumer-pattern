package sk.lichvar.pcp.services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.lichvar.pcp.config.HibernateConfig;

import java.util.List;

public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	private static UserService userService;

	public static UserService getInstance() {
		if (userService == null) {
			userService = new UserServiceImpl();
		}
		return userService;
	}

	@Override
	public void insert(final User user) {
		Transaction tx = null;
		try (Session session = openSession()) {
			tx = session.beginTransaction();
			session.save(user);
			tx.commit();
		} catch (Throwable t) {
			rollback(tx, t);
			throw new RuntimeException("Failed to save user", t);
		}
	}

	@Override
	public List<User> listAll() {
		try (Session session = openSession()) {
			return session.createQuery("SELECT u FROM User u", User.class).getResultList();
		}
	}

	@Override
	public int deleteAll() {
		int updateCount = -1;
		Transaction tx = null;
		try (Session session = openSession()) {
			tx = session.beginTransaction();
			updateCount = session.createQuery("DELETE FROM User u").executeUpdate();
			tx.commit();
		} catch (Throwable t) {
			rollback(tx, t);
			throw new RuntimeException("Failed to delete all users", t);
		}
		return updateCount;
	}

	private Session openSession() {
		return HibernateConfig.getSessionFactory().openSession();
	}

	private void rollback(Transaction tx, Throwable t) {
		if (tx != null) {
			tx.rollback();
		}
		LOGGER.error("Transaction rollbacked.", t);
	}
}
