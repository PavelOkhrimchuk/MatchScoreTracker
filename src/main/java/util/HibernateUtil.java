package util;

import model.Match;
import model.Player;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {

                Configuration configuration = new Configuration().configure("hibernate.cfg.xml");


                registry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build();


                MetadataSources sources = new MetadataSources(registry)
                        .addAnnotatedClass(Player.class)
                        .addAnnotatedClass(Match.class);


                Metadata metadata = sources.getMetadataBuilder().build();


                sessionFactory = metadata.getSessionFactoryBuilder().build();
            } catch (Exception e) {
                releaseResources();
                throw new ExceptionInInitializerError(e);
            }
        }
        return sessionFactory;
    }

    public static void releaseResources() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

}
