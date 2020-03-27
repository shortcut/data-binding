package se.ingenuity.databinding.adapter

import android.annotation.SuppressLint
import android.widget.CompoundButton
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.adapters.CompoundButtonBindingAdapter

object CompoundButtonBindingAdapter {
    @SuppressLint("RestrictedApi")
    @BindingAdapter(
        value = ["android:onCheckedChanged", "android:checkedAttrChanged", "broadcastFirst"],
        requireAll = false
    )
    @JvmStatic
    fun CompoundButton.setListeners(
        listener: CompoundButton.OnCheckedChangeListener?,
        attrChange: InverseBindingListener?,
        broadcastFirst: Boolean?
    ) {
        CompoundButtonBindingAdapter.setListeners(this, listener, attrChange)
        if (broadcastFirst == true) {
            listener?.onCheckedChanged(this, isChecked)
            attrChange?.onChange()
        }
    }
}