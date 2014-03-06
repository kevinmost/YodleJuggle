import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author kevin
 * @dateCreated 3/4/14
 */
public class JuggleFestCircuitAssignments {


    // The arrays for the circuits and the jugglers, respectively
    private List<Circuit> circuits = new ArrayList<>(); // Each of these circuits has 3 parameters, and a list of the current jugglers
    private List<Juggler> jugglers = new ArrayList<>(); // Each of these jugglers has 3 parameters, and an array of their desired circuits

    public int MAX_JUGGLERS_PER_CIRCUIT; // A constant that contains the number of jugglers that one circuit can have

    public static void main(String[] args) {

        // Start a new instance of this class, which parses and adds jugglefest.txt to your Lists
        JuggleFestCircuitAssignments assignment = null;
        try {
            assignment = new JuggleFestCircuitAssignments();
            System.err.println("Added " + assignment.jugglers.size() + " jugglers and " + assignment.circuits.size() + " circuits. There will be " + assignment.MAX_JUGGLERS_PER_CIRCUIT + " jugglers per circuit.");
        } catch(FileNotFoundException fnfe) {
            System.err.println("Could not find file");
        }

        // For each juggler, add them to the best circuit they can be part of
        for (int i = 0; i < assignment.jugglers.size(); i++) {
            assignment.addJugglerToCircuit(i, assignment.jugglers.get(i).getDesiredCircuits().get(0));
        }

        System.err.println("\n\n ------------------ \n\n");
        for (int i = 0; i < assignment.circuits.size(); i++) {
            System.out.print("Circuit " + i + " contains: ");
            for (Integer juggler : assignment.circuits.get(i).getJugglersInCircuit()) {
                System.out.print(juggler + ", ");
            }
            System.out.println();
        }
    }

    public void addJugglerToCircuit(int juggler, int desiredCircuit) {
        circuits.get(desiredCircuit).getJugglersInCircuit().add(juggler);
        System.err.println("Added juggler " + juggler + " to " + desiredCircuit);

        if (circuits.get(desiredCircuit).getJugglersInCircuit().size() > MAX_JUGGLERS_PER_CIRCUIT) {
            kickWorstJugglerToNextChoice(desiredCircuit);
        }
    }

    public void kickWorstJugglerToNextChoice(int circuit) {
        int jugglerBeingKicked = 0;
        int jugglerBeingKickedDotProduct = Integer.MAX_VALUE;

        for (Integer juggler : circuits.get(circuit).getJugglersInCircuit()) {
            int currentJugglerDotProduct = jugglers.get(juggler).getDotProduct(circuits.get(circuit));
            if (currentJugglerDotProduct < jugglerBeingKickedDotProduct) {
                jugglerBeingKickedDotProduct = currentJugglerDotProduct;
                jugglerBeingKicked = juggler;
            }
        }

        // Remove the worst juggler from his circuit
        circuits.get(circuit).getJugglersInCircuit().remove(
                circuits.get(circuit).getJugglersInCircuit().indexOf(jugglerBeingKicked)
        );

        int nextBestCircuit;
        // Get his next-best choice
        if (jugglers.get(jugglerBeingKicked).getDesiredCircuits().indexOf(circuit) == jugglers.get(jugglerBeingKicked).getDesiredCircuits().size()) {
            nextBestCircuit = jugglers.get(jugglerBeingKicked).getDesiredCircuits().get(
                    jugglers.get(jugglerBeingKicked).getDesiredCircuits().indexOf(circuit)+1
            );
        }
        else {
            nextBestCircuit = jugglers.get(jugglerBeingKicked).getDesiredCircuits().get(jugglers.get(jugglerBeingKicked).getDesiredCircuits().size()-1);
        }

        // Add him to that choice
        addJugglerToCircuit(jugglerBeingKicked, nextBestCircuit);
        System.err.println("Kicked juggler " + jugglerBeingKicked + " from circuit " + circuit + " to circuit " + nextBestCircuit);
    }

    // Attempts to add every juggler to their first-choice circuit
    // If that circuit is already full, after being added to the circuit, the worst person in the circuit will be kicked to their 2nd choice
    public void addAllJugglersToFirstCircuitChoice() {
        for (int currentJuggler = 0; currentJuggler < jugglers.size(); currentJuggler++) { // For each juggler who needs to be added to a circuit...
            int currentJugglerMostDesiredCircuitIndex = jugglers.get(currentJuggler).getDesiredCircuits().get(0);
            Circuit currentJugglerMostDesiredCircuit = circuits.get(currentJugglerMostDesiredCircuitIndex);
            currentJugglerMostDesiredCircuit // Get the current juggler's most desired circuit...
                    .getJugglersInCircuit().add(currentJuggler); //... and add this juggler's number to the circuit

            // If we now have too many jugglers in the circuit:
            // 1) Identify the least-suitable juggler in this circuit (using dot-product)
            // 2) Remove him from this circuit
            // 3) Find his next-most-preferred circuit and put him in there
            // , remove the one least suited for this circuit and put him in his next-desired
            if (currentJugglerMostDesiredCircuit.getJugglersInCircuit().size() > MAX_JUGGLERS_PER_CIRCUIT) {
                int worstJugglerInCircuitNumber = currentJuggler; // This number corresponds to the juggler who is going to be kicked out of this circuit. Defaults to the juggler who just got in, because he has to prove himself first!
                // Check each juggler. If they are worse than the new juggler, they become the "worst juggler in the circuit", and will be kicked out.
                for (Integer jugglerNumber : currentJugglerMostDesiredCircuit.getJugglersInCircuit()) {
                    if (jugglers.get(jugglerNumber).getDotProduct(currentJugglerMostDesiredCircuit) < jugglers.get(worstJugglerInCircuitNumber).getDotProduct(currentJugglerMostDesiredCircuit)) {
                        worstJugglerInCircuitNumber = jugglerNumber;
                    }
                }
                kickToLowerDesiredCircuit(worstJugglerInCircuitNumber, currentJugglerMostDesiredCircuitIndex); // Kicks this juggler from this circuit to his next one
            }
        }
    }

    public void kickToLowerDesiredCircuit(int jugglerBeingKicked, int circuitJugglerIsCurrentlyIn) {
        // Remove this juggler from his current circuit.
        circuits.get(circuitJugglerIsCurrentlyIn).getJugglersInCircuit().remove(
                circuits.get(circuitJugglerIsCurrentlyIn).getJugglersInCircuit().indexOf(jugglerBeingKicked));


    }

    // Takes in a user's jugglefest.txt file and reads it line-by-line, parsing and adding each line to their arrays
    public JuggleFestCircuitAssignments() throws FileNotFoundException {
        // Prompt the user for the jugglefest.txt file and store the path to a variable
        // Scanner userFileNameInput = new Scanner(System.in);
        // System.out.println("Please enter the path to your jugglefest.txt file");
        // String filename = userFileNameInput.nextLine();
        // userFileNameInput.close();
        // TODO Uncomment above code and make the Scanner read in from "filename" before sending this out to production

        Scanner file = new Scanner(new File("/Users/kevin/Github/YodleJuggleFest/jugglefest-mini.txt")); // Open the jugglefest.txt file

        // Read the file one line at a time and parse their contents
        while (file.hasNext()) {
            /* Splits the line by spaces. Expect the array to contain:
             * [0] The identifier for the line (either C or J for Circuit or Juggler)
             * [1] The identifier concatenated to the # of that Circuit or Juggler (eg, C101 for Circuit #101)
             * [2] "H" for this Circuit/Juggler
             * [3] "E" for this Circuit/Juggler
             * [4] "P" for this Circuit/Juggler
             * [5] Only exists for Jugglers: a comma-delimited list of the 10 preferred Circuits for this Juggler
             */
            String[] inputFileLine = file.nextLine().split(" ");

            // Remove everything that isn't a digit or a comma, so now all we have is the raw data that we need.
            for (int i = 0; i < inputFileLine.length; i++) {
                inputFileLine[i] = inputFileLine[i].replaceAll("[^\\d,]", "");
            }

            // If there are 5 elements, this is a circuit element. Add it to the circuits list
            if (inputFileLine.length == 5) {
                circuits.add(Integer.parseInt(inputFileLine[1]),
                        new Circuit(
                        Integer.parseInt(inputFileLine[2]), // "H" value
                        Integer.parseInt(inputFileLine[3]), // "E" value
                        Integer.parseInt(inputFileLine[4])  // "P" value
                        ));
            }

            // If there are 6 elements, this is a juggler element. Parse the last element into an array and add it to the circuits list
            else if (inputFileLine.length == 6) {
                // Parses the String into a comma-delimited array, and then converts those Strings to Integers
                String[] desiredCircuitsString = inputFileLine[5].split(",");
                Integer[] desiredCircuitsInt = new Integer[desiredCircuitsString.length];
                for (int i = 0; i < desiredCircuitsString.length; i++) {
                    desiredCircuitsInt[i] = Integer.parseInt(desiredCircuitsString[i]);
                }

                jugglers.add(Integer.parseInt(inputFileLine[1]),
                        new Juggler(
                        Integer.parseInt(inputFileLine[2]), // "H" value
                        Integer.parseInt(inputFileLine[3]), // "E" value
                        Integer.parseInt(inputFileLine[4]), // "P" value
                        Arrays.asList(desiredCircuitsInt)   // Desired circuits
                ));
            }
        }

        file.close();
        MAX_JUGGLERS_PER_CIRCUIT = jugglers.size() / circuits.size();
    }
}
