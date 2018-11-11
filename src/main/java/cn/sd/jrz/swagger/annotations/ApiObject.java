package cn.sd.jrz.swagger.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作者 江荣展
 * 类名 ApiObject
 * 时间 2018/11/11 20:27
 */
@Target(value = {ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiObject {
}
