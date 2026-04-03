package br.com.fmoyses;

import br.com.fmoyses.ops.Operation;

/**
 * Hello world!
 */
public class App {
    void main() {
        IO.println("Hello World!");
        Operation op = new Operation();
        op.calculatedCredit(1L);
    }
}