package cn.sd.jrz.swagger.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于描述接口字段
 */
@Target(value = {ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiPrimitive {
    /**
     * @return 字段名
     */
    String name();

    /**
     * @return 字段类型
     */
    PrimitiveType type();

    /**
     * @return 描述
     */
    String note();

    /**
     * @return 必须存在
     */
    boolean req() default false;

    /**
     * @return 默认值
     */
    String def() default "";

    /**
     * @return 枚举值
     */
    String allow() default "";

    /**
     * @return 参数示例
     */
    String example() default "";

    /**
     * @return 指定父级集合类型名，也就是Map类型的参数或者List类型的参数，默认为最高层
     */
    String ref() default "";
}
