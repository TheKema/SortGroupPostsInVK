package ainullov.kamil.com.sortinvk.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ainullov.kamil.com.sortinvk.R;
import ainullov.kamil.com.sortinvk.adapters.GroupSelectionAdapter;
import ainullov.kamil.com.sortinvk.models.GroupSelectionItem;
import ainullov.kamil.com.sortinvk.mvp.MainContract;
import ainullov.kamil.com.sortinvk.mvp.MainPresenter;

public class GroupSelection extends AppCompatActivity implements MainContract.View {

    private MainContract.Presenter mPresenter;

    List<GroupSelectionItem> groupSelectionItemList;
    GroupSelectionAdapter groupSelectionAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_selection);

        mPresenter = new MainPresenter(this);
        mPresenter.onGroupListButtonWasClicked();
    }


    @Override
    public void showData(List<?> list) {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewGroupSelection);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        groupSelectionItemList = new ArrayList<>();
        groupSelectionItemList = (List<GroupSelectionItem>) list;
        groupSelectionAdapter = new GroupSelectionAdapter(this, groupSelectionItemList);
        recyclerView.setAdapter(groupSelectionAdapter);

        groupSelectionAdapter.notifyDataSetChanged();

    }
}
