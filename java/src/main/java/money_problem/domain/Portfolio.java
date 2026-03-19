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
        for (Currency currency : moneys.keySet()){
            if (moneys.get(currency) != 0) {
                eval += bank.convert(new Money(currency, moneys.get(currency)), toCurrency).amount();
            }
        }
        return eval;
    }

    public void add(Money money) {

        this.moneys.put(money.currency(), money.amount());
    }

    public Map<Currency, Double> getDetails() {
        return Collections.unmodifiableMap(moneys);
    }
}
