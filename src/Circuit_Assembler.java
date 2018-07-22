import gates.Hadamard;
import gates.PhaseShift;
import gates.QuantumGate;
import gates.Swap;
import gates.pauli.XGate;
import gates.pauli.YGate;
import gates.pauli.ZGate;
import gates.universal.Fredkin;
import gates.universal.Toffoli;
import org.jblas.ComplexDoubleMatrix;

import java.util.HashMap;
import java.util.HashSet;
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
        HashMap<String, QuantumGate> activeGates = new HashMap<>();

        while (true) {
            System.out.print("\n>");
            String input = sc.next();
            String[] words = input.split(" ");
            for (String s:
                 words) {
                System.out.println(s);
            }

            switch (words[0]) {
                case "-h":
                    System.out.println("\n -- COMMAND LIST -- \n");
                    System.out.println("create [qubitName] [first coeff] [second coeff]");
                    System.out.println("entangle [entangledQubitName] [qubitName1] [qubitName2]");
                    System.out.println("collapse [qubitName] [entry]");
                    System.out.println("measure [qubitName] // BEING REWORKED CURRENTLY");
                    System.out.println("delete [qubitName]");
                    System.out.println("applyGate [qubitName] [gateCode]");
                    System.out.println("\n -- ACTIVATE NEW GATES -- \n");
                    System.out.println("-H [name](Hadamard)");
                    System.out.println("-X [name](Pauli X)");
                    System.out.println("-Z [name](Pauli Z)");
                    System.out.println("-Y [name](Pauli Y)");
                    System.out.println("-PS [name][theta] (Phase shift gate)");
                    System.out.println("-print");
                    System.out.println("\n -- GATE CODES -- \n");
                    System.out.println("[gateName]");
                    System.out.println("[Frd] (Fredkin)");
                    System.out.println("[Swp] (Swap two qubits)");
                    System.out.println("[Tof] (Toffoli Gate)");
                    break;
                case ("create"):
                    ComplexDoubleMatrix state = new ComplexDoubleMatrix(2, 1);
                    state.put(0, 0, sc.nextInt());
                    state.put(1, 0, sc.nextInt());

                    qubits.put(words[1], new Qubit(state));
                case "-H":
                    System.out.println(words[1]);
                    activeGates.put(words[1],new Hadamard());
                    break;
                case "-X":
                    activeGates.put(words[1],new XGate());
                    break;
                case "-Y":
                    activeGates.put(words[1],new YGate());
                    break;
                case "-Z":
                    activeGates.put(words[1],new ZGate());
                    break;
                case "-PS":
                    int arg = Integer.parseInt(input);
                    activeGates.put(words[1],new PhaseShift(arg));
                    break;

                case "-print":
                    for (String key :
                            activeGates.keySet()) {
                        System.out.print("\n"+key+": ");
                        activeGates.get(key).printType();
                    }
                    break;


                case "-q":
                    System.out.println("\nNow exiting\n");
                    qubits = new HashMap<>();
                    activeGates = new HashMap<>();
                    break;

            }

        }
    }


}

