package gates.pauli;

import gates.PhaseShift;

public class ZGate extends PhaseShift {

// the ZGate is a phase shifter for theta = pi

    ZGate() {
        super(Math.PI);
    }

    ZGate(int qubits) {
        super(qubits, Math.PI);
    }

}
