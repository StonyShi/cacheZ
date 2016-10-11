package com.stone.cache.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <p>Created with IntelliJ IDEA. </p>
 * <p>User: Stony </p>
 * <p>Date: 2016/10/11 </p>
 * <p>Time: 11:45 </p>
 * <p>Version: 1.0.0 </p>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:jedis-cache-test.xml")
public class AppServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(AppServiceTest.class);

    @javax.annotation.Resource
    AppService appService;

    long appId = 1000L;

    @Test
    public void testAppServiceCache(){
        logger.info("appService.findAll = {}", appService.findAll());
        logger.info("appService.findAll = {}", appService.findAll());
        logger.info("appService.findAppByAppKey = {}", appService.findAppByAppKey("645ba612-370a-43a8-a8e0-993e7a590cf0"));
        logger.info("appService.findAppByAppKey = {}", appService.findAppByAppKey("645ba612-370a-43a8-a8e0-993e7a590cf0"));
        for (int i = 0; i < 10; i++) {
            App app = new App();
            app.setId(appId++);
            app.setName("test_"+ appId);
            app.setAppKey("645ba616-370a-43a8-a8e0-993e7a"+appId);
            app.setAppSecret("a5351234-3512a-43a8-a8e0-9231"+appId);
            app.setAvailable(1);
            App createApp = appService.createApp(app);
            logger.info("appService.createApp = {}", createApp);
        }
        App app = new App();
        app.setId(appId++);
        app.setName("test_"+ appId);
        app.setAppKey("645ba612-370a-43a8-a8e0-993e7a590cf0");
        app.setAppSecret("645ba612-370a-43a8-a8e0-993e7a590cf0");
        app.setAvailable(1);
        App createApp = appService.createApp(app);
        logger.info("appService.createApp = {}", createApp);
        logger.info("appService.findAll = {}", appService.findAll());
        logger.info("appService.findAll = {}", appService.findAll());
        logger.info("appService.findAppByAppKey = {}", appService.findAppByAppKey("645ba612-370a-43a8-a8e0-993e7a590cf0"));
        logger.info("appService.findAppByAppKey = {}", appService.findAppByAppKey("645ba612-370a-43a8-a8e0-993e7a590cf0"));
        logger.info("appService.findAppByAppKey = {}", appService.findAppByAppKey(app.getAppKey()));
        logger.info("appService.findAppByAppKey = {}", appService.findAppByAppKey(app.getAppKey()));
        appService.deleteApp(app.getId());
    }
}
