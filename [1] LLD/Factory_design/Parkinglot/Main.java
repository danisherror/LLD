// package Factory_design.ParkingLot;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

// Abstract Product: Vehicle
abstract class Vehicle {
    private String licensePlate;
    private VehicleType type;

    public Vehicle(String licensePlate, VehicleType type) {
        this.licensePlate = licensePlate;
        this.type = type;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public VehicleType getType() {
        return type;
    }
}

// Concrete Products: Car, Bike, HeavyVehicle
class Car extends Vehicle {
    public Car(String licensePlate) {
        super(licensePlate, VehicleType.CAR);
    }
}

class Bike extends Vehicle {
    public Bike(String licensePlate) {
        super(licensePlate, VehicleType.BIKE);
    }
}

class HeavyVehicle extends Vehicle {
    public HeavyVehicle(String licensePlate) {
        super(licensePlate, VehicleType.HEAVY);
    }
}

// Enum for Vehicle Types
enum VehicleType {
    CAR, BIKE, HEAVY
}

// Abstract Product: ParkingSpot
abstract class ParkingSpot {
    private String id;
    private boolean occupied;
    private boolean isHandicap;
    private boolean isReserved;
    private int proximityIndex; // Lower values indicate closer to entrance

    public ParkingSpot(String id, boolean isHandicap, boolean isReserved, int proximityIndex) {
        this.id = id;
        this.occupied = false;
        this.isHandicap = isHandicap;
        this.isReserved = isReserved;
        this.proximityIndex = proximityIndex;
    }

    public String getId() {
        return id;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public boolean isHandicap() {
        return isHandicap;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public int getProximityIndex() {
        return proximityIndex;
    }

    public void occupy() {
        if (occupied) {
            throw new IllegalStateException("Spot " + id + " is already occupied.");
        }
        occupied = true;
    }

    public void free() {
        if (!occupied) {
            throw new IllegalStateException("Spot " + id + " is already free.");
        }
        occupied = false;
    }
}

// Concrete Products: CarSpot, BikeSpot, HeavySpot
class CarSpot extends ParkingSpot {
    public CarSpot(String id, boolean isHandicap, boolean isReserved, int proximityIndex) {
        super(id, isHandicap, isReserved, proximityIndex);
    }
}

class BikeSpot extends ParkingSpot {
    public BikeSpot(String id, boolean isHandicap, boolean isReserved, int proximityIndex) {
        super(id, isHandicap, isReserved, proximityIndex);
    }
}

class HeavySpot extends ParkingSpot {
    public HeavySpot(String id, boolean isHandicap, boolean isReserved, int proximityIndex) {
        super(id, isHandicap, isReserved, proximityIndex);
    }
}

// Abstract Factory: ParkingFactory
interface ParkingFactory {
    ParkingSpot createSpot(String id, boolean isHandicap, boolean isReserved, int proximityIndex);
    Ticket createTicket(Vehicle vehicle, ParkingSpot spot, int floorNumber, String gateId);
}

// Concrete Factory: CarParkingFactory
class CarParkingFactory implements ParkingFactory {
    @Override
    public ParkingSpot createSpot(String id, boolean isHandicap, boolean isReserved, int proximityIndex) {
        return new CarSpot(id, isHandicap, isReserved, proximityIndex);
    }

    @Override
    public Ticket createTicket(Vehicle vehicle, ParkingSpot spot, int floorNumber, String gateId) {
        return new Ticket(vehicle, spot, floorNumber, gateId);
    }
}

// Concrete Factory: BikeParkingFactory
class BikeParkingFactory implements ParkingFactory {
    @Override
    public ParkingSpot createSpot(String id, boolean isHandicap, boolean isReserved, int proximityIndex) {
        return new BikeSpot(id, isHandicap, isReserved, proximityIndex);
    }

    @Override
    public Ticket createTicket(Vehicle vehicle, ParkingSpot spot, int floorNumber, String gateId) {
        return new Ticket(vehicle, spot, floorNumber, gateId);
    }
}

// Concrete Factory: HeavyParkingFactory
class HeavyParkingFactory implements ParkingFactory {
    @Override
    public ParkingSpot createSpot(String id, boolean isHandicap, boolean isReserved, int proximityIndex) {
        return new HeavySpot(id, isHandicap, isReserved, proximityIndex);
    }

    @Override
    public Ticket createTicket(Vehicle vehicle, ParkingSpot spot, int floorNumber, String gateId) {
        return new Ticket(vehicle, spot, floorNumber, gateId);
    }
}

// Factory Provider
class ParkingFactoryProvider {
    public static ParkingFactory getFactory(VehicleType type) {
        switch (type) {
            case CAR:
                return new CarParkingFactory();
            case BIKE:
                return new BikeParkingFactory();
            case HEAVY:
                return new HeavyParkingFactory();
            default:
                throw new IllegalArgumentException("Invalid vehicle type: " + type);
        }
    }
}

// Ticket Class
class Ticket {
    private Vehicle vehicle;
    private ParkingSpot spot;
    private int floorNumber;
    private LocalDateTime entryTime;
    private String ticketId;
    private String entryGateId;

    public Ticket(Vehicle vehicle, ParkingSpot spot, int floorNumber, String entryGateId) {
        this.vehicle = vehicle;
        this.spot = spot;
        this.floorNumber = floorNumber;
        this.entryTime = LocalDateTime.now();
        this.ticketId = "TICKET-" + UUID.randomUUID().toString().substring(0, 8);
        this.entryGateId = entryGateId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSpot getSpot() {
        return spot;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public String getEntryGateId() {
        return entryGateId;
    }
}

// ParkingFloor Class
class ParkingFloor {
    private VehicleType type;
    private List<ParkingSpot> spots;
    private int floorNumber;
    private int capacity;
    private final ReentrantLock lock = new ReentrantLock();

    public ParkingFloor(VehicleType type, int floorNumber, int capacity, int handicapSpots, int reservedSpots) {
        this.type = type;
        this.floorNumber = floorNumber;
        this.capacity = capacity;
        this.spots = new ArrayList<>();
        ParkingFactory factory = ParkingFactoryProvider.getFactory(type);
        int handicapCount = 0;
        int reservedCount = 0;
        for (int i = 1; i <= capacity; i++) {
            boolean isHandicap = handicapCount < handicapSpots && i <= handicapSpots;
            boolean isReserved = !isHandicap && reservedCount < reservedSpots && i <= (handicapSpots + reservedSpots);
            // Proximity index is the spot number (lower is closer to gate)
            spots.add(factory.createSpot(type.name() + "-F" + floorNumber + "-" + i, isHandicap, isReserved, i));
            if (isHandicap) handicapCount++;
            if (isReserved) reservedCount++;
        }
    }

    public VehicleType getType() {
        return type;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public ParkingSpot findAvailableSpot(boolean needsHandicap, boolean needsReserved, boolean hasHandicapPermit, boolean hasReservation, String gateId) {
        lock.lock();
        try {
            if (needsHandicap && !hasHandicapPermit) {
                throw new IllegalArgumentException("Handicap permit required for handicap spot.");
            }
            if (needsReserved && !hasReservation) {
                throw new IllegalArgumentException("Reservation required for reserved spot.");
            }
            ParkingSpot closestSpot = null;
            int minProximity = Integer.MAX_VALUE;
            for (ParkingSpot spot : spots) {
                if (!spot.isOccupied() &&
                    (!needsHandicap || spot.isHandicap()) &&
                    (!needsReserved || spot.isReserved()) &&
                    spot.getProximityIndex() < minProximity) {
                    closestSpot = spot;
                    minProximity = spot.getProximityIndex();
                }
            }
            return closestSpot;
        } finally {
            lock.unlock();
        }
    }

    public int getAvailableSpots(boolean needsHandicap, boolean needsReserved) {
        lock.lock();
        try {
            int count = 0;
            for (ParkingSpot spot : spots) {
                if (!spot.isOccupied() &&
                    (!needsHandicap || spot.isHandicap()) &&
                    (!needsReserved || spot.isReserved())) {
                    count++;
                }
            }
            return count;
        } finally {
            lock.unlock();
        }
    }
}

// DisplayBoard Class
class DisplayBoard {
    private ParkingLot parkingLot;

    public DisplayBoard(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public void showAvailability() {
        System.out.println("----- Parking Availability -----");
        for (Map.Entry<VehicleType, List<ParkingFloor>> entry : parkingLot.getFloors().entrySet()) {
            VehicleType type = entry.getKey();
            List<ParkingFloor> floors = entry.getValue();
            System.out.println("Vehicle Type: " + type);
            for (ParkingFloor floor : floors) {
                System.out.println("  Floor " + floor.getFloorNumber() + ":");
                System.out.println("    Regular Spots: " + floor.getAvailableSpots(false, false));
                System.out.println("    Handicap Spots: " + floor.getAvailableSpots(true, false));
                System.out.println("    Reserved Spots: " + floor.getAvailableSpots(false, true));
            }
        }
        System.out.println("-------------------------------");
    }
}

// ParkingLot Class
class ParkingLot {
    private Map<VehicleType, List<ParkingFloor>> floors;

    public ParkingLot() {
        floors = new HashMap<>();
        floors.put(VehicleType.CAR, new ArrayList<>());
        floors.get(VehicleType.CAR).add(new ParkingFloor(VehicleType.CAR, 1, 50, 5, 5));
        floors.get(VehicleType.CAR).add(new ParkingFloor(VehicleType.CAR, 2, 50, 5, 5));
        floors.put(VehicleType.BIKE, new ArrayList<>());
        floors.get(VehicleType.BIKE).add(new ParkingFloor(VehicleType.BIKE, 1, 100, 10, 10));
        floors.put(VehicleType.HEAVY, new ArrayList<>());
        floors.get(VehicleType.HEAVY).add(new ParkingFloor(VehicleType.HEAVY, 1, 20, 2, 2));
    }

    public Map<VehicleType, List<ParkingFloor>> getFloors() {
        return floors;
    }

    public void addFloor(VehicleType type, int floorNumber, int capacity, int handicapSpots, int reservedSpots) {
        floors.computeIfAbsent(type, k -> new ArrayList<>());
        floors.get(type).add(new ParkingFloor(type, floorNumber, capacity, handicapSpots, reservedSpots));
    }
}

// Enum for Billing Mode
enum BillingMode {
    HOURLY, MINUTE
}

// Payment Interface
interface PaymentProcessor {
    boolean processPayment(double amount);
}

// Concrete Payment Processors
class CashPayment implements PaymentProcessor {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing cash payment of $" + String.format("%.2f", amount));
        return true;
    }
}

class CreditCardPayment implements PaymentProcessor {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing credit card payment of $" + String.format("%.2f", amount));
        return true;
    }
}

class UPIPayment implements PaymentProcessor {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing UPI payment of $" + String.format("%.2f", amount));
        return true;
    }
}

// Payment Class
class Payment {
    private static final Map<VehicleType, Double> HOURLY_RATES = Map.of(
        VehicleType.CAR, 10.0,
        VehicleType.BIKE, 5.0,
        VehicleType.HEAVY, 20.0
    );
    private static final Map<VehicleType, Double> MINUTE_RATES = Map.of(
        VehicleType.CAR, 0.20,
        VehicleType.BIKE, 0.10,
        VehicleType.HEAVY, 0.40
    );

    public double calculateFee(Ticket ticket, LocalDateTime exitTime, BillingMode mode) {
        Duration duration = Duration.between(ticket.getEntryTime(), exitTime);
        VehicleType type = ticket.getVehicle().getType();
        if (mode == BillingMode.HOURLY) {
            long hours = duration.toHours();
            if (duration.toMinutes() % 60 > 0) {
                hours++;
            }
            return hours * HOURLY_RATES.getOrDefault(type, 0.0);
        } else {
            long minutes = duration.toMinutes();
            return minutes * MINUTE_RATES.getOrDefault(type, 0.0);
        }
    }
}

// Gate Abstract Class
abstract class Gate {
    protected ParkingLot parkingLot;
    protected Payment payment;
    protected String gateId;

    public Gate(ParkingLot parkingLot, Payment payment, String gateId) {
        this.parkingLot = parkingLot;
        this.payment = payment;
        this.gateId = gateId;
    }

    public String getGateId() {
        return gateId;
    }
}

// EntryGate
class EntryGate extends Gate {
    public EntryGate(ParkingLot parkingLot, Payment payment, String gateId) {
        super(parkingLot, payment, gateId);
    }

    public Ticket enter(Vehicle vehicle, boolean needsHandicap, boolean needsReserved, boolean hasHandicapPermit, boolean hasReservation) {
        VehicleType type = vehicle.getType();
        List<ParkingFloor> floors = parkingLot.getFloors().get(type);
        if (floors == null || floors.isEmpty()) {
            throw new IllegalArgumentException("No parking floors for vehicle type: " + type);
        }
        // Find floor with most available spots
        ParkingFloor bestFloor = null;
        int maxAvailable = -1;
        for (ParkingFloor floor : floors) {
            int available = floor.getAvailableSpots(needsHandicap, needsReserved);
            if (available > maxAvailable) {
                maxAvailable = available;
                bestFloor = floor;
            }
        }
        if (bestFloor == null || maxAvailable == 0) {
            throw new IllegalStateException("No available spots for " + type + (needsHandicap ? " (handicap)" : "") + (needsReserved ? " (reserved)" : ""));
        }
        ParkingSpot spot = bestFloor.findAvailableSpot(needsHandicap, needsReserved, hasHandicapPermit, hasReservation, gateId);
        if (spot == null) {
            throw new IllegalStateException("No suitable spot found for " + type);
        }
        spot.occupy();
        ParkingFactory factory = ParkingFactoryProvider.getFactory(type);
        Ticket ticket = factory.createTicket(vehicle, spot, bestFloor.getFloorNumber(), gateId);
        System.out.println("Vehicle " + vehicle.getLicensePlate() + " entered via Gate " + gateId + ". Spot: " + spot.getId() + ", Floor: " + bestFloor.getFloorNumber() + ", Ticket: " + ticket.getTicketId());
        return ticket;
    }
}

// ExitGate
class ExitGate extends Gate {
    public ExitGate(ParkingLot parkingLot, Payment payment, String gateId) {
        super(parkingLot, payment, gateId);
    }

    public double exit(Ticket ticket, PaymentProcessor processor, BillingMode mode) {
        if (!ticket.getEntryGateId().equals(gateId)) {
            System.out.println("Warning: Vehicle exiting from different gate (" + gateId + ") than entry gate (" + ticket.getEntryGateId() + ")");
        }
        LocalDateTime exitTime = LocalDateTime.now();
        double fee = payment.calculateFee(ticket, exitTime, mode);
        boolean paymentSuccess = processor.processPayment(fee);
        if (!paymentSuccess) {
            throw new IllegalStateException("Payment failed for ticket: " + ticket.getTicketId());
        }
        ticket.getSpot().free();
        System.out.println("Vehicle " + ticket.getVehicle().getLicensePlate() + " exited via Gate " + gateId + " from spot " + ticket.getSpot().getId() + ", Floor: " + ticket.getFloorNumber() + ". Fee: $" + String.format("%.2f", fee) + " (" + mode + ")");
        return fee;
    }
}

// Client: ParkingLotDemo
public class Main {
    public static void main(String[] args) {
        // Initialize parking lot and dependencies
        ParkingLot parkingLot = new ParkingLot();
        Payment payment = new Payment();
        DisplayBoard displayBoard = new DisplayBoard(parkingLot);
        EntryGate entryGate1 = new EntryGate(parkingLot, payment, "GATE-1");
        EntryGate entryGate2 = new EntryGate(parkingLot, payment, "GATE-2");
        ExitGate exitGate1 = new ExitGate(parkingLot, payment, "EXIT-1");
        ExitGate exitGate2 = new ExitGate(parkingLot, payment, "EXIT-2");

        // Display initial availability
        displayBoard.showAvailability();

        // Scenario 1: Car entering via Gate 1, regular spot, hourly billing
        Vehicle car = new Car("ABC123");
        Ticket carTicket = entryGate1.enter(car, false, false, false, false);
        displayBoard.showAvailability();

        // Simulate 2 hours and 15 minutes parking
        System.out.println("Simulating 2 hours 15 minutes parking for car...");

        // Car exits via Gate 2, hourly billing
        double carFee = exitGate2.exit(carTicket, new CreditCardPayment(), BillingMode.HOURLY);
        System.out.println("Car fee: $" + String.format("%.2f", carFee));
        displayBoard.showAvailability();

        // Scenario 2: Bike entering via Gate 2, handicap spot, minute billing
        Vehicle bike = new Bike("XYZ789");
        try {
            Ticket bikeTicket = entryGate2.enter(bike, true, false, true, false);
            displayBoard.showAvailability();

            // Simulate 1 hour 30 minutes parking
            System.out.println("Simulating 1 hour 30 minutes parking for bike...");

            // Bike exits via Gate 1, minute billing
            double bikeFee = exitGate1.exit(bikeTicket, new UPIPayment(), BillingMode.MINUTE);
            System.out.println("Bike fee: $" + String.format("%.2f", bikeFee));
            displayBoard.showAvailability();
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Scenario 3: Heavy vehicle entering via Gate 1, reserved spot, hourly billing
        Vehicle heavy = new HeavyVehicle("HEV456");
        try {
            Ticket heavyTicket = entryGate1.enter(heavy, false, true, false, true);
            displayBoard.showAvailability();

            // Simulate 3 hours parking
            System.out.println("Simulating 3 hours parking for heavy vehicle...");

            // Heavy vehicle exits via Gate 1, hourly billing
            double heavyFee = exitGate1.exit(heavyTicket, new CashPayment(), BillingMode.HOURLY);
            System.out.println("Heavy vehicle fee: $" + String.format("%.2f", heavyFee));
            displayBoard.showAvailability();
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Scenario 4: Invalid entry (no handicap permit)
        try {
            Vehicle car2 = new Car("DEF456");
            entryGate2.enter(car2, true, false, false, false);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
/*
----- Parking Availability -----
Vehicle Type: BIKE
  Floor 1:
    Regular Spots: 100
    Handicap Spots: 10
    Reserved Spots: 10
Vehicle Type: CAR
  Floor 1:
    Regular Spots: 50
    Handicap Spots: 5
    Reserved Spots: 5
  Floor 2:
    Regular Spots: 50
    Handicap Spots: 5
    Reserved Spots: 5
Vehicle Type: HEAVY
  Floor 1:
    Regular Spots: 20
    Handicap Spots: 2
    Reserved Spots: 2
-------------------------------
Vehicle ABC123 entered via Gate GATE-1. Spot: CAR-F1-1, Floor: 1, Ticket: TICKET-77aa2845
----- Parking Availability -----
Vehicle Type: BIKE
  Floor 1:
    Regular Spots: 100
    Handicap Spots: 10
    Reserved Spots: 10
Vehicle Type: CAR
  Floor 1:
    Regular Spots: 49
    Handicap Spots: 4
    Reserved Spots: 5
  Floor 2:
    Regular Spots: 50
    Handicap Spots: 5
    Reserved Spots: 5
Vehicle Type: HEAVY
  Floor 1:
    Regular Spots: 20
    Handicap Spots: 2
    Reserved Spots: 2
-------------------------------
Simulating 2 hours 15 minutes parking for car...
Warning: Vehicle exiting from different gate (EXIT-2) than entry gate (GATE-1)
Processing credit card payment of $0.00
Vehicle ABC123 exited via Gate EXIT-2 from spot CAR-F1-1, Floor: 1. Fee: $0.00 (HOURLY)
Car fee: $0.00
----- Parking Availability -----
Vehicle Type: BIKE
  Floor 1:
    Regular Spots: 100
    Handicap Spots: 10
    Reserved Spots: 10
Vehicle Type: CAR
  Floor 1:
    Regular Spots: 50
    Handicap Spots: 5
    Reserved Spots: 5
  Floor 2:
    Regular Spots: 50
    Handicap Spots: 5
    Reserved Spots: 5
Vehicle Type: HEAVY
  Floor 1:
    Regular Spots: 20
    Handicap Spots: 2
    Reserved Spots: 2
-------------------------------
Vehicle XYZ789 entered via Gate GATE-2. Spot: BIKE-F1-1, Floor: 1, Ticket: TICKET-6af0070d
----- Parking Availability -----
Vehicle Type: BIKE
  Floor 1:
    Regular Spots: 99
    Handicap Spots: 9
    Reserved Spots: 10
Vehicle Type: CAR
  Floor 1:
    Regular Spots: 50
    Handicap Spots: 5
    Reserved Spots: 5
  Floor 2:
    Regular Spots: 50
    Handicap Spots: 5
    Reserved Spots: 5
Vehicle Type: HEAVY
  Floor 1:
    Regular Spots: 20
    Handicap Spots: 2
    Reserved Spots: 2
-------------------------------
Simulating 1 hour 30 minutes parking for bike...
Warning: Vehicle exiting from different gate (EXIT-1) than entry gate (GATE-2)
Processing UPI payment of $0.00
Vehicle XYZ789 exited via Gate EXIT-1 from spot BIKE-F1-1, Floor: 1. Fee: $0.00 (MINUTE)
Bike fee: $0.00
----- Parking Availability -----
Vehicle Type: BIKE
  Floor 1:
    Regular Spots: 100
    Handicap Spots: 10
    Reserved Spots: 10
Vehicle Type: CAR
  Floor 1:
    Regular Spots: 50
    Handicap Spots: 5
    Reserved Spots: 5
  Floor 2:
    Regular Spots: 50
    Handicap Spots: 5
    Reserved Spots: 5
Vehicle Type: HEAVY
  Floor 1:
    Regular Spots: 20
    Handicap Spots: 2
    Reserved Spots: 2
-------------------------------
Vehicle HEV456 entered via Gate GATE-1. Spot: HEAVY-F1-3, Floor: 1, Ticket: TICKET-af775983
----- Parking Availability -----
Vehicle Type: BIKE
  Floor 1:
    Regular Spots: 100
    Handicap Spots: 10
    Reserved Spots: 10
Vehicle Type: CAR
  Floor 1:
    Regular Spots: 50
    Handicap Spots: 5
    Reserved Spots: 5
  Floor 2:
    Regular Spots: 50
    Handicap Spots: 5
    Reserved Spots: 5
Vehicle Type: HEAVY
  Floor 1:
    Regular Spots: 19
    Handicap Spots: 2
    Reserved Spots: 1
-------------------------------
Simulating 3 hours parking for heavy vehicle...
Warning: Vehicle exiting from different gate (EXIT-1) than entry gate (GATE-1)
Processing cash payment of $0.00
Vehicle HEV456 exited via Gate EXIT-1 from spot HEAVY-F1-3, Floor: 1. Fee: $0.00 (HOURLY)
Heavy vehicle fee: $0.00
----- Parking Availability -----
Vehicle Type: BIKE
  Floor 1:
    Regular Spots: 100
    Handicap Spots: 10
    Reserved Spots: 10
Vehicle Type: CAR
  Floor 1:
    Regular Spots: 50
    Handicap Spots: 5
    Reserved Spots: 5
  Floor 2:
    Regular Spots: 50
    Handicap Spots: 5
    Reserved Spots: 5
Vehicle Type: HEAVY
  Floor 1:
    Regular Spots: 20
    Handicap Spots: 2
    Reserved Spots: 2
-------------------------------
ERROR!
Error: Handicap permit required for handicap spot.

=== Code Execution Successful ===

*/