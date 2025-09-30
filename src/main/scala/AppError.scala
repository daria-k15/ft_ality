sealed trait AppError

object AppError {
  case object Help extends AppError
  case object InvalidArguments extends AppError
  case class IOError(cause: Throwable) extends AppError
  case class ParsingError(cause: String) extends AppError
  case class ValidationError(cause: String) extends AppError
}