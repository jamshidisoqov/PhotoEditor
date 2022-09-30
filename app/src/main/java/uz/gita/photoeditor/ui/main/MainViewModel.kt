package uz.gita.photoeditor.ui.main

import android.net.Uri
import kotlinx.coroutines.flow.Flow

interface MainViewModel {

    val openCameraFlow: Flow<Unit>
    val openGalleryFlow: Flow<Unit>

    fun openEditorScreen(imgUri: Uri)
    fun takeImageFromCamera()
    fun takeImageFromGallery()

}