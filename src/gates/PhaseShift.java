package gates;

import org.jblas.ComplexDouble;
import org.jblas.ComplexDoubleMatrix;

public class PhaseShift extends QuantumGate {
     protected PhaseShift(double theta) {
        ComplexDouble[] phaseShift = new ComplexDouble[4];
        ComplexDouble d = new ComplexDouble(Math.cos(theta), Math.sin(theta));
        phaseShift[0].set(1, 0);
        phaseShift[1].set(0, 0);
        phaseShift[2].set(0, 0);
        phaseShift[3].set(d.real(), 0);
        this.gate = new ComplexDoubleMatrix(phaseShift);

        ComplexDouble[] cphaseShift = new ComplexDouble[16];
        for (int i = 0; i < 16; i++) {
            if (i == 0 || i == 5) {
                cphaseShift[i].set(1, 0);
            } else {
                cphaseShift[i].set(0, 0);
            }
        }
        cphaseShift[10].set(1, 0);
        cphaseShift[15].set(d.real(), 0);

        this.cgate = new ComplexDoubleMatrix(cphaseShift);
    }

    protected PhaseShift(int qubits, double theta) {
        ComplexDouble[] phaseShift = new ComplexDouble[4];
        ComplexDouble d = new ComplexDouble(Math.cos(theta), Math.sin(theta));
        phaseShift[0].set(1, 0);
        phaseShift[1].set(0, 0);
        phaseShift[2].set(0, 0);
        phaseShift[3].set(d.real(), 0);
        this.gate = new ComplexDoubleMatrix(phaseShift);

        ComplexDouble[] cphaseShift = new ComplexDouble[16];
        for (int i = 0; i < 16; i++) {
            if (i == 0 || i == 5) {
                cphaseShift[i].set(1, 0);
            } else {
                cphaseShift[i].set(0, 0);
            }
        }
        cphaseShift[10].set(1, 0);
        cphaseShift[15].set(d.real(), 0);

        this.cgate = new ComplexDoubleMatrix(cphaseShift);
        scaleGate(qubits, new PhaseShift(theta)); // tantamount to "qubit" rotations ? (for d)
    }


}
