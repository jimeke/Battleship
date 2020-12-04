package battleship;

import java.util.EnumSet;
import java.util.Scanner;

public class Board {
    private static int n;
    private static int m;
    private final char[][] board;
    private final int[][] occupationBoard;
    private int leftBoatParts;

    public Board() {
        n = 10;
        m = 10;
        this.board = initBoardWithDefaultValues();
        this.occupationBoard = new int[n][m];
        this.leftBoatParts = 17;
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

    public void requestCoordinates() {
        EnumSet.allOf(ShipType.class)
                .forEach(shipType -> shipInput(board, shipType));
    }

    public void drawBoard() {

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

    public void shipInput(char[][] board, ShipType shipType) {
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
        System.out.println();
        drawBoard();
    }

    public void drawShip(char[][] board, String c1, String c2) {
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

//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < m; j++) {
//                System.out.print(occupationBoard[i][j] + " ");
//            }
//            System.out.println();
//        }
    }

    public boolean checkShipLocation(String c1, String c2) {
        if (c1.charAt(0) == c2.charAt(0) || c1.charAt(1) == c2.charAt(1)) {
            return true;
        } else {
            System.out.println("\nError! Wrong ship location! Try again:");
            return false;
        }
    }

    public boolean checkShipLength(ShipType shipType, String c1, String c2) {
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

    public boolean checkShipNeighbourhood(String c1, String c2) {
        boolean isValid = true;
        int minRow = Math.min(c1.charAt(0) - 'A', c2.charAt(0) - 'A');
        int maxRow = Math.max(c1.charAt(0) - 'A', c2.charAt(0) - 'A');
        int minCol = Math.min(Integer.parseInt(c1.substring(1)) - 1, Integer.parseInt(c2.substring(1)) - 1);
        int maxCol = Math.max(Integer.parseInt(c1.substring(1)) - 1, Integer.parseInt(c2.substring(1)) - 1);

        for (int i = minRow; i <= maxRow; i++) {
            for (int j = minCol; j <= maxCol; j++) {
//                System.out.println(String.format("i = %d, j = %d", i, j));
                if (occupationBoard[i][j] == 1) {
                    System.out.println("\nError! You placed it too close to another one. Try again:");
                    isValid = false;
                    break;
                }
            }
        }
        return isValid;
    }

    public boolean isInputValid(ShipType shipType, String c1, String c2) {
        return checkShipLocation(c1, c2) && checkShipLength(shipType, c1, c2) && checkShipNeighbourhood(c1, c2);
    }

    public void addOccupation(int max, int min, int c, boolean isHorizontal) {
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

    public char[][] getBoard() {
        return board;
    }

    public int getLeftBoatParts() {
        return leftBoatParts;
    }

    public void decreaseLeftBoatParts() {
        this.leftBoatParts --;
    }

    public void setBoard(int a, int b, char c) {
        board[a][b] = c;
    }
}
