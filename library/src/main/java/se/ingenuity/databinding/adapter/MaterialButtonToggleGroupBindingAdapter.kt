package se.ingenuity.databinding.adapter

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.InverseBindingMethod
import androidx.databinding.InverseBindingMethods
import androidx.databinding.adapters.ListenerUtil
import com.google.android.material.button.MaterialButtonToggleGroup
import se.ingenuity.databinding.R

@InverseBindingMethods(
    InverseBindingMethod(
        type = MaterialButtonToggleGroup::class,
        attribute = "checkedButton",
        method = "getCheckedButtonId"
    )
)
object MaterialButtonToggleGroupBindingAdapter {
    @JvmStatic
    @BindingAdapter("checkedButton")
    fun setCheckedButton(view: MaterialButtonToggleGroup, id: Int) {
        if (id != view.checkedButtonId) {
            view.check(id)
        }
    }

    @JvmStatic
    @BindingAdapter(
        value = ["onButtonCheckedListener", "checkedButtonAttrChanged"],
        requireAll = false
    )
    fun setListeners(
        view: MaterialButtonToggleGroup,
        listener: MaterialButtonToggleGroup.OnButtonCheckedListener?,
        attrChange: InverseBindingListener?
    ) {
        val newListener: MaterialButtonToggleGroup.OnButtonCheckedListener? =
            if (listener == null && attrChange == null) {
                null
            } else {
                MaterialButtonToggleGroup.OnButtonCheckedListener { group, checkedId, isChecked ->
                    listener?.onButtonChecked(group, checkedId, isChecked)
                    attrChange?.onChange()
                }
            }

        val oldListener: MaterialButtonToggleGroup.OnButtonCheckedListener? =
            ListenerUtil.trackListener(
                view,
                newListener,
                R.id.db_helper__material_button_toggle_group__listener
            )

        oldListener?.let(view::removeOnButtonCheckedListener)
        newListener?.let(view::addOnButtonCheckedListener)
    }
}