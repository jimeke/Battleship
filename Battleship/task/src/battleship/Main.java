package battleship;

import java.util.EnumSet;
import java.util.Scanner;

public class Main {
    final static int n = 10;
    final static int m = 10;
    static char[][] board = initBoardWithDefaultValues();
    static int[][] occupationBoard = new int[n][m];
    static int leftBoatParts = 16;

    public static void main(String[] args) {

        drawBoard(board);
        requestCoordinates(board);
        startGame();
    }

    static void startGame() {
        System.out.println("\nThe game starts!\n");
        drawFogOfWar(board);
        System.out.println("\nTake a shot!\n");
        Scanner r = new Scanner(System.in);
        char positionA_B = 0;
        int position1_10 = 0;
        boolean properInput = false;

        while (!properInput) {
            String coordinate = r.nextLine();
            positionA_B = coordinate.charAt(0);
            position1_10 = Integer.parseInt(coordinate.substring(1));
            properInput = checkFireCoordinate(positionA_B, position1_10);
        }
        while (leftBoatParts != 0) {
            if (board[positionA_B - 'A'][position1_10 - 1] == 'O') {
                board[positionA_B - 'A'][position1_10 - 1] = 'X';
                leftBoatParts--;
                drawFogOfWar(board);
                if (checkSank(positionA_B, position1_10)) {
                    System.out.println("You sank a ship! Specify a new target:");
                } else {
                    System.out.println("\nYou hit a ship! Try again:\n");
                }
            } else if (board[positionA_B - 'A'][position1_10 - 1] == 'X') {
                drawFogOfWar(board);
                System.out.println("\nYou hit a ship! Try again:\n");

            } else if (board[positionA_B - 'A'][position1_10 - 1] != 'O') {
                board[positionA_B - 'A'][position1_10 - 1] = 'M';
                drawFogOfWar(board);
                System.out.println("\nYou missed. Try again:\n");
            }
            String coordinate = r.nextLine();
            positionA_B = coordinate.charAt(0);
            position1_10 = Integer.parseInt(coordinate.substring(1));
            System.out.println();
        }

        if (board[positionA_B - 'A'][position1_10 - 1] == 'O') {
            board[positionA_B - 'A'][position1_10 - 1] = 'X';
        }

        drawBoard(board);
        System.out.println("\nYou sank the last ship. You won. Congratulations!");
    }

    static boolean checkSank(char a, int b) {
        int countOfO = 0;
        boolean isHorizontal = true;
        if (a == 'A') {
            if (board[1][b - 1] == 'X' || board[1][b - 1] == 'O') {
                isHorizontal = false;
            }
        } else if (a == 'J') {
            if (board[a - 'A' - 1][b - 1] == 'X' || board[a - 'A' - 1][b - 1] == 'O') {
                isHorizontal = false;
            }
        } else {
            if (board[a - 'A' + 1][b - 1] == 'X'
                    || board[a - 'A' + 1][b - 1] == 'O'
                    || board[a - 'A' - 1][b - 1] == 'X'
                    || board[a - 'A' - 1][b - 1] == 'O') {
                isHorizontal = false;
            }
        }

        if (!isHorizontal) { // for vertical
            for (int i = a - 'A'; i < 10; i++) { //for vertical in two direction counter
                if (board[i][b - 1] != '~') {
                    if (board[i][b - 1] == 'O') {
                        countOfO++;
                    }
                }
            }
            for (int i = a - 'A'; i > 0; i--) {
                if (board[i][b - 1] != '~') {
                    if (board[i][b - 1] == 'O') {
                        countOfO++;
                    }
                }
            }
        } else { //for horizontal
            for (int i = b - 1; i < 10; i++) {
                if (board[a - 'A'][i] != '~') {
                    if (board[a - 'A'][i] == 'O') {
                        countOfO++;
                    }
                }
            }
            for (int i = b - 1; i > 0; i--) {
                if (board[a - 'A'][i] != '~') {
                    if (board[a - 'A'][i] == 'O') {
                        countOfO++;
                    }
                }
            }
        }
        return countOfO == 0;
    }

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
    }

    static void requestCoordinates(char[][] board) {
        EnumSet.allOf(ShipType.class)
                .forEach(shipType -> shipInput(board, shipType));
    }

    static void drawBoard(char[][] board) {
        char letters = 'A';
        int digits = 1;

        for (int i = 0; i <= 10; i++) {
            for (int j = 0; j <= 10; j++) {
                if (i == 0 && j == 0) {
                    System.out.print("  ");
                } else if (j == 0) {
                    System.out.print(letters + " ");
                    letters++;
                } else if (i == 0) {
                    System.out.print(digits + " ");
                    digits++;
                } else {
                    System.out.print(board[i - 1][j - 1] + " ");
                }
            }
            System.out.println();
        }
    }

    static char[][] initBoardWithDefaultValues() {
        char[][] board = new char[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                board[i][j] = '~';
            }
        }
        return board;
    }

    static void shipInput(char[][] board, ShipType shipType) {
        Scanner s = new Scanner(System.in);
        String c1 = null;
        String c2 = null;
        boolean properInput = false;

        System.out.println(String.format("\nEnter the coordinates of the %s (%d cells):", shipType.getName(), shipType.getLength()));

        while (!properInput) {
            System.out.println();
            String coordinates = s.nextLine();
            c1 = coordinates.split(" ")[0];
            c2 = coordinates.split(" ")[1];
            properInput = isInputValid(shipType, c1, c2);
        }

        drawShip(board, c1, c2);
        drawBoard(board);
    }

    static void drawShip(char[][] board, String c1, String c2) {
        if (c1.charAt(0) == c2.charAt(0)) {
            int row = c1.charAt(0) - 'A';
            int a = Integer.parseInt(c1.substring(1)) - 1;
            int b = Integer.parseInt(c2.substring(1)) - 1;
            int max = Math.max(a, b);
            int min = Math.min(a, b);

            for (int i = min; i <= max; i++) {
                board[row][i] = 'O';
            }
            addOccupation(max, min, row, true);

        } else if (c1.substring(1).equals(c2.substring(1))) {
            int col = Integer.parseInt(c1.substring(1)) - 1;
            int a = c1.charAt(0) - 'A';
            int b = c2.charAt(0) - 'A';
            int max = Math.max(a, b);
            int min = Math.min(a, b);

            for (int i = min; i <= max; i++) {
                board[i][col] = 'O';
            }
            addOccupation(max, min, col, false);
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(occupationBoard[i][j] + " ");
            }
            System.out.println();
        }
    }

    static boolean checkShipLocation(String c1, String c2) {
        if (c1.charAt(0) == c2.charAt(0) || c1.charAt(1) == c2.charAt(1)) {
            return true;
        } else {
            System.out.println("\nError! Wrong ship location! Try again:");
            return false;
        }
    }

    static boolean checkShipLength(ShipType shipType, String c1, String c2) {
        boolean isValid = false;
        if (c1.charAt(0) == c2.charAt(0)) {
            int a = Integer.parseInt(c1.substring(1));
            int b = Integer.parseInt(c2.substring(1));
            isValid = a >= b ? a - b + 1 == shipType.getLength() : b - a + 1 == shipType.getLength();
        } else if (c1.substring(1).equals(c2.substring(1))) {
            char a = c1.charAt(0);
            char b = c2.charAt(0);
            isValid = a >= b ? a - b + 1 == shipType.getLength() : b - a + 1 == shipType.getLength();
        }
        if (!isValid) {
            System.out.println(String.format("\nError! Wrong length of the %s! Try again:", shipType.getName()));
        }
        return isValid;
    }

    static boolean checkShipNeighbourhood(String c1, String c2) {
        boolean isValid = true;
        int minRow = Math.min(c1.charAt(0) - 'A', c2.charAt(0) - 'A');
        int maxRow = Math.max(c1.charAt(0) - 'A', c2.charAt(0) - 'A');
        int minCol = Math.min(Integer.parseInt(c1.substring(1)) - 1, Integer.parseInt(c2.substring(1)) - 1);
        int maxCol = Math.max(Integer.parseInt(c1.substring(1)) - 1, Integer.parseInt(c2.substring(1)) - 1);

        for (int i = minRow; i <= maxRow; i++) {
            for (int j = minCol; j <= maxCol; j++) {
                System.out.println(String.format("i = %d, j = %d", i, j));
                if (occupationBoard[i][j] == 1) {
                    System.out.println("\nError! You placed it too close to another one. Try again:");
                    isValid = false;
                    break;
                }
            }
        }
        return isValid;
    }

    static boolean isInputValid(ShipType shipType, String c1, String c2) {
        return checkShipLocation(c1, c2) && checkShipLength(shipType, c1, c2) && checkShipNeighbourhood(c1, c2);
    }

    static void addOccupation(int max, int min, int c, boolean isHorizontal) {
        int minCol, maxCol, minRow, maxRow;

        minCol = isHorizontal ? Math.max(min - 1, 0) : Math.max(c - 1, 0);
        maxCol = isHorizontal ? Math.min(max + 1, 9) : Math.min(c + 1, 9);
        minRow = isHorizontal ? Math.max(c - 1, 0) : Math.max(min - 1, 0);
        maxRow = isHorizontal ? Math.min(c + 1, 9) : Math.min(max + 1, 9);

        for (int i = minRow; i <= maxRow; i++) {
            for (int j = minCol; j <= maxCol; j++) {
                occupationBoard[i][j] = 1;
            }
        }
    }

    static boolean checkFireCoordinate(char a, int b) {
        if (a > 'A' - 1 && a < 'K' && b > 0 && b < 11) {
            return true;
        } else {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            return false;
        }
    }
}