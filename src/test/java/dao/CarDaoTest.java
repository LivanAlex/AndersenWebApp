package dao;

import model.Car;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;


class CarDaoTest {

    private static CarDao carDao;

    private static Car testCar;
    private static Car testCar2;

    @BeforeEach
    void init() {
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

        executeSqlScript("createCarTable.sql");
        executeSqlScript("createCarOfTheDayTable.sql");
    }


    @AfterEach
    void wipeData(){
        executeSqlScript("createCarTable.sql");
        executeSqlScript("createCarOfTheDayTable.sql");
    }


    @Test
    void itShouldReturnNumberOfCarsInCarTable() throws NoSuchMethodException, SQLException, InvocationTargetException, IllegalAccessException {
        //given (create car table with 2 cars)
        executeSqlScript("insertCarsIntoCarTable.sql");
        //when
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.getConnection();
        Method method = CarDao.class.getDeclaredMethod("getCounter", Connection.class);
        method.setAccessible(true);
        Integer result = (Integer) method.invoke(new CarDao(), connection);
        //then
        assertEquals(2, result);

        factory.close();
    }

    @Test
    void itShouldReturnRandomNumBetweenZeroAndGivenNumMinusOne() throws NoSuchMethodException, SQLException, InvocationTargetException, IllegalAccessException {
        //given
        //when
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.getConnection();
        Method method = CarDao.class.getDeclaredMethod("getRandomNum", Connection.class, Integer.TYPE);
        method.setAccessible(true);
        Integer result = (Integer) method.invoke(new CarDao(), connection, 10);
        //then
        assertTrue(result >= 0 && result < 10);
        factory.close();
    }


    @Test
    void itShouldReturnCarByIdFromCarOfTheDayTable() throws NoSuchMethodException, SQLException, InvocationTargetException, IllegalAccessException {
        //given (create car table with 2 cars)
        executeSqlScript("insertCarsIntoCarTable.sql");
        //when
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.getConnection();
        Method method = CarDao.class.getDeclaredMethod("getCarOfTheDay", Connection.class, Integer.TYPE);
        method.setAccessible(true);
        Car car = (Car) method.invoke(new CarDao(), connection, 0);
        Car car2 = (Car) method.invoke(new CarDao(), connection, 1);
        //then

        assertEquals(testCar, car);
        assertEquals(testCar2, car2);

        factory.close();
    }


    @Test
    void itShouldInsertCarInCarOfTheDayTable() throws NoSuchMethodException, SQLException, InvocationTargetException, IllegalAccessException {
        //given
        //when
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.getConnection();
        Method insertCarOfTheDay = CarDao.class.getDeclaredMethod("insertCarOfTheDay", Connection.class, Car.class);
        insertCarOfTheDay.setAccessible(true);
        int result = (Integer) insertCarOfTheDay.invoke(new CarDao(), connection, testCar);

        //then
        assertTrue(1 == result);

        factory.close();
    }


    @Test
    void itShouldReturnPreviousCarOfTheDay() throws NoSuchMethodException, SQLException, InvocationTargetException, IllegalAccessException {
        //given (create car table with 2 cars)
        executeSqlScript("insertCarsIntoCarOfTheDayTable.sql");
        //when
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.getConnection();
        Method method = CarDao.class.getDeclaredMethod("getPreviousCarOfTheDay", Connection.class);
        method.setAccessible(true);
        Car car = (Car) method.invoke(new CarDao(), connection);
        //then
        assertEquals(testCar, car);

        factory.close();
    }



    @Test
    void itShouldSaveCarToDatabaseAndFindIt() {
        //given
        //when
        carDao.save(testCar);
        Car carExpected = carDao.find(testCar.getRegNum()).orElse(new Car());
        //then
        assertEquals(testCar, carExpected);

        carDao.delete(testCar);
    }

    @Test
    void itShouldFindAllCarsInDatabase() {
        //given
        carDao.save(testCar);
        carDao.save(testCar2);
        //when
        List<Car> carList = carDao.findAll();
        //then
        carList.forEach(car ->
                assertTrue(car.equals(testCar) || car.equals(testCar2)));
        carList.forEach(carDao::delete);
    }

    @Test
    void itShouldUpdateCarInDatabase() {
        //given
        carDao.save(testCar);
        //when
        String newModel = "newModel";
        testCar.setModel(newModel);
        carDao.update(testCar);
        //then
        Car result = carDao.find(testCar.getRegNum()).orElse(new Car());
        assertEquals(newModel, result.getModel());
        carDao.delete(testCar);
        testCar.setModel("X5");
    }

    @Test
    void itShouldDeleteCarInDatabase() {
        //given
        carDao.save(testCar);
        //when
        carDao.delete(testCar);
        //then
        Car result = carDao.find(testCar.getRegNum()).orElse(null);
        assertNull(result.getModel());
        assertNull(result.getRegNum());
        assertNull(result.getManufacturer());
        assertEquals(0, result.getYear());
    }

    private void executeSqlScript(String fileName) {
        Connection conn = null;
        ConnectionFactory cf = new ConnectionFactory();
        try {
            conn = cf.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        File inputFile = null;
        final URL url = this.getClass().getClassLoader().getResource(fileName);
        try {
            inputFile = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        // Delimiter
        String delimiter = ";";

        // Create scanner
        Scanner scanner;
        try {
            scanner = new Scanner(inputFile).useDelimiter(delimiter);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return;
        }

        // Loop through the SQL file statements
        Statement currentStatement = null;
        while (scanner.hasNext()) {

            // Get statement
            String rawStatement = scanner.next() + delimiter;
            try {
                // Execute statement
                currentStatement = conn.createStatement();
                currentStatement.execute(rawStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Release resources
                if (currentStatement != null) {
                    try {
                        currentStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                currentStatement = null;
            }
        }
        scanner.close();
        cf.close();
    }
}