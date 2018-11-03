package xin.sutton.test.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * 测试类以及参数化测试方法
 * @author codingZhengsz
 * @since 2018-10-28 12:57
 **/
@RunWith(Parameterized.class)
public class MyCalendar2Test {

    private int expectResult;
    private int year;
    private int month;
    private MyCalendar2 myCalendar2;

    @Before
    public void initialize(){
        myCalendar2 = new MyCalendar2();
    }

    public MyCalendar2Test(int expectResult,int year,int month){
        this.expectResult = expectResult;
        this.year = year;
        this.month = month;
    }

    @Parameterized.Parameters
    public static Collection primeNumbers() {
        return Arrays.asList(new Object[][]{
                {31,2018,1},
                {29,2016,2},
                {31,2017,3},
                {30,2005,4},
                {31,1994,5},
                {30,2014,6},
                {31,1997,7},
                {31,1998,8},
                {30,2009,9},
                {31,2010,10},
                {30,2011,11},
                {31,2015,12},
                {28,2015,2},
        });

    }

    @Test
    public void testGetNumberOfDaysInMonth(){
        System.out.println("Output:Expected:{" + expectResult+"};Input:year:{"+year+"},"+"month:{"+month+"}.");
        Assert.assertEquals(expectResult,myCalendar2.getNumberOfDaysInMonth(year,month));
    }
}
