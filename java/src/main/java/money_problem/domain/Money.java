package money_problem.domain;

public record Money(Currency currency, double amount) {

    public Money add(Money moneyIncoming) {
        if (this.currency != moneyIncoming.currency()) {
            throw new IllegalArgumentException("Currency mismatch: " + this.currency + " vs " + moneyIncoming.currency());
        }
        double result = this.amount + moneyIncoming.amount();
        if (result < 0) {
            throw new IllegalArgumentException("Resulting amount cannot be negative: " + result);
        }
        return new Money(this.currency, result);
    }

    public Money multiply(double multiplier) {
        double result = this.amount * multiplier;
        if (result < 0) {
            throw new IllegalArgumentException("Resulting amount cannot be negative: " + result);
        }   
        return new Money(this.currency, result);
    }

    public double divide(double divisor) {
        double result = this.amount / divisor;
        if (result < 0) {
            throw new IllegalArgumentException("Resulting amount cannot be negative: " + result);
        }   
        return result;
    }
}