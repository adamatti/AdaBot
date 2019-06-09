package adabot.bot.repo

import adabot.bot.model.BotSession
import adabot.core.MongoHelper
import adabot.core.repo.BaseMongoRepo
import groovy.transform.CompileStatic

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@CompileStatic
class SessionRepo extends BaseMongoRepo<BotSession> {

    @Inject
    SessionRepo(MongoHelper mongoHelper){
        this.collection = mongoHelper.getCollection("session", BotSession.class)
    }
}
