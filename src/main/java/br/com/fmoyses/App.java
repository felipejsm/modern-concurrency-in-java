package br.com.fmoyses;

import br.com.fmoyses.ops.Operation;
import br.com.fmoyses.util.ExecutionTimer;

public class App {
    void main() throws Exception {
        Operation op = new Operation();
        IO.println("====== Sequential Execution ======");
        ExecutionTimer.measure(() -> op.calculatedCredit(1L));

        IO.println("====== Parallel Execution ======");
        ExecutionTimer.measure(() -> {
                try{
                    return op.calculatedCreditWIthUnboundedThreads(1L);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
        });
        IO.println("====== Parallel Execution w/ Executors ======");
        ExecutionTimer.measure(() -> {
            try{
                return op.calculateCreditWithExecutor(1L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        });
    }
}