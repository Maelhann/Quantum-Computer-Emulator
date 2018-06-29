package gates.pauli;

import gates.PhaseShift;

public class ZGate extends PhaseShift {

// NOTE : the ZGate is a phaseshift gate where for theta = pi..

    public ZGate() {
        super(Math.PI);
    }

    public ZGate(int qubits) {
        super(qubits, Math.PI);
    }

}
