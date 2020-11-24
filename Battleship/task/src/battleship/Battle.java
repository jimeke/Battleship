package battleship;

import java.util.Scanner;

public class Battle {
    private String[][] field;
    private int[] ships;
    private Scanner scanner;

    public Battle() {
        this.field = new String[10][10];
        this.ships = new int[] {5, 4, 3, 3, 2};
        this.scanner = new Scanner(System.in);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                this.field[i][j] = "~";
            }
        }
    }

    public void getField() {
        System.out.println();
        System.out.print(" ");
        for (int i = 1; i < 11; i++) {
            System.out.print(" " + i);
        }
        char A_J = 65;
        System.out.println();
        for (int i = A_J; i < 75; i++) {
            System.out.print((char) i);
            for (int j = 0; j < 10; j++) {
                System.out.print(" " + field[i - 65][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public void shipPlacement() {
        for (int i = 0; i < ships.length; i++) {
            String shipType = "";

            switch (i) { case 0: shipType = "Aircraft Carrier"; break; case 1: shipType = "Battleship"; break;
            case 2: shipType = "Submarine"; break; case 3: shipType = "Cruiser"; break;
            case 4: shipType = "Destroyer"; break; default: shipType = ""; break;}

            System.out.printf("Enter the coordinates of the %s (%d cells):", shipType, ships[i]);
            System.out.println();
            System.out.println();

            String firstCoordinate = scanner.next();
            String secondCoordinate = scanner.next();
            String[] splitFirstCoord = firstCoordinate.split("");
            String[] splitSecondCoord = secondCoordinate.split("");
            char firstMainPositionOfA_J = splitFirstCoord[0].charAt(0);
            char secondMainPositionOfA_J = splitSecondCoord[0].charAt(0);
            int firstMainPositionOf1_10 = Integer.parseInt(firstCoordinate.substring(1, firstCoordinate.length()));
            int secondMainPositionOf1_10 = Integer.parseInt(secondCoordinate.substring(1, secondCoordinate.length()));


            while (Math.abs(firstMainPositionOfA_J - secondMainPositionOfA_J) + 1 != ships[i]
                    && Math.abs(firstMainPositionOf1_10 - secondMainPositionOf1_10) + 1 != ships[i]) {
                System.out.println();
                System.out.println("Error! Wrong length of the Submarine! Try again:");
                System.out.println();
                firstCoordinate = scanner.next();
                secondCoordinate = scanner.next();
                splitFirstCoord = firstCoordinate.split("");
                splitSecondCoord = secondCoordinate.split("");
                firstMainPositionOfA_J = splitFirstCoord[0].charAt(0);
                secondMainPositionOfA_J = splitSecondCoord[0].charAt(0);
                firstMainPositionOf1_10 = Integer.parseInt(firstCoordinate.substring(1, firstCoordinate.length()));
                secondMainPositionOf1_10 = Integer.parseInt(secondCoordinate.substring(1, secondCoordinate.length()));

            }

            if (splitFirstCoord[0].equals(splitSecondCoord[0])) { //True for horizontal placement, False for vertical placement

                for (int j = firstMainPositionOf1_10 - 1; j < secondMainPositionOf1_10; j++) {
                    field[firstMainPositionOfA_J - 65][j] = "O";
                }
            } else {
                for (int j = firstMainPositionOfA_J - 65; j < secondMainPositionOfA_J - 64; j++) {
                    field[j][Integer.parseInt(splitFirstCoord[1]) - 1] = "O";
                }
            }
            getField();

        }
    }


}
