package se.ingenuity.databinding.adapter

import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.databinding.BindingAdapter
import se.ingenuity.databinding.adapter.EditTextBindingAdapter.onFinishedEditing

object EditTextBindingAdapter {

    @JvmStatic
    @BindingAdapter("onSubmit")
    fun EditText.onSubmit(onSubmit: () -> Unit) {
        this.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                actionId == EditorInfo.IME_ACTION_DONE ||
                event != null &&
                event.action == KeyEvent.ACTION_DOWN &&
                event.keyCode == KeyEvent.KEYCODE_ENTER) {
                if (event == null || !event.isShiftPressed) {
                    onSubmit()
                }
            }
            false
        }
    }

}