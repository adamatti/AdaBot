package adabot.core

import groovy.transform.CompileStatic
import io.github.cdimascio.dotenv.Dotenv
import io.micronaut.context.env.Environment

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@CompileStatic
class ConfigHelper {
    private static String USER_HOME = System.getProperty("user.home")

    private static Dotenv dotenv = Dotenv.configure()
        .directory(USER_HOME)
        .ignoreIfMalformed()
        .ignoreIfMissing()
        .load()

    @Inject
    private Environment environment

    String getString(EnvVars varName){
        dotenv.get(varName.name())
    }

    boolean isTest(){
        environment.getActiveNames().contains(Environment.TEST)
    }
}

enum EnvVars {
    TELEGRAM_TOKEN,
    DASHBOT_TOKEN,
    DIALOGFLOW_SERVICE_TOKEN,
    DIALOGFLOW_PROJECT
}
