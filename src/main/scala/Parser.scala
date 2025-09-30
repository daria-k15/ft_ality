object Parser {

  private def cleanLine(line: String): String =
    line.takeWhile(_ != '#').trim

  def parseFile(lines: List[String]): Either[AppError, Grammar] = {
    try {
      val keyMappingLines = lines
        .dropWhile(l => !l.trim.startsWith("# KeyMapping:"))
        .drop(1)
        .takeWhile(l => !l.trim.startsWith("# Combos:"))
        .map(cleanLine)

      val comboLines = lines
        .dropWhile(l => !l.trim.startsWith("# Combos:"))
        .drop(1)
        .map(cleanLine)

      val keyMappings = keyMappingLines.map { line =>
        val Array(key, action) = line.split("->", 2).map(_.trim.stripPrefix("'").stripSuffix("'"))
        KeyMapping(key, action)
      }

      val combos = comboLines.map { line =>
        val Array(seq, move) = line.split("->", 2).map(_.trim)
        val sequence = parseSequence(seq)
        Combo(sequence, move)
      }

      Right(Grammar(keyMappings, combos))
    } catch {
      case e: Exception => Left(AppError.ParsingError(s"Got error while parsing file: ${e.getMessage}"))
    }
  }

  private def parseSequence(seq: String): List[String] = {
    seq.split(',')
      .flatMap(_.split('+'))
      .map(_.trim)
      .filter(_.nonEmpty)
      .toList
  }
}
