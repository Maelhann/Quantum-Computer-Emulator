package gates;

import org.jblas.ComplexDoubleMatrix;

public class Hadamard extends QuantumGate {

    public Hadamard() {

        ComplexDoubleMatrix hadamard = new ComplexDoubleMatrix(2, 2);
        hadamard.put(0, 0, 1);
        hadamard.put(0, 1, 1);
        hadamard.put(1, 0, 1);
        hadamard.put(1, 1, -1);
        hadamard.mul(1 / Math.sqrt(2));
        this.gate = hadamard;


        // this is an attempt to build a Hadamard gate that takes
        // a Control input like the C-Pauli gates -- coz why not
        ComplexDoubleMatrix chadamard = new ComplexDoubleMatrix(4, 4);
        chadamard.put(0, 0, 1);
        chadamard.put(1, 1, 1);
        chadamard.put(2, 2, 1);
        chadamard.put(2, 3, 1);
        chadamard.put(3, 2, 1);
        chadamard.put(3, 3, -1);
        chadamard.mul(1/Math.sqrt(2));

        this.cgate = chadamard;


    }

    // constructor for n-qubit inputs
    public Hadamard(int qubits) {
        ComplexDoubleMatrix hadamard = new ComplexDoubleMatrix(2, 2);
        hadamard.put(0, 0, 1);
        hadamard.put(0, 1, 1);

        hadamard.put(1, 0, 1);

        hadamard.put(1, 1, -1);
        this.gate = hadamard;

        ComplexDoubleMatrix chadamard = new ComplexDoubleMatrix(4, 4);
        chadamard.put(0, 0, 1);
        chadamard.put(1, 1, 1);
        chadamard.put(2, 2, 1);
        chadamard.put(2, 3, 1);
        chadamard.put(3, 2, 1);
        chadamard.put(3, 3, -1);


        this.cgate = chadamard;
        scaleGate(qubits, new Hadamard());
    }


}
