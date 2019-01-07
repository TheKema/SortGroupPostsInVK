package ainullov.kamil.com.sortinvk.mvp;

import java.util.ArrayList;
import java.util.List;

import ainullov.kamil.com.sortinvk.models.GroupSelectionItem;
import ainullov.kamil.com.sortinvk.models.ItemInGroupListAdapter;

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View mView;
    private MainContract.Model mRepository;

    public MainPresenter(MainContract.View mView) {
        this.mView = mView;
        this.mRepository = new MainModel();
    }

    @Override
    public void onGroupListButtonWasClicked() {
        List<GroupSelectionItem> groupSelectionItemList = new ArrayList<>();
        groupSelectionItemList = mRepository.makeRequestGroupList();
        mView.showData(groupSelectionItemList);
    }

    @Override
    public void onSortPostsButtonWasClicked(int GROUP_ID, int POSTS_COUNT, int offset, List<ItemInGroupListAdapter> itemInGroupListAdapterList, int num) {
        itemInGroupListAdapterList = mRepository.makeRequestSortPosts(GROUP_ID, POSTS_COUNT, offset, itemInGroupListAdapterList, num);
        mView.showData(itemInGroupListAdapterList);
    }

    @Override
    public void onDestroy() {
    }
}
