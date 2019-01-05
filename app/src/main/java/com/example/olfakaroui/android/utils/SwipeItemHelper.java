package com.example.olfakaroui.android.utils;


import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.olfakaroui.android.adapter.PendingCollabsAdapter;

public class SwipeItemHelper extends ItemTouchHelper.SimpleCallback {
    private SwipeItemHelperListener listener;

    public SwipeItemHelper(int dragDirs, int swipeDirs, SwipeItemHelperListener listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null) {
            final View foregroundView = ((PendingCollabsAdapter.ViewHolder) viewHolder).card;

            getDefaultUIUtil().onSelected(foregroundView);
            ((PendingCollabsAdapter.ViewHolder) viewHolder).denyView.setVisibility(View.GONE);
            ((PendingCollabsAdapter.ViewHolder) viewHolder).confirmView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((PendingCollabsAdapter.ViewHolder) viewHolder).card;
        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive);
        ((PendingCollabsAdapter.ViewHolder) viewHolder).denyView.setVisibility(View.GONE);
        ((PendingCollabsAdapter.ViewHolder) viewHolder).confirmView.setVisibility(View.VISIBLE);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final View foregroundView = ((PendingCollabsAdapter.ViewHolder) viewHolder).card;
        getDefaultUIUtil().clearView(foregroundView);
        ((PendingCollabsAdapter.ViewHolder) viewHolder).denyView.setVisibility(View.GONE);
        ((PendingCollabsAdapter.ViewHolder) viewHolder).confirmView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((PendingCollabsAdapter.ViewHolder) viewHolder).card;

        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    public interface SwipeItemHelperListener {
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }
}
