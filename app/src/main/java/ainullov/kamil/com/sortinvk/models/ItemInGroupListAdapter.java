package ainullov.kamil.com.sortinvk.models;

import java.util.Comparator;

public class ItemInGroupListAdapter {
    private String links;
    private int likes;
    private int num;
    private int reposts;

    public ItemInGroupListAdapter(String links, int likes, int reposts, int num) {
        this.links = links;
        this.likes = likes;
        this.reposts = reposts;
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getReposts() {
        return reposts;
    }

    public void setReposts(int reposts) {
        this.reposts = reposts;
    }

    @Override
    public String toString() {
        return "ItemInGroupListAdapter: " + "links= " + links + ", likes= " + likes + ", reposts= " + reposts;
    }

    public static final Comparator<ItemInGroupListAdapter> COMPARE_BY_LIKES = new Comparator<ItemInGroupListAdapter>() {
        @Override
        public int compare(ItemInGroupListAdapter itemInGroupListAdapter, ItemInGroupListAdapter t1) {
            return t1.likes - itemInGroupListAdapter.likes;
        }
    };

}
