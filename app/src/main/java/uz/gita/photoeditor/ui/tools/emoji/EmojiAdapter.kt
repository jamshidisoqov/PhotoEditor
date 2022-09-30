package uz.gita.photoeditor.ui.tools.emoji

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import uz.gita.photoeditor.databinding.ItemEmojiToolBinding

class EmojiAdapter : RecyclerView.Adapter<EmojiAdapter.EmojiViewHolder>() {

    private var itemClickListener: ((Emoji) -> Unit)? = null

    fun setItemClickListener(block: (Emoji) -> Unit) {
        itemClickListener = block
    }

    inner class EmojiViewHolder(private val binding: ItemEmojiToolBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            Glide
                .with(binding.root.context)
                .asBitmap()
                .apply(RequestOptions().override(150))
                .load(EmojiSource.emojis[absoluteAdapterPosition].res)
                .into(binding.imgEffect)
        }

        init {
            binding.root.setOnClickListener {
                itemClickListener?.invoke(EmojiSource.emojis[absoluteAdapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = EmojiViewHolder(
        ItemEmojiToolBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: EmojiViewHolder, position: Int) = holder.bind()

    override fun getItemCount(): Int = EmojiSource.emojis.size
}