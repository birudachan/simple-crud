package app.twentyhours.models

import java.util.UUID

data class Ingredient(val id: UUID = UUID.randomUUID(), val name: String)
