package gates.universal;

import gates.QuantumGate;
import org.jblas.ComplexDouble;
import org.jblas.ComplexDoubleMatrix;

public class Fredkin extends QuantumGate {
    Fredkin() {
        ComplexDouble[] fredkinData = new ComplexDouble[64];

        for (int i = 0; i < 64; i++) {
            if (i % 9 == 0 && i < 37) {
                fredkinData[i].set(1, 0);
            } else {
                fredkinData[i].set(0, 0);
            }
        }
        fredkinData[46].set(1, 0);
        fredkinData[53].set(1, 0);
        fredkinData[63].set(1, 0);
        this.gate = this.cgate = new ComplexDoubleMatrix(fredkinData);
    }

    @Override
    public void scaleGate(int qubits, QuantumGate g) {
        // can't scale universal gate
    }

}
