package gates;

import org.jblas.ComplexDouble;
import org.jblas.ComplexDoubleMatrix;

public class Swap extends QuantumGate {

    Swap() {
        ComplexDouble[] swapData = new ComplexDouble[16];
        for (int i = 0; i < 16; i++) {
            swapData[i].set(0, 0);
        }
        swapData[0].set(1, 0);
        swapData[15].set(1, 0);
        swapData[6].set(1, 0);
        swapData[9].set(1, 0);

        this.gate = this.cgate = new ComplexDoubleMatrix(swapData);


    }

    @Override
    public void scaleGate(int qubits, QuantumGate g) {
        // swap only swaps two qubits, can't scale up this gate
    }


}
