import org.jblas.ComplexDouble;
import org.jblas.ComplexDoubleMatrix;
import org.junit.Test;

public class Qubit_Utilities {
    @Test
    public void kronecker() {
        ComplexDoubleMatrix m1 = new ComplexDoubleMatrix(2, 2);
        m1.put(0, 0, 1);
        m1.put(0, 1, 2);
        m1.put(1, 0, 3);
        m1.put(1, 1, 4);

        ComplexDoubleMatrix m2 = new ComplexDoubleMatrix(2, 2);
        m2.put(0, 0, 0);
        m2.put(0, 1, 5);
        m2.put(1, 0, 6);
        m2.put(1, 1, 7);

        ComplexDoubleMatrix res = new ComplexDoubleMatrix(4, 4);
        res.put(0, 0, 0);
        res.put(0, 1, 5);
        res.put(0, 2, 0);
        res.put(0, 3, 10);

        res.put(1, 0, 6);
        res.put(1, 1, 7);
        res.put(1, 2, 12);
        res.put(1, 3, 14);

        res.put(2, 0, 0);
        res.put(2, 1, 15);
        res.put(2, 2, 0);
        res.put(2, 3, 20);

        res.put(3, 0, 18);
        res.put(3, 1, 21);
        res.put(3, 2, 24);
        res.put(3, 3, 28);

        tensorProduct(m1, m2).print();

        res.print();
        assert  tensorProduct(m1,m2).equals(res) ;

    }

    private ComplexDoubleMatrix tensorProduct(ComplexDoubleMatrix q1, ComplexDoubleMatrix q2) {
        ComplexDoubleMatrix tensorData = new ComplexDoubleMatrix(q1.rows * q2.rows
                , q2.columns * q1.columns);
        ComplexDoubleMatrix[][] cq =
                new ComplexDoubleMatrix[q1.rows][q1.columns];

        for (int i = 0; i < q1.rows; i++) {
            for (int j = 0; j < q1.columns; j++) {
                cq[i][j] = q2.mul(q1.get(i, j));
                for (int k = 0; k < q2.rows; k++) {
                    for (int p = 0; p < q2.columns; p++) {
                        tensorData.put(q2.rows * i + k, j * q2.columns + p
                                , cq[i][j].get(k, p));

                    }
                }

            }
        }

        // now cq has all the matrices needed by tensor Data


        return tensorData;
    }

    @Test
    public void entanglement(){

        ComplexDoubleMatrix bra0ket
                = new ComplexDoubleMatrix(2, 1);

        bra0ket.put(0, 0, 1);
        bra0ket.put(1, 0, 0);
        ComplexDoubleMatrix bra1ket
                = new ComplexDoubleMatrix(2, 1);

        bra1ket.put(0, 0, 0);
        bra1ket.put(1, 0, 1);

        Qubit q0 = new Qubit(bra0ket);
        Qubit q1 = new Qubit(bra1ket);

        Qubit q3 =  q0.entangle(q1);
        Qubit q4 = q1.entangle(q0);



        assert !q3.equals(q4);
        ComplexDoubleMatrix entangled1 = new ComplexDoubleMatrix(4,1);
        ComplexDoubleMatrix entangled2 = new ComplexDoubleMatrix(4,1);

        entangled1.put(1,0,1);
        entangled2.put(2,0,1);
        q3.getState().print();
        q4.getState().print();
        assert q3.getState().equals(entangled1);
        assert q4.getState().equals(entangled2);

    }


    @Test
    public void validity(){

        // very basic tests for checking the validity of basis and combined qubits
        ComplexDoubleMatrix bra0ket
                = new ComplexDoubleMatrix(2, 1);

        bra0ket.put(0, 0, 1);
        bra0ket.put(1, 0, 0);
        ComplexDoubleMatrix bra1ket
                = new ComplexDoubleMatrix(2, 1);

        bra1ket.put(0, 0, 0);
        bra1ket.put(1, 0, 1);

        Qubit q0 = new Qubit(bra0ket);
        Qubit q1 = new Qubit(bra1ket);

        Qubit q3 =  q0.entangle(q1);
        Qubit q4 = q1.entangle(q0);

        Qubit q5 = q3.entangle(q4);
        Qubit q6 = q4.entangle(q3);

        assert q0.isValid();
        assert q1.isValid();
        assert q3.isValid();
        assert q4.isValid();
        assert q5.isValid();
        assert q6.isValid();

        // example for a valid Qubit pulled from Jocy Allcock's
        // https://www.doc.ic.ac.uk/teaching/distinguished-projects/2010/s.allcock.pdf
        // page 9 on Qubits.

        ComplexDoubleMatrix validData = new ComplexDoubleMatrix(2,1);
        validData.put(0,0,new ComplexDouble(2,3).div(Math.sqrt(327)));
        validData.put(1,0,new ComplexDouble(17,5).div(Math.sqrt(327)));

        Qubit measurableQubit = new Qubit(validData);
        assert measurableQubit.isValid();

        ComplexDoubleMatrix invalidData = new ComplexDoubleMatrix(2,1);
        invalidData.put(0,0,new ComplexDouble(7,7).div(Math.sqrt(168)));
        invalidData.put(1,0,new ComplexDouble(9,3).div(Math.sqrt(177)));

        Qubit immeasurableQubit = new Qubit(invalidData);
        assert !immeasurableQubit.isValid();



        }
}
