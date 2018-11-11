package cn.sd.jrz.swagger.parser;

/**
 * 作者 江荣展
 * 类名 ApiConstant
 * 时间 2018/11/10 22:21
 */
public class ApiConstant {
    public static String controllerDescription;

    public static String interfaceArray;

    public static String pathArray;

    public static String methodArray;

    public static String productArray;

    public static String interfaceName;

    public static String inPath;

    public static String inQuery;

    public static String inHeader;

    public static String inBody;

    public static String outHeader;

    public static String outBody;

    public static String name;

    public static String type;

    public static String note;

    public static String required;

    public static String defaultValue;

    public static String allowValue;

    public static String example;

    public static String subParams;

    static {
        init(1);
    }

    public static void init(int i) {
        controllerDescription = i == 0 ? "controllerDescription" : "控制器描述";
        interfaceArray = i == 0 ? "interfaceArray" : "接口列表";
        pathArray = i == 0 ? "pathArray" : "路径列表";
        methodArray = i == 0 ? "methodArray" : "请求方式列表";
        productArray = i == 0 ? "productArray" : "返回参数类型";
        interfaceName = i == 0 ? "interfaceName" : "接口功能描述";
        inPath = i == 0 ? "inPath" : "路径请求参数";
        inQuery = i == 0 ? "inQuery" : "查询请求参数";
        inHeader = i == 0 ? "inHeader" : "请求头参数";
        inBody = i == 0 ? "inBody" : "请求体参数";
        outHeader = i == 0 ? "outHeader" : "响应头参数";
        outBody = i == 0 ? "outBody" : "响应体参数";
        name = i == 0 ? "name" : "参数名";
        type = i == 0 ? "type" : "参数类型";
        note = i == 0 ? "note" : "参数描述";
        required = i == 0 ? "required" : "必须存在";
        defaultValue = i == 0 ? "defaultValue" : "默认值";
        allowValue = i == 0 ? "allowValue" : "枚举值";
        example = i == 0 ? "example" : "示例";
        subParams = i == 0 ? "subParams" : "子属性";
    }
}
