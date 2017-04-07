package bill.chen.com.bill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bill.chen.com.bill.data.ItemData;
import bill.chen.com.bill.data.LineData;
import bill.chen.com.bill.utils.ItemFormatUtil;

/**
 * Created by chenwenping on 17/4/5.
 */

public class LineAdapter extends BaseAdapter
        implements PinnedSectionListView.PinnedSectionListAdapter {
    private static final int ITEM_TYPE_COUNT = 3;

    private static final int TYPE_DATE_TAG = 0;

    private static final int TYPE_INTERVIEW_ITEM = 1;

    private static final int TYPE_SECTION_TAG = 2;

    private Context mContext;

    private List<Object> mDatas;

    private Delegate mDelegate;

    public LineAdapter(Context context, List<Object> datas) {
        mContext = context;
        mDatas = datas;
        mDelegate = new Delegate();
    }

    public Delegate getDelegate() {
        return mDelegate;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        Object object = getItem(position);
        if (object instanceof LineData) {
            if (((LineData) object).getType() == LineData.TYPE_ITEM_JOINT) {
                return TYPE_DATE_TAG;
            } else {
                return TYPE_SECTION_TAG;
            }
        }
        return TYPE_INTERVIEW_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return ITEM_TYPE_COUNT;//3
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        Object item = getItem(position);
        if ((type == TYPE_DATE_TAG || type == TYPE_SECTION_TAG) && item instanceof LineData) {//
            TimelineViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext)
                        .inflate(R.layout.layout_time_line_item, parent, false);
                holder = new TimelineViewHolder();
                holder.tvTag =
                        (TextView) convertView.findViewById(R.id.interview_date_tag_tv_tag);
                holder.ivType =
                        (ImageView) convertView.findViewById(R.id.interview_date_tag_iv_type);
                holder.llParent =
                        (LinearLayout) convertView.findViewById(R.id.interview_date_tag_ll_parent);
                convertView.setTag(holder);
            } else {
                holder = (TimelineViewHolder) convertView.getTag();
            }
            bindTimeLine(holder, (LineData) item);//将数据赋值给id
        } else if (type == TYPE_INTERVIEW_ITEM) {
            Object holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext)
                        .inflate(R.layout.layout_item, parent, false);
                holder = createHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = convertView.getTag();
            }
            bindItem((ItemHolder) holder, (ItemData) item);//将数据赋值给id
        }
        return convertView;
    }

    private ItemHolder createHolder(View convertView) {
        ItemHolder holder = new ItemHolder();
        holder.ivAvatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
        holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
        return holder;
    }


    private void bindTimeLine(TimelineViewHolder holder, final LineData timelineModel) {
        holder.ivType.setEnabled(true);
        holder.ivType.setActivated(false);
        holder.tvTag.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams lp = holder.llParent.getLayoutParams();
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        if (timelineModel.getType() == LineData.TYPE_TOP) {
            holder.ivType.setActivated(true);
        } else if (timelineModel.getType() == LineData.TYPE_ITEM_JOINT) {//liebiao
            holder.ivType.setEnabled(false);
            holder.tvTag.setVisibility(View.GONE);
            lp.height = mContext.getResources()
                    .getDimensionPixelSize(R.dimen.default_time_line_joint_height);
        }
        holder.tvTag.setText(timelineModel.formatToString(mContext));
        holder.tvTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListItemClickListener.headerOnClick(timelineModel);
            }
        });
    }

    /**
     * list数据设置
     * @param holder
     * @param itemModel
     */
    private void bindItem(ItemHolder holder, final ItemData itemModel) {
        holder.ivAvatar.setImageResource(itemModel.avatarRes);
        holder.ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListItemClickListener.itemOnClick(itemModel);
            }
        });
        holder.tvName.setText(itemModel.name);
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == TYPE_SECTION_TAG;
    }

    private static class TimelineViewHolder {
        TextView tvTag;
        ImageView ivType;
        LinearLayout llParent;
    }

    private static class ItemHolder {
        ImageView ivAvatar;
        TextView tvName;
    }

    public class Delegate {

        private Delegate() {
        }

        public void setData(List<Object> datas) {
            mDatas = datas;
            notifyDataSetChanged();
        }

        public void addData(List<Object> datas) {
            if (datas == null) {
                return;
            }
            if (mDatas == null) {
                mDatas = new ArrayList<>();
            }

            mDatas.addAll(datas);
            notifyDataSetChanged();
        }



        public void addData2(List<ItemData> itemModels) {
            if (itemModels == null) {
                return;
            }
            if (mDatas == null) {
                mDatas = new ArrayList<>();
            }

            ItemFormatUtil.mergePendingInterviewDatas(mDatas, itemModels);
            notifyDataSetChanged();
        }
    }


    public interface ListItemClickListener {
        void itemOnClick(ItemData itemModel);
        void headerOnClick(LineData timelineModel);
    }

    private ListItemClickListener mListItemClickListener;

    public void setListItemClickListener(ListItemClickListener listItemClickListener) {
        this.mListItemClickListener = listItemClickListener;
    }

}

