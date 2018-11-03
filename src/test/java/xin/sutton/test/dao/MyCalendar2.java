package xin.sutton.test.dao;

/**
 * 被测试方法
 *
 * @author codingZhengsz
 * @since 2018-10-28 12:56
 **/
public class MyCalendar2 {

    public int getNumberOfDaysInMonth(int year, int month) {
        if (month == 1 || month == 3 || month == 6 || month == 7 ||
                month == 8 || month == 10 )
            return 31;
        if (month == 4 || month == 5 || month == 9 || month == 11)
            return 30;
        if (month == 2) return  28;
        return 0; // If month is incorrect
    }

}
