package cryptid.parsegrind.valgrind.util;

public abstract class ValgrindUtil {
    public static String trimToNull(String s) {
        if (s == null)
            return null;

        s = s.trim();

        if (s.isEmpty())
            return null;

        return s;
    }
}


