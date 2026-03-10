package money_problem.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class Portfolio {

    private final HashMap<Currency, Double> moneys = new HashMap<>();
    
    public double evaluate(Bank bank, Currency toCurrency) throws MissingExchangeRateException {
        double eval = 0.0;
        for (Currency currency : moneys.keySet()){
            if (moneys.get(currency) != 0) {
                eval += bank.convert(new Money(currency, moneys.get(currency)), toCurrency).amount();
            }
        }
        return eval;
    }

    public void add(double amount, Currency currency) {

        this.moneys.put(currency, amount);
    }

    public Map<Currency, Double> getDetails() {
        return Collections.unmodifiableMap(moneys);
    }
}
