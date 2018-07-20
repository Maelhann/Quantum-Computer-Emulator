package gates;

import org.jblas.ComplexDoubleMatrix;

public abstract class QuantumGate {
    protected ComplexDoubleMatrix gate;
    protected ComplexDoubleMatrix cgate;
    protected String type ;
    public ComplexDoubleMatrix getGate() {
        return gate;
    }

    public ComplexDoubleMatrix getCgate() {
        return cgate;
    }

    public void printType(){
        System.out.println(this.type);
    }

    public ComplexDoubleMatrix applyTo(ComplexDoubleMatrix c) {
        assert gate.columns == c.rows;
        return gate.mmul(c);
    }

    public ComplexDoubleMatrix applyControlledTo(ComplexDoubleMatrix c) {
        assert gate.columns == c.rows;
        return cgate.mmul(c);
    }

    public void scaleGate(int qubits, QuantumGate g) {
        // scales up our gate to handle n-qubit inputs
        int i = 1;
        ComplexDoubleMatrix m = this.gate;
        ComplexDoubleMatrix cm = g.gate;
        while (i < qubits) {
            m = tensorProduct(this.gate, g.gate);
            i++;
        }

        if(g instanceof Hadamard){
            m.mul(Math.pow(1/Math.sqrt(2),qubits - 1 )) ;
        }
        this.gate = this.cgate = m;
    }


    // IMPLEMENTATION OF THE KRONECKER TENSOR-PRODUCT FOR JBLAS
    // note

    private ComplexDoubleMatrix tensorProduct(ComplexDoubleMatrix q1, ComplexDoubleMatrix q2) {
        ComplexDoubleMatrix tensorData = new ComplexDoubleMatrix(q1.rows * q2.rows
                , q2.columns * q1.columns);
        ComplexDoubleMatrix[][] cq =
                new ComplexDoubleMatrix[q1.rows][q1.columns];

        for (int i = 0; i < q1.rows; i++) {
            for (int j = 0; j < q1.columns; j++) {
                cq[i][j] = q2.mul(q1.get(i, j));
                for (int k = 0; k < q2.rows; k++) {
                    for (int p = 0; p < q2.columns; p++) {
                        tensorData.put(q2.rows * i + k, j * q2.columns + p
                                , cq[i][j].get(k, p));

                    }
                }

            }
        }

        // now cq has all the matrices needed by tensor Data


        return tensorData;
    }

}
