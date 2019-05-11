package ua.dp.ollu.task_accounting.service;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public interface DateConverter {

    Date parse(String string);

    String format(Date date);

    @Service("dateConverter")
    class TaskConverter implements DateConverter {
        private static final String pattern = "yyyy-MM-dd";
        private static final SimpleDateFormat formatter = new SimpleDateFormat(pattern);

        @Override
        public Date parse(String string) {
            try {
                return formatter.parse(string);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return new Date();
        }

        @Override
        public String format(Date date) {
            return formatter.format(date);
        }
    }
}
