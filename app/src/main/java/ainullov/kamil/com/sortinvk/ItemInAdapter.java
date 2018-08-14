package ainullov.kamil.com.sortinvk;

public class ItemInAdapter {
    private String links;
    private int likes;
    private int reposts;

    public ItemInAdapter(String links, int likes, int reposts) {
        this.links = links;
        this.likes = likes;
        this.reposts = reposts;
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
}
