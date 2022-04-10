package dao;

import model.Car;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.util.List;

@Disabled
class CarDaoTest {

    private static CarDao carDao;
    private static Car testCar;
    private static Car testCar2;

    @BeforeAll
    static void init() {
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

    void itShouldReturnInsertedCarAndPreviousCar() {
        //given
        //when
        List<Car> carList = carDao.carOfTheDay();
        Car carExpected = carDao.find(testCar.getRegNum()).orElse(new Car());
        //then
        Assertions.assertEquals(testCar, carExpected);
        carDao.delete(testCar);
    }

    @Test
    void itShouldSaveCarToDatabaseAndFindIt() {
        //given
        //when
        carDao.save(testCar);
        Car carExpected = carDao.find(testCar.getRegNum()).orElse(new Car());
        //then
        Assertions.assertEquals(testCar, carExpected);
        carDao.delete(testCar);
    }

    @Test
    void itShouldFindAllCars() {
        //given
        carDao.save(testCar);
        carDao.save(testCar2);
        //when
        List<Car> carList = carDao.findAll();
        //then
        carList.forEach(car ->
                Assertions.assertTrue(car.equals(testCar) || car.equals(testCar2)));
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
        Assertions.assertEquals(newModel, result.getModel());
        carDao.delete(testCar);
    }

    @Test
    void delete() {
        //given
        carDao.save(testCar);
        //when
        carDao.delete(testCar);
        //then
        Car result = carDao.find(testCar.getRegNum()).orElse(null);
        Assertions.assertNull(result.getModel());
        Assertions.assertNull(result.getRegNum());
        Assertions.assertNull(result.getManufacturer());
        Assertions.assertEquals(0, result.getYear());
    }
}