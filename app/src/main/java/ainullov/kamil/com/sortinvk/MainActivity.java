package ainullov.kamil.com.sortinvk;

import android.content.Intent;
import android.os.Bundle;
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
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    List<ItemInAdapter> itemInAdapterList;
    Adapter adapter;
    RecyclerView recyclerView;

    TextView textViewGroupAdress;
    TextView textViewPostsCount;
    EditText editTextPostsCount;
    Button buttonStart;
    Button btnGroupSelection;

    final int REQUEST_CODE_SELECT_GROUP = 1;
    String GROUP_NAME = "";
    int GROUP_ID = 0;

    int POSTS_COUNT = 100;
    int offset = 100;
    static int num = 1;

    private String[] scope = new String[]{VKScope.GROUPS, VKScope.WALL};
    VKRequest vkRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VKSdk.initialize(this);
        VKSdk.login(this, scope);
        textViewGroupAdress = (TextView) findViewById(R.id.textViewGroupAdress);
        textViewPostsCount = (TextView) findViewById(R.id.textViewPostsCount);
        editTextPostsCount = (EditText) findViewById(R.id.editTextPostsCount);
        buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(this);
        btnGroupSelection = (Button) findViewById(R.id.btnGroupSelection);
        btnGroupSelection.setOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemInAdapterList = new ArrayList<>();
        adapter = new Adapter(this, itemInAdapterList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGroupSelection:
                Intent intent = new Intent(this, GroupSelection.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT_GROUP);
                break;
            case R.id.buttonStart:
                itemInAdapterList.clear();
                offset = 0;
                num = 0;
                if (GROUP_ID == 0) {
                    Toast.makeText(this, "Выберите группу ", Toast.LENGTH_SHORT).show();
                } else {
                    if (editTextPostsCount != null)
                        POSTS_COUNT = Integer.valueOf(editTextPostsCount.getText().toString()) * 100;

                    int allPosts = 0;
                    while (allPosts != POSTS_COUNT) {

                        vkRequest = VKApi.wall().get(VKParameters.from(VKApiConst.OWNER_ID, "-" + GROUP_ID, VKApiConst.COUNT, POSTS_COUNT, VKApiConst.OFFSET, offset));
                        vkRequest.executeSyncWithListener(new VKRequest.VKRequestListener() {
                            @Override
                            public void onComplete(VKResponse response) {
                                super.onComplete(response);
                                try {
                                    JSONObject jsonObject = (JSONObject) response.json.get("response");
                                    JSONArray jsonArray = (JSONArray) jsonObject.get("items"); // Получили items

                                    for (int i = 0; i < jsonArray.length(); i++) { // Пробегаемся по всему json'у
                                        JSONObject post = (JSONObject) jsonArray.get(i);
                                        JSONObject likes = (JSONObject) post.getJSONObject("likes");
                                        int likesCount = likes.getInt("count");
                                        JSONObject reposts = (JSONObject) post.getJSONObject("reposts");
                                        int repostsCount = reposts.getInt("count");

                                        itemInAdapterList.add(new ItemInAdapter("https://vk.com/" + GROUP_ID + "?w=wall" +
                                                post.getInt("owner_id") + "_" + post.getInt("id"), likesCount, repostsCount, num));
                                        num++;

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        allPosts += 100;
                        offset += 100;

                        try {
                            synchronized (this) {
                                wait(400);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                   // offset = 100;
                    Collections.sort(itemInAdapterList, ItemInAdapter.COMPARE_BY_LIKES);
                    adapter.notifyDataSetChanged();

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

}
