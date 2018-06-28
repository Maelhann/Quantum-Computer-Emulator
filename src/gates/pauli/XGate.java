package gates.pauli;

import gates.QuantumGate;
import org.jblas.ComplexDouble;
import org.jblas.ComplexDoubleMatrix;


public class XGate extends QuantumGate {


    public XGate() {
        ComplexDoubleMatrix newGate = new ComplexDoubleMatrix(2, 2);

        /* newGate.toArray2()[0][0] = new ComplexDouble( 0);
        newGate.toArray2()[0][1] = new ComplexDouble(1);
        newGate.toArray2()[1][0] = new ComplexDouble(1);
        newGate.toArray2()[1][1] = new ComplexDouble(0);
        */

        newGate.put(0, 1, 1);
        newGate.put(1, 0, 1);
        this.gate = newGate;


        ComplexDouble[] cxData = new ComplexDouble[16];
        for (int i = 0; i < 16; i++) {
            if (i == 0 || i == 5) {
                cxData[i] = new ComplexDouble(1, 0);
            } else {
                cxData[i] = new ComplexDouble(0, 0);
            }
        }
        cxData[11].set(1, 0);
        cxData[14].set(1, 0);

        this.cgate = new ComplexDoubleMatrix(cxData);
    }


    // constructor for n-qubit inputs
    public XGate(int qubits) {
        ComplexDoubleMatrix newGate = new ComplexDoubleMatrix(2, 2);

        /* newGate.toArray2()[0][0] = new ComplexDouble( 0);
        newGate.toArray2()[0][1] = new ComplexDouble(1);
        newGate.toArray2()[1][0] = new ComplexDouble(1);
        newGate.toArray2()[1][1] = new ComplexDouble(0);
        */

        newGate.put(0, 1, 1);
        newGate.put(1, 0, 1);
        this.gate = newGate;


        ComplexDouble[] cxData = new ComplexDouble[16];
        for (int i = 0; i < 16; i++) {
            if (i == 0 || i == 5) {
                cxData[i] = new ComplexDouble(1, 0);
            } else {
                cxData[i] = new ComplexDouble(0, 0);
            }
        }
        cxData[11].set(1, 0);
        cxData[14].set(1, 0);

        this.cgate = new ComplexDoubleMatrix(cxData);
        scaleGate(qubits,new XGate());
    }
}
