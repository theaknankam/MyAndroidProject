package ui_elemente.model

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import ui_elemente.model.enums.Brands
import ui_elemente.model.enums.FuelType
import ui_elemente.model.enums.VehiculeType

@Entity(tableName = "vehicule")
data class Vehicule(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val brand: Brands,
    val type: VehiculeType,
    val seats: Int,
    val fuelType: FuelType,
    val available: Boolean,
    val image: Int
)