package ainullov.kamil.com.sortinvk.mvp;

import android.content.Context;

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

import ainullov.kamil.com.sortinvk.models.GroupSelectionItem;
import ainullov.kamil.com.sortinvk.models.ItemInGroupListAdapter;

public class MainModel implements MainContract.Model {
    List<GroupSelectionItem> groupSelectionItemList;
    VKRequest vkRequest;
    List<ItemInGroupListAdapter> _itemInGroupListAdapterList;
    int _GROUP_ID;
    int _POSTS_COUNT;
    int _offset;
    int _num = 1;

    @Override
    public List<GroupSelectionItem> makeRequestGroupList() {
        groupSelectionItemList = new ArrayList<>();

        vkRequest = VKApi.groups().get(VKParameters.from(VKApiConst.COUNT, 1000, VKApiConst.EXTENDED, 1));
        vkRequest.executeSyncWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
//                System.out.println(response.responseString);
                try {
                    JSONObject jsonObject = (JSONObject) response.json.get("response");
                    JSONArray jsonArray = (JSONArray) jsonObject.get("items");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject post = (JSONObject) jsonArray.get(i);
                        int id = post.getInt("id");
                        String name = post.getString("name");
                        String imageLink = post.getString("photo_100");
                        groupSelectionItemList.add(new GroupSelectionItem(name, id, imageLink));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return groupSelectionItemList;
    }


    @Override
    public List<ItemInGroupListAdapter> makeRequestSortPosts(final Context context, int GROUP_ID, int POSTS_COUNT, int offset, List<ItemInGroupListAdapter> itemInGroupListAdapterList, int num) {
        _num = num;
        _itemInGroupListAdapterList = itemInGroupListAdapterList;
        _GROUP_ID = GROUP_ID;
        _POSTS_COUNT = POSTS_COUNT;
        _offset = offset;

        vkRequest = VKApi.wall().get(VKParameters.from(VKApiConst.OWNER_ID, "-" + _GROUP_ID, VKApiConst.COUNT, _POSTS_COUNT, VKApiConst.OFFSET, _offset));
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

                        _itemInGroupListAdapterList.add(new ItemInGroupListAdapter("https://vk.com/" + _GROUP_ID + "?w=wall" +
                                post.getInt("owner_id") + "_" + post.getInt("id"), likesCount, repostsCount, _num));
                        _num++;

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return _itemInGroupListAdapterList;
    }

}
