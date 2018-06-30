import gates.Hadamard;
import gates.QuantumGate;
import gates.pauli.ID;
import gates.pauli.XGate;
import org.jblas.ComplexDoubleMatrix;

import java.util.Scanner;

public class Deutsch_Algorithm {


    public static void main(String[] args) {
        Scanner uGateScanner = new Scanner(System.in);
        System.out.println("\ninitializing two qubits in the basis states |1> and |0> \n");
        ComplexDoubleMatrix bra0ket
                = new ComplexDoubleMatrix(2, 1);

        bra0ket.put(0, 0, 1);
        bra0ket.put(1, 0, 0);
        ComplexDoubleMatrix bra1ket
                = new ComplexDoubleMatrix(2, 1);

        bra1ket.put(0, 0, 0);
        bra1ket.put(1, 0, 1);

        Qubit q0 = new Qubit(bra0ket);
        Qubit q1 = new Qubit(bra1ket);
        Hadamard h = new Hadamard();

        System.out.println("\nApplying Hadamard to both Qubits\n");
        q0.applyGate(h);
        q1.applyGate(h);
        System.out.println("\n -- Enter gate number for U gate or -h to print the Uf mappings -- \n");
        String input = uGateScanner.next();
        if (input.equals("-h")) {
            System.out.println("DISPLAY HELP");
        } else {
            int gateNum = Integer.parseInt(input);
            if (gateNum > 4 || gateNum < 1) {
                System.out.println("\n incorrect gate number specified : " + gateNum);
            } else {
                UFGate u = new UFGate(gateNum);
                System.out.println(q0.getState().columns);
                Qubit q1_2 = q0.entangle(q1);
                System.out.println(q1_2.getState().columns);
                System.out.println(q1_2.getState().rows);
                q1_2.applyGate(u);

                Hadamard h2 = new Hadamard(2);
                q1_2.applyGate(h2);
                System.out.println("\nExecution successfully terminated\n");
                System.out.println("top qubit measured at :" + q1_2.measure(0));

            }
        }


    }

    private static class UFGate extends QuantumGate {
        UFGate(int gateNum) {
            ComplexDoubleMatrix UFData = new ComplexDoubleMatrix(4, 4);
            switch (gateNum) {
                case 1:
                    ID id = new ID();
                    UFData = id.getCgate();
                    break;
                case 2:
                    UFData.put(0, 1, 1);
                    UFData.put(1, 0, 1);
                    UFData.put(2, 3, 1);
                    UFData.put(3, 2, 1);
                    break;
                case 3:
                    XGate n = new XGate();
                    UFData = n.getCgate();
                    break;
                case 4:
                    UFData.put(0, 1, 1);
                    UFData.put(1, 0, 1);
                    UFData.put(2, 2, 1);
                    UFData.put(3, 3, 1);
                    break;

            }
            this.gate = this.cgate = UFData;
        }

        @Override
        public void scaleGate(int qubits, QuantumGate gate) {
            System.out.println("Attempting to scale a gate during execution of the Deutsch algorithm -");
        }

    }

}
