package ainullov.kamil.com.sortinvk.mvp;

public interface MainContract {
    interface View {
        void showInfo();
    }

    interface Model {
        void makeRequestSortPosts();
        void makeRequestGroupList();
    }

    interface Presenter {
        void onButtonWasClicked();
        void onDestroy();
    }

}
