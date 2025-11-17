package sk.lichvar.pcp.services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.lichvar.pcp.config.HibernateConfig;

import java.util.List;

/**
 * Implementation of the UserService interface.
 *
 * This class provides methods to interact with the User entity in a database,
 * using Hibernate as the ORM tool. It uses a singleton pattern to ensure that only one instance of this class exists.
 */
public class UserServiceImpl implements UserService {

	/**
	 * Logger for logging important events.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	/**
	 * Singleton instance of this class.
	 */
	private static UserService userService;

	/**
	 * Returns the singleton instance of this class.
	 *
	 * @return the singleton instance
	 */
	public static UserService getInstance() {
		if (userService == null) {
			userService = new UserServiceImpl();
		}
		return userService;
	}

	/**
	 * Inserts a new user into the database.
	 *
	 * This method starts a transaction, saves the user entity, and then commits the transaction.
	 * If any error occurs during this process, it rolls back the transaction and throws an exception.
	 *
	 * @param user the user to be inserted
	 */
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

	/**
	 * Retrieves a list of all users from the database.
	 *
	 * This method starts a transaction, executes a query to retrieve all users,
	 * and then returns the result as a list.
	 *
	 * @return a list of all users
	 */
	@Override
	public List<User> listAll() {
		try (Session session = openSession()) {
			return session.createQuery("SELECT u FROM User u", User.class).getResultList();
		}
	}

	/**
	 * Deletes all users from the database.
	 *
	 * This method starts a transaction, executes deletion query to remove all users,
	 * and then returns the number of rows updated.
	 *
	 * @return the number of rows updated
	 */
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

	/**
	 * Opens a new Hibernate session.
	 *
	 * This method returns a new session object that can be used to interact with the database.
	 *
	 * @return a new Hibernate session
	 */
	private Session openSession() {
		return HibernateConfig.getSessionFactory().openSession();
	}

	/**
	 * Rolls back a transaction if an error occurs.
	 *
	 * This method takes a transaction object and a Throwable exception as input,
	 * rolls back the transaction and logs an error message.
	 *
	 * @param tx the transaction to be rolled back
	 * @param t the exception that occurred
	 */
	private void rollback(Transaction tx, Throwable t) {
		if (tx != null) {
			tx.rollback();
		}
		LOGGER.error("Transaction rollbacked.", t);
	}
}
