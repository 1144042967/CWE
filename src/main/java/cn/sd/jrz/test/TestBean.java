package cn.sd.jrz.test;

import cn.sd.jrz.swagger.annotations.ApiPrimitive;
import cn.sd.jrz.swagger.annotations.PrimitiveType;

/**
 * 作者 江荣展
 * 类名 TestBean
 * 时间 2018/11/11 20:30
 */
public class TestBean {
    @ApiPrimitive(name = "key", type = PrimitiveType.STRING, note = "测试键")
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
