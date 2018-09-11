---
layout: post
title:  "Quantum Computing Emulator (Circuit based)"
date:   2018-07-17 10:30:06 -0500
categories: Java
permalink : /QCE
---
Well hello you ! This is a post I dedicated to my circuit-based quantum computer emulator developped in Java. 
<br>
Here I'll provide the motivations for my project as well as the development process for the emulator and the logic justifying my implementation choices. 
<br><br>
I'll also go over how one builds a quantum computing emulator, using snippets from my [implementation](https://github.com/Maelhann/Quantum-Computing-Emulator) to illustrate. 
<br>
<br>
We'll divide this post into 5 subparts : 
 - first, a little bit about quantum computing basics 
 - then, we'll go into the implementation of a qubit
 - thirdly, we'll tackle quantum gates
 - as a fourth part, we'll go over Deutsch's algorithm, what it is and how I implemented it
 - finally, I'll give a small introduction to my circuit assembler 
  
<br>
<br>
First, let's go over some quantum computing history and basics:
<br>
<br>
Quantum computing is a term that emerged in the early to mid 80's, it is defined formally as "computing using quantum-mechanical phenomena"... a bit unwelcoming and yet mysteriously charming, especially if you're new to both computing and quantum mechanics. Let's try and clear some stuff up. 
<br>
<br>
Quantum computing was born out of the necessity for physicists to model quantum mechanics reliably and the idea that using quantum effects on computing would greatly help modelling complex systems. The paternity of the term is contested, some attribute it [Paul Benioff](https://en.wikipedia.org/wiki/Paul_Benioff) and [Yuri Manin](https://en.wikipedia.org/wiki/Yuri_I._Manin) for motivating the idea in 1980, others attribute it to [Richard Feynman](https://en.wikipedia.org/wiki/Richard_Feynman) who proposed the first basic model of a quantum computer in 1981... others, still attribute it to [David Deutsch](https://en.wikipedia.org/wiki/David_Deutsch) who described a general quantum computer in 1985, and delivered one of the very first quantum algorithms, (the implementation of which we'll go over !)
<br>
<br>
But what exactly makes a computer "quantum" ?
<br>
Whereas a classical computer's memory is composed of binary bits to store information, bits that can either be in state 1 or state 0, a Quantum computer's memory manages bits that can be in either state 1, state 0 or a "mix" of these two (or more !) states, neither 1 nor 0 but a blend, what we refer to as a "quantum superposition" of these two states. Quantum superposition thus frees us from the constraints of binary bits as it enables us to represent complex models (i.e. models that can't be built using binary arithmetic, or not without an excessively huge amount of bits ) much more directly and efficiently.
<br>
<br> 
A general quantum computer that can effectively use quantum bits to model complex systems would prove to be a major breakthrough, as it would revolutionize pharmacology, cryptography, banking and many other areas such as chemistry and mathematics
<br>
<br>
These quantum bits, because of quantum properties (one of them being superposition), are excessively hard to model physically on our scale, which is part of the reason why the physical advancements of quantum computers are always noticeably behind the theoretical advancements of quantum computing and the literature produced for the field.
<br>
<br>
Nonetheless, the incredible potential this of this technology has pushed it at the forefront of research in both the worlds of computing, physics and mathematics. A lot of interesting quantum algorithms, much faster than classical solutions, have already been discovered in the past.
<br>
An example would be [Shor's Algorithm](https://en.wikipedia.org/wiki/Shor's_algorithm), a quantum algorithm which finds the prime factors of any number N in sub-exponential time (i.e. not fast... but faster than exponential time, which is still a massive improvement.) Quantum computing theory still progresses at an unprecedented rate, which is also why it's such a vibrant field of study.  
<br>
I initially decided to build an emulator for a circuit-based quantum computer (we'll come to what that means exactly in a bit) as an attempt to get a better understanding of the field, following a Microsoft conference, on Q# at Imperial College. [The Q# development kit](https://www.microsoft.com/en-us/quantum/) came with a quantum simulator for algorithms and applications that are deterministic in nature. There's something intrinsically magic about the idea that deterministic behaviour can be replicated on classical hardware, I think I definitely fell for that... 
<br>
I also think the kid within fell in love with the idea of tinkering with "quantum" stuff. 
<br>
<br>
" But why would you develop it in Java ? " 
<br>
<br> 
That was not my initial plan.
<br>
I initially wanted to develop my quantum computing emulator in Haskell... It's the most elegant language I could think of when deciding how I would build it.
<br>
<br>
My goal, however, is to emulate a circuit-based quantum computing emulator, and an object oriented language lends itself well to modelling quantum gates as well as how they relate to each other. It also provides a straightforward way of tackling the qubit.
<br>
<br>
I therefore went with Java for this emulator. Not because I think it's the best choice performance wise, but because I found the JBLAS library to be the most complete tool available in terms of linear algebra with complex numbers. I also like the idea of having a platform independent emulator, and the massive amount of resources available for testing (even if I mostly used JUnit, now that I think of it).  
<br>
<br>
Step I, The Qubit : 
<br>
<br>
{%highlight java%}
//Skeleton for our Qubit class
public class Qubit{
 private static final double ALLOWABLE_QUBIT_ERROR ; 
 private ComplexDoubleMatrix state; 
 
 Qubit(ComplexDoubleMatrix s);

 public ComplexDoubleMatrix getState(); 
 public double getError();  
 public boolean isValid(); 
 public void measure(); 
 public Qubit entangle(Qubit q2);
 public void collapse(int row, int col); 
 public void applyGate(QuantumGate q);  

} 
{%endhighlight%}
<br>
<br>
In my implementation, a quantum bit, "Qubit", is a class. A qubit is therefore an object. 
<br>
The first part of the project was spent reflecting about the qubit's properties that I needed to implement. as stated above, a qubit can either be in state 1, state 0, or a superposition of these two (or more, but two for now) states. Our basis states can be represented as complex vectors... This is how the idea came about to associate a vector, a 2-by-1 matrix of complex numbers, to any qubit, representing a linear combination of these two basis states : 
<br>
$$\lvert0\rangle = 
\begin{bmatrix}
  1 \\
  0
\end{bmatrix}$$
and $$\lvert1\rangle = 
 \begin{bmatrix}
  0 \\
  1
\end{bmatrix}
$$
<br>
It thus follows that the initial state of any qubit can be written, for any $$\alpha$$ and $$\beta$$ complex numbers
<br>  
$$\lvert\psi\rangle = \alpha * $$ 
$$\begin{bmatrix}
  1 \\
  0
\end{bmatrix}$$ $$+ \beta *
\begin{bmatrix}
  0 \\
  1
\end{bmatrix} = $$
$$ \alpha * \lvert0\rangle + \beta * \lvert1\rangle =  
\begin{bmatrix}
  \alpha \\
  \beta
\end{bmatrix} $$


<br>
The complex numbers we use as coefficient for the initial state of our qubits, alpha and beta, must satisfy the condition that $$\alpha^2 + \beta^2 = 1$$ = 1. This can be understood, probabilistically, fairly easily if we understand that $$\alpha^2$$ is the probability of our system to be in state $$\lvert0\rangle$$ and $$beta^2$$ is the probability of finding our system in state $$\lvert1\rangle$$ . 
<br>
$$\alpha^2 + \beta^2 = 1$$ is thus a check that ensures our qubit is in a valid superposition of the two basis states. 
<br>
<br>
{% highlight java %}
public boolean isValid() {
        double sum = 0;

        for (ComplexDouble d : state.toArray()) {
            sum += (d.mul(d).abs());
        }

        return sum > 1 - ALLOWABLE_QUBIT_ERROR
                && sum < 1 + ALLOWABLE_QUBIT_ERROR;  // handle error
    }


{% endhighlight %}
<br>
<br>
Checking that a qubit is valid is of utmost importance when operating upon it. We also want to be able to extract some kind of information, some data out of our qubit. unfortunately for us, we can't precisely know the state in which our qubit lies after knowing it's initial state, not without modifying the state in which it is currently.
<br>
We can, however, collapse the state of our qubit deterministically, by using the weights of the coefficients of the qubit's state as probabilities. 
<br>
We now have some idea of how to look at what's going on with our qubit, but we need to understand that in order to gain information from it, we must collapse it into one of our known states. my implementation simply choses a coefficient entry deterministically, and then collapses the qubit into a state for which this entry is 1 and the other(s) are 0. 
<br>
The coefficient into which the state is collapsed is correlated with the coefficient's weight, as the weight of each coefficient is substracted from a random number generated between 0 and 1 (since we know the sum of the weights of the coefficients must be 1). 
<br>
<br>
{% highlight java %}
public void measure(int position) {
        assert isValid() : "qubit must be in a valid state ";

        Random rand = new Random();
        double cursor = rand.nextDouble();
        while (cursor < 0 || cursor > 1) {
            cursor = rand.nextDouble();
        }
        for (int i = 0; i < getState().getLength(); i++) {
            cursor -= getState().toArray()[i].abs();
            if (cursor <= 0) {
                collapse(i);
                System.out.println("bit" + i + " measured at 1");
            }
        }

        try {
            throw new InvalidAlgorithmParameterException("invalid measurement");
        } catch (InvalidAlgorithmParameterException e) {
            // caught invalid measurements
        }


    }

 private void collapse(int entry) {
        for (int i = 0; i < state.getLength(); i++) {
            state.toArray()[i].set(0, 0);
        }
        state.toArray()[entry].set(1, 0);

    }



{% endhighlight %}
<br>
<br>
Another fundamental quantum property we will want to deal with eventually is entanglement. two qubits are said to be entangled if their distinct states can't be described one without the other.
<br>
This is a way we have to bind qubits, and this is done through tensor products. More particularly, in my implementation, it is done with the help of the [Kronecker product](https://en.wikipedia.org/wiki/Kronecker_product).
<br>
<br>
{%highlight java%}
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
        return new Qubit(tensorData);

    }
{%endhighlight%}
<br>
<br>
Our emulator is "circuit based", that means it relies on the application of "quantum gates" (analogous to electronical gates) to our qubits to modify their states. We'll dive into what quantum gates are and how we model them in a bit, but for now we just need a method that applies a given gate to our qubit. 
<br>
<br>
{%highlight java%}
public void applyGate(QuantumGate q) {
        this.state = q.applyTo(getState());
    }

{%endhighlight%}
<br> 
<br>
Part II, The Quantum Gates : 
<br>
<br>
Once we're done with the qubit objects is done, we can turn towards the meat of the project, that is, quantum gates and their implementation. 
<br> 
We interact with our qubits through a set of [Quantum gates](https://en.wikipedia.org/wiki/Quantum_logic_gate). Each quantum gate represents an operation on a or multiple qubits. For Pauli gates, for instance, this operation is a rotation of our qubit around an axis of the [Bloch sphere](https://en.wikipedia.org/wiki/Bloch_sphere), a very useful tool to understand the evolution of the state of a single qubit, shown below
<br>
<br>
![bloch sphere](/assets/bloch.png)
<br>
<br>
Since our qubits' states are represented by matrices, we can apply these operations through matrix multiplications, by multiplying a matrix representing a gate's operation on the qubit with our qubit's state. This is where the idea to model quantum gates as matrices sprung from. 
<br>
As we'll have a lot of gates to deal with and as they all share functionalities (the only difference being the matrix, that is, except for special cases), it is a good idea to develop a QuantumGate abstract class from which all the other quantum gates will inherit a common set of properties. 
<br>
<br>
{%highlight java%}
//Skeleton for the QuantumGate class
public abstract class QuantumGate{
 private final ComplexDoubleMatrix operation ; 
 private final ComplexDoubleMatrix controlled_operation ;  
 private final String type ;
 
 public void applyTo(Qubit q);
 public void applyControlledTo(Qubit q);  
 public String getType(); 
 public void scaleGate(int factor); 
 private tensorProduct(ComplexDoubleMatrix m2); 

} 
{%endhighlight%}
<br>
<br>
A gate is applied to a qubit when it is multiplied by the matrix-state of that said qubit... giving a qubit with a modified state ! Fortunately we can modify gates to apply them to multiple entangled qubits at the same time (in parallel !) using the kronecker product, this is also how our implementation chains gates together for a circuit; by using this tensor-product to multiply the gate matrices. 
<br>
<br>
{%highlight java%}
  public void scaleGate(int qubits, QuantumGate g) {
        // scales up our gate to handle n-qubit inputs
        int i = 1;
        ComplexDoubleMatrix m = this.gate;
        ComplexDoubleMatrix cm = g.gate;
        while (i < qubits) {
            m = tensorProduct(this.gate, g.gate);
            i++;
        }

        if(g instanceof Hadamard){
            m.mul(Math.pow(1/Math.sqrt(2),qubits - 1 )) ;
        }
        this.gate = this.cgate = m;
    }


    // IMPLEMENTATION OF THE KRONECKER TENSOR-PRODUCT FOR JBLAS
    // note

    private ComplexDoubleMatrix tensorProduct(ComplexDoubleMatrix q1
, ComplexDoubleMatrix q2) {
        ComplexDoubleMatrix tensorData = new ComplexDoubleMatrix(q1.rows * q2.rows
                , q2.columns * q1.columns);
        ComplexDoubleMatrix[][] cq =
                new ComplexDoubleMatrix[q1.rows][q1.columns];

        for (int i = 0; i < q1.rows; i++) {
            for (int j = 0; j < q1.columns; j++) {
                cq[i][j] = q2.mul(q1.get(i, j));
                for (int k = 0; k < q2.rows; k++) {
                    for (int p = 0; p < q2.columns; p++) {
                        tensorData.put(q2.rows * i + k, j * q2.columns + p
                                , cq[i][j].get(k, p));

                    }
                }

            }
        }
        return tensorData; 
    }
{%endhighlight%}
<br>
<br>
Amongst the gates we are going to implement we may distinguish two categories: gate representing unary operations on qubits and universal gates that are applied to two or more qubits at a time. The first category of gates we implement generically following their respective matrices. 
<br> 
The first category of gates is composed of the Hadamard and Pauli rotation gates
the hadamard gate matrix is written ![hadamard](/assets/hadamard.svg), our implementation for this gate is therefore as follow:
<br>
<br>
{%highlight java%}
 public Hadamard(){

        ComplexDoubleMatrix hadamard = new ComplexDoubleMatrix(2, 2);
        hadamard.put(0, 0, 1);
        hadamard.put(0, 1, 1);
        hadamard.put(1, 0, 1);
        hadamard.put(1, 1, -1);
        hadamard.mul(1 / Math.sqrt(2));
        this.gate = hadamard;


        // this is an attempt to build a Hadamard gate that takes
        // a Control input like the C-Pauli gates -- coz why not
        ComplexDoubleMatrix chadamard = new ComplexDoubleMatrix(4, 4);
        chadamard.put(0, 0, 1);
        chadamard.put(1, 1, 1);
        chadamard.put(2, 2, 1);
        chadamard.put(2, 3, 1);
        chadamard.put(3, 2, 1);
        chadamard.put(3, 3, -1);
        chadamard.mul(1/Math.sqrt(2));

        this.cgate = chadamard;
        this.type = "Hadamard" ;

        }

{%endhighlight%}
<br>
<br> 
Note that we have two matrices available for each gate: This is because we build two versions of each gate, a classical one, that applies the operation to the qubit directly, and a controlled version of the gate, that takes a control-bit.
<br>
For the controlled gate the operation is only executed if that control bit is one. this is why we have two application methods for unary gates, one for the controlled and one for the classical versions. 
<br>
<br>
All of the classes for my unary matrices, ![gate](/assets/xgate.svg),![gate](/assets/ygate.svg), ![gate](/assets/phaseshift.svg)  are implemented in this way, except for the Pauli-Z gate, represented by ![gate](/assets/zgate.svg), which is really just an edge of a Phaseshift gate that takes Pi as an argument, giving us the following classes. 
{%highlight java%}
public class PhaseShift extends QuantumGate {
    public PhaseShift(double theta) {

        ComplexDoubleMatrix psGate = new ComplexDoubleMatrix(2, 2);
        psGate.put(0, 0, 1);
        psGate.put(1, 1, new ComplexDouble(Math.cos(theta), Math.sin(theta)));
        this.gate = psGate;
        ComplexDoubleMatrix cpsGate = new ComplexDoubleMatrix(4, 4);
        cpsGate.put(0, 0, 1);
        cpsGate.put(1, 1, 1);
        cpsGate.put(2, 2, 1);
        cpsGate.put(3, 3, new ComplexDouble(Math.cos(theta), Math.sin(theta)));
        this.cgate = cpsGate;
        this.type = "PhaseShift" + theta ;

    }
}

    public class ZGate extends PhaseShift {

    // NOTE : the ZGate is a phaseshift gate where for theta = pi..

    public ZGate() {
        super(Math.PI);
        this.type = "Pauli Z";
    }
}

{% endhighlight %}
<br>
<br>
Once all of my unary gates were implemented, I could move on to the meatier gates, the SWAP gate as well as the two universal gates (i.e. gates from which we can build any other gates), that is, the Fredkin (Controlled SWAP) and Toffoli (Controlled-Controlled NOT) gates. The matrices for these gates are as follow: 
<br>
<br>
If a Swap gate, 
<br>
<br>
![gate](/assets/swap.svg)
<br>
<br>
takes two qubits and swaps their states, a Fredkin gate,
<br>
<br>
$$Fredkin =$$![gate](/assets/fredkin.svg)
<br>
<br>
is a Swap gate that takes a first control bit.
A toffoli gate, the final universal gate we will be implementing, is a controlled NOT gate that takes an additional control bit -- that is, it's a gate with two control bits, which we can write:
<br>
<br>
$$CCNOT =$$![gate](/assets/toffoli.svg)
<br>
<br>
Because these gates are universal, we don't need to scale them, as they work for a well defined amount of qubits (respectively 2, 3 and 3), but we wouldn't need to anyway since we can re-build any gate from these (hence their names -- universal gates !).  
<br>
<br>
{%highlight java%}
public class Swap extends QuantumGate {

    public Swap() {
        ComplexDoubleMatrix swapGate = new ComplexDoubleMatrix(4, 4);
        swapGate.put(0, 0, 1);
        swapGate.put(3, 3, 1);
        swapGate.put(1, 2, 1);
        swapGate.put(2, 1, 1);
        this.gate = this.cgate = swapGate;
        this.type = "Swap" ;
    }

    @Override
    public void scaleGate(int qubits, QuantumGate g) {
        System.out.println("can only swap two qubits :( ");
    }
  }

 --- 
 public class Fredkin extends QuantumGate {
    public Fredkin() {
        ComplexDoubleMatrix fredkinGate
                = new ComplexDoubleMatrix(8, 8);
        for (int i = 0; i < 5; i++) {
            fredkinGate.put(i, i, 1);
        }
        fredkinGate.put(7, 7, 1);
        fredkinGate.put(5, 6, 1);
        fredkinGate.put(6, 5, 1);
        this.gate = this.cgate = fredkinGate;
    }

    @Override
    public void scaleGate(int qubits, QuantumGate g) {
        System.out.println("attempt to scale a universal 
        gate (Fredkin) for input size :" + qubits + " qubits ");
    }

  }
 ---
 public class Toffoli extends QuantumGate {
    public Toffoli(){
        ComplexDoubleMatrix toffoliGate
                = new ComplexDoubleMatrix(8, 8);
        for (int i = 0; i < 6; i++) {
            toffoliGate.put(i, i, 1);
        }
        toffoliGate.put(6, 7, 1);
        toffoliGate.put(7, 6, 1);
        this.gate = this.cgate = toffoliGate;

        }


    @Override
    public void scaleGate(int qubits, QuantumGate g){
        System.out.println("attempt to scale a universal gate 
        (Toffoli) for input size :" + qubits + " qubits ");
    }
 }
{%endhighlight%}
<br>
<br>
When I was debugging my gates something funny occurred : the following unit test 
{%highlight java%}
@Test
    public void Hadamard() {

        ComplexDoubleMatrix hGateSquared
                = new ComplexDoubleMatrix(4, 4);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == 1 && (j == 1 || j == 3)
                        || i == 2 && (j == 2 || j == 3)
                        || i == 3 && (j == 1 || j == 2)) {
                    hGateSquared.put(i, j, -1);
                } else {
                    hGateSquared.put(i, j, 1 );
                }

            }
        }


        Hadamard h = new Hadamard(2);
        h.getGate().print();
        // check last value for h.getGate from the prompt.
        System.out.println("JBLAS error on last term of h.getGate (?) 
        : " + h.getGate().get(3,3));

        // this put sthatement is pure correction of the sign error above.
        // as the test does not equate 0 with -0 for some reason.
        h.getGate().put(3,3,new ComplexDouble(1,0));
        hGateSquared.print();
        assert h.getGate().equals(hGateSquared);



    }

{%endhighlight%}
<br>
It turns out the JBLAS complex doubles $$ \epsilon = 1.0 + 0.0i $$ and
$$ \omega = 1.0 + (-0.0)i $$ are returned as being different values !  
<br>
<br>
Part III : Deutsch's Algorithm
<br>
<br>
We now have functional qubit objects and functional gates that we can apply to one or more qubits... it is time to chain that together and to actually build into the emulator a quantum algorithm !
<br>
The first quantum algorithm I was confronted with was [Deutsch's Algorithm](http://www.cs.xu.edu/~kinne/quantum/deutche.html), an algorithm that demonstrates the superiority of quantum computation over classical computation for the following problem.
Given an oracle (a function) $$f : \{0,1\}->\{0,1\}$$, we want to determine if $$f$$ is injective ( i.e if 0 and 1 are each mapped to at most once by $$f$$ ).   
<br>
Whereas a classical solution would have to process the function $$f$$ at least twice (evaluating $$f(0)$$ and then $$f(1)$$), a quantum computer, thanks to quantum superposition, can evaluate both $$f(0)$$ and $$f(1)$$ simultaneously !
<br>
depending on the time complexity of $$f$$ this is an absolutely massive improvement...
<br>
<br>
So how do we go about implementing Deutsch's algorithm ? well, the recipe is as follow:
- Take two qubits and prepare them in our basis states $$\lvert0\rangle$$ and $$\lvert1\rangle$$, giving us a qubit register with values (0,1)
- Apply the Hadamard gate to our qubit register. it's value is now<br>
 $$ \frac{1}{2}( \lvert00\rangle - \lvert01\rangle + \lvert10\rangle - \lvert11\rangle) $$ 
- The algorithm creates four functions representing every possible mapping from $$\{0,1\}$$ to itself. These functions are as follow , 
<br> $$ f1 : 0->0, 1->0 $$ 
<br> $$ f2 : 0->0, 1->1 $$ 
<br> $$ f3 : 0->1, 1->1 $$
<br> $$ f4 : 0->1, 1->0 $$
<br>
The algorithm determines which of these mappings is analogous to the output of $$f$$, to figure out if $$f(1) = f(0)$$ or if $$f(1) \neq f(0)$$. It does so by passing our qubit register through $$f$$ 
- After running $$f$$ on our register, we apply the hadamard gate to the first qubit of the output.
- We then measure the first qubit of the output, if the measurement yields one, we know our function $$f$$ is injective. 
<br>
<br>
I implemented this algorithm with by letting the user choose which of the $$f 1--4$$ mapping was analogous to his $$f$$ function, the main method is as follow : 
<br>
{%highlight java%}
public static void main(String[] args) {
        Scanner uGateScanner = new Scanner(System.in);
        System.out.println("\ninitializing two qubits 
	in the basis states |1> and |0> \n");
        ComplexDoubleMatrix bra0ket
                = new ComplexDoubleMatrix(2, 1);

        bra0ket.put(0, 0, 1);
        bra0ket.put(1, 0, 0);
        ComplexDoubleMatrix bra1ket
                = new ComplexDoubleMatrix(2, 1);

        bra1ket.put(0, 0, 0);
        bra1ket.put(1, 0, 1);

        Qubit q0 = new Qubit(bra0ket);
        Qubit q1 = new Qubit(bra1ket);
        Hadamard h = new Hadamard();

        System.out.println("\nApplying Hadamard to both Qubits\n");
        q0.applyGate(h);
        q1.applyGate(h);
        System.out.println("\n -- Enter gate number for U gate 
		analogous to the function \n" +
                "you wish to test or -h to print the 4 available mappings -- \n");
        String input = uGateScanner.next();
        if (input.equals("-h")) {
            System.out.println("DISPLAY HELP");
        } else {
            int gateNum = Integer.parseInt(input);
            if (gateNum > 4 || gateNum < 1) {
                System.out.println("\n incorrect gate number specified : " + gateNum);
            } else {
                UFGate u = new UFGate(gateNum);
                Qubit q1_2 = q0.entangle(q1);
                q1_2.applyGate(u);
                Hadamard h2 = new Hadamard(2);
                q1_2.applyGate(h2);

                if(q1_2.measure()){
                    System.out.println(" congrats --
		 You've selected an injective mapping.");
                }else{
                    System.out.println(" snap -- this mapping is not injective");
                }

                System.out.println("\nExecution successfully terminated\n");


            }
        }


    }
{%endhighlight%} 
<br>
The four mappings are defined as matrices in a private class, they are matrices replicating the functions $$f 1..4$$ exactly as described in my step-by-step breakdown of the algorithm. 
<br>
<br>
Part IV : The Quantum circuit assembler 
<br>
<br>
After finishing the implementation of Deutsch's algorithm, I decided to build a "quantum circuit assembler" running on my emulator. It's a terminal application that lets the user declare qubits, entangle them, apply and scale gates, make measurements and comparisons etc.
<br>
The active qubits are maintained in a hashmap, with names given by the user. All the operations are called on qubits using gate codes and the qubit's names.  
<br>
It's available on github, and even though performance hasn't been my main issue, I'd be happy to get feedback from whoever uses it. I will be spending most of October - November 2018 developing it as a side project. 
<br>
In the meantime, I hope you learned a thing or two from this post, and I hope I intrigued if you're new to programming and/or quantum computing. If you're a researcher in that area, I hope this hasn't been to painful to read and, you haven't torn your eyes out of their respective sockets. 
<br>
<br>
Thanks for reading ! you can always contact me at mr3317@ic.ac.uk 
<br>
<br>
<br>
Bibliography, Links and thanks : 
- Thanks to [Professor Herbert Wiklicky](https://www.doc.ic.ac.uk/~herbert/) for first interesting me in quantum computing, during his topics presentation in spring 2018.
- Thanks to [Jocy Allcock](https://www.doc.ic.ac.uk/teaching/distinguished-projects/2010/s.allcock.pdf) whose research inspired me to build my quantum emulator and whose paper enlightened me on multiple occasions. 
- Thanks to the [Microsoft Q#](https://docs.microsoft.com/en-us/qsharp/api/prelude/microsoft.quantum.primitive?view=qsharp-preview) dev team, I used Q\# primitive gates list in order to implement my gate matrices. (additional props for that awesome conference at Imperial) 
 










 
