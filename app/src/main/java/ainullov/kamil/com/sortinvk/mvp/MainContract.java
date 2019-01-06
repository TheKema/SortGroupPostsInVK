package ainullov.kamil.com.sortinvk.mvp;

import android.content.Context;

import java.util.List;

import ainullov.kamil.com.sortinvk.GroupSelectionItem;
import ainullov.kamil.com.sortinvk.ItemInAdapter;

public interface MainContract {
    interface Model {
        List<GroupSelectionItem> makeRequestGroupList();

        List<ItemInAdapter> makeRequestSortPosts(Context context, int GROUP_ID, int POSTS_COUNT, int offset, List<ItemInAdapter> itemInAdapterList, int num);
    }

    interface View {

        void showData(List<?> list);
    }

    interface Presenter {
        void onGroupListButtonWasClicked();

        void onSortPostsButtonWasClicked(Context context, int GROUP_ID, int POSTS_COUNT, int offset, List<ItemInAdapter> itemInAdapterList, int num);

        void onDestroy();
    }

}
