package gates.pauli;

import gates.QuantumGate;
import org.jblas.ComplexDouble;
import org.jblas.ComplexDoubleMatrix;

public class YGate extends QuantumGate {
    public YGate() {
        ComplexDoubleMatrix yGate = new ComplexDoubleMatrix(2, 2);
        yGate.put(0, 1, new ComplexDouble(0,-1));
        yGate.put(1, 0, new ComplexDouble(0,1));
        this.gate = yGate;
        ComplexDoubleMatrix cyGate = new ComplexDoubleMatrix(4, 4);
        cyGate.put(0, 0, 1);
        cyGate.put(1, 1, 1);
        cyGate.put(2, 3, new ComplexDouble(0,-1));
        cyGate.put(3, 2, new ComplexDouble(0,1));
        this.cgate = cyGate;
    this.type = "Pauli Y" ;

    }

    public YGate(int qubits) {
        ComplexDoubleMatrix yGate = new ComplexDoubleMatrix(2, 2);
        yGate.put(0, 1, new ComplexDouble(0,-1));
        yGate.put(1, 0, new ComplexDouble(0,1));
        this.gate = yGate;
        ComplexDoubleMatrix cyGate = new ComplexDoubleMatrix(4, 4);
        cyGate.put(0, 0, 1);
        cyGate.put(1, 1, 1);
        cyGate.put(2, 3, new ComplexDouble(0,-1));
        cyGate.put(3, 2, new ComplexDouble(0,1));
        this.cgate = cyGate;

        scaleGate(qubits, new YGate());

    }

}
