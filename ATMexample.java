
import java.util.Scanner;
public class ATMexample {
public static void main(String[] args) {
    Scanner input = new Scanner(System.in);

    // Initial account details
    int correctPIN = 1234;
    int attempts = 0;
    int maxAttempts = 3;
    double balance = 1000.0;

    // Array to store transaction history
    String[] transactions = new String[100];
    int transactionCount = 0;

    // PIN Authentication
    while (attempts < maxAttempts) {

        System.out.print("Enter your PIN: ");
        int pin = input.nextInt();

        if (pin == correctPIN) {
            System.out.println("Login successful!");
            break;
        } else {
            attempts++;
            System.out.println("Incorrect PIN!");

            if (attempts == maxAttempts) {
                System.out.println("Maximum attempts reached. Account locked.");
                input.close();
                return;
            }

            System.out.println("Attempts remaining: "
                    + (maxAttempts - attempts));
        }
    }

    int choice;

    // Main ATM Menu
    do {
        System.out.println("\n===== ATM MENU =====");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. View Transactions");
        System.out.println("5. Exit");

        System.out.print("Choose an option: ");
        choice = input.nextInt();

        switch (choice) {

            case 1:
                System.out.println("Your balance is: $" + balance);
                break;

            case 2:
                System.out.print("Enter deposit amount: $");
                double deposit = input.nextDouble();

                if (deposit > 0) {
                    balance += deposit;

                    transactions[transactionCount] =
                            "Deposit: $" + deposit;
                    transactionCount++;

                    System.out.println("Deposit successful!");
                    System.out.println("New balance: $" + balance);
                } else {
                    System.out.println("Invalid deposit amount!");
                }
                break;

            case 3:
                System.out.print("Enter withdrawal amount: $");
                double withdrawal = input.nextDouble();

                if (withdrawal <= 0) {
                    System.out.println("Invalid withdrawal amount!");
                } else if (withdrawal <= balance) {

                    balance -= withdrawal;

                    transactions[transactionCount] =
                            "Withdrawal: $" + withdrawal;
                    transactionCount++;

                    System.out.println("Withdrawal successful!");
                    System.out.println("Remaining balance: $" + balance);

                } else {
                    System.out.println("Insufficient balance!");
                }
                break;

            case 4:
                System.out.println("\n===== TRANSACTION HISTORY =====");

                if (transactionCount == 0) {
                    System.out.println("No transactions found.");
                } else {
                    for (int i = 0; i < transactionCount; i++) {
                        System.out.println((i + 1) + ". "
                                + transactions[i]);
                    }
                }
                break;

            case 5:
                System.out.println("Thank you for using our ATM!");
                break;

            default:
                System.out.println("Invalid choice! Try again.");
        }

    } while (choice != 5);

    input.close();
}
}
