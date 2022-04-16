package dao;

import model.Car;
import model.CarOfTheDay;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarDao implements Dao<Car, String> {

    public void save(Car car) {
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(car);
        transaction.commit();
        session.close();
    }

    @Override
    public Optional<Car> find(String regNum) {
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        Car car = session.get(Car.class, regNum);
        transaction.commit();
        session.close();
        return Optional.of(car);
    }

    @Override
    public List<Car> findAll() {
        List carList;
        Session session = HibernateSessionFactory.getSession();
        carList = session.createQuery("from Car").list();
        session.close();
        return carList;
    }

    @Override
    public void update(Car car) {
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        session.update(car);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Car car) {
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(car);
        transaction.commit();
        session.close();
    }

    public List<Car> carOfTheDay() {
        List<Car> carOfTheDayList = new ArrayList<>();
        Session session = HibernateSessionFactory.getSession();
        Transaction transaction = session.beginTransaction();

        try{
        //1. get count of all cars
        int counter = getCounter(session);
        //2. get random num
        int randomNum = getRandomNum(session, counter);
        //3. get car of the day
        Car carOfTheDay = getCarOfTheDay(session, randomNum);
        //4. insert car of the day on car_of_the_day table
        insertCarOfTheDay(session, carOfTheDay);
        //5. get number of previous car
        CarOfTheDay previousCarOfTheDay = getPreviousCarOfTheDay(session);
        carOfTheDayList.add(carOfTheDay);
        carOfTheDayList.add(previousCarOfTheDay.getCar());
        transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            return null;
        } finally {
            session.close();
        }
        return carOfTheDayList;

    }


    public int getCounter(Session session) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        cq.select(cb.count(cq.from(Car.class)));
        Integer result = Math.toIntExact(session.createQuery(cq).getSingleResult());
        return result;
    }

    public int getRandomNum(Session session, int counter) {
        String sql;
        sql = "SELECT floor(random()*( " + (counter - 1) + " -0+1))+0 as random_num;";
        Double result = (Double) session.createNativeQuery(sql).getSingleResult();
        return result.intValue();
    }

    public Car getCarOfTheDay(Session session, int randomNum) {
        return (Car) session.createQuery("from Car").list().get(randomNum);
    }

    public void insertCarOfTheDay(Session session, Car car) {
        CarOfTheDay carOfTheDay = new CarOfTheDay();
        carOfTheDay.setManufacturer(car.getManufacturer());
        carOfTheDay.setRegNum(car.getRegNum());
        carOfTheDay.setModel(car.getModel());
        carOfTheDay.setYear(car.getYear());
        session.save(carOfTheDay);
    }

    @SuppressWarnings("unchecked")
    public CarOfTheDay getPreviousCarOfTheDay(Session session) {
        CarOfTheDay carOfTheDay = null;
        List<CarOfTheDay> carOfTheDayList =
                (List<CarOfTheDay>) session
                        .createQuery("select c from CarOfTheDay c order by c.id desc ")
                        .setFirstResult(1)
                        .setMaxResults(1)
                        .getResultList();
        if (carOfTheDayList.size() == 1) {
            carOfTheDay = carOfTheDayList.get(0);
        }
        return carOfTheDay;
    }
}
