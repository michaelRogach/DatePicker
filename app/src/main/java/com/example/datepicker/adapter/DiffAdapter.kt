package com.example.datepicker.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.annotation.StringRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class DiffAdapter<VH : DiffVH<ITEM>, ITEM : Any>(@NonNull diffCallback: DiffUtil.ItemCallback<ITEM>) : ListAdapter<ITEM, VH>(diffCallback) {

    companion object {
        fun <T> diffCallbackWithoutChanges() = object : DiffUtil.ItemCallback<T>() {
            override fun areContentsTheSame(oldItem: T, newItem: T) = false
            override fun areItemsTheSame(oldItem: T, newItem: T) = false
        }
    }

    private var layoutInflater: LayoutInflater? = null

    private fun inflater(context: Context): LayoutInflater {
        val inflater = layoutInflater ?: LayoutInflater.from(context)
        layoutInflater = inflater
        return inflater
    }

    @LayoutRes
    abstract fun getLayoutResourceId(viewType: Int): Int

    abstract fun createViewHolder(rootView: View, viewType: Int): VH

    open fun bindViewHolder(holder: VH, item: ITEM) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        createViewHolder(inflater(parent.context).inflate(getLayoutResourceId(viewType), parent, false), viewType)

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        bindViewHolder(holder, item)
        holder.bind(item, null)
    }

    override fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) {
        val item = getItem(position)
        if (payloads.isEmpty()) {
            bindViewHolder(holder, item)
            holder.bind(item, null)
        } else {
            holder.bind(item, DiffVH.mergePayloads(payloads))
        }
    }

    override fun submitList(list: List<ITEM>?) {
        super.submitList(list?.toMutableList())
    }

    // Callback can be executed when you already destroyed screen. Be careful!
    override fun submitList(list: List<ITEM>?, commitCallback: Runnable?) {
        super.submitList(list?.toMutableList(), commitCallback)
    }

    fun submitList(list: List<ITEM>?, commitCallback: Runnable, allowPassCommit: () -> Boolean) {
        super.submitList(list?.toMutableList()) {
            if (allowPassCommit.invoke())
                commitCallback.run()
        }
    }
}

abstract class DiffVH<ITEM : Any>(val containerView: View) : RecyclerView.ViewHolder(containerView) {

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun mergePayloads(payloads: List<Any>): Set<String> {
            return if (payloads.size == 1)
                payloads[0] as Set<String>
            else {
                val diffs = mutableSetOf<String>()
                for (pay in payloads) diffs.addAll(pay as Set<String>)
                diffs
            }
        }
    }

    open lateinit var data: ITEM

    abstract fun render(data: ITEM)

    open fun render(data: ITEM, payloads: Set<String>) {}

    fun getString(@StringRes stringResId: Int): String = context.getString(stringResId)
    fun getString(@StringRes stringResId: Int, vararg formatArgs: Any): String = context.getString(stringResId, *formatArgs)


    fun isDataInitialised() = ::data.isInitialized

    fun bind(data: ITEM, payloads: Set<String>?) {
        this.data = data
        if (payloads == null) {
            render(data)
        } else {
            render(data, payloads)
        }
    }

    val context: Context = itemView.context
}

fun Any.equalObjects(a: Any?, b: Any?): Boolean {
    return if (a == null && b == null)
        true
    else if (a == null || b == null)
        false
    else if (a is CharSequence && b is CharSequence)
        a.toString() == b.toString()
    else a == b
}

fun Any.notEqualObjects(a: Any?, b: Any?) = !equalObjects(a, b)