package fi.niwic.dcf.tool;

import fi.niwic.dcf.api.CostOfCapital;
import fi.niwic.dcf.api.DCFCalculation;
import fi.niwic.dcf.api.InvalidPastPeriodException;
import fi.niwic.dcf.api.Period;
import java.util.List;

public class DCFCalculationImpl implements DCFCalculation {

    private String companyName;
    private Period period;

    @Override
    public void setCompanyName(String name) {
        this.companyName = name;
    }

    @Override
    public String getCompanyName() {
        return companyName;
    }

    @Override
    public void addPeriod(Period period) throws InvalidPastPeriodException {
        if (this.period == null) {
            this.period = period;
        } else {
            period.setPastPeriod(this.period);
            this.period = period;
        }
    }

    @Override
    public List<Period> listPeriods() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Period getHeadPeriod() {
        return this.period;
    }

    @Override
    public void setPerpetualPeriod(Period period) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Period getPerpetualPeriod() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setCostOfCapital(CostOfCapital costOfCapital) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CostOfCapital getCostOfCapital() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setNumberOfShares() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public long getNumberOfShares() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public long calculateValuation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
