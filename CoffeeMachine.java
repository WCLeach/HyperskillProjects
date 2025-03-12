package machine;
import java.util.Objects;
import java.util.Scanner;

/*
Create a mock-up of a coffee machine, initialized with a specific amount of supplies. The machine supports the actions
buy which will be passed the correct argument based on the testing parameters, this step supports 3 different types of coffee
    each of which use a certain amount of supplies it will check if the machine has the current amount of supplies.
fill prompts the user for the number of supplies they would like to places in the machine.
take removes all money the machine has made.
clean resets the #of cups made tracker if this is =10 coffees can not be made until it is cleaned.
remaining lists the total supplies currently in the machine.
exit exit the program

This was a learning exercise with classes, the Machine, and the individual types of coffee were to be classes.
I understand the implementation quite well and believe I transitioned theory into practice. I'm not completely satisfied
with my organization of the program, however.
 */

public class CoffeeMachine {
        int cash;
        int water;
        int milk;
        int beans;
        int cups;
        int made;

        public CoffeeMachine() {
            this.cash = 550;
            this.water = 400;
            this.milk = 540;
            this.beans = 120;
            this.cups = 9;
            this.made = 0;
        }

        public void displaySupplies (){
            System.out.printf("The coffee machine has:%n");
            System.out.printf("%d ml of water%n", this.water);
            System.out.printf("%d ml of milk%n", this.milk);
            System.out.printf("%d g of coffee beans%n", this.beans);
            System.out.printf("%d disposable cups%n", this.cups);
            System.out.printf("$%d of money%n", this.cash);
        }

        public void takeCash () {
            System.out.printf("I gave you $%d%n",this.cash);
            this.cash = 0;
        }

        public void userFill (Scanner scanner){
            System.out.println("Write how many ml of water you want to add:");
            this.water += scanner.nextInt();
            System.out.println("Write how many ml of milk you want to add:");
            this.milk += scanner.nextInt();
            System.out.println("Write how many grams of coffee you want to add:");
            this.beans += scanner.nextInt();
            System.out.println("Write how many disposable cups you want to add:");
            this.cups += scanner.nextInt();
        }

        public void clean (){
            System.out.println("I have been cleaned!");
            made = 0;
        }

        public boolean canMake (Coffee selection){
            if (water < selection.getWaterRequired()){
                System.out.println("Sorry, not enough water!");
                return false;
            }
            if (milk < selection.getMilkRequired()){
                System.out.println("Sorry, not enough water!");
                return false;
            }
            if (beans < selection.getBeansRequired()){
                System.out.println("Sorry, not enough water!");
                return false;
            }
            if (cups < 1){
                System.out.println("Sorry, not enough cups!");
                return false;
            }
            return true;
        }

    public static void userMenu (Scanner scanner, CoffeeMachine machine, Coffee espresso, Coffee latte, Coffee cappuccino) {
        while (true) {
            System.out.println("Write action (buy, fill, take, clean, remaining, exit):");
            String userInput = scanner.nextLine();
            if (Objects.equals(userInput, "buy")) {
                if (machine.made > 9){
                    System.out.println("I need cleaning!");
                } else {
                    userBuy(scanner, machine, espresso, latte, cappuccino);
                }
            }
            if (Objects.equals(userInput, "fill")) {
                machine.userFill(scanner);
            }
            if (Objects.equals(userInput, "take")) {
                machine.takeCash();
            }
            if (Objects.equals(userInput, "remaining")){
                machine.displaySupplies();
            }
            if (Objects.equals(userInput, "clean")){
                machine.clean();
            }
            if (Objects.equals(userInput, "exit")) {
              break;
            }
        }
    }

    public static void userBuy (Scanner scanner, CoffeeMachine machine, Coffee espresso, Coffee latte, Coffee cappuccino) {
        String userInput = "";
            System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3- cappuccino, back - to main menu:");
            userInput = scanner.nextLine();
            if (Objects.equals(userInput, "1")) {
                if (machine.canMake(espresso)) {
                    System.out.println("I have enough resources, making you a coffee!");
                    machine.water -= espresso.getWaterRequired();
                    machine.milk -= espresso.getMilkRequired();
                    machine.beans -= espresso.getBeansRequired();
                    machine.cups--;
                    machine.made++;
                    machine.cash += espresso.getCost();
                    return;
                }
            }
            if (Objects.equals(userInput, "2")) {
                if (machine.canMake(latte)) {
                    System.out.println("I have enough resources, making you a coffee!");
                    machine.water -= latte.getWaterRequired();
                    machine.milk -= latte.getMilkRequired();
                    machine.beans -= latte.getBeansRequired();
                    machine.cups--;
                    machine.made++;
                    machine.cash += latte.getCost();
                    return;
                }
            }
            if (Objects.equals(userInput, "3")) {
                if (machine.canMake(cappuccino)) {
                    System.out.println("I have enough resources, making you a coffee!");
                    machine.water -= cappuccino.getWaterRequired();
                    machine.milk -= cappuccino.getMilkRequired();
                    machine.beans -= cappuccino.getBeansRequired();
                    machine.cups--;
                    machine.made++;
                    machine.cash += cappuccino.getCost();
                    return;
                }
            }
            if (Objects.equals(userInput, "back")) {
                return;
            }
        }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CoffeeMachine machine = new CoffeeMachine();
        Coffee espresso = new Coffee(250, 0, 16, 4);
        Coffee latte = new Coffee (350, 75, 20, 7);
        Coffee cappuccino = new Coffee(200, 100, 12, 6);
        userMenu(scanner, machine, espresso, latte, cappuccino);
    }
}

class Coffee {
    private int waterRequired;
    private int milkRequired;
    private int beansRequired;
    private int cost;

    public Coffee (int waterRequired, int milkRequired, int beansRequired, int cost){
        this.waterRequired = waterRequired;
        this.milkRequired = milkRequired;
        this.beansRequired = beansRequired;
        this.cost = cost;
    }

    public int getWaterRequired() {
        return waterRequired;
    }

    public int getMilkRequired() {
        return milkRequired;
    }

    public int getBeansRequired() {
        return beansRequired;
    }

    public int getCost() {
        return cost;
    }
}
