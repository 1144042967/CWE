package cn.sd.jrz.test;

import cn.sd.jrz.swagger.annotations.ApiPrimitive;
import cn.sd.jrz.swagger.annotations.PrimitiveType;

public class TestSubBean {
    @ApiPrimitive(name = "key", type = PrimitiveType.STRING, note = "测试键")
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
