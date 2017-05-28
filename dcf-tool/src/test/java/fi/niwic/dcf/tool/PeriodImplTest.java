package fi.niwic.dcf.tool;

import fi.niwic.dcf.api.InvalidPastPeriodException;
import fi.niwic.dcf.api.Period;
import java.util.Optional;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;

public class PeriodImplTest {

	private Period period;
	
	@Before
	public void before() {
		period = new PeriodImpl(2017, false);
	}
	
	@Test
	public void checkConstructor() {
		assertEquals(2017, period.getYear());
		assertFalse(period.isPrediction());
	}
	
	@Test
	public void checkValidPastPeriod() throws InvalidPastPeriodException {
		Period pastPeriod = new PeriodImpl(period.getYear() - 1, false);
		period.setPastPeriod(pastPeriod);
		
		assertEquals(Optional.of(pastPeriod), period.getPastPeriod());
	}
	
	@Test
	public void checkValidPastPeriodEmpty() {
		assertEquals(Optional.empty(), period.getPastPeriod());
	}
	
	@Test(expected=InvalidPastPeriodException.class)
	public void checkInvalidPastPeriodSame() throws InvalidPastPeriodException {
		Period pastPeriod = new PeriodImpl(period.getYear(), false);
		period.setPastPeriod(pastPeriod);
	}
	
	@Test(expected=InvalidPastPeriodException.class)
	public void checkInvalidPastPeriodPlusOne() throws InvalidPastPeriodException {
		Period pastPeriod = new PeriodImpl(period.getYear() + 1, false);
		period.setPastPeriod(pastPeriod);
	}
	
	@Test(expected=InvalidPastPeriodException.class)
	public void checkInvalidPastPeriodMinusTwo() throws InvalidPastPeriodException {
		Period pastPeriod = new PeriodImpl(period.getYear() - 2, false);
		period.setPastPeriod(pastPeriod);
	}
	
	@Test(expected=NullPointerException.class)
	public void checkInvalidPastPeriodNull() throws InvalidPastPeriodException {
		period.setPastPeriod(null);
	}
	
}
