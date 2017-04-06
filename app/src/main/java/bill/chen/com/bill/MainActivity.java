package bill.chen.com.bill;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

import bill.chen.com.bill.data.ItemData;
import bill.chen.com.bill.utils.ItemFormatUtil;

public class MainActivity extends AppCompatActivity {
    private static final long ONE_DAY_TIME_MILLIS = 24 * 60 * 60 * 1000;

    private LineAdapter mAdapter;
    long currentTime = System.currentTimeMillis();
    ItemData[] itemModelArray = new ItemData[]{
            new ItemData(currentTime, R.mipmap.icon1, "name0"),
            new ItemData(currentTime, R.mipmap.icon2, "name0"),
            new ItemData(currentTime, R.mipmap.icon3, "name0"),

            new ItemData(currentTime - ONE_DAY_TIME_MILLIS, R.mipmap.icon6, "name1"),
            new ItemData(currentTime - ONE_DAY_TIME_MILLIS, R.mipmap.icon7, "name2"),
            new ItemData(currentTime - ONE_DAY_TIME_MILLIS, R.mipmap.icon1, "name3"),

            new ItemData(currentTime - 2 * ONE_DAY_TIME_MILLIS, R.mipmap.icon4, "name6"),
            new ItemData(currentTime - 2 * ONE_DAY_TIME_MILLIS, R.mipmap.icon6, "name8"),

            new ItemData(currentTime - 3 * ONE_DAY_TIME_MILLIS, R.mipmap.icon2, "name14"),
            new ItemData(currentTime - 3 * ONE_DAY_TIME_MILLIS, R.mipmap.icon3, "name15"),

            new ItemData(currentTime - 5 * ONE_DAY_TIME_MILLIS, R.mipmap.icon4, "name11")

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PinnedSectionListView pinnedSectionListView =
                (PinnedSectionListView) findViewById(R.id.pinned_list_view);
        pinnedSectionListView.setShadowVisible(true);

        mAdapter = new LineAdapter(this, null);
        pinnedSectionListView.setAdapter(mAdapter);

        pinnedSectionListView.setListBottomListener(new PinnedSectionListView.ListBottomListener() {
            @Override
            public void loadMore() {
                List<ItemData> itemModels = Arrays.asList(itemModelArray);
                mAdapter.getDelegate().addData(ItemFormatUtil.assembleItemDatas(itemModels));
            }
        });

        addData();
    }

    private void addData() {

        List<ItemData> itemModels = Arrays.asList(itemModelArray);
        mAdapter.getDelegate().setData(ItemFormatUtil.assembleItemDatas(itemModels));
    }
}
