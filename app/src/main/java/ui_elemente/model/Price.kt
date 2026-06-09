package ui_elemente.model

import androidx.compose.foundation.pager.PagerSnapDistance
import java.util.Date

data class Price(
    var leasingbegin : Date,
    var leasinend : Date,
    var age : Int,
    var distance: Int,
    var carType: String,
    var insurence: Int

    )