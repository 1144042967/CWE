package cn.sd.jrz.swagger.annotations;

import java.lang.annotation.*;

import static cn.sd.jrz.swagger.annotations.PrimitiveType.STRING;

/**
 * 用于标记控制器
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Api {
    /**
     * @return 控制器描述
     */
    String value();
}
