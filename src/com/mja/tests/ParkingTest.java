package com.mja.tests;

import com.mja.Parking;

import java.time.LocalDateTime;

public class ParkingTest {

    public static void main(String[] args) {

        Parking parking = new Parking(10);

        System.out.println("Test of creating tickets:");
        for (int i = 0; i < 9 ; i++) {
            parking.createTicket();
        }

        parking.createTicket(100);
        parking.createTicket(101);
        System.out.println();

        System.out.println("Test of paying the ticket:");
        parking.payTicket(100, LocalDateTime.now().plusMinutes(30));
        parking.createTicket(101);
        parking.payTicket(101, LocalDateTime.now().plusHours(2));
        parking.createTicket(101);
        parking.payTicket(101, LocalDateTime.now().plusDays(2));

        System.out.println();
        System.out.println("Raport of revenue:");
        parking.monthlyRevenue();

        System.out.println();
        System.out.println("Raport of parking spots:");
        parking.monthlyUsedSpots();
    }
}
