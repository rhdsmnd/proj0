package enigma;

/** Class that represents a rotor in the enigma machine.
 *  @author Ron Desmond
 */
class Rotor {

    /** Size of alphabet used for plaintext and ciphertext. */
    static final int ALPHABET_SIZE = 26;

    /** The ascii integer value for 'A'. */
    static final int ASCII_A = 65;

    /** Uses NAME to find the corresponding rotor like above,
     *except initializing setting to be 0 or 'A'. */
    public Rotor(String name) {
        boolean init = false;
        for (String[] rotorSpec: PermutationData.ROTOR_SPECS) {
            if (rotorSpec[0].equals(name)) {
                perm = rotorSpec[1];
                if (rotorSpec.length > 2) {
                    invPerm = rotorSpec[2];
                }
                if (rotorSpec.length > 3) {
                    notch = rotorSpec[3];
                }
                init = true;
                break;
            }
        }
        if (!init) {
            System.exit(1);
        }
        _setting = 0;
    }

    /** Assuming that P is an integer in the range 0..25, returns the
     *  corresponding upper-case letter in the range A..Z. */
    static char toLetter(int p) {
        return (char) (p + ASCII_A);
    }

    /** Assuming that C is an upper-case letter in the range A-Z, return the
     *  corresponding index in the range 0..25. Inverse of toLetter. */
    static int toIndex(char c) {
        return ((int) c) - ASCII_A;
    }

    /** Return my current rotational setting as an integer between 0
     *  and 25 (corresponding to letters 'A' to 'Z').  */
    int getSetting() {
        return _setting;
    }

    /** Set getSetting() to POSN.  */
    void set(int posn) {
        assert 0 <= posn && posn < ALPHABET_SIZE;
        _setting = posn;
    }

    /** Return the conversion of P (an integer in the range 0..25)
     *  according to my permutation. */
    int convertForward(int p) {
        int ind = (this._setting + p) % ALPHABET_SIZE;
        char cInd = perm.charAt(ind);
        int check = toIndex(cInd) - _setting;
        if (check < 0) {
            check += ALPHABET_SIZE;
        }
        return check;
    }

    /** Return the conversion of E (an integer in the range 0..25)
     *  according to the inverse of my permutation. */
    int convertBackward(int e) {
        int ind = (_setting + e) % ALPHABET_SIZE;
        char cInd = invPerm.charAt(ind);
        int check =  toIndex(cInd) - _setting;
        if (check < 0) {
            check += ALPHABET_SIZE;
        }
        return check;
    }

    /** Returns true iff I am positioned to allow the rotor to my left
     *  to advance. */
    boolean atNotch() {
        if (notch.length() > 1) {
            int fNotch = toIndex(notch.charAt(0));
            int sNotch = toIndex(notch.charAt(1));
            return fNotch == _setting || sNotch == _setting;
        }
        return _setting == toIndex(notch.charAt(0));
    }

    /** Advance me one position. */
    void advance() {
        _setting = (_setting + 1) % ALPHABET_SIZE;
    }

    /** This rotor's setting, from 0 to 25. */
    private int _setting;

    /**The location of the notch(es) of this rotor. */
    private String notch;

    /** The permutation values of this rotor, one for each letter. */
    private String perm;

    /** The inverse of this rotor's permutation values. */
    private String invPerm;
}
