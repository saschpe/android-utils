/*
 * Copyright 2016 Sascha Peilicke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package saschpe.android.utils.adapter.base;

import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Handler;
import android.widget.Filter;
import android.widget.FilterQueryProvider;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Provide a {@link RecyclerView.Adapter}
 * implementation with cursor support.
 *
 * Child classes only need to implement {@link #onCreateViewHolder(android.view.ViewGroup, int)} and
 * {@link #onBindViewHolderCursor( RecyclerView.ViewHolder, Cursor)}.
 *
 * This class does not implement deprecated fields and methods from CursorAdapter! Incidentally,
 * only {@link android.widget.CursorAdapter#FLAG_REGISTER_CONTENT_OBSERVER} is available, so the
 * flag is implied, and only the Adapter behavior using this flag has been ported.
 *
 * @param <VH> {@inheritDoc}
 *
 * @see RecyclerView.Adapter
 * @see android.widget.CursorAdapter
 * @see Filterable
 */
public abstract class CursorRecyclerAdapter<VH
        extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH>
        implements Filterable, CursorFilter.CursorFilterClient {
    private boolean dataValid;
    private int rowIDColumn;
    private Cursor cursor;
    private ChangeObserver changeObserver;
    private DataSetObserver dataSetObserver;
    private CursorFilter cursorFilter;
    private FilterQueryProvider filterQueryProvider;

    public CursorRecyclerAdapter(final Cursor cursor) {
        init(cursor);
    }

    protected void init(final Cursor cursor) {
        boolean cursorPresent = cursor != null;
        this.cursor = cursor;
        dataValid = cursorPresent;
        rowIDColumn = cursorPresent ? cursor.getColumnIndexOrThrow("_id") : -1;

        changeObserver = new ChangeObserver();
        dataSetObserver = new MyDataSetObserver();

        if (cursorPresent) {
            if (changeObserver != null) cursor.registerContentObserver(changeObserver);
            if (dataSetObserver != null) cursor.registerDataSetObserver(dataSetObserver);
        }
    }

    /**
     * This method will move the Cursor to the correct position and call
     * {@link #onBindViewHolderCursor( RecyclerView.ViewHolder,
     * Cursor)}.
     *
     * @param holder {@inheritDoc}
     * @param i {@inheritDoc}
     */
    @Override
    public void onBindViewHolder(@NonNull VH holder, int i){
        if (!dataValid) {
            throw new IllegalStateException("this should only be called when the cursor is valid");
        }
        if (!cursor.moveToPosition(i)) {
            throw new IllegalStateException("couldn't move cursor to position " + i);
        }
        onBindViewHolderCursor(holder, cursor);
    }

    /**
     * See {@link android.widget.CursorAdapter#bindView(android.view.View, android.content.Context,
     * Cursor)},
     * {@link #onBindViewHolder( RecyclerView.ViewHolder, int)}
     *
     * @param holder View holder.
     * @param cursor The cursor from which to get the data. The cursor is already
     * moved to the correct position.
     */
    public abstract void onBindViewHolderCursor(VH holder, Cursor cursor);

    @Override
    public int getItemCount() {
        if (dataValid && cursor != null) {
            return cursor.getCount();
        } else {
            return 0;
        }
    }

    /**
     * @see android.widget.ListAdapter#getItemId(int)
     */
    @Override
    public long getItemId(int position) {
        if (dataValid && cursor != null) {
            if (cursor.moveToPosition(position)) {
                return cursor.getLong(rowIDColumn);
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public Cursor getCursor(){
        return cursor;
    }

    /**
     * Change the underlying cursor to a new cursor. If there is an existing cursor it will be
     * closed.
     *
     * @param cursor The new cursor to be used
     */
    public void changeCursor(final Cursor cursor) {
        Cursor old = swapCursor(cursor);
        if (old != null) {
            old.close();
        }
    }

    /**
     * Swap in a new Cursor, returning the old Cursor.  Unlike
     * {@link #changeCursor(Cursor)}, the returned old Cursor is <em>not</em>
     * closed.
     *
     * @param newCursor The new cursor to be used.
     * @return Returns the previously set Cursor, or null if there wasa not one.
     * If the given new Cursor is the same instance is the previously set
     * Cursor, null is also returned.
     */
    public Cursor swapCursor(final Cursor newCursor) {
        if (newCursor == cursor) {
            return null;
        }
        Cursor oldCursor = cursor;
        if (oldCursor != null) {
            if (changeObserver != null) oldCursor.unregisterContentObserver(changeObserver);
            if (dataSetObserver != null) oldCursor.unregisterDataSetObserver(dataSetObserver);
        }
        cursor = newCursor;
        if (newCursor != null) {
            if (changeObserver != null) newCursor.registerContentObserver(changeObserver);
            if (dataSetObserver != null) newCursor.registerDataSetObserver(dataSetObserver);
            rowIDColumn = newCursor.getColumnIndexOrThrow("_id");
            dataValid = true;
            // notify the observers about the new cursor
            notifyDataSetChanged();
        } else {
            rowIDColumn = -1;
            dataValid = false;
            // notify the observers about the lack of a data set
            // notifyDataSetInvalidated();
            notifyItemRangeRemoved(0, getItemCount());
        }
        return oldCursor;
    }

    /**
     * <p>Converts the cursor into a CharSequence. Subclasses should override this
     * method to convert their results. The default implementation returns an
     * empty String for null values or the default String representation of
     * the value.</p>
     *
     * @param cursor the cursor to convert to a CharSequence
     * @return a CharSequence representing the value
     */
    public CharSequence convertToString(final Cursor cursor) {
        return cursor == null ? "" : cursor.toString();
    }

    /**
     * Runs a query with the specified constraint. This query is requested
     * by the filter attached to this adapter.
     *
     * The query is provided by a
     * {@link FilterQueryProvider}.
     * If no provider is specified, the current cursor is not filtered and returned.
     *
     * After this method returns the resulting cursor is passed to {@link #changeCursor(Cursor)}
     * and the previous cursor is closed.
     *
     * This method is always executed on a background thread, not on the
     * application's main thread (or UI thread.)
     *
     * Contract: when constraint is null or empty, the original results,
     * prior to any filtering, must be returned.
     *
     * @param constraint the constraint with which the query must be filtered
     *
     * @return a Cursor representing the results of the new query
     *
     * @see #getFilter()
     * @see #getFilterQueryProvider()
     * @see #setFilterQueryProvider(FilterQueryProvider)
     */
    public Cursor runQueryOnBackgroundThread(final CharSequence constraint) {
        if (filterQueryProvider != null) {
            return filterQueryProvider.runQuery(constraint);
        }

        return cursor;
    }

    public Filter getFilter() {
        if (cursorFilter == null) {
            cursorFilter = new CursorFilter(this);
        }
        return cursorFilter;
    }

    /**
     * Returns the query filter provider used for filtering. When the
     * provider is null, no filtering occurs.
     *
     * @return the current filter query provider or null if it does not exist
     *
     * @see #setFilterQueryProvider(FilterQueryProvider)
     * @see #runQueryOnBackgroundThread(CharSequence)
     */
    public FilterQueryProvider getFilterQueryProvider() {
        return filterQueryProvider;
    }

    /**
     * Sets the query filter provider used to filter the current Cursor.
     * The provider's
     * {@link FilterQueryProvider#runQuery(CharSequence)}
     * method is invoked when filtering is requested by a client of
     * this adapter.
     *
     * @param filterQueryProvider the filter query provider or null to remove it
     *
     * @see #getFilterQueryProvider()
     * @see #runQueryOnBackgroundThread(CharSequence)
     */
    public void setFilterQueryProvider(FilterQueryProvider filterQueryProvider) {
        this.filterQueryProvider = filterQueryProvider;
    }

    /**
     * Called when the {@link ContentObserver} on the cursor receives a change notification.
     * Can be implemented by sub-class.
     *
     * @see ContentObserver#onChange(boolean)
     */
    protected void onContentChanged() {
    }

    private final class ChangeObserver extends ContentObserver {
        public ChangeObserver() {
            super(new Handler());
        }

        @Override
        public boolean deliverSelfNotifications() {
            return true;
        }

        @Override
        public void onChange(boolean selfChange) {
            onContentChanged();
        }
    }

    private final class MyDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            dataValid = true;
            notifyDataSetChanged();
        }

        @Override
        public void onInvalidated() {
            dataValid = false;
            // notifyDataSetInvalidated();
            notifyItemRangeRemoved(0, getItemCount());
        }
    }
}
