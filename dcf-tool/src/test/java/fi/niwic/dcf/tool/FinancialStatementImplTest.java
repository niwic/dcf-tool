package fi.niwic.dcf.tool;

import fi.niwic.dcf.api.BalanceSheet;
import fi.niwic.dcf.api.FinancialStatement;
import fi.niwic.dcf.api.IncomeStatement;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class FinancialStatementImplTest {

	@Test
	public void testConstructor() {
		BalanceSheet bs = new BalanceSheetImpl();
		IncomeStatement is = new IncomeStatementImpl();
		FinancialStatement fs = new FinancialStatementImpl(is, bs);
		
		assertEquals(is, fs.getIncomeStatement());
		assertEquals(bs, fs.getBalanceSheet());
	}
	
}
