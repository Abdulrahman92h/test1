package com.example.ayatbahaa.waveapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

public class PersonItemCallback extends ItemTouchHelper.SimpleCallback {
    private Listener mListener;

    public interface Listener{
        void deletePerson(int position);
        void editPerson(int position);
    }

    public PersonItemCallback(int dragDirs, int swipeDirs, Listener listener) {
        super(dragDirs, swipeDirs);
        mListener= listener;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position= viewHolder.getAdapterPosition();
        if(direction == ItemTouchHelper.RIGHT){
            // edit orphan
            mListener.editPerson(position);

        }else if(direction == ItemTouchHelper.LEFT){
            // delete orphan
            mListener.deletePerson(position);
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
            View itemView= viewHolder.itemView;

            float iconWidth= itemView.getHeight()/2;
            RectF background;
            RectF iconRect;
            Paint paint= new Paint();
            Bitmap icon;

            if(dX > 0){
                // setup swipe background color
                paint.setColor(ContextCompat.getColor(itemView.getContext(), R.color.swipeEdit));
                background= new RectF(itemView.getLeft(), itemView.getTop(), dX, itemView.getBottom());
                c.drawRoundRect(background,6, 6, paint);

                // setup swipe background icon
                iconRect= new RectF( 20,
                        itemView.getTop() + iconWidth/2,
                        20 + iconWidth,
                        itemView.getBottom() - iconWidth/2 );
                icon= Util.getBitmapFromVectorDrawable(itemView.getContext(), R.drawable.ic_edit_white_24dp);
                c.drawBitmap(icon, null, iconRect, null);

            }else if(dX < 0){
                // setup swipe background color
                paint.setColor(ContextCompat.getColor(itemView.getContext(), R.color.swipeDelete));
                ConstraintLayout itemLayout= viewHolder.itemView.findViewById(R.id.itemLayout);
                background= new RectF(itemLayout.getRight() + dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                c.drawRoundRect(background,6, 6, paint);

                // setup swipe background icon
                iconRect= new RectF(itemView.getRight() - (20 + iconWidth),
                        itemView.getTop() + iconWidth/2,
                        itemView.getRight() - 20,
                        itemView.getBottom() - iconWidth/2 );
                icon= Util.getBitmapFromVectorDrawable(itemView.getContext(), R.drawable.ic_delete_white_24dp);
                c.drawBitmap(icon, null, iconRect, null);
            }
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
