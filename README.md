Pet project to create a bot

#### Stack

* Micronaut
    * Created with `mn create-app adabot-2019 --features=mongo-reactive,groovy`

#### known issues / technical debts

* still high couple with telegram. Must change it when implement a second channel

#### Todo

* deploy to heroku
* enable / disable dialog flow
* handover
* webchat
* generic bot messages (stop using telegram ones)
* pause function
* Infra
    * Telegram: use webhook
    * Mongo
    * Schedule jobs
* Core: Create a BPM engine
* Business cases
    * HR interview
    * Jokes (e.q. máquina)
         
#### References

* [Telegram API](https://core.telegram.org/bots/api) / [Apache camel mapping](https://github.com/apache/camel/blob/master/components/camel-telegram/src/main/java/org/apache/camel/component/telegram/service/RestBotAPI.java)
* [Redis](https://micronaut-projects.github.io/micronaut-redis/latest/guide/)
* [Dashbot](https://www.dashbot.io/) - reports and metrics for bots
* [DialogFlow](https://dialogflow.com/)