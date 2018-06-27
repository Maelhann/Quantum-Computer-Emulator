package gates;

import org.jblas.ComplexDouble;
import org.jblas.ComplexDoubleMatrix;

public class Hadamard extends QuantumGate {

    Hadamard() {
        ComplexDouble[] xData = new ComplexDouble[4];
        xData[0].set(1, 0);
        xData[1].set(1, 0);
        xData[2].set(1, 0);
        xData[3].set(-1, 0);
        this.gate = this.cgate =
                new ComplexDoubleMatrix(xData).mmul(1 / Math.sqrt(2));


    }

    // constructor for n-qubit inputs
    Hadamard(int qubits) {
        ComplexDouble[] xData = new ComplexDouble[4];
        xData[0].set(1, 0);
        xData[1].set(1, 0);
        xData[2].set(1, 0);
        xData[3].set(-1, 0);
        this.gate = this.cgate =
                new ComplexDoubleMatrix(xData).mmul(1 / Math.sqrt(2));

        scaleGate(qubits, new Hadamard());

    }


}
