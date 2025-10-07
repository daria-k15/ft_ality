SBT = sbt
GRAMMAR ?= 
DEBUG ?= 

.PHONY: all compile run clean re version build

all: run

build:
	docker build -t ft_ality .

run:
	docker-compose run --rm app sbt "run $(GRAMMAR) $(DEBUG)"

compile:
	@echo compile
	@$(SBT) compile

re:	clean compile

clean:
	rm -rf target project/target

version:
	@$(SBT) version
