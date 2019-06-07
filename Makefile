.DEFAULT_GOAL := help

clean-ide:
	@rm -rf .settings
	@rm -rf bin
	@rm -rf .classpath
	@rm -rf .project
	@rm -rf build
	@rm -rf out

docker-up:
	docker-compose up -d

docker-stop:
	docker-compose stop -t 0

compile: clean-ide
	./gradlew classes --stacktrace

.PHONY: help
help: ## show this help
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'