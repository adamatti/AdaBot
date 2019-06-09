package adabot.bot

import static adabot.core.MapHelper.merge
import static adabot.core.MapHelper.removeNulls
import static adabot.core.extensions.AsyncExtension.blockingIterable
import adabot.bot.model.BotSession
import adabot.bot.repo.SessionRepo
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
            return updateVariables(entity,update)
        }

        createSession(update)
    }

    private BotSession updateVariables(BotSession entity, Update update){
        entity.variables = merge(entity.variables, extractVariables(update))
        sessionRepo.updateOne(
            [_id: entity.sessionId],
            [variables: entity.variables]
        )
        entity
    }

    private BotSession createSession(Update update){
        def entity = new BotSession(
            sessionId: UUID.randomUUID().toString(),
            createDate: new Date(),
            variables: merge(
                initialVariables(update) ,
                extractVariables(update)
            )
        )

        blockingIterable(sessionRepo.insertOne(entity)).each { Success it ->
            log.info("InsertOne result: ${it.toString()}")
        }

        log.info("Session created [id: ${entity.sessionId}]")
        entity
    }


    private Map initialVariables(Update update){
        [
            pause: false
        ]
    }

    private Map extractVariables(Update update){
        def user = update?.message?.from

        removeNulls([
            telegramChatId: update?.message?.chat?.id,
            telegramUserName: user?.username,
            firstName: user?.firstName,
            lastName: user?.lastName
        ])
    }

}
