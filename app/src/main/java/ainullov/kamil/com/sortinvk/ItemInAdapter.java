package ainullov.kamil.com.sortinvk;

import java.util.Comparator;

public class ItemInAdapter {
    private String links;
    private int likes;
    private int num;
    private int reposts;

    public ItemInAdapter(String links, int likes, int reposts, int num) {
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
        return "ItemInAdapter: " + "links= " + links + ", likes= " + likes + ", reposts= " + reposts;
    }

    public static final Comparator<ItemInAdapter> COMPARE_BY_LIKES = new Comparator<ItemInAdapter>() {
        @Override
        public int compare(ItemInAdapter itemInAdapter, ItemInAdapter t1) {
            return t1.likes - itemInAdapter.likes;
        }
    };

}
