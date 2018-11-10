package cn.sd.jrz.swagger.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static cn.sd.jrz.swagger.annotations.PrimitiveType.STRING;

/**
 * 用于描述接口
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiMethod {
    /**
     * @return 接口功能描述
     */
    String name() default "";

    /**
     * @return 输入路径参数结构描述
     */
    ApiPrimitive[] inPath() default @ApiPrimitive(name = "default", type = STRING, note = "default");

    /**
     * @return 输入查询字符串结构描述
     */
    ApiPrimitive[] inQuery() default @ApiPrimitive(name = "default", type = STRING, note = "default");

    /**
     * @return 请求头结构描述
     */
    ApiPrimitive[] inHeader() default @ApiPrimitive(name = "default", type = STRING, note = "default");

    /**
     * @return 输入body结构描述
     */
    ApiPrimitive[] inBody() default @ApiPrimitive(name = "default", type = STRING, note = "default");

    /**
     * @return 输出响应头结构描述
     */
    ApiPrimitive[] outHeader() default @ApiPrimitive(name = "default", type = STRING, note = "default");

    /**
     * @return 输出body结构描述
     */
    ApiPrimitive[] outBody() default @ApiPrimitive(name = "default", type = STRING, note = "default");
}
