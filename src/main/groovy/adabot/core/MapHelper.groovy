package adabot.core

import groovy.transform.CompileStatic

@CompileStatic
abstract class MapHelper {
    static Map merge(Map map1, Map map2){
        Map map3 = new HashMap(map1)
        map3.putAll(map2)
        map3
    }

    static Map removeNulls(Map map){
        map.findAll {k, v -> v}
    }
}
