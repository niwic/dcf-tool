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

    /**
     * Asettaa tarkasteltavana olevan yrityksen nimen.
     * @param name yrityksen nimi
     */
    public void setCompanyName(String name);

    /**
     * Hakee yrityksen nimen.
     * @return yrityksen nimi
     */
    public String getCompanyName();

    /**
     * Lisää tarkasteltavan jakson.
     * @param period jakso
     * @throws InvalidPastPeriodException 
     */
    public void addPeriod(Period period) throws InvalidPastPeriodException;

    /**
     * Listaa kaikki jaksot.
     * @return jaksot
     */
    public List<Period> listPeriods();

    /**
     * Hakee uusimman lisätyn jakson.
     * @return uusin lisätty jakso
     */
    public Period getHeadPeriod();

    /**
     * Asettaa viimeisen jakson.
     * @param period viimeinen jakso
     */
    public void setPerpetualPeriod(Period period);

    /**
     * Palauttaa viimeisen jakson.
     * @return viimeinen jakso
     */
    public Period getPerpetualPeriod();

    /**
     * Asettaa pääomakustannuslaskurin.
     * @param costOfCapital pääomakustnanuslaskuri
     */
    public void setCostOfCapital(CostOfCapital costOfCapital);

    /**
     * Palauttaa pääomakustannuslaskurin.
     * @return pääomakustannuslaskurin
     */
    public CostOfCapital getCostOfCapital();

    /**
     * Asettaa osakkeiden lukumäärän.
     * @param shares osakkeiden lukumäärä
     */
    public void setNumberOfShares(Long shares);

    /**
     * Hakee osakkeiden lukumäärän.
     * @return osakkeiden lukumäärä
     */
    public long getNumberOfShares();

    /**
     * Palautta yrityksen arvon osaketta kohti.
     * @return yrityksen arvo osaketta kohti
     */
    public long calculateValuation();

}
