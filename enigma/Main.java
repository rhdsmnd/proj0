
package enigma;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;

/** Enigma simulator.
 *  @author Ron Desmond
 */
public final class Main {

    /** Process a sequence of encryptions and decryptions, as
     *  specified in the input from the standard input.  Print the
     *  results on the standard output. Exits normally if there are
     *  no errors in the input; otherwise with code 1. */
    public static void main(String[] unused) {
        Machine M;
        BufferedReader input =
            new BufferedReader(new InputStreamReader(System.in));
        M = null;
        try {
            while (true) {
                String line = input.readLine();
                if (line == null) {
                    if (M == null) {
                        System.exit(1);
                    }
                    break;
                }
                if (isConfigurationLine(line)) {
                    M = new Machine();
                    configure(M, line);
                } else {
                    printMessageLine(M.convert(standardize(line)));
                }
            }
        } catch (IOException excp) {
            System.err.printf("Input error: %s%n", excp.getMessage());
            System.exit(1);
        }
    }

    /** Return true iff LINE is an Enigma configuration line. */
    private static boolean isConfigurationLine(String line) {
        String[] configure = line.trim().split(" ");
        boolean b = configure[0].equals("*");
        boolean c = configure.length == 7;
        return configure[0].equals("*") && configure.length == 7;
    }

    /** Configure M according to the specification given on CONFIG,
     *  which must have the format specified in the assignment. */
    private static void configure(Machine M, String config) {
        String[] configure = config.split(" ");
        M.replaceRotors(Arrays.copyOfRange(configure, 1, 6));
        M.setRotors(configure[6]);
    }

    /** Return the result of converting LINE to all upper case,
     *  removing all blanks and tabs.  It is an error if LINE contains
     *  characters other than letters and blanks. */
    private static String standardize(String line) {
        line = line.toUpperCase();
        line = line.replaceAll("\\s", "");
        for (char letter: line.toCharArray()) {
            if (!(Character.isLetter(letter))) {
                System.exit(1);
            }
        }
        return line;
    }

    /** Print MSG in groups of five (except that the last group may
     *  have fewer letters). */
    private static void printMessageLine(String msg) {
        while (msg.length() > 5) {
            System.out.print(msg.substring(0, 5) + " ");
            msg = msg.substring(5);
        }
        System.out.println(msg);
    }
}

