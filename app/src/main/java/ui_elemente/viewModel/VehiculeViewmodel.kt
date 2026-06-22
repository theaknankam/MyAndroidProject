package ui_elemente.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carsharing_app.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import ui_elemente.model.Vehicule
import ui_elemente.model.enums.Brands
import ui_elemente.model.enums.FuelType
import ui_elemente.model.enums.VehiculeType
import kotlin.time.Duration.Companion.milliseconds

class VehiculeViewmodel : ViewModel() {

    private val _vehicles = MutableLiveData<List<Vehicule>>()
    val vehicles: LiveData<List<Vehicule>> = _vehicles

    fun loadCars() {
        viewModelScope.launch {

            // Simulation d'un chargement
            delay(1000.milliseconds)

            _vehicles.value = listOf(
                Vehicule(
                    1,
                    Brands.Mazda,
                    VehiculeType.SUV,
                    5,
                    FuelType.Diesel,
                    true,
                    R.drawable.car1
                ),
                Vehicule(
                    2,
                    Brands.RangeRover,
                    VehiculeType.SUV,
                    5,
                    FuelType.Diesel,
                    true,
                    R.drawable.car2
                ),
                Vehicule(
                    3,
                    Brands.Renault,
                    VehiculeType.Car,
                    4,
                    FuelType.Electric,
                    true,
                    R.drawable.car3

                ),
                Vehicule(
                    4,
                    Brands.Mercedes,
                    VehiculeType.Van,
                    3,
                    FuelType.Gazoline,
                    false,
                    R.drawable.car4
                ),
                Vehicule(
                    5,
                    Brands.RangeRover,
                    VehiculeType.SUV,
                    5,
                    FuelType.Hybrid,
                    true,
                    R.drawable.car2
                ),
                Vehicule(
                    6,
                    Brands.Scooter,
                    VehiculeType.Scooter,
                    2,
                    FuelType.Electric,
                    true,
                    R.drawable.scooter


                )

            )

        }
    }
}
