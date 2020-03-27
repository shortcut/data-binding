package se.ingenuity.databinding.adapter

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.InverseBindingMethod
import androidx.databinding.InverseBindingMethods
import androidx.databinding.adapters.ListenerUtil
import androidx.viewpager2.widget.ViewPager2
import se.ingenuity.databinding.R

@InverseBindingMethods(
    InverseBindingMethod(
        type = ViewPager2::class,
        attribute = "currentItem",
        method = "getCurrentItem"
    )
)
object ViewPager2BindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["onPageSelected", "currentItemAttrChanged"], requireAll = false)
    fun setListener(
        view: ViewPager2,
        onPageSelected: OnPageSelected?,
        attrChange: InverseBindingListener?
    ) {
        val newListener: ViewPager2.OnPageChangeCallback? =
            if (onPageSelected == null && attrChange == null) {
                null
            } else {
                object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        onPageSelected?.invoke(position)
                        attrChange?.onChange()
                    }
                }
            }

        val oldListener = ListenerUtil.trackListener(
            view,
            newListener,
            R.id.db_helper__view_pager_2__listener
        )

        oldListener?.let(view::unregisterOnPageChangeCallback)
        newListener?.let(view::registerOnPageChangeCallback)
    }


    interface OnPageSelected {
        fun invoke(position: Int)
    }
}