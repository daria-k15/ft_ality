# Scala Build Tool
SBT = sbt

# Grammar file
GRAMMAR ?=
DEBUG ?=

.PHONY: all compile run clean re version

all: run

clean:
	@echo clean
	@$(SBT) clean

compile:
	@echo compile
	@$(SBT) compile

run:
	@echo run
	@$(SBT) "run $(GRAMMAR) $(DEBUG)"

re:	clean compile

version:
	@$(SBT) version
