package adabot.core

import groovy.transform.CompileStatic
import io.lettuce.core.api.StatefulRedisConnection

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@CompileStatic
class CacheHelper {
    @Inject
    @Delegate
    private StatefulRedisConnection<String, String> connection

    String syncSet(String key, String value){
        connection.sync().set(key,value)
    }

    String syncGet(String key){
        connection.sync().get(key)
    }

    String doCache(String key, Closure func){
        String result = syncGet(key)
        if (!result) {
            result = func.call()
            syncSet(key, result)
        }
        result
    }
}
