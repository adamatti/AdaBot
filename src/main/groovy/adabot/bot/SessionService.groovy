package adabot.bot

import adabot.bot.model.BotSession
import adabot.bot.repo.SessionRepo
import adabot.core.extensions.AsyncExtension
import adabot.telegram.model.Update
import com.mongodb.reactivestreams.client.Success
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

import javax.inject.Inject
import javax.inject.Singleton

@Slf4j
@Singleton
@CompileStatic
class SessionService {
    @Inject
    private SessionRepo sessionRepo

    private BotSession findSession(Update update){
        sessionRepo.findOne(["variables.telegramChatId": update.message.chat.id])
    }

    BotSession findOrCreateSession(Update update){
        def entity = findSession(update)

        if (entity) {
            log.info("Found existing session")
            // FIXME update variables?
            return entity
        }

        createSession(update)
    }

    private BotSession createSession(Update update){
        def entity = new BotSession(
            sessionId: UUID.randomUUID().toString(),
            createDate: new Date(),
            variables:extractVariables(update)
        )

        AsyncExtension.blockingIterable(sessionRepo.insertOne(entity)).each { Success it ->
            log.info("InsertOne result: ${it.toString()}")
        }

        log.info("Session created [id: ${entity.sessionId}]")
        entity
    }

    private Map extractVariables(Update update){
        [
            telegramChatId: update.message.chat.id
            //FIXME save other telegram variables (e.g. first/last name, username)
        ]
    }

}
