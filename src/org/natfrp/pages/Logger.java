package org.natfrp.pages;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    public static void info(Object data) {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        System.out.println("[" + sdf.format(d) + " INFO] " + data);
    }
}
