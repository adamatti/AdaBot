package adabot.telegram

import adabot.telegram.model.Message
import adabot.telegram.model.OutgoingTextMessage
import adabot.telegram.model.Result
import adabot.telegram.model.Update
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client

@Client("https://api.telegram.org")
interface TelegramClient {
    @Get("/bot{authorizationToken}/getUpdates")
    Result<List<Update>> getUpdates(String authorizationToken)

    @Get("/bot{authorizationToken}/getUpdates")
    Result<List<Update>> getUpdates(
        String authorizationToken,
        @QueryValue("offset") Long offset,
        @QueryValue("limit") Integer limit,
        @QueryValue("timeout") Integer timeoutSeconds
    )

    @Post("/bot{authorizationToken}/sendMessage")
    Result<Message> sendMessage(String authorizationToken, @Body OutgoingTextMessage message)
}
