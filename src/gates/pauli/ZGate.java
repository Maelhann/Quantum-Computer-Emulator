package gates.pauli;

import gates.QuantumGate;
import org.jblas.ComplexDouble;
import org.jblas.ComplexDoubleMatrix;

public class ZGate extends QuantumGate {
    ZGate() {
        ComplexDouble[] zData = new ComplexDouble[4];
        zData[0].set(1, 0);
        zData[1].set(0, 0);
        zData[2].set(0, 0);
        zData[3].set(-1, 0);
        this.gate = new ComplexDoubleMatrix(zData);

        ComplexDouble[] czData = new ComplexDouble[16];
        for (int i = 0; i < 16; i++) {
            if (i == 0 || i == 5) {
                czData[i].set(1, 0);
            } else {
                czData[i].set(0, 0);
            }
        }
        czData[10].set(1, 0);
        czData[15].set(-1, 0);

        this.cgate = new ComplexDoubleMatrix(czData);
    }

    ZGate(int qubits) {
        ComplexDouble[] zData = new ComplexDouble[4];
        zData[0].set(1, 0);
        zData[1].set(0, 0);
        zData[2].set(0, 0);
        zData[3].set(-1, 0);
        this.gate = new ComplexDoubleMatrix(zData);

        ComplexDouble[] czData = new ComplexDouble[16];
        for (int i = 0; i < 16; i++) {
            if (i == 0 || i == 5) {
                czData[i].set(1, 0);
            } else {
                czData[i].set(0, 0);
            }
        }
        czData[10].set(1, 0);
        czData[15].set(-1, 0);

        this.cgate = new ComplexDoubleMatrix(czData);
        scaleGate(qubits);
    }

}
