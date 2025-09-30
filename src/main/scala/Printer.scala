object Printer {

  def printGrammar(grammar: Grammar): Unit = {
    println("----Key mappings----")
    grammar.keyMapping.foreach(it => println(s"${it.key} -> ${it.action}"))

    println("----Combos----")
    grammar.combos.foreach { combo =>
      println(s"${combo.sequence.mkString(",")} -> ${combo.moveName}")
    }
    println("__________________________________")
  }
}
