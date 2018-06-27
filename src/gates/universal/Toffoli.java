package gates.universal;

import gates.QuantumGate;
import org.jblas.ComplexDouble;
import org.jblas.ComplexDoubleMatrix;

public class Toffoli extends QuantumGate {
    Toffoli() {
        ComplexDouble[] toffoliData = new ComplexDouble[64];
        for (int i = 0; i < 64; i++) {
            if (i % 9 == 0 && i < 46) {
                toffoliData[i].set(1, 0);
            } else {
                toffoliData[i].set(0, 0);
            }
        }
        toffoliData[55].set(1, 0);
        toffoliData[62].set(1, 0);
        this.gate = this.cgate = new ComplexDoubleMatrix(toffoliData);
    }


    @Override
    public void scaleGate(int qubits, QuantumGate g){
        // can't scale universal gate
    }
}
