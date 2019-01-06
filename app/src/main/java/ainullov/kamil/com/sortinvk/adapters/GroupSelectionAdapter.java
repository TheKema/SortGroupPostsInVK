package ainullov.kamil.com.sortinvk.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ainullov.kamil.com.sortinvk.models.GroupSelectionItem;
import ainullov.kamil.com.sortinvk.R;

import static android.app.Activity.RESULT_OK;

public class GroupSelectionAdapter extends RecyclerView.Adapter<GroupSelectionAdapter.ViewHolder> {
    private List<GroupSelectionItem> groupSelectionItemList;
    private LayoutInflater inflater;
    private Context context;

    public GroupSelectionAdapter(Context context, List<GroupSelectionItem> groupSelectionItemList) {
        this.groupSelectionItemList = groupSelectionItemList;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public GroupSelectionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.list_item, parent, false);
        View view = inflater.inflate(R.layout.group_selection_item, parent, false);
        return new GroupSelectionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupSelectionAdapter.ViewHolder holder, int position) {
        holder.tvGroupName.setText("" + groupSelectionItemList.get(position).getGroupName());
//        Glide.with(holder.itemView.getContext()).load(groupSelectionItemList.get(position).getGroupIcon()).into(holder.ivGroupIcon);
        Picasso.with(holder.itemView.getContext()).load(groupSelectionItemList.get(position).getGroupIcon()).into(holder.ivGroupIcon);
    }

    @Override
    public int getItemCount() {
        return groupSelectionItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final TextView tvGroupName;
        final ImageView ivGroupIcon;
        final ConstraintLayout groupSelectionItem;

        public ViewHolder(View itemView) {
            super(itemView);
            tvGroupName = itemView.findViewById(R.id.tvGroupName);
            ivGroupIcon = itemView.findViewById(R.id.ivGroupIcon);
            groupSelectionItem = itemView.findViewById(R.id.groupSelectionItem);

            groupSelectionItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent();
                    intent.putExtra("GROUP_ID", groupSelectionItemList.get(getAdapterPosition()).getId());
                    intent.putExtra("GROUP_NAME", groupSelectionItemList.get(getAdapterPosition()).getGroupName());
                    ((Activity) context).setResult(RESULT_OK, intent);

//                    GROUP_ID = groupSelectionItemList.get(getAdapterPosition()).getId();
//                    GROUP_NAME = groupSelectionItemList.get(getAdapterPosition()).getGroupName();
                    ((Activity) context).finish();
                }
            });
        }

    }
}
