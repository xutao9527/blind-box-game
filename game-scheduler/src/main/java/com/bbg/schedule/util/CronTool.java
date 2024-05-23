package com.bbg.schedule.util;

import java.time.LocalDateTime;

public class CronTool {
    public static String convertToCron(LocalDateTime localDateTime) {
        // 提取各个时间字段
        int second = localDateTime.getSecond();
        int minute = localDateTime.getMinute();
        int hour = localDateTime.getHour();
        int dayOfMonth = localDateTime.getDayOfMonth();
        int month = localDateTime.getMonthValue();
        int year = localDateTime.getYear();
        return String.format("%d %d %d %d %d ? %d", second, minute, hour, dayOfMonth, month, year);
    }
}
