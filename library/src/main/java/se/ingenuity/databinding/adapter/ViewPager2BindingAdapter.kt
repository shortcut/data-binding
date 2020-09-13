package se.ingenuity.databinding.adapter

import androidx.annotation.Keep
import androidx.annotation.Px
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
    ),
    InverseBindingMethod(
        type = ViewPager2::class,
        attribute = "currentScrollState",
        method = "getScrollState"
    )
)
object ViewPager2BindingAdapter {
    @Keep
    @JvmStatic
    @BindingAdapter("currentScrollState")
    fun ViewPager2.setCurrentScrollState(state: Int) {
        // NO-OP
        // ViewPager2 doesn't have a setter for applying current scroll state but this attribute
        // needs to be consumed in order for the inverse method to accepted.
    }

    @JvmStatic
    @BindingAdapter(
        value = [
            "onPageSelected",
            "currentItemAttrChanged",
            "onPageScrolled",
            "onPageScrollStateChanged",
            "currentScrollStateAttrChanged"
        ],
        requireAll = false
    )
    fun setListener(
        view: ViewPager2,
        onPageSelected: OnPageSelected?,
        currentItemAttrChange: InverseBindingListener?,
        onPageScrolled: OnPageScrolled?,
        onPageScrollStateChanged: OnPageScrollStateChanged?,
        currentScrollStateAttrChanged: InverseBindingListener?
    ) {
        val newListener: ViewPager2.OnPageChangeCallback? =
            if (onPageSelected == null &&
                currentItemAttrChange == null &&
                onPageScrolled == null &&
                onPageScrollStateChanged == null &&
                currentScrollStateAttrChanged == null
            ) {
                null
            } else {
                object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        onPageSelected?.invoke(position)
                        currentItemAttrChange?.onChange()
                    }

                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {
                        onPageScrolled?.invoke(position, positionOffset, positionOffsetPixels)
                    }

                    override fun onPageScrollStateChanged(state: Int) {
                        onPageScrollStateChanged?.invoke(state)
                        currentScrollStateAttrChanged?.onChange()
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

    interface OnPageScrollStateChanged {
        fun invoke(state: Int)
    }

    interface OnPageScrolled {
        fun invoke(position: Int, positionOffset: Float, @Px positionOffsetPixels: Int)
    }
}