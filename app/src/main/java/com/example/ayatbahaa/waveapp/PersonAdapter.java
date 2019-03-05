package com.example.ayatbahaa.waveapp;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ayatbahaa.waveapp.database.Person;

import java.util.List;
import java.util.Locale;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {
    private List<Person> mPersonList;
    private PersonActivity mPersonActivity;

    public PersonAdapter(List<Person> personList, PersonActivity mPersonActivity) {
        mPersonList = personList;
        this.mPersonActivity= mPersonActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orphan_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Person person = mPersonList.get(position);
        String age;

        if (Locale.getDefault().getDisplayLanguage().equals("ar")) {
            age = holder.itemView.getResources().getString(R.string.years_old) + " " + person.getAge();
        } else {
            age = person.getAge() + " " + holder.itemView.getResources().getString(R.string.years_old);
        }
        if (person.getPictureUri() != null) {
            Glide.with(holder.itemView.getContext())
                    .load(person.getPictureUri())
                    .into(holder.mPersonalImage);

        } else if (person.getPictureUrl() != null) {
            TextView backgroundLabel = holder.itemView.findViewById(R.id.noImageLabel);
            backgroundLabel.setText(R.string.loading);
            backgroundLabel.setTextSize(10);


            Glide
                    .with(holder.itemView.getContext().getApplicationContext())
                    .load(person.getPictureUrl())
                    .into(holder.mPersonalImage);

        }

        if (person.getName() == null || person.getName().isEmpty()) {
            holder.mName.setText(R.string.no_name);
        } else {
            holder.mName.setText(person.getName());
        }
        holder.mAge.setText(age);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = mPersonActivity.getProfileIntent(person);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    Pair<View, String>[] pairs = new Pair[3];

                    View cardView = holder.mImageFrame;
                    View name = holder.mName;
                    View age = holder.mAge;

                    pairs[0] = new Pair<>(cardView,
                            holder.itemView.getContext().getString(R.string.personal_image_transition));
                    pairs[1] = new Pair<>(name,
                            holder.itemView.getContext().getString(R.string.name_transition));
                    pairs[2] = new Pair<>(age,
                            holder.itemView.getContext().getString(R.string.age_transition));

                    ActivityOptions ao = ActivityOptions.makeSceneTransitionAnimation((Activity) holder.itemView.getContext(), pairs);
                    holder.itemView.getContext().startActivity(intent, ao.toBundle());
                    return;
                }

                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPersonList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mPersonalImage;
        private TextView mName;
        private TextView mAge;
        private CardView mImageFrame;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mPersonalImage = itemView.findViewById(R.id.personalImage);
            mName = itemView.findViewById(R.id.name);
            mAge = itemView.findViewById(R.id.age);
            mImageFrame = itemView.findViewById(R.id.imageFrame);
        }

    }
}
