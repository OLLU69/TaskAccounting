package ua.dp.ollu.task_accounting.utils;

import java.util.Date;

public class DateUtils {
    public static boolean isIntersection(Date start1, Date end1, Date start2, Date end2) {
        return !start1.after(end2) && !start2.after(end1);
    }
}
