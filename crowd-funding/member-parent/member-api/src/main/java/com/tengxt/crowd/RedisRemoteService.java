package com.tengxt.crowd;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tengxt.util.ResultEntity;

import java.util.concurrent.TimeUnit;

@FeignClient("member-crowd-redis")
public interface RedisRemoteService {
    @RequestMapping("/set/redis/key/value/remote")
    ResultEntity<String> setRedisKeyValueRemote(
            @RequestParam("key") String key,
            @RequestParam("value") String value
    );

    @RequestMapping("/set/redis/key/value/with/timeout/remote")
    ResultEntity<String> setRedisKeyValueWithTimeoutRemote(
            @RequestParam("key") String key,
            @RequestParam("value") String value,
            @RequestParam("time") long time,
            @RequestParam("timeUnit") TimeUnit timeUnit
    );

    @RequestMapping("/get/redis/value/by/key/remote")
    ResultEntity<String> getRedisValueByKeyRemote(@RequestParam("key") String key);

    @RequestMapping("/remove/redis/key/by/key/remote")
    ResultEntity<String> RemoveRedisKeyByKeyRemote(@RequestParam("key") String key);
}
