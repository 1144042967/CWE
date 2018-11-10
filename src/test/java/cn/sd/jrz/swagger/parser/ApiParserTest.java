package cn.sd.jrz.swagger.parser;

import cn.sd.jrz.test.DemoController;
import com.google.gson.JsonArray;
import org.junit.Test;

public class ApiParserTest {
    @Test
    public void parserTest() throws ClassNotFoundException {
        ApiParser apiParser = new ApiParser();
        JsonArray parse = apiParser.parse(DemoController.class);
        System.out.println(parse);
    }
}
