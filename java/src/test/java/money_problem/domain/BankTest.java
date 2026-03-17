package money_problem.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;

import static money_problem.domain.Currency.EUR;
import static money_problem.domain.Currency.KRW;
import static money_problem.domain.Currency.USD;

class BankTest {

    @Test
    void convert_eur_to_usd_returns_money() throws MissingExchangeRateException {
        // Given
        var from = EUR;
        var to = USD;
        // When 
        Money result = Bank.withExchangeRate(from, to, 1.2).convert(new Money(from, 10), to);
        
        // Then
        assertThat(result.amount()).isEqualTo(12);
        assertThat(result.currency()).isEqualTo(to);
    }

    @Test
    void convert_eur_to_eur_returns_same_value() throws MissingExchangeRateException {
        // Given
        var from = EUR;
        // When 
        Money result = Bank.withExchangeRate(from, USD, 1.2).convert(new Money(from, 10), from);
        
        // Then
        assertThat(result.amount()).isEqualTo(10);
        assertThat(result.currency()).isEqualTo(from);
    }

    @Test
    void convert_throws_exception_on_missing_exchange_rate() {
        // Given
        var from = EUR;
        var to = KRW;
        Bank bank = Bank.withExchangeRate(from, USD, 1.2);
        
        // When & Then
        assertThatThrownBy(() -> bank.convert(new Money(from, 10), to))
                .isInstanceOf(MissingExchangeRateException.class)
                .hasMessage("EUR->KRW");
    }

    @Test
    void convert_with_different_exchange_rates_returns_different_amounts() throws MissingExchangeRateException {
        // Given
        var from = EUR;
        var to = USD;
        
        // When 
        Money result1 = Bank.withExchangeRate(from, to, 1.2).convert(new Money(from, 10), to);
        Money result2 = Bank.withExchangeRate(from, to, 1.3).convert(new Money(from, 10), to);
        
        // Then
        assertThat(result1.amount()).isEqualTo(12);
        assertThat(result2.amount()).isEqualTo(13);
    }

    @Test
    void convert_with_added_exchange_rate() throws MissingExchangeRateException {
        // Given
        var from = EUR;
        var to = USD;
        Bank bank = Bank.withExchangeRate(from, to, 1.2);
        bank.addExchangeRate(from, to, 1.3);
        
        // When 
        Money result = bank.convert(new Money(from, 10), to);
        
        // Then
        assertThat(result.amount()).isEqualTo(13);
    }

    @Test
    void convert_with_fractional_exchange_rate() throws MissingExchangeRateException {
        // Given
        var from = EUR;
        var to = USD;
        Bank bank = Bank.withExchangeRate(from, to, 1.25);
        
        // When 
        Money result = bank.convert(new Money(from, 10), to);
        
        // Then
        assertThat(result.amount()).isEqualTo(12.5);
    }
}