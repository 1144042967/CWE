package cn.sd.jrz.function;

/**
 * Created with Software Dept.
 * <p>
 *
 * @author : 江荣展
 * Date: 2018-11-07
 * Time: 9:33
 * Description: CweSupplier
 */
@FunctionalInterface
public interface CweSupplier<T> {
    /**
     * 获得结果
     *
     * @return 结果
     * @throws Exception 发生错误时
     */
    T get() throws Exception;
}
