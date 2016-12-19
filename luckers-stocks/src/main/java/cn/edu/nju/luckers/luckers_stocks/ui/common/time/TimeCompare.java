package cn.edu.nju.luckers.luckers_stocks.ui.common.time;

import java.util.Calendar;

/**
 * Created by Sissel on 2015/12/16.
 */
public class TimeCompare {

    public static boolean compareCalendar(Calendar c1, Calendar c2){
        if(c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
                && c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)){
            return true;
        }else{
            return false;
        }
    }
}
