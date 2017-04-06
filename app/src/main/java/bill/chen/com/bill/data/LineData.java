package bill.chen.com.bill.data;

import android.content.Context;

import java.util.Calendar;

import bill.chen.com.bill.R;
import bill.chen.com.bill.utils.DateUtil;

/**
 * Created by chenwenping on 17/4/5.
 */

public class LineData {

    public static final int TYPE_TOP = 1;
    public static final int TYPE_ITEM_JOINT = 2;
    public static final int TYPE_DAY_JOINT = 3;

    private Calendar mCalendar;

    private int mType;

    public LineData(Calendar calendar) {
        this(calendar, TYPE_DAY_JOINT);
    }

    public LineData(Calendar calendar, int type) {
        if (calendar == null) {
            throw new IllegalStateException("Calendar cannot be null.");
        }
        mCalendar = calendar;
        mType = type;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public String formatToString(Context context) {
        if (mType == TYPE_ITEM_JOINT) {
            return null;
        }
        String month = String.format(context.getString(R.string.string_month_format1),
                mCalendar.get(Calendar.MONTH) + 1);
        String dayOfMonth = String.format(context.getString(R.string.string_day_format),
                mCalendar.get(Calendar.DAY_OF_MONTH));

        String date = month + dayOfMonth;

        Calendar calendar = Calendar.getInstance();
        if (DateUtil.isSampleDay(calendar, mCalendar)) {
            date += context.getString(R.string.today);
            return date;
        }
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        if (DateUtil.isSampleDay(calendar, mCalendar)) {
            date += context.getString(R.string.tomorrow);
            return date;
        }
        date += DateUtil.formatDayOfWeek(context, mCalendar.get(Calendar.DAY_OF_WEEK));
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof LineData) {
            return DateUtil.isSampleDay(((LineData) o).mCalendar, mCalendar);
        }
        return super.equals(o);
    }
}
