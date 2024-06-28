import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Customer Class
class Customer {
    private String customerId;
    private String name;
    private String password;
    private String contact;
    private String licenseNumber;

    // Constructor
    public Customer(String customerId, String name, String password, String contact, String licenseNumber) {
        this.customerId = customerId;
        this.name = name;
        this.password = password;
        this.contact = contact;
        this.licenseNumber = licenseNumber;
    }

    // Getters and Setters
    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getContact() {
        return contact;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }

    public String toString() {
        return String.format("| %-7s | %-20s | %-15s | %-15s |", customerId, name, contact, licenseNumber);
    }

}

// Car Class
class Car {
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    // Constructor
    public Car(String carId, String brand, String model, double basePricePerDay) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    // Getters and Setters
    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double getBasePricePerDay() {
        return basePricePerDay;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }

    public double calculatePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }

    public String toString() {
        return String.format("| %-6s | %-12s | %-11s | %14.2f | %-9s |",
                carId, brand, model, basePricePerDay, isAvailable);
    }

}

// Rental Class
class Rental {
    private Car car;
    private Customer customer;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalCost;

    // Constructor
    public Rental(Car car, Customer customer, LocalDate startDate, LocalDate endDate, double totalCost) {
        this.car = car;
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCost = totalCost;
    }

    // Getters and Setters
    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public double getTotalCost() {
        return totalCost;
    }
}

// In-Memory Database for demonstration purposes
class InMemoryDatabase {
    private List<Car> cars = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private List<Rental> rentals = new ArrayList<>();

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void addRental(Rental rental) {
        rentals.add(rental);
    }

    public List<Car> getCars() {
        return cars;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public List<Car> findCarById(String carId) {
        List<Car> foundCars = new ArrayList<>();
        for (Car car : cars) {
            if (car.getCarId().equalsIgnoreCase(carId)) {
                foundCars.add(car);
            }
        }
        return foundCars;
    }

    public List<Customer> findCustomerById(String customerId) {
        List<Customer> foundCustomers = new ArrayList<>();
        for (Customer customer : customers) {
            if (customer.getCustomerId().equalsIgnoreCase(customerId)) {
                foundCustomers.add(customer);
            }
        }
        return foundCustomers;
    }

    public List<Rental> findRentalByCarId(String carId) {
        List<Rental> foundRentals = new ArrayList<>();
        for (Rental rental : rentals) {
            if (rental.getCar().getCarId().equalsIgnoreCase(carId)) {
                foundRentals.add(rental);
            }
        }
        return foundRentals;
    }

    public void removeRental(Rental rental) {
        rentals.remove(rental);
    }
}

// CarRentalService Class
class CarRentalService {
    public InMemoryDatabase db = new InMemoryDatabase();

    public void addCar(String brand, String model, double basePricePerDay) {
        String carId = "C" + (db.getCars().size() + 1);
        Car newCar = new Car(carId, brand, model, basePricePerDay);
        db.addCar(newCar);
        System.out.println("");
        System.out.println("Car " + newCar.getBrand() + " " + newCar.getModel() + " added successfully and CarId is "
                + newCar.getCarId());
    }

    public void addCustomer(String name, String password, String contact, String licenseNumber) {
        String customerId = "CU" + (db.getCustomers().size() + 1);
        Customer newCustomer = new Customer(customerId, name, password, contact, licenseNumber);
        db.addCustomer(newCustomer);
        System.out.println("");
        System.out.println("Customer " + newCustomer.getName() + " added successfully and CustomerId is "
                + newCustomer.getCustomerId());
    }

    public void listCars() {
        List<Car> cars = db.getCars();
        if (cars.isEmpty()) {
            System.out.println("No cars available.");
        } else {
            System.out.println("+--------+--------------+-------------+----------------+-----------+");
            System.out.println("| Car ID | Brand        | Model       | Base Price/Day | Available |");
            System.out.println("+--------+--------------+-------------+----------------+-----------+");
            for (Car car : cars) {
                System.out.println(car);
            }
            System.out.println("+--------+--------------+-------------+----------------+-----------+");
        }
    }

    public void listCustomers() {
        List<Customer> customers = db.getCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers registered.");
        } else {
            System.out.println("+---------+----------------------+-----------------+-----------------+");
            System.out.println("| Cust ID | Name                 | Contact         | License Number  |");
            System.out.println("+---------+----------------------+-----------------+-----------------+");
            for (Customer customer : customers) {
                System.out.println(customer);
            }
            System.out.println("+---------+----------------------+-----------------+-----------------+");
        }
    }

    public void rentCar(String carId, String customerId, LocalDate startDate, LocalDate endDate) {
        List<Car> cars = db.findCarById(carId);
        List<Customer> customers = db.findCustomerById(customerId);

        if (cars.isEmpty() || customers.isEmpty()) {
            System.out.println("Car or customer not found.");
            return;
        }

        Car car = cars.get(0); // Assuming carId is unique, take the first car found
        Customer customer = customers.get(0); // Assuming customerId is unique, take the first customer found

        if (!car.isAvailable()) {
            System.out.println("Car is not available for rental.");
            return;
        }

        int rentalDays = (int) (endDate.toEpochDay() - startDate.toEpochDay());
        double totalCost = car.calculatePrice(rentalDays);
        car.rent();
        Rental rental = new Rental(car, customer, startDate, endDate, totalCost);
        db.addRental(rental);
        System.out.println("");
        System.out.println("Car rented successfully. Total cost: $" + totalCost);
    }

    public void returnCar(String carId) {
        List<Rental> rentals = db.findRentalByCarId(carId);

        if (rentals.isEmpty()) {
            System.out.println("Rental record not found for the given Car ID.");
            return;
        }

        Rental rental = rentals.get(0); // Assuming there's only one rental per car

        Car car = rental.getCar();
        car.returnCar();
        db.removeRental(rental);
        System.out.println("");
        System.out.println("Car returned successfully by " + rental.getCustomer().getName());
    }

    public void listRentals() {
        List<Rental> rentals = db.getRentals();
        if (rentals.isEmpty()) {
            System.out.println("No rentals recorded.");
        } else {
            System.out.println("\nRecorded Rentals:");
            System.out.println(
                    "+----------------------+----------------------+-----------------+-----------------+---------------------+");
            System.out.println(
                    "| Car                  | Customer             | Start Date      | End Date        | Total Cost          |");
            System.out.println(
                    "+----------------------+----------------------+-----------------+-----------------+---------------------+");
            for (Rental rental : rentals) {
                System.out.printf("| %-20s | %-20s | %-15s | %-15s | %-19.2f |\n",
                        rental.getCar().getBrand() + " " + rental.getCar().getModel(),
                        rental.getCustomer().getName(),
                        rental.getStartDate(),
                        rental.getEndDate(),
                        rental.getTotalCost());
            }
            System.out.println(
                    "+----------------------+----------------------+-----------------+-----------------+---------------------+");
        }
    }
}

// Main Application
public class CarRentalApp {
    private static Scanner scanner = new Scanner(System.in);
    private static CarRentalService carRentalService = new CarRentalService();

    public static void main(String[] args) {
        initializeDemoData();
        boolean exit = false;
        while (!exit) {
            try {
                showMenu();
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1 -> addCar();
                    case 2 -> addCustomer();
                    case 3 -> listCars();
                    case 4 -> listCustomers();
                    case 5 -> rentCar();
                    case 6 -> returnCar();
                    case 7 -> listRentals();
                    case 8 -> exit = true;
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        System.out.println("Thank you for using the Car Rental System.");
    }

    private static void showMenu() {
        System.out.println("\nCar Rental System Menu:");
        System.out.println("1. Add Car");
        System.out.println("2. Add Customer");
        System.out.println("3. List Cars");
        System.out.println("4. List Customers");
        System.out.println("5. Rent Car");
        System.out.println("6. Return Car");
        System.out.println("7. List Rentals");
        System.out.println("8. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addCar() {
        try {
            System.out.print("Enter car brand: ");
            String brand = scanner.nextLine().trim();
            System.out.print("Enter car model: ");
            String model = scanner.nextLine().trim();
            System.out.print("Enter base price per day: ");
            double price = Double.parseDouble(scanner.nextLine().trim());

            if (brand.isEmpty() || model.isEmpty() || price <= 0) {
                throw new IllegalArgumentException("Invalid car details. Please provide valid inputs.");
            }

            carRentalService.addCar(brand, model, price);
        } catch (Exception e) {
            System.out.println("Error adding car: " + e.getMessage());
        }
    }

    private static void addCustomer() {
        try {
            System.out.print("Enter customer name: ");
            String name = scanner.nextLine().trim();
            System.out.print("Enter customer password: ");
            String password = scanner.nextLine().trim();
            System.out.print("Enter contact number: ");
            String contact = scanner.nextLine().trim();
            System.out.print("Enter license number: ");
            String licenseNumber = scanner.nextLine().trim();

            if (name.isEmpty() || password.isEmpty() || contact.isEmpty() || licenseNumber.isEmpty()) {
                throw new IllegalArgumentException("Invalid customer details. Please provide valid inputs.");
            }

            carRentalService.addCustomer(name, password, contact, licenseNumber);
        } catch (Exception e) {
            System.out.println("Error adding customer: " + e.getMessage());
        }
    }

    private static void listCars() {
        carRentalService.listCars();
    }

    private static void listCustomers() {
        carRentalService.listCustomers();
    }

    private static void rentCar() {
        try {
            System.out.print("Enter car ID to rent: ");
            String carId = scanner.nextLine().trim();
            System.out.print("Enter customer ID: ");
            String customerId = scanner.nextLine().trim();
            System.out.print("Enter rental start date (yyyy-mm-dd): ");
            LocalDate startDate = LocalDate.parse(scanner.nextLine().trim());
            System.out.print("Enter rental end date (yyyy-mm-dd): ");
            LocalDate endDate = LocalDate.parse(scanner.nextLine().trim());

            if (carId.isEmpty() || customerId.isEmpty() || startDate.isAfter(endDate)) {
                throw new IllegalArgumentException("Invalid rental details. Please provide valid inputs.");
            }

            carRentalService.rentCar(carId, customerId, startDate, endDate);
        } catch (Exception e) {
            System.out.println("Error renting car: " + e.getMessage());
        }
    }

    private static void returnCar() {
        List<Rental> rentals = carRentalService.db.getRentals();

        // Check if there are no rentals
        if (rentals.isEmpty()) {
            System.out.println("No cars are currently rented. Please check the rental list and try again.");
            return;
        }
        try {
            System.out.print("Enter car ID to return: ");
            String carId = scanner.nextLine().trim();

            if (carId.isEmpty()) {
                throw new IllegalArgumentException("Invalid car ID. Please provide a valid ID.");
            }

            carRentalService.returnCar(carId);
        } catch (Exception e) {
            System.out.println("Error returning car: " + e.getMessage());
        }
    }

    private static void listRentals() {
        carRentalService.listRentals();
    }

    private static void initializeDemoData() {
        carRentalService.addCar("Toyota", "Camry", 60.0);
        carRentalService.addCar("Honda", "Accord", 70.0);
        carRentalService.addCar("Mahindra", "Thar", 150.0);

        carRentalService.addCustomer("John Doe", "1234", "555-1234", "DL123456");
        carRentalService.addCustomer("Jane Smith", "2345", "555-5678", "DL654321");

    }
}