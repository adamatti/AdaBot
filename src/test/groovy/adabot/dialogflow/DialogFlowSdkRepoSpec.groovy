package adabot.dialogflow

import adabot.BaseSpec
import spock.lang.Shared

class DialogFlowSdkRepoSpec extends BaseSpec {
    @Shared
    private DialogFlowSdkRepo dialogFlowRepo = context.getBean(DialogFlowSdkRepo.class)

    def "query"(){
        given:
            def sessionId = UUID.randomUUID().toString()
        when:
            2.times {
                def response = dialogFlowRepo.query("olá",sessionId)
                println response
                log.info (response.intent.displayName)
            }
        then:
            noExceptionThrown()
    }
}
