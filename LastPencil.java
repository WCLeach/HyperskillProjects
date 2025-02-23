package lastpencil;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Random;


public class LastPencil {


    public static String userInputPencils(Scanner scanner) {
        int userInput = 0;
        String userString;
        String pencilHolder = "";

        while (true) {

            userString = scanner.nextLine();
            try {
                userInput = Integer.parseInt(userString);
                if (userInput == 0) {
                    System.out.println("The number of pencils should be positive:");
                }else if (userInput < 0) {
                    System.out.println("The number of pencils should be numeric.");
                }else if (userInput > 0)
                    break;
            }catch (NumberFormatException e) {
                System.out.println("The number of pencils should be numeric.");
            }
        }
        for (int i = 0; i < userInput; i++) {
            pencilHolder = pencilHolder + "|";

        }
        return pencilHolder;
    }

    public static int goesFirst(Scanner scanner){
        String userInput;
        int returnValue = 0;
        System.out.println("Who will be the first (John, Jack):");

        while(true){
            userInput = scanner.nextLine();
            if (userInput.equals("John")){
                returnValue = 0;
                break;
            } else if (userInput.equals("Jack")) {
                returnValue = 1;
                break;
            } else {
                System.out.println("Choose between 'John' and 'Jack'");
            }
        }
        return returnValue;
    }
    public static String takePencils (String pencilsLeft, Scanner scanner) {
        int userInput = 0;

        while (true) {
            try {
                userInput = scanner.nextInt();
                if (userInput == 1 || userInput == 2 || userInput == 3) {
                    if (userInput > pencilsLeft.length()) {
                        System.out.println("Too many pencils were taken");
                    } else {
                        break;
                    }

                } else {
                    System.out.println("Possible values: '1', '2' or '3'");
                }
            } catch (InputMismatchException e){
                System.out.println("Possible values: '1', '2' or '3'");
                scanner.nextLine();
            }
        }
        return (pencilsLeft.substring(0, pencilsLeft.length()-userInput));
    }

    public static String botTakePencils (String pencilsLeft, Scanner scanner){
        int numPencils = pencilsLeft.length();
        Random random = new Random();
        int numToRemove = 0;
        
        if (numPencils > 4){
            numToRemove = switch (numPencils % 4) {
                case 1 -> random.nextInt(3) + 1;
                case 2 -> 1;
                case 3 -> 2;
                case 0 -> 3;
                default -> numToRemove;
            };
        } else if (numPencils > 1){
            numToRemove = numPencils -1;
        } else {
            numToRemove = 1;
        }
        System.out.println(numToRemove);
        return (pencilsLeft.substring(0, pencilsLeft.length()-numToRemove));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int userTakeAway = 0;

        System.out.println("How many pencils would you like to use:");
        String pencilHolder = userInputPencils(scanner);
        int playerturn = goesFirst(scanner);

        while (pencilHolder.length() > 0){
            System.out.println(pencilHolder);
            if (playerturn == 1){
                System.out.println("Jack's Turn!");
                playerturn = 0;
                pencilHolder = botTakePencils(pencilHolder, scanner);
            } else {
                System.out.println("John's Turn!");
                playerturn = 1;
                pencilHolder = takePencils(pencilHolder, scanner);
            }
        }
        if (playerturn == 1){
            System.out.println("Jack won!");
        } else {
            System.out.println("John won!");
        }
    }
    }
