package ui_elemente.model

import ui_elemente.model.enums.Brands
import ui_elemente.model.enums.FuelType
import ui_elemente.model.enums.VehiculeType

data class Vehicule(
    val id: Int,
    val brand: Brands,
    val type: VehiculeType,
    val seats: Int,
    val fuelType: FuelType,
    val available: Boolean,
    val image: Int
)