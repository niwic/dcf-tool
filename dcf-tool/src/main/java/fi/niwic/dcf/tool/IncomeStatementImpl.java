package fi.niwic.dcf.tool;

import fi.niwic.dcf.api.IncomeStatement;

/**
* @see IncomeStatement
*/
public class IncomeStatementImpl implements IncomeStatement {

	private long turnover;
	private long costs;
	private long depreciation;
	private long otherIncomeAndCosts;
	private long interestIncome;
	private long interestCosts;
	private long taxCosts;
	private long dividendCosts;
	
	@Override
	public void setTurnover(long turnover) {
		this.turnover = turnover;
	}

	@Override
	public long getTurnover() {
		return turnover;
	}

	@Override
	public void setCosts(long costs) {
		this.costs = costs;
	}

	@Override
	public long getCosts() {
		return costs;
	}

	@Override
	public long getEBITDA() {
		return Math.subtractExact(turnover, costs);
	}

	@Override
	public void setDepreciation(long depreciation) {
		this.depreciation = depreciation;
	}

	@Override
	public long getDepreciation() {
		return depreciation;
	}

	@Override
	public long getOperatingProfit() {
		return Math.subtractExact(getEBITDA(), depreciation);
	}

	@Override
	public void setOtherIncomeAndCosts(long otherIncomeAndCosts) {
		this.otherIncomeAndCosts = otherIncomeAndCosts;
	}

	@Override
	public long getOtherIncomeAndCosts() {
		return otherIncomeAndCosts;
	}

	@Override
	public long getEBIT() {
		return Math.addExact(getOperatingProfit(), otherIncomeAndCosts);
	}

	@Override
	public void setInterestIncome(long interestIncome) {
		this.interestIncome = interestIncome;
	}

	@Override
	public long getInterestIncome() {
		return interestIncome;
	}

	@Override
	public void setInterestCosts(long interestCosts) {
		this.interestCosts = interestCosts;
	}

	@Override
	public long getInterestCosts() {
		return interestCosts;
	}

	@Override
	public long getEBT() {
		long ebitMinusCosts = Math.subtractExact(getEBIT(), interestCosts);
		return Math.addExact(ebitMinusCosts, interestIncome);
	}

	@Override
	public void setTaxCosts(long taxCosts) {
		this.taxCosts = taxCosts;
	}

	@Override
	public long getTaxCosts() {
		return taxCosts;
	}

	@Override
	public long getNetIncome() {
		return Math.subtractExact(getEBT(), taxCosts);
	}

	@Override
	public void setDividendCosts(long dividends) {
		this.dividendCosts = dividends;
	}

	@Override
	public long getDividendCosts() {
		return dividendCosts;
	}
	
	@Override
	public double getRealizedTaxRate() {
		return (double) getTaxCosts() / getEBT();
	}
	
}
