package adabot.bot.repo

import adabot.bot.model.BotSession
import adabot.core.MongoHelper
import com.mongodb.BasicDBObject
import com.mongodb.reactivestreams.client.MongoCollection
import org.bson.conversions.Bson

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionRepo {
    @Delegate
    private final MongoCollection collection

    @Inject
    SessionRepo(MongoHelper mongoHelper){
        this.collection = mongoHelper.getCollection("session", BotSession.class)
    }

    Iterable<BotSession> find(Map criteria){
        collection.find(toBson(criteria)).blockingIterable()
    }

    BotSession findOne(Map criteria){
        collection.find(toBson(criteria)).limit(1).blockingIterable()?.find{true}
    }

    private Bson toBson(Map criteria){
        new BasicDBObject(criteria)
    }
}
