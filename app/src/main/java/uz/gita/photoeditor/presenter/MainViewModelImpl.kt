package uz.gita.photoeditor.presenter

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import uz.gita.photoeditor.navigation.Navigator
import uz.gita.photoeditor.ui.main.MainScreenDirections
import uz.gita.photoeditor.ui.main.MainViewModel
import uz.gita.photoeditor.utils.eventFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModelImpl @Inject constructor(
    private val navigator: Navigator
) : MainViewModel, ViewModel() {

    override val openCameraFlow = eventFlow()
    override val openGalleryFlow = eventFlow()

    override fun openEditorScreen(imgUri: Uri) {
        viewModelScope.launch {
            navigator.navigateTo(MainScreenDirections.actionMainScreenToEditorScreen(imgUri))
        }
    }

    override fun takeImageFromCamera() {
        viewModelScope.launch {
            openCameraFlow.emit(Unit)
        }
    }

    override fun takeImageFromGallery() {
        viewModelScope.launch {
            openGalleryFlow.emit(Unit)
        }
    }
}