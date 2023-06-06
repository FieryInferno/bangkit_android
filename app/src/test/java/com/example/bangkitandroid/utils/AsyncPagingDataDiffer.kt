package com.example.bangkitandroid.utils

import androidx.annotation.IntRange
import androidx.annotation.MainThread
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.paging.*
import androidx.paging.LoadType.REFRESH
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.paging.DiffingChangePayload.ITEM_TO_PLACEHOLDER
import androidx.paging.DiffingChangePayload.PLACEHOLDER_POSITION_CHANGE
import androidx.paging.DiffingChangePayload.PLACEHOLDER_TO_ITEM
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView

class AsyncPagingDataDiffer<T : Any>
/**
 * Construct an [AsyncPagingDataDiffer].
 *
 * @param diffCallback Callback for calculating the diff between two non-disjoint lists on
 * [REFRESH]. Used as a fallback for item-level diffing when Paging is unable to find a faster
 * path for generating the UI events required to display the new list.
 * @param updateCallback [ListUpdateCallback] which receives UI events dispatched by this
 * [AsyncPagingDataDiffer] as items are loaded.
 * @param mainDispatcher [CoroutineContext] where UI events are dispatched. Typically, this should
 * be [Dispatchers.Main].
 * @param workerDispatcher [CoroutineContext] where the work to generate UI events is dispatched,
 * for example when diffing lists on [REFRESH]. Typically, this should dispatch on a background
 * thread; [Dispatchers.Default] by default.
 */
@JvmOverloads
constructor(
    private val diffCallback: DiffUtil.ItemCallback<T>,
    @Suppress("ListenerLast") // have to suppress for each, due to optional args
    private val updateCallback: ListUpdateCallback,
    @Suppress("ListenerLast") // have to suppress for each, due to optional args
    private val mainDispatcher: CoroutineContext = Dispatchers.Main,
    @Suppress("ListenerLast") // have to suppress for each, due to optional args
    private val workerDispatcher: CoroutineContext = Dispatchers.Default,
) {
    /**
     * Construct an [AsyncPagingDataDiffer].
     *
     * @param diffCallback Callback for calculating the diff between two non-disjoint lists on
     * [REFRESH]. Used as a fallback for item-level diffing when Paging is unable to find a faster
     * path for generating the UI events required to display the new list.
     * @param updateCallback [ListUpdateCallback] which receives UI events dispatched by this
     * [AsyncPagingDataDiffer] as items are loaded.
     * @param mainDispatcher [CoroutineDispatcher] where UI events are dispatched. Typically,
     * this should be [Dispatchers.Main].
     */
    @Deprecated(
        message = "Superseded by constructors which accept CoroutineContext",
        level = DeprecationLevel.HIDDEN
    )
    // Only for binary compatibility; cannot apply @JvmOverloads as the function signature would
    // conflict with the primary constructor.
    @Suppress("MissingJvmstatic")
    constructor(
        diffCallback: DiffUtil.ItemCallback<T>,
        @Suppress("ListenerLast") // have to suppress for each, due to optional args
        updateCallback: ListUpdateCallback,
        @Suppress("ListenerLast") // have to suppress for each, due to optional args
        mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
    ) : this(
        diffCallback = diffCallback,
        updateCallback = updateCallback,
        mainDispatcher = mainDispatcher,
        workerDispatcher = Dispatchers.Default
    )

    /**
     * Construct an [AsyncPagingDataDiffer].
     *
     * @param diffCallback Callback for calculating the diff between two non-disjoint lists on
     * [REFRESH]. Used as a fallback for item-level diffing when Paging is unable to find a faster
     * path for generating the UI events required to display the new list.
     * @param updateCallback [ListUpdateCallback] which receives UI events dispatched by this
     * [AsyncPagingDataDiffer] as items are loaded.
     * @param mainDispatcher [CoroutineDispatcher] where UI events are dispatched. Typically,
     * this should be [Dispatchers.Main].
     * @param workerDispatcher [CoroutineDispatcher] where the work to generate UI events is
     * dispatched, for example when diffing lists on [REFRESH]. Typically, this should dispatch on a
     * background thread; [Dispatchers.Default] by default.
     */
    @Deprecated(
        message = "Superseded by constructors which accept CoroutineContext",
        level = DeprecationLevel.HIDDEN
    )
    // Only for binary compatibility; cannot apply @JvmOverloads as the function signature would
    // conflict with the primary constructor.
    @Suppress("MissingJvmstatic")
    constructor(
        diffCallback: DiffUtil.ItemCallback<T>,
        @Suppress("ListenerLast") // have to suppress for each, due to optional args
        updateCallback: ListUpdateCallback,
        @Suppress("ListenerLast") // have to suppress for each, due to optional args
        mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
        @Suppress("ListenerLast") // have to suppress for each, due to optional args
        workerDispatcher: CoroutineDispatcher = Dispatchers.Default,
    ) : this(
        diffCallback = diffCallback,
        updateCallback = updateCallback,
        mainDispatcher = mainDispatcher,
        workerDispatcher = workerDispatcher
    )

    @Suppress("MemberVisibilityCanBePrivate") // synthetic access
    internal val differCallback = object : DifferCallback {
        override fun onInserted(position: Int, count: Int) {
            // Ignore if count == 0 as it makes this event a no-op.
            if (count > 0) {
                updateCallback.onInserted(position, count)
            }
        }

        override fun onRemoved(position: Int, count: Int) {
            // Ignore if count == 0 as it makes this event a no-op.
            if (count > 0) {
                updateCallback.onRemoved(position, count)
            }
        }

        override fun onChanged(position: Int, count: Int) {
            // Ignore if count == 0 as it makes this event a no-op.
            if (count > 0) {
                // NOTE: pass a null payload to convey null -> item, or item -> null
                updateCallback.onChanged(position, count, null)
            }
        }
    }

    /** True if we're currently executing [getItem] */
    @Suppress("MemberVisibilityCanBePrivate") // synthetic access
    internal var inGetItem: Boolean = false

    private val differBase = object : PagingDataDiffer<T>(differCallback, mainDispatcher) {
        override suspend fun presentNewList(
            previousList: NullPaddedList<T>,
            newList: NullPaddedList<T>,
            lastAccessedIndex: Int,
            onListPresentable: () -> Unit,
        ) = when {
            // fast path for no items -> some items
            previousList.size == 0 -> {
                onListPresentable()
                differCallback.onInserted(0, newList.size)
                null
            }
            // fast path for some items -> no items
            newList.size == 0 -> {
                onListPresentable()
                differCallback.onRemoved(0, previousList.size)
                null
            }
            else -> {
                val diffResult = withContext(workerDispatcher) {
                    previousList.computeDiff(newList, diffCallback)
                }
                onListPresentable()
                previousList.dispatchDiff(updateCallback, newList, diffResult)
                previousList.transformAnchorIndex(
                    diffResult = diffResult,
                    newList = newList,
                    oldPosition = lastAccessedIndex
                )
            }
        }

        /**
         * Return if [getItem] is running to post any data modifications.
         *
         * This must be done because RecyclerView can't be modified during an onBind, when
         * [getItem] is generally called.
         */
        override fun postEvents(): Boolean {
            return inGetItem
        }
    }

    private val submitDataId = AtomicInteger(0)

    /**
     * Present a [PagingData] until it is invalidated by a call to [refresh] or
     * [PagingSource.invalidate].
     *
     * This method is typically used when collecting from a [Flow][kotlinx.coroutines.flow.Flow]
     * produced by [Pager]. For RxJava or LiveData support, use the non-suspending overload of
     * [submitData], which accepts a [Lifecycle].
     *
     * Note: This method suspends while it is actively presenting page loads from a [PagingData],
     * until the [PagingData] is invalidated. Although cancellation will propagate to this call
     * automatically, collecting from a [Pager.flow] with the intention of presenting the most
     * up-to-date representation of your backing dataset should typically be done using
     * [collectLatest][kotlinx.coroutines.flow.collectLatest].
     *
     * @see [Pager]
     */
    suspend fun submitData(pagingData: PagingData<T>) {
        submitDataId.incrementAndGet()
        differBase.collectFrom(pagingData)
    }

    /**
     * Present a [PagingData] until it is either invalidated or another call to [submitData] is
     * made.
     *
     * This method is typically used when observing a RxJava or LiveData stream produced by [Pager].
     * For [Flow][kotlinx.coroutines.flow.Flow] support, use the suspending overload of
     * [submitData], which automates cancellation via
     * [CoroutineScope][kotlinx.coroutines.CoroutineScope] instead of relying of [Lifecycle].
     *
     * @see submitData
     * @see [Pager]
     */
    fun submitData(lifecycle: Lifecycle, pagingData: PagingData<T>) {
        val id = submitDataId.incrementAndGet()
        lifecycle.coroutineScope.launch {
            // Check id when this job runs to ensure the last synchronous call submitData always
            // wins.
            if (submitDataId.get() == id) {
                differBase.collectFrom(pagingData)
            }
        }
    }

    /**
     * Retry any failed load requests that would result in a [LoadState.Error] update to this
     * [AsyncPagingDataDiffer].
     *
     * Unlike [refresh], this does not invalidate [PagingSource], it only retries failed loads
     * within the same generation of [PagingData].
     *
     * [LoadState.Error] can be generated from two types of load requests:
     *  * [PagingSource.load] returning [PagingSource.LoadResult.Error]
     *  * [RemoteMediator.load] returning [RemoteMediator.MediatorResult.Error]
     */
    fun retry() {
        differBase.retry()
    }

    /**
     * Refresh the data presented by this [AsyncPagingDataDiffer].
     *
     * [refresh] triggers the creation of a new [PagingData] with a new instance of [PagingSource]
     * to represent an updated snapshot of the backing dataset. If a [RemoteMediator] is set,
     * calling [refresh] will also trigger a call to [RemoteMediator.load] with [LoadType] [REFRESH]
     * to allow [RemoteMediator] to check for updates to the dataset backing [PagingSource].
     *
     * Note: This API is intended for UI-driven refresh signals, such as swipe-to-refresh.
     * Invalidation due repository-layer signals, such as DB-updates, should instead use
     * [PagingSource.invalidate].
     *
     * @see PagingSource.invalidate
     *
     * @sample androidx.paging.samples.refreshSample
     */
    fun refresh() {
        differBase.refresh()
    }

    /**
     * Get the item from the current PagedList at the specified index.
     *
     * Note that this operates on both loaded items and null padding within the PagedList.
     *
     * @param index Index of item to get, must be >= 0, and < [itemCount]
     * @return The item, or `null`, if a `null` placeholder is at the specified position.
     */
    @MainThread
    fun getItem(@IntRange(from = 0) index: Int): T? {
        try {
            inGetItem = true
            return differBase[index]
        } finally {
            inGetItem = false
        }
    }

    /**
     * Returns the presented item at the specified position, without notifying Paging of the item
     * access that would normally trigger page loads.
     *
     * @param index Index of the presented item to return, including placeholders.
     * @return The presented item at position [index], `null` if it is a placeholder
     */
    @MainThread
    fun peek(@IntRange(from = 0) index: Int): T? {
        return differBase.peek(index)
    }

    /**
     * Returns a new [ItemSnapshotList] representing the currently presented items, including any
     * placeholders if they are enabled.
     */
    fun snapshot(): ItemSnapshotList<T> = differBase.snapshot()

    /**
     * Get the number of items currently presented by this Differ. This value can be directly
     * returned to [androidx.recyclerview.widget.RecyclerView.Adapter.getItemCount].
     *
     * @return Number of items being presented, including placeholders.
     */
    val itemCount: Int
        get() = differBase.size

    /**
     * A hot [Flow] of [CombinedLoadStates] that emits a snapshot whenever the loading state of the
     * current [PagingData] changes.
     *
     * This flow is conflated, so it buffers the last update to [CombinedLoadStates] and
     * immediately delivers the current load states on collection.
     *
     * @sample androidx.paging.samples.loadStateFlowSample
     */
    val loadStateFlow: Flow<CombinedLoadStates> = differBase.loadStateFlow.filterNotNull()

    /**
     * A hot [Flow] that emits after the pages presented to the UI are updated, even if the
     * actual items presented don't change.
     *
     * An update is triggered from one of the following:
     *   * [submitData] is called and initial load completes, regardless of any differences in
     *     the loaded data
     *   * A [Page][androidx.paging.PagingSource.LoadResult.Page] is inserted
     *   * A [Page][androidx.paging.PagingSource.LoadResult.Page] is dropped
     *
     * Note: This is a [SharedFlow][kotlinx.coroutines.flow.SharedFlow] configured to replay
     * 0 items with a buffer of size 64. If a collector lags behind page updates, it may
     * trigger multiple times for each intermediate update that was presented while your collector
     * was still working. To avoid this behavior, you can
     * [conflate][kotlinx.coroutines.flow.conflate] this [Flow] so that you only receive the latest
     * update, which is useful in cases where you are simply updating UI and don't care about
     * tracking the exact number of page updates.
     */
    val onPagesUpdatedFlow: Flow<Unit> = differBase.onPagesUpdatedFlow

    /**
     * Add a listener which triggers after the pages presented to the UI are updated, even if the
     * actual items presented don't change.
     *
     * An update is triggered from one of the following:
     *   * [submitData] is called and initial load completes, regardless of any differences in
     *     the loaded data
     *   * A [Page][androidx.paging.PagingSource.LoadResult.Page] is inserted
     *   * A [Page][androidx.paging.PagingSource.LoadResult.Page] is dropped
     *
     * @param listener called after pages presented are updated.
     *
     * @see removeOnPagesUpdatedListener
     */
    fun addOnPagesUpdatedListener(listener: () -> Unit) {
        differBase.addOnPagesUpdatedListener(listener)
    }

    /**
     * Remove a previously registered listener for new [PagingData] generations completing
     * initial load and presenting to the UI.
     *
     * @param listener Previously registered listener.
     *
     * @see addOnPagesUpdatedListener
     */
    fun removeOnPagesUpdatedListener(listener: () -> Unit) {
        differBase.removeOnPagesUpdatedListener(listener)
    }

    /**
     * Add a [CombinedLoadStates] listener to observe the loading state of the current [PagingData].
     *
     * As new [PagingData] generations are submitted and displayed, the listener will be notified to
     * reflect the current [CombinedLoadStates].
     *
     * @param listener [LoadStates] listener to receive updates.
     *
     * @see removeLoadStateListener
     *
     * @sample androidx.paging.samples.addLoadStateListenerSample
     */
    fun addLoadStateListener(listener: (CombinedLoadStates) -> Unit) {
        differBase.addLoadStateListener(listener)
    }

    /**
     * Remove a previously registered [CombinedLoadStates] listener.
     *
     * @param listener Previously registered listener.
     * @see addLoadStateListener
     */
    fun removeLoadStateListener(listener: (CombinedLoadStates) -> Unit) {
        differBase.removeLoadStateListener(listener)
    }

    private companion object {
        init {
            /**
             * Implements the Logger interface from paging-common and injects it into the LOGGER
             * global var stored within Pager.
             *
             * Checks for null LOGGER because paging-compose can also inject a Logger
             * with the same implementation
             */
        }
    }
}

/**
 * Methods for computing and applying DiffResults between PagedLists.
 *
 * To minimize the amount of diffing caused by placeholders, we only execute DiffUtil in a reduced
 * 'diff space' - in the range (computeLeadingNulls..size-computeTrailingNulls).
 *
 * This allows the diff of a PagedList, e.g.:
 * 100 nulls, placeholder page, (empty page) x 5, page, 100 nulls
 *
 * To only inform DiffUtil about single loaded page in this case, by pruning all other nulls from
 * consideration.
 */
internal fun <T : Any> NullPaddedList<T>.computeDiff(
    newList: NullPaddedList<T>,
    diffCallback: DiffUtil.ItemCallback<T>
): NullPaddedDiffResult {
    val oldSize = storageCount
    val newSize = newList.storageCount

    val diffResult = DiffUtil.calculateDiff(
        object : DiffUtil.Callback() {
            override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
                val oldItem = getFromStorage(oldItemPosition)
                val newItem = newList.getFromStorage(newItemPosition)

                return when {
                    oldItem === newItem -> true
                    else -> diffCallback.getChangePayload(oldItem, newItem)
                }
            }

            override fun getOldListSize() = oldSize

            override fun getNewListSize() = newSize

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = getFromStorage(oldItemPosition)
                val newItem = newList.getFromStorage(newItemPosition)

                return when {
                    oldItem === newItem -> true
                    else -> diffCallback.areItemsTheSame(oldItem, newItem)
                }
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = getFromStorage(oldItemPosition)
                val newItem = newList.getFromStorage(newItemPosition)

                return when {
                    oldItem === newItem -> true
                    else -> diffCallback.areContentsTheSame(oldItem, newItem)
                }
            }
        },
        true
    )
    // find first overlap
    val hasOverlap = (0 until storageCount).any {
        diffResult.convertOldPositionToNew(it) != RecyclerView.NO_POSITION
    }
    return NullPaddedDiffResult(
        diff = diffResult,
        hasOverlap = hasOverlap
    )
}

/**
 * See NullPaddedDiffing.md for how this works and why it works that way :).
 *
 * Note: if lists mutate between diffing the snapshot and dispatching the diff here, then we
 * handle this by passing the snapshot to the callback, and dispatching those changes
 * immediately after dispatching this diff.
 */
internal fun <T : Any> NullPaddedList<T>.dispatchDiff(
    callback: ListUpdateCallback,
    newList: NullPaddedList<T>,
    diffResult: NullPaddedDiffResult
) {
    if (diffResult.hasOverlap) {
        OverlappingListsDiffDispatcher.dispatchDiff(
            oldList = this,
            newList = newList,
            callback = callback,
            diffResult = diffResult,
        )
    } else {
        // if no values overlapped between two lists, use change with payload *unless* the
        // position represents real items in both old and new lists in which case *change* would
        // be misleading hence we need to dispatch add/remove.
        DistinctListsDiffDispatcher.dispatchDiff(
            callback = callback,
            oldList = this,
            newList = newList,
        )
    }
}

/**
 * Given an oldPosition representing an anchor in the old data set, computes its new position
 * after the diff, or a guess if it no longer exists.
 */
internal fun NullPaddedList<*>.transformAnchorIndex(
    diffResult: NullPaddedDiffResult,
    newList: NullPaddedList<*>,
    oldPosition: Int
): Int {
    if (!diffResult.hasOverlap) {
        // if lists didn't overlap, use old position
        return oldPosition.coerceIn(0 until newList.size)
    }
    // diffResult's indices starting after nulls, need to transform to diffutil indices
    // (see also dispatchDiff(), which adds this offset when dispatching)
    val diffIndex = oldPosition - placeholdersBefore

    val oldSize = storageCount

    // if our anchor is non-null, use it or close item's position in new list
    if (diffIndex in 0 until oldSize) {
        // search outward from old position for position that maps
        for (i in 0..29) {
            val positionToTry = diffIndex + i / 2 * if (i % 2 == 1) -1 else 1

            // reject if (null) item was not passed to DiffUtil, and wouldn't be in the result
            if (positionToTry < 0 || positionToTry >= storageCount) {
                continue
            }

            val result = diffResult.diff.convertOldPositionToNew(positionToTry)
            if (result != -1) {
                // also need to transform from diffutil output indices to newList
                return result + newList.placeholdersBefore
            }
        }
    }

    // not anchored to an item in new list, so just reuse position (clamped to newList size)
    return oldPosition.coerceIn(0 until newList.size)
}

internal class NullPaddedDiffResult(
    val diff: DiffUtil.DiffResult,
    // true if two lists have at least 1 item the same
    val hasOverlap: Boolean
)

/**
 * Helper class to implement the heuristic documented in NullPaddedDiffing.md.
 */
internal object OverlappingListsDiffDispatcher {
    fun <T> dispatchDiff(
        oldList: NullPaddedList<T>,
        newList: NullPaddedList<T>,
        callback: ListUpdateCallback,
        diffResult: NullPaddedDiffResult
    ) {
        val callbackWrapper = PlaceholderUsingUpdateCallback(
            oldList = oldList,
            newList = newList,
            callback = callback
        )
        diffResult.diff.dispatchUpdatesTo(callbackWrapper)
        callbackWrapper.fixPlaceholders()
    }

    @Suppress("NOTHING_TO_INLINE")
    private class PlaceholderUsingUpdateCallback<T>(
        private val oldList: NullPaddedList<T>,
        private val newList: NullPaddedList<T>,
        private val callback: ListUpdateCallback
    ) : ListUpdateCallback {
        // These variables hold the "current" value for placeholders and storage count and are
        // updated as we dispatch notify events to `callback`.
        private var placeholdersBefore = oldList.placeholdersBefore
        private var placeholdersAfter = oldList.placeholdersAfter
        private var storageCount = oldList.storageCount

        // Track if we used placeholders for a certain case to avoid using them for both additions
        // and removals at the same time, which might end up sending misleading change events.
        private var placeholdersBeforeState = UNUSED
        private var placeholdersAfterState = UNUSED

        /**
         * Offsets a value based on placeholders to make it suitable to pass into the callback.
         */
        private inline fun Int.offsetForDispatch() = this + placeholdersBefore

        fun fixPlaceholders() {
            // add / remove placeholders to match the new list
            fixLeadingPlaceholders()
            fixTrailingPlaceholders()
        }

        private fun fixTrailingPlaceholders() {
            // the #of placeholders that didn't have any updates. We might need to send position
            // change events for them if their original positions are no longer valid.
            var unchangedPlaceholders = minOf(oldList.placeholdersAfter, placeholdersAfter)

            val postPlaceholdersToAdd = newList.placeholdersAfter - placeholdersAfter
            val runningListSize = placeholdersBefore + storageCount + placeholdersAfter
            // check if unchanged placeholders changed their positions between two lists
            val unchangedPlaceholdersStartPos = runningListSize - unchangedPlaceholders
            val unchangedPlaceholdersMoved =
                unchangedPlaceholdersStartPos != (oldList.size - unchangedPlaceholders)
            if (postPlaceholdersToAdd > 0) {
                // always add to the end of the list
                callback.onInserted(runningListSize, postPlaceholdersToAdd)
            } else if (postPlaceholdersToAdd < 0) {
                // always remove from the end
                // notice that postPlaceholdersToAdd is negative, thats why it is added to
                // runningListEnd
                callback.onRemoved(
                    runningListSize + postPlaceholdersToAdd,
                    -postPlaceholdersToAdd
                )
                // remove them from unchanged placeholders, notice that it is an addition because
                // postPlaceholdersToAdd is negative
                unchangedPlaceholders += postPlaceholdersToAdd
            }
            if (unchangedPlaceholders > 0 && unchangedPlaceholdersMoved) {
                // These placeholders didn't get any change event yet their list positions changed.
                // We should send an update as the position of a placeholder is part of its data.
                callback.onChanged(
                    unchangedPlaceholdersStartPos,
                    unchangedPlaceholders,
                    PLACEHOLDER_POSITION_CHANGE
                )
            }
            placeholdersAfter = newList.placeholdersAfter
        }

        private fun fixLeadingPlaceholders() {
            // the #of placeholders that didn't have any updates. We might need to send position
            // change events if we further modify the list.
            val unchangedPlaceholders = minOf(oldList.placeholdersBefore, placeholdersBefore)
            val prePlaceholdersToAdd = newList.placeholdersBefore - placeholdersBefore
            if (prePlaceholdersToAdd > 0) {
                if (unchangedPlaceholders > 0) {
                    // these will be shifted down so send a change event for them
                    callback.onChanged(0, unchangedPlaceholders, PLACEHOLDER_POSITION_CHANGE)
                }
                // always insert to the beginning of the list
                callback.onInserted(0, prePlaceholdersToAdd)
            } else if (prePlaceholdersToAdd < 0) {
                // always remove from the beginning of the list
                callback.onRemoved(0, -prePlaceholdersToAdd)
                if (unchangedPlaceholders + prePlaceholdersToAdd > 0) {
                    // these have been shifted up, send a change event for them. We add the negative
                    // number of `prePlaceholdersToAdd` not to send change events for them
                    callback.onChanged(
                        0, unchangedPlaceholders + prePlaceholdersToAdd,
                        PLACEHOLDER_POSITION_CHANGE
                    )
                }
            }
            placeholdersBefore = newList.placeholdersBefore
        }

        override fun onInserted(position: Int, count: Int) {
            when {
                dispatchInsertAsPlaceholderAfter(position, count) -> {
                    // dispatched as placeholders after
                }
                dispatchInsertAsPlaceholderBefore(position, count) -> {
                    // dispatched as placeholders before
                }
                else -> {
                    // not at the edge, dispatch as usual
                    callback.onInserted(position.offsetForDispatch(), count)
                }
            }
            storageCount += count
        }

        /**
         * Return true if it is dispatched, false otherwise.
         */
        private fun dispatchInsertAsPlaceholderBefore(position: Int, count: Int): Boolean {
            if (position > 0) {
                return false // not at the edge
            }
            if (placeholdersBeforeState == USED_FOR_REMOVAL) {
                return false
            }
            val asPlaceholderChange = minOf(count, placeholdersBefore)
            if (asPlaceholderChange > 0) {
                placeholdersBeforeState = USED_FOR_ADDITION
                // this index is negative because we are going back. offsetForDispatch will fix it
                val index = (0 - asPlaceholderChange)
                callback.onChanged(
                    index.offsetForDispatch(), asPlaceholderChange, PLACEHOLDER_TO_ITEM
                )
                placeholdersBefore -= asPlaceholderChange
            }
            val asInsert = count - asPlaceholderChange
            if (asInsert > 0) {
                callback.onInserted(
                    0.offsetForDispatch(), asInsert
                )
            }
            return true
        }

        /**
         * Return true if it is dispatched, false otherwise.
         */
        private fun dispatchInsertAsPlaceholderAfter(position: Int, count: Int): Boolean {
            if (position < storageCount) {
                return false // not at the edge
            }
            if (placeholdersAfterState == USED_FOR_REMOVAL) {
                return false
            }
            val asPlaceholderChange = minOf(count, placeholdersAfter)
            if (asPlaceholderChange > 0) {
                placeholdersAfterState = USED_FOR_ADDITION
                callback.onChanged(
                    position.offsetForDispatch(), asPlaceholderChange, PLACEHOLDER_TO_ITEM
                )
                placeholdersAfter -= asPlaceholderChange
            }
            val asInsert = count - asPlaceholderChange
            if (asInsert > 0) {
                callback.onInserted(
                    (position + asPlaceholderChange).offsetForDispatch(), asInsert
                )
            }
            return true
        }

        override fun onRemoved(position: Int, count: Int) {
            when {
                dispatchRemovalAsPlaceholdersAfter(position, count) -> {
                    // dispatched as changed into placeholder
                }
                dispatchRemovalAsPlaceholdersBefore(position, count) -> {
                    // dispatched as changed into placeholder
                }
                else -> {
                    // fallback, need to handle here
                    callback.onRemoved(position.offsetForDispatch(), count)
                }
            }
            storageCount -= count
        }

        /**
         * Return true if it is dispatched, false otherwise.
         */
        private fun dispatchRemovalAsPlaceholdersBefore(position: Int, count: Int): Boolean {
            if (position > 0) {
                return false
            }
            if (placeholdersBeforeState == USED_FOR_ADDITION) {
                return false
            }
            // see how many removals we can convert to change.
            // make sure we don't end up having too many placeholders that we'll end up removing
            // anyways
            val maxPlaceholdersToAdd = newList.placeholdersBefore - placeholdersBefore
            val asPlaceholders = minOf(maxPlaceholdersToAdd, count).coerceAtLeast(0)
            val asRemoval = count - asPlaceholders
            // first remove then use placeholders to make sure items that are closer to the loaded
            // content center are more likely to stay in the list
            if (asRemoval > 0) {
                callback.onRemoved(0.offsetForDispatch(), asRemoval)
            }
            if (asPlaceholders > 0) {
                placeholdersBeforeState = USED_FOR_REMOVAL
                callback.onChanged(
                    0.offsetForDispatch(),
                    asPlaceholders,
                    ITEM_TO_PLACEHOLDER
                )
                placeholdersBefore += asPlaceholders
            }
            return true
        }

        /**
         * Return true if it is dispatched, false otherwise.
         */
        private fun dispatchRemovalAsPlaceholdersAfter(position: Int, count: Int): Boolean {
            val end = position + count
            if (end < storageCount) {
                return false // not at the edge
            }
            if (placeholdersAfterState == USED_FOR_ADDITION) {
                return false
            }
            // see how many removals we can convert to change.
            // make sure we don't end up having too many placeholders that we'll end up removing
            // anyways
            val maxPlaceholdersToAdd = newList.placeholdersAfter - placeholdersAfter
            val asPlaceholders = minOf(maxPlaceholdersToAdd, count).coerceAtLeast(0)
            val asRemoval = count - asPlaceholders
            // first use placeholders then removal to make sure items that are closer to
            // the loaded content center are more likely to stay in the list
            if (asPlaceholders > 0) {
                placeholdersAfterState = USED_FOR_REMOVAL
                callback.onChanged(
                    position.offsetForDispatch(),
                    asPlaceholders,
                    ITEM_TO_PLACEHOLDER
                )
                placeholdersAfter += asPlaceholders
            }
            if (asRemoval > 0) {
                callback.onRemoved((position + asPlaceholders).offsetForDispatch(), asRemoval)
            }
            return true
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            callback.onMoved(fromPosition.offsetForDispatch(), toPosition.offsetForDispatch())
        }

        override fun onChanged(position: Int, count: Int, payload: Any?) {
            callback.onChanged(position.offsetForDispatch(), count, payload)
        }

        companion object {
            // markers for edges to avoid using them for both additions and removals
            private const val UNUSED = 1
            private const val USED_FOR_REMOVAL = UNUSED + 1
            private const val USED_FOR_ADDITION = USED_FOR_REMOVAL + 1
        }
    }
}

/**
 * Helper object to dispatch diffs when two lists do not overlap at all.
 *
 * We try to send change events when an item's position is replaced with a placeholder or vice
 * versa.
 * If there is an item in a given position in before and after lists, we dispatch add/remove for
 * them not to trigger unexpected change animations.
 */
internal object DistinctListsDiffDispatcher {
    fun <T : Any> dispatchDiff(
        callback: ListUpdateCallback,
        oldList: NullPaddedList<T>,
        newList: NullPaddedList<T>,
    ) {
        val storageOverlapStart = maxOf(
            oldList.placeholdersBefore, newList.placeholdersBefore
        )
        val storageOverlapEnd = minOf(
            oldList.placeholdersBefore + oldList.storageCount,
            newList.placeholdersBefore + newList.storageCount
        )
        // we need to dispatch add/remove for overlapping storage positions
        val overlappingStorageSize = storageOverlapEnd - storageOverlapStart
        if (overlappingStorageSize > 0) {
            callback.onRemoved(storageOverlapStart, overlappingStorageSize)
            callback.onInserted(storageOverlapStart, overlappingStorageSize)
        }
        // now everything else is good as a change animation.
        // make sure to send a change for old items whose positions are still in the list
        // to handle cases where there is no overlap, we min/max boundaries
        val changeEventStartBoundary = minOf(storageOverlapStart, storageOverlapEnd)
        val changeEventEndBoundary = maxOf(storageOverlapStart, storageOverlapEnd)
        dispatchChange(
            callback = callback,
            startBoundary = changeEventStartBoundary,
            endBoundary = changeEventEndBoundary,
            start = oldList.placeholdersBefore.coerceAtMost(newList.size),
            end = (oldList.placeholdersBefore + oldList.storageCount).coerceAtMost(newList.size),
            payload = ITEM_TO_PLACEHOLDER
        )
        // now for new items that were mapping to placeholders, send change events
        dispatchChange(
            callback = callback,
            startBoundary = changeEventStartBoundary,
            endBoundary = changeEventEndBoundary,
            start = newList.placeholdersBefore.coerceAtMost(oldList.size),
            end = (newList.placeholdersBefore + newList.storageCount).coerceAtMost(oldList.size),
            payload = PLACEHOLDER_TO_ITEM
        )
        // finally, fix the size
        val itemsToAdd = newList.size - oldList.size
        if (itemsToAdd > 0) {
            callback.onInserted(oldList.size, itemsToAdd)
        } else if (itemsToAdd < 0) {
            callback.onRemoved(oldList.size + itemsToAdd, -itemsToAdd)
        }
    }

    private fun dispatchChange(
        callback: ListUpdateCallback,
        startBoundary: Int,
        endBoundary: Int,
        start: Int,
        end: Int,
        payload: Any
    ) {
        val beforeOverlapCount = startBoundary - start
        if (beforeOverlapCount > 0) {
            callback.onChanged(start, beforeOverlapCount, payload)
        }
        val afterOverlapCount = end - endBoundary
        if (afterOverlapCount > 0) {
            callback.onChanged(endBoundary, afterOverlapCount, payload)
        }
    }
}
