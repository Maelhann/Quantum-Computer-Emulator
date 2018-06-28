package gates.pauli;

import gates.QuantumGate;
import org.jblas.ComplexDouble;
import org.jblas.ComplexDoubleMatrix;

public class YGate extends QuantumGate {
    public YGate() {
        ComplexDouble[] yData = new ComplexDouble[4];
        yData[0].set(0, 0);
        yData[1].set(0, -1);
        yData[2].set(0, 1);
        yData[3].set(0, 0);
        this.gate = new ComplexDoubleMatrix(yData);

        ComplexDouble[] cyData = new ComplexDouble[16];
        for (int i = 0; i < 16; i++) {
            if (i == 0 || i == 5) {
                cyData[i].set(1, 0);
            } else {
                cyData[i].set(0, 0);
            }
        }
        cyData[11].set(0, -1);
        cyData[14].set(0, 1);

        this.cgate = new ComplexDoubleMatrix(cyData);
    }

    public YGate(int qubits) {
        ComplexDouble[] yData = new ComplexDouble[4];
        yData[0].set(0, 0);
        yData[1].set(0, -1);
        yData[2].set(0, 1);
        yData[3].set(0, 0);
        this.gate = new ComplexDoubleMatrix(yData);

        ComplexDouble[] cyData = new ComplexDouble[16];
        for (int i = 0; i < 16; i++) {
            if (i == 0 || i == 5) {
                cyData[i].set(1, 0);
            } else {
                cyData[i].set(0, 0);
            }
        }
        cyData[11].set(0, -1);
        cyData[14].set(0, 1);

        this.cgate = new ComplexDoubleMatrix(cyData);

        scaleGate(qubits,new YGate());
    }

}
