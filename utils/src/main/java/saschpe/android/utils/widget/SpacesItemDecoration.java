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

package saschpe.android.utils.widget;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * SpacesItemDecoration is a {@link RecyclerView.ItemDecoration} that can be
 * used to add spacing between items of a {@link LinearLayoutManager}. It
 * supports both {@link #HORIZONTAL} and {@link #VERTICAL} orientations.
 *
 * <pre>
 * SpacesItemDecoration spacesItemDecoration = new SpacesItemDecoration(
 *         recyclerView.getContext(), 16, layoutManager.getOrientation());
 * recyclerView.addItemDecoration(spacesItemDecoration);
 * </pre>
 */
public final class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    public static final int HORIZONTAL = OrientationHelper.HORIZONTAL;
    public static final int VERTICAL = OrientationHelper.VERTICAL;

    private final int space;

    /**
     * Current orientation. Either {@link #HORIZONTAL} or {@link #VERTICAL}.
     */
    private int orientation;

    /**
     * Sets the orientation for this divider. This should be called if
     * {@link RecyclerView.LayoutManager} changes orientation.
     *
     * @param space Item spacing in PX
     * @param orientation {@link #HORIZONTAL} or {@link #VERTICAL}
     */
    public SpacesItemDecoration(final int space, final int orientation) {
        this.space = space;
        setOrientation(orientation);
    }

    /**
     * Sets the orientation for this divider. This should be called if
     * {@link RecyclerView.LayoutManager} changes orientation.
     *
     * @param orientation {@link #HORIZONTAL} or {@link #VERTICAL}
     */
    public void setOrientation(final int orientation) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw new IllegalArgumentException(
                    "Invalid orientation. It should be either HORIZONTAL or VERTICAL");
        }
        this.orientation = orientation;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (orientation == HORIZONTAL) {
            outRect.left = space;
            outRect.right = space;
        } else {
            outRect.top = space;
            outRect.bottom = space;
        }
    }
}