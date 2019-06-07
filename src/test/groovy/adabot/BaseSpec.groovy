package adabot

import adabot.core.ConfigHelper
import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.server.EmbeddedServer
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

abstract class BaseSpec extends Specification {
    @Shared
    @AutoCleanup
    protected EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer.class)

    @Shared
    protected ApplicationContext context = embeddedServer.getApplicationContext()

    @Shared
    protected ConfigHelper configHelper = context.getBean(ConfigHelper.class)

}
