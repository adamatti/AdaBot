Pet project to create a bot

#### Stack

* Micronaut
    * Created with `mn create-app adabot-2019 --features=mongo-reactive,groovy`

#### known issues / technical debts

* still high couple with telegram. Must change it when implement a second channel

#### Todo

* deploy to heroku
* generic bot messages(classes) - stop using telegram ones
* Core: Create a BPM engine
* Business cases
    * HR interview
    * Jokes (e.q. máquina)
    
#### Todo Low

* track time (from user to answer)
* influx? newrelic?
* enable / disable dialog flow
* admin ui
    * handover
    * pause function: enable UI
* webchat
* Infra
    * Telegram: use webhook
    * Schedule jobs

#### References

* [Telegram API](https://core.telegram.org/bots/api) / [Apache camel mapping](https://github.com/apache/camel/blob/master/components/camel-telegram/src/main/java/org/apache/camel/component/telegram/service/RestBotAPI.java)
* [Redis](https://micronaut-projects.github.io/micronaut-redis/latest/guide/)
* [Dashbot](https://www.dashbot.io/) - reports and metrics for bots
* [DialogFlow](https://dialogflow.com/)