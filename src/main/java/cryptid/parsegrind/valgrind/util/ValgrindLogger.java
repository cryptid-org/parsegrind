package cryptid.parsegrind.valgrind.util;

import java.util.logging.Logger;

public abstract class ValgrindLogger {
    private static final Logger LOGGER = Logger.getLogger(ValgrindLogger.class.getSimpleName());

    public static void log(final String message) {
        LOGGER.info(message);
    }

    public static void logFine(final String message) {
        LOGGER.fine(message);
    }

    public static void logWarn(final String message) {
        LOGGER.warning(message);
    }
}
