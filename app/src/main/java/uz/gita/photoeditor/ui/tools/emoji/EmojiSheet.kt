package uz.gita.photoeditor.ui.tools.emoji

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import timber.log.Timber
import uz.gita.photoeditor.databinding.SheetEmojiToolBinding

class EmojiSheet : BottomSheetDialogFragment() {

    private var _viewBinding: SheetEmojiToolBinding? = null
    private val viewBinding get() = _viewBinding!!

    private var itemSelectedListener: ((Emoji) -> Unit)? = null

    fun setItemSelectedListener(block: (Emoji) -> Unit) {
        itemSelectedListener = block
    }

    private val adapter: EmojiAdapter by lazy { EmojiAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = SheetEmojiToolBinding.inflate(inflater, container, false)
        return _viewBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter.setItemClickListener {
            Timber.d(it.toString())
            itemSelectedListener?.invoke(it.copy())
        }
        viewBinding.lisEmoji.adapter = adapter
        viewBinding.lisEmoji.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }


}