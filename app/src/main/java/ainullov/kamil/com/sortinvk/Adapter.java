package ainullov.kamil.com.sortinvk;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private List<ItemInAdapter> itemInAdapters;
    private LayoutInflater inflater;

    Adapter(Context context, List<ItemInAdapter> itemInAdapters) {
        this.itemInAdapters = itemInAdapters;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.list_item, parent, false);
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewLinks.setText(itemInAdapters.get(position).getLinks().toString());
        holder.textViewLikes.setText("Likes "+itemInAdapters.get(position).getLikes());
        holder.textViewReposts.setText("Reposts "+itemInAdapters.get(position).getReposts());
    }

    @Override
    public int getItemCount() {
        return itemInAdapters.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final TextView textViewLikes;
        final TextView textViewReposts;
        final TextView textViewLinks;
        final ImageView imageViewLikes;
        final ImageView imageViewReposts;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewLikes = itemView.findViewById(R.id.textViewLikes);
            textViewReposts = itemView.findViewById(R.id.textViewReposts);
            textViewLinks = itemView.findViewById(R.id.textViewLinks);
            imageViewLikes = itemView.findViewById(R.id.imageViewLikes);
            imageViewReposts = itemView.findViewById(R.id.imageViewReposts);
        }

    }
}
