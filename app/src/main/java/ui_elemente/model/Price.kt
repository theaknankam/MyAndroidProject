package ui_elemente.model

import androidx.compose.foundation.pager.PagerSnapDistance
import ui_elemente.model.enums.Insurance
import java.util.Date

data class Price(
    val startTime: Date,
    val endTime: Date,
    val from: String,
    val to: String,
    val vehicle: Vehicule,
    var insurence: Insurance

    )