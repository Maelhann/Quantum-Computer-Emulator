import org.jblas.ComplexDouble;
import org.jblas.ComplexDoubleMatrix;

import java.util.concurrent.ThreadLocalRandom;

class Qubit {
    private static final int ALLOWABLE_QUBIT_ERROR = 1;
    private static final int RANDOM_RANGE = 1;
    private ComplexDoubleMatrix state;

    Qubit(ComplexDouble[] state) {
        assert state.length == 2 : "superposition of two states analogous to classical bits";
        ComplexDoubleMatrix m = new ComplexDoubleMatrix(state);
    }

    public static int getAllowableQubitError() {
        return ALLOWABLE_QUBIT_ERROR;
    }

    public static int getRandomRange() {
        return RANDOM_RANGE;
    }

    public ComplexDoubleMatrix getState() {
        return state;
    }

    public boolean isValid() {
        ComplexDouble sum = new ComplexDouble(0);
        ComplexDouble witness = new ComplexDouble(1);
        for (ComplexDouble d : state.toArray()) {
            sum.add(d.mul(d));
        }
        return sum.eq(witness);
        }


    public int measure(int qubit) {
        assert isValid();
        double cursor = ThreadLocalRandom.current().nextDouble(0, RANDOM_RANGE + 1);
        for (int i = 0; i < state.length; i++) {
            cursor -= state.data[i];
            if (cursor <= 0) {
                getState().print();
                collapse(i);
                return (qubit >> (i - 1)) & 1;
            }
        }
        try {
            throw new InterruptedException("invalid measurement");
        } catch (InterruptedException e) {
            // error catching for invalid qubit measurements
        }
        return -1;
    }


    private int collapse(int entry) {
        for (int i = 0; i < state.length; i++) {
            state.data[i] = 0;
        }
        state.data[entry] = 1;
        return entry;
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
