package homework.homework9.util;

public class Log {
    private static final boolean LOG_ENABLE = true;

    public static void log(Object message) {
        if (LOG_ENABLE) {
            System.out.println(message);
        }
    }
}
