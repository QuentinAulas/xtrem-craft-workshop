package money_problem.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class PortfolioTest {

    @Test
    // Evaluate an empty portfolio in EUR
    public void testEmptyPortfolio() throws MissingExchangeRateException {
        Portfolio portfolio = new Portfolio();
        Bank bank = Bank.withExchangeRate(Currency.EUR, Currency.USD, 1.2);
        double value = portfolio.evaluate(bank, Currency.EUR);
        assertThat(value).isEqualTo(0.0);
    }
    
    @Test
    // Evaluer son portfolio en convertissant deux devises en une des deux devises: Given a portfolio containing 5 USD And 10 EUR And a Bank with an exchange rate  EUR and USD of 1.2When I evaluate the portfolio to the Bank USDThen I should receive 17 USD
    public void testPortfolioEvaluation() throws MissingExchangeRateException {
        Portfolio portfolio = new Portfolio();
        portfolio.add(5, Currency.USD);
        portfolio.add(10, Currency.EUR);
        Bank bank = Bank.withExchangeRate(Currency.EUR, Currency.USD, 1.2);
        double value = portfolio.evaluate(bank, Currency.USD);
        assertThat(value).isEqualTo(17.0);
    }   

    @Test
    // Evaluer son portfolio en convertissant deux devises en une devise tertiaire: Given a portfolio containing 5 USD And 10 EUR And a Bank with two exchange rates EUR and KRW of 1.5 USD and KRW of 1.5When I evaluate the portfolio to the Bank KRW Then I should receive 27.5 KRW
    public void testPortfolioEvaluationWithThreeCurrencies() throws MissingExchangeRateException {
        Portfolio portfolio = new Portfolio();
        portfolio.add(5, Currency.USD);
        portfolio.add(10, Currency.EUR);
        Bank bank = Bank.withExchangeRate(Currency.EUR, Currency.KRW, 1.5);
        bank.addExchangeRate(Currency.USD, Currency.KRW, 1.5);
        double value = portfolio.evaluate(bank, Currency.KRW);
        assertThat(value).isEqualTo(22.5);
    }

    @Test
    // S'il manque une devise lors de l'évaluation, renvoyer une erreur: Given a portfolio containing 5 USD And a Bank with an exchange rate EUR and USD of 1.2 When I want to add 10 in USD Then I should see an error saying "The incoming currency is missing"
    void should_throw_error_when_exchange_rate_is_missing() {
        Portfolio portfolio = new Portfolio();
        portfolio.add(5, Currency.USD);
        Bank bank = Bank.withExchangeRate(Currency.EUR, Currency.USD, 1.2);
        assertThrows(MissingExchangeRateException.class, () -> portfolio.evaluate(bank, Currency.KRW));
    }


    // @ParameterizedTest(name = "Checking {1} {0}")
    // @MethodSource("provideDetailsForEvaluation")
    // void should_evaluate_each_currency_individually(Currency currency, double expectedAmount, Portfolio portfolio, Bank bank) {

    //     Map<Currency, Double> details = portfolio.getDetails();
        
    //     assertThat(details.get(currency))
    //         .as("Check amount for " + currency)
    //         .isEqualTo(expectedAmount);
    // }

    // private static Stream<Arguments> provideDetailsForEvaluation() {

    //     Portfolio portfolio = new Portfolio();
    //     portfolio.add(5, Currency.USD);
    //     portfolio.add(10, Currency.EUR);
    //     portfolio.add(20, Currency.KRW);

    //     Bank bank = Bank.withExchangeRate(Currency.EUR, Currency.USD, 1.2);
    //     bank.addExchangeRate(Currency.EUR, Currency.KRW, 1.5);
    //     bank.addExchangeRate(Currency.USD, Currency.KRW, 1.5);

    //     return Stream.of(
    //         Arguments.of(Currency.EUR, 10.0, portfolio, bank),
    //         Arguments.of(Currency.USD, 5.0, portfolio, bank),
    //         Arguments.of(Currency.KRW, 20.0, portfolio, bank)
    //     );
    }

    
