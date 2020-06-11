package id.mobileprogramming.tugasproject5.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.transition.Fade;

import com.bumptech.glide.Glide;

import java.util.List;

import id.mobileprogramming.tugasproject5.R;
import id.mobileprogramming.tugasproject5.models.Pariwisata;
import id.mobileprogramming.tugasproject5.views.PariwisataActivity;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    private List<Pariwisata> list;
    private Context context;
    private String location;

    public static class HomeViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView pariwisata, lokasi, detail;
        private CardView card_pariwisata;
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.landscape);
            pariwisata = itemView.findViewById(R.id.pariwisata);
            lokasi = itemView.findViewById(R.id.lokasi);
            detail = itemView.findViewById(R.id.detail);
            card_pariwisata = itemView.findViewById(R.id.card_pariwisata);
        }
    }

    public void init(String location, List<Pariwisata> list, Context context){
        this.list = list;
        this.context = context;
        this.location = location;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeViewHolder holder, final int position) {
        holder.pariwisata.setText(list.get(position).getNama_wisata());
        holder.lokasi.setText(location);
        holder.detail.setText(list.get(position).getDetail());
        Glide.with(context)
                .load("https://api.sharekom.my.id/travel/assets/images.jpg")
                .into(holder.imageView);

        holder.card_pariwisata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PariwisataActivity.class);
                i.putExtra("image", list.get(position).getGambar());
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, holder.imageView, ViewCompat.getTransitionName(holder.imageView));
                context.startActivity(i, options.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
