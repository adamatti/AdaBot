package adabot.telegram

import adabot.telegram.model.Message
import adabot.telegram.model.OutgoingTextMessage
import adabot.telegram.model.Result
import adabot.telegram.model.Update
import groovy.transform.CompileStatic
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client
import io.micronaut.retry.annotation.Retryable
import io.reactivex.Single

import javax.annotation.Nullable

@CompileStatic
//@Retryable(attempts = "2", delay = "1s")
@Client("https://api.telegram.org")
interface TelegramClient {
    @Get("/bot{authorizationToken}/getUpdates")
    Single<Result<List<Update>>> getUpdates(String authorizationToken)

    @Get("/bot{authorizationToken}/getUpdates")
    Single<Result<List<Update>>> getUpdates(
        String authorizationToken,
        @QueryValue("offset") Long offset,
        @QueryValue("limit") Integer limit,
        @QueryValue("timeout") @Nullable Integer timeoutSeconds
    )

    @Post("/bot{authorizationToken}/sendMessage")
    Single<Result<Message>> sendMessage(String authorizationToken, @Body OutgoingTextMessage message)
}
