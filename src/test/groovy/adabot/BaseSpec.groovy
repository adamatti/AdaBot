package adabot

import adabot.core.ConfigHelper
import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.server.EmbeddedServer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

abstract class BaseSpec extends Specification {
    @Shared
    protected Logger log = LoggerFactory.getLogger(this.class)

    @Shared
    @AutoCleanup
    protected EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer.class)

    @Shared
    protected ApplicationContext context = embeddedServer.getApplicationContext()

    @Shared
    protected ConfigHelper configHelper = context.getBean(ConfigHelper.class)

}
