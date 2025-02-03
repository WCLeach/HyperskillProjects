package tictactoe;
import java.util.Scanner;

public class TicTacToe {
    static char[] playerMove(char[] ticTacGrid, boolean playerTurn) {
        Scanner scanner = new Scanner(System.in);
        int currentPlace = 0;
        int cord1 = 0;
        int cord2 = 0;
            try {
                cord1 = scanner.nextInt();
                if (cord1 < 1 || cord1 > 3) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    return playerMove(ticTacGrid, playerTurn);
                }
            } catch (java.util.InputMismatchException e){
                System.out.println("You should enter numbers!");
                return playerMove(ticTacGrid, playerTurn);
            }

            try {
                cord2 = scanner.nextInt();
                if (cord2 < 1 || cord2 > 3) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    return playerMove(ticTacGrid, playerTurn);
                }
            } catch (java.util.InputMismatchException e) {
            System.out.println("You should enter numbers!");
                return playerMove(ticTacGrid, playerTurn);
            }
        //I think currentPlace = ((cord1-1)*3 + (cord2-1)) should always work and be better than this?
        //Realized after the fact.
        outer:
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (i == cord1-1 && j == cord2-1){
                    break outer;
                } else {
                    currentPlace++;
                }
            }
        }
        if (ticTacGrid[currentPlace] == '_'){
            if (playerTurn){
                ticTacGrid[currentPlace] = 'X';
            } else {
                ticTacGrid[currentPlace] = 'O';
            }

            return  ticTacGrid;
        }else {
            System.out.println("This cell is occupied! Choose another one!");
            return playerMove(ticTacGrid, playerTurn);
        }
    }

    static void displayGrid(char[] ticTacGrid){

        System.out.println("---------");
        System.out.println("| " + ticTacGrid[0] + " " + ticTacGrid[1] + " " + ticTacGrid[2] + " |");
        System.out.println("| " + ticTacGrid[3] + " " + ticTacGrid[4] + " " + ticTacGrid[5] + " |");
        System.out.println("| " + ticTacGrid[6] + " " + ticTacGrid[7] + " " + ticTacGrid[8] + " |");
        System.out.println("---------");

    }

    static boolean checkWinner (char[] ticTacGrid){

        // [0,1,2], [0,3,6], [1,4,7], [2,5,8], [3,4,5], [6,7,8], [0,4,8], [2,4,6]
        Integer[][] possibleWins = { {0,1,2}, {0,3,6}, {1,4,7}, {2,5,8}, {3,4,5}, {6,7,8}, {0,4,8}, {2,4,6}};

        for (int i = 0; i < possibleWins.length; i++) {
            if (ticTacGrid[possibleWins[i][0]] == ticTacGrid[possibleWins[i][1]] & ticTacGrid[possibleWins[i][0]] == ticTacGrid[possibleWins[i][2]]) {
                if (ticTacGrid[possibleWins[i][0]] != '_'){
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        // write your code here

        char[] ticTacGrid = {'_', '_', '_', '_', '_', '_', '_', '_', '_'};
        Boolean playerTurn = true;
        Boolean gameOver = false;
        Boolean gameWon = false;
        int movesPlayed = 0;

        while (gameOver == false) {
            displayGrid(ticTacGrid);
            ticTacGrid = playerMove(ticTacGrid, playerTurn);

            movesPlayed++;
            if (movesPlayed >= 5) {
                gameWon = checkWinner(ticTacGrid);
                if (gameWon == true) {
                    if (playerTurn == true){
                        displayGrid(ticTacGrid);
                        System.out.println("X wins");
                    } else {
                        displayGrid(ticTacGrid);
                        System.out.println("O wins");
                    }
                    gameOver = true;
                    return;
                }
            }
            if (movesPlayed == 9) {
                displayGrid(ticTacGrid);
                System.out.println("Draw");
                gameOver = true;
            }
            playerTurn = !playerTurn;
        }
    }
}
