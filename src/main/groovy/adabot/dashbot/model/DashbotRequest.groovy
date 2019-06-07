package adabot.dashbot.model

import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includeNames = true, includePackage = false)
class DashbotRequest {
    String text
    String userId
    Object platformUserJson
    Intent intent = new Intent(name: "Unknown")
}
