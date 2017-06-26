package fi.niwic.dcf.api;

import java.util.List;
import java.util.Optional;

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
     * Hakee uusimman lisätyn jakson.
     * @return uusin lisätty jakso
     */
    public Period getHeadPeriod();

    /**
     * Asettaa viimeisen jakson.
     * @param period viimeinen jakso
     */
    public void setPerpetualPeriod(Period period) throws InvalidPastPeriodException;

    /**
     * Palauttaa viimeisen jakson.
     * @return viimeinen jakso
     */
    public Optional<Period> getPerpetualPeriod();

    /**
     * Palauttaa pääomakustannuslaskurin.
     * @return pääomakustannuslaskurin
     */
    public Optional<CostOfCapital> getCostOfCapital();

    /**
     * Asettaa osakkeiden lukumäärän.
     * @param shares osakkeiden lukumäärä
     */
    public void setNumberOfShares(Long shares);

    /**
     * Hakee osakkeiden lukumäärän.
     * @return osakkeiden lukumäärä
     */
    public Optional<Long> getNumberOfShares();

    /**
     * Palautta yrityksen arvon osaketta kohti.
     * @return yrityksen arvo osaketta kohti
     */
    public Optional<Long> calculateValuation();

}
