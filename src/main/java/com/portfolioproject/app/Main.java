package com.portfolioproject.app;

import java.util.Comparator;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.portfolioproject.concurrent.PriceUpdateTask;
import com.portfolioproject.model.Asset;
import com.portfolioproject.model.Holding;
import com.portfolioproject.model.MutualFund;
import com.portfolioproject.model.Stock;
import com.portfolioproject.model.User;
import com.portfolioproject.service.PortfolioService;
import com.portfolioproject.util.JsonUtil;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        PortfolioService portfolioService = new PortfolioService();

        int choice;

        System.out.println("=====================================");
        System.out.println("   STOCK PORTFOLIO MANAGEMENT SYSTEM");
        System.out.println("=====================================");

        do {

            System.out.println("\n----------- MENU -----------");
            System.out.println("1. Create User");
            System.out.println("2. Add Stock Holding");
            System.out.println("3. Add Mutual Fund Holding");
            System.out.println("4. Display User");
            System.out.println("5. Display Holdings");
            System.out.println("6. Sort Holdings");
            System.out.println("7. Save Data");
            System.out.println("8. Load Data");
            System.out.println("9. Update Stock Prices Concurrently");
            System.out.println("10. Exit");
            System.out.println("----------------------------");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                // =========================================
                // CASE 1: CREATE USER
                // =========================================

            case 1:

				System.out.println("\n--- Create User ---");

				System.out.print("Enter User ID: ");
				String userid = sc.nextLine();

				System.out.print("Enter User Name: ");
				String name = sc.nextLine();

				System.out.print("Enter Email: ");
				String email = sc.nextLine();

				// Check whether user already exists
				if (portfolioService.userExists(userid)) 
				{
					System.out.println("User already exists!");
					// Log a warning
					Object logger;
					logger.warn("Attempt to create an existing user: {}", userid);

				} 
				else 
				{
					User user = new User(userid, name, email);
					portfolioService.addUser(user);
					System.out.println("User created successfully!");
					// Log successful user creation
					 .info("User created successfully: {}", userid);
				}
				break;



                case 2:

                    System.out.println("\n--- Add Stock Holding ---");

                    System.out.print("Enter User ID: ");
                    String stockUserId = sc.nextLine();

                    // Get user from HashMap through service
                    User stockUser = portfolioService.getUser(stockUserId);

                    if (stockUser == null) {

                        System.out.println("User not found.");
                        break;
                    }

                    System.out.print("Enter Holding ID: ");
                    String stockHoldingId = sc.nextLine();

                    System.out.print("Enter Stock ID: ");
                    String stockId = sc.nextLine();

                    System.out.print("Enter Stock Name: ");
                    String stockName = sc.nextLine();

                    System.out.print("Enter Purchase Price: ");
                    double purchasePrice = sc.nextDouble();

                    System.out.print("Enter Current Price: ");
                    double currentPrice = sc.nextDouble();

                    System.out.print("Enter Quantity: ");
                    int quantity = sc.nextInt();

                    sc.nextLine();

                    // Create Stock object
                    Stock stock = new Stock(
                            stockId,
                            stockName,
                            purchasePrice,
                            currentPrice
                    );

                    // Create Holding object      
                    Holding stockHolding = new Holding(
                            stockHoldingId,
                            stock,
                            quantity
                    );

                    // Add holding to user
                    stockUser.addHolding(stockHolding);

                    System.out.println(
                            "Stock holding added successfully!"
                    );

                    break;


                // =========================================
                // CASE 3: ADD MUTUAL FUND HOLDING
                // =========================================

                case 3:

                    System.out.println("\n--- Add Mutual Fund Holding ---");

                    System.out.print("Enter User ID: ");
                    String mfUserId = sc.nextLine();

                    // Get user from HashMap through service
                    User mfUser = portfolioService.(mfUserId);

                    if (mfUser == null) {

                        System.out.println("User not found.");
                        break;
                    }

                    System.out.print("Enter Holding ID: ");
                    String mfHoldingId = sc.nextLine();

                    System.out.print("Enter Mutual Fund ID: ");
                    String mfId = sc.nextLine();

                    System.out.print("Enter Mutual Fund Name: ");
                    String mfName = sc.nextLine();

                    System.out.print("Enter Purchase Price: ");
                    double mfPurchasePrice = sc.nextDouble();

                    System.out.print("Enter NAV: ");
                    double nav = sc.nextDouble();

                    System.out.print("Enter Quantity: ");
                    int mfQuantity = sc.nextInt();

                    sc.nextLine();

                    // Create MutualFund object
                    MutualFund mutualFund = new MutualFund(
                            mfId,
                            mfName,
                            mfPurchasePrice,
                            nav
                    );

                    // Create Holding object
                    Holding mfHolding = new Holding(
                            mfHoldingId,
                            mutualFund,
                            mfQuantity
                    );

                    // Add holding to user
                    mfUser.addHolding(mfHolding);

                    System.out.println(
                            "Mutual fund holding added successfully!"
                    );

                    break;


                // =========================================
                // CASE 4: DISPLAY USER
                // =========================================

                case 4:

                    System.out.println("\n--- Display All Users ---");

                    boolean found = false;

                    for (User user1 : portfolioService.getAllUsers()) {

                        user1.display();

                        System.out.println("----------------------------");

                        found = true;
                    }

                    if (!found) {
                        System.out.println("No users created.");
                    }

                    break;

                // =========================================
                // CASE 5: DISPLAY HOLDINGS
                // =========================================

                case 5:

                    System.out.println("\n--- Holdings ---");

                    boolean userFound = false;

                    // Get all users from HashMap
                    for (User user1 : portfolioService.getAllUsers()) {

                        userFound = true;

                        System.out.println(
                                "\nUser ID: " + user1.getUserid()
                        );

                        System.out.println(
                                "User Name: " + user1.getName()
                        );

                        if (user1.getHoldings().isEmpty()) {

                            System.out.println(
                                    "No holdings available."
                            );

                        } else {

                            for (Holding holding :
                                    user1.getHoldings()) {

                                System.out.println(holding);
                            }
                        }

                        System.out.println(
                                "----------------------------"
                        );
                    }

                    if (!userFound) {

                        System.out.println("No users created.");
                    }

                    break;


                // =========================================
                // CASE 6: SORT HOLDINGS
                // =========================================

                case 6:

                    System.out.println("\n--- Sort Holdings ---");

                    System.out.println("1. Sort by Holding ID");
                    System.out.println("2. Sort by Quantity");

                    System.out.print("Enter your choice: ");
                    int sortChoice = sc.nextInt();

                    sc.nextLine();

                    boolean sorted = false;

                    // Get all users from HashMap
                    for (User user1 :
                            portfolioService.getAllUsers()) {

                        if (user1.getHoldings().isEmpty()) {

                            continue;
                        }

                        if (sortChoice == 1) {

                            user1.getHoldings().sort(
                                    Comparator.comparing(
                                            Holding::getHoldingId
                                    )
                            );

                            sorted = true;

                        } else if (sortChoice == 2) {

                            user1.getHoldings().sort(
                                    Comparator.comparingInt(
                                            Holding::getQuantity
                                    )
                            );

                            sorted = true;

                        } else {

                            System.out.println(
                                    "Invalid sorting choice."
                            );

                            break;
                        }
                    }

                    if (sorted) {

                        System.out.println(
                                "Holdings sorted successfully."
                        );
                    }

                    break;


                // =========================================
                // CASE 7: Save Data
                // =========================================
                case 7:

                    System.out.println("\n--- Save Data ---");

                    JsonUtil.saveUsers(
                            portfolioService.getAllUsers()
                    );

                    break;
                 // =========================================
                // CASE 8: Load Data
                // =========================================
                case 8:

                    System.out.println("\n--- Load Data ---");

                    User[] loadedUsers = JsonUtil.loadUsers();

                    portfolioService.loadUsers(
                            java.util.Arrays.asList(loadedUsers)
                    );

                    System.out.println("Data loaded successfully.");

                    break;
                 // =========================================
                 // CASE 9: Update Stock Prices Concurrently
                 // =========================================

//                 case 9:
//                     System.out.println("\n--- Update Stock Prices Concurrently ---");
//
//                     break;
                    // =========================================
                   // CASE 9: Update Stock Prices Concurrently
                   // =========================================
                    
//                case 9: 
//                {
//                    System.out.println("\n--- Update Stock Prices Concurrently ---");
//                    for (User user : portfolioService.getAllUsers()) 
//                    {
//                        for (Holding holding : user.getHoldings())
//                        {
//                            Asset asset = holding.getAsset();
//                            if (asset instanceof Stock)
//                            {
//                                Stock stockForUpdate = (Stock) asset;
//                                PriceUpdateTask task =new PriceUpdateTask(stockForUpdate);
//                                Thread thread = new Thread(task);
//                                thread.start();
//                            }
//                        }
//                    }
//
//                    break;
//                }
                 // =========================================
                 // CASE 9: Update Stock Prices Concurrently
                 // =========================================
                case 9:
                {
                    System.out.println("\n--- Update Stock Prices Concurrently ---");
                    ExecutorService executor = Executors.newFixedThreadPool(4);
                    for (User user : portfolioService.getAllUsers())
                    {
                        for (Holding holding : user.getHoldings())
                        {
                            Asset asset = holding.getAsset();
                            if (asset instanceof Stock)
                            {
                                Stock stockForUpdate = (Stock) asset;
                                PriceUpdateTask task =new PriceUpdateTask(stockForUpdate);
                                Future<Double> future = executor.submit(task);
                                System.out.println("Task submitted for: "
                                        + stockForUpdate.getAssetName());
                            }
                        }
                    }
                    executor.shutdown();
                    break;
                }
                
                //If we got race condtion overcome it using syncronization method
//                case 9:
//                {
//                    System.out.println("\n--- Race Condition Demonstration ---");
//                    ExecutorService executor =Executors.newFixedThreadPool(2);
//                    Stock stockForUpdate = null;
//                    // Find the first stock
//                    for (User user : portfolioService.getAllUsers())
//                    {
//                        for (Holding holding : user.getHoldings())
//                        {
//                            Asset asset = holding.getAsset();
//                            if (asset instanceof Stock)
//                            {
//                                stockForUpdate = (Stock) asset;
//                                break;
//                            }
//                        }
//                        if (stockForUpdate != null)
//                        {
//                            break;
//                        }
//                    }
//                    if (stockForUpdate != null)
//                    {
//                        System.out.println("Stock: "+ stockForUpdate.getAssetName());
//                        System.out.println("Initial Price: "+ stockForUpdate.getCurrentPrice());
//                        PriceUpdateTask task1 =new PriceUpdateTask(stockForUpdate);
//                        PriceUpdateTask task2 =new PriceUpdateTask(stockForUpdate);
//                        executor.submit(task1);
//                        executor.submit(task2);
//                        executor.shutdown();
//                    }
//
//                    break;
//                }
                 // =========================================
                 // CASE 10: Exit
                 // =========================================
                case 10:

                    System.out.println(
                            "\nThank you for using " +
                            "Stock Portfolio Management System."
                    );

                    break;


                // =========================================
                // DEFAULT
                // =========================================

                default:

                    System.out.println(
                            "Invalid choice. Please enter 1 to 9."
                    );
            }

        } while (choice != 10);

        sc.close();
    }
}