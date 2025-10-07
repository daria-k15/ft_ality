import scala.annotation.tailrec
import scala.io.Source
import scala.util.Using

object Main {
  private def readFile(path: String): Either[AppError, List[String]] =
    Using(Source.fromFile(path)) { source =>
      source.getLines().toList
    }.toEither.left.map(ex => AppError.IOError(ex))

  private def printHelp(): Unit = {
    println("Usage: run <grammar-file-path> [--debug]")
    println("Type combo as keys separated by spaces (e.g.: s d x) or enter 'exit' to quit.")
  }

  private def translateInput(group: String, keyMap: Map[String, String]): String = {
    group.split('+').map(k => keyMap.getOrElse(k, k)).mkString("+")
  }

  def main(args: Array[String]): Unit = {
    val result: Either[AppError, Unit] = args.toList match {
      case Nil =>
        Left(AppError.InvalidArguments)

      case ("-h" | "--help") :: _ =>
        printHelp()
        Left(AppError.Help)

      case file :: tail =>
        val debugMode = tail.contains("--debug")

        for {
          lines <- readFile(file)
          grammar <- Parser.parseFile(lines)
          validatedGrammar <- GrammarValidator.validate(grammar)
        } yield {
          val keyMap: Map[String, String] = validatedGrammar.keyMapping.map(km => km.key -> km.action).toMap
          val fsm = FSM(validatedGrammar.combos, keyMap)
          Printer.printGrammar(grammar)
          println(s"\n[Debug mode: ${if (debugMode) "ON" else "OFF"}]")

          @tailrec
          def loop(): Unit = {
            println("\nEnter keys for a combo (or 'exit'):")
            val line = scala.io.StdIn.readLine()
            if (line == null || line.trim.toLowerCase == "exit") ()
            else {
              val groups = line.trim.split("\\s+").toList.map(g => translateInput(g, keyMap))
              if (debugMode) {
                val trace = fsm.debugTrace(groups)
                println(trace.mkString("\n"))
              }
              val recognized = fsm.recognize(groups)
              if (recognized.isEmpty) println("No move recognized.")
              else recognized.foreach(mv => println(s"Move recognized: $mv"))
              loop()
            }
          }

          loop()
        }
    }

    result match {
      case Left(AppError.Help) => // Help already printed
      case Left(AppError.InvalidArguments) =>
        println("Error: Invalid arguments")
        printHelp()
      case Left(AppError.IOError(ex)) =>
        println(s"Error reading file: ${ex.getMessage}")
      case Left(AppError.ParsingError(msg)) =>
        println(s"Error parsing grammar: $msg")
      case Left(AppError.ValidationError(msg)) =>
        println(s"Validation error: $msg")
      case Right(_) => // Success
    }
  }
}

