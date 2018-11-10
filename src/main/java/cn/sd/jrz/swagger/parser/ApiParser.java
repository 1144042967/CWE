package cn.sd.jrz.swagger.parser;

import cn.sd.jrz.swagger.annotations.Api;
import cn.sd.jrz.swagger.annotations.ApiMethod;
import cn.sd.jrz.swagger.annotations.ApiPrimitive;
import cn.sd.jrz.swagger.annotations.PrimitiveType;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

import static cn.sd.jrz.swagger.annotations.PrimitiveType.*;

/**
 * 接口文档解析器
 */
public class ApiParser {
    public JsonArray parse(Class<?>... clss) throws ClassNotFoundException {
        JsonArray result = new JsonArray();
        for (Class cls : clss) {
            JsonObject parse = parse(cls);
            if (parse != null) {
                result.add(parse);
            }
        }
        return result;
    }

    private JsonObject parse(Class<?> cls) {
        //解析类注解
        Annotation[] annotations = cls.getAnnotations();
        boolean isRestController = false;
        Api api = null;
        for (Annotation annotation : annotations) {
            if (annotation instanceof RestController) {
                isRestController = true;
            } else if (annotation instanceof Api) {
                api = (Api) annotation;
            }
        }
        if (!isRestController || api == null) {
            return null;
        }
        JsonObject apiObject = new JsonObject();
        //解析类注解
        apiObject.addProperty("name", api.name());
        //解析方法注解
        JsonArray methodArray = new JsonArray();
        Method[] methods = cls.getMethods();
        for (Method m : methods) {
            JsonObject methodObject = parseMethod(m);
            if (methodObject != null) {
                methodArray.add(methodObject);
            }
        }
        apiObject.add("methodArray", methodArray);
        return apiObject;
    }

    private JsonObject parseMethod(Method m) {
        Annotation[] annotations = m.getDeclaredAnnotations();
        JsonObject methodObject = new JsonObject();
        boolean hasApiMethod = false;
        for (Annotation annotation : annotations) {
            if (annotation instanceof RequestMapping) {
                RequestMapping rm = (RequestMapping) annotation;
                //处理url
                String[] values = rm.value();
                JsonArray urlArray = new JsonArray();
                Arrays.asList(values).forEach(value -> urlArray.add(new JsonPrimitive(value)));
                methodObject.add("url", urlArray);
                //处理method
                RequestMethod[] methods = rm.method();
                JsonArray methodArray = new JsonArray();
                Arrays.asList(methods).forEach(value -> methodArray.add(new JsonPrimitive(value.toString())));
                methodObject.add("method", methodArray);
                //处理produces
                String[] produces = rm.produces();
                JsonArray produceArray = new JsonArray();
                Arrays.asList(produces).forEach(value -> produceArray.add(new JsonPrimitive(value)));
                methodObject.add("produces", produceArray);
            } else if (annotation instanceof ApiMethod) {
                hasApiMethod = true;
                ApiMethod am = (ApiMethod) annotation;
                JsonObject parse = parse(am.inPath());
                if (parse != null) {
                    methodObject.add("inPath", parse);
                }
                parse = parse(am.inQuery());
                if (parse != null) {
                    methodObject.add("inQuery", parse);
                }
                parse = parse(am.inHeader());
                if (parse != null) {
                    methodObject.add("inHeader", parse);
                }
                parse = parse(am.inBody());
                if (parse != null) {
                    methodObject.add("inBody", parse);
                }
                parse = parse(am.outHeader());
                if (parse != null) {
                    methodObject.add("outHeader", parse);
                }
                parse = parse(am.outBody());
                if (parse != null) {
                    methodObject.add("outBody", parse);
                }
            }
        }
        if (!hasApiMethod) {
            return null;
        } else {
            return methodObject;
        }
    }

    private JsonObject parse(ApiPrimitive[] primitives) {
        if (primitives == null || primitives.length == 0) {
            return null;
        }
        if (primitives.length == 1 && isDefaultPrimitive(primitives[0])) {
            return null;
        }
        return getObject(null, primitives);
    }

    private boolean isDefaultPrimitive(ApiPrimitive primitive) {
        String name = primitive.name();
        PrimitiveType type = primitive.type();
        String note = primitive.note();
        return "default".equals(name) && STRING == type && "default".equals(note);
    }

    private JsonObject getObject(ApiPrimitive masterPrimitive, ApiPrimitive[] primitives) {
        if (masterPrimitive == null) {
            System.out.println("解析根结点");
        } else {
            System.out.println("解析" + masterPrimitive.name());
        }
        JsonObject object = new JsonObject();
        String ref = "";
        if (masterPrimitive != null) {
            ref = masterPrimitive.name();
            setAttributes(object, masterPrimitive);
        }
        for (ApiPrimitive primitive : primitives) {
            if (!ref.equals(primitive.ref())) {
                continue;
            }
            if (primitive.type() == MAP || primitive.type() == LIST_MAP) {
                object.add(primitive.name(), getObject(primitive, primitives));
            }else{
                object.add(primitive.name(), getObject(null, primitives));
            }
        }
        return object;
    }

    private void setAttributes(JsonObject object, ApiPrimitive primitive) {
        object.addProperty("name", primitive.name());
        object.addProperty("type", primitive.type().getDescription());
        object.addProperty("note", primitive.note());
        if (primitive.req()) {
            object.addProperty("required", true);
        }
        if (!"".equals(primitive.def())) {
            object.addProperty("defaultValue", primitive.def());
        }
        if (!"".equals(primitive.allow())) {
            object.addProperty("allowableValues", primitive.allow());
        }
        if (!"".equals(primitive.example())) {
            object.addProperty("example", primitive.example());
        }
    }
}
