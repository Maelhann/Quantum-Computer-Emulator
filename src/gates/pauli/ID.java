package gates.pauli;

import gates.QuantumGate;
import org.jblas.ComplexDouble;
import org.jblas.ComplexDoubleMatrix;

public class ID extends QuantumGate{
    ID() {
        ComplexDouble[] IDData = new ComplexDouble[4];
        IDData[0].set(1, 0);
        IDData[1].set(0, 0);
        IDData[2].set(0, 0);
        IDData[3].set(1, 0);
        this.gate = new ComplexDoubleMatrix(IDData);

        ComplexDouble[] cIDData = new ComplexDouble[16];
        for (int i = 0; i < 16; i++) {
            if (i == 0 || i == 5) {
                cIDData[i].set(1, 0);
            } else {
                cIDData[i].set(0, 0);
            }
        }
        cIDData[10].set(1, 0);
        cIDData[15].set(1, 0);

        this.cgate = new ComplexDoubleMatrix(cIDData);
    }


    // constructor for n-qubit inputs

    ID(int qubits) {
        ComplexDouble[] IDData = new ComplexDouble[4];
        IDData[0].set(1, 0);
        IDData[1].set(0, 0);
        IDData[2].set(0, 0);
        IDData[3].set(1, 0);
        this.gate = new ComplexDoubleMatrix(IDData);

        ComplexDouble[] cIDData = new ComplexDouble[16];
        for (int i = 0; i < 16; i++) {
            if (i == 0 || i == 5) {
                cIDData[i].set(1, 0);
            } else {
                cIDData[i].set(0, 0);
            }
        }
        cIDData[10].set(1, 0);
        cIDData[15].set(1, 0);
        this.cgate = new ComplexDoubleMatrix(cIDData);

        scaleGate(qubits, new ID());

    }

}
