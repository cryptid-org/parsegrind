package org.jenkinsci.plugins.valgrind.util;

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


