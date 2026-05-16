package br.com.fmoyses.chapter2;

public class PlatformThreadInterruption {
    void main() {
        virtualMe();
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
}
