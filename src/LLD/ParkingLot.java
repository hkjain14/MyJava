package LLD;

import java.util.HashMap;
import java.util.Map;

public class ParkingLot {

    public class Parking {
        String add;
        String name;

        int NUM_FLOORS = 5;
        Floor[] floors;

        Parking(String a, String n) {
            this.add = a;
            this.name = n;
            floors = new Floor[NUM_FLOORS];
            for(int i=0;i<NUM_FLOORS;i++) {
                floors[i] = new Floor(i,30);
            }
        }

        public void parkVehicle(Vehicle vehicle, double parkingTime) {
            for(int i=0;i<NUM_FLOORS;i++) {
                if(this.floors[i].parkVehicle(vehicle, parkingTime)) {
                    System.out.println(vehicle.vehicleType+" "+ vehicle.vehicleNumber +" parked successfully.");
                    return;
                }
            }
            System.out.println(vehicle.vehicleType+" "+ vehicle.vehicleNumber +" not parked successfully.");
        }

        public void freeVehicle(Vehicle vehicle, double exitingTime) {
            for(int i=0;i<NUM_FLOORS;i++) {
                double parkingTime = this.floors[i].freeVehicle(vehicle);
                if(parkingTime > 0) {
                    double totalTime = exitingTime-parkingTime;
                    double cost = totalTime*500;
                    System.out.println(vehicle.vehicleType+" "+ vehicle.vehicleNumber +" successfully exited. It was parked for "+totalTime*60+" minutes. Cost is "+cost);
                    return;
                }
            }
            System.out.println("Vehicle "+vehicle.vehicleType+ " of number "+vehicle.vehicleNumber+ " was not originally parked successfully. Hence no exit.");
        }

        public void getParkingLocation(Vehicle vehicle) {
            for(int i=0;i<NUM_FLOORS;i++) {
                if(this.floors[i].getParkingLocationAtFloor(vehicle)) {
                    return;
                }
            }
        }

    }

    public class Floor {
        int floorNum;
        int numSlots;
        Map<ParkingLot.VehicleType,Integer> availableSlotsMap;
        Map<Vehicle, Slot> parkMap;
        Slot[] slots;

        Floor(int f, int n) {
            this.floorNum = f;
            this.numSlots = n;
            this.availableSlotsMap = new HashMap<>();
            this.parkMap = new HashMap<>();
            availableSlotsMap.put(VehicleType.TRUCK,n/3);
            availableSlotsMap.put(VehicleType.CAR,n/3);
            availableSlotsMap.put(VehicleType.BIKE,n/3);
            slots = new Slot[n];
            for(int i=0;i<n;i++) {
                VehicleType slotVehicleType = VehicleType.TRUCK;
                if(i<10)
                    slotVehicleType = VehicleType.BIKE;
                else if(i<20)
                    slotVehicleType = VehicleType.CAR;
                slots[i] = new Slot(i,slotVehicleType,floorNum);
            }
        }

        public boolean parkVehicle(Vehicle vehicle, double parkingTime) {
            ParkingLot.VehicleType vehicleType = vehicle.vehicleType;

            if(getCountAvailableSlots(vehicleType)<=0) {
                System.out.println("Parking full for " + vehicleType + "on floor number " + this.floorNum);
                return false;
            }

            for(int i=0;i<numSlots;i++) {
                if(slots[i].parkVehicle(vehicle, parkingTime)) {
                    this.parkMap.put(vehicle,slots[i]);
                    this.availableSlotsMap.put(vehicleType, getCountAvailableSlots(vehicleType)-1);
                    return true;
                }
            }

            return false;
        }

        public double freeVehicle(Vehicle vehicle) {
            Slot slot = this.parkMap.get(vehicle);
            if(slot == null)
                return -1;
            double parkingTime = slot.parkingTime;
            this.parkMap.remove(vehicle);
            slot.freeSlot();
            this.availableSlotsMap.put(vehicle.vehicleType, getCountAvailableSlots(vehicle.vehicleType)+1);
            return parkingTime;
        }

        public boolean getParkingLocationAtFloor(Vehicle vehicle) {
            Slot slot = this.parkMap.get(vehicle);
            if(slot == null)
                return false;
            System.out.println("Vehicle bearing number "+vehicle.vehicleNumber+ " is parked on floor no "+ this.floorNum+ " at slot "+slot.id);
            return true;
        }

        public int getCountAvailableSlots(ParkingLot.VehicleType vehicleType) {return this.availableSlotsMap.get(vehicleType);}
    }

    public class Slot {

        public enum Status {
            OCCUPIED,
            EMPTY
        }

        Status status;
        VehicleType slotVehicleType;
        int id;
        int floorNum;
        double parkingTime;

        Slot(int id,VehicleType slotVehicleType, int floorNum) {
            this.id=id;
            this.slotVehicleType = slotVehicleType;
            this.floorNum = floorNum;
            this.status = Status.EMPTY;
        }

        public boolean parkVehicle(Vehicle vehicle, double parkingTime) {
            if(this.slotVehicleType == vehicle.vehicleType && this.status == Status.EMPTY) {
                this.parkingTime = parkingTime;
                this.status = Status.OCCUPIED;
                return true;
            }
            return false;
        }

        public void freeSlot() {
            this.parkingTime = 0;
            this.status = Status.EMPTY;
        }
    }

    public enum VehicleType {
        BIKE,
        CAR,
        TRUCK
    }

    public abstract class Vehicle {
        String vehicleNumber;
        VehicleType vehicleType;
    }

    public class Bike extends Vehicle {
        Bike(String vn) {
            this.vehicleNumber = vn;
            this.vehicleType = VehicleType.BIKE;
        }
    }
    public class Car extends Vehicle {
        Car(String vn) {
            this.vehicleNumber = vn;
            this.vehicleType = VehicleType.CAR;
        }
    }
    public class Truck extends Vehicle {
        Truck(String vn) {
            this.vehicleNumber = vn;
            this.vehicleType = VehicleType.TRUCK;
        }
    }

    public void driver() {
        Parking p = new Parking("Delhi","Ambience");

        Bike b1 = new Bike("123");
        p.parkVehicle(b1,1);
        p.getParkingLocation(b1);
        Bike b2 = new Bike("456");
        System.out.println("========================");
        p.parkVehicle(b2,10);
        p.getParkingLocation(b2);
        System.out.println("========================");
        p.freeVehicle(b1,2);
        System.out.println("========================");

        Car c1 = new Car("987");
        p.parkVehicle(c1,3);
        p.getParkingLocation(c1);
        p.freeVehicle(c1,3.5);
        System.out.println("========================");

        Truck t1 = new Truck("456");
        p.parkVehicle(t1,5);
        p.getParkingLocation(t1);
        p.freeVehicle(t1,7);
    }

    public static void main(String[] args) {
        ParkingLot pl = new ParkingLot();
        pl.driver();
    }
}
// Extra resources:
// https://github.com/careercup/CtCI-6th-Edition/tree/master/Java/Ch%2007.%20Object-Oriented%20Design/Q7_04_Parking_Lot
// https://leetcode.com/discuss/interview-question/124739/Parking-Lot-Design-Using-OO-Design
