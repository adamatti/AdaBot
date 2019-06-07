package adabot.dashbot

import adabot.BaseSpec
import adabot.core.EnvVars
import adabot.dashbot.model.DashbotRequest
import adabot.dashbot.model.Intent
import spock.lang.Shared

class DashbotClientSpec extends BaseSpec {
    @Shared
    private DashbotClient dashbotClient = context.getBean(DashbotClient.class)

    def "test track"(){
        given:
            def token = configHelper.getString(EnvVars.DASHBOT_TOKEN)
            def request = new DashbotRequest(
                text: "junit",
                userId: "junit",
                intent: new Intent(name: "unknown")
            )
        when:
            def response = dashbotClient.track("incoming",token,request).blockingGet()
            println response
        then:
            response.success
    }
}
