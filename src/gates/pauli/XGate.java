package gates.pauli;

import gates.QuantumGate;
import org.jblas.ComplexDouble;
import org.jblas.ComplexDoubleMatrix;


public class XGate extends QuantumGate {


    public XGate() {
        ComplexDoubleMatrix xGate = new ComplexDoubleMatrix(2, 2);
        xGate.put(0, 1, 1);
        xGate.put(1, 0, 1);
        this.gate = xGate;
        ComplexDoubleMatrix cxGate = new ComplexDoubleMatrix(4, 4);
        cxGate.put(0, 0, 1);
        cxGate.put(1, 1, 1);
        cxGate.put(2, 3, 1);
        cxGate.put(3, 2, 1);
        this.cgate = cxGate;
    }


    // constructor for n-qubit inputs

    public XGate(int inputs) {
        ComplexDoubleMatrix xGate = new ComplexDoubleMatrix(2, 2);
        xGate.put(0, 1, 1);
        xGate.put(1, 0, 1);
        this.gate = xGate;

        ComplexDoubleMatrix cxGate = new ComplexDoubleMatrix(4, 4);
        cxGate.put(0, 0, 1);
        cxGate.put(1, 1, 1);
        cxGate.put(2, 3, 1);
        cxGate.put(3, 2, 1);
        this.cgate = cxGate;
        scaleGate(inputs,new XGate());
    }
}
