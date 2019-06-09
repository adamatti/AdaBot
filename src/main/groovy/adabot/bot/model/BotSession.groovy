package adabot.bot.model

import groovy.transform.ToString
import org.bson.Document
import org.bson.codecs.pojo.annotations.BsonId

@ToString(includePackage = false,includeNames = true)
class BotSession {
    @BsonId
    String sessionId
    Map variables
    Date createDate
}
