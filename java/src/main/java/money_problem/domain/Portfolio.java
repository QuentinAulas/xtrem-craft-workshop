package money_problem.domain;

import java.util.HashMap;
import java.util.Map;


public class Portfolio {
    private final Map<Currency, Money> moneys = new HashMap<>();

    public void add(Money money) {
        moneys.merge(money.currency(), money, Money::add);
    }

    public double evaluate(Bank bank, Currency toCurrency) throws MissingExchangeRateException {
        double eval = 0.0;
        for (Money money : moneys.values()) {
            eval += bank.convert(money, toCurrency).amount();
        }
        return eval;
    }
}