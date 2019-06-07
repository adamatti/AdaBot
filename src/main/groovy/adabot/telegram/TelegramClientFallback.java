package adabot.telegram;

import adabot.telegram.model.Message;
import adabot.telegram.model.OutgoingTextMessage;
import adabot.telegram.model.Result;
import adabot.telegram.model.Update;
import groovy.transform.CompileStatic;
import io.micronaut.retry.annotation.Fallback;
import io.reactivex.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.List;

import static java.util.Collections.emptyList;

/**
 * For some weird reason this file doesn't compile as .groovy
 */
@Fallback
@Singleton
@CompileStatic
public class TelegramClientFallback implements TelegramClient {
    private static final Logger log = LoggerFactory.getLogger(TelegramClientFallback.class);

    @Override
    public Single<Result<List<Update>>> getUpdates(String authorizationToken) {
        log.warn("Fallback");
        Result<List<Update>> result = new Result<>(false, emptyList());
        return Single.just(result);
    }

    @Override
    public Single<Result<List<Update>>>  getUpdates(
        String authorizationToken,
        Long offset,
        Integer limit,
        Integer timeoutSeconds
    ) {
        log.warn("Fallback");
        Result<List<Update>> result = new Result<>(false, emptyList());
        return Single.just(result);
    }

    @Override
    public Single<Result<Message>> sendMessage(String authorizationToken, OutgoingTextMessage message){
        log.warn("Fallback");
        Result<Message> result = new Result<>(false,null);
        return Single.just(result);
   }
}
