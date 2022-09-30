package uz.gita.photoeditor.ui.main

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.ldralighieri.corbind.view.clicks
import uz.gita.photoeditor.R
import uz.gita.photoeditor.databinding.ScreenMainBinding
import uz.gita.photoeditor.presenter.MainViewModelImpl
import uz.gita.photoeditor.utils.CLICK_TIME_OUT
import uz.gita.photoeditor.utils.toast

@AndroidEntryPoint
class MainScreen : Fragment(R.layout.screen_main) {

    private val viewBinding: ScreenMainBinding by viewBinding(ScreenMainBinding::bind)
    private val viewModel: MainViewModel by viewModels<MainViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeEvents()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setClickListeners()
    }

    private fun subscribeEvents() {
        viewModel.openCameraFlow.onEach {
            ImagePicker.with(this).crop().cameraOnly().createIntent {
                startForProfileImageResult.launch(it)
            }
        }.launchIn(lifecycleScope)

        viewModel.openGalleryFlow.onEach {
            ImagePicker.with(this).crop().galleryOnly().createIntent {
                startForProfileImageResult.launch(it)
            }
        }.launchIn(lifecycleScope)
    }

    @OptIn(FlowPreview::class)
    private fun setClickListeners() {
        viewBinding.btnCamera
            .clicks()
            .debounce(CLICK_TIME_OUT)
            .onEach {
                viewModel.takeImageFromCamera()
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewBinding.btnGallery
            .clicks()
            .debounce(CLICK_TIME_OUT)
            .onEach {
                viewModel.takeImageFromGallery()
            }.launchIn(viewLifecycleOwner.lifecycleScope)

    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    val fileUri = data?.data!!
                    viewModel.openEditorScreen(fileUri)
                }
                ImagePicker.RESULT_ERROR -> {
                    toast(ImagePicker.getError(data))
                }
                else -> {
                    toast("Task Cancelled")
                }
            }
        }


}