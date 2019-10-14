package com.mja;

import com.mja.myenum.FeeStatus;

import java.time.LocalDateTime;
import java.util.Objects;

public class Ticket {

    private int number;
    private int fee;
    private ParkingSpot parkingSpot;
    private FeeStatus feeStatus = FeeStatus.NOT_PAID;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Ticket(int number) {
        this.number = number;
        this.startTime = LocalDateTime.now();
    }

    public Enum getFeeStatus() {
        return feeStatus;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public void setFeeStatus(FeeStatus feeStatus) {
        this.feeStatus = feeStatus;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public int getFee() {
        return fee;
    }

    public void calculateFee() {
        this.fee = FeeSchedule.calculateFee(startTime, endTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return number == ticket.number &&
                Objects.equals(feeStatus, ticket.feeStatus) &&
                Objects.equals(startTime, ticket.startTime) &&
                Objects.equals(endTime, ticket.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, feeStatus, startTime, endTime);
    }
}
