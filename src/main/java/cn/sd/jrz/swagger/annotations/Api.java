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
    ApiPrimitive[] outBody() default@ApiPrimitive(name = "default", type = STRING, note = "default");
}
