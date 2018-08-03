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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public abstract class ArrayAdapter<T, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {
    private List<T> objects;

    public ArrayAdapter(final List<T> objects) {
        if (objects == null) {
            this.objects = new ArrayList<>();
        } else {
            this.objects = objects;
        }
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public T getItem(final int position) {
        return objects.get(position);
    }

    public long getItemId(final int position) {
        return position;
    }

    /**
     * Returns the position of the specified item in the array.
     *
     * @param item The item to retrieve the position of.
     * @return The position of the specified item.
     */
    public int getPosition(final T item) {
        return objects.indexOf(item);
    }

    /**
     * Adds the specified object at the end of the array.
     *
     * @param object The object to add at the end of the array.
     */
    public boolean add(final T object) {
        objects.add(object);
        notifyItemInserted(getItemCount() - 1);
        return true;
    }

    /**
     * Inserts the specified object at the specified index in the array.
     *
     * @param position The index at which the object must be inserted.
     * @param object The object to insert into the array.
     */
    public void insert(int position, final T object) {
        objects.add(position, object);
        notifyItemInserted(position);
    }

    /**
     * Updates the specified object at the specified index in the array.
     *
     * @param position The index at which the object must be inserted.
     * @param object The object to insert into the array.
     */
    public T set(int position, final T object) {
        T old = objects.set(position, object);
        notifyItemChanged(position);
        return old;
    }

    /**
     * Replace the specified object at the specified index in the array.
     *
     * @param old The object to replace.
     * @param object The object to insert into the array.
     */
    public void replaceOrAdd(final T old, final T object) {
        final int position = getPosition(old);
        if (position >= 0) {
            objects.set(position, object);
            notifyItemChanged(position);
        } else {
            add(object);
        }
    }

    /**
     * Removes the specified object from the array.
     *
     * @param object The object to remove.
     */
    public boolean remove(final T object) {
        final int position = getPosition(object);
        if (position >= 0) {
            objects.remove(object);
            notifyItemRemoved(position);
            return true;
        }
        return false;
    }

    /**
     * Replace all elements with a new list.
     */
    public void replaceAll(final List<T> objects) {
        this.objects = objects;
        notifyDataSetChanged();
    }

    /**
     * Remove all elements from the list.
     */
    public void clear() {
        final int size = getItemCount();
        objects.clear();
        notifyItemRangeRemoved(0, size);
    }

    /**
     * Sorts the content of this adapter using the specified comparator.
     *
     * @param comparator The comparator used to sort the objects contained in this adapter.
     */
    public void sort(final Comparator<? super T> comparator) {
        Collections.sort(objects, comparator);
        notifyItemRangeChanged(0, getItemCount());
    }
}