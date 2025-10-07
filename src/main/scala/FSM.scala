case class FSM(combos: List[Combo], keyMap: Map[String, String]) {

  private def mapKeysToTokens(inputGroups: List[String]): List[List[String]] = {
    inputGroups.map { group =>
      group.split('+').toList.map(k => keyMap.getOrElse(k, k))
    }
  }

  def recognize(input: List[String]): List[String] = {
    val tokens = mapKeysToTokens(input)
    combos.filter(_.sequence == tokens).map(_.moveName)
  }

  def debugTrace(input: List[String]): List[String] = {
    val tokens = mapKeysToTokens(input)
    combos.zipWithIndex.collect {
      case (combo, idx) if tokens.startsWith(combo.sequence) =>
        val matchStatus = if (tokens == combo.sequence) "Match!" else "Partial match..."
        val seqStr = combo.sequence.map(_.mkString("+")).mkString(",")
        s"State $idx ($seqStr) -> $matchStatus (${combo.moveName})"
    }
  }
}