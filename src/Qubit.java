import org.jblas.ComplexDouble;
import org.jblas.ComplexDoubleMatrix;

import java.util.InvalidPropertiesFormatException;
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

    public int measure(int position) {
        assert isValid() : "qubit must be in a valid state "; // add normalization function
        Random rand = new Random();
        double cursor;
        do {
            cursor = rand.nextDouble();
        } while (cursor > RANDOM_RANGE);

        for (int i = 0; i < state.toArray().length; i++) {
            cursor -= state.toArray()[i]
                    .mul(state.toArray()[i])
                    .mul(RANDOM_RANGE).abs();
            if (cursor <= 0) {
                collapse(i);
                return ((i >> (position - 1)) & 1);

            }
        }
        try {
            throw new InvalidPropertiesFormatException("Invalid measurement @ qubit " + position);
        } catch (InvalidPropertiesFormatException e) {
            // catching invalid measurements
        }
        return -1;
    }


    private void collapse(int entry) {
        for (int i = 0; i < state.toArray().length; i++) {
            state.toArray()[i].set(0, 0);
        }
        state.toArray()[entry].set(1, 0);

    }

    public ComplexDouble[] combine(ComplexDouble[] q1, ComplexDouble[] q2) {
        ComplexDouble[] tensorData
                = new ComplexDouble[q2.length * q1.length];
        for (int i = 0; i < q1.length; i++) {
            for (int j = 0; j < q2.length; j++) {
                tensorData[i * q2.length + j]
                        = q1[i].mul(q2[j]);
            }
        }
        return tensorData;
    }


}
