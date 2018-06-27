import org.jblas.ComplexDouble;
import org.jblas.ComplexDoubleMatrix;

public class Qubit_Test {

    public static void main(String[] args) {
        ComplexDouble[] doubles = new ComplexDouble[4];
        doubles[0]= new ComplexDouble(1);
        doubles[1] = new ComplexDouble(0);
        doubles[2] = new ComplexDouble(1,1);
        doubles[3] = new ComplexDouble(0,1);
        ComplexDoubleMatrix state = new ComplexDoubleMatrix(doubles);
        ComplexDouble d = new ComplexDouble(Math.cos(Math.PI),Math.sin(Math.PI));
        System.out.println(d);


          }

}
