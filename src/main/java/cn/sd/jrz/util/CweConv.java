package cn.sd.jrz.util;

/**
 * Created with Software Dept.
 * <p>
 *
 * @author : 江荣展
 * Date: 2018-11-06
 * Time: 15:34
 * Description: CweConv 类型转换类
 */
public class CweConv {

    public static String tos(Object source) {
        return tos(source, null);
    }

    public static String tos(Object source, String def) {
        try {
            return source.toString();
        } catch (Exception e) {
            return def;
        }
    }

    public static Boolean tobo(Object source) {
        return tobo(source, null);
    }

    public static Boolean tobo(Object source, Boolean def) {
        try {
            return Boolean.valueOf(source.toString());
        } catch (Exception e) {
            return def;
        }
    }

    public static Byte tob(Object source) {
        return tob(source, null);
    }

    public static Byte tob(Object source, Byte def) {
        try {
            return tod(source).byteValue();
        } catch (Exception e) {
            return def;
        }
    }

    public static Short tosh(Object source) {
        return tosh(source, null);
    }

    public static Short tosh(Object source, Short def) {
        try {
            return tod(source).shortValue();
        } catch (Exception e) {
            return def;
        }
    }

    public static Integer toi(Object source) {
        return toi(source, null);
    }

    public static Integer toi(Object source, Integer def) {
        try {
            return tod(source).intValue();
        } catch (Exception e) {
            return def;
        }
    }

    public static Long tol(Object source) {
        return tol(source, null);
    }

    public static Long tol(Object source, Long def) {
        try {
            return tod(source).longValue();
        } catch (Exception e) {
            return def;
        }
    }

    public static Float tof(Object source) {
        return tof(source, null);
    }

    public static Float tof(Object source, Float def) {
        try {
            return tod(source).floatValue();
        } catch (Exception e) {
            return def;
        }
    }

    public static Double tod(Object source) {
        return tod(source, null);
    }

    public static Double tod(Object source, Double def) {
        try {
            return Double.valueOf(source.toString());
        } catch (Exception e) {
            return def;
        }
    }

}
