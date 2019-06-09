package adabot.dashbot.model

import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includeNames = true, includePackage = false)
class DashbotRequest {
    String text
    String userId

    // end your platform-specific message JSON here. It will be available for viewing in your transcripts
    Object platformJson
    // send any user-specific information (ie. zipcode, A/B test group, etc)
    Object platformUserJson

    Intent intent = new Intent(name: "Unknown")
}
