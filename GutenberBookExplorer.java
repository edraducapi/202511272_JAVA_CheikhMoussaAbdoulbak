import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GutenberBookExplorer {

    static Scanner input = new Scanner(System.in);

    // Book arrays
    static Book[] catalog = new Book[100];
    static Book[] readingList = new Book[20];

    static int catalogCount = 0;
    static int readingCount = 0;

    // BOOK CLASS
    static class Book {

        String title;
        String author;
        String price;
        String availability;
        String url;

        Book(String title, String author, String price,
             String availability, String url) {

            this.title = title;
            this.author = author;
            this.price = price;
            this.availability = availability;
            this.url = url;
        }
    }

    // MAIN METHOD
    public static void main(String[] args) {

        int choice;

        do {

            System.out.println("\n====================================");
            System.out.println("          BOOK EXPLORER");
            System.out.println("====================================");
            System.out.println("1. Explore Books");
            System.out.println("2. Search by Title");
            System.out.println("3. Search by Author");
            System.out.println("4. Add Book to Reading List");
            System.out.println("5. View Reading List");
            System.out.println("6. Remove Book");
            System.out.println("7. Display Book Details");
            System.out.println("0. Exit");
            System.out.println("====================================");

            System.out.print("Enter your choice: ");

            choice = readNumber();

            if (choice == 1) {

                exploreBooks();

            } else if (choice == 2) {

                System.out.print("Enter book title: ");
                String title = input.nextLine();

                searchBook(title);

            } else if (choice == 3) {

                System.out.print("Enter author name: ");
                String author = input.nextLine();

                searchBook(author, true);

            } else if (choice == 4) {

                addBook();

            } else if (choice == 5) {

                viewReadingList();

            } else if (choice == 6) {

                removeBook();

            } else if (choice == 7) {

                displayBookDetails();

            } else if (choice == 0) {

                System.out.println("Thank you for using Book Explorer!");

            } else {

                System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 0);
    }

    // READ NUMBER
    static int readNumber() {

        try {

            return Integer.parseInt(input.nextLine());

        } catch (NumberFormatException e) {

            return -1;
        }
    }

    // EXPLORE BOOKS
    static void exploreBooks() {

        System.out.println("\nLoading books from Books to Scrape...");

        catalogCount = 0;

        fetchBooks("https://books.toscrape.com/");

        if (catalogCount == 0) {

            System.out.println("No books found. Check your internet connection.");

        } else {

            System.out.println("\nBOOKS AVAILABLE:");
            showCatalog();
        }
    }

    // FETCH BOOKS USING JSOUP
    static void fetchBooks(String website) {

        try {

            Document doc = Jsoup.connect(website)
                    .userAgent("Mozilla/5.0")
                    .timeout(20000)
                    .get();

            Elements books = doc.select("article.product_pod");

            for (Element book : books) {

                String title = book.select("h3 a").attr("title");

                String price = book.select(".price_color").text();

                String availability =
                        book.select(".availability").text().trim();

                String bookUrl =
                        book.select("h3 a").first().absUrl("href");

                String author = "Not provided";

                if (title.isEmpty() || bookUrl.isEmpty()) {
                    continue;
                }

                if (catalogCount >= catalog.length) {
                    break;
                }

                if (!alreadyInCatalog(bookUrl)) {

                    Book newBook = new Book(
                            title,
                            author,
                            price,
                            availability,
                            bookUrl
                    );

                    catalog[catalogCount] = newBook;
                    catalogCount++;
                }
            }

        } catch (Exception e) {

            System.out.println("Connection error: " + e.getMessage());
        }
    }

    // CHECK DUPLICATES
    static boolean alreadyInCatalog(String url) {

        for (int i = 0; i < catalogCount; i++) {

            if (catalog[i].url.equals(url)) {

                return true;
            }
        }

        return false;
    }

    // DISPLAY CATALOG
    static void showCatalog() {

        for (int i = 0; i < catalogCount; i++) {

            System.out.println("\nBook Number: " + (i + 1));
            System.out.println("Title: " + catalog[i].title);
            System.out.println("Price: " + catalog[i].price);
            System.out.println("Availability: "
                    + catalog[i].availability);

            System.out.println("--------------------------------");
        }
    }

    // SEARCH BY TITLE (OVERLOADING 1)
    static void searchBook(String title) {

        if (catalogCount == 0) {

            exploreBooks();
        }

        if (catalogCount == 0) {
            return;
        }

        boolean found = false;

        System.out.println("\nTITLE SEARCH RESULTS:");

        for (int i = 0; i < catalogCount; i++) {

            if (catalog[i].title.toLowerCase()
                    .contains(title.toLowerCase())) {

                System.out.println("\nBook Number: " + (i + 1));
                System.out.println("Title: " + catalog[i].title);
                System.out.println("Price: " + catalog[i].price);

                found = true;
            }
        }

        if (!found) {

            System.out.println("No matching book found.");
        }
    }

    // SEARCH BY AUTHOR (OVERLOADING 2)
    static void searchBook(String keyword, boolean byAuthor) {

        if (byAuthor) {

            System.out.println("\nAuthor searching is not available.");
            System.out.println(
                "Books to Scrape does not provide author information."
            );

        } else {

            searchBook(keyword);
        }
    }

    // ADD BOOK TO READING LIST
    static void addBook() {

        if (catalogCount == 0) {

            exploreBooks();
        }

        if (catalogCount == 0) {
            return;
        }

        if (readingCount >= readingList.length) {

            System.out.println("Reading list is full!");
            return;
        }

        showCatalog();

        System.out.print("\nEnter book number to add: ");

        int number = readNumber();

        if (number < 1 || number > catalogCount) {

            System.out.println("Invalid book number.");
            return;
        }

        Book selected = catalog[number - 1];

        for (int i = 0; i < readingCount; i++) {

            if (readingList[i].url.equals(selected.url)) {

                System.out.println("Book already exists in your list.");
                return;
            }
        }

        readingList[readingCount] = selected;
        readingCount++;

        System.out.println("Book added successfully!");
    }

    // VIEW READING LIST
    static void viewReadingList() {

        System.out.println("\n========== MY READING LIST ==========");

        if (readingCount == 0) {

            System.out.println("Your reading list is empty.");
            return;
        }

        for (int i = 0; i < readingCount; i++) {

            System.out.println("\nBook Number: " + (i + 1));
            System.out.println("Title: " + readingList[i].title);
            System.out.println("Price: " + readingList[i].price);
            System.out.println("URL: " + readingList[i].url);
        }
    }

    // REMOVE BOOK
    static void removeBook() {

        if (readingCount == 0) {

            System.out.println("Your reading list is empty.");
            return;
        }

        viewReadingList();

        System.out.print("\nEnter book number to remove: ");

        int number = readNumber();

        if (number < 1 || number > readingCount) {

            System.out.println("Invalid book number.");
            return;
        }

        for (int i = number - 1; i < readingCount - 1; i++) {

            readingList[i] = readingList[i + 1];
        }

        readingCount--;

        readingList[readingCount] = null;

        System.out.println("Book removed successfully!");
    }

    // DISPLAY BOOK DETAILS
    static void displayBookDetails() {

        if (catalogCount == 0) {

            exploreBooks();
        }

        if (catalogCount == 0) {
            return;
        }

        showCatalog();

        System.out.print("\nEnter book number: ");

        int number = readNumber();

        if (number < 1 || number > catalogCount) {

            System.out.println("Invalid book number.");
            return;
        }

        Book selected = catalog[number - 1];

        System.out.println("\n========== BOOK DETAILS ==========");
        System.out.println("Title: " + selected.title);
        System.out.println("Author: " + selected.author);
        System.out.println("Price: " + selected.price);
        System.out.println("Availability: " + selected.availability);
        System.out.println("Book URL: " + selected.url);
    }

}