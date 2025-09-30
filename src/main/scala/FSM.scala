case class FSM(combos: List[Combo]) {
  def recognize(input: List[String]): List[String] =
    combos.filter(_.sequence == input).map(_.moveName)

  def debugTrace(input: List[String]): List[String] =
    combos.zipWithIndex.collect {
      case (combo, idx) if input.startsWith(combo.sequence) =>
        val matchStatus = if (input == combo.sequence) "Match!" else "Partial match..."
        s"State $idx (${combo.sequence.mkString(",")}) -> $matchStatus (${combo.moveName})"
    }
}