package ainullov.kamil.com.sortinvk.mvp;

import java.util.List;

import ainullov.kamil.com.sortinvk.models.GroupSelectionItem;
import ainullov.kamil.com.sortinvk.models.ItemInGroupListAdapter;

public interface MainContract {
    interface Model {
        List<GroupSelectionItem> makeRequestGroupList();

        List<ItemInGroupListAdapter> makeRequestSortPosts(int GROUP_ID, int POSTS_COUNT, int offset, List<ItemInGroupListAdapter> itemInGroupListAdapterList, int num);
    }

    interface View {
        void showData(List<?> list);
    }

    interface Presenter {
        void onGroupListButtonWasClicked();

        void onSortPostsButtonWasClicked(int GROUP_ID, int POSTS_COUNT, int offset, List<ItemInGroupListAdapter> itemInGroupListAdapterList, int num);

        void onDestroy();
    }

}
