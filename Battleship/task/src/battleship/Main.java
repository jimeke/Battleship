package battleship;

import java.util.Scanner;

public class Main {

    static int evenOdd = 0;
    static Board player1 = new Board();
    static Board player2 = new Board();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Player 1, place your ships on the game field\n"); // Game starts from replacement of 1st player's ships

        player1.drawBoard();
        player1.requestCoordinates();

        System.out.print("\nPress Enter and pass the move to another player"); // after pressing "Enter", 2nd player starts replacing own ships
        String enter = scanner.nextLine();
        if ("".equals(enter)) {
            System.out.println("...\nPlayer 2, place your ships to the game field\n");
            player2.drawBoard();
            player2.requestCoordinates();
        }
        //end of ships replacement
        pressEnter();

        Scanner r = new Scanner(System.in); //here starts open the fire by coordinate
        char positionA_B = 0;
        int position1_10 = 0;
        boolean properInput = false;

        while (!properInput) { //if fire coordinate is incorrect, need to write again
            String coordinate = r.nextLine();
            positionA_B = coordinate.charAt(0);
            position1_10 = Integer.parseInt(coordinate.substring(1));
            properInput = checkFireCoordinate(positionA_B, position1_10);
        }
//        evenOdd++;
        while (true) {
            if (playerChanger(player1, player2, evenOdd).getBoard()[positionA_B - 'A'][position1_10 - 1] == 'O') {
                playerChanger(player1, player2, evenOdd).getBoard()[positionA_B - 'A'][position1_10 - 1] = 'X';
                playerChanger(player1, player2, evenOdd).decreaseLeftBoatParts();

                if (player1.getLeftBoatParts() == 0 || player2.getLeftBoatParts() == 0) {
                    if (playerChanger(player1, player2, evenOdd).getBoard()[positionA_B - 'A'][position1_10 - 1] == 'O') {
                        playerChanger(player1, player2, evenOdd).getBoard()[positionA_B - 'A'][position1_10 - 1] = 'X';
                    }
                    break;
                } else if (checkSank(positionA_B, position1_10)) {
                    System.out.println("You sank a ship!");
                    pressEnter();

                } else {
                    System.out.print("\nYou hit a ship!");
                    pressEnter();
                }
            } else if (playerChanger(player1, player2, evenOdd).getBoard()[positionA_B - 'A'][position1_10 - 1] == 'X') {
                drawFogOfWar(playerChanger(player1, player2, evenOdd).getBoard());
                System.out.print("\nYou hit a ship!");
                pressEnter();

            } else if (playerChanger(player1, player2, evenOdd).getBoard()[positionA_B - 'A'][position1_10 - 1] != 'O') {
                playerChanger(player1, player2, evenOdd).setBoard(positionA_B - 'A', position1_10 - 1, 'M');
                System.out.print("You missed!");
                pressEnter();
            }
            String coordinate = r.nextLine();
            positionA_B = coordinate.charAt(0);
            position1_10 = Integer.parseInt(coordinate.substring(1));
            System.out.println();
        }

        playerChanger(player1, player2, evenOdd).drawBoard();
        System.out.println("\nYou sank the last ship. You won. Congratulations!");
    } //game body

    static boolean checkSank(char a, int b) {
        int countOfO = 0;
        boolean isHorizontal = true;
        if (a == 'A') {
            if (playerChanger(player1, player2, evenOdd).getBoard()[1][b - 1] == 'X'
                    || playerChanger(player1, player2, evenOdd).getBoard()[1][b - 1] == 'O') {
                isHorizontal = false;
            }
        } else if (a == 'J') {
            if (playerChanger(player1, player2, evenOdd).getBoard()[a - 'A' - 1][b - 1] == 'X'
                    || playerChanger(player1, player2, evenOdd).getBoard()[a - 'A' - 1][b - 1] == 'O') {
                isHorizontal = false;
            }
        } else {
            if (playerChanger(player1, player2, evenOdd).getBoard()[a - 'A' + 1][b - 1] == 'X'
                    || playerChanger(player1, player2, evenOdd).getBoard()[a - 'A' + 1][b - 1] == 'O'
                    || playerChanger(player1, player2, evenOdd).getBoard()[a - 'A' - 1][b - 1] == 'X'
                    || playerChanger(player1, player2, evenOdd).getBoard()[a - 'A' - 1][b - 1] == 'O') {
                isHorizontal = false;
            }
        }

        if (!isHorizontal) { // for vertical
            for (int i = a - 'A'; i < 10; i++) { //for vertical in two direction counter
                if (playerChanger(player1, player2, evenOdd).getBoard()[i][b - 1] != '~') {
                    if (playerChanger(player1, player2, evenOdd).getBoard()[i][b - 1] == 'O') {
                        countOfO++;
                    }
                }
            }
            for (int i = a - 'A'; i > 0; i--) {
                if (playerChanger(player1, player2, evenOdd).getBoard()[i][b - 1] != '~') {
                    if (playerChanger(player1, player2, evenOdd).getBoard()[i][b - 1] == 'O') {
                        countOfO++;
                    }
                }
            }
        } else { //for horizontal
            for (int i = b - 1; i < 10; i++) {
                if (playerChanger(player1, player2, evenOdd).getBoard()[a - 'A'][i] != '~') {
                    if (playerChanger(player1, player2, evenOdd).getBoard()[a - 'A'][i] == 'O') {
                        countOfO++;
                    }
                }
            }
            for (int i = b - 1; i > 0; i--) {
                if (playerChanger(player1, player2, evenOdd).getBoard()[a - 'A'][i] != '~') {
                    if (playerChanger(player1, player2, evenOdd).getBoard()[a - 'A'][i] == 'O') {
                        countOfO++;
                    }
                }
            }
        }
        return countOfO == 0;
    } //returning True if ship is sank

    static void drawFogOfWar(char[][] board) {
        char letter = 'A';
        int digits = 1;

        for (int i = 0; i <= 10; i++) {
            for (int j = 0; j <= 10; j++) {
                if (i == 0 && j == 0) {
                    System.out.print("  ");
                } else if (j == 0) {
                    System.out.print(letter + " ");
                    letter++;
                } else if (i == 0) {
                    System.out.print(digits + " ");
                    digits++;
                } else {
                    if (board[i - 1][j - 1] == 'O') {
                        System.out.print("~" + " ");
                    } else {
                        System.out.print(board[i - 1][j - 1] + " ");
                    }
                }
            }
            System.out.println();
        }
    } //showing opposite board without ships, just markers M -missed and X - crashed ships

    static boolean checkFireCoordinate(char a, int b) {
        if (a > 'A' - 1 && a < 'K' && b > 0 && b < 11) {
            return true;
        } else {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            return false;
        }
    } //if input for fire coordinate is correct - returning true

    static Board playerChanger(Board player1, Board player2, int evenOdd) {
        if (evenOdd % 2 == 0) {
            return player1;
        }
        return player2;
    } // returning switched player, depend of evenOdd variable

    static void pressEnter() { // switching player's board
        Scanner scanner2 = new Scanner(System.in);
        System.out.println("\nPress Enter and pass the move to another player\n...");
        evenOdd++;
        String enter2 = scanner2.nextLine();
        if ("".equals(enter2)) {
            drawFogOfWar(playerChanger(player1, player2, evenOdd).getBoard());
            evenOdd++;
            System.out.println("---------------------");
            playerChanger(player1, player2, evenOdd).drawBoard();
            if (evenOdd % 2 == 0) {
                System.out.println("\nPlayer 1, it's your turn:\n");
                evenOdd++;
            } else {
                System.out.println("\nPlayer 2, it's your turn:\n");
                evenOdd++;
            }
        }
    } //button for switching board
}