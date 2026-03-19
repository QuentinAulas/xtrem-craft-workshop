package money_problem.domain;

import static money_problem.domain.BankBuilder.builtBank;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class PortfolioTest {

    @Test
    void testEmptyPortfolio() throws MissingExchangeRateException {
        // Given
    // Evaluate an empty portfolio in EUR
    public void testEmptyPortfolio() throws MissingExchangeRateException {
        Portfolio portfolio = new Portfolio();
        Bank bank = builtBank()
                .withExchangeRate(Currency.EUR, Currency.USD, 1.2)
                .build();
        // When
        double value = portfolio.evaluate(bank, Currency.EUR);
        
        // Then

    void testEmptyPortfolio() throws MissingExchangeRateException {
        // Given
        Portfolio portfolio = new Portfolio();
        Bank bank = Bank.withExchangeRate(Currency.EUR, Currency.USD, 1.2);
        
        // When
        double value = portfolio.evaluate(bank, Currency.EUR);
        
        // Then
        assertThat(value).isEqualTo(0.0);
    }
    
    @Test
    void testPortfolioEvaluation() throws MissingExchangeRateException {
        // Given

    // Evaluer son portfolio en convertissant deux devises en une des deux devises: Given a portfolio containing 5 USD And 10 EUR And a Bank with an exchange rate  EUR and USD of 1.2When I evaluate the portfolio to the Bank USDThen I should receive 17 USD
    public void testPortfolioEvaluation() throws MissingExchangeRateException {
        Portfolio portfolio = new Portfolio();
        portfolio.add(new Money(Currency.USD, 5));
        portfolio.add(new Money(Currency.EUR, 10));
        
        Bank bank = Bank.withExchangeRate(Currency.EUR, Currency.USD, 1.2);
        
        // When
        double value = portfolio.evaluate(bank, Currency.USD);
    
    void testPortfolioEvaluation() throws MissingExchangeRateException {
        // Given
        Portfolio portfolio = new Portfolio();
        portfolio.add(new Money(Currency.USD, 5));
        portfolio.add(new Money(Currency.EUR, 10));
        
        Bank bank = Bank.withExchangeRate(Currency.EUR, Currency.USD, 1.2);
        
        // When
        double value = portfolio.evaluate(bank, Currency.USD);
        
        // Then
        assertThat(value).isEqualTo(17.0);
    }   

    @Test
    void testPortfolioEvaluationWithThreeCurrencies() throws MissingExchangeRateException {
        // Given
    // Evaluer son portfolio en convertissant deux devises en une devise tertiaire: Given a portfolio containing 5 USD And 10 EUR And a Bank with two exchange rates EUR and KRW of 1.5 USD and KRW of 1.5When I evaluate the portfolio to the Bank KRW Then I should receive 27.5 KRW
    public void testPortfolioEvaluationWithThreeCurrencies() throws MissingExchangeRateException {
        Portfolio portfolio = new Portfolio();
        portfolio.add(new Money(Currency.USD, 5));
        portfolio.add(new Money(Currency.EUR, 10));
        
        Bank bank = Bank.withExchangeRate(Currency.EUR, Currency.KRW, 1.5);
        bank.addExchangeRate(Currency.USD, Currency.KRW, 1.5);
        
        // When
        double value = portfolio.evaluate(bank, Currency.KRW);

    void testPortfolioEvaluationWithThreeCurrencies() throws MissingExchangeRateException {
        // Given
        Portfolio portfolio = new Portfolio();
        portfolio.add(new Money(Currency.USD, 5));
        portfolio.add(new Money(Currency.EUR, 10));
        
        Bank bank = Bank.withExchangeRate(Currency.EUR, Currency.KRW, 1.5);
        bank.addExchangeRate(Currency.USD, Currency.KRW, 1.5);
        
        // When
        double value = portfolio.evaluate(bank, Currency.KRW);
        
        // Then

        assertThat(value).isEqualTo(22.5);
    }

    @Test

    // S'il manque une devise lors de l'évaluation, renvoyer une erreur: Given a portfolio containing 5 USD And a Bank with an exchange rate EUR and USD of 1.2 When I want to add 10 in USD Then I should see an error saying "The incoming currency is missing"
    void should_throw_error_when_exchange_rate_is_missing() {
        // Given
        Portfolio portfolio = new Portfolio();
        // Modification ici
        portfolio.add(new Money(Currency.USD, 5));
        Bank bank = Bank.withExchangeRate(Currency.EUR, Currency.USD, 1.2);
        
        // When & Then
        assertThrows(MissingExchangeRateException.class, () -> 
            portfolio.evaluate(bank, Currency.KRW)
        );
    }

    void should_throw_error_when_exchange_rate_is_missing() {
        // Given
        Portfolio portfolio = new Portfolio();
        // Modification ici
        portfolio.add(new Money(Currency.USD, 5));
        Bank bank = Bank.withExchangeRate(Currency.EUR, Currency.USD, 1.2);
        
        // When & Then
        assertThrows(MissingExchangeRateException.class, () -> 
            portfolio.evaluate(bank, Currency.KRW)
        );
    }

    @Test
    void should_add_to_existing_currency_in_portfolio() throws MissingExchangeRateException {
        // Given
        Portfolio portfolio = new Portfolio();
        // Modification ici
        portfolio.add(new Money(Currency.USD, 5));
        portfolio.add(new Money(Currency.USD, 10)); 
        Bank bank = Bank.withExchangeRate(Currency.USD, Currency.USD, 1.0);
        
        // When
        double value = portfolio.evaluate(bank, Currency.USD);
        
        // Then
        assertThat(value).isEqualTo(15.0);
    }
}
