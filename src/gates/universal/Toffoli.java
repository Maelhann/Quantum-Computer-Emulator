package gates.universal;

import gates.QuantumGate;
import org.jblas.ComplexDouble;
import org.jblas.ComplexDoubleMatrix;

public class Toffoli extends QuantumGate {
    public Toffoli(){
        ComplexDoubleMatrix toffoliGate
                = new ComplexDoubleMatrix(8, 8);
        for (int i = 0; i < 6; i++) {
            toffoliGate.put(i, i, 1);
        }
        toffoliGate.put(6, 7, 1);
        toffoliGate.put(7, 6, 1);
        this.gate = this.cgate = toffoliGate;
        }


    @Override
    public void scaleGate(int qubits, QuantumGate g){
        System.out.println("attempt to scale a universal gate (Toffoli) for input size :" + qubits + " qubits ");
    }
}
