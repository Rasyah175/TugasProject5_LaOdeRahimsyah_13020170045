package id.mobileprogramming.tugasproject5.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import id.mobileprogramming.tugasproject5.R;
import id.mobileprogramming.tugasproject5.views.HomeActivity;
import id.mobileprogramming.tugasproject5.views.sharedpreferences.LoginPreferences;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder> {
    private List<String> list;
    private Context context;

    public static class BookmarkViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        private LinearLayout linearLayout;
        public BookmarkViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.bookmark_text);
            linearLayout = itemView.findViewById(R.id.bookmark_list);
        }
    }

    public void init(List<String> list, Context context){
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookmarkViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkViewHolder holder, final int position) {
        holder.textView.setText(list.get(position));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginPreferences.getInstance().getShared(context);
                LoginPreferences.getInstance().init(context);
                LoginPreferences.getInstance().setLocation(list.get(position));

                BottomNavigationView bottom = ((HomeActivity) context).findViewById(R.id.bottom_menu);
                bottom.setSelectedItemId(R.id.home);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
