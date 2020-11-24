import java.io.InputStream;

class Main {
    public static void main(String[] args) throws Exception {
        InputStream inputStream = System.in;
        do {
            System.out.print(inputStream.read());
        } while (inputStream.available() != 0);
    }
}