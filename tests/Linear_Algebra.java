import org.jblas.ComplexDoubleMatrix;
import org.junit.Test;

public class Linear_Algebra {
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
}
