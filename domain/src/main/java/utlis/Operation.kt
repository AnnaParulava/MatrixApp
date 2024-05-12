package utlis

sealed class Operation {
    object Add : Operation()
    object Subtract : Operation()
    object Multiply : Operation()
    object Transpose : Operation()
    object Rank : Operation()
    object Determinant: Operation()
}