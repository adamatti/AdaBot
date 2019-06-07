package adabot.telegram.model

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includeNames = true, includePackage = false)
class User {
    Integer id

    @JsonProperty("is_bot")
    boolean isBot

    @JsonProperty("first_name")
    String firstName

    @JsonProperty("last_name")
    String lastName

    String username

    @JsonProperty("language_code")
    String languageCode
}
