package money_problem.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;

import static money_problem.domain.BankBuilder.aBank;
import static money_problem.domain.Currency.EUR;
import static money_problem.domain.Currency.KRW;
import static money_problem.domain.Currency.USD;

class BankTest {

    @Test
    void convert_eur_to_usd_returns_money() throws MissingExchangeRateException {
        // Given
        Bank bank = aBank()
                .withExchangeRate(EUR, USD, 1.2)
                .build();

        // When
        Money result = bank.convert(new Money(EUR, 10), USD);

        // Then
        assertThat(result.amount()).isEqualTo(12);
        assertThat(result.currency()).isEqualTo(USD);
    }

    @Test
    void convert_eur_to_eur_returns_same_value() throws MissingExchangeRateException {
        // Given
        Bank bank = aBank()
                .withExchangeRate(EUR, USD, 1.2)
                .build();

        // When
        Money result = bank.convert(new Money(EUR, 10), EUR);

        // Then
        assertThat(result.amount()).isEqualTo(10);
        assertThat(result.currency()).isEqualTo(EUR);
    }

    @Test
    void convert_throws_exception_on_missing_exchange_rate() {
        // Given
        Bank bank = aBank()
                .withExchangeRate(EUR, USD, 1.2)
                .build();

        // When & Then
        assertThatThrownBy(() -> bank.convert(new Money(EUR, 10), KRW))
                .isInstanceOf(MissingExchangeRateException.class)
                .hasMessage("EUR->KRW");
    }

    @Test
    void convert_with_different_exchange_rates_returns_different_amounts() throws MissingExchangeRateException {
        // Given
        Bank bankAt1_2 = aBank()
                .withExchangeRate(EUR, USD, 1.2)
                .build();

        Bank bankAt1_3 = aBank()
                .withExchangeRate(EUR, USD, 1.3)
                .build();

        // When
        Money result1 = bankAt1_2.convert(new Money(EUR, 10), USD);
        Money result2 = bankAt1_3.convert(new Money(EUR, 10), USD);

        // Then
        assertThat(result1.amount()).isEqualTo(12);
        assertThat(result2.amount()).isEqualTo(13);
    }

    @Test
    void convert_with_added_exchange_rate() throws MissingExchangeRateException {
        // Given
        Bank bank = aBank()
                .withExchangeRate(EUR, USD, 1.2)
                .build();
        bank.addExchangeRate(EUR, USD, 1.3);

        // When
        Money result = bank.convert(new Money(EUR, 10), USD);

        // Then
        assertThat(result.amount()).isEqualTo(13);
    }

    @Test
    void convert_with_fractional_exchange_rate() throws MissingExchangeRateException {
        // Given
        Bank bank = aBank()
                .withExchangeRate(EUR, USD, 1.25)
                .build();

        // When
        Money result = bank.convert(new Money(EUR, 10), USD);
        // Then
        assertThat(result.amount()).isEqualTo(12.5);
    }
}