import gates.QuantumGate;
import org.jblas.ComplexDouble;
import org.jblas.ComplexDoubleMatrix;

import java.security.InvalidAlgorithmParameterException;
import java.util.Random;

public class Qubit {
    private static final double ALLOWABLE_QUBIT_ERROR = .005;
    //sometimes need more than double precision.
    private ComplexDoubleMatrix state;

    public Qubit(ComplexDoubleMatrix state) {
        //assert state.length == 2 : "superposition of two states analogous to classical bits";
        this.state = state;
        Random rand = new Random();


    }

    public static double getAllowableQubitError() {
        return ALLOWABLE_QUBIT_ERROR;
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

    public int getDimension(){
       return  (int)Math.log(getState().columns);
    }


    //For the measurement, we're going to go off directly given the probabilities of the initial state
    public void measure() {
        assert isValid() : "qubit must be in a valid state "; // add normalization function

        Random rand = new Random();
        double cursor = rand.nextDouble();
        while (cursor < 0 || cursor > 1) {
            cursor = rand.nextDouble();
        }
        for (int i = 0; i < getState().rows; i++) {
            for (int j = 0; j < getState().columns; j++) {
                cursor -= getState().get(i,j).abs();
                if (cursor <= 0) {
                    collapse(i,j);
                    System.out.println("bit" + "( "
                            + i + "," + j + " )" + " measured at 1");
                }
            }
        }

        try {
            throw new InvalidAlgorithmParameterException("invalid measurement");
        } catch (InvalidAlgorithmParameterException e) {
            // caught invalid measurements
        }


    }


    private void collapse(int row,int col) {
        for (int i = 0; i < state.getLength(); i++) {
            state.toArray()[i].set(0, 0);
        }
        state.put(row,col,1);

    }

    public Qubit entangle(Qubit q2) {
        ComplexDoubleMatrix tensorData
                = new ComplexDoubleMatrix(this.getState().rows
                * q2.getState().rows
                , q2.getState().columns * this.getState().columns);
        ComplexDoubleMatrix[][] cq =
                new ComplexDoubleMatrix[this.getState().rows][this.getState().columns];

        for (int i = 0; i < this.getState().rows; i++) {
            for (int j = 0; j < this.getState().columns; j++) {
                cq[i][j] = q2.getState().mul(this.getState().get(i, j));
                for (int k = 0; k < q2.getState().rows; k++) {
                    for (int p = 0; p < q2.getState().columns; p++) {
                        tensorData.put(q2.getState().rows * i + k,
                                j * q2.getState().columns + p
                                , cq[i][j].get(k, p));

                    }
                }

            }
        }

        // now cq has all the matrices needed by tensor Data


        return new Qubit(tensorData);

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
    */

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
