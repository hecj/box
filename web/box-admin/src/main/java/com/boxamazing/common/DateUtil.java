package com.boxamazing.common;

import java.util.Date;

/**
 * Created by jhl on 15/9/6.
 */
public class DateUtil {

    private static final long DAY = 60 * 60 * 24 * 1000;

    /**
     * 返回从开始时间后经历天数后的时间
     *
     * @param startDate
     * @param days
     * @return
     */
    public static Date getDate(Date startDate, Integer days) {
        return new Date(startDate.getTime() + DAY * days);
    }

}
