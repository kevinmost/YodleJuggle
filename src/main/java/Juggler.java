import java.util.List;

/**
 * @author kevin
 * @dateCreated 3/4/14
 */
public class Juggler {

    // These are the 3 "ability parameters" of this juggler
    private int h; // Hand-eye coordination
    private int e; // Endurance
    private int p; // Pizzazz

    private List<Integer> desiredCircuits; // The desired circuits for this juggler

    public Juggler(int h, int e, int p, List<Integer> desiredCircuits) {
        this.h = h;
        this.e = e;
        this.p = p;
        this.desiredCircuits = desiredCircuits;
    }

    public int getH() {
        return h;
    }

    public int getE() {
        return e;
    }

    public int getP() {
        return p;
    }

    public List<Integer> getDesiredCircuits() {
        return desiredCircuits;
    }

    public int getDotProduct(Circuit c) {
        return h*c.getH() + e*c.getE() + p*c.getP();
    }
}
