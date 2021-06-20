package com.cvc.cvcms.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ZZJ
 * @date 2021/4/3 16:29
 * @desc
 */
@SpringBootTest
class SalesRecordServiceImplTest {
    @Test
    void calendarTest(){
        Calendar start = Calendar.getInstance(Locale.CHINA);
        int day_of_week = start.get(Calendar.DAY_OF_WEEK) - 1 == 0? 7 : start.get(Calendar.DAY_OF_WEEK) - 1;
        start.add(Calendar.DATE, -day_of_week-6);
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);
        System.out.println(start.getTime());
    }
}