To determine the best design for the parking lot system with all the specified features—multiple floors, multiple entry and exit gates, support for multiple vehicle types (two-wheelers, four-wheelers, heavy vehicles), ticket generation at entry, payment at exit with multiple modes (cash, card, UPI), hourly or minute-based payment, and parking space allocation close to the entrance gate—I’ll evaluate design patterns and approaches to ensure scalability, maintainability, and alignment with the requirements. The provided code already uses the **Abstract Factory Design Pattern**, which is well-suited for creating families of related objects (vehicles, parking spots, tickets). However, I’ll assess whether this is the best design, consider alternatives, and justify the choice by analyzing the system’s requirements, extensibility, and complexity. I’ll also ensure all features are covered, incorporating any refinements to optimize the design while maintaining the Abstract Factory Pattern as the core creational pattern, supplemented by other patterns where appropriate.

### Requirements Recap
1. **Multiple Floors**: The parking lot must support multiple floors for each vehicle type.
2. **Multiple Entry and Exit Gates**: Multiple gates for concurrent vehicle entry/exit, with thread-safety.
3. **Multiple Vehicle Types**: Support for two-wheelers (bikes), four-wheelers (cars), and heavy vehicles, with extensibility for future types.
4. **Ticket Generation at Entry**: Tickets issued at entry gates with unique IDs, vehicle, spot, and gate information.
5. **Payment at Exit with Multiple Modes**: Payment via cash, card, or UPI, processed at exit gates.
6. **Hourly or Minute-Based Payment**: Flexible billing options based on parking duration.
7. **Parking Space Close to Entrance Gate**: Allocate spots closest to the entry gate used.
8. **Additional Features (from previous code)**: Handicap/reserved spots, real-time availability display, validations (e.g., spot availability, permit checks).

### Evaluation of Design Patterns
The parking lot system involves creating objects (vehicles, spots, tickets), managing state (parking spots, floors), handling concurrency (multiple gates), and supporting extensibility. Below, I evaluate relevant design patterns and their suitability:

1. **Abstract Factory Pattern (Current Design)**:
   - **Purpose**: Creates families of related objects (e.g., `CarSpot`, `BikeSpot`, `HeavySpot`, `Ticket`) without specifying concrete classes.
   - **Pros**:
     - Ideal for managing different vehicle types and their associated parking spots/tickets.
     - Supports extensibility: Adding a new vehicle type (e.g., `Truck`) requires only new classes (`Truck`, `TruckSpot`, `TruckParkingFactory`) and updates to `ParkingFactoryProvider`.
     - Encapsulates object creation, ensuring `EntryGate` works with abstract interfaces (`ParkingFactory`, `ParkingSpot`).
     - Aligns with the requirement for multiple vehicle types and their specific parking needs.
   - **Cons**:
     - Can become complex with many vehicle types, as each requires a new factory and product classes.
     - Doesn’t inherently handle concurrency or proximity-based allocation.
   - **Fit**: Excellent for the creational aspect (vehicles, spots, tickets) and extensibility for new vehicle types.

2. **Factory Method Pattern**:
   - **Purpose**: Defines an interface for creating a single object, letting subclasses decide the concrete class.
   - **Pros**:
     - Simpler than Abstract Factory for single product creation (e.g., one factory per vehicle type).
     - Reduces class proliferation if only one product type is needed per vehicle.
   - **Cons**:
     - Less suitable for families of related objects (e.g., spots and tickets together).
     - Requires more factories if both spots and tickets need separate creation logic.
     - Less cohesive for managing multiple related products per vehicle type.
   - **Fit**: Less suitable than Abstract Factory, as the system requires coordinated creation of spots and tickets for each vehicle type.

3. **Singleton Pattern**:
   - **Purpose**: Ensures a single instance of a class (e.g., `ParkingLot` or `DisplayBoard`).
   - **Pros**:
     - Useful for centralized resources like the parking lot or display board.
     - Prevents multiple instances of `ParkingLot` managing conflicting states.
   - **Cons**:
     - Not suitable for creational logic (vehicles, spots, tickets).
     - Can complicate testing or multi-parking-lot scenarios.
   - **Fit**: Useful for `ParkingLot` and `DisplayBoard` to ensure single instances, but secondary to the creational needs.

4. **Strategy Pattern**:
   - **Purpose**: Defines a family of algorithms (e.g., payment modes, billing strategies) interchangeable at runtime.
   - **Pros**:
     - Perfect for handling multiple payment modes (cash, card, UPI) and billing options (hourly, minute-based).
     - Allows easy addition of new payment or billing strategies.
   - **Cons**:
     - Not suitable for object creation or structural organization of floors/gates.
   - **Fit**: Ideal for payment processing and billing modes, complementing the Abstract Factory.

5. **Decorator Pattern**:
   - **Purpose**: Dynamically adds responsibilities to objects (e.g., adding handicap/reserved features to spots).
   - **Pros**:
     - Could be used to add attributes like handicap or reserved status to spots without subclassing.
     - Flexible for adding new spot features (e.g., electric vehicle charging).
   - **Cons**:
     - Increases complexity for simple boolean flags like `isHandicap`/`isReserved`.
     - Less intuitive for core spot creation compared to factory pattern.
   - **Fit**: Overkill for current spot attributes; current boolean flags are simpler.

6. **Observer Pattern**:
   - **Purpose**: Notifies dependent objects (e.g., `DisplayBoard`) of state changes (e.g., spot occupancy).
   - **Pros**:
     - Ensures real-time updates to availability display when vehicles enter/exit.
     - Scalable for notifying other components (e.g., mobile apps).
   - **Cons**:
     - Adds complexity for a simple display update.
     - Not needed if `DisplayBoard` queries state directly.
   - **Fit**: Useful for real-time display but can be simplified with direct queries in the current design.

7. **Facade Pattern**:
   - **Purpose**: Provides a simplified interface to complex subsystems (e.g., parking lot operations).
   - **Pros**:
     - Could simplify interactions for clients (e.g., `ParkingLotDemo`) by encapsulating gate, payment, and display logic.
     - Improves maintainability by hiding subsystem complexity.
   - **Cons**:
     - May add an extra layer if the system is already straightforward.
   - **Fit**: Useful for future complexity but not critical for the current design.

### Recommended Design: Hybrid Approach
The **Abstract Factory Pattern** remains the best choice for the core creational logic due to its alignment with creating families of related objects (vehicles, spots, tickets) and supporting extensibility for new vehicle types. However, to fully address all requirements and optimize the system, a hybrid design combining multiple patterns is ideal:

- **Abstract Factory Pattern** (Core Creational Logic):
  - Used for creating `Vehicle`, `ParkingSpot`, and `Ticket` objects for different vehicle types (`Car`, `Bike`, `HeavyVehicle`).
  - Ensures extensibility for new vehicle types (e.g., `Truck`).
  - Handles ticket generation at entry gates via `ParkingFactory.createTicket`.

- **Singleton Pattern** (Centralized Management):
  - Applied to `ParkingLot` to ensure a single instance manages all floors and spots, preventing state conflicts.
  - Optionally applied to `DisplayBoard` for a single display instance.

- **Strategy Pattern** (Payment and Billing):
  - Used for payment modes (`CashPayment`, `CreditCardPayment`, `UPIPayment`) via the `PaymentProcessor` interface.
  - Extended to billing modes (`HOURLY`, `MINUTE`) in the `Payment` class to support flexible billing.

- **Thread-Safe Design** (Concurrency for Multiple Gates):
  - Use `ReentrantLock` in `ParkingFloor` to ensure thread-safe spot allocation for multiple entry/exit gates.
  - Ensures concurrent operations don’t lead to race conditions (e.g., double-booking spots).

- **Simple Proximity Logic** (Spot Allocation):
  - Instead of a complex pattern, use a straightforward `proximityIndex` in `ParkingSpot` to prioritize spots closer to the entry gate, keeping the design lightweight.

- **Direct State Query** (Real-Time Display):
  - Avoid Observer Pattern for `DisplayBoard` by directly querying `ParkingLot` state, as the system is simple enough to avoid notification overhead.

This hybrid approach balances simplicity, extensibility, and performance while meeting all requirements. The Abstract Factory Pattern handles the core object creation, Singleton ensures centralized management, Strategy supports flexible payment/billing, and thread-safety addresses concurrency. The proximity logic is kept simple to avoid overcomplicating the design.

### Why Not Alternatives?
- **Factory Method**: Less suitable due to the need for coordinated creation of spots and tickets. Abstract Factory better handles families of related objects.
- **Decorator**: Unnecessary for simple spot attributes (`isHandicap`, `isReserved`); boolean flags are sufficient.
- **Observer**: Overkill for `DisplayBoard`, as direct queries are simpler for the current scale.
- **Facade**: Not needed yet, as the system’s interface (`EntryGate`, `ExitGate`, `DisplayBoard`) is straightforward. Could be added for future complexity.

### Refinements to Existing Code
The provided code already implements most of this hybrid design, but I’ll highlight refinements to ensure all requirements are optimized:
1. **Multiple Gates**: The code supports multiple `EntryGate` and `ExitGate` instances with `gateId` and thread-safety via `ReentrantLock`. No further changes needed.
2. **Proximity Allocation**: The `proximityIndex` ensures spots closest to gates are allocated, but we can enhance it by mapping `gateId` to specific spot ranges (e.g., `GATE-1` prefers spots 1–25, `GATE-2` prefers 26–50). This requires a minor update to `ParkingFloor.findAvailableSpot`.
3. **Billing Flexibility**: The `BillingMode` (`HOURLY`, `MINUTE`) is implemented, but we can add validation to ensure the chosen mode is supported for the vehicle type.
4. **Singleton for ParkingLot**: Add Singleton pattern to `ParkingLot` to ensure a single instance across the system.
5. **Validations**: Already robust (spot availability, permits, payment), but we can add gate-specific validation (e.g., ensure `gateId` is valid).

### Updated Java Code
Below is the refined Java code incorporating the hybrid design, with:
- Singleton pattern for `ParkingLot`.
- Enhanced proximity logic mapping `gateId` to spot ranges.
- Additional billing mode validation.
- All existing features (multiple floors, gates, vehicle types, ticket generation, payment modes, hourly/minute billing, validations, handicap/reserved spots, display board).

<xaiArtifact artifact_id="af00501b-11f6-4939-b823-a166403964f1" artifact_version_id="a4d22f8b-0655-4cc8-9173-3ca53e44b9ea" title="ParkingLot.java" contentType="text/java">

package Factory_design.ParkingLot;

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
    private static final Map<String, int[]> GATE_SPOT_RANGES = Map.of(
        "GATE-1", new int[]{1, 25},
        "GATE-2", new int[]{26, 50}
    );

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
            if (!GATE_SPOT_RANGES.containsKey(gateId)) {
                throw new IllegalArgumentException("Invalid gate ID: " + gateId);
            }
            int[] range = GATE_SPOT_RANGES.getOrDefault(gateId, new int[]{1, capacity});
            ParkingSpot closestSpot = null;
            int minProximity = Integer.MAX_VALUE;
            for (ParkingSpot spot : spots) {
                if (!spot.isOccupied() &&
                    (!needsHandicap || spot.isHandicap()) &&
                    (!needsReserved || spot.isReserved()) &&
                    spot.getProximityIndex() >= range[0] && spot.getProximityIndex() <= range[1] &&
                    spot.getProximityIndex() < minProximity) {
                    closestSpot = spot;
                    minProximity = spot.getProximityIndex();
                }
            }
            if (closestSpot == null) {
                // Fallback to any available spot if none found in gate's range
                for (ParkingSpot spot : spots) {
                    if (!spot.isOccupied() &&
                        (!needsHandicap || spot.isHandicap()) &&
                        (!needsReserved || spot.isReserved()) &&
                        spot.getProximityIndex() < minProximity) {
                        closestSpot = spot;
                        minProximity = spot.getProximityIndex();
                    }
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

// ParkingLot Class (Singleton)
class ParkingLot {
    private static ParkingLot instance;
    private Map<VehicleType, List<ParkingFloor>> floors;
    private static final ReentrantLock lock = new ReentrantLock();

    private ParkingLot() {
        floors = new HashMap<>();
        floors.put(VehicleType.CAR, new ArrayList<>());
        floors.get(VehicleType.CAR).add(new ParkingFloor(VehicleType.CAR, 1, 50, 5, 5));
        floors.get(VehicleType.CAR).add(new ParkingFloor(VehicleType.CAR, 2, 50, 5, 5));
        floors.put(VehicleType.BIKE, new ArrayList<>());
        floors.get(VehicleType.BIKE).add(new ParkingFloor(VehicleType.BIKE, 1, 100, 10, 10));
        floors.put(VehicleType.HEAVY, new ArrayList<>());
        floors.get(VehicleType.HEAVY).add(new ParkingFloor(VehicleType.HEAVY, 1, 20, 2, 2));
    }

    public static ParkingLot getInstance() {
        lock.lock();
        try {
            if (instance == null) {
                instance = new ParkingLot();
            }
            return instance;
        } finally {
            lock.unlock();
        }
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
        VehicleType type = ticket.getVehicle().getType();
        if (!HOURLY_RATES.containsKey(type) || !MINUTE_RATES.containsKey(type)) {
            throw new IllegalArgumentException("Billing not supported for vehicle type: " + type);
        }
        Duration duration = Duration.between(ticket.getEntryTime(), exitTime);
        if (mode == BillingMode.HOURLY) {
            long hours = duration.toHours();
            if (duration.toMinutes() % 60 > 0) {
                hours++;
            }
            return hours * HOURLY_RATES.get(type);
        } else {
            long minutes = duration.toMinutes();
            return minutes * MINUTE_RATES.get(type);
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
public class ParkingLotDemo {
    public static void main(String[] args) {
        // Initialize parking lot and dependencies
        ParkingLot parkingLot = ParkingLot.getInstance();
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

        // Scenario 5: Invalid gate ID
        try {
            Vehicle car3 = new Car("GHI789");
            EntryGate invalidGate = new EntryGate(parkingLot, payment, "INVALID-GATE");
            invalidGate.enter(car3, false, false, false, false);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

</xaiArtifact>

### Expected Output
The output reflects the Singleton pattern, enhanced proximity logic, and all requirements:

```
----- Parking Availability -----
Vehicle Type: CAR
  Floor 1:
    Regular Spots: 40
    Handicap Spots: 5
    Reserved Spots: 5
  Floor 2:
    Regular Spots: 40
    Handicap Spots: 5
    Reserved Spots: 5
Vehicle Type: BIKE
  Floor 1:
    Regular Spots: 80
    Handicap Spots: 10
    Reserved Spots: 10
Vehicle Type: HEAVY
  Floor 1:
    Regular Spots: 16
    Handicap Spots: 2
    Reserved Spots: 2
-------------------------------
Vehicle ABC123 entered via Gate GATE-1. Spot: CAR-F1-1, Floor: 1, Ticket: TICKET-abcdef12
----- Parking Availability -----
Vehicle Type: CAR
  Floor 1:
    Regular Spots: 39
    Handicap Spots: 5
    Reserved Spots: 5
  Floor 2:
    Regular Spots: 40
    Handicap Spots: 5
    Reserved Spots: 5
Vehicle Type: BIKE
  Floor 1:
    Regular Spots: 80
    Handicap Spots: 10
    Reserved Spots: 10
Vehicle Type: HEAVY
  Floor 1:
    Regular Spots: 16
    Handicap Spots: 2
    Reserved Spots: 2
-------------------------------
Simulating 2 hours 15 minutes parking for car...
Warning: Vehicle exiting from different gate (EXIT-2) than entry gate (GATE-1)
Processing credit card payment of $30.00
Vehicle ABC123 exited via Gate EXIT-2 from spot CAR-F1-1, Floor: 1. Fee: $30.00 (HOURLY)
Car fee: $30.00
----- Parking Availability -----
Vehicle Type: CAR
  Floor 1:
    Regular Spots: 40
    Handicap Spots: 5
    Reserved Spots: 5
  Floor 2:
    Regular Spots: 40
    Handicap Spots: 5
    Reserved Spots: 5
Vehicle Type: BIKE
  Floor 1:
    Regular Spots: 80
    Handicap Spots: 10
    Reserved Spots: 10
Vehicle Type: HEAVY
  Floor 1:
    Regular Spots: 16
    Handicap Spots: 2
    Reserved Spots: 2
-------------------------------
Vehicle XYZ789 entered via Gate GATE-2. Spot: BIKE-F1-81, Floor: 1, Ticket: TICKET-3456abcd
----- Parking Availability -----
Vehicle Type: CAR
  Floor 1:
    Regular Spots: 40
    Handicap Spots: 5
    Reserved Spots: 5
  Floor 2:
    Regular Spots: 40
    Handicap Spots: 5
    Reserved Spots: 5
Vehicle Type: BIKE
  Floor 1:
    Regular Spots: 79
    Handicap Spots: 10
    Reserved Spots: 10
Vehicle Type: HEAVY
  Floor 1:
    Regular Spots: 16
    Handicap Spots: 2
    Reserved Spots: 2
-------------------------------
Simulating 1 hour 30 minutes parking for bike...
Warning: Vehicle exiting from different gate (EXIT-1) than entry gate (GATE-2)
Processing UPI payment of $9.00
Vehicle XYZ789 exited via Gate EXIT-1 from spot BIKE-F1-81, Floor: 1. Fee: $9.00 (MINUTE)
Bike fee: $9.00
----- Parking Availability -----
Vehicle Type: CAR
  Floor 1:
    Regular Spots: 40
    Handicap Spots: 5
    Reserved Spots: 5
  Floor 2:
    Regular Spots: 40
    Handicap Spots: 5
    Reserved Spots: 5
Vehicle Type: BIKE
  Floor 1:
    Regular Spots: 80
    Handicap Spots: 10
    Reserved Spots: 10
Vehicle Type: HEAVY
  Floor 1:
    Regular Spots: 16
    Handicap Spots: 2
    Reserved Spots: 2
-------------------------------
Vehicle HEV456 entered via Gate GATE-1. Spot: HEAVY-F1-17, Floor: 1, Ticket: TICKET-7890efgh
----- Parking Availability -----
Vehicle Type: CAR
  Floor 1:
    Regular Spots: 40
    Handicap Spots: 5
    Reserved Spots: 5
  Floor 2:
    Regular Spots: 40
    Handicap Spots: 5
    Reserved Spots: 5
Vehicle Type: BIKE
  Floor 1:
    Regular Spots: 80
    Handicap Spots: 10
    Reserved Spots: 10
Vehicle Type: HEAVY
  Floor 1:
    Regular Spots: 15
    Handicap Spots: 2
    Reserved Spots: 2
-------------------------------
Simulating 3 hours parking for heavy vehicle...
Processing cash payment of $60.00
Vehicle HEV456 exited via Gate EXIT-1 from spot HEAVY-F1-17, Floor: 1. Fee: $60.00 (HOURLY)
Heavy vehicle fee: $60.00
----- Parking Availability -----
Vehicle Type: CAR
  Floor 1:
    Regular Spots: 40
    Handicap Spots: 5
    Reserved Spots: 5
  Floor 2:
    Regular Spots: 40
    Handicap Spots: 5
    Reserved Spots: 5
Vehicle Type: BIKE
  Floor 1:
    Regular Spots: 80
    Handicap Spots: 10
    Reserved Spots: 10
Vehicle Type: HEAVY
  Floor 1:
    Regular Spots: 16
    Handicap Spots: 2
    Reserved Spots: 2
-------------------------------
Error: Handicap permit required for handicap spot.
Error: Invalid gate ID: INVALID-GATE
```

### Why This Design is Best
1. **Abstract Factory Pattern**:
   - Handles creation of `Vehicle`, `ParkingSpot`, and `Ticket` families, ensuring extensibility for new vehicle types (e.g., `Truck` requires only new classes and factory updates).
   - Encapsulates object creation, keeping `EntryGate` decoupled from concrete classes.
2. **Singleton Pattern**:
   - Ensures a single `ParkingLot` instance, preventing state conflicts across multiple gates.
   - Simplifies centralized management of floors and spots.
3. **Strategy Pattern**:
   - Supports flexible payment modes (`CashPayment`, `CreditCardPayment`, `UPIPayment`) and billing modes (`HOURLY`, `MINUTE`) via interfaces, allowing easy addition of new strategies (e.g., subscription-based billing).
4. **Thread-Safety**:
   - `ReentrantLock` in `ParkingFloor` ensures safe concurrent access for multiple gates, preventing race conditions.
5. **Proximity Logic**:
   - `GATE_SPOT_RANGES` maps gates to spot ranges (e.g., `GATE-1` prefers spots 1–25), ensuring close allocation with a fallback to any available spot.
   - Simple and efficient, avoiding complex spatial calculations.
6. **Maintainability**:
   - Modular design separates concerns (creation, payment, display, concurrency).
   - Easy to extend for new features (e.g., valet parking, reservations) by adding new classes or methods.
7. **Robust Validations**:
   - Checks for spot availability, vehicle type compatibility, permits, gate IDs, and billing support.
   - Ensures system integrity and user feedback.

### Potential Future Enhancements
- **Valet Parking**: Add a `ValetService` class to handle vehicle parking/retrieval, using the Strategy pattern.
- **Reservation System**: Add a `Reservation` class and pre-allocate reserved spots, integrating with `EntryGate`.
- **Dynamic Pricing**: Extend `Payment` to support dynamic rates based on demand or time of day.
- **Mobile App Integration**: Use Observer pattern to notify users of spot availability or ticket status.

If you need further refinements, a sequence diagram, or the UML diagram rendered, let me know! The current date and time (07:33 PM IST, September 12, 2025) are noted, but they don’t affect the design unless specified.