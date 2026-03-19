package money_problem.domain;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import static money_problem.domain.Currency.EUR;
import static money_problem.domain.Currency.KRW;
import static money_problem.domain.Currency.USD;

class MoneyCalculatorTest {
    @Test
    void shouldAddInUsd() {
        // Given
        var basedAmount = 5;
        var addedAmount = 10;
        // When 
        double result = MoneyCalculator.add(basedAmount, USD, addedAmount);
        // Then
        assertThat(result).isEqualTo(15);
    }

    @Test
    void shouldMultiplyInEuros() {
        // Given
        var basedAmount = 5;
        var value = 4;
        // When
        double result = MoneyCalculator.times(basedAmount, EUR, value);
        // Then
        assertThat(result).isEqualTo(20);
    }

    @Test
    void shouldDivideInKoreanWons() {
        // Given
        var basedAmount = 4002;
        var divisor = 4;
        // When
        double result = MoneyCalculator.divide(basedAmount, KRW, divisor);
        // Then
        assertThat(result).isEqualTo(1000.5);
    }

    @Test
    void shouldAddInEuros() {
        // Given
        var basedAmount = 100;
        var addedAmount = 50;
        // When
        double result = MoneyCalculator.add(basedAmount, EUR, addedAmount);
        // Then
        assertThat(result).isEqualTo(150);
    }

    @Test
    void shouldAddWithZero() {
        // Given
        var basedAmount = 100;
        var addedAmount = 0;
        // When
        double result = MoneyCalculator.add(basedAmount, USD, addedAmount);
        // Then
        assertThat(result).isEqualTo(100);
    }

    @Test
    void shouldMultiplyInUsd() {
        // Given
        var basedAmount = 10;
        var value = 5;
        // When
        double result = MoneyCalculator.times(basedAmount, USD, value);
        // Then
        assertThat(result).isEqualTo(50);
    }

    @Test
    void shouldDivideInEuros() {
        // Given
        var basedAmount = 100;
        var divisor = 2;
        // When
        double result = MoneyCalculator.divide(basedAmount, EUR, divisor);
        // Then
        assertThat(result).isEqualTo(50);
    }
}