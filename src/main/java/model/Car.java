package model;

import lombok.Data;

/**
 * This class represents Car model
 */
@Data
public class Car {
    private String regNum;
    private String manufacturer;
    private String model;
    private int year;
}
