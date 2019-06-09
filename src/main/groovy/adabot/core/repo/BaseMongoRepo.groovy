package adabot.core.repo

import adabot.bot.model.BotSession
import com.mongodb.BasicDBObject
import com.mongodb.client.model.UpdateOptions
import com.mongodb.reactivestreams.client.MongoCollection
import groovy.transform.CompileStatic
import org.bson.conversions.Bson

import static adabot.core.extensions.AsyncExtension.blockingIterable

@CompileStatic
abstract class BaseMongoRepo<E> {
    @Delegate
    protected final MongoCollection<E> collection

    Iterable<E> find(Map criteria){
        blockingIterable(
            collection.find(toBson(criteria))
        )
    }

    E findOne(Map criteria){
        blockingIterable(
            collection.find(toBson(criteria)).limit(1)
        )?.find{true} as E
    }

    void updateOne(Map filter, Map update){
        blockingIterable(
            collection.updateOne(
                toBson(filter),
                toBson([
                    '$set': update
                ]),
                new UpdateOptions(upsert:true)
            )
        ).find {true}
    }

    protected Bson toBson(Map criteria){
        new BasicDBObject(criteria)
    }
}
