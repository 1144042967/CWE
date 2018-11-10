package cn.sd.jrz.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with Software Dept.
 * <p>
 * User: 江荣展
 * Date: 2018-02-02
 * Time: 13:42
 * Description: 不可变日期类
 */
public final class CweDate {
    private final Instant date;

    public CweDate() {
        this.date = new Date().toInstant();
    }

    public static CweDate of(String date) {
        return of(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static CweDate of(String date, String format) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            Date d = dateFormat.parse(date);
            if (d == null) {
                return null;
            } else {
                return new CweDate(d);
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static CweDate of(int year, int month, int day, int hour, int minute, int second) {
        return new CweDate(LocalDateTime.of(year, month, day, hour, minute, second));
    }

    public CweDate(Date date) {
        if (date == null) {
            throw new NullPointerException("date is null");
        }
        this.date = date.toInstant();
    }

    public CweDate(Calendar calendar) {
        if (calendar == null) {
            throw new NullPointerException("calendar is null");
        }
        this.date = calendar.getTime().toInstant();
    }

    public CweDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            throw new NullPointerException("localDateTime is null");
        }
        this.date = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
    }

    public final Date toDate() {
        return Date.from(date);
    }

    public final LocalDateTime toLocalDateTime() {
        return LocalDateTime.ofInstant(date, ZoneId.systemDefault());
    }

    public final Calendar toCalendar() {
        Calendar instance = Calendar.getInstance();
        instance.setTime(toDate());
        return instance;
    }

    public final CweDate addSeconds(long seconds) {
        return new CweDate(toLocalDateTime().plusSeconds(seconds));
    }

    public final CweDate addMinutes(long minutes) {
        return new CweDate(toLocalDateTime().plusMinutes(minutes));
    }

    public final CweDate addHours(long hours) {
        return new CweDate(toLocalDateTime().plusHours(hours));
    }

    public final CweDate addDays(long days) {
        return new CweDate(toLocalDateTime().plusDays(days));
    }

    public final CweDate addMonths(long months) {
        return new CweDate(toLocalDateTime().plusMonths(months));
    }

    public final CweDate addYears(long years) {
        return new CweDate(toLocalDateTime().plusYears(years));
    }

    public final int second() {
        return toLocalDateTime().getSecond();
    }

    public final int minute() {
        return toLocalDateTime().getMinute();
    }

    public final int hour() {
        return toLocalDateTime().getHour();
    }

    public final int day() {
        return toLocalDateTime().getDayOfMonth();
    }

    public final int month() {
        return toLocalDateTime().getMonthValue();
    }

    public final int year() {
        return toLocalDateTime().getYear();
    }

    public final boolean isBefore(CweDate cweDate) {
        return toLocalDateTime().isBefore(cweDate.toLocalDateTime());
    }

    public final boolean isBefore(Date date) {
        return isBefore(new CweDate(date));
    }

    public final boolean isBefore(LocalDateTime localDateTime) {
        return isBefore(new CweDate(localDateTime));
    }

    public final boolean isBefore(Calendar calendar) {
        return isBefore(new CweDate(calendar));
    }

    public final boolean isAfter(CweDate cweDate) {
        return toLocalDateTime().isAfter(cweDate.toLocalDateTime());
    }

    public final boolean isAfter(Date date) {
        return isAfter(new CweDate(date));
    }

    public final boolean isAfter(LocalDateTime localDateTime) {
        return isAfter(new CweDate(localDateTime));
    }

    public final boolean isAfter(Calendar calendar) {
        return isAfter(new CweDate(calendar));
    }

    public final long timeMillis() {
        return toDate().getTime();
    }

    @Override
    public final String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(toDate());
    }

    /**
     * 判断与目标对象数据是否相等，支持Date、LocalDateTime、Calendar、CweDate、格式化字符串(yyyy-MM-dd HH:mm:ss)类数值比较
     *
     * @param o 目标对象
     * @return 数据相等返回true，否则返回false
     */
    public final boolean equalsIgnore(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof Date) {
            return toDate().equals(o);
        }
        if (o instanceof LocalDateTime) {
            return toLocalDateTime().equals(o);
        }
        if (o instanceof Calendar) {
            return toCalendar().equals(o);
        }
        if (o instanceof String) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date d = dateFormat.parse((String) o);
                return toDate().equals(d);
            } catch (ParseException e) {
                return false;
            }
        }
        return o instanceof CweDate && this.date.equals(((CweDate) o).date);
    }
}
