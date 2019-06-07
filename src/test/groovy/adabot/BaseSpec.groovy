package adabot

import io.micronaut.context.ApplicationContext
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
abstract class BaseSpec extends Specification {
    @Inject
    protected ApplicationContext context

    @Inject
    @Client('/')
    protected RxHttpClient client
}
