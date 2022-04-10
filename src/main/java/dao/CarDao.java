package dao;

import model.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarDao implements Dao<Car, String> {

    private PreparedStatement ps;
    private ResultSet rs;

    public boolean save(Car car) {
        int result = 0;
        try (Connection connection = ConnectionFactory.getConnection()) {
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
        try (Connection connection = ConnectionFactory.getConnection()) {
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
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "select * from car";
//            ps = connection.prepareStatement(sql);
//            rs = ps.executeQuery();
            final Statement statement = connection.createStatement();
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
        try (Connection connection = ConnectionFactory.getConnection()) {
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
        try (Connection connection = ConnectionFactory.getConnection()) {
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
}
