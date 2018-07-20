package gates.pauli;

import gates.QuantumGate;
import org.jblas.ComplexDoubleMatrix;

public class ID extends QuantumGate {
    public ID() {
        ComplexDoubleMatrix idGate = new ComplexDoubleMatrix(2, 2);
        idGate.put(0, 0, 1);
        idGate.put(1, 1, 1);
        this.gate = idGate;
        ComplexDoubleMatrix cidGate = new ComplexDoubleMatrix(4, 4);
        cidGate.put(0, 0, 1);
        cidGate.put(1, 1, 1);
        cidGate.put(2, 2, 1);
        cidGate.put(3, 3, 1);
        this.cgate = cidGate;
        // there really is no need for a controlled version of ID, 
        // as cidGate basically is ID of dim 4
        this.type = "ID";
    }


    // constructor for n-qubit inputs

    public ID(int qubits) {
        ComplexDoubleMatrix idGate = new ComplexDoubleMatrix(2, 2);
        idGate.put(0, 0, 1);
        idGate.put(1, 1, 1);
        this.gate = idGate;
        ComplexDoubleMatrix cidGate = new ComplexDoubleMatrix(4, 4);
        cidGate.put(0, 0, 1);
        cidGate.put(1, 1, 1);
        cidGate.put(2, 2, 1);
        cidGate.put(3, 3, 1);
        this.cgate = cidGate;

    }

}
