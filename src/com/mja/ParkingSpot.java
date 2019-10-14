package com.mja;

import com.mja.myenum.CarSpotStatus;

import java.util.Objects;

public class ParkingSpot {

    private int number;
    private CarSpotStatus carSpotStatus = CarSpotStatus.FREE;

    public ParkingSpot(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public CarSpotStatus getCarSpotStatus() {
        return carSpotStatus;
    }

    public void setCarSpotStatus(CarSpotStatus carSpotStatus) {
        this.carSpotStatus = carSpotStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSpot that = (ParkingSpot) o;
        return number == that.number &&
                carSpotStatus == that.carSpotStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, carSpotStatus);
    }

    @Override
    public String toString() {
        return "ParkingSpot{" +
                "number=" + number +
                ", carSpotStatus=" + carSpotStatus +
                '}';
    }
}
