package br.com.fmoyses.chapter2;

public class Chapter2 {
   void main() throws InterruptedException {
    IO.println("Starting chapter 2!");
    var vThread = Thread.startVirtualThread(() -> {
        IO.println("Chamai-me Ismael");
    });
    vThread.join();
   } 
}