package com.example.datepicker

import java.lang.UnsupportedOperationException
import java.util.LinkedHashMap

/**
 * Take advantage of LinkedHashMap's iterable ordering but also keep track of the indexes and allow
 * - Fast key lookup by index
 * - Fast index lookup by key
 */
internal class IndexedLinkedHashMap<K, V> : LinkedHashMap<K, V>() {
    private val indexToKey: MutableMap<Int, K> = LinkedHashMap()
    private val keyToIndex: MutableMap<K, Int> = LinkedHashMap()
    private var index = 0
    override fun put(key: K, value: V): V? {
        indexToKey[index] = key
        keyToIndex[key] = index
        index++
        return super.put(key, value)
    }

    override fun clear() {
        super.clear()
        index = 0
        indexToKey.clear()
        keyToIndex.clear()
    }

    fun getValueAtIndex(index: Int): V? {
        return get(indexToKey[index])
    }

    fun getIndexOfKey(key: K): Int {
        return keyToIndex[key]!!
    }
}