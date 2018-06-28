import gates.pauli.XGate;
import org.jblas.ComplexDouble;
import org.junit.Test;


public class Qubit_Test {
    @Test
    public void test1() {
        ComplexDouble[] bra0ket = new ComplexDouble[2];

        bra0ket[0] = new ComplexDouble(1, 0);
        bra0ket[1] = new ComplexDouble(0, 0);


        ComplexDouble[] bra1ket = new ComplexDouble[2];

        bra1ket[0] = new ComplexDouble(0, 0);
        bra1ket[1] = new ComplexDouble(1, 0);

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

       // TODO : fix scalegate function in QuantumProgramming file

        /* XGate n2 = new XGate(2);
        Qubit q3 = q0.combine(q1);
        q3.getState().print();

        n2.applyTo(q3.getState());
        */



    }

}
