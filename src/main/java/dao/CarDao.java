package dao;

import model.Car;
import model.CarOfTheDay;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.sql.*;
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

        //1. get count of all cars
        int counter = getCounter(session);
        //2. get random num
        int randomNum = getRandomNum(session, counter);
        //3. get car of the day
        Car carOfTheDay = getCarOfTheDay(session, randomNum);
        //4. insert car of the day on car_of_the_day table
        insertCarOfTheDay(session, carOfTheDay);


//
//
//            //5. get number of previous car
//            Car previousCarOfTheDay = getPreviousCarOfTheDay(connection);
//
//            carOfTheDayList.add(carOfTheDay);
//            carOfTheDayList.add(previousCarOfTheDay);
//
//            connection.commit();
//
//        } catch (SQLException e) {
//            try {
//                connection.rollback();
//                return null;
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//            e.printStackTrace();
//        }


        return carOfTheDayList;
    }

    private Car getPreviousCarOfTheDay(Connection connection) throws SQLException {
        ResultSet resultSet;
        String sql;
        Statement statement;
        Car previousCarOfTheDay = null;
        sql = "select * from car_of_the_day order by id desc offset 1 limit 1;";
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            final String regNum = resultSet.getString("reg_num");
            final String manufacturer = resultSet.getString("manufacturer");
            final String model = resultSet.getString("model");
            final int year = resultSet.getInt("year");
            final int id = resultSet.getInt("id");
            previousCarOfTheDay = new Car();
            previousCarOfTheDay.setManufacturer(manufacturer);
            previousCarOfTheDay.setRegNum(regNum);
            previousCarOfTheDay.setModel(model);
            previousCarOfTheDay.setYear(year);
        }

        if (previousCarOfTheDay == null) {
            throw new SQLException("can't get previousCarOfTheDay");
        }
        return previousCarOfTheDay;
    }

    public void insertCarOfTheDay(Session session, Car car){
        CarOfTheDay carOfTheDay = new CarOfTheDay();
        carOfTheDay.setManufacturer(car.getManufacturer());
        carOfTheDay.setModel(car.getModel());
        carOfTheDay.setYear(car.getYear());
        session.save(carOfTheDay);
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
}
