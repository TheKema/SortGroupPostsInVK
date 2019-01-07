package ainullov.kamil.com.sortinvk.models;

public class GroupSelectionItem {
    private String groupIcon;
    private String groupName;
    private String groupFollowers;
    private int id;

    public GroupSelectionItem(String groupName, int id, String groupIcon) {
        this.groupName = groupName;
        this.id = id;
        this.groupIcon = groupIcon;
    }

    public GroupSelectionItem(String groupName) {
        this.groupName = groupName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupIcon() {
        return groupIcon;
    }

    public void setGroupIcon(String groupIcon) {
        this.groupIcon = groupIcon;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupFollowers() {
        return groupFollowers;
    }

    public void setGroupFollowers(String groupFollowers) {
        this.groupFollowers = groupFollowers;
    }
}
