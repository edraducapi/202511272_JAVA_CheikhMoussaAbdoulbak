/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.individualassignent;

/**
 *
 * @author HP
 */
import java.util.Scanner;
public class IndividualAssignent {

    public static void main(String[] args) {

    Scanner input = new Scanner(System.in);

    // Drink price constant
    final double DRINK_PRICE = 15.50;

    // Variables
    String customerName;
    int age;
    double mealPrice;
    int numberOfDrinks;
    boolean isStudent;
    int serviceRating;

    double drinksCost;
    double subtotal;
    double discountRate = 0;
    double discountAmount;
    double tipAmount = 0;
    double finalBill;

    // Collect customer information
    System.out.println("===== SMART RESTAURANT ORDER =====");

    System.out.print("Enter customer name: ");
    customerName = input.nextLine();

    System.out.print("Enter customer age: ");
    age = input.nextInt();

    System.out.print("Enter meal price: ");
    mealPrice = input.nextDouble();

    System.out.print("Enter number of drinks: ");
    numberOfDrinks = input.nextInt();

    System.out.print("Are you a student? (true/false): ");
    isStudent = input.nextBoolean();

    System.out.print("Enter service rating (1-5): ");
    serviceRating = input.nextInt();

    // Validate service rating
    if (serviceRating < 1 || serviceRating > 5) {

        System.out.println("Error: Service rating must be between 1 and 5.");

    } else {

        // Calculate drinks cost
        drinksCost = numberOfDrinks * DRINK_PRICE;

        // Calculate subtotal
        subtotal = mealPrice + drinksCost;

        // Calculate discount
        if (isStudent) {
            discountRate = 0.10;
        }
        else if (age >= 60) {
            discountRate = 0.05;
        }
        else {
            discountRate = 0;
        }

        discountAmount = subtotal * discountRate;

        // Calculate tip
        if (serviceRating >= 4 && serviceRating <= 5) {
            tipAmount = subtotal * 0.10;
        }
        else {
            tipAmount = 0;
        }

        // Calculate final bill
        finalBill = subtotal - discountAmount + tipAmount;

        // Type casting
        int wholeAmount = (int) finalBill;

        // Print receipt
        System.out.println("\n================================");
        System.out.println("       SMART RESTAURANT");
        System.out.println("================================");

        System.out.println("Customer: " + customerName);
        System.out.printf("Meal: $%.2f%n", mealPrice);
        System.out.printf("Drinks: $%.2f%n", drinksCost);
        System.out.printf("Discount: $%.2f%n", discountAmount);
        System.out.printf("Tip: $%.2f%n", tipAmount);

        System.out.println("--------------------------------");
        System.out.printf("Final bill: $%.2f%n", finalBill);
        System.out.println("Whole amount: $" + wholeAmount);

        System.out.println("================================");
        System.out.println("Thank you for visiting!");

    }

    input.close();
}
}
