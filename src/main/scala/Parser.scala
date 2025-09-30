import scala.io.Source

object Parser {

  def parseFile(lines: List[String]): Either[AppError, Grammar] = {
    try {
      val parsedLines = lines
        .map(_.trim)
        .filter(line => line.nonEmpty && !line.startsWith("#"))
        .flatMap(parseLine)

      val (keys, combos) = parsedLines.foldLeft(List[KeyMapping](), List[Combo]()) {
        case ((keys, combos), line) => line match {
          case k: KeyMapping => (k :: keys, combos)
          case c: Combo => (keys, c :: combos)
        }
      }

      Right(Grammar(keys, combos))
    } catch {
      case e: Exception => Left(AppError.ParsingError("Got error while parsing file: ${e.getMessage}"))
    }
  }

  private def parseLine(line: String): Option[GrammarSealed] = {
    line match {
      case l if l.startsWith("'") && l.contains("->") => parseKeyMapping(l)
      case l if l.contains("->") => parseCombo(l)
      case _ => None
    }
  }

  private def parseKeyMapping(line: String): Option[KeyMapping] = {
    val pattern = "'(.+)'\\s*->\\s*(.+)".r

    line match {
      case pattern(key, action) => Some(KeyMapping(key, action))
      case _ => None
    }
  }

  private def parseCombo(line: String): Option[Combo] = {
    val pattern = "(.+)\\s*->\\s*(.+)".r
    line match {
      case pattern(sequence, name) =>
        Some(Combo(sequence.split(",").flatMap(_.split('+')).map(_.trim).toList, name.trim))
      case _ => None
    }
  }

}
