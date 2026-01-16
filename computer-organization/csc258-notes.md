---
title: CSC258 Notes
author: Harsh Jaluka
subject: 
keywords: 
created: "2021-08-18T12:41:00+00:00"
modified: "2021-08-19T14:31:00+00:00"
last_modified_by: Harsh Jaluka
revision: 49
category: 
comments: 
source_file: CSC258 Notes.docx
file_size: 22877
converted_date: "2026-01-15T20:15:02.338344"
---

Logical Devices:
1. Combinational circuits: output values entirely dependent and predictable from inputs
    1. Multiplexers (and demultiplexers)
        1. To select from multiple input values
    2. Decoders (eg 7-segment decoder)
        1. Translate from the output of one circuit to the input of another
    3. Adders (half, full and ripple carry)
        1. Small circuit devices that add two digits together
        2. Half adder: 2-input, 1-bit width binary adder, expressed as sum bit S and carry bit C
        3. Full adder: similar to half, with one more input C-in, which is added to the sum of the original two inputs
        4. Ripple carry adder: bunch of full adders to perform addition on multiple bit binary numbers
    4. Subtractors
        1. Extension of adders
            1. 2’s complement = 1’s complement (bitwise negation) + 1
            2. Sign-and-magnitude = first digit represents sign (0 positive, 1 negative) and the rest represents magnitude; not in use nowadays
        2. Use not gates before adders to change to one’s complement, use C-in to give 1 as carry input (signifies the +1 in the 2’s complement)
    5. Comparators
        1. Two input vectors and determines whether one is greater than, equal or less than the other
        2. As numbers get larger, comparators get more complex
        3. Sometimes it is easier to subtract the values and process the result (not faster, just easier with less circuitry)

Flip-flops:
1. Creating flip-flops:
    1. Gate delay / propagation delay:
        1. The amount of time it takes for an input change to result in the corresponding output change
    2. Feedback circuit:
        1. AND and OR gates don’t have interesting uses as feedback circuits
        2. NAND and NOR circuits are more interesting; the outputs don’t get stuck
            1. Both enter unsteady states, however, and may not be able to store a value
        3. A better solution is using latches (multiple gates combined)
            1. SR-bar latch
                1. Two NAND gates connected in a latch with S-bar and R-bar as inputs
                2. Inputs of 11 maintain previous output state
                3. Going from 00 to 11 produces unstable behaviour depending on which input changes first (00 forbidden state)
                4. S is set and R is reset
            2. SR latch (exact opposite of SR-bar latch)
                1. Two NOR gates connected in a latch with S and R as inputs
                2. Inputs of 00 maintain previous output state
                3. Going from 11 to 00 produces unstable behaviour depending on which input changes first (11 forbidden state)
            3. Clocked SR latch (or gated SR latch)
                1. Adding another layer of NAND gates to the SR-bar latch
                2. Inputs to the NAND gates are S, R and C (clock)
                3. Setting inputs to 00 maintains output values
                4. Clock needs to be high for inputs to have any effect
                5. Still face problem of when both inputs are high (indeterminate state)
            4. (gated) D-latch
                1. Making R and S dependent on a single input can avoid the indeterminate state problem
                2. Design is good but has timing issues
                3. Transparent: any change to input is visible to output when clock signal is 1
                    1. Output of a latch should not be applied directly or through combinational logic to the input of the same or another latch when they all have the same control (clock) signal
                    2. Solution: create disconnect between circuit input and output (separate inputs to each latch, negate every other input)
            5. SR master-slave flip flop
                1. Flip flop is a latched circuit whose output is triggered by the rising or falling edge of the clock
                2. Two clocked SR latches connected with negated clock input for the second
                3. Still have issue of unstable behaviour
            6. D flip flop
                1. D latch connected with SR latch, second clock negated input
                2. Negative edge triggered
                3. Can make positive edge triggered, just add a not gate at the input of first clock (second clock is then negated twice) – most used flip flop in the course
            7. T flip-flop
                1. Toggle value whenever input to T is high
            8. JK flip-flop
                1. JK = 00 maintain output
                2. JK = 01, reset output to 0
                3. JK = 10, set output to 1
                4. JK = 11, toggle output
    3. Timing considerations:
        1. Inputs should not be changing at the same time as the active edge of the clock
        2. Setup time: input should be stable for some time before active clock edge
        3. Hold time: input should be stable for some time after active clock edge
        4. Maximum frequency: time period between two active clock edges cannot be shorter than longest propagation delay between any two flip-flops and setup time.
    4. Resetting inputs
        1. Unknown state when powered up -> need to initialize
        2. Reset signal:
            1. Synchronous reset: output reset to 0 on active clock edge
            2. Asynchronous reset: output reset to 0 immediately
2. Sequential circuits: circuits that depend on current input and previous state of the circuit
    1. Basis for memory, instruction processing, and any other operation that requires the circuit to remember past data values (also called states of the circuit)
    2. Registers:
        1. Shift registers:
            1. Series of D flip flops can store a multiple bit value
            2. Data shifted into register one bit at a time
        2. Load registers:
            1. Series of D flip-flops
            2. Load all values at once by individually inputting each flip flop
            3. To control when the register is allowed to load values, add an enable circuit in front of D flip-flops to create D flip flops with enable
    3. Counters:
        1. Uses T flip-flops
        2. 4-bit ripple counter:
            1. Connect four T flip flops together, Q-bar connected to clock input of the next T flip-flop
            2. All inputs to T are 1
            3. Asynchronous, cheap to implement but unreliable for timing
            4. To make it synchronous: connect individual clocks to all four, and make input to each T flip-flop previous T input AND previous T output
        3. How to make a counter that goes high every x rising clock edges?
            1. Add combinational circuit to the outputs of counter to check for x, go high when x comes and link this back to clear (so that the counter is set back to 0 every x rising edges)

Finite state machines:
1. Defined as:
    1. A finite set of states
    2. A finite set of transitions between states
    3. Output values associated with each state or transition (depending on the kind of machine)
    4. Start and end states for the machine
2. How to design FSM
    1. Draw state diagram
    2. Derive state table
    3. Assign flip-flop configurations to each state (no. of flip-flops needed = log_2(no. of states)
    4. Redraw state table with flip-flop values
    5. Derive combinational circuit for output and each flip-flop input
3. Sequence recognizer:
    1. Recognize a sequence of input values and raise a signal if that input has been seen
4. Two ways to derive the circuitry needed for the output values of the state machine
    1. Moore machine: the output of the FSM depends solely on the current state
        1. Outputs are written within the state circles in the state diagrams
    2. Mealy machine: the output of the FSM depends on the state and the input
        1. Outputs are written on the transitions between states on the state diagram
5. Issue of timing with the states (race conditions)
    1. Whenever possible, make neighbouring states differ by at most one flip-flop value
        1. Intermediate states can be allowed if they have the same output as the start or destination
        2. If the intermediate states are unused in the diagram, you can set these to have the outputs that you need

Microprocessor:
1. A device that processes input, can store values and produces outputs, according to a set of onboard instructions
2. Datapath: where all the data computations take place
3. Control unit: control the actions that will take place in the datapath (big finite state machine)
    1. Outputs datapath control signals
    2. Synchronized to system-wide signals (clock, reset)
4. ALU: performs arithmetic and logical operations (AND, OR, NOT …)
    1. Recall adders and subtractors, these are part of ALU
    2. Inputs:
        1. Select bits (S2, S1, S0): specify the operation to perform
            1. S_2 chooses between arithmetic block and logic block
            2. S_1 and S_0 specify the operation within each block
        2. Carry bit C-in (only used in arithmetic block): to increment an output value or overall result
    3. Outputs (VCNZ only for arithmetic block):
        1. G output
        2. Overflow condition V: result of operation could not completely be stored in G, implies incorrect result
        3. Carry-out bit C: detect errors in unsigned arithmetic
        4. Negative indicator N
        5. Zero-condition indicator Z
    4. One possible implementation of an ALU is just all the individual circuits (adders, subtractors…) and a mux that chooses which output is needed (S inputs)
    5. How to implement multiplication:
        1. Layered rows of adder units
            1. Grows in size very quickly (for n bits, size is O(n^2))
        2. An adder/shifter circuit
        3. Booth’s algorithm
            1. (when written in column form) Whenever 01 appears in bottom number, add the top number with the least significant digit at the same place as the 0. When the next 10 appears, deduct (using 2s complement) the top number with the least significant digit at the same place as the 1. Remember to sign extend.
            2. If AxB=P. Add 0 to rightmost side of A. start from rightmost two digits of A. if same, do nothing. If 01, add B to HIGHEST digits of P. if 10, add -B to HIGHEST digits of P. perform one shift right (sign extend) on A and P.
            3. P is the result
5. The storage unit (register file and main memory)
    1. Register file:
        1. Stores 32 registers that can be used for quick read and write
        2. How to write to registers:
            1. Use a demux (aka one-hot decoder)
                1. Decodes m bit input to enable write to some registers
            2. Address of each register is 5 bits long
    2. Main memory:
        1. Larger grid of memory cells that can be used to store the main information to be processed by the CPU
        2. Uses same n-bit wires to read and write data
            1. Instead of muxes, we now use a tri-state buffer
            2. Includes a third write enable signal – when write enable is low, buffer output is a high impedance signal (neither high nor low voltage, represented by Z)
            3. We now use data buses (literally just wires) to communicate between memory and processor


