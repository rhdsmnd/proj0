package enigma;

import java.util.ArrayList;
import java.util.Arrays;

/** Class that represents a complete enigma machine.
 *  @author Ron Desmond
 */
class Machine {

    /** Set my rotors to (from left to right) NAMES.  Initially, the rotor
     *  settings are all 'A'. */
    void replaceRotors(String[] names) {
        if (isReflector(names[0]) && isFixedRotor(names[1])) {
            this.reflector = new Reflector(names[0]);
            this.rotor2 = new FixedRotor(names[1]);
        } else {
            System.exit(1);
        }
        if (validRotors(Arrays.copyOfRange(names, 2, 5))) {
            this.rotor3 = new Rotor(names[2]);
            this.rotor4 = new Rotor(names[3]);
            this.rotor5 = new Rotor(names[4]);
        } else {
            System.exit(1);
        }
    }

    /** This method returns whether  S is the name of a reflector. */
    private static  boolean isReflector(String s) {
        return s.equals("B") || s.equals("C");
    }

    /** returns true if the name S is either "BETA" or "GAMMA".  */
    private static boolean isFixedRotor(String s) {
        return s.equals("BETA") || s.equals("GAMMA");
    }

    /** validRotors returns whether CHECKROTORS has
     *no duplicate rotorse or invalid rotors. */
    private static boolean validRotors(String[] checkRotors) {
        ArrayList<String> rotors = new ArrayList<String>();
        for (int i = 0; i < 8; i += 1) {
            rotors.add(PermutationData.ROTOR_SPECS[i][0]);
        }
        for (int i = 0; i < 3; i += 1) {
            if (!(rotors.contains(checkRotors[i]))) {
                return false;
            } else {
                rotors.remove(checkRotors[i]);
            }
        }
        return true;
    }

    /** Sets the rotors according to SETTING. */
    void setRotors(String setting) {
        if (setting.length() > 4) {
            System.exit(1);
        }
        for (int i = 0; i < 4; i += 1) {
            if (!(Character.isLetter(setting.charAt(i)))) {
                System.exit(1);
            }
        }
        this.rotor2.set(Rotor.toIndex(setting.charAt(0)));
        this.rotor3.set(Rotor.toIndex(setting.charAt(1)));
        this.rotor4.set(Rotor.toIndex(setting.charAt(2)));
        this.rotor5.set(Rotor.toIndex(setting.charAt(3)));
    }

    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) {
        String result = "";
        while (msg.length() > 0) {
            if (this.rotor4.atNotch()) {
                this.rotor3.advance();
                this.rotor4.advance();
            } else if (this.rotor5.atNotch()) {
                this.rotor4.advance();
            }
            this.rotor5.advance();
            int letter = Rotor.toIndex(msg.charAt(0));
            letter = this.rotor5.convertForward(letter);
            letter = this.rotor4.convertForward(letter);
            letter = this.rotor3.convertForward(letter);
            letter = this.rotor2.convertForward(letter);
            letter = this.reflector.convertForward(letter);
            letter = this.rotor2.convertBackward(letter);
            letter = this.rotor3.convertBackward(letter);
            letter = this.rotor4.convertBackward(letter);
            letter = this.rotor5.convertBackward(letter);
            result += Rotor.toLetter(letter);
            msg = msg.substring(1);
        }
        return result;
    }

    /** This machine's reflector. */
    private Reflector reflector;

    /** The second rotor from the left, must be a fixed rotor. */
    private FixedRotor rotor2;

    /** Rotor 3, a rotor that advances (also 3-5). */
    private Rotor rotor3;

    /** Rotor 4 in the machine. */
    private Rotor rotor4;

    /** Rotor 5, the rightmost rotor. */
    private Rotor rotor5;
}
