package se.ingenuity.databinding.adapter

import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.databinding.BindingAdapter

object EditTextBindingAdapter {

    @JvmStatic
    @BindingAdapter("onFinishEditing")
    fun setOnFinishEditing(view: EditText, onFinishEditing: () -> Unit) {
        view.onFinishedEditing(onFinishEditing)
    }

    private fun EditText.onFinishedEditing(onFinishedEditing: () -> Unit) {
        this.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                actionId == EditorInfo.IME_ACTION_DONE ||
                event != null &&
                event.action == KeyEvent.ACTION_DOWN &&
                event.keyCode == KeyEvent.KEYCODE_ENTER) {
                if (event == null || !event.isShiftPressed) {
                    onFinishedEditing()
                }
            }
            false
        }
    }

}