package Factory_design.Restaurant;

// package Factory_design.Restaurant;

import java.util.*;

// Abstract Product: Interface for all dishes
interface Dish {
    String getName();
    double getPrice();
    void prepare();
}

// Interface for customizable dishes
interface Customizable {
    void customize(Map<String, String> options);
    double getCustomizationPrice();
}

// Concrete Product: VegBurger
class VegBurger implements Dish, Customizable {
    private String name;
    private double price;
    private String[] ingredients;
    private double customizationPrice;

    public VegBurger() {
        name = "Veggie Delight Burger";
        price = 5.99;
        ingredients = new String[]{"Lettuce", "Tomato", "Veggie Patty", "Cucumber", "Mayo"};
        customizationPrice = 0.0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price + customizationPrice;
    }

    @Override
    public void prepare() {
        System.out.println("Preparing " + name);
        System.out.println("Ingredients: ");
        for (String ingredient : ingredients) {
            System.out.println("- " + ingredient);
        }
    }

    @Override
    public void customize(Map<String, String> options) {
        if (options.containsKey("extraCheese")) {
            customizationPrice += 1.0;
            ingredients = Arrays.copyOf(ingredients, ingredients.length + 1);
            ingredients[ingredients.length - 1] = "Extra Cheese";
        }
    }

    @Override
    public double getCustomizationPrice() {
        return customizationPrice;
    }
}

// Concrete Product: CrushBurger
class CrushBurger implements Dish, Customizable {
    private String name;
    private double price;
    private String[] ingredients;
    private double customizationPrice;

    public CrushBurger() {
        name = "Crush Burger";
        price = 6.49;
        ingredients = new String[]{"Spinach", "Avocado", "Mushroom Patty", "Onion", "Vegan Mayo"};
        customizationPrice = 0.0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price + customizationPrice;
    }

    @Override
    public void prepare() {
        System.out.println("Preparing " + name);
        System.out.println("Ingredients: ");
        for (String ingredient : ingredients) {
            System.out.println("- " + ingredient);
        }
    }

    @Override
    public void customize(Map<String, String> options) {
        if (options.containsKey("extraAvocado")) {
            customizationPrice += 1.5;
            ingredients = Arrays.copyOf(ingredients, ingredients.length + 1);
            ingredients[ingredients.length - 1] = "Extra Avocado";
        }
    }

    @Override
    public double getCustomizationPrice() {
        return customizationPrice;
    }
}

// Concrete Product: PaneerBurger
class PaneerBurger implements Dish, Customizable {
    private String name;
    private double price;
    private String[] ingredients;
    private double customizationPrice;

    public PaneerBurger() {
        name = "Paneer Burger";
        price = 6.99;
        ingredients = new String[]{"Paneer Patty", "Lettuce", "Tomato", "Cucumber", "Mint Chutney"};
        customizationPrice = 0.0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price + customizationPrice;
    }

    @Override
    public void prepare() {
        System.out.println("Preparing " + name);
        System.out.println("Ingredients: ");
        for (String ingredient : ingredients) {
            System.out.println("- " + ingredient);
        }
    }

    @Override
    public void customize(Map<String, String> options) {
        if (options.containsKey("extraPaneer")) {
            customizationPrice += 1.2;
            ingredients = Arrays.copyOf(ingredients, ingredients.length + 1);
            ingredients[ingredients.length - 1] = "Extra Paneer";
        }
    }

    @Override
    public double getCustomizationPrice() {
        return customizationPrice;
    }
}

// Concrete Product: NonVegBurger
class NonVegBurger implements Dish, Customizable {
    private String name;
    private double price;
    private String[] ingredients;
    private double customizationPrice;

    public NonVegBurger() {
        name = "Chicken Supreme Burger";
        price = 7.99;
        ingredients = new String[]{"Lettuce", "Tomato", "Chicken Patty", "Cheese", "Spicy Sauce"};
        customizationPrice = 0.0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price + customizationPrice;
    }

    @Override
    public void prepare() {
        System.out.println("Preparing " + name);
        System.out.println("Ingredients: ");
        for (String ingredient : ingredients) {
            System.out.println("- " + ingredient);
        }
    }

    @Override
    public void customize(Map<String, String> options) {
        if (options.containsKey("extraChicken")) {
            customizationPrice += 2.0;
            ingredients = Arrays.copyOf(ingredients, ingredients.length + 1);
            ingredients[ingredients.length - 1] = "Extra Chicken";
        }
    }

    @Override
    public double getCustomizationPrice() {
        return customizationPrice;
    }
}

// Concrete Product: ColaDrink
class ColaDrink implements Dish, Customizable {
    private String name;
    private double price;
    private String size;
    private double customizationPrice;

    public ColaDrink() {
        name = "Cola";
        price = 2.49;
        size = "Medium";
        customizationPrice = 0.0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price + customizationPrice;
    }

    @Override
    public void prepare() {
        System.out.println("Preparing " + name);
        System.out.println("Size: " + size);
        System.out.println("Serving cold with ice.");
    }

    @Override
    public void customize(Map<String, String> options) {
        if (options.containsKey("size") && options.get("size").equals("large")) {
            customizationPrice += 0.5;
            size = "Large";
        }
    }

    @Override
    public double getCustomizationPrice() {
        return customizationPrice;
    }
}

// Concrete Product: Lemonade
class Lemonade implements Dish, Customizable {
    private String name;
    private double price;
    private String size;
    private double customizationPrice;

    public Lemonade() {
        name = "Fresh Lemonade";
        price = 2.99;
        size = "Medium";
        customizationPrice = 0.0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price + customizationPrice;
    }

    @Override
    public void prepare() {
        System.out.println("Preparing " + name);
        System.out.println("Size: " + size);
        System.out.println("Serving chilled with lemon slices.");
    }

    @Override
    public void customize(Map<String, String> options) {
        if (options.containsKey("size") && options.get("size").equals("large")) {
            customizationPrice += 0.5;
            size = "Large";
        }
    }

    @Override
    public double getCustomizationPrice() {
        return customizationPrice;
    }
}

// Concrete Product: IceCream
class IceCream implements Dish, Customizable {
    private String name;
    private double price;
    private String flavor;
    private double customizationPrice;

    public IceCream() {
        name = "Vanilla Ice Cream";
        price = 3.99;
        flavor = "Vanilla";
        customizationPrice = 0.0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price + customizationPrice;
    }

    @Override
    public void prepare() {
        System.out.println("Preparing " + name);
        System.out.println("Flavor: " + flavor);
        System.out.println("Serving chilled in a bowl.");
    }

    @Override
    public void customize(Map<String, String> options) {
        if (options.containsKey("flavor")) {
            flavor = options.get("flavor");
            customizationPrice += flavor.equals("Chocolate") ? 0.5 : 0.0;
        }
    }

    @Override
    public double getCustomizationPrice() {
        return customizationPrice;
    }
}

// Concrete Product: Cheesecake
class Cheesecake implements Dish, Customizable {
    private String name;
    private double price;
    private String topping;
    private double customizationPrice;

    public Cheesecake() {
        name = "New York Cheesecake";
        price = 4.99;
        topping = "Strawberry";
        customizationPrice = 0.0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price + customizationPrice;
    }

    @Override
    public void prepare() {
        System.out.println("Preparing " + name);
        System.out.println("Topping: " + topping);
        System.out.println("Serving chilled on a plate.");
    }

    @Override
    public void customize(Map<String, String> options) {
        if (options.containsKey("topping")) {
            topping = options.get("topping");
            customizationPrice += topping.equals("Blueberry") ? 0.7 : 0.0;
        }
    }

    @Override
    public double getCustomizationPrice() {
        return customizationPrice;
    }
}

// Abstract Factory: Interface for creating dishes
interface DishFactory {
    Dish createDish(String type);
}

// Concrete Factory: Creates burger family
class BurgerFactory implements DishFactory {
    @Override
    public Dish createDish(String type) {
        switch (type.toLowerCase()) {
            case "veg":
                return new VegBurger();
            case "crush":
                return new CrushBurger();
            case "paneer":
                return new PaneerBurger();
            case "nonveg":
                return new NonVegBurger();
            default:
                throw new IllegalArgumentException("Invalid burger type: " + type);
        }
    }
}

// Concrete Factory: Creates drink family
class DrinkFactory implements DishFactory {
    @Override
    public Dish createDish(String type) {
        switch (type.toLowerCase()) {
            case "cola":
                return new ColaDrink();
            case "lemonade":
                return new Lemonade();
            default:
                throw new IllegalArgumentException("Invalid drink type: " + type);
        }
    }
}

// Concrete Factory: Creates dessert family
class DessertFactory implements DishFactory {
    @Override
    public Dish createDish(String type) {
        switch (type.toLowerCase()) {
            case "icecream":
                return new IceCream();
            case "cheesecake":
                return new Cheesecake();
            default:
                throw new IllegalArgumentException("Invalid dessert type: " + type);
        }
    }
}

// Factory Provider: Returns appropriate factory
class FactoryProvider {
    public static DishFactory getFactory(String category) {
        switch (category.toLowerCase()) {
            case "burger":
                return new BurgerFactory();
            case "drink":
                return new DrinkFactory();
            case "dessert":
                return new DessertFactory();
            default:
                throw new IllegalArgumentException("Invalid dish category: " + category);
        }
    }
}

// Order Status Enum
enum OrderStatus {
    PLACED, PREPARING, SERVED, COMPLETED
}

// Customer Class
class Customer {
    private String name;
    private String contact;
    private int loyaltyPoints;

    public Customer(String name, String contact) {
        this.name = name;
        this.contact = contact;
        this.loyaltyPoints = 0;
    }

    public void addLoyaltyPoints(int points) {
        loyaltyPoints += points;
        System.out.println("Added " + points + " loyalty points to " + name + ". Total: " + loyaltyPoints);
    }

    public double getLoyaltyDiscount() {
        return loyaltyPoints >= 100 ? 0.1 : 0.0; // 10% discount for 100+ points
    }

    public String getName() {
        return name;
    }
}

// Payment Processor Interface
interface PaymentProcessor {
    boolean processPayment(double amount);
}

// Concrete Payment Processor: CashPayment
class CashPayment implements PaymentProcessor {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing cash payment of $" + String.format("%.2f", amount));
        return true;
    }
}

// Concrete Payment Processor: CreditCardPayment
class CreditCardPayment implements PaymentProcessor {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing credit card payment of $" + String.format("%.2f", amount));
        return true;
    }
}

// Concrete Payment Processor: OnlinePayment
class OnlinePayment implements PaymentProcessor {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing online payment of $" + String.format("%.2f", amount));
        return true;
    }
}

// Menu Class
class Menu {
    private Map<String, List<String>> categories;

    public Menu() {
        categories = new HashMap<>();
        categories.put("burger", Arrays.asList("veg", "crush", "paneer", "nonveg"));
        categories.put("drink", Arrays.asList("cola", "lemonade"));
        categories.put("dessert", Arrays.asList("icecream", "cheesecake"));
    }

    public void displayMenu() {
        System.out.println("----- Restaurant Menu -----");
        for (Map.Entry<String, List<String>> entry : categories.entrySet()) {
            System.out.println("Category: " + entry.getKey());
            for (String type : entry.getValue()) {
                System.out.println("- " + type);
            }
        }
        System.out.println("---------------------------");
    }

    public boolean isValidDish(String category, String type) {
        return categories.containsKey(category.toLowerCase()) && categories.get(category.toLowerCase()).contains(type.toLowerCase());
    }
}

// OrderItem Class: Represents a dish with quantity and customizations
class OrderItem {
    private Dish dish;
    private int quantity;
    private Map<String, String> customizations;

    public OrderItem(Dish dish, int quantity, Map<String, String> customizations) {
        this.dish = dish;
        this.quantity = quantity;
        this.customizations = customizations != null ? new HashMap<>(customizations) : new HashMap<>();
        if (dish instanceof Customizable && customizations != null) {
            ((Customizable)dish).customize(customizations);
        }
    }

    public Dish getDish() {
        return dish;
    }

    public int getQuantity() {
        return quantity;
    }

    public Map<String, String> getCustomizations() {
        return customizations;
    }

    public double getTotalPrice() {
        return dish.getPrice() * quantity;
    }
}

// Order Class: Manages order with token number
class Order {
    private List<OrderItem> items = new ArrayList<>();
    private double taxRate = 0.10; // 10% tax
    private double discount = 0.0; // Default no discount
    private OrderStatus status = OrderStatus.PLACED;
    private Customer customer;
    private int tokenNumber;
    private static int nextTokenNumber = 1001; // Start token numbers from 1001

    public Order() {
        this.tokenNumber = nextTokenNumber++;
        System.out.println("Order placed with Token Number: " + tokenNumber);
    }

    public int getTokenNumber() {
        return tokenNumber;
    }

    public void addDish(Dish dish, int quantity, Map<String, String> customizations) {
        items.add(new OrderItem(dish, quantity, customizations));
        System.out.println("Added " + quantity + " x " + dish.getName() + " to order (Token: " + tokenNumber + ")" +
                          (customizations != null && !customizations.isEmpty() ? " with customizations: " + customizations : ""));
    }

    public void removeDish(Dish dish) {
        items.removeIf(item -> item.getDish() == dish);
        System.out.println("Removed " + dish.getName() + " from order (Token: " + tokenNumber + ").");
    }

    public void setDiscount(double discount) {
        this.discount = discount;
        System.out.println("Applied " + (discount * 100) + "% discount to order (Token: " + tokenNumber + ").");
    }

    public double getSubtotal() {
        double subtotal = 0.0;
        for (OrderItem item : items) {
            subtotal += item.getTotalPrice();
        }
        return subtotal;
    }

    public double getTax() {
        return getSubtotal() * taxRate;
    }

    public double getDiscountAmount() {
        return getSubtotal() * discount;
    }

    public double getTotal() {
        return getSubtotal() + getTax() - getDiscountAmount();
    }

    public void generateBill() {
        System.out.println("----- Restaurant Bill (Token: " + tokenNumber + ") -----");
        if (customer != null) {
            System.out.println("Customer: " + customer.getName());
        }
        System.out.println("Dishes:");
        for (OrderItem item : items) {
            String customizationDetails = item.getCustomizations().isEmpty() ? "" : " (Customizations: " + item.getCustomizations() + ")";
            System.out.println("- " + item.getQuantity() + " x " + item.getDish().getName() +
                              ": $" + String.format("%.2f", item.getDish().getPrice()) +
                              " = $" + String.format("%.2f", item.getTotalPrice()) + customizationDetails);
        }
        System.out.println("Subtotal: $" + String.format("%.2f", getSubtotal()));
        System.out.println("Tax (10%): $" + String.format("%.2f", getTax()));
        System.out.println("Discount (" + (discount * 100) + "%): -$" + String.format("%.2f", getDiscountAmount()));
        System.out.println("Total: $" + String.format("%.2f", getTotal()));
        System.out.println("Status: " + status);
        System.out.println("---------------------------");
    }

    public void updateStatus(OrderStatus newStatus) {
        this.status = newStatus;
        System.out.println("Order status updated to: " + status + " (Token: " + tokenNumber + ")");
        if (status == OrderStatus.SERVED) {
            System.out.println("*** Notification: Order " + tokenNumber + " is ready for pickup! ***");
        }
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        setDiscount(customer.getLoyaltyDiscount());
    }

    public boolean completeOrder(PaymentProcessor processor) {
        double total = getTotal();
        boolean paymentSuccess = processor.processPayment(total);
        if (paymentSuccess && customer != null) {
            customer.addLoyaltyPoints((int)(total * 10)); // 10 points per dollar
        }
        return paymentSuccess;
    }

    public List<OrderItem> getItems() {
        return items;
    }
}

// Client: Demonstrates Abstract Factory Pattern usage
public class Main {
    public static void main(String[] args) {
        // Initialize menu
        Menu menu = new Menu();
        menu.displayMenu();

        // Create customer
        Customer customer = new Customer("John Doe", "john@example.com");
        customer.addLoyaltyPoints(120); // Simulate prior purchases

        // Abstract Factory Pattern: Get factories for dish creation
        DishFactory burgerFactory = FactoryProvider.getFactory("burger");
        DishFactory drinkFactory = FactoryProvider.getFactory("drink");
        DishFactory dessertFactory = FactoryProvider.getFactory("dessert");

        // Create order with token number
        Order order = new Order();
        order.setCustomer(customer);

        // Add dishes using factories, demonstrating multiple instances
        if (menu.isValidDish("burger", "paneer")) {
            Dish paneerBurger1 = burgerFactory.createDish("paneer"); // Factory creates PaneerBurger
            order.addDish(paneerBurger1, 2, Map.of("extraPaneer", "yes"));

            Dish paneerBurger2 = burgerFactory.createDish("paneer"); // Factory creates another PaneerBurger
            order.addDish(paneerBurger2, 1, null);
        }

        if (menu.isValidDish("drink", "cola")) {
            Dish cola1 = drinkFactory.createDish("cola"); // Factory creates ColaDrink
            order.addDish(cola1, 1, Map.of("size", "large"));

            Dish cola2 = drinkFactory.createDish("cola"); // Factory creates another ColaDrink
            order.addDish(cola2, 2, null);
        }

        if (menu.isValidDish("dessert", "icecream")) {
            Dish iceCream = dessertFactory.createDish("icecream"); // Factory creates IceCream
            order.addDish(iceCream, 1, Map.of("flavor", "Chocolate"));
        }

        // Prepare dishes
        order.updateStatus(OrderStatus.PREPARING);
        System.out.println("Preparing order (Token: " + order.getTokenNumber() + "):");
        for (OrderItem item : order.getItems()) {
            for (int i = 0; i < item.getQuantity(); i++) {
                item.getDish().prepare();
                System.out.println();
            }
        }
        order.updateStatus(OrderStatus.SERVED);

        // Generate bill
        order.generateBill();

        // Process payment
        PaymentProcessor payment = new CreditCardPayment();
        if (order.completeOrder(payment)) {
            order.updateStatus(OrderStatus.COMPLETED);
        }
    }
}

// ----- Restaurant Menu -----
// Category: burger
// - veg
// - crush
// - paneer
// - nonveg
// Category: dessert
// - icecream
// - cheesecake
// Category: drink
// - cola
// - lemonade
// ---------------------------
// Added 120 loyalty points to John Doe. Total: 120
// Order placed with Token Number: 1001
// Applied 10.0% discount to order (Token: 1001).
// Added 2 x Paneer Burger to order (Token: 1001) with customizations: {extraPaneer=yes}
// Added 1 x Paneer Burger to order (Token: 1001)
// Added 1 x Cola to order (Token: 1001) with customizations: {size=large}
// Added 2 x Cola to order (Token: 1001)
// Added 1 x Vanilla Ice Cream to order (Token: 1001) with customizations: {flavor=Chocolate}
// Order status updated to: PREPARING (Token: 1001)
// Preparing order (Token: 1001):
// Preparing Paneer Burger
// Ingredients:
// - Paneer Patty
// - Lettuce
// - Tomato
// - Cucumber
// - Mint Chutney
// - Extra Paneer

// Preparing Paneer Burger
// Ingredients:
// - Paneer Patty
// - Lettuce
// - Tomato
// - Cucumber
// - Mint Chutney
// - Extra Paneer

// Preparing Paneer Burger
// Ingredients:
// - Paneer Patty
// - Lettuce
// - Tomato
// - Cucumber
// - Mint Chutney

// Preparing Cola
// Size: Large
// Serving cold with ice.

// Preparing Cola
// Size: Medium
// Serving cold with ice.

// Preparing Cola
// Size: Medium
// Serving cold with ice.

// Preparing Vanilla Ice Cream
// Flavor: Chocolate
// Serving chilled in a bowl.

// Order status updated to: SERVED (Token: 1001)
// *** Notification: Order 1001 is ready for pickup! ***
// ----- Restaurant Bill (Token: 1001) -----
// Customer: John Doe
// Dishes:
// - 2 x Paneer Burger: $8.19 = $16.38 (Customizations: {extraPaneer=yes})
// - 1 x Paneer Burger: $6.99 = $6.99
// - 1 x Cola: $2.99 = $2.99 (Customizations: {size=large})
// - 2 x Cola: $2.49 = $4.98
// - 1 x Vanilla Ice Cream: $4.49 = $4.49 (Customizations: {flavor=Chocolate})
// Subtotal: $35.83
// Tax (10%): $3.58
// Discount (10.0%): -$3.58
// Total: $35.83
// Status: SERVED
// ---------------------------
// Processing credit card payment of $35.83
// Added 358 loyalty points to John Doe. Total: 478
// Order status updated to: COMPLETED (Token: 1001)

// === Code Execution Successful ===