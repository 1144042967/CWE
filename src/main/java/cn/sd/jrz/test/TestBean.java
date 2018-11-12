package cn.sd.jrz.test;

import cn.sd.jrz.swagger.annotations.ApiPrimitive;
import cn.sd.jrz.swagger.annotations.PrimitiveType;

import java.util.List;

/**
 * 作者 江荣展
 * 类名 TestBean
 * 时间 2018/11/11 20:30
 */
public class TestBean {
    @ApiPrimitive(name = "key", type = PrimitiveType.STRING, note = "测试键")
    private String key;
    @ApiPrimitive(name = "list", type = PrimitiveType.LIST_STRING, note = "测试列表")
    private List<String> list;
    @ApiPrimitive(name = "testSubBeanList", type = PrimitiveType.LIST_MAP, note = "测试List<Map>")
    private List<TestSubBean> testSubBeanList;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public List<TestSubBean> getTestSubBeanList() {
        return testSubBeanList;
    }

    public void setTestSubBeanList(List<TestSubBean> testSubBeanList) {
        this.testSubBeanList = testSubBeanList;
    }
}
