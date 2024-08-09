package util;

import model.Match;
import model.Player;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

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

                executeSqlScript("data.sql");

            } catch (Exception e) {
                releaseResources();
                throw new ExceptionInInitializerError(e);
            }
        }
        return sessionFactory;
    }

    private static void executeSqlScript(String scriptPath) {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:h2:mem:test", "ohrim", "");
             Statement statement = connection.createStatement();
             BufferedReader reader = new BufferedReader(new InputStreamReader(
                     HibernateUtil.class.getClassLoader().getResourceAsStream(scriptPath)))) {

            StringBuilder sql = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sql.append(line).append("\n");
            }

            statement.execute(sql.toString());
            System.out.println("SQL script executed successfully.");

        } catch (Exception e) {
            System.err.println("Failed to execute SQL script.");
        }
    }

    public static void releaseResources() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

}
