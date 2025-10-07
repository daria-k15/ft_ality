
sealed trait GrammarSealed

case class KeyMapping(key: String, action: String) extends GrammarSealed
case class Combo(sequence: List[List[String]], moveName: String) extends GrammarSealed
case class Grammar(keyMapping: List[KeyMapping], combos: List[Combo])
