package code;

import dao.CarDao;
import model.Car;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "CarController", value = "/")
public class CarController extends HttpServlet {
    private CarDao carDao = new CarDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getServletPath();

        switch (action) {
            case "/new": {
                showNewForm(request, response);
                break;
            }
            case "/insert": {
                insertCar(request, response);
                break;
            }
            case "/delete": {
                deleteCar(request, response);
                break;
            }
            case "/edit": {
                showEditForm(request, response);
                break;
            }
            case "/update": {
                updateCar(request, response);
                break;
            }
            default:
                listCars(request, response);
        }
    }

    private void updateCar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Car car = getCar(request);
        carDao.update(car);
        response.sendRedirect("/jsp/CarList.jsp");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String regNum = request.getParameter("regNum");
        Optional<Car> existingCar = carDao.find(regNum);
//        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/CarForm.jsp");
//
//        carDao = new CarDao();
//        Car testCar = new Car();
//        testCar.setRegNum("test1");
//        testCar.setYear(2010);
//        testCar.setManufacturer("BMW");
//        testCar.setModel("X5");

//        request.setAttribute("car", testCar);
        existingCar.ifPresent(car -> request.setAttribute("car", car));
        getServletContext().getRequestDispatcher("/jsp/Test.jsp").forward(request, response);
//        dispatcher.forward(request, response);
    }

    private void deleteCar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Car car = new Car();
        car.setRegNum(request.getParameter("regNum"));
        carDao.delete(car);
        response.sendRedirect("/jsp/CarList.jsp");
    }

    private void insertCar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Car car = getCar(request);
        carDao.save(car);
        response.sendRedirect("/jsp/CarList.jsp");
    }

    private Car getCar(HttpServletRequest request) {
        Car car = new Car();
        car.setRegNum(request.getParameter("regNum"));
        car.setManufacturer(request.getParameter("manufacturer"));
        car.setModel(request.getParameter("model"));
        car.setYear(Integer.parseInt(request.getParameter("year")));
        return car;
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/CarForm.jsp");
        dispatcher.forward(request, response);
    }

    private void listCars(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/CarList.jsp");
        List<Car> listCar = carDao.findAll();
        request.setAttribute("listCar", listCar);
        getServletContext().getRequestDispatcher("/jsp/CarList.jsp").forward(request, response);
         }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}