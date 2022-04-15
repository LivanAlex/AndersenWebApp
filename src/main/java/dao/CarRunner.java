package dao;

import model.Car;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


public class CarRunner {
    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Car.class);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Car testCar = new Car();
        testCar.setRegNum("test1");
        testCar.setYear(2010);
        testCar.setManufacturer("BMW");
        testCar.setModel("X5");
        session.save(testCar);
        transaction.commit();
        session.close();
        sessionFactory.close();
    }
}
