package se.ingenuity.databinding.adapter

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.adapters.ListenerUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import se.ingenuity.databinding.R

object RecyclerViewBindingAdapter {
    @JvmStatic
    @BindingAdapter(
        value = ["itemDecoration"],
        requireAll = false
    )
    fun setItemDecoration(
        view: RecyclerView,
        itemDecoration: RecyclerView.ItemDecoration?
    ) {
        val oldValue = ListenerUtil
            .trackListener(view, itemDecoration, R.id.db_helper__recycler_view__item_decoration)
        oldValue?.let(view::removeItemDecoration)
        itemDecoration?.let(view::addItemDecoration)
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "currentPosition", event = "currentPositionAttributeChanged")
    fun getCurrentPosition(rv: RecyclerView): Int {
        return (rv.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
    }

    @JvmStatic
    @BindingAdapter(value = ["currentPositionAttributeChanged"])
    fun setListener(rv: RecyclerView, l: InverseBindingListener) {
        val layoutManager = (rv.layoutManager as LinearLayoutManager?)!!
        var prevPos = layoutManager.findFirstVisibleItemPosition()
        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy == 0) {
                    return
                }
                val newPos = layoutManager.findFirstVisibleItemPosition()
                if (prevPos != newPos) {
                    prevPos = newPos
                    l.onChange()
                }
            }
        })
    }

    @JvmStatic
    @BindingAdapter("currentPosition")
    fun setCurrentPosition(rv: RecyclerView, pos: Int) {
        (rv.layoutManager as LinearLayoutManager).scrollToPosition(pos)
    }
}