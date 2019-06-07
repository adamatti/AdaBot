package adabot.telegram.model

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includeNames = true, includePackage = false)
class Message {
    @JsonProperty("message_id")
    Integer messageId

    User from
    Chat chat
    Integer date
    String text

    Date getDatetime(){
        new Date(date * 1000)
    }
}
