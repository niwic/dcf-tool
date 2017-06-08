package fi.niwic.dcf.api;

import java.util.List;

/**
 * Discounted Cash-flow Calculation laskee yrityksen jaksojen taseista ja
 * tuloslaskelmista yrityksen arvo-arvio.
 * 
 * Jokaisen jakson vapaa kassavirta lasketaan, minkä jälkeen nämä diskontataan
 * yrityksen rahoituksen kustannuksella. Jaksojen diskontatut vapaat kassavirrat
 * lasketaan yhteen, ja tästä muodostuu yrityksen arvo-arvio.
 * 
 * Voidaan myös jakaa arvio osakkeeiden määrällä, jolloin saadaan osakekurssin
 * arvio.
 */
public interface DCFCalculation {
	
	public void setCompanyName(String name);
	public String getCompanyName();
	
	public void addPeriod(Period period);
	public List<Period> listPeriods();
	
	public void setPerpetualPeriod(Period period);
	public Period getPerpetualPeriod();
	
	public void setCostOfCapital(CostOfCapital costOfCapital);
	public CostOfCapital getCostOfCapital();
	
	public void setNumberOfShares();
	public long getNumberOfShares();
	
	public long calculateValuation();
	
}
