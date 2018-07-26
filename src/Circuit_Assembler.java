import gates.Hadamard;
import gates.PhaseShift;
import gates.QuantumGate;
import gates.Swap;
import gates.pauli.XGate;
import gates.pauli.YGate;
import gates.pauli.ZGate;
import gates.universal.Fredkin;
import gates.universal.Toffoli;
import org.jblas.ComplexDouble;
import org.jblas.ComplexDoubleMatrix;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

public class Circuit_Assembler {


    public static void main(String[] args) {
        System.out.println("\n |--------------------------------------------------|\n" +
                " |   WELCOME TO M.ROZE'S QUANTUM CIRCUIT ASSEMBLER   |\n" +
                " |--------------------------------------------------|\n");

        System.out.println(" help : -h ");
        System.out.println(" exit : -q ");
        Scanner sc = new Scanner(System.in);
        HashMap<String, Qubit> qubits = new HashMap<>();


        while (true) {
            System.out.println(">");
            String command = sc.nextLine();
            String[] words = command.split(" ");
            switch(words[0]){
                case "-h" :
                    System.out.println("-- COMMANDS --");
                    System.out.println("create [name] [coeff1] [coeff2] - " +
                            "(creates a qubit with the given coeffs as init state)");
                    System.out.println("apply [qubitName] [gateCode] " +
                            "applies a quantum gate to the given qubit");
                    System.out.println("entangle [qubit1] [qubit2] [result_name] " +
                            "creates a qubit from the kron product of the two first ones");
                    System.out.println("-- GATE CODE --");
                    System.out.println("-X");
                    System.out.println("-Y");
                    System.out.println("-Z");
                    System.out.println("-cX");
                    System.out.println("-cY");
                    System.out.println("-cZ");
                    System.out.println("-Swap");
                    System.out.println("-Toffoli");
                    System.out.println("-Fredkin");
                    System.out.println("-H");
                    break;
                case "-q" :
                    System.out.println("exiting assembler");
                case "create" :
                    assert words.length == 3 ;
                    ComplexDoubleMatrix state = new ComplexDoubleMatrix(2,1);
                    state.put(0,0,1);
                    state.put(1,0,1);
                    qubits.put(words[1], new Qubit(state));


            }
        }

    }


}

