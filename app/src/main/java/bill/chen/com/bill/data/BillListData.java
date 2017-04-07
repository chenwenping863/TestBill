package bill.chen.com.bill.data;

import java.util.List;

/**
 * Created by chenwenping on 17/3/20.
 */

public class BillListData {
    public List<BillDetailList> billList;
    public boolean isEnd;

    public class BillDetailList {
        public String detailId;
        public int OrderType;
        public String isFi;
        public String status;
        public String type;
        public String date;
        public String time;
        public String iconUrl;
        public String money;
        public String desc;
        public boolean newFlag;
    }
}

