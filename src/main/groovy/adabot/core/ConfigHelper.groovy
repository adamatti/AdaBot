package adabot.core

import groovy.transform.CompileStatic
import io.github.cdimascio.dotenv.Dotenv

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

    String getString(EnvVars varName){
        dotenv.get(varName.name())
    }
}

enum EnvVars {
    TELEGRAM_TOKEN,
    DASHBOT_TOKEN
}
