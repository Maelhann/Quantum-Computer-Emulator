package gates;

import org.jblas.ComplexDoubleMatrix;

public abstract class QuantumGate {
    protected ComplexDoubleMatrix gate;
    protected ComplexDoubleMatrix cgate;


    public ComplexDoubleMatrix applyTo(ComplexDoubleMatrix c) {
        assert gate.columns == c.rows;
        return gate.mmul(c);
    }

    public ComplexDoubleMatrix applyControlledTo(ComplexDoubleMatrix c) {
        assert gate.columns == c.rows;
        return cgate.mmul(c);
    }

    protected void scaleGate(int qubits, QuantumGate g) {
        // scales up our gate to handle n-qubit inputs
        int i = 0;
        ComplexDoubleMatrix m = this.gate;
        ComplexDoubleMatrix cm = g.gate;
        while (i < qubits) {
            this.gate = tensorProduct(this.gate, g.gate);
            i++;
        }
        this.gate = this.cgate = m;
    }


    // IMPLEMENTATION OF THE KRONECKER TENSOR-PRODUCT FOR JBLAS
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
