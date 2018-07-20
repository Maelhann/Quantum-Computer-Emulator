package gates.universal;

import gates.QuantumGate;
import org.jblas.ComplexDoubleMatrix;

public class Fredkin extends QuantumGate {
    public Fredkin() {
        ComplexDoubleMatrix fredkinGate
                = new ComplexDoubleMatrix(8, 8);
        for (int i = 0; i < 5; i++) {
            fredkinGate.put(i, i, 1);
        }
        fredkinGate.put(7, 7, 1);
        fredkinGate.put(5, 6, 1);
        fredkinGate.put(6, 5, 1);
        this.gate = this.cgate = fredkinGate;
    }

    @Override
    public void scaleGate(int qubits, QuantumGate g) {
        System.out.println("attempt to scale a universal gate (Fredkin) for input size :" + qubits + " qubits ");
    }

}
