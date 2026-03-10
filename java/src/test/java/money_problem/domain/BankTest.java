package money_problem.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;

import static money_problem.domain.Currency.EUR;
import static money_problem.domain.Currency.KRW;
import static money_problem.domain.Currency.USD;

class BankTest {

    @Test
    void convert_eur_to_usd_returns_double() throws MissingExchangeRateException {
        // Given
        var from = EUR;
        var to = USD;
        // When 
        double convertResult = Bank.withExchangeRate(from, to, 1.2).convert(10, from, to);
        // Then
        assertThat(convertResult).isEqualTo(12);
    }

    @Test
    void convert_eur_to_eur_returns_same_value() throws MissingExchangeRateException {
        // Given
        var from = EUR;
        var to = EUR;
        // When 
        double convertResult = Bank.withExchangeRate(from, to, 1.0).convert(10, from, to);
        // Then
        assertThat(convertResult)
                .isEqualTo(10);
    }

    @Test
    void convert_throws_exception_on_missing_exchange_rate() {
        // Given
        var from = EUR;
        var to = KRW;
        Bank bank = Bank.withExchangeRate(from, USD, 1.2);
        // When & Then
        assertThatThrownBy(() -> bank.convert(10, from, to))
                .isInstanceOf(MissingExchangeRateException.class)
                .hasMessage("EUR->KRW");
        
    }

    @Test
    void convert_with_different_exchange_rates_returns_different_floats() throws MissingExchangeRateException {
        // Given
        var from = EUR;
        var to = USD;
        // When 
        double convertResult = Bank.withExchangeRate(from, to, 1.2).convert(10, from, to);
        double convertResult2 = Bank.withExchangeRate(from, to, 1.3).convert(10, from, to);
        // Then
        assertThat(convertResult)
                .isEqualTo(12);
        assertThat(convertResult2)
                .isEqualTo(13);
    }

    @Test
    void convert_with_added_exchange_rate() throws MissingExchangeRateException {
        // Given
        var from = EUR;
        var to = USD;
        Bank bank = Bank.withExchangeRate(from, to, 1.2);
        bank.addExchangeRate(from, to, 1.3);
        // When 
        double convertResult = bank.convert(10, from, to);
        // Then
        assertThat(convertResult)
                .isEqualTo(13);
    }

    @Test
    void convert_with_fractional_exchange_rate() throws MissingExchangeRateException {
        // Given
        var from = EUR;
        var to = USD;
        Bank bank = Bank.withExchangeRate(from, to, 1.25);
        // When 
        double convertResult = bank.convert(10, from, to);
        // Then
        assertThat(convertResult)
                .isEqualTo(12.5);
    }

    @Test
    void missing_exchange_rate_from_known_currency_to_unknown() {
        // Given
        var from = EUR;
        var to = KRW;
        Bank bank = Bank.withExchangeRate(from, USD, 1.2);
        // When & Then
        assertThatThrownBy(() -> bank.convert(10, from, to))
                .isInstanceOf(MissingExchangeRateException.class)
                .hasMessage("EUR->KRW");
    }
}