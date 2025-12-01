import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlin.random.Random
import com.example.myapplication.R

class CoroutineViewModel : ViewModel() {

    var sliderValue by mutableStateOf(10f)
    var sequential by mutableStateOf(true)
    var parallel by mutableStateOf(false)
    var delayedStart by mutableStateOf(false)
    var selectedDispatcher by mutableStateOf(R.string.default_dispatcher)
    var isRunning by mutableStateOf(false)

    private val _events = MutableSharedFlow<UiEvent>()
    val events = _events.asSharedFlow()

    private fun getDispatcher() = when(selectedDispatcher) {
        R.string.dispatcher_io -> Dispatchers.IO
        else -> Dispatchers.Default
    }

    fun startCoroutines() {
        if (isRunning) return
        isRunning = true

        viewModelScope.launch {
            val dispatcher = getDispatcher()
            val jobs = mutableListOf<Job>()

            repeat(sliderValue.toInt()) {
                val job = launch(dispatcher) {
                    if (delayedStart) delay(1000L)
                    val delayTime = Random.nextLong(1000L, 10000L)
                    delay(delayTime)

                    if (delayTime >= 7000L && Random.nextInt(100) < 30) {
                        val exceptionType = Random.nextInt(3)
                        when (exceptionType) {
                            0 -> _events.emit(UiEvent.ShowToast(R.string.toast_exception))
                            1 -> _events.emit(UiEvent.ShowSnackbar(R.string.snackbar_exception))
                            2 -> _events.emit(UiEvent.ResetSettings)
                        }
                    }
                }
                jobs.add(job)
                if (sequential) job.join()
            }

            jobs.joinAll()
            isRunning = false
            _events.emit(UiEvent.ShowToast(R.string.all_coroutines_done))
        }
    }

    sealed class UiEvent {
        data class ShowToast(@StringRes val messageRes: Int) : UiEvent()
        data class ShowSnackbar(@StringRes val messageRes: Int) : UiEvent()
        object ResetSettings : UiEvent()
    }
}
