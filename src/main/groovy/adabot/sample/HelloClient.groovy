package adabot.sample

import groovy.transform.CompileStatic
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client
import io.reactivex.Single

@CompileStatic
@Client("/sample")
interface HelloClient {
    @Get("/hello")
    Single<String> getHello()
}
