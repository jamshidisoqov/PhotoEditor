package uz.gita.photoeditor.ui.editor

import android.annotation.SuppressLint
import android.graphics.PointF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.children
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.ldralighieri.corbind.view.clicks
import timber.log.Timber
import uz.gita.photoeditor.R
import uz.gita.photoeditor.databinding.ItemEmojiBinding
import uz.gita.photoeditor.databinding.ScreenEditorBinding
import uz.gita.photoeditor.presenter.EditorViewModelImpl
import uz.gita.photoeditor.ui.tools.emoji.Emoji
import uz.gita.photoeditor.ui.tools.emoji.EmojiSheet
import uz.gita.photoeditor.utils.invisible
import uz.gita.photoeditor.utils.removeViewByTag
import uz.gita.photoeditor.utils.visible
import kotlin.math.PI
import kotlin.math.acos
import kotlin.math.sqrt


@AndroidEntryPoint
class EditorScreen : Fragment(R.layout.screen_editor) {

    private val viewBinding: ScreenEditorBinding by viewBinding(ScreenEditorBinding::bind)
    private val viewModel: EditorViewModel by viewModels<EditorViewModelImpl>()
    private val navArgs: EditorScreenArgs by navArgs()

    private var startPoint = PointF()
    private var startScalePoint = PointF()
    private var imgPosition = PointF()
    private var startPositionOfRotation = PointF()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setImage(navArgs.uri)
        subscribeEvents()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setClickListeners()
        subscribeUIState()
    }

    private fun subscribeEvents() {

    }

    private fun subscribeUIState() {
        viewModel.imgUriFlow.onEach {
            Glide.with(this)
                .load(it)
                .into(viewBinding.image)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.addEmojiFlow.onEach {
            addEmojiToContainer(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.removeEmojiFlow.onEach {
            Timber.d("Remove view $it")
            removeEmojiFromContainer(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.unselectAllFlow.onEach {
            Timber.d("Unselect childs")
            unselectAllItems()
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.imgModifyingFlow.onEach {
            Timber.d("Toolsbar State $it")
            viewBinding.toolsBar.apply {
                if (it)
                    animate().translationY(height.toFloat()).setDuration(500).start()
                else
                    animate().translationY(0f).setDuration(500).start()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setClickListeners() {

        viewBinding.btnToolEffect.clicks()
            .onEach {
            //Open bottom
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewBinding.btnToolEmoji.clicks()
            .onEach {
                val emojiSheet = EmojiSheet()
                emojiSheet.setItemSelectedListener {
                    viewModel.addEmoji(it)
                    emojiSheet.dismiss()
                }
                emojiSheet.show(childFragmentManager, "Emoji")
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewBinding.btnToolText.clicks()
            .onEach {

            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewBinding.btnBack.clicks().onEach {
            viewModel.back()
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        viewBinding.editorContainer.setOnClickListener { viewModel.unselectAll() }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun addEmojiToContainer(emoji: Emoji) {
        val emojiBinding = ItemEmojiBinding.inflate(
            LayoutInflater.from(requireContext()),
            viewBinding.editorContainer,
            false
        )
        emojiBinding.root.tag = emoji.tag
        Glide.with(this)
            .asDrawable()
            .override(200)
            .load(emoji.res)
            .into(emojiBinding.img)

        emojiBinding.root.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    showEmojiFunctions(emojiBinding)
                    startPoint.x = event.x
                    startPoint.y = event.y
                }
                MotionEvent.ACTION_MOVE -> {
                    val dx = event.x - startPoint.x
                    val dy = event.y - startPoint.y
                    emojiBinding.root.x += dx
                    emojiBinding.root.y += dy
                }
                MotionEvent.ACTION_UP -> {
                    startPoint.x = 0f
                    startPoint.y = 0f
                }
                else -> {

                }
            }
            true
        }

        emojiBinding.btnScale.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startScalePoint.x = event.x
                    startScalePoint.y = event.y
                }
                MotionEvent.ACTION_MOVE -> {
                    val dx = event.x - startScalePoint.x
                    val dy = event.y - startScalePoint.y

                    val scaleX = (emojiBinding.img.width + dx) / emojiBinding.img.width
                    val scaleY = (emojiBinding.img.height + dy) / emojiBinding.img.height

                    val newWSize = emojiBinding.img.width * scaleX
                    val newHSize = emojiBinding.img.height * scaleY

                    if (newWSize > 100f
                        && newHSize > 100f
                        && newWSize < viewBinding.editorContainer.width - 30f
                        && newHSize < viewBinding.editorContainer.height - 35f
                    ) {

                        emojiBinding.img.updateLayoutParams {
                            this.width = newWSize.toInt()
                            this.height = newHSize.toInt()
                        }

                    }

                }
                MotionEvent.ACTION_UP -> {
                    emoji.emojiPosition.x = emojiBinding.root.x.toInt()
                    emoji.emojiPosition.y = emojiBinding.root.y.toInt()

                    startScalePoint.x = 0f
                    startScalePoint.y = 0f
                    imgPosition.x = 0f
                    imgPosition.y = 0f
                }
            }
            true
        }

        emojiBinding.btnRotate.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startPositionOfRotation.x = event.x
                    startPositionOfRotation.y = event.y
                }
                MotionEvent.ACTION_MOVE -> {
                    val degree = getRotationDegree(emojiBinding, event.x, event.y)
                    Timber.d("Degree $degree")
                    emojiBinding.root.rotation = degree + emojiBinding.root.rotation
                }
                MotionEvent.ACTION_UP -> {
                    startPositionOfRotation.x = 0f
                    startPositionOfRotation.y = 0f
                }
            }
            true
        }

        emojiBinding.btnRemove.setOnClickListener {
            viewModel.removeEmoji(emoji)
        }

        viewBinding.editorContainer.addView(
            emojiBinding.root
        )

        emojiBinding.root.x = emoji.emojiPosition.x.toFloat()
        emojiBinding.root.y = emoji.emojiPosition.y.toFloat()

    }

    private fun removeEmojiFromContainer(emoji: Emoji) {
        viewBinding.editorContainer.removeViewByTag(emoji.tag)
    }

    private fun unselectAllItems() {
        Timber.d("Child count ${viewBinding.editorContainer.childCount}")

        viewBinding.editorContainer.children
            .forEach {
                Timber.d("Make invisible")
                val child = it as LinearLayout
                val btnCancel: View = child.findViewById(R.id.btnRemove)
                val img: View = child.findViewById(R.id.img)
                val btnScale: View = child.findViewById(R.id.btnScale)
                btnCancel.invisible()
                btnScale.invisible()
                img.background = null
            }

    }

    private fun showEmojiFunctions(emojiBinding: ItemEmojiBinding) {
        emojiBinding.btnRemove.visible()
        emojiBinding.btnScale.visible()
        emojiBinding.img.setBackgroundResource(R.drawable.item_border)
    }

    private fun getRotationDegree(emojiBinding: ItemEmojiBinding, x: Float, y: Float): Float {
        val cx = emojiBinding.root.width / 2f + emojiBinding.root.x
        val cy = emojiBinding.root.height / 2f + emojiBinding.root.y
        val firstDistance =
            getDistance(cx, cy, startPositionOfRotation.x, startPositionOfRotation.y)
        val finalDistance = getDistance(cx, cy, x, y)

        //(start.x-cx, start.y-cy)
        val firstVectorX = startPositionOfRotation.x - cx
        val firstVectorY = startPositionOfRotation.y - cy

        //(x-cx, y-cy)
        val secondVectorX = x - cx
        val secondVectorY = y - cy

        val multiplicationOfVectors = firstVectorX * secondVectorX + firstVectorY * secondVectorY
        val cosOfDegree = multiplicationOfVectors / (firstDistance * finalDistance)
        val degreeInRadian = acos(cosOfDegree)

        return degreeInRadian.toDegree()
    }

    private fun getDistance(x1: Float, y1: Float, x2: Float, y2: Float): Float {
        return sqrt(sqr(x1 - x2) + sqr(y1 - y2))
    }

    private fun sqr(a: Float) = a * a

    private fun Float.toDegree() = (this * 180 / PI).toFloat()

}