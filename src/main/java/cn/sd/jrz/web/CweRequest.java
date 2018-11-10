package cn.sd.jrz.web;

import cn.sd.jrz.date.CweDate;
import cn.sd.jrz.util.CweConv;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created with Software Dept.
 * <p>
 *
 * @author : 江荣展
 * Date: 2018-11-06
 * Time: 15:26
 * Description: CweRequest request读取类
 */
public class CweRequest {

    private HttpServletRequest request;

    public CweRequest(HttpServletRequest request) {
        if (request == null) {
            throw new NullPointerException("request is null");
        }
        this.request = request;
    }

    public String getHeader(String key) {
        return request.getHeader("key");
    }

    public String getString(String key) {
        try {
            return URLDecoder.decode(request.getHeader(key), "utf-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public Boolean getBoolean(String key) {
        return CweConv.tobo(request.getHeader(key));
    }

    public Byte getByte(String key) {
        return CweConv.tob(request.getHeader(key));
    }

    public Short getShort(String key) {
        return CweConv.tosh(request.getHeader(key));
    }

    public Integer getInteger(String key) {
        return CweConv.toi(request.getHeader(key));
    }

    public Float getFloat(String key) {
        return CweConv.tof(request.getHeader(key));
    }

    public Double getDouble(String key) {
        return CweConv.tod(request.getHeader(key));
    }

    public CweDate getDate(String key) {
        try {
            return CweDate.of(URLDecoder.decode(request.getHeader(key), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
