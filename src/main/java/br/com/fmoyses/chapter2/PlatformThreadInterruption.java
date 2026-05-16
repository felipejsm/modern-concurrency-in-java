package br.com.fmoyses.chapter2;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class PlatformThreadInterruption {
    void main() throws InterruptedException {
        vtDemo();
    }
    static void platformMe() {
        Thread platformThread = Thread.ofPlatform().start(() -> {
            try {
                IO.println("Platform started...");
                for (int i = 0; i < 5; i++) {
                    IO.println("Platform thread working... "+i);
                    Thread.sleep(1000);
                }
                IO.println("Platform thread finished.");
            } catch (InterruptedException e) {
                // TODO: handle exception
                IO.println("Platform thread interrupted!");
            }
        });
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            // TODO: handle exception
            platformThread.interrupt();
        }
    }
    static void virtualMe() {
        Thread virtualThread = Thread.ofVirtual().start(() -> {
            try {
                IO.println("Virtual started...");
                for (int i = 0; i < 5; i++) {
                    IO.println("Virtual thread working... "+i);
                    Thread.sleep(1000);
                }
                IO.println("Virtual thread finished.");
            } catch (InterruptedException e) {
                // TODO: handle exception
                IO.println("Virtual thread interrupted!");
            }
        });
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            // TODO: handle exception
            virtualThread.interrupt();
        }
    }
    static void threadGroup() throws InterruptedException {
        Set<ThreadGroup> threadGroups = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            Thread vThread = Thread.ofVirtual().start(() -> {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    // TODO: handle exception
                    Thread.currentThread().interrupt();
                }
            });
            threadGroups.add(vThread.getThreadGroup());
            Thread.sleep(1000);
            IO.println("Unique thread groups: " + threadGroups.size());
            IO.println("Thread group " + threadGroups.iterator().next());
        }
    }

    static void vtDemo() {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
        //try (var executor = Executors.newCachedThreadPool()) {
            IntStream.range(0, 10_000).forEach(i -> {
                executor.submit(() -> {
                    Thread.sleep(Duration.ofSeconds(1));
                    return i;
                });
            });
        }
    }
}
