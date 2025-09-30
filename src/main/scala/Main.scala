

object Main {

  def main(args: Array[String]): Unit = {
    if (args.length < 1) {
      println("Usage: run <grammar-file-path>")
      System.exit(1)
    }

    val grammarFile = args(0)
    if (!grammarFile.endsWith(".grm")) {
      println("Wrong grammar file format")
      System.exit(1)
    }

    Parser.parseFile(grammarFile) match {
      case Right(grammar) => Printer.printGrammar(grammar)
      case Left(error) => println("error")
    }


    //    val linesTry = Try(Source.fromFile(grammarFile).getLines().toList)
    //    linesTry match {
    //      case Success(lines) =>
    //        val keyMappings = Grammar.parseKeyMappings(lines)
    //        val moves = Grammar.parseMoves(lines)
    //        val fsm = FSM(moves)
    //
    //        Moves.prettyPrintKeyMappings(keyMappings)
    //
    //        println("Введите последовательность клавиш через пробел (например, d x):")
    //        val input = scala.io.StdIn.readLine().split(" ").toList
    //        val tokens = input.map(k => keyMappings.getOrElse(k, Token("unknown")))
    //        val recognized = fsm.recognize(tokens)
    //
    //        if (recognized.isEmpty) println("Комбо не распознано.")
    //        else recognized.foreach(move => println(s"Распознано комбо: $move"))
    //
    //      case Failure(ex) =>
    //        println(s"Ошибка при чтении файла грамматики '$grammarFile': ${ex.getMessage}")
    //        System.exit(2)
    //    }
  }

  //  private def parseFile(args: Array[String]): Grammar =
}

