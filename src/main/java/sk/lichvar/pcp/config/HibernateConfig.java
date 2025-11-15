package sk.lichvar.pcp.config;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import sk.lichvar.pcp.services.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * A utility class responsible for initializing and managing a {@link SessionFactory}
 */
public class HibernateConfig {

	/**
	 * The singleton instance of the Hibernate SessionFactory.
	 */
	@Getter
	private static SessionFactory sessionFactory = createHibernateSessionFactory();

	/**
	 * Creates a new instance of the Hibernate SessionFactory using the settings from
	 * {@link #getHibernateProperties()} and adds the annotated classes to be managed by Hibernate.
	 *
	 * @throws IOException if an error occurs while loading Hibernate properties.
	 */
	private static SessionFactory createHibernateSessionFactory() {
		try {
			StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(getHibernateProperties())
					.build();

			Configuration configuration = new Configuration()
					.addAnnotatedClass(User.class);

			return configuration.buildSessionFactory(serviceRegistry);
		} catch (IOException e) {
			throw new RuntimeException("Unable to create HibernateSessionFactory", e);
		}
	}

	/**
	 * Loads the Hibernate properties from a file named "hibernate.properties" located in the classpath.
	 *
	 * @return the loaded Hibernate properties.
	 * @throws IOException if an error occurs while loading the properties.
	 */
	private static Properties getHibernateProperties() throws IOException {
		Properties hibernateProperties = new Properties();
		hibernateProperties.load(new FileInputStream(
				Thread.currentThread().getContextClassLoader().getResource("hibernate.properties").getPath()
		));
		return hibernateProperties;
	}
}
