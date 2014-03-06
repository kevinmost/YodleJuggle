import java.util.ArrayList;
import java.util.List;

/**
 * @author kevin
 * @dateCreated 3/4/14
 */
public class Circuit {

    // These are the 3 "ability parameters" of this circuit
    private int h; // Hand-eye coordination
    private int e; // Endurance
    private int p; // Pizzazz

    private List<Integer> jugglersInCircuit; // The numbers of the jugglersInCircuit who will be part of this circuit



    public Circuit(int h, int e, int p) {
        this.h = h;
        this.e = e;
        this.p = p;

        jugglersInCircuit = new ArrayList<>();
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

    public List<Integer> getJugglersInCircuit() {
        return jugglersInCircuit;
    }


}
