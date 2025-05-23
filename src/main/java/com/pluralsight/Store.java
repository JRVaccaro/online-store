package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Store {

    public static void main(String[] args) {
        // Initialize variables
        ArrayList<Product> inventory = new ArrayList<Product>();
        ArrayList<Product> cart = new ArrayList<Product>();
        double totalAmount = 0.0;

        // Load inventory from CSV file
        loadInventory("products.csv", inventory);

        // Create scanner to read user input
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        // Display menu and get user choice until they choose to exit
        while (choice != 3) {
            System.out.println("Welcome to the Online Store!");
            System.out.println("1. Show Products");
            System.out.println("2. Show Cart");
            System.out.println("3. Exit");

            choice = scanner.nextInt();
            scanner.nextLine();

            // Call the appropriate method based on user choice
            switch (choice) {
                case 1:
                    displayProducts(inventory, cart, scanner);
                    break;
                case 2:
                    displayCart(cart, scanner, totalAmount);
                    break;
                case 3:
                    System.out.println("Thank you for shopping with us!");
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }
            //Method to load inventory
    public static void loadInventory(String fileName, ArrayList<Product> inventory) {
        String line;
        try{
            //opens file to read
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            //reads file line by line
            while ((line = reader.readLine()) != null) {
                //splits the line by | delimiter
                String[] parts = line.split("\\|");

                //extract id, removes additional spaces
                String id = parts[0].trim();
                //extract name, removes additional spaces
                String name =parts[1].trim();

                //parsing amount from String to double
                double amount = Double.parseDouble(parts[2].trim());

                inventory.add(new Product(id, name, amount));
            }
            reader.close();
        }catch (Exception e){
            System.out.println("Error has occurred!");
            e.printStackTrace();
        }
        // This method should read a CSV file with product information and
        // populate the inventory ArrayList with com.pluralsight.Product objects. Each line
        // of the CSV file contains product information in the following format:
        //
        // id,name,price
        //
        // where id is a unique string identifier, name is the product name,
        // price is a double value representing the price of the product
    }

    public static void displayProducts(ArrayList<Product> inventory, ArrayList<Product> cart, Scanner scanner) {

            //Loops through then shows all products
        for (Product product : inventory){
            System.out.println(product.toString());

        }
        System.out.println("Please enter the ID of the product you want to add to your cart");
        String id = scanner.nextLine().trim();

        Product selectedProduct = findProductById(id,inventory);

            //seeing if product was found or not
        if (selectedProduct != null) {
            cart.add(selectedProduct);
            System.out.println("Product has been added to cart.");
        }else {
            System.out.println("Sorry product ID was invalid.");
        }


        // This method should display a list of products from the inventory,
        // and prompt the user to add items to their cart. The method should
        // prompt the user to enter the ID of the product they want to add to
        // their cart. The method should
        // add the selected product to the cart ArrayList.
    }

    public static void displayCart(ArrayList<Product> cart, Scanner scanner, double totalAmount) {

      for (Product product : cart){
          System.out.println(product.toString());

      }

      totalAmount = 0;
      for (Product product : cart){
          totalAmount += product.getPrice();
      }
        System.out.println("Total amount: " + totalAmount);


        System.out.println("If you would like to remove an item, please enter the ID or press enter to skip");
      String id = scanner.nextLine().trim();


      if (!id.isEmpty()) {
          Product selectedProduct = findProductById(id, cart);


          if (selectedProduct != null) {
              cart.remove(selectedProduct);
              System.out.println("Product has been removed from cart");
          } else {
              System.out.println("Sorry product ID was invalid");
          }
      }
        System.out.println("Would you like to check out? Yes/No");
            String answer = scanner.nextLine().trim();
            if (answer.equalsIgnoreCase("yes")){
                checkOut(cart, totalAmount, scanner); //calling to check out method
            } else if (answer.equalsIgnoreCase("no")) {
                System.out.println("You can continue shopping.");

            } else {
                System.out.println("Invalid. Please enter yes or no");
            }


        // This method should display the items in the cart ArrayList, along
        // with the total cost of all items in the cart. The method should
        // prompt the user to remove items from their cart by entering the ID
        // of the product they want to remove. The method should update the cart ArrayList and totalAmount
        // variable accordingly.
    }

    public static void checkOut(ArrayList<Product> cart, double totalAmount, Scanner scanner) {

        totalAmount = 0;

        for (Product product : cart) {
            System.out.println(product.toString());
            totalAmount += product.getPrice();

        }
        System.out.println("Total amount: " + totalAmount);

        {
            System.out.println("Please enter payment amount: ");
            double payment = Double.parseDouble(scanner.nextLine().trim());

            if (payment >= totalAmount) {
                double change = payment - totalAmount; //calculating change
                System.out.println("Thank you for your payment, you change is: " + change);


                cart.clear(); //clearing out cart
                System.out.println("Cart is now empty. Have a nice day.");
            } else {
                System.out.println("Checkout invalid not enough funds.");
            }

        }

    }
    // This method should calculate the total cost of all items in the cart,
    // and display a summary of the purchase to the user. The method should
    // prompt the user to confirm the purchase, and calculate change and clear the cart
    // if they confirm.

    public static Product findProductById(String id, ArrayList<Product> inventory) {
        for (Product product : inventory){
            if (product.getId().equalsIgnoreCase(id)){
                return product; //return product if ID matches
            }
        }
        return null; // if no product with ID is found
        // This method should search the inventory ArrayList for a product with
        // the specified ID, and return the corresponding com.pluralsight.Product object. If
        // no product with the specified ID is found, the method should return
        // null.
    }
}
