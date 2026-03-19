package money_problem.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import static money_problem.domain.BankBuilder.aBank;

public class PortfolioTest {

    @Test
    void testEmptyPortfolio() throws MissingExchangeRateException {
        // Given
        Portfolio portfolio = new Portfolio();
        Bank bank = aBank()
                .withExchangeRate(Currency.EUR, Currency.USD, 1.2)
                .build();

        // When
        double value = portfolio.evaluate(bank, Currency.EUR);

        // Then
        assertThat(value).isEqualTo(0.0);
    }

    @Test
    void testPortfolioEvaluation() throws MissingExchangeRateException {
        // Given
        Portfolio portfolio = new Portfolio();
        portfolio.add(new Money(Currency.USD, 5));
        portfolio.add(new Money(Currency.EUR, 10));

        Bank bank = aBank()
                .withExchangeRate(Currency.EUR, Currency.USD, 1.2)
                .build();

        // When
        double value = portfolio.evaluate(bank, Currency.USD);

        // Then
        assertThat(value).isEqualTo(17.0);
    }

    @Test
    void testPortfolioEvaluationWithThreeCurrencies() throws MissingExchangeRateException {
        // Given
        Portfolio portfolio = new Portfolio();
        portfolio.add(new Money(Currency.USD, 5));
        portfolio.add(new Money(Currency.EUR, 10));

        Bank bank = aBank()
                .withExchangeRate(Currency.EUR, Currency.KRW, 1.5)
                .build();
        bank.addExchangeRate(Currency.USD, Currency.KRW, 1.5);

        // When
        double value = portfolio.evaluate(bank, Currency.KRW);

        // Then
        assertThat(value).isEqualTo(22.5);
    }

    @Test
    void should_throw_error_when_exchange_rate_is_missing() {
        // Given
        Portfolio portfolio = new Portfolio();
        portfolio.add(new Money(Currency.USD, 5));

        Bank bank = aBank()
                .withExchangeRate(Currency.EUR, Currency.USD, 1.2)
                .build();

        // When & Then
        assertThrows(MissingExchangeRateException.class, () ->
                portfolio.evaluate(bank, Currency.KRW)
        );
    }

    @Test
    void should_add_to_existing_currency_in_portfolio() throws MissingExchangeRateException {
        // Given
        Portfolio portfolio = new Portfolio();
        portfolio.add(new Money(Currency.USD, 5));
        portfolio.add(new Money(Currency.USD, 10));

        Bank bank = aBank()
                .withExchangeRate(Currency.USD, Currency.USD, 1.0)
                .build();

        // When
        double value = portfolio.evaluate(bank, Currency.USD);

        // Then
        assertThat(value).isEqualTo(10.0);
    }
}