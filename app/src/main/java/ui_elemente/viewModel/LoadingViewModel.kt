package ui_elemente.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoadingViewModel: ViewModel() {
    var firstHomeLoading by mutableStateOf(true)
}
