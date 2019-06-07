package adabot.telegram.model

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includeNames = true, includePackage = false)
class OutgoingTextMessage {
    @JsonProperty("chat_id")
    Integer chatId

    String text
}
