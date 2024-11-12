package store.domain.model

enum class Constants(private val value: String) {
    COMMA(","),
    HYPHEN("-"),
    SQUARE_BRACKETS_LEFT("["),
    SQUARE_BRACKETS_RIGHT("]");

    override fun toString(): String = value
}