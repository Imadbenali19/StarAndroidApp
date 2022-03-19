package com.example.appstar.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.appstar.R;
import com.example.appstar.beans.Team;
import com.example.appstar.services.ServiceTeam;

import java.util.ArrayList;
import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> implements Filterable {
    private static final String TAG = "TeamAdapter";
    private List<Team> teams;
    private Context context;
    private List<Team> teamsFilter;
    private NewFilter mfilter;


    public TeamAdapter(List<Team> teams, Context context) {
        this.teams = teams;
        this.context = context;
        teamsFilter = new ArrayList<>();
        teamsFilter.addAll(teams);
        mfilter = new NewFilter(this);

    }

    @NonNull
    @Override
    public TeamAdapter.TeamViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(this.context).inflate(R.layout.team_item_activity,
                viewGroup, false);

        final TeamViewHolder holder = new TeamViewHolder(v);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popup = LayoutInflater.from(context).inflate(R.layout.team_edit_item, null,
                        false);
                final ImageView img = popup.findViewById(R.id.img);
                final RatingBar bar = popup.findViewById(R.id.ratingBar);
                final TextView idss = popup.findViewById(R.id.idss);
                Bitmap bitmap =
                        ((BitmapDrawable)((ImageView)v.findViewById(R.id.img)).getDrawable()).getBitmap();
                img.setImageBitmap(bitmap);
                bar.setRating(((RatingBar)v.findViewById(R.id.stars)).getRating());
                idss.setText(((TextView)v.findViewById(R.id.ids)).getText().toString());
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle("Rate them!")
                        .setMessage("Rate PL team between 1 and 5 :")
                        .setView(popup)
                        .setPositiveButton("Validate", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                float s = bar.getRating();
                                int ids = Integer.parseInt(idss.getText().toString());
                                Team team = ServiceTeam.getInstance().findById(ids);
                                team.setStar(s);
                                ServiceTeam.getInstance().update(team);
                                notifyItemChanged(holder.getAdapterPosition());
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
            }
        });






        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TeamAdapter.TeamViewHolder teamViewHolder, int i) {
        Log.d(TAG, "onBindView call ! "+ i);
        Glide.with(context)
                .asBitmap()
                .load(teamsFilter.get(i).getImage())
                .apply(new RequestOptions().override(100, 100))
                .into(teamViewHolder.img);
        teamViewHolder.name.setText(teamsFilter.get(i).getName().toUpperCase());
        teamViewHolder.stars.setRating(teamsFilter.get(i).getStar());
        teamViewHolder.idss.setText(teamsFilter.get(i).getId()+"");

    }

    @Override
    public int getItemCount() {
        return teamsFilter.size();

    }



    public class TeamViewHolder extends RecyclerView.ViewHolder {
        TextView idss;
        ImageView img;
        TextView name;
        RatingBar stars;
        RelativeLayout parent;
        public TeamViewHolder(@NonNull View itemView) {
            super(itemView);
            idss = itemView.findViewById(R.id.ids);
            img = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.name);
            stars = itemView.findViewById(R.id.stars);
            parent = itemView.findViewById(R.id.parent);
        }
    }

    @Override
    public Filter getFilter() {
        return mfilter;
    }


    public class NewFilter extends Filter {
        public RecyclerView.Adapter mAdapter;
        public NewFilter(RecyclerView.Adapter mAdapter) {
            super();
            this.mAdapter = mAdapter;
        }
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            teamsFilter.clear();
            final FilterResults results = new FilterResults();
            if (charSequence.length() == 0) {
                teamsFilter.addAll(teams);
            } else {
                final String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Team p : teams) {
                    if (p.getName().toLowerCase().startsWith(filterPattern)) {
                        teamsFilter.add(p);
                    }
                }
            }
            results.values = teamsFilter;
            results.count = teamsFilter.size();
            return results;
        }
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            teamsFilter = (List<Team>) filterResults.values;
            this.mAdapter.notifyDataSetChanged();
        }
    }

}
