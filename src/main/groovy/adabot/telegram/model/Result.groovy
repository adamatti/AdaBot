package adabot.telegram.model

import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includeNames = true, includePackage = false)
class Result<T> {
    boolean ok
    T result
}
