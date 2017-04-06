package bill.chen.com.bill.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bill.chen.com.bill.data.ItemData;
import bill.chen.com.bill.data.LineData;

/**
 * 作者：YaLin
 * 日期：2016/10/11.
 */

public class ItemFormatUtil {
    /**
     * 将item列表按日期组装，即为itemModelList生成相应的TimelineModel，放入同一个List中
     *
     * @param itemModels 待面试列表
     * @return 组装后的包含日期tag的待面试列表
     */
    public static List<Object> assembleItemDatas(List<ItemData> itemModels) {
        if (itemModels == null || itemModels.isEmpty()) {
            return null;
        }
        List<Object> objects = new ArrayList<>();
        appendItemWithTag(objects, itemModels);

        return objects;
    }

    /**
     * 组装的实现方法
     *
     * @param objects           组装进的列表
     * @param orderedItemModels 待面试列表
     */
    private static void appendItemWithTag(List<Object> objects, List<ItemData> orderedItemModels) {
        Calendar calendar = Calendar.getInstance();
        boolean firstTag = objects.size() == 0;
        for (ItemData itemModel : orderedItemModels) {
            Calendar tagCalendar = (Calendar) calendar.clone();
            tagCalendar.setTimeInMillis(itemModel.time);
            tagCalendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
            LineData timelineModel = new LineData(tagCalendar);
            if (!objects.contains(timelineModel)) {//title...
                timelineModel.setType(firstTag ? LineData.TYPE_TOP : LineData.TYPE_DAY_JOINT);
                firstTag = false;
            } else {
                timelineModel.setType(LineData.TYPE_ITEM_JOINT);
            }
            objects.add(timelineModel);//title
            objects.add(itemModel);//列表
        }
    }

    /**
     * 将以有的组装好的（包含TimelineModel和ItemModel）的List，添加新的ItemModel
     *
     * @param origin        已有的List
     * @param newInterviews 新增加的
     * @return 新组装的List
     */
    public static List<Object> mergePendingInterviewDatas(List<Object> origin,
                                                          List<ItemData> newInterviews) {
        if (origin == null || origin.size() == 0) {
            return assembleItemDatas(newInterviews);
        }
        appendItemWithTag(origin, newInterviews);
        return origin;
    }
}
