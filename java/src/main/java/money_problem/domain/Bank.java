package money_problem.domain;

import java.util.HashMap;
import java.util.Map;

public final class Bank {
    private final Map<String, Double> exchangeRates;

    private Bank(Map<String, Double> exchangeRates) {
        this.exchangeRates = exchangeRates;
    }

    public static Bank withExchangeRate(Currency from, Currency to, double rate) {
        var bank = new Bank(new HashMap<>());
        bank.addExchangeRate(from, to, rate);

        return bank;
    }

    public void addExchangeRate(Currency from, Currency to, double rate) {
        exchangeRates.put(from + "->" + to, rate);
    }

    public double convert(double amount, Currency from, Currency to) throws MissingExchangeRateException {
        if (!(from == to || exchangeRates.containsKey(from + "->" + to))) {
            throw new MissingExchangeRateException(from, to);
        }
        return from == to
                ? amount
                : amount * exchangeRates.get(from + "->" + to);
    }

}