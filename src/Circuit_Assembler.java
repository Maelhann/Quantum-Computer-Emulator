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
import java.util.Scanner;

public class Circuit_Assembler {


    public static void main(String[] args) {
        System.out.println("\n |--------------------------------------------------|\n" +
                " |   WELCOME TO M.ROZE'S QUANTUM CIRCUIT ASSEMBLER  |\n" +
                " |--------------------------------------------------|\n");

        System.out.println(" help : -h ");
        System.out.println(" exit : -q ");
        Scanner sc = new Scanner(System.in);
        HashMap<String, Qubit> qubits = new HashMap<>();

        while(true){
            System.out.print(">");
            String command = sc.nextLine();
            String[] words = command.split(" ");
            switch(words[0]){
                case "-h" :
                    System.out.println("-- COMMANDS --");
                    System.out.println("create [name] [coeff1] [coeff2] - " +
                            "(creates a qubit with the given coeffs as init state)");
                    System.out.println("measure [name]");
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
                    assert words.length == 4 ;
                    ComplexDoubleMatrix state = new ComplexDoubleMatrix(2,1);
                    state.put(0,0,Integer.parseInt(words[2]));
                    state.put(1,0,Integer.parseInt(words[3]));
                    qubits.put(words[1], new Qubit(state));
                    break;
                case "print" :
                    System.out.println("now printing active qubits");
                    for(String s : qubits.keySet()){
                        System.out.println(s);
                    }
                    break;
                case "measure" :
                      assert qubits.containsKey(words[1]): "qubit must exist.";
                      qubits.get(words[1]).measure();
                      break ;
                case "entangle" :
                    Qubit newq = qubits.get(words[1]).entangle(qubits.get(words[2]));
                    qubits.put(words[3],newq);
                    break;
                case "apply" :
                    assert qubits.containsKey(words[1]);
                    QuantumGate gate ;
                    switch (words[2]){
                        case "-H" :
                            gate = new Hadamard(qubits.get(words[1]).getDimension());
                            gate.applyTo(qubits.get(words[1]).getState());
                            break;
                        case "-X" :
                            gate = new XGate(qubits.get(words[1]).getDimension());
                            gate.applyTo(qubits.get(words[1]).getState());
                            break;
                        case "-Y" :
                            gate = new YGate(qubits.get(words[1]).getDimension());
                            gate.applyTo(qubits.get(words[1]).getState());
                            break;
                        case "-Z" :
                            gate = new ZGate(qubits.get(words[1]).getDimension());
                            gate.applyTo(qubits.get(words[1]).getState());
                            break;
                        case "-cX" :
                            gate = new XGate(qubits.get(words[1]).getDimension());
                            gate.applyControlledTo(qubits.get(words[1]).getState());
                            break;
                        case "-cY" :
                            gate = new YGate(qubits.get(words[1]).getDimension());
                            gate.applyControlledTo(qubits.get(words[1]).getState());
                            break;
                        case "-cZ" :
                            gate = new ZGate(qubits.get(words[1]).getDimension());
                            gate.applyControlledTo(qubits.get(words[1]).getState());
                            break;
                        case "-Swap" :
                            assert qubits.get(words[1]).getDimension() == 2 ;
                            gate = new Swap();
                            qubits.get(words[1]).applyGate(gate);
                            break;
                        case "-Fredkin" :
                            assert qubits.get(words[1]).getDimension() == 3 ;
                            gate = new Fredkin() ;
                            qubits.get(words[1]).applyGate(gate);
                        case "-Toffoli" :
                            assert qubits.get(words[1]).getDimension() == 3 ;
                            gate = new Toffoli();
                            qubits.get(words[1]).applyGate(gate);
                        default:
                            System.out.println("error : gate not found");
                            break;
                    }
                    break;

             }
        }

    }


}

