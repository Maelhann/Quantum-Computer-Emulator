import org.jblas.ComplexDoubleMatrix;

public class Qubit_Test {

    public static void main(String[] args) {
        double[] data = {0, 1};
        ComplexDoubleMatrix state = new ComplexDoubleMatrix(data);
        Qubit qubit = new Qubit(state);
        System.out.println(qubit.isValid());
        qubit.measure(0);

    }

}
