package com.boxamazing.common;

import org.junit.Test;

import java.util.Date;

/**
 * Created by jhl on 15/9/6.
 */
public class DateUtilTest {
    @Test
    public void getDate() {
        Date now = new Date();
        Date future = DateUtil.getDate(now, 10);
        System.out.println(now);
        System.out.println(future);
    }
}
