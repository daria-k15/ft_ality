object GrammarValidator {

  def validate(grammar: Grammar): Either[AppError, Grammar] = {
    if (grammar.keyMapping.isEmpty) {
      Left(AppError.ValidationError("No key mappings found in grammar"))
    } else if (grammar.combos.isEmpty) {
      Left(AppError.ValidationError("No combos found in grammar"))
    } else if (grammar.keyMapping.exists(km => km.key.isEmpty || km.action.isEmpty)) {
      Left(AppError.ValidationError("Key mapping contains empty key or action"))
    } else if (grammar.combos.exists(c => c.sequence.isEmpty || c.moveName.isEmpty)) {
      Left(AppError.ValidationError("Combo contains empty sequence or move name"))
    } else {
      val availableActions = grammar.keyMapping.map(_.action).toSet
      val comboTokens = grammar.combos.flatMap(_.sequence).toSet
      val missingTokens = comboTokens.diff(availableActions)

      if (missingTokens.nonEmpty) {
        Left(AppError.ValidationError(s"Combo references undefined tokens: ${missingTokens.mkString(", ")}"))
      } else {
        Right(grammar)
      }
    }
  }
}
