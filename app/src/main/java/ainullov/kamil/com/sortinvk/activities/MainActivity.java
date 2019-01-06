package ainullov.kamil.com.sortinvk.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ainullov.kamil.com.sortinvk.R;
import ainullov.kamil.com.sortinvk.adapters.GroupListAdapter;
import ainullov.kamil.com.sortinvk.models.ItemInGroupListAdapter;
import ainullov.kamil.com.sortinvk.mvp.MainContract;
import ainullov.kamil.com.sortinvk.mvp.MainPresenter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MainContract.View {
    private MainContract.Presenter mPresenter;
    Context context;

    List<ItemInGroupListAdapter> itemInGroupListAdapterList;
    GroupListAdapter groupListAdapter;
    RecyclerView recyclerView;

    TextView textViewGroupAdress;
    TextView textViewPostsCount;
    EditText editTextPostsCount;
    Button buttonStart;
    Button btnGroupSelection;

    ProgressDialog pd;
    Handler h;

    final int REQUEST_CODE_SELECT_GROUP = 1;
    String GROUP_NAME = "";
    int GROUP_ID = 0;

    int POSTS_COUNT = 100;
    int offset = 100;
    static int num = 1;

    private String[] scope = new String[]{VKScope.GROUPS, VKScope.WALL};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        if (!VKSdk.isLoggedIn()) {
            VKSdk.initialize(this);
            VKSdk.login(this, scope);
        }

        textViewGroupAdress = (TextView) findViewById(R.id.textViewGroupAdress);
        textViewPostsCount = (TextView) findViewById(R.id.textViewPostsCount);
        editTextPostsCount = (EditText) findViewById(R.id.editTextPostsCount);
        buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(this);
        btnGroupSelection = (Button) findViewById(R.id.btnGroupSelection);
        btnGroupSelection.setOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemInGroupListAdapterList = new ArrayList<>();
        groupListAdapter = new GroupListAdapter(this, itemInGroupListAdapterList);
        recyclerView.setAdapter(groupListAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGroupSelection:
                Intent intent = new Intent(this, GroupSelection.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT_GROUP);
                break;
            case R.id.buttonStart:

                itemInGroupListAdapterList.clear();

                offset = 0;
                num = 0;
                if (GROUP_ID == 0) {
                    Toast.makeText(this, "Выберите группу ", Toast.LENGTH_SHORT).show();
                } else {
                    if (editTextPostsCount != null)
                        POSTS_COUNT = Integer.valueOf(editTextPostsCount.getText().toString()) * 100;

                    pd = new ProgressDialog(this);
                    pd.setTitle("Загрузка");
//                    pd.setMessage("Примерное время ожидания: " + Math.round((POSTS_COUNT / 100) * 0.5) + " секунд");
                    pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    pd.setMax(POSTS_COUNT);
                    pd.show();

                    mPresenter = new MainPresenter(this);

                    h = new Handler() {
                        public void handleMessage(Message msg) {
                            if (pd.getProgress() < pd.getMax()) {
                                pd.incrementProgressBy(100);
                                pd.incrementSecondaryProgressBy(100);
                                h.sendEmptyMessageDelayed(0, 300);

                                mPresenter.onSortPostsButtonWasClicked(context, GROUP_ID, POSTS_COUNT, offset, itemInGroupListAdapterList, num);
                                offset += 100;
                                num += 100;

                                Collections.sort(itemInGroupListAdapterList, ItemInGroupListAdapter.COMPARE_BY_LIKES);
                                groupListAdapter.notifyDataSetChanged();
                            } else {
                                Collections.sort(itemInGroupListAdapterList, ItemInGroupListAdapter.COMPARE_BY_LIKES);
                                groupListAdapter.notifyDataSetChanged();
                                pd.dismiss();
                            }
                        }
                    };
                    h.sendEmptyMessageDelayed(0, 1000);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SELECT_GROUP:
                    GROUP_NAME = data.getStringExtra("GROUP_NAME");
                    GROUP_ID = data.getIntExtra("GROUP_ID", 0);
                    break;

            }
        }
        textViewGroupAdress.setText("Группа: " + GROUP_NAME);

        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {

            }

            @Override
            public void onError(VKError error) {
                Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void showData(List<?> list) {
        itemInGroupListAdapterList = (List<ItemInGroupListAdapter>) list;
        groupListAdapter = new GroupListAdapter(this, itemInGroupListAdapterList);
        recyclerView.setAdapter(groupListAdapter);

        Collections.sort(itemInGroupListAdapterList, ItemInGroupListAdapter.COMPARE_BY_LIKES);
        groupListAdapter.notifyDataSetChanged();

    }
}
