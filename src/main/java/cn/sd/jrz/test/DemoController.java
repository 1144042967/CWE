package cn.sd.jrz.test;

import cn.sd.jrz.swagger.annotations.Api;
import cn.sd.jrz.swagger.annotations.ApiMethod;
import cn.sd.jrz.swagger.annotations.ApiPrimitive;
import cn.sd.jrz.swagger.annotations.PrimitiveType;
import org.springframework.web.bind.annotation.RestController;

import static cn.sd.jrz.swagger.annotations.PrimitiveType.INTEGER;
import static cn.sd.jrz.swagger.annotations.PrimitiveType.STRING;

@Api(name = "子产品管理样例控制器",
        inHeader = {
                @ApiPrimitive(name = "userId", type = INTEGER, note = "用户ID", req = true),
                @ApiPrimitive(name = "token", type = STRING, note = "权限标记", req = true)
        },
        outHeader = {
                @ApiPrimitive(name = "resultCode", type = INTEGER, note = "结果码")
        }
)
@RestController
public class DemoController {
    @ApiMethod(name = "插入子产品",
            inPath = {
                    @ApiPrimitive(name = "handlerType", type = STRING, note = "操作类型", allow = "insert、delete", def = "insert")
            },
            inQuery = {
                    @ApiPrimitive(name = "productId", type = INTEGER, note = "要操作的产品ID", req = true)
            },
            inBody = {
                    @ApiPrimitive(name = "productSubList", type = PrimitiveType.LIST_MAP, note = "子产品列表", req = true),
                    @ApiPrimitive(name = "name", type = STRING, note = "子产品名称", req = true, ref = "productSubList"),
                    @ApiPrimitive(name = "projectType", type = INTEGER, note = "工程类型：1 家庭 2 楼宇", req = true, allow = "1、2", ref = "productSubList"),
                    @ApiPrimitive(name = "materiel", type = STRING, note = "物料编码", ref = "productSubList"),
                    @ApiPrimitive(name = "color", type = STRING, note = "颜色", req = true, ref = "productSubList"),
                    @ApiPrimitive(name = "image", type = STRING, note = "图片路径", ref = "productSubList"),
                    @ApiPrimitive(name = "state", type = INTEGER, note = "状态：0 正常 1 隐藏 2 缺货", req = true, ref = "productSubList")
            },
            outBody = {
                    @ApiPrimitive(name = "result", type = STRING, note = "操作结果", req = true)
            }
    )
    public String insert(String input) {
        return input;
    }
}
