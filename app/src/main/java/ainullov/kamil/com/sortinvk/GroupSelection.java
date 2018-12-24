package ainullov.kamil.com.sortinvk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GroupSelection extends AppCompatActivity {
    VKRequest vkRequest;

    List<GroupSelectionItem> groupSelectionItemList;
    GroupSelectionAdapter groupSelectionAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_selection);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewGroupSelection);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        groupSelectionItemList = new ArrayList<>();
        groupSelectionAdapter = new GroupSelectionAdapter(this, groupSelectionItemList);
        recyclerView.setAdapter(groupSelectionAdapter);

//                        try {
//                            JSONObject jsonObject = (JSONObject) response.json.get("response");
//                            JSONArray jsonArray = (JSONArray) jsonObject.get("items"); // Получили items
//                            //  JSONArray jsonArrayGroupsCount = (JSONArray) jsonObject.get("count");
//                            int groupCount = jsonObject.getInt("count");
//                            Toast.makeText(getApplicationContext(), "Groups Count: " + groupCount, Toast.LENGTH_SHORT).show();
//
//                            for (int i = 0; i < jsonArray.length(); i++) { // Пробегаемся по всему json'у
//                                JSONObject post = (JSONObject) jsonArray.get(i);
//                                //JSONObject name = (JSONObject) post.getJSONObject("name");
//                                //JSONObject id = (JSONObject) post.getJSONObject("id");
//
//                                String name = post.getString("name");
//                                long id = post.getLong("id");
//                                // JSONObject reposts = (JSONObject) post.getJSONObject("reposts");
//                                // int repostsCount = reposts.getInt("count");
//
//                                groupSelectionItemList.add(new GroupSelectionItem(name));
//                                groupSelectionAdapter.notifyDataSetChanged();
//
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }

        vkRequest = VKApi.groups().get(VKParameters.from(VKApiConst.COUNT, 1000, VKApiConst.EXTENDED, 1));
        vkRequest.executeSyncWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                System.out.println(response.responseString);
                try {
                    JSONObject jsonObject = (JSONObject) response.json.get("response");
                    JSONArray jsonArray = (JSONArray) jsonObject.get("items");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject post = (JSONObject) jsonArray.get(i);
                        int id = post.getInt("id");
                        String name = post.getString("name");
                        groupSelectionItemList.add(new GroupSelectionItem(name, id));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        groupSelectionAdapter.notifyDataSetChanged();
    }
}
