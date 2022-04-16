package dao;

import model.Car;
import model.CarOfTheDay;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


public class HibernateSessionFactory {

    private static SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration().configure();
            configuration.addAnnotatedClass(Car.class);
            configuration.addAnnotatedClass(CarOfTheDay.class);
            StandardServiceRegistryBuilder builder =
                    new StandardServiceRegistryBuilder()
                            .applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(builder.build());
        } catch (Exception exception){
            exception.printStackTrace();
        }
    }

    private HibernateSessionFactory() {
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }
}
