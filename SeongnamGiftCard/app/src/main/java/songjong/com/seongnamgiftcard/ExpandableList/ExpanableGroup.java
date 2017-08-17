package songjong.com.seongnamgiftcard.ExpandableList;

import java.util.ArrayList;

/**
 * Created by dongwook on 2017. 8. 17..
 */
public class ExpanableGroup {
    public ArrayList<String> child;
    public String groupName;

    public ExpanableGroup(String name){
        groupName = name;
        child = new ArrayList<String>();
    }

}
