package gates.pauli;

import gates.QuantumGate;
import org.jblas.ComplexDouble;
import org.jblas.ComplexDoubleMatrix;


public class XGate extends QuantumGate {


    XGate() {
        ComplexDouble[] xData = new ComplexDouble[4];
        xData[0].set(0, 0);
        xData[1].set(1, 0);
        xData[2].set(1, 0);
        xData[3].set(0, 0);
        this.gate = new ComplexDoubleMatrix(xData);

        ComplexDouble[] cxData = new ComplexDouble[16];
        for (int i = 0; i < 16; i++) {
            if (i == 0 || i == 5) {
                cxData[i].set(1, 0);
            } else {
                cxData[i].set(0, 0);
            }
        }
        cxData[11].set(1, 0);
        cxData[14].set(1, 0);

        this.cgate = new ComplexDoubleMatrix(cxData);
    }


    // constructor for n-qubit inputs

    XGate(int qubits) {
        ComplexDouble[] xData = new ComplexDouble[4];
        xData[0].set(0, 0);
        xData[1].set(1, 0);
        xData[2].set(1, 0);
        xData[3].set(0, 0);
        this.gate = new ComplexDoubleMatrix(xData);

        ComplexDouble[] cxData = new ComplexDouble[16];
        for (int i = 0; i < 16; i++) {
            if (i == 0 || i == 5) {
                cxData[i].set(1, 0);
            } else {
                cxData[i].set(0, 0);
            }
        }
        cxData[11].set(1, 0);
        cxData[14].set(1, 0);
        this.cgate = new ComplexDoubleMatrix(cxData);

        scaleGate(qubits);

    }

}
