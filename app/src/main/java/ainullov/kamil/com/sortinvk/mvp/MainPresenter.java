package ainullov.kamil.com.sortinvk.mvp;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ainullov.kamil.com.sortinvk.GroupSelectionItem;
import ainullov.kamil.com.sortinvk.ItemInAdapter;

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
    public void onSortPostsButtonWasClicked(Context context, int GROUP_ID, int POSTS_COUNT, int offset, List<ItemInAdapter> itemInAdapterList, int num) {
        itemInAdapterList = mRepository.makeRequestSortPosts(context, GROUP_ID, POSTS_COUNT, offset, itemInAdapterList, num);
        mView.showData(itemInAdapterList);
    }

    @Override
    public void onDestroy() {
    }
}
