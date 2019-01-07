package ainullov.kamil.com.sortinvk.activities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ainullov.kamil.com.sortinvk.models.GroupSelectionItem;
import ainullov.kamil.com.sortinvk.models.ItemInGroupListAdapter;
import ainullov.kamil.com.sortinvk.mvp.MainModel;
import ainullov.kamil.com.sortinvk.mvp.MainPresenter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainActivityUnitTest {
    List<ItemInGroupListAdapter> itemInGroupListAdapterList;

    @Before
    public void setUp() throws Exception {
        itemInGroupListAdapterList = new ArrayList<>();
        itemInGroupListAdapterList.add(new ItemInGroupListAdapter("", 13, 0, 0));
        itemInGroupListAdapterList.add(new ItemInGroupListAdapter("", 77, 0, 1));
        itemInGroupListAdapterList.add(new ItemInGroupListAdapter("", 43, 0, 2));
        itemInGroupListAdapterList.add(new ItemInGroupListAdapter("", 65, 0, 3));
    }


    @Test
    public void checkCollectionSort() {
        Collections.sort(itemInGroupListAdapterList, ItemInGroupListAdapter.COMPARE_BY_LIKES);
        Assert.assertEquals(77, itemInGroupListAdapterList.get(0).getLikes());

    }

}