package fi.niwic.dcf.ui.vm;

import fi.niwic.dcf.api.Period;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class PeriodView {

    private String header;
    private Function<Period, Long> getter;
    private BiConsumer<Period, Long> setter;
    
    public PeriodView(String header, Function<Period, Long> getter, BiConsumer<Period, Long> setter) {
        this.header = header;
        this.getter = getter;
        this.setter = setter;
    }
    
    public String getHeader() {
        return this.header;
    }
    
    public Function<Period, Long> getGetter() {
        return getter;
    }
    
    public BiConsumer<Period, Long> getSetter() {
        return setter;
    }
    
}
