import org.jblas.ComplexDoubleMatrix;
import org.junit.Test;

public class Linear_Algebra {
    @Test
    public void kronecker1() {
        ComplexDoubleMatrix m1 = new ComplexDoubleMatrix(2, 2);
        m1.put(0, 0, 1);
        m1.put(0, 1, 2);
        m1.put(1, 0, 3);
        m1.put(1, 1, 4);

        ComplexDoubleMatrix m2 = new ComplexDoubleMatrix(2,2);
        m2.put(0,0,0);
        m2.put(0,1,5);
        m2.put(1,0,6);
        m2.put(1,1,7);

        ComplexDoubleMatrix res = new ComplexDoubleMatrix(4,4);
        res.put(0,0, 0);
        res.put(0,1, 5);
        res.put(0,2, 0);
        res.put(0,3, 10);

        res.put(1,0, 6);
        res.put(1,1, 7);
        res.put(1,2, 12);
        res.put(1,3, 14);

        res.put(2,0, 0);
        res.put(2,1, 15);
        res.put(2,2, 0);
        res.put(2,3, 20);

        res.put(3,0, 18);
        res.put(3,1, 21);
        res.put(3,2, 24);
        res.put(3,3, 28);

        tensorProduct(m1,m2).print();
        res.print();

        assert tensorProduct(m1,m2) == res ;

    }

    private ComplexDoubleMatrix tensorProduct(ComplexDoubleMatrix q1, ComplexDoubleMatrix q2) {
        ComplexDoubleMatrix tensorData = new ComplexDoubleMatrix(q1.rows*q2.rows
                , q2.columns*q1.columns);
        for (int i = 0; i < q1.rows; i++) {
            for (int k = 0; k < q2.rows; k++) {
                for (int j = 0; j < q1.columns; j++) {
                    for (int l = 0; l < q2.columns; l++) {
                        tensorData.put(i + l
                                , j + k
                                , q1.get(i, j)
                                        .mul(q2.get(k, l)));


                    }
                }
            }

        }

        return tensorData ;
    }
}
