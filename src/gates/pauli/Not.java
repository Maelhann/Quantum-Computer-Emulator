package gates.pauli;

import gates.QuantumGate;
import org.jblas.ComplexDouble;
import org.jblas.ComplexDoubleMatrix;


public class Not extends QuantumGate {
    private ComplexDoubleMatrix notGate;
    private ComplexDoubleMatrix cNotGate;

    Not() {
        ComplexDouble[] notData = new ComplexDouble[4];
        notData[0].set(0, 0);
        notData[1].set(1, 0);
        notData[2].set(1, 0);
        notData[3].set(0, 0);
        this.notGate = new ComplexDoubleMatrix(notData);

        ComplexDouble[] cnotData = new ComplexDouble[16];
        for (int i = 0; i < 16; i++) {
            if (i == 0 || i == 5) {
                cnotData[i].set(1, 0);
            } else {
                cnotData[i].set(0, 0);
            }
        }
        cnotData[11].set(1, 0);
        cnotData[14].set(1, 0);

        this.cNotGate = new ComplexDoubleMatrix(cnotData);
    }


    // constructor for n-qubit inputs

    Not(int qubits) {
        ComplexDouble[] notData = new ComplexDouble[4];
        notData[0].set(0, 0);
        notData[1].set(1, 0);
        notData[2].set(1, 0);
        notData[3].set(0, 0);
        this.notGate = new ComplexDoubleMatrix(notData);

        ComplexDouble[] cnotData = new ComplexDouble[16];
        for (int i = 0; i < 16; i++) {
            if (i == 0 || i == 5) {
                cnotData[i].set(1, 0);
            } else {
                cnotData[i].set(0, 0);
            }
        }
        cnotData[11].set(1, 0);
        cnotData[14].set(1, 0);
        this.cNotGate = new ComplexDoubleMatrix(cnotData);

        scaleGate(qubits);

    }

}
