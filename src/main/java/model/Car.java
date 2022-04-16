package model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "car")
public class Car {

    @Id
    @Column(name = "reg_num", nullable = false, length = 15)
    protected String regNum;


    @Column(name = "manufacturer", length = 30)
    protected String manufacturer;


    @Column(name = "model", length = 30)
    protected String model;


    @Column(name = "year")
    protected Integer year;


}