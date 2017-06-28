package fi.niwic.dcf.api;

import java.util.Optional;

/**
 * Yhdistää kaikki elementit jotka kuuluvat tiettyyn tilikauteen.
 */
public interface Period {
	
    /**
     * Tilikausi.
     * @return tilikausi
     */
	public Integer getYear();
    
    /**
     * Monellako vuodella diskontataan.
     * @return lukumäärä
     */
    public Long getDiscountYears();
    
    /**
     * Asettaa diskonttausjaksojen lukumäärä.
     * @param years jaksojen lukumäärä
     */
    public void setDiscountYears(long years);
    
    /**
     * Onko jakso arvio tulevasta jaksosta.
     * @return true jos on arvio
     */
	public boolean isPrediction();
	
    /**
     * Palauttaa jakson tilinpäätöksen.
     * @return tilinpäätöksen
     */
	public FinancialStatement getCurrentFinancialStatement();
	
    /**
     * Asettaa tätä jaksoa edeltävä jakso. Tarvitaan muutosten laskemiseen.
     * @param period edeltävä jakso
     * @throws InvalidPastPeriodException 
     */
	public void setPastPeriod(Period period) throws InvalidPastPeriodException;
    
    /**
     * Hae tätä jaksoa edeltävä jakso.
     * @return edeltävä jakso
     */
	public Optional<Period> getPastPeriod();
	
    /**
     * Palauttaa jakson kassavirta-analyysin.
     * @return kassavirta-analyysi
     */
	public FreeCashFlowCalculation getFreeCashFlowCalculation();
	
}
