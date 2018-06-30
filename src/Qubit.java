import gates.QuantumGate;
import org.jblas.ComplexDouble;
import org.jblas.ComplexDoubleMatrix;

import java.util.InvalidPropertiesFormatException;
import java.util.Random;

public class Qubit {
    private static final double ALLOWABLE_QUBIT_ERROR = .005;
    //sometimes need more than double precision.
    private final int RANDOM_RANGE; //
    private ComplexDoubleMatrix state;

    public Qubit(ComplexDoubleMatrix state) {
        //assert state.length == 2 : "superposition of two states analogous to classical bits";
        this.state = state;
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
        double sum = 0;

        for (ComplexDouble d : state.toArray()) {
            sum += (d.mul(d).abs());
        }

        return sum > 1 - ALLOWABLE_QUBIT_ERROR
                && sum < 1 + ALLOWABLE_QUBIT_ERROR;  // handle error
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

    /* public Qubit entangle(Qubit q2) {
        ComplexDouble[] tensorData
                = new ComplexDouble[q2.state.toArray().length * this.state.toArray().length];

        for (int i = 0; i < this.state.toArray().length; i++) {
            for (int j = 0; j < q2.state.toArray().length; j++) {
                tensorData[i * q2.state.toArray().length + j]
                        = this.state.toArray()[i].mul(q2.state.toArray()[j]);
            }
        }
        return new Qubit(tensorData);
    }
    */

    public Qubit entangle(Qubit q2) {
        ComplexDoubleMatrix tensorData
                = new ComplexDoubleMatrix(this.getState().rows * q2.getState().rows
                , q2.state.columns * this.getState().columns);

        if (tensorData.columns == 1) {

            for (int i = 0; i < this.getState().rows; i++) {
                for (int k = 0; k < q2.getState().rows; k++) {
                    for (int j = 0; j < this.getState().columns; j++) {
                        for (int l = 0; l < q2.getState().columns; l++) {
                            tensorData.put(i + l
                                    , 0
                                    , getState().get(i, j)
                                            .mul(q2.getState().get(k, l)));


                        }
                    }
                }

            }
        } else {
            // for combining a pre-entangled qubit with another one
            for (int i = 0; i < this.getState().rows; i++) {
                for (int k = 0; k < q2.getState().rows; k++) {
                    for (int j = 0; j < this.getState().columns; j++) {
                        for (int l = 0; l < q2.getState().columns; l++) {
                            tensorData.put(i + l
                                    , j + k
                                    , getState().get(i, j)
                                            .mul(q2.getState().get(k, l)));


                        }
                    }
                }

            }

        }
        return new Qubit(tensorData);
    }

    public void applyGate(QuantumGate q) {
        this.state = q.applyTo(getState());
    }

    @Override
    public boolean equals(Object o) {
        assert o instanceof Qubit;
        if (((Qubit) o).getState().length != getState().length) {
            return false;
        } else {
            for (int i = 0; i < getState().length; i++) {
                if (!getState().toArray()[i].eq(((Qubit) o).getState().toArray()[i])) {

                    return false;
                }
            }

            return true;
        }
    }


}
