package ainullov.kamil.com.sortinvk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class MainActivity extends AppCompatActivity {

    List<ItemInAdapter> itemInAdapterList = new ArrayList<>();
    Adapter adapter;
    RecyclerView recyclerView;
    //    private String[] scope = new String[]{VKScope.GROUPS, VKScope.WALL, VKScope.FRIENDS, VKScope.MESSAGES};
    TextView textView;

    String GROUP_NAME = "tj";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);



        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this, itemInAdapterList);
        recyclerView.setAdapter(adapter);

        VKSdk.login(this);
//      VKSdk.login(this, scope);
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
                                    .get(0).fields.getInt("id"), VKApiConst.COUNT, 100));
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

//                                            System.out.println(post.getString("text")); // текст отдельно
                                            textView.setText(post.getString("text"));
                                            itemInAdapterList.add(new ItemInAdapter("https://vk.com/" + GROUP_NAME + "?w=wall" + post.getInt("owner_id") + "_" + post.getInt("id"), likesCount, repostsCount));
                                            Collections.sort(itemInAdapterList, ItemInAdapter.COMPARE_BY_LIKES);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
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
