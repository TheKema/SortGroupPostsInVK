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
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.methods.VKApiGroups;
import com.vk.sdk.api.methods.VKApiWall;
import com.vk.sdk.api.model.VKList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    List<ItemInAdapter> itemInAdapterList = new ArrayList<>();
    Adapter adapter;
    RecyclerView recyclerView;
//    private String[] scope = new String[]{VKScope.GROUPS, VKScope.WALL, VKScope.FRIENDS, VKScope.MESSAGES};
    TextView textViewGroupAdress;
    TextView textViewPostsCount;
    EditText editTextGroupAdress;
    EditText editTextPostsCount;

    Button buttonStart;
    String GROUP_NAME = "tj";
    int POSTS_COUNT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewGroupAdress = (TextView) findViewById(R.id.textViewGroupAdress);
        textViewPostsCount = (TextView) findViewById(R.id.textViewPostsCount);
        editTextGroupAdress = (EditText) findViewById(R.id.editTextGroupAdress);
        editTextPostsCount = (EditText) findViewById(R.id.editTextPostsCount);
        buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this, itemInAdapterList);
        recyclerView.setAdapter(adapter);
//      VKSdk.login(this, scope);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonStart:
                if (editTextGroupAdress != null)
                    GROUP_NAME = editTextGroupAdress.getText().toString();
                if (editTextPostsCount != null)
                    POSTS_COUNT = Integer.valueOf(editTextPostsCount.getText().toString());
                VKSdk.login(this);
                break;
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {

                VKRequest vkRequest = new VKApiGroups().getById(VKParameters.from("group_ids", GROUP_NAME));
                vkRequest.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);

                        final VKList vkList = (VKList) response.parsedModel;
                        try {
                            VKRequest vkRequest1 = new VKApiWall().get(VKParameters.from(VKApiConst.OWNER_ID, "-" + vkList
                                    .get(0).fields.getInt("id"), VKApiConst.COUNT, POSTS_COUNT));
                            vkRequest1.executeWithListener(new VKRequest.VKRequestListener() {
                                @Override
                                public void onComplete(VKResponse response) {
                                    super.onComplete(response); //System.out.println("!!!!!" + response.responseString);
                                    try {
                                        JSONObject jsonObject = (JSONObject) response.json.get("response");
                                        JSONArray jsonArray = (JSONArray) jsonObject.get("items"); // получили items

                                        for (int i = 0; i < jsonArray.length(); i++) {// Пробегаемся по всему json'у
                                            JSONObject post = (JSONObject) jsonArray.get(i);
                                            JSONObject likes = (JSONObject) post.getJSONObject("likes");
                                            int likesCount = likes.getInt("count");
                                            JSONObject reposts = (JSONObject) post.getJSONObject("reposts");
                                            int repostsCount = reposts.getInt("count");

                                            itemInAdapterList.add(new ItemInAdapter("https://vk.com/" + GROUP_NAME + "?w=wall" + post.getInt("owner_id") + "_" + post.getInt("id"), likesCount, repostsCount));

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            Collections.sort(itemInAdapterList, ItemInAdapter.COMPARE_BY_LIKES);
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
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
