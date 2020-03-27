package se.ingenuity.databinding.adapter

import androidx.databinding.BindingAdapter
import androidx.databinding.adapters.ListenerUtil
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
}