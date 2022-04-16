package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
@Table(name = "car_of_the_day")
public class CarOfTheDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "reg_num", length = 15)
    private String regNum;

    @Column(name = "manufacturer", length = 30)
    private String manufacturer;

    @Column(name = "model", length = 30)
    private String model;

    @Column(name = "year")
    private Integer year;

    public Car getCar(){
        Car car= new Car();
        car.manufacturer = manufacturer;
        car.model = model;
        car.regNum = regNum;
        car.year = year;
        return car;
    }
}