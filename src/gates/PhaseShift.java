package gates;

import org.jblas.ComplexDouble;
import org.jblas.ComplexDoubleMatrix;

public class PhaseShift extends QuantumGate {
    public PhaseShift(double theta) {
        ComplexDoubleMatrix psGate = new ComplexDoubleMatrix(2, 2);
        psGate.put(0, 0, 1);
        psGate.put(1, 1, new ComplexDouble(Math.cos(theta), Math.sin(theta)));
        this.gate = psGate;
        ComplexDoubleMatrix cpsGate = new ComplexDoubleMatrix(4, 4);
        cpsGate.put(0, 0, 1);
        cpsGate.put(1, 1, 1);
        cpsGate.put(2, 2, 1);
        cpsGate.put(3, 3, new ComplexDouble(Math.cos(theta), Math.sin(theta)));
        this.cgate = cpsGate;
        this.type = "PhaseShift" + theta ;

    }

    public PhaseShift(int qubits, double theta) {
        ComplexDoubleMatrix psGate = new ComplexDoubleMatrix(2, 2);
        psGate.put(0, 0, 1);
        psGate.put(1, 1, new ComplexDouble(Math.cos(theta), Math.sin(theta)));
        this.gate = psGate;
        ComplexDoubleMatrix cpsGate = new ComplexDoubleMatrix(4, 4);
        cpsGate.put(0, 0, 1);
        cpsGate.put(1, 1, 1);
        cpsGate.put(2, 2, 1);
        cpsGate.put(3, 3, new ComplexDouble(Math.cos(theta), Math.sin(theta)));
        this.cgate = cpsGate;

        scaleGate(qubits, new PhaseShift(theta));

    }


}
