package fi.niwic.dcf.tool;

/**
* Luokka jolla voidaan laskea yhteen taulukon arvot.
*/
public class Sum {
    public static long ofItems(long[] items) throws ArithmeticException {
        long sum = 0;
        for (int n = 0; n < items.length; n++) {
            sum = Math.addExact(sum, items[n]);
        }
        
        return sum;
    }
}
