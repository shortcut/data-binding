package se.ingenuity.databinding.adapter

import android.os.Build
import android.view.ViewTreeObserver
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

object TabLayoutBindingAdapter {

    @JvmStatic
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    @BindingAdapter(value = ["viewPager", "pageNames"], requireAll = false)
    fun TabLayout.setViewPager(viewPager: ViewPager2, pageNames: List<String>?) {
        viewPager.viewTreeObserver?.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {

            override fun onGlobalLayout() {
                TabLayoutMediator(this@setViewPager, viewPager) { tab, position ->
                    pageNames?.let { names ->
                        tab.text = names.getOrElse(position) { "" }
                    }
                }.attach()
                viewPager.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }
}