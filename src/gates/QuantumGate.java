package gates;

import org.jblas.ComplexDouble;
import org.jblas.ComplexDoubleMatrix;

public abstract class QuantumGate {
    protected ComplexDoubleMatrix gate;
    protected ComplexDoubleMatrix cgate;


    public ComplexDoubleMatrix applyTo(ComplexDoubleMatrix c) {
        return gate.mmul(c);
    }

    public ComplexDoubleMatrix applyControlledTo(ComplexDoubleMatrix c) {
        // controlled version of the applyTo method --
        // control bits to be specified in input !!
        return cgate.mul(c);
    }

    protected void scaleGate(int qubits, QuantumGate g) {
        // scales up our gate to handle n-qubit inputs
        int i = 0;
        ComplexDoubleMatrix m = this.gate;
        ComplexDoubleMatrix cm = g.gate;
        System.out.println(gate.rows);
        System.out.println(gate.columns);
        while (i < qubits) {
            m = new ComplexDoubleMatrix(tensorProduct(m.toArray(), cm.toArray()));
            i++;
        }
        this.gate = this.cgate = m;
        System.out.println(gate.rows);
        System.out.println(gate.columns);

    }

    private ComplexDouble[] tensorProduct(ComplexDouble[] q1, ComplexDouble[] q2) {
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
