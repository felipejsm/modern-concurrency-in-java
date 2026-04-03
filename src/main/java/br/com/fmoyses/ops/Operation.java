package br.com.fmoyses.ops;

import br.com.fmoyses.model.Asset;
import br.com.fmoyses.model.Credit;
import br.com.fmoyses.model.Liability;
import br.com.fmoyses.model.Person;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Operation {
    final int MILLISECONDS = 2000;
    public Credit calculatedCredit(Long personId) {
        var person = getPerson(personId);
        var assets = getAssets(person);
        var liabilities = getLiabilities(person);
        importantWork();
        return calculatedCredits(assets, liabilities);
    }
    public Credit calculatedCreditWIthUnboundedThreads(Long personId)
        throws InterruptedException {
        var person = getPerson(personId);
        var assetsRef = new AtomicReference<List<Asset>>();
        var t1 = new Thread(
                () -> {
                    var assets = getAssets(person);
                    assetsRef.set(assets);
                }
        );
        var liabilitiesRef = new AtomicReference<List<Liability>>();
        var t2 = new Thread(
                () -> {
                    var liabilities = getLiabilities(person);
                    liabilitiesRef.set(liabilities);
                }
        );
        var t3 = new Thread(
                () -> importantWork()
        );
        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();

        var credit = calculatedCredits(assetsRef.get(), liabilitiesRef.get());
        t3.join();
        return credit;

    }
    Person getPerson(Long personId) {
        simulateDelay(MILLISECONDS);
        return new Person(personId, "John Doe");
    }
    List<Asset> getAssets(Person person) {
        simulateDelay(MILLISECONDS);
        return List.of(
                new Asset("House", 300000),
                new Asset("Car", 2500)
        );
    }
    List<Liability> getLiabilities(Person person) {
        simulateDelay(200);
        return List.of(
                new Liability("Mortgage", 20000),
                new Liability("Credit Card", 5000)
        );
    }
    void importantWork(){
        simulateDelay(200);
        IO.println("Important work completed");
    }
    Credit calculatedCredits(List<Asset> assets, List<Liability> liabilities) {
        simulateDelay(200);
        double totalAssets =
                assets.stream().mapToDouble(Asset::value).sum();
        double totalLiabilities = liabilities.stream()
                .mapToDouble(Liability::amount)
                .sum();
        double creditScore = (totalAssets - totalLiabilities) / 1_000;
        return new Credit(creditScore);
    }
    void simulateDelay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
