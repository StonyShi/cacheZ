package com.stone.cache.test;

import com.google.common.collect.Sets;
import com.stone.cache.annotation.Cachezable;
import com.stone.cache.util.CollectionUtil;
import com.stone.cache.util.DateUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>Created with IntelliJ IDEA. </p>
 * <p>User: Stony </p>
 * <p>Date: 2016/10/11 </p>
 * <p>Time: 11:34 </p>
 * <p>Version: 1.0.0 </p>
 */
@Service("appService")
public class AppServiceImpl implements AppService{

    public static final String REMOVE_WATCH_KEYS = "AppKeys";
    List<App> apps = new LinkedList<>();

    @Cachezable(key = "ALL,MAPALL", prefix = SecurityKeys.KEY_FIND_APP, type = Cachezable.CachezType.REMOVE, watch = REMOVE_WATCH_KEYS)
    public App createApp(App app) {
        apps.add(app);
        return app;
    }

    @Override
    @Cachezable(key = "#app.id,ALL,MAPALL", prefix = SecurityKeys.KEY_FIND_APP, type = Cachezable.CachezType.REMOVE, watch = REMOVE_WATCH_KEYS)
    public App updateApp(App app) {
        deleteApp(app.getId());
        apps.add(app);
        return app;
    }

    @Override
    @Cachezable(key = "#appId,ALL,MAPALL", prefix = SecurityKeys.KEY_FIND_APP, type = Cachezable.CachezType.REMOVE, watch = REMOVE_WATCH_KEYS)
    public int deleteApp(Long appId) {
        return apps.remove(findOne(appId)) ? 1 : 0;
    }

    @Override
    @Cachezable(key = "#appIds,ALL,MAPALL", prefix = SecurityKeys.KEY_FIND_APP, type = Cachezable.CachezType.REMOVE, watch = REMOVE_WATCH_KEYS)
    public int deleteApps(String appIds) {
        Iterable<String> ids = CollectionUtil.split(appIds);
        HashSet<String> _ids = Sets.newHashSet(ids);
        int i = 0;
        for (String appId : _ids) {
            i += deleteApp(Long.valueOf(appId));
        }
        return i;
    }

    @Override
    @Cachezable(key = "#appIds,ALL,MAPALL", prefix = SecurityKeys.KEY_FIND_APP, type = Cachezable.CachezType.REMOVE, watch = REMOVE_WATCH_KEYS)
    public int disableApps(String appIds) {
        Iterable<String> ids = CollectionUtil.split(appIds);
        HashSet<String> _ids = Sets.newHashSet(ids);
        int i = 0;
        for (String appId : _ids) {
            if(null != updateApp(new App((Long.valueOf(appId)), ResourceStatus.UNAVAILABLE.STATUS, DateUtils.now()))){
                i++;
            }
        }
        return i;
    }

    @Override
    @Cachezable(key = "#appId", prefix = SecurityKeys.KEY_FIND_APP, type = Cachezable.CachezType.SET)
    public App findOne(Long appId) {
        for(App app : apps){
            if(appId.longValue() == app.getId().longValue()){
                return app;
            }
        }
        return null;
    }

    @Override
    @Cachezable(key = "ALL", prefix = SecurityKeys.KEY_FIND_APP, type = Cachezable.CachezType.SET)
    public List<App> findAll() {
        return apps;
    }

    @Override
    @Cachezable(key = "#appKey", prefix = SecurityKeys.KEY_FIND_APP, type = Cachezable.CachezType.SET, watch = REMOVE_WATCH_KEYS)
    public App findAppByAppKey(String appKey) {
        for(App app : apps){
            if(appKey.equals(app.getAppKey())){
                return app;
            }
        }
        return null;
    }

    @Override
    @Cachezable(key = "#appSecret", prefix = SecurityKeys.KEY_FIND_APP, type = Cachezable.CachezType.SET, watch = REMOVE_WATCH_KEYS)
    public App findAppByAppSecret(String appSecret){
        for(App app : apps){
            if(appSecret.equals(app.getAppSecret())){
                return app;
            }
        }
        return null;
    }

    @Override
    @Cachezable(key = "MAPALL", prefix = SecurityKeys.KEY_FIND_APP, type = Cachezable.CachezType.SET)
    public Map<Long, App> findApps() {
        Map<Long, App> map = new HashMap<>();
        List<App> list = findAll();
        for(App app : list){
            map.put(app.getId(), app);
        }
        return map;
    }
    static enum ResourceStatus {
        /** 1 有效 **/
        AVAILABLE(1),
        /** 0 无效 **/
        UNAVAILABLE(0);

        public int STATUS;
        ResourceStatus(int i) {
            this.STATUS = i;
        }

    }
}
