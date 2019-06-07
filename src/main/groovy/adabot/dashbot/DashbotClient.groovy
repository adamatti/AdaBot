package adabot.dashbot

import adabot.dashbot.model.DashbotRequest
import adabot.dashbot.model.DashbotResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client
import io.reactivex.Single

@Client("https://tracker.dashbot.io")
interface DashbotClient {
    @Post("/track?platform=generic&v=9.9.1-rest")
    Single<DashbotResponse> track(
        @QueryValue("type") String type, // incoming or outgoing
        @QueryValue("apiKey") String apiKey,
        @Body DashbotRequest request
    )
}


