package cinema;
import java.util.Scanner;

/*
Write a "Cinema control panel" At the start of the program ask the user for two ints, rows and number of seats.
The given input will always be 1-9. The show the user a menu one that will display to console a "preview" of the theatre.
Another will allow you to "buy a ticket" as well as display a price. out of bounds inputs and duplicate inputs will be given
Finally statistics which will print out number of tickets sold, percentage of the cinema filled as with 2 decimals.
The price earned for current tickets sold (variable price given by other prompts) and the potential profit if all seats are filled.
*/

public class Cinema {
    public static void main(String[] args) {
        // Write your code here
        Scanner scanner = new Scanner(System.in);
        char[][] cineSeats = getSize(scanner);
        menu(scanner, cineSeats);
    }

    public static char[][] getSize(Scanner scanner) {

        System.out.println("Enter the number of rows:");
        int numRows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int numSeats = scanner.nextInt();

        char[][] cineSeats = new char[numRows + 1][numSeats + 1];

        for (int r = 0; r < cineSeats.length; r++) {
            for (int c = 0; c < cineSeats[r].length; c++) {
                if (r == 0) {
                    if (c == 0) {
                        cineSeats[r][c] = ' ';
                    } else {
                        cineSeats[r][c] = (char) ('0' + c);
                    }
                } else {
                    if (c == 0) {
                        cineSeats[r][c] = (char) ('0' + r);
                    } else {
                        cineSeats[r][c] = 'S';
                    }
                }
            }
        }
        return cineSeats;
    }

    public static void printSeats(char[][] cineSeats) {

        System.out.println("Cinema:");
        for (char[] cineSeat : cineSeats) {
            for (int c = 0; c < cineSeat.length; c++) {
                System.out.print(cineSeat[c]);
                if (c < cineSeat.length - 1) {
                    System.out.print(" ");
                } else {
                    System.out.println();
                }
            }
        }
    }

    public static void userSeat(Scanner scanner, char[][] cineSeats) {

        System.out.println("Enter a row number:");
        int userRow = scanner.nextInt();
        System.out.println("Enter a seat number in that row:");
        int userSeat = scanner.nextInt();

        if (userRow < 0 || userRow > cineSeats.length-1 || userSeat < 0 || userSeat > cineSeats[userRow].length ){
            System.out.println("Wrong input!");
        } else {
            if (cineSeats[userRow][userSeat] == 'B'){
                System.out.println("That ticket has already been purchased!");
                userSeat(scanner,cineSeats);
            } else {
                calcPrice(cineSeats, userRow);
                cineSeats[userRow][userSeat] = 'B';
            }
        }
    }

    public static void calcPrice(char[][] cineSeats, int userRow) {

        int numRows = cineSeats.length-1;
        int numSeats = cineSeats[0].length-1;
        int premium = 10;
        int discount = 8;
        int frontHalf = 0;


        if (numRows * numSeats > 60) {
            frontHalf = numRows / 2;
            if (userRow <= frontHalf) {
                System.out.printf("Ticket price: $%d \n", premium);
            } else {
                System.out.printf("Ticket price: $%d \n", discount);
            }
        } else {
            System.out.printf("Ticket price: $%d \n", premium);
        }
    }

    public static void menu (Scanner scanner, char[][] cineSeats){
        System.out.println ("1. Show the seats");
        System.out.println ("2. Buy a ticket");
        System.out.println ("3. Statistics");
        System.out.println ("0. Exit");
        int userInput = scanner.nextInt();

        while (userInput != 0) {
            if (userInput == 1){
                printSeats(cineSeats);
            } else if (userInput == 2){
                userSeat(scanner, cineSeats);
            } else if (userInput == 3){
                statistics(cineSeats);
            }
            System.out.println();
            System.out.println ("1. Show the seats");
            System.out.println ("2. Buy a ticket");
            System.out.println ("3. Statistics");
            System.out.println ("0. Exit");
            userInput = scanner.nextInt();
        }
    }

    public static void statistics (char [][] cineSeats)  {
        int seatsPurchased = 0;
        int numRows = cineSeats.length-1;
        int numColumns = cineSeats[0].length-1;
        int numSeats = numRows * numColumns;
        int frontHalf = 0;
        int backHalf;
        int totalProfit = 0;
        int currentProfit = 0;

        if (numSeats > 60){
            frontHalf = numRows / 2;
            backHalf = numRows - frontHalf;
            totalProfit = (frontHalf*numColumns*10) + (backHalf*numColumns*8);
        } else {
            totalProfit = numSeats * 10;
        }

        for (int i = 0; i < cineSeats.length; i++) {
            for (int j = 0; j < cineSeats[i].length; j++) {
                if (cineSeats[i][j] == 'B') {
                    seatsPurchased++;
                    if (numSeats > 60 && i > frontHalf) {
                        currentProfit += 8;
                    } else {
                        currentProfit +=10;
                    }
                }
            }
        }
        System.out.printf("Number of purchased tickets: %d%n", seatsPurchased);
        System.out.printf("Percentage: %.2f%%%n", (seatsPurchased * 100.0 / numSeats));
        System.out.printf("Current income: $%d%n", currentProfit);
        System.out.printf("Total income: $%d%n", totalProfit);
    }
}
