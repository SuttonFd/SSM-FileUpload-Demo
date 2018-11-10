package xin.sutton.test.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO...
 *
 * @author codingZhengsz
 * @since 2018-11-09 12:29
 **/
public class DateFormatTest {



    public static void main(String[] args) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = df.parse("1970-01-02");
        long dateTime = date.getTime();
        System.out.println(dateTime);

        System.out.println(df.parse("2018-11-08").getTime());
        System.out.println(df.parse("2018-11-10").getTime());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = new Date(dateTime);
        System.out.println(sdf.format(date1));

    }
}
