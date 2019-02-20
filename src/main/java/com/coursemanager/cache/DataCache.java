package com.coursemanager.cache;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author 李如豪
 * @Date 2019/2/19 15:51
 * @VERSION 1.0
 **/
@Component
public class DataCache {
    private static Map<String,Map<String,Long>> userAccessRecordMap = new ConcurrentHashMap<>(256);

    @PostConstruct
    public void init(){
    }

    /**
     * 缓存新增用户访问方法的时间
     * @param userId 用户id
     * @param method 方法名
     */
    public static void addUserAccessRecord(String userId,String method) {
        synchronized (userAccessRecordMap) {
            Long currentTime = System.currentTimeMillis();
            if (userAccessRecordMap.isEmpty() || !userAccessRecordMap.containsKey(userId)) {
                Map<String,Long> map = new ConcurrentHashMap();
                map.put(method,currentTime);
                userAccessRecordMap.put(userId,map);
            }else {
                Map<String,Long> map = userAccessRecordMap.get(userId);
                map.put(method,currentTime);
            }
        }
    }

    /**
     * 缓存删除用户访问方法的时间
     * @param userId 用户id
     */
    public static void deleteUserAccessRecord(String userId) {
        synchronized (userAccessRecordMap) {
            userAccessRecordMap.remove(userId);
        }
    }

    /**
     * 根据userId获取用户姓名
     * @param userId
     * @return
     */
    public static Long getLastTime(String userId,String method) {
        if(!userAccessRecordMap.isEmpty()){
            Map<String,Long> map = userAccessRecordMap.get(userId);
            if(!map.isEmpty()){
                Long time = map.get(method);
                return time == null ? 0L : time;
            }
        }
        return 0L;
    }
}
