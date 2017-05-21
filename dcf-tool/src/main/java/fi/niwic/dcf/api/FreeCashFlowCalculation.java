package fi.niwic.dcf.api;

public interface FreeCashFlowCalculation {
	public FreeCashFlowCalculation FreeCashFlowCalculation(
			FinancialStatement current,
			FinancialStatement past);
}
