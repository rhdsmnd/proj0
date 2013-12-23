package enigma;

/** Class that represents a reflector in the enigma.
 *  @author Ron Desmond
 */
class Reflector extends Rotor {

    /** Call the superclass Rotor
     *to initialize this Reflector with specific NAME. */
    public Reflector(String name) {
        super(name);
    }

    /** Returns a useless value; should never be called. */
    @Override
    int convertBackward(int unused) {
        throw new UnsupportedOperationException();
    }
}
