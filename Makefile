# Scala Build Tool
SBT = sbt

# Grammar file
GRAMMAR ?= grammars/valid_file.grm
DEBUG ?= --debug

.PHONY: all compile run clean re version

all: run

clean:
	@echo clean
	@$(SBT) clean

compile:
	@echo compile
	@$(SBT) compile

run:
	docker-compose run --rm app $(GRAMMAR) $(DEBUG)
	#@echo run
	#@$(SBT) "run $(GRAMMAR) $(DEBUG)"

re:	clean compile

version:
	@$(SBT) version
