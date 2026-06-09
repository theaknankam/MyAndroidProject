package ui_elemente.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carsharing_app.R
import ui_elemente.model.Vehicule
import ui_elemente.model.enums.Brands
import ui_elemente.model.enums.FuelType
import ui_elemente.model.enums.VehiculeType

class VehiculeViewmodel : ViewModel() {

        private val _vehicles = MutableLiveData(
            listOf(
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
                    6 ,
                    Brands.Scooter,
                    VehiculeType.Scooter,
                    2,
                    FuelType.Electric,
                    true,
                    R.drawable.scooter


                )

            )
        )

        val vehicles: LiveData<List<Vehicule>>
            get() = _vehicles
    }
