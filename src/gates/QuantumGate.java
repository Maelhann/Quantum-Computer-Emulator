package gates;

import org.jblas.ComplexDouble;
import org.jblas.ComplexDoubleMatrix;

public abstract class QuantumGate {
    private ComplexDoubleMatrix gate;
    private ComplexDoubleMatrix cgate;

    protected QuantumGate() {
        ComplexDouble[] notData = new ComplexDouble[4];
        notData[0].set(0, 0);
        notData[1].set(0, 0);
        notData[2].set(0, 0);
        notData[3].set(0, 0);
        this.gate = new ComplexDoubleMatrix(notData);

        ComplexDouble[] cnotData = new ComplexDouble[16];
        for (int i = 0; i < 16; i++) {
            if (i == 0 || i == 5) {
                cnotData[i].set(1, 0);
            } else {
                cnotData[i].set(0, 0);
            }
        }
        this.cgate = new ComplexDoubleMatrix(cnotData);
    }


    protected QuantumGate(int qubits) {
        ComplexDouble[] notData = new ComplexDouble[4];
        notData[0].set(0, 0);
        notData[1].set(0, 0);
        notData[2].set(0, 0);
        notData[3].set(0, 0);
        this.gate = new ComplexDoubleMatrix(notData);

        ComplexDouble[] cnotData = new ComplexDouble[16];
        for (int i = 0; i < 16; i++) {
            if (i == 0 || i == 5) {
                cnotData[i].set(1, 0);
            } else {
                cnotData[i].set(0, 0);
            }
        }
        this.cgate = new ComplexDoubleMatrix(cnotData);
        scaleGate(qubits);
    }


    public ComplexDoubleMatrix applyTo(ComplexDoubleMatrix c) {
        return gate.mul(c);
    }

    public ComplexDoubleMatrix applyControlledTo(ComplexDoubleMatrix c) {
        // controlled version of the applyTo method --
        // control bits to be specified in input !!
        return cgate.mul(c);
    }

    public void scaleGate(int qubits) {
        // scales up our gate to handle n-qubit inputs
        int i = 0;
        ComplexDoubleMatrix m = this.gate;
        ComplexDoubleMatrix cm = this.cgate;
        while (i < qubits) {
            m = new ComplexDoubleMatrix(tensorProduct(m.toArray(), m.toArray()));
            cm = new ComplexDoubleMatrix(tensorProduct(cm.toArray(), cm.toArray()));
            i++;
        }
        this.gate = m;
        this.cgate = cm;
    }

    public ComplexDouble[] tensorProduct(ComplexDouble[] q1, ComplexDouble[] q2) {
        ComplexDouble[] tensorData
                = new ComplexDouble[q2.length * q1.length];
        for (int i = 0; i < q1.length; i++) {
            for (int j = 0; j < q2.length; j++) {
                tensorData[i * q2.length + j]
                        = q1[i].mul(q2[j]);
            }
        }
        return tensorData;
    }


}
