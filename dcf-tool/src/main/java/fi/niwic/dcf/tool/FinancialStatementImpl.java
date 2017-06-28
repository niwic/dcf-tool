package fi.niwic.dcf.tool;

import fi.niwic.dcf.api.BalanceSheet;
import fi.niwic.dcf.api.FinancialStatement;
import fi.niwic.dcf.api.IncomeStatement;

public class FinancialStatementImpl implements FinancialStatement {

    private IncomeStatement incomeStatement;
    private BalanceSheet balanceSheet;

    /**
     * Luo uusi tilinpäätös annetuilla tuloslaskelmalla ja taseella.
     * @param incomeStatement tuloslaskelma
     * @param balanceSheet tase
     */
    public FinancialStatementImpl(IncomeStatement incomeStatement,
            BalanceSheet balanceSheet) {

        this.incomeStatement = incomeStatement;
        this.balanceSheet = balanceSheet;
    }

    /**
     * Luo uusi tilnpäätös ja samalla uuden tuloslaskelman ja taseen.
     */
    public FinancialStatementImpl() {
        this.incomeStatement = new IncomeStatementImpl();
        this.balanceSheet = new BalanceSheetImpl();
    }

    @Override
    public IncomeStatement getIncomeStatement() {
        return this.incomeStatement;
    }

    @Override
    public BalanceSheet getBalanceSheet() {
        return this.balanceSheet;
    }

}
