package gates;

import org.jblas.ComplexDoubleMatrix;

public class Swap extends QuantumGate {

    public Swap() {
        ComplexDoubleMatrix swapGate = new ComplexDoubleMatrix(4, 4);
        swapGate.put(0, 0, 1);
        swapGate.put(3, 3, 1);
        swapGate.put(1, 2, 1);
        swapGate.put(2, 1, 1);
        this.gate = this.cgate = swapGate;
        this.type = "Swap" ;
    }

    @Override
    public void scaleGate(int qubits, QuantumGate g) {
        System.out.println("can only swap two qubits :( ");
    }


}
