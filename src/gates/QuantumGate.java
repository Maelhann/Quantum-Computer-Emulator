package gates;

import org.jblas.ComplexDoubleMatrix;

public abstract class QuantumGate {
    protected ComplexDoubleMatrix gate;
    protected ComplexDoubleMatrix cgate;


    public ComplexDoubleMatrix applyTo(ComplexDoubleMatrix c) {
        System.out.println("here comes the interesting bit");
        System.out.println(c.rows);
        System.out.println(c.columns);
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
        while (i < qubits ) {
            this.gate = tensorProduct(this.gate, g.gate);
            i++;
        }
        this.gate = this.cgate = m;
        }


    /*private ComplexDouble[] tensorProduct(ComplexDouble[] q1, ComplexDouble[] q2) {
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
    */


    private ComplexDoubleMatrix tensorProduct(ComplexDoubleMatrix q1, ComplexDoubleMatrix q2) {
        ComplexDoubleMatrix c = new ComplexDoubleMatrix(q1.length, q2.length);
        for (int i = 0; i < q1.length; i++) {
            for (int j = 0; j < q2.length; j++) {
                c.put(i, j, q1.toArray()[i].mul(q2.toArray()[j]));
            }
        }
        return c;
    }


}
