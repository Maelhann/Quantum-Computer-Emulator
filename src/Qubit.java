import org.jblas.ComplexDoubleMatrix;

import java.util.concurrent.ThreadLocalRandom;

public class Qubit {
    private static final int ALLOWABLE_QUBIT_ERROR = 1;
    private static final int RANDOM_RANGE = 1;
    ComplexDoubleMatrix state;

    Qubit(ComplexDoubleMatrix state) {
        this.state = state;
    }


    boolean isValid() {
        double sum = 0;
        for (Double d : state.data) {
            sum += d;
        }
        return Math.abs(sum - 1) < ALLOWABLE_QUBIT_ERROR;
    }


    boolean measure(int qubit) {
        assert isValid();
        double cursor = ThreadLocalRandom.current().nextDouble(0, RANDOM_RANGE + 1);
        for (int i = 0; i < state.length; i++) {
            cursor -= state.data[i];
            if (cursor <= 0) {
                collapse(i);
                return getBit(i, qubit); // what does getBit do >??/
            }
        }
        return false;
        }


    int collapse(int entry) {
        for (int i = 0; i < state.length; i++) {
            state.data[i] = 0;
        }
        state.data[entry] = 1;
        return entry;
    }


}
