package adabot.telegram.model

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includeNames = true, includePackage = false)
class Chat {
    Integer id

    @JsonProperty("first_name")
    String first_name

    @JsonProperty("last_name")
    String last_name

    String username
    String type
}
