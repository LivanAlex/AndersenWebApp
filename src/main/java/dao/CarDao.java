package dao;

import com.zaxxer.hikari.HikariDataSource;
import model.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarDao implements Dao<Car, String> {


    private HikariDataSource hds;
    private PreparedStatement ps;
    private Statement statement;
    private ResultSet rs;

    public boolean save(Car car) {
        int result = 0;
        try (ConnectionFactory factory = new ConnectionFactory();
             HikariDataSource hds = factory.getHikariDataSource();
             Connection connection = hds.getConnection()) {
            String sql = "insert into car values (?,?,?,?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1, car.getRegNum());
            ps.setString(2, car.getManufacturer());
            ps.setString(3, car.getModel());
            ps.setInt(4, car.getYear());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return result > 0;
    }

    @Override
    public Optional<Car> find(String regNum) {
        Car car = new Car();
        try (ConnectionFactory factory = new ConnectionFactory();
             HikariDataSource hds = factory.getHikariDataSource();
             Connection connection = hds.getConnection()) {
            String sql = "select * from car where reg_num = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, regNum);
            rs = ps.executeQuery();
            if (rs.next()) {
                car.setRegNum(rs.getString("reg_num"));
                car.setManufacturer(rs.getString("manufacturer"));
                car.setModel(rs.getString("model"));
                car.setYear(rs.getInt("year"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.of(car);
    }

    @Override
    public List<Car> findAll() {
        List<Car> carList = new ArrayList<>();
        try (ConnectionFactory factory = new ConnectionFactory();
             HikariDataSource hds = factory.getHikariDataSource();
             Connection connection = hds.getConnection();
             Statement statement = connection.createStatement()
        ) {
            String sql = "select * from car";
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                Car car = new Car();
                car.setRegNum(rs.getString("reg_num"));
                car.setManufacturer(rs.getString("manufacturer"));
                car.setModel(rs.getString("model"));
                car.setYear(rs.getInt("year"));
                carList.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return carList;
        }
        return carList;
    }

    @Override
    public boolean update(Car car) {
        int result = 0;
        try (ConnectionFactory factory = new ConnectionFactory();
             HikariDataSource hds = factory.getHikariDataSource();
             Connection connection = hds.getConnection()) {
            String sql =
                    "update car set manufacturer = ?, model = ?, year = ? where  reg_num = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, car.getManufacturer());
            ps.setString(2, car.getModel());
            ps.setInt(3, car.getYear());
            ps.setString(4, car.getRegNum());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return result > 0;
    }

    @Override
    public boolean delete(Car car) {
        int result = 0;
        try (ConnectionFactory factory = new ConnectionFactory();
             HikariDataSource hds = factory.getHikariDataSource();
             Connection connection = hds.getConnection()) {
            String sql = "delete from car where reg_num = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, car.getRegNum());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return result > 0;
    }

    public List<Car> carOfTheDay() {
        List<Car> carOfTheDayList = new ArrayList<>();

        try (ConnectionFactory factory = new ConnectionFactory();
             HikariDataSource hds = factory.getHikariDataSource()
        ) {
            hds.setAutoCommit(false);
            Connection connection = hds.getConnection();
            connection.setAutoCommit(false);

            //1. get count of all cars

            int counter = 0;
            String sql = "SELECT count(*) as counter FROM car;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                counter = resultSet.getInt("counter");
            }


            //2. get random num

            int randomNum = -1;
            sql = "SELECT floor(random()*( " + (counter - 1) + " -0+1))+0 as random_num;";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                randomNum = resultSet.getInt("random_num");
            }


            //3. get car of the day

            Car carOfTheDay = new Car();
            sql = "SELECT * from car OFFSET " + randomNum + "LIMIT 1;";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                final String regNum = resultSet.getString("reg_num");
                final String manufacturer = resultSet.getString("manufacturer");
                final String model = resultSet.getString("model");
                final int year = resultSet.getInt("year");
                carOfTheDay.setManufacturer(manufacturer);
                carOfTheDay.setRegNum(regNum);
                carOfTheDay.setModel(model);
                carOfTheDay.setYear(year);
            }


            //4. get car of the day


            sql = "INSERT INTO car_of_the_day (reg_num, manufacturer, model, year) values (?,?,?,?);";
            ps = connection.prepareStatement(sql);
            ps.setString(1, carOfTheDay.getRegNum());
            ps.setString(2, carOfTheDay.getManufacturer());
            ps.setString(3, carOfTheDay.getModel());
            ps.setInt(4, carOfTheDay.getYear());
            final int i = ps.executeUpdate();
            if (i < 1) {
                throw new SQLException("can't insert data in car_of_the_day table");
            }


            //5. get number of previous car

            Car previousCarOfTheDay = new Car();
            sql = "select * from car_of_the_day order by id desc offset 1 limit 1;";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                final String regNum = resultSet.getString("reg_num");
                final String manufacturer = resultSet.getString("manufacturer");
                final String model = resultSet.getString("model");
                final int year = resultSet.getInt("year");
                final int id = resultSet.getInt("id");
                previousCarOfTheDay.setManufacturer(manufacturer);
                previousCarOfTheDay.setRegNum(regNum);
                previousCarOfTheDay.setModel(model);
                previousCarOfTheDay.setYear(year);
            }

            carOfTheDayList.add(carOfTheDay);
            carOfTheDayList.add(previousCarOfTheDay);

            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return carOfTheDayList;
    }
}
