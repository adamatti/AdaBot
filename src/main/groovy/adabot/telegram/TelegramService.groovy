package adabot.telegram

import adabot.core.CacheHelper
import adabot.core.ConfigHelper
import adabot.core.EnvVars
import adabot.core.EventEmitter
import adabot.core.Events
import adabot.telegram.model.OutgoingTextMessage
import adabot.telegram.model.Result
import adabot.telegram.model.Update
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.discovery.event.ServiceStartedEvent
import io.micronaut.http.client.exceptions.ReadTimeoutException
import io.micronaut.scheduling.annotation.Async
import io.micronaut.scheduling.annotation.Scheduled
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Slf4j
@Singleton
@CompileStatic
class TelegramService implements ApplicationEventListener<ServiceStartedEvent> {
    private static final String TELEGRAM_OFFSET = "TELEGRAM_OFFSET"

    @Inject
    private ConfigHelper configHelper

    @Inject
    private TelegramClient telegramClient

    @Inject
    private CacheHelper cacheHelper

    @Inject
    private EventEmitter eventEmitter

    @Scheduled(fixedDelay = "1s", initialDelay = "5s")
    void fetch(){
        if (configHelper.isTest()) return

        def telegramResult = getUpdates()

        if (telegramResult.ok && telegramResult.result){
            log.info("Updates received: ${telegramResult.result.size()}")
            emitUpdates(telegramResult.result)
            updateOffset(telegramResult.result)
        } else if (!telegramResult.ok){
            log.info("No updates received [ok: ${telegramResult.ok}]")
        }
    }

    @Async
    @Override
    void onApplicationEvent(ServiceStartedEvent event) {
        if (configHelper.isTest()) return

        def token = configHelper.getString(EnvVars.TELEGRAM_TOKEN)

        eventEmitter.on(Events.TELEGRAM_MSG_SEND){ OutgoingTextMessage msg ->
            def result = telegramClient.sendMessage(token,msg).blockingGet()
            log.info("Msg sent [result: ${result.ok}, msg: ${msg}]")
        }
    }

    private Result<List<Update>> getUpdates(){
        def ERROR_ITEM = new Result<List<Update>>(ok:false,result: [])
        Single<Result<List<Update>>> promise = null

        try {
            def token = configHelper.getString(EnvVars.TELEGRAM_TOKEN)
            Long offset = (cacheHelper.syncGet(TELEGRAM_OFFSET) ?: "0").toLong()
            promise = telegramClient.getUpdates(token, offset, 100, 0)
            return (promise
                .onErrorReturnItem(ERROR_ITEM)
                .blockingGet())
        } catch (ReadTimeoutException e) {
            log.warn("Timeout: ${e.message}")
            return ERROR_ITEM
        }
    }

    private void updateOffset(List<Update> updates){
        Long offset = updates.max { it.updateId}?.updateId +1
        cacheHelper.async().set(TELEGRAM_OFFSET, offset.toString())
    }

    private void emitUpdates(List<Update> updates){
        updates.each { Update update ->
            eventEmitter.emit(Events.TELEGRAM_MSG_RECEIVED, update)
        }
    }
}
