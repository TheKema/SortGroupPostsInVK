package ainullov.kamil.com.sortinvk.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ainullov.kamil.com.sortinvk.models.ItemInGroupListAdapter;
import ainullov.kamil.com.sortinvk.R;

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.ViewHolder> {
    private List<ItemInGroupListAdapter> itemInGroupListAdapters;
    private LayoutInflater inflater;

    public GroupListAdapter(Context context, List<ItemInGroupListAdapter> itemInGroupListAdapters) {
        this.itemInGroupListAdapters = itemInGroupListAdapters;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewLinks.setText(itemInGroupListAdapters.get(position).getLinks().toString());
        holder.textViewLikes.setText("Likes " + itemInGroupListAdapters.get(position).getLikes());
        holder.textViewNum.setText("" + itemInGroupListAdapters.get(position).getNum());
        holder.textViewReposts.setText("Reposts " + itemInGroupListAdapters.get(position).getReposts());
    }

    @Override
    public int getItemCount() {
        return itemInGroupListAdapters.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final TextView textViewLikes;
        final TextView textViewNum;
        final TextView textViewReposts;
        final TextView textViewLinks;
        final ImageView imageViewLikes;
        final ImageView imageViewReposts;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewNum = itemView.findViewById(R.id.tvNum);
            textViewLikes = itemView.findViewById(R.id.textViewLikes);
            textViewReposts = itemView.findViewById(R.id.textViewReposts);
            textViewLinks = itemView.findViewById(R.id.textViewLinks);
            imageViewLikes = itemView.findViewById(R.id.imageViewLikes);
            imageViewReposts = itemView.findViewById(R.id.imageViewReposts);
        }

    }
}
