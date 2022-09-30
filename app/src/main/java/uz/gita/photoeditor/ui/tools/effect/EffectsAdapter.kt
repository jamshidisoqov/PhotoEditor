package uz.gita.photoeditor.ui.tools.effect

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.gita.photoeditor.databinding.ItemFilterBinding

class EffectsAdapter : RecyclerView.Adapter<EffectsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind() {

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = EffectSource.effects.size

}