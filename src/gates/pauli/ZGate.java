package gates.pauli;

import gates.PhaseShift;

public class ZGate extends PhaseShift {

// the ZGate is a phase shifter for theta = pi

    public ZGate() {
        super(Math.PI);
    }

    public ZGate(int qubits) {
        super(qubits, Math.PI);
    }

}
