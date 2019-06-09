package adabot.core

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoDatabase
import groovy.transform.CompileStatic

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@CompileStatic
class MongoHelper {
    private final MongoClient mongoClient

    @Delegate
    private final MongoDatabase database

    @Inject
    MongoHelper(MongoClient mongoClient){
        this.mongoClient = mongoClient
        this.database = mongoClient.getDatabase("adabot")
    }

}
