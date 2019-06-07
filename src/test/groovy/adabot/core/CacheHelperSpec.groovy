package adabot.core

import adabot.BaseSpec
import spock.lang.Shared

class CacheHelperSpec extends BaseSpec {
    @Shared
    private CacheHelper cacheHelper = context.getBean(CacheHelper.class)

    def "basic operations"(){
        given:
            def key = "junit-count"
        when:
            def c = cacheHelper.syncGet(key)
            println "Current count: ${c}"
            def result = cacheHelper.syncSet(key,  c ? (c.toInteger() + 1).toString() : "1")
            println "Result: ${result}"
        then:
            result != null
    }

    def "do cache"(){
        when:
            def result = cacheHelper.doCache("junit-call"){
                println "Real call"
                Thread.sleep(3000)
                "expense call"
            }
        then:
            result == "expense call"
    }
}
