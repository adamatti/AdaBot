package adabot.dialogflow

import adabot.core.ConfigHelper
import adabot.core.EnvVars
import com.google.api.gax.core.CredentialsProvider
import com.google.api.gax.core.FixedCredentialsProvider
import com.google.auth.Credentials
import com.google.auth.oauth2.ServiceAccountCredentials
import com.google.cloud.dialogflow.v2beta1.DetectIntentResponse
import com.google.cloud.dialogflow.v2beta1.QueryInput
import com.google.cloud.dialogflow.v2beta1.QueryResult
import com.google.cloud.dialogflow.v2beta1.SessionName
import com.google.cloud.dialogflow.v2beta1.SessionsClient
import com.google.cloud.dialogflow.v2beta1.SessionsSettings
import com.google.cloud.dialogflow.v2beta1.TextInput
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.discovery.event.ServiceStartedEvent
import io.micronaut.scheduling.annotation.Async

import javax.inject.Inject
import javax.inject.Singleton

@Slf4j
@Singleton
@CompileStatic
class DialogFlowSdkRepo implements ApplicationEventListener<ServiceStartedEvent> {
    @Inject
    private ConfigHelper configHelper

    private SessionsClient sessionsClient

    @Async
    @Override
    void onApplicationEvent(ServiceStartedEvent event) {
        sessionsClient = buildSessionClient()
        log.info("Session created")
    }

    QueryResult query(String text, String sessionId){
        if (log.isTraceEnabled()){
            log.trace "Calling [text: ${text}, session: ${sessionId}]"
        }

        def project = configHelper.getString(EnvVars.DIALOGFLOW_PROJECT)

        QueryInput queryInput = buildQueryInput(text)
        SessionName session = SessionName.of(project, sessionId)

        DetectIntentResponse response = sessionsClient.detectIntent(session,queryInput)
        if (log.isTraceEnabled()){
            log.trace "Response: ${response}"
        }
        def queryResult = response.queryResult
        log.info("Got intent [intent: ${queryResult?.intent?.displayName}]")
        queryResult
    }

    private QueryInput buildQueryInput(String text){
        TextInput textInput = TextInput.newBuilder().setText(text).setLanguageCode("pt-BR").build()
         QueryInput.newBuilder().setText(textInput).build()
    }

    private SessionsClient buildSessionClient(){
        File file = new File(configHelper.getString(EnvVars.DIALOGFLOW_SERVICE_TOKEN))
        FileInputStream fis = new FileInputStream(file)
        Credentials credentials = ServiceAccountCredentials.fromStream(fis)
        CredentialsProvider credentialsProvider = FixedCredentialsProvider.create(credentials)
        SessionsSettings sessionsSettings = SessionsSettings.newBuilder().setCredentialsProvider(credentialsProvider).build()
        SessionsClient.create(sessionsSettings)
    }
}
