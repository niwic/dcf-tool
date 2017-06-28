package fi.niwic.dcf.api;

import java.util.Optional;

/**
 * Laskelma jakson vapaasta kassavirrasta.
 */
public interface FreeCashFlowCalculation {
	
    /**
     * Toiminnan tulos.
     * @return tulos
     */
	public long getOperatingProfit();
    
    /**
     * Toiminnan kustannukset.
     * @return kustannukset
     */
	public long getTaxCosts();
    
    /**
     * Korkomenojen vaikutus veroihin.
     * @return Korkomenojen vaikutus
     */
	public long getInterestCostTaxEffect();
    
    /**
     * Korkotulojen vaikutus veroihin.
     * @return korkotulojen vaikutus
     */
	public long getInterestIncomeTaxEffect();
    
    /**
     * Muuiden tulojen/menojen vaikutus veroihin.
     * @return muiten tulojen/menojen vaikutus
     */
	public long getOtherPostsTaxEffect();

    /**
     * Toiminnan tulos ilman veroja.
     * @return toiminnan tulos ilman veroja
     */
	public long getNOPLAT();
	
    /**
     * Poistot.
     * @return poistot 
     */
	public long getDepreciation();
	
    /**
     * Kassavirta.
     * @return kassavirta
     */
	public long getGrossCashFlow();
	
    /**
     * Käyttöpääoman muutos.
     * @return käyttöpääoman muutos
     */
	public Optional<Long> getNetWorkingCapitalDelta();
    
    /**
     * Muutos investoineissa.
     * @return investointimuutos
     */
	public Optional<Long> getGrossInvestments();
	
    /**
     * Toiminnallinen kassavirta.
     * @return toiminnallinen kassavirta
     */
	public Optional<Long> getOperatingFreeCashFlow();
	
    /**
     * Toiminnan ulkopuolinen kassavirta.
     * @return toiminnan ulkopuolinen kassavirta
     */
	public Long getNonOperatingCashFlow();
	
    /**
     * Vapaa kassavirta.
     * @return vapaa kassavirta
     */
	public Optional<Long> getFreeCashFlow();
    
    /**
     * Diskontattu vapaa kassavirta.
     * @param costOfCapital pääoman kustannuslaskelma
     * @return diskontattu vapaa kassavirta
     */    
    public Optional<Long> getDiscountedFreeCashFlow(CostOfCapital costOfCapital);
	
}
