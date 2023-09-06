package sk.lichvar.pcp.hibernate;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import sk.lichvar.pcp.model.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Static utils class for creating Hibernate {@link SessionFactory}
 */
public class HibernateUtils {

	@Getter
	private static SessionFactory sessionFactory;

	static {
		try {
			createHibernateSessionFactory();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void createHibernateSessionFactory() throws IOException {
		StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(getHibernateProperties())
				.build();

		Configuration configuration = new Configuration()
				.addAnnotatedClass(User.class);

		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}

	private static Properties getHibernateProperties() throws IOException {
		Properties hibernateProperties = new Properties();
		hibernateProperties.load(new FileInputStream(
				Thread.currentThread().getContextClassLoader().getResource("hibernate.properties").getPath()
		));
		return hibernateProperties;
	}
}
