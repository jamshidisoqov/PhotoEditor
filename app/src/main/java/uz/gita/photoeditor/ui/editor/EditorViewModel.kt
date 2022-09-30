package uz.gita.photoeditor.ui.editor

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import uz.gita.photoeditor.ui.tools.emoji.Emoji

interface EditorViewModel {

    val imgUriFlow: Flow<Uri>
    val addEmojiFlow: Flow<Emoji>
    val removeEmojiFlow: Flow<Emoji>
    val unselectAllFlow: Flow<Unit>
    val imgModifyingFlow: Flow<Boolean>

    fun setImage(imgUri: Uri)

    fun addEmoji(emoji: Emoji)

    fun removeEmoji(emoji: Emoji)

    fun unselectAll()

    fun setModified()

    fun back()

}