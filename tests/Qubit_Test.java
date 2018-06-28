import gates.pauli.XGate;
import org.jblas.ComplexDoubleMatrix;
import org.junit.Test;


public class Qubit_Test {
    @Test
    public void XGate() {
        ComplexDoubleMatrix bra0ket = new ComplexDoubleMatrix(2, 1);

        bra0ket.put(0, 0, 1);
        bra0ket.put(1, 0, 0);
        ComplexDoubleMatrix bra1ket = new ComplexDoubleMatrix(2, 1);

        bra1ket.put(0, 0, 0);
        bra1ket.put(1, 0, 1);

        Qubit q0 = new Qubit(bra0ket);
        Qubit q1 = new Qubit(bra1ket);

        assert q0.isValid();
        assert q1.isValid();

        XGate n = new XGate();
        q0.applyGate(n);
        q0.getState().print();
        q1.getState().print();

        assert q1.equals(q0);
        n.applyTo(q1.getState());
        n.applyTo(q0.getState());
        assert q1.equals(q0);


        XGate n2 = new XGate(2);
        Qubit q3 = q0.combine(q1);
        assert q3.isValid();
        Qubit q4 = new Qubit(q3.getState());
        q3.getState().print();
        q3.applyGate(n2);
        q3.getState().print();
        assert q3.isValid();

        assert !q3.equals(q4);
        q3.applyGate(n2);
        assert q3.equals(q4);


    }

}
