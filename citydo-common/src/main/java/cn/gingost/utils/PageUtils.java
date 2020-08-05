package cn.gingost.utils;

import org.springframework.data.domain.Page;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author:lezzy
 * @Date:2020/8/3 14:06
 */

public class PageUtils {

    public static Map toPage(Page page) {
        Map<String,Object> map=new LinkedHashMap<>(4);
        map.put("total",page.getTotalElements());
        //map.put("page",page.getNumber());
        map.put("content",page.getContent());
        map.put("size",page.getSize());
        return map;
    }

    public static Map toPage(Object object, int page, int size, Object total) {
        Map<String, Object> map = new LinkedHashMap<>(4);
        map.put("content", object);
        //map.put("page", page);
        map.put("size", size);
        map.put("total", total);
        return map;
    }
}
