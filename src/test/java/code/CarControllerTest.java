package code;

import dao.CarDao;
import model.Car;
import org.junit.jupiter.api.Test;

import java.util.Optional;


class CarControllerTest {

    @Test
    void showEditForm()  {
        CarDao carDao = new CarDao();
        Car testCar = new Car();
        testCar.setRegNum("test1");
        testCar.setYear(2010);
        testCar.setManufacturer("BMW");
        testCar.setModel("X5");
        carDao.save(testCar);

        Optional<Car> existingCar = carDao.find("test1");

        existingCar.ifPresent(car -> System.out.println(car.getYear()));

        carDao.delete(testCar);

    }
}