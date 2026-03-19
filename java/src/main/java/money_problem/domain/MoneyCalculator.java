package money_problem.domain;

public class MoneyCalculator {
    
    public static double add(double basedAmount, Currency currency, double addedAmount) {
        return basedAmount + addedAmount;
    }

    public static double times(double basedAmount, Currency currency, int value) {
        return basedAmount * value;
    }

    public static double divide(double basedAmount, Currency currency, int value) {
        return basedAmount / value;
    }
}