package dao;

import model.Car;
import model.CarOfTheDay;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.id.IdentifierGenerationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CarDaoTest {

    private static CarDao carDao;

    private static Car testCar;
    private static Car testCar2;

    @BeforeEach
    void init() {
        final Session session = HibernateSessionFactory.getSession();
        session.beginTransaction();
        String truncateCar = "Truncate table car";
        String truncateCarOfTheDay = "Truncate table car_of_the_day";
        session.createSQLQuery(truncateCar).executeUpdate();
        session.createSQLQuery(truncateCarOfTheDay).executeUpdate();
        session.getTransaction().commit();
        session.close();


        carDao = new CarDao();

        testCar = new Car();
        testCar.setRegNum("test1");
        testCar.setYear(2010);
        testCar.setManufacturer("BMW");
        testCar.setModel("X5");

        testCar2 = new Car();
        testCar2.setRegNum("test2");
        testCar2.setYear(1990);
        testCar2.setManufacturer("Ваз");
        testCar2.setModel("2101");

    }

    @Test
    void itShouldSaveCarAndFindIt() {
        carDao.save(testCar);
        Optional<Car> fromBase = carDao.find(testCar.getRegNum());
        assertEquals(testCar, fromBase.get());
    }

    @Test
    void itShouldThrowIdentifierGenerationExceptionWhenRegNumIsNull() {
        assertThrows(IdentifierGenerationException.class, () -> {
            carDao.save(new Car());
        });
    }

    @Test
    void itShouldThrowNullPointerExceptionWhenKeyNotFound() {
        assertThrows(NullPointerException.class, () -> {
            Optional<Car> fromBase = carDao.find(testCar.getRegNum());
        });
    }

    @Test
    void itShouldThrowHibernateExceptionWhenKeysAreDuplicated() {
        assertThrows(PersistenceException.class, () -> {
            carDao.save(testCar);
            carDao.save(testCar);
        });
    }


    @Test
    void itShouldReturnListOfCars() {
        carDao.save(testCar);
        carDao.save(testCar2);
        List<Car> returned = carDao.findAll();
        assertEquals(testCar, returned.get(0));
        assertEquals(testCar2, returned.get(1));
    }

    @Test
    void itShouldUpdateCar() {
        carDao.save(testCar);
        String expected = "TEST_MANUFACTURER";
        testCar.setManufacturer(expected);
        carDao.update(testCar);
        Optional<Car> fromBase = carDao.find(testCar.getRegNum());
        assertEquals(expected, fromBase.get().getManufacturer());
    }

    @Test
    void itShouldDeleteCar() {
        carDao.save(testCar);
        carDao.save(testCar2);
        List<Car> returned = carDao.findAll();
        assertTrue(returned.contains(testCar));
        carDao.delete(testCar);
        returned = carDao.findAll();
        assertFalse(returned.contains(testCar));
    }


    @Test
    void itShouldReturnCounterOfCars() {
        carDao.save(testCar);
        carDao.save(testCar2);


        final Session session = HibernateSessionFactory.getSession();
        final Transaction transaction = session.beginTransaction();

        Integer value = carDao.getCounter(session);
        transaction.commit();
        session.close();

        assertEquals(2,value);
    }

    @Test
    void itShouldReturnRandomNum() {
        final Session session = HibernateSessionFactory.getSession();
        final Transaction transaction = session.beginTransaction();

        for (int i = 0; i < 100; i++) {
            Integer value = carDao.getRandomNum(session, 10);
            assertTrue(value >= 0 && value <10);
        }
        transaction.commit();
        session.close();
    }

    @Test
    void itShouldReturnCarOfTheDay(){
        List<Car> carList = new ArrayList<>();
        carList.add(testCar);
        carList.add(testCar2);
        carList.forEach(carDao::save);
        final Session session = HibernateSessionFactory.getSession();
        final Transaction transaction = session.beginTransaction();

        Car carOfTheDay = carDao.getCarOfTheDay(session, 0);
        assertEquals(testCar, carOfTheDay);
        carOfTheDay = carDao.getCarOfTheDay(session, 1);
        assertEquals(testCar2, carOfTheDay);
        transaction.commit();
        session.close();

    }

    @Test
    void itShouldInsertCarOfTheDay(){

        final Session session = HibernateSessionFactory.getSession();
        final Transaction transaction = session.beginTransaction();

        carDao.insertCarOfTheDay(session, testCar);
        List<CarOfTheDay> carOfTheDayList = session.createQuery("from CarOfTheDay ").list();
        System.out.println(carOfTheDayList.size());
        CarOfTheDay fromDB = carOfTheDayList.get(carOfTheDayList.size()-1);

        assertEquals(testCar.getRegNum(), fromDB.getRegNum());

        transaction.commit();
        session.close();

    }

    @Test
    void itShouldReturnPreviousCarOfTheDay(){
        final Session session = HibernateSessionFactory.getSession();
        final Transaction transaction = session.beginTransaction();

        carDao.insertCarOfTheDay(session, testCar2);
        carDao.insertCarOfTheDay(session, testCar2);
        carDao.insertCarOfTheDay(session, testCar2);
        carDao.insertCarOfTheDay(session, testCar2);
        carDao.insertCarOfTheDay(session, testCar2);
        carDao.insertCarOfTheDay(session, testCar);
        carDao.insertCarOfTheDay(session, testCar2);

        CarOfTheDay fromDB = carDao.getPreviousCarOfTheDay(session);
        assertEquals(testCar.getRegNum(), fromDB.getRegNum());

        transaction.commit();
        session.close();
    }

    @Test
    void itShouldReturnNull(){
        final Session session = HibernateSessionFactory.getSession();
        final Transaction transaction = session.beginTransaction();

        CarOfTheDay fromDB = carDao.getPreviousCarOfTheDay(session);
        assertNull(fromDB);

        transaction.commit();
        session.close();
    }








}