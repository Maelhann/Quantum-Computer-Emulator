import org.jblas.ComplexDouble;

import java.util.HashMap;
import java.util.Scanner;

public class Circuit_Assembler {


    public static void main(String[] args) {
        System.out.println("\n |--------------------------------------------------|\n" +
                " |   WELCOME TO M.ROZE'S QUANTUM CIRCUIT EMULATOR   |\n" +
                " |--------------------------------------------------|\n");

        System.out.println(" help : -h ");
        System.out.println(" exit : -q ");
        Scanner sc = new Scanner(System.in);
        HashMap<String, Qubit> qubits = new HashMap<>();
        while (true) {
            System.out.print("\n >");
            String input = sc.next();
            Parser p = new Parser(input);
            String firstWord = p.getWord(1);



            switch (firstWord) {
                case "-h":
                    System.out.println("\n -- COMMAND LIST -- \n");
                    System.out.println("create [qubitName] [first coeff] [second coeff]");
                    System.out.println("entangle [entangledQubitName] [qubitName1] [qubitName2]");
                    System.out.println("collapse [qubitName] [entry]");
                    System.out.println("measure [qubitName] // BEING REWORKED CURRENTLY");
                    System.out.println("delete [qubitName]");
                    System.out.println("applyGate [qubitName] [gateCode]");
                    System.out.println("\n -- GATE LIST -- \n");
                    System.out.println("-H (Hadamard)");
                    System.out.println("-X (Pauli X)");
                    System.out.println("-Z (Pauli Z)");
                    System.out.println("-Y (Pauli Y)");
                    System.out.println("-PS [theta] (Phase shift gate)");
                    System.out.println("-S (Swap)");
                    System.out.println("-Frd (Fredkin)");
                    System.out.println("-Tof (Toffoli)");
                    break;
                case ("create") :

                    System.out.println("wowow");
                    break;




                case "-q":
                    System.out.println("\nbyebye !\n");
                    break;

            }

        }
    }
    private static class Parser{

        private final String parsed ;
        Parser(String parsed){
            this.parsed = parsed;
        }

        public String getWord(int n){
            assert parsed.length() != 0 ;
            assert parsed.contains(" ") : "must contain at least one word" ;
            String[] words = parsed.split(" ");

            return words[n-1];
        }




    }
}

