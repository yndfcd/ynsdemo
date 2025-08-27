import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class MainState(
    val name: String = "",
    val greeting: String = ""
)

sealed class MainIntent {
    data class UpdateName(val name: String) : MainIntent()
    object SayHello : MainIntent()
}

class MainViewModel : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state

    fun processIntent(intent: MainIntent) {
        viewModelScope.launch {
            when (intent) {
                is MainIntent.UpdateName -> {
                    _state.value = _state.value.copy(name = intent.name)
                }
                is MainIntent.SayHello -> {
                    _state.value = _state.value.copy(greeting = "Hello ${_state.value.name}!")
                }
            }
        }
    }
}