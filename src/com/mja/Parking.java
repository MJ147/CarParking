package com.mja;

import com.mja.myenum.CarSpotStatus;
import com.mja.myenum.FeeStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Parking {

    private int parkingSpotAmount;
    private int ticketNumber;

    private List<ParkingSpot> parkingSpotList;
    private Map<Integer , Ticket> ticketMap;
    private List<Ticket> ticketArchive;

    private Random numberGenerator = new Random();

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public Parking(int parkingSpotAmount) {
        this.parkingSpotAmount = parkingSpotAmount;
        parkingSpotList = new ArrayList<>();
        ticketMap = new HashMap<>();
        ticketArchive = new ArrayList<>();
        createParking();
    }

    private void createParking() {
        for (int i = 0; i < parkingSpotAmount; i++) {
            parkingSpotList.add(new ParkingSpot(i));
        }
    }

    //create ticket with random number
    public void createTicket() {
        ticketNumber = createNumber();
        int emptySpot = findEmptySpot();
        if (emptySpot == -1) {
            System.out.println("Car parking is full.");
        } else {
            ticketMap.put(ticketNumber, new Ticket(ticketNumber));
            parkingSpotList.get(emptySpot).setCarSpotStatus(CarSpotStatus.TAKEN);
            System.out.print("Ticket number: " + ticketNumber);
            System.out.println(", Praking Spot: " + emptySpot);
        }
    }

    //create ticket with specific number
    public void createTicket(int ticketNumber) {
        if(ticketMap.containsKey(ticketNumber)){
            System.out.println("Numer biletu jest już zajęty.");
            return;
        }
        int emptySpot = findEmptySpot();
        if (emptySpot == -1) {
            System.out.println("Car parking is full.");
        } else {
            ticketMap.put(ticketNumber, new Ticket(ticketNumber));
            parkingSpotList.get(emptySpot).setCarSpotStatus(CarSpotStatus.TAKEN);
            ticketMap.get(ticketNumber).setParkingSpot(parkingSpotList.get(emptySpot));
            System.out.print("Ticket number: " + ticketNumber);
            System.out.println(", Praking Spot: " + emptySpot);
        }
    }
    // ticket number generator
    public int createNumber() {
        int newTicketNumber;
        do {
            newTicketNumber = numberGenerator.nextInt(9000) + 1000;
        } while (ticketMap.containsKey(newTicketNumber));
        return newTicketNumber;
    }
    // finding first empty parking spot
    public int findEmptySpot() {
        int indexOfSpot = -1;
        for (ParkingSpot spot : parkingSpotList) {
            if (spot.getCarSpotStatus() == CarSpotStatus.FREE) {
                indexOfSpot = parkingSpotList.indexOf(spot);
                break;
            }
        }
        return indexOfSpot;
    }

    // paying the ticket when car leaving the parking
    public void payTicket(int ticketNumber) {
        if (ticketMap.containsKey(ticketNumber)) {
            ticketMap.get(ticketNumber).setEndTime(LocalDateTime.now());
            ticketMap.get(ticketNumber).calculateFee();
            ticketMap.get(ticketNumber).setFeeStatus(FeeStatus.PAID);
            ticketMap.get(ticketNumber).getParkingSpot().setCarSpotStatus(CarSpotStatus.FREE);
            ticketArchive.add(ticketMap.get(ticketNumber));
            System.out.println("Ticket number: " + ticketNumber + " was paid\n" +
                    "Period: " + ticketMap.get(ticketNumber).getStartTime().format(formatter) + " to " + ticketMap.get(ticketNumber).getEndTime().format(formatter) + "\n" +
                    "Cost: " + ticketMap.get(ticketNumber).getFee() + " zł");
            ticketMap.remove(ticketNumber);
        } else {
            System.out.println("Ticket is not in the system.");
        }
    }

    // payTicket with forced end time of period (to test)
    public void payTicket(int ticketNumber, LocalDateTime endTime) {
        if (ticketMap.containsKey(ticketNumber)) {
            ticketMap.get(ticketNumber).setEndTime(endTime);
            ticketMap.get(ticketNumber).calculateFee();
            ticketMap.get(ticketNumber).setFeeStatus(FeeStatus.PAID);
            ticketMap.get(ticketNumber).getParkingSpot().setCarSpotStatus(CarSpotStatus.FREE);
            ticketArchive.add(ticketMap.get(ticketNumber));
            System.out.println("\nTransaction accepted!" + "\nTicket number: " + ticketNumber + ", Cost: " + ticketMap.get(ticketNumber).getFee() + " zł\n" +
                    "Period: " + ticketMap.get(ticketNumber).getStartTime().format(formatter) + " to " + ticketMap.get(ticketNumber).getEndTime().format(formatter) + "\n");
            ticketMap.remove(ticketNumber);
        } else {
            System.out.println("Ticket is not in the system.");
        }
    }

    // summary of monthly revenue of whole year
    public void monthlyRevenue() {
        int[] monthlyMany = new int[12];
        for (int i = 0; i < monthlyMany.length ; i++) {
            for (Ticket ticket : ticketArchive) {
                if (ticket.getEndTime().getMonthValue() == i + 1) {
                    monthlyMany[i] += ticket.getFee();
                }
            }
            System.out.println("Number od month: " + (i + 1) + " - " + monthlyMany[i] + " zł");
        }
    }

    // summary of monthly used spots of whole year
    public void monthlyUsedSpots() {
        int[] monthlyUsedSpots = new int[12];
        for (int i = 0; i < monthlyUsedSpots.length ; i++) {
            for (Ticket ticket : ticketArchive) {
                if (ticket.getEndTime().getMonthValue() == i + 1) {
                    monthlyUsedSpots[i] += 1;
                }
            }
            System.out.println("Number od month: " + (i + 1) + " - " + monthlyUsedSpots[i] + " taken parking spot");
        }
    }
}
