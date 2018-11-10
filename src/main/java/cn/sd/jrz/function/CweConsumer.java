package cn.sd.jrz.function;

import java.util.function.Consumer;

/**
 * Created with Software Dept.
 * <p>
 *
 * @author : 江荣展
 * Date: 2018-11-07
 * Time: 10:19
 * Description: CweConsumer
 */
@FunctionalInterface
public interface CweConsumer<T> {

    static <R> Consumer<R> catchException(CweConsumer<R> consumer) {
        return (R r) -> {
            try {
                consumer.accept(r);
            } catch (Exception ignored) {
            }
        };
    }

    /**
     * 进行数据处理
     *
     * @param t 输入
     * @throws Exception 发生错误时
     */
    void accept(T t) throws Exception;

    /**
     * 将两步处理过程组合
     *
     * @param after 下一步处理进程
     * @return 组合后的处理过程
     * @throws Exception 发生错误时
     */
    default CweConsumer<T> andThen(CweConsumer<? super T> after) throws Exception {
        return (T t) -> {
            accept(t);
            after.accept(t);
        };
    }
}
