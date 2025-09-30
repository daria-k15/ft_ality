# Scala Build Tool
SBT = sbt

# Grammar file
GRAMMAR = grammars/mk9.grm

all: run

clean:
	@echo clean
	@$(SBT) clean

compile:
	@echo compile
	@$(SBT) compile

run:
	@echo run
	@$(SBT) "run $(GRAMMAR)"

re:	clean compile

version:
	@$(SBT) version