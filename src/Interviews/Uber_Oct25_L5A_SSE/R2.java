package Interviews.Uber_Oct25_L5A_SSE;

public class R2 {
    // Design Parking Lot
    // My soln:
    /*
        import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

import javax.management.RuntimeErrorException;



// /**
// Question

// Provide code for a parking lot with the following assumptions,

// • The parking lot has multiple levels. Each level has multiple rows of spots.
// • The parking lot has motorcycle spots and car spots.
// • A motorcycle can park in any empty spot that is empty.
// • A car can only park in a single car spot that is empty.


// Provide code for a parking lot with the following assumptions,

// • The parking lot has multiple levels. Each level has multiple rows of spots.
// • The parking lot has motorcycle spots and car spots.
// • A motorcycle can park in any spot that is empty.
// • A car can only park in a single car spot that is empty.

// Provide 3 functions for a working parking lot:

// Given a vehicle, you should be able to park it.
// Given a vehicle, you should be able to unpark it.
// Given a spot, you should be able to find the vehicle parked in it.










    //
    public class ParkingLot {
        String lotId;
        Level[] levels;
        int MAX_LEVELS = 5;

        ParkingLot(String lotId) {
            this.lotId = lotId;
            levels = new Level[MAX_LEVELS];
            for (int i=0;i<MAX_LEVELS;i++) {
                levels[i] = new Level(i, 40);
            }
        }

        void parkVehicle(Vehicle vehicle) {
            for (int i=0;i<MAX_LEVELS;i++) {
                if(this.levels[i].parkVehicle(vehicle)) {
                    System.out.println("Parked successfully");
                }
            }
            throw new InternalError("No Parking lot found");
        }
        // void unparkVehicle(Vehicle vehicle);
        // Vehicle fetchVehicleOnSpot(Spot spot);
    }

    public class Level {
        int levelId;
        int totalNumSpots;
        Map<Spot,Vehicle> parkingMap;
        // 0 -> M, 1=> C
        Map<Integer, Integer> availableParkingMap;
        Spot[] spots;

        Level(int levelId, int totalNumSpots) {
            this.levelId = levelId;
            this.totalNumSpots = totalNumSpots;
            this.parkingMap = new HashMap<>();
            this.availableParkingMap = new HashMap<>();
            this.availableParkingMap.put(0, totalNumSpots/2);
            this.availableParkingMap.put(1, totalNumSpots/2);
            spots = new Spot[totalNumSpots];
            for (int i=0;i<totalNumSpots;i++) {
                int vehicleType;
                if(i<totalNumSpots/2) {
                    vehicleType = 0;
                } else {
                    vehicleType = 1;
                }
                spots[i] = new Spot(i, vehicleType, levelId);
            }
        }

        boolean parkVehicle(Vehicle vehicle) {
            int vehicleType = vehicle.vehicleType;
            int slotsForVehicle = getSlotsAvailableForVehicleType(vehicleType);
            if (slotsForVehicle > 0) {
                for (int i=0;i<totalNumSpots;i++) {
                    if(spots[i].parkVehicle(vehicle)) {
                        parkingMap.put(spots[i],vehicle);
                        // Due to lack of time, assuming bike would be parked in bike slot only.
                        this.availableParkingMap.put(vehicleType, slotsForVehicle - 1);
                    }
                }
            }
            return false;
        }
        private int getSlotsAvailableForVehicleType(int vehicleType) {
            this.availableParkingMap.get(vehicleType);
        }
        // void unparkVehicle(Vehicle vehicle);
        // Vehicle fetchVehicle(Spot spot);
    }

    public class Spot {
        enum Status {
            OCCUPIED,
            EMPTY
        }
        int spotId;
        Status status;
        int spotVehicleType;
        int levelId;

        Spot(int spotId, int spotVehicleType, int levelId) {
            this.spotId = spotId;
            this.spotVehicleType = spotVehicleType;
            this.levelId = levelId;
        }

        boolean parkVehicle(Vehicle vehicle) {
            int vehicleType = vehicle.vehicleType;
            if (status == Status.EMPTY && isSpotValidForVehicleType(vehicleType)) {
                this.status = Status.OCCUPIED;
                return true;
            }
            return false;
        }

        private boolean isSpotValidForVehicleType(int vehicleType) {
            if (vehicleType == 1)
                return spotVehicleType == 1;
            else if (vehicleType == 0)
                return true;
        }

        // void unparkVehicle(Vehicle vehicle);
    }

    public abstract class Vehicle {
        int vehicleType;
        int vehicleId;
    }

    public class Motorcycle extends Vehicle {
        Motorcycle(int vehicleId) {
            this.vehicleType = 0;
            this.vehicleId = vehicleId;
        }
    }

    public class Car extends Vehicle {
        Car(int vehicleId) {
            this.vehicleType = 1;
            this.vehicleId = vehicleId;
        }
    }



     */
}
