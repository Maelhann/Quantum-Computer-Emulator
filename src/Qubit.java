import org.jblas.ComplexDouble;
import org.jblas.ComplexDoubleMatrix;

import java.util.Random;

class Qubit {
    private static final double ALLOWABLE_QUBIT_ERROR = .005;
    //sometimes need more than double precision.
    private final int RANDOM_RANGE; //
    private ComplexDoubleMatrix state;

    Qubit(ComplexDouble[] state) {
        assert state.length == 2 : "superposition of two states analogous to classical bits";
        this.state = new ComplexDoubleMatrix(state);
        Random rand = new Random();
        this.RANDOM_RANGE = rand.nextInt();

    }

    public static double getAllowableQubitError() {
        return ALLOWABLE_QUBIT_ERROR;
    }

    public int getRandomRange() {
        return RANDOM_RANGE;
    }

    public ComplexDoubleMatrix getState() {
        return state;
    }

    // checking that the sum of all complex factors is 1
    public boolean isValid() {
        ComplexDouble sum = new ComplexDouble(0);
        ComplexDouble witness = new ComplexDouble(1);
        for (ComplexDouble d : state.toArray()) {
            sum.add(d.mul(d).abs());
        }
        return sum.abs() > witness.abs() - ALLOWABLE_QUBIT_ERROR
                && sum.abs() < witness.abs() + ALLOWABLE_QUBIT_ERROR;  // handle error
    }


    // needs to yield 1 with prob alpha^2 or 0 with prob beta^2 for a SINGLE QUBIT
    // probabilistically measure for work with more than two superposed state --
    // hence the use of random.

    public int measure() {
        assert isValid() : "qubit must be in a valid state "; // add normalization function
        Random rand = new Random();
        double cursor;
        do {
            cursor = rand.nextDouble();
        } while (cursor > RANDOM_RANGE);

        for (int i = 0 ; i < state.toArray().length ; i++) {
            cursor -= state.toArray()[i]
                    .mul(state.toArray()[i])
                    .mul(RANDOM_RANGE).abs() ;
            if (cursor <= 0) {
                collapse(i);
                return  ;     // the bit you collapsed

            }
        }

    }


    private void collapse(int entry) {
        for (int i = 0; i < state.toArray().length; i++) {
            state.toArray()[i].set(0,0);
        }
        state.toArray()[entry].set(1,0);

    }

    public Qubit combine(Qubit q2) {
        double[] tensorData
                = new double[q2.state.length * this.state.length];
        for (int i = 0; i < this.state.length; i++) {
            for (int j = 0; j < q2.state.length; j++) {
                tensorData[i * q2.state.length + j]
                        = this.state.data[i] * q2.state.data[j];
            }
        }
        ComplexDoubleMatrix newState = new ComplexDoubleMatrix(tensorData);
        return new Qubit(newState);
    }


}
