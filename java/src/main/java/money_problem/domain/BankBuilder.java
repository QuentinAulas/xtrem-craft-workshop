package money_problem.domain;

public class BankBuilder {
    private Currency pivotCurrency = Currency.EUR;
    private Currency toCurrency = Currency.USD;
    private double rate = 1.0;

    public static BankBuilder aBank() {
        return new BankBuilder();
    }

    public BankBuilder withPivotCurrency(Currency pivotCurrency) {
        this.pivotCurrency = pivotCurrency;
        return this;
    }

    public BankBuilder withExchangeRate(Currency from, Currency to, double rate) {
        this.pivotCurrency = from;
        this.toCurrency = to;
        this.rate = rate;
        return this;
    }

    public Bank build() {
        Bank bank = Bank.withExchangeRate(pivotCurrency, toCurrency, rate);
        return bank;
    }
}