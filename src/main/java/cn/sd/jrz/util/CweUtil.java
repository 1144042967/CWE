package cn.sd.jrz.util;

import cn.sd.jrz.function.CweSupplier;

/**
 * Created with Software Dept.
 * <p>
 *
 * @author : 江荣展
 * Date: 2018-11-07
 * Time: 9:10
 * Description: CweUtil
 */
public class CweUtil {
    /**
     * 判断字符串为空引用
     * 例如：
     * null -> true
     * "" -> false
     * "   " -> false
     * "adb" -> false
     * "   abc" -> false
     * "abc   " -> false
     *
     * @param value 要判断的值
     * @return 判断结果
     */
    public static boolean isNull(Object value) {
        return value == null;
    }

    /**
     * 判断字符串为空字符串
     * 例如：
     * null -> true
     * "" -> true
     * "   " -> false
     * "adb" -> false
     * "   abc" -> false
     * "abc   " -> false
     *
     * @param value 要判断的值
     * @return 判断结果
     */
    public static boolean isEmpty(String value) {
        return isNull(value) || value.length() == 0;
    }

    /**
     * 判断字符串为空白字符串
     * 例如：
     * null -> true
     * "" -> true
     * "   " -> true
     * "adb" -> false
     * "   abc" -> false
     * "abc   " -> false
     *
     * @param value 要判断的值
     * @return 判断结果
     */
    public static boolean isBlank(String value) {
        return isEmpty(value) || value.trim().length() == 0;
    }

    /**
     * 判断 Integer 值为 true
     * 例如：
     * null -> false
     * 0 -> false
     * 1 -> true
     * -100 -> true
     *
     * @param value 要判断的值
     * @return 判断结果
     */
    public static boolean isTrue(Integer value) {
        return value != null && value != 0;
    }

    /**
     * 判断 Boolean 值为 true
     * 例如：
     * null -> false
     * FALSE -> false
     * TRUE -> true
     *
     * @param value 要判断的值
     * @return 判断结果
     */
    public static boolean isTure(Boolean value) {
        return value != null && value;
    }

    /**
     * 判断字符串为 true
     * 例如：
     * null -> false
     * "" -> false
     * "abc" -> false
     * "false" -> false
     * "true" -> true
     *
     * @param value 要判断的值
     * @return 判断结果
     */
    public static boolean isTure(String value) {
        return "true".equals(value);
    }

    /**
     * 用于处理可抛出异常的获参数方法
     * 例如： Integer name = CweUtil.cache(()->json.get("name").getAsInteger());
     *
     * @param supplier 参数获取部分
     * @param <T>      参数类型
     * @return 参数，如果出现异常，返回null
     */
    public static <T> T cache(CweSupplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            return null;
        }
    }
}
