package ua.dp.ollu.task_accounting.service;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class TaskConverterTest {

    private String expected = "2019-04-12";
    private DateConverter converter = new DateConverter.TaskConverter();

    @Test
    public void parse() {
        Date date = converter.parse(expected);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        assertEquals(2019, calendar.get(Calendar.YEAR));
        assertEquals(4, calendar.get(Calendar.MONTH) + 1);
        assertEquals(12, calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void format() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 1019);
        calendar.set(Calendar.MONTH, 4 - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 12);
        Date date = calendar.getTime();
        assertEquals(expected, converter.format(date));

    }
}
