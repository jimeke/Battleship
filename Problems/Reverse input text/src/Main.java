import java.io.BufferedReader;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        // start coding here
        char[] data = new char[50];
        int number = reader.read(data);
        for (int i = number - 1; i > -1; i--) {
            System.out.print(data[i]);
        }
    }
}