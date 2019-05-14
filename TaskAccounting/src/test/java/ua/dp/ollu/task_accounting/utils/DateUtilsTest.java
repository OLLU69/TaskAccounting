package ua.dp.ollu.task_accounting.utils;

import org.junit.Assert;
import org.junit.Test;
import ua.dp.ollu.task_accounting.service.TaskConverterImpl;

import java.util.Date;

public class DateUtilsTest {

    private TaskConverterImpl converter = new TaskConverterImpl();

    @Test
    public void isIntersectionTest() {
        DateInterval interval = new DateInterval("2019-05-05", "2019-05-05", "2019-05-10", "2019-05-10");
        Assert.assertFalse(DateUtils.isIntersection(interval.startDate1, interval.endDate1, interval.startDate2, interval.endDate2));

        interval = new DateInterval("2019-05-05", "2019-05-10", "2019-05-10", "2019-05-10");
        Assert.assertTrue(DateUtils.isIntersection(interval.startDate1, interval.endDate1, interval.startDate2, interval.endDate2));

        interval = new DateInterval("2019-05-05", "2019-05-11", "2019-05-10", "2019-05-12");
        Assert.assertTrue(DateUtils.isIntersection(interval.startDate1, interval.endDate1, interval.startDate2, interval.endDate2));

        interval = new DateInterval("2019-05-05", "2019-05-12", "2019-05-10", "2019-05-12");
        Assert.assertTrue(DateUtils.isIntersection(interval.startDate1, interval.endDate1, interval.startDate2, interval.endDate2));

        interval = new DateInterval("2019-05-05", "2019-05-13", "2019-05-10", "2019-05-12");
        Assert.assertTrue(DateUtils.isIntersection(interval.startDate1, interval.endDate1, interval.startDate2, interval.endDate2));


        interval = new DateInterval("2019-05-10", "2019-05-10", "2019-05-10", "2019-05-12");
        Assert.assertTrue(DateUtils.isIntersection(interval.startDate1, interval.endDate1, interval.startDate2, interval.endDate2));

        interval = new DateInterval("2019-05-10", "2019-05-11", "2019-05-10", "2019-05-12");
        Assert.assertTrue(DateUtils.isIntersection(interval.startDate1, interval.endDate1, interval.startDate2, interval.endDate2));

        interval = new DateInterval("2019-05-10", "2019-05-12", "2019-05-10", "2019-05-12");
        Assert.assertTrue(DateUtils.isIntersection(interval.startDate1, interval.endDate1, interval.startDate2, interval.endDate2));

        interval = new DateInterval("2019-05-10", "2019-05-13", "2019-05-10", "2019-05-12");
        Assert.assertTrue(DateUtils.isIntersection(interval.startDate1, interval.endDate1, interval.startDate2, interval.endDate2));


        interval = new DateInterval("2019-05-11", "2019-05-11", "2019-05-10", "2019-05-12");
        Assert.assertTrue(DateUtils.isIntersection(interval.startDate1, interval.endDate1, interval.startDate2, interval.endDate2));

        interval = new DateInterval("2019-05-11", "2019-05-12", "2019-05-10", "2019-05-12");
        Assert.assertTrue(DateUtils.isIntersection(interval.startDate1, interval.endDate1, interval.startDate2, interval.endDate2));

        interval = new DateInterval("2019-05-11", "2019-05-13", "2019-05-10", "2019-05-12");
        Assert.assertTrue(DateUtils.isIntersection(interval.startDate1, interval.endDate1, interval.startDate2, interval.endDate2));


        interval = new DateInterval("2019-05-12", "2019-05-12", "2019-05-10", "2019-05-12");
        Assert.assertTrue(DateUtils.isIntersection(interval.startDate1, interval.endDate1, interval.startDate2, interval.endDate2));

        interval = new DateInterval("2019-05-12", "2019-05-13", "2019-05-10", "2019-05-12");
        Assert.assertTrue(DateUtils.isIntersection(interval.startDate1, interval.endDate1, interval.startDate2, interval.endDate2));

        interval = new DateInterval("2019-05-13", "2019-05-14", "2019-05-10", "2019-05-12");
        Assert.assertFalse(DateUtils.isIntersection(interval.startDate1, interval.endDate1, interval.startDate2, interval.endDate2));
    }

    class DateInterval {
        private final Date startDate1;
        private final Date endDate1;
        private final Date startDate2;
        private final Date endDate2;

        DateInterval(String startDate1, String endDate1, String startDate2, String endDate2) {
            this.startDate1 = converter.parse(startDate1);
            this.endDate1 = converter.parse(endDate1);
            this.startDate2 = converter.parse(startDate2);
            this.endDate2 = converter.parse(endDate2);
        }
    }
}
