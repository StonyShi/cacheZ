package com.stone.cache.test;

import java.util.List;
import java.util.Map;

public interface AppService {


    public App createApp(App app);
    public App updateApp(App app);
    int deleteApp(Long appId);

    /**
     * 批量删除应用
     * @param appIds
     */
    int deleteApps(String appIds);

    /**
     * 批量禁用应用
     * @param appIds
     */
    int disableApps(String appIds);

    public App findOne(Long appId);
    public List<App> findAll();

    /**
     * 根据appKey查找AppId
     * @param appKey
     * @return
     */
    public App findAppByAppKey(String appKey);

    public App findAppByAppSecret(String appSecret);

    /**
     *
     * @return
     */
    Map<Long, App> findApps();
}