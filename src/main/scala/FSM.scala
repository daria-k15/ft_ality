case class FSM(combos: List[Combo], keyMap: Map[String, String]) {

  def mapKeysToTokens(keys: List[String]): List[String] =
    keys.map(k => keyMap.getOrElse(k, k))

  def recognize(inputKeys: List[String]): List[String] = {
    val tokens = mapKeysToTokens(inputKeys)
    combos.filter(_.sequence == tokens).map(_.moveName)
  }

  def debugTrace(inputKeys: List[String]): List[String] = {
    val tokens = mapKeysToTokens(inputKeys)
    combos.zipWithIndex.collect {
      case (combo, idx) if tokens.startsWith(combo.sequence) =>
        val matchStatus = if (tokens == combo.sequence) "Match!" else "Partial match..."
        s"State $idx (${combo.sequence.mkString(",")}) -> $matchStatus (${combo.moveName})"
    }
  }
}