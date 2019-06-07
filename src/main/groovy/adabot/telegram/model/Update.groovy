package adabot.telegram.model

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includeNames = true, includePackage = false)
class Update {
    @JsonProperty("update_id")
    Integer updateId

    Message message
}
