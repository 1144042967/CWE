package cn.sd.jrz.swagger.parser;

import cn.sd.jrz.swagger.annotations.Api;
import cn.sd.jrz.swagger.annotations.ApiMethod;
import cn.sd.jrz.swagger.annotations.ApiObject;
import cn.sd.jrz.swagger.annotations.ApiPrimitive;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;

import static cn.sd.jrz.swagger.annotations.PrimitiveType.*;

/**
 * 接口文档解析器
 */
public class ApiParser {
    public JsonArray parse(Class<?>... clss) throws ClassNotFoundException {
        JsonArray result = new JsonArray();
        for (Class controllerClass : clss) {
            JsonObject controllerApi = parseControllerClass(controllerClass);
            if (controllerApi != null) {
                result.add(controllerApi);
            }
        }
        return result;
    }

    private JsonObject parseControllerClass(Class<?> controllerClass) {
        JsonObject controllerApi = new JsonObject();
        Annotation[] annotations = controllerClass.getDeclaredAnnotations();
        boolean isApi = false;
        for (Annotation annotation : annotations) {
            if (annotation instanceof Api) {
                isApi = true;
                Api api = (Api) annotation;
                //处理类头
                controllerApi.addProperty(ApiConstant.controllerDescription, api.value());
                //处理接口
                JsonArray interfaceArray = new JsonArray();
                Method[] methods = controllerClass.getMethods();
                for (Method method : methods) {
                    JsonObject interfaceObject = parseInterfaceMethod(method);
                    if (interfaceObject != null) {
                        interfaceArray.add(interfaceObject);
                    }
                }
                controllerApi.add(ApiConstant.interfaceArray, interfaceArray);
            }
        }
        return isApi ? controllerApi : null;
    }

    private JsonObject parseInterfaceMethod(Method method) {
        JsonObject interfaceObject = new JsonObject();
        Annotation[] annotations = method.getDeclaredAnnotations();
        boolean isApiMethod = false;
        //处理接口相关注解
        for (Annotation annotation : annotations) {
            if (annotation instanceof RequestMapping) {
                RequestMapping rm = (RequestMapping) annotation;
                //路径
                if (rm.value().length > 0) {
                    JsonArray pathArray = new JsonArray();
                    Arrays.asList(rm.value()).forEach(path -> pathArray.add(new JsonPrimitive(path)));
                    interfaceObject.add(ApiConstant.pathArray, pathArray);
                }
                //请求方法
                if (rm.method().length > 0) {
                    JsonArray methodArray = new JsonArray();
                    Arrays.asList(rm.method()).forEach(m -> methodArray.add(new JsonPrimitive(m.toString())));
                    interfaceObject.add(ApiConstant.methodArray, methodArray);
                }
                //返回格式
                if (rm.produces().length > 0) {
                    JsonArray productArray = new JsonArray();
                    Arrays.asList(rm.produces()).forEach(product -> productArray.add(new JsonPrimitive(product)));
                    interfaceObject.add(ApiConstant.productArray, productArray);
                }
            } else if (annotation instanceof ApiMethod) {
                isApiMethod = true;
                ApiMethod am = (ApiMethod) annotation;
                //name
                interfaceObject.addProperty(ApiConstant.interfaceName, am.name());
                //path
                JsonArray inPath = parseParams(am.inPath(), null);
                if (inPath != null) {
                    interfaceObject.add(ApiConstant.inPath, inPath);
                }
                //query
                JsonArray inQuery = parseParams(am.inQuery(), null);
                if (inQuery != null) {
                    interfaceObject.add(ApiConstant.inQuery, inQuery);
                }
                //in header
                JsonArray inHeader = parseParams(am.inHeader(), null);
                if (inHeader != null) {
                    interfaceObject.add(ApiConstant.inHeader, inHeader);
                }
                //in body
                JsonArray inBody = parseParams(am.inBody(), null);
                if (inBody != null) {
                    interfaceObject.add(ApiConstant.inBody, inBody);
                }
                //out header
                JsonArray outHeader = parseParams(am.outHeader(), null);
                if (outHeader != null) {
                    interfaceObject.add(ApiConstant.outHeader, outHeader);
                }
                //out body
                JsonArray merge = merge(parseParams(am.outBody(), null), interfaceObject.getAsJsonArray(ApiConstant.outBody));
                if (merge != null) {
                    interfaceObject.add(ApiConstant.outBody, merge);
                }
            } else if (annotation instanceof ApiObject) {
                JsonArray result = merge(parseObject(method.getReturnType()), interfaceObject.getAsJsonArray(ApiConstant.outBody));
                if (result != null) {
                    interfaceObject.add(ApiConstant.outBody, result);
                }
            }
        }
        //处理ApiObject注解参数
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            for (Annotation annotation : parameter.getAnnotations()) {
                if (annotation instanceof ApiObject) {
                    JsonArray result = merge(parseObject(parameter.getType()), interfaceObject.getAsJsonArray(ApiConstant.inBody));
                    if (result != null) {
                        interfaceObject.add(ApiConstant.inBody, result);
                    }
                }
            }
        }
        return isApiMethod ? interfaceObject : null;
    }

    private JsonArray merge(JsonArray array1, JsonArray array2) {
        JsonArray result = new JsonArray();
        if (array1 != null) {
            for (int i = 0; i < array1.size(); i++) {
                result.add(array1.get(i));
            }
        }
        if (array2 != null) {
            for (int i = 0; i < array2.size(); i++) {
                result.add(array2.get(i));
            }
        }
        return result.size() > 0 ? result : null;
    }

    private JsonArray parseParams(ApiPrimitive[] primitives, JsonArray def) {
        if (primitives == null || primitives.length == 0 || isDefaultPrimitive(primitives)) {
            return def;
        }
        JsonArray paramArray = new JsonArray();
        for (ApiPrimitive primitive : primitives) {
            if (!"".equals(primitive.ref())) {
                continue;
            }
            JsonObject paramObject = new JsonObject();
            //处理基本数据
            paramObject.addProperty(ApiConstant.name, primitive.name());
            paramObject.addProperty(ApiConstant.type, primitive.type().getDescription());
            paramObject.addProperty(ApiConstant.note, primitive.note());
            paramObject.addProperty(ApiConstant.required, primitive.req());
            paramObject.addProperty(ApiConstant.defaultValue, primitive.def());
            paramObject.addProperty(ApiConstant.allowValue, primitive.allow());
            paramObject.addProperty(ApiConstant.example, primitive.example());
            //处理子类型数据
            if (primitive.type() == LIST_MAP || primitive.type() == MAP) {
                JsonArray subParams = parserSubParams(primitive.name(), primitives);
                if (subParams != null) {
                    paramObject.add(ApiConstant.subParams, subParams);
                }
            }
            paramArray.add(paramObject);
        }
        return paramArray.size() > 0 ? paramArray : def;
    }

    private boolean isDefaultPrimitive(ApiPrimitive[] primitives) {
        if (primitives.length == 1) {
            ApiPrimitive primitive = primitives[0];
            return "default".equals(primitive.name()) && STRING == primitive.type() && "default".equals(primitive.note());
        } else {
            return false;
        }
    }

    private JsonArray parserSubParams(String ref, ApiPrimitive[] primitives) {
        JsonArray subParams = new JsonArray();
        for (ApiPrimitive primitive : primitives) {
            if (!ref.equals(primitive.ref())) {
                continue;
            }
            JsonObject paramObject = new JsonObject();
            //处理基本数据
            paramObject.addProperty(ApiConstant.name, primitive.name());
            paramObject.addProperty(ApiConstant.type, primitive.type().getDescription());
            paramObject.addProperty(ApiConstant.note, primitive.note());
            paramObject.addProperty(ApiConstant.required, primitive.req());
            paramObject.addProperty(ApiConstant.defaultValue, primitive.def());
            paramObject.addProperty(ApiConstant.allowValue, primitive.allow());
            paramObject.addProperty(ApiConstant.example, primitive.example());
            //处理子类型数据
            if (primitive.type() == LIST_MAP || primitive.type() == MAP) {
                JsonArray subSubParams = parserSubParams(primitive.name(), primitives);
                if (subSubParams != null) {
                    paramObject.add(ApiConstant.subParams, subSubParams);
                }
            }
            subParams.add(paramObject);
        }
        return subParams.size() > 0 ? subParams : null;
    }

    private JsonArray parseObject(Class<?> cls) {
        JsonArray result = new JsonArray();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof ApiPrimitive) {
                    ApiPrimitive primitive = (ApiPrimitive) annotation;
                    JsonObject paramObject = new JsonObject();
                    //处理基本数据
                    paramObject.addProperty(ApiConstant.name, primitive.name());
                    paramObject.addProperty(ApiConstant.type, primitive.type().getDescription());
                    paramObject.addProperty(ApiConstant.note, primitive.note());
                    paramObject.addProperty(ApiConstant.required, primitive.req());
                    paramObject.addProperty(ApiConstant.defaultValue, primitive.def());
                    paramObject.addProperty(ApiConstant.allowValue, primitive.allow());
                    paramObject.addProperty(ApiConstant.example, primitive.example());
                    //处理子类型数据
                    if (primitive.type() == LIST_MAP) {
                        //获得泛型
                        ParameterizedType type = (ParameterizedType) field.getGenericType();
                        Class real = (Class) type.getActualTypeArguments()[0];
                        //获得类型
                        JsonArray subSubParams = parseObject(real);
                        if (subSubParams != null) {
                            paramObject.add(ApiConstant.subParams, subSubParams);
                        }

                    } else if (primitive.type() == MAP) {
                        //获得类型
                        JsonArray subSubParams = parseObject(field.getType());
                        if (subSubParams != null) {
                            paramObject.add(ApiConstant.subParams, subSubParams);
                        }
                    }
                    result.add(paramObject);
                }
            }
        }
        return result.size() > 0 ? result : null;
    }
}
