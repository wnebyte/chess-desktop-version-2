package util;

import static java.lang.System.out;
import static java.lang.System.err;

public final class Log {

    public static void i(String message) {
        out.println(message);
    }

    public static void e(String message) {
        err.println(message);
    }

    public static void e(Object message) {
        err.println((message != null) ? message.toString() : "");
    }
}
