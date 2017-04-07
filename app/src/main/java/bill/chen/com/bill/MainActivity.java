package bill.chen.com.bill;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import bill.chen.com.bill.data.ItemData;
import bill.chen.com.bill.data.LineData;
import bill.chen.com.bill.utils.ItemFormatUtil;

public class MainActivity extends AppCompatActivity {
    private static final long ONE_DAY_TIME_MILLIS = 24 * 60 * 60 * 1000;

    private LineAdapter mAdapter;
    long currentTime = System.currentTimeMillis();
    ItemData[] itemModelArray = new ItemData[]{
            new ItemData(currentTime, R.mipmap.icon1, "name0"),
            new ItemData(currentTime, R.mipmap.icon2, "name0"),
            new ItemData(currentTime, R.mipmap.icon3, "name0"),

            /*new ItemData(currentTime - ONE_DAY_TIME_MILLIS, R.mipmap.icon6, "name1"),
            new ItemData(currentTime - ONE_DAY_TIME_MILLIS, R.mipmap.icon7, "name2"),
            new ItemData(currentTime - ONE_DAY_TIME_MILLIS, R.mipmap.icon1, "name3"),

            new ItemData(currentTime - 2 * ONE_DAY_TIME_MILLIS, R.mipmap.icon4, "name6"),
            new ItemData(currentTime - 2 * ONE_DAY_TIME_MILLIS, R.mipmap.icon6, "name8"),

            new ItemData(currentTime - 3 * ONE_DAY_TIME_MILLIS, R.mipmap.icon2, "name14"),
            new ItemData(currentTime - 3 * ONE_DAY_TIME_MILLIS, R.mipmap.icon3, "name15"),

            new ItemData(currentTime - 5 * ONE_DAY_TIME_MILLIS, R.mipmap.icon4, "name11")*/
            new ItemData(currentTime - ONE_DAY_TIME_MILLIS, R.mipmap.icon6, "name1"),
            new ItemData(currentTime - ONE_DAY_TIME_MILLIS, R.mipmap.icon7, "name2"),
            new ItemData(currentTime - ONE_DAY_TIME_MILLIS, R.mipmap.icon1, "name3"),

            new ItemData(currentTime - 2 * ONE_DAY_TIME_MILLIS, R.mipmap.icon4, "name6"),
            new ItemData(currentTime - 2 * ONE_DAY_TIME_MILLIS, R.mipmap.icon6, "name8"),

            new ItemData(currentTime - 3 * ONE_DAY_TIME_MILLIS, R.mipmap.icon2, "name14"),
            new ItemData(currentTime - 3 * ONE_DAY_TIME_MILLIS, R.mipmap.icon3, "name15"),

            new ItemData(currentTime - 5 * ONE_DAY_TIME_MILLIS, R.mipmap.icon4, "name11")

    };

    private LayoutInflater mInflater;
    private RelativeLayout moredata;

    private View progressBarView;
    private TextView progressBarTextView;
    private AnimationDrawable loadingAnimation;
    private boolean isLoading = false;

    private RelativeLayout pullToRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInflater = LayoutInflater.from(this);

        moredata = (RelativeLayout) mInflater.inflate(R.layout.moredata, null);
        progressBarView = (View) moredata.findViewById(R.id.loadmore_foot_progressbar);
        progressBarTextView = (TextView) moredata.findViewById(R.id.loadmore_foot_text);
        loadingAnimation = (AnimationDrawable) progressBarView.getBackground();

        pullToRefresh = (RelativeLayout) mInflater.inflate(R.layout.pull_to_refresh, null);

        PinnedSectionListView pinnedSectionListView =
                (PinnedSectionListView) findViewById(R.id.pinned_list_view);
        pinnedSectionListView.setShadowVisible(true);

        mAdapter = new LineAdapter(this, null);
        pinnedSectionListView.addHeaderView(pullToRefresh);
        pinnedSectionListView.addFooterView(moredata);

        pinnedSectionListView.setAdapter(mAdapter);

        pinnedSectionListView.setListBottomListener(new PinnedSectionListView.ListBottomListener() {
            @Override
            public void loadMore() {

                progressBarView.setVisibility(View.VISIBLE);
                progressBarTextView.setVisibility(View.VISIBLE);

                loadingAnimation.start();

                if(!isLoading) {
                    isLoading = true;
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            List<ItemData> itemModels = Arrays.asList(itemModelArray);
                            mAdapter.getDelegate().addData(ItemFormatUtil.assembleItemDatas(itemModels));

                            loadingFinished();
                        }
                    }, 1200);
                }
            }
        });

        addData();

        mAdapter.setListItemClickListener(new LineAdapter.ListItemClickListener() {
            @Override
            public void itemOnClick(ItemData itemModel) {
                Toast.makeText(MainActivity.this, itemModel.name, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void headerOnClick(LineData timelineModel) {
                Toast.makeText(MainActivity.this, timelineModel.getType(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadingFinished() {

        if (null != loadingAnimation && loadingAnimation.isRunning()) {
            loadingAnimation.stop();
        }
        progressBarView.setVisibility(View.INVISIBLE);
        progressBarTextView.setVisibility(View.INVISIBLE);
        isLoading = false;
    }

    private void addData() {

        List<ItemData> itemModels = Arrays.asList(itemModelArray);

        mAdapter.getDelegate().setData(ItemFormatUtil.assembleItemDatas(itemModels));
    }
}
