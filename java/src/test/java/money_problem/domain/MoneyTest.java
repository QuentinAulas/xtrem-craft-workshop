package money_problem.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;


public class MoneyTest {
    
    // Tentative d'ajout de 100 EUR à 100 EUR, résultat attendu : 200 EUR
    @Test
    void shouldCreateMoney() {
        // Given
        var currency = Currency.USD;
        var amount = 100.0;
        // When
        Money money = new Money(currency, amount);
        Money result = money.add(new Money(currency, amount));
        // Then
        assertThat(result.amount()).isEqualTo(200.0);
    }

    // Tentative d'ajout de 100 EUR à 100 USD, résultat attendu : une erreur de devise
    @Test
    void shouldThrowErrorOnCurrencyMismatch() {
        // Given
        var currency1 = Currency.EUR;
        var currency2 = Currency.USD;
        var amount = 100.0;
        // When
        Money money = new Money(currency1, amount);
        // Then
        assertThatThrownBy(() -> money.add(new Money(currency2, amount)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Currency mismatch: " + currency1 + " vs " + currency2);
    }   

    // Tentative d'ajout de -150 EUR à 100 EUR, résultat attendu : une erreur de montant négatif
    @Test
    void shouldThrowErrorOnNegativeResult() {
        var amount1 = 100.0;
        var amount2 = -150.0;
        Money money = new Money(Currency.EUR, amount1);
        
        assertThatThrownBy(() -> money.add(new Money(Currency.EUR, amount2)))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Resulting amount cannot be negative: " + (amount1 + amount2));
    }

    // Multiplication de 10 EUR par 5, résultat attendu : 50 EUR
    @Test
    void shouldMultiplyMoney() {
        // Given
        var currency = Currency.EUR;
        var amount = 10.0;
        var multiplier = 5.0;
        // When
        Money money = new Money(currency, amount);
        Money result = money.multiply(multiplier);
        // Then
        assertThat(result.amount()).isEqualTo(50.0);
    }

    // Multiplication de 10 EUR par -5, résultat attendu : une erreur de montant négatif
    @Test
    void shouldThrowErrorOnNegativeMultiplication() {
        // Given
        var currency = Currency.EUR;
        var amount = 10.0;
        var multiplier = -5.0;
        // When
        Money money = new Money(currency, amount);
        // Then
        assertThatThrownBy(() -> money.multiply(multiplier))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Resulting amount cannot be negative: " + (amount * multiplier)); 
    }
    
    @Test
    void shouldAllowAdditionResultingInZero() {
        Money money = new Money(Currency.EUR, 10.0);
        Money result = money.add(new Money(Currency.EUR, -10.0));
        assertThat(result.amount()).isEqualTo(0.0);
    }

    @Test
    void shouldAllowMultiplicationByZero() {
        Money money = new Money(Currency.EUR, 10.0);
        Money result = money.multiply(0.0);
        assertThat(result.amount()).isEqualTo(0.0);
    }

    @Test
    void shouldAllowDividingZero() {
        Money money = new Money(Currency.EUR, 0.0);
        double result = money.divide(2.0);
        assertThat(result).isEqualTo(0.0);
    }

    // Division de 10 EUR par 2, résultat attendu : 5 EUR
    @Test
    void shouldDivideMoney() {
        // Given
        var currency = Currency.EUR;
        var amount = 10.0;
        var divisor = 2.0;
        // When
        Money money = new Money(currency, amount);
        double result = money.divide(divisor);
        // Then
        assertThat(result).isEqualTo(5.0);
    }

    // Division de 10 EUR par -2, résultat attendu : une erreur de montant négatif
    @Test
    void shouldThrowErrorOnNegativeDivision() {
        // Given
        var currency = Currency.EUR;
        var amount = 10.0;
        var divisor = -2.0;
        // When
        Money money = new Money(currency, amount);
        // Then
        assertThatThrownBy(() -> money.divide(divisor))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Resulting amount cannot be negative: " + (amount / divisor)); 
    }
    
}
