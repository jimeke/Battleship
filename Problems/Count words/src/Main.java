import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;

class Main {
    public static void main(String[] args) throws Exception {
        try (Reader reader = new BufferedReader(new InputStreamReader(System.in))) {

            // Reader is supertype of BufferedReader
            // and BufferedReader is the actual object created
            // at runtime so we are free to cast reader to BufferedReader
            // to get the readLine() functionality
            String line = ((BufferedReader) reader).readLine();

            // read line is only white spaces or empty (length == 0) return 0
            // otherwise we remove all leading and trailing white spaces
            // from line to use split by white space (1 or more)
            int result = line.isEmpty() ? 0 : line.trim().split("\\s+").length;

            System.out.println(result);
        }
    }
}
