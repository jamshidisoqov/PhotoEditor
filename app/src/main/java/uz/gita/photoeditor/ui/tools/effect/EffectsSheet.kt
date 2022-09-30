package uz.gita.photoeditor.ui.tools.effect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import uz.gita.photoeditor.databinding.SheetEffectToolBinding

class EffectsSheet : BottomSheetDialogFragment() {

    private val viewBinding: SheetEffectToolBinding by viewBinding(SheetEffectToolBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = viewBinding.root


}