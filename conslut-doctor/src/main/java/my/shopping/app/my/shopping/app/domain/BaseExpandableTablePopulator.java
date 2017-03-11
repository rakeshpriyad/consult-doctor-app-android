package my.shopping.app.my.shopping.app.domain;

import android.support.v4.app.FragmentActivity;

import java.util.HashMap;
import java.util.List;

import my.shopping.app.my.shopping.app.util.ExpandableTableAdapter;
import my.shopping.app.my.shopping.app.util.ExpandableUserListAdapter;

/**
 * Created by aayushraj on 11-02-2017.
 */

public abstract class BaseExpandableTablePopulator {
    private List<User> userList;
    public abstract List<User> getListDataHeader(List<User> userList);
    public abstract HashMap<String, List<String>> getListDataChild(List<User> userList);

    public ExpandableTableAdapter getExpandableListAdapter(FragmentActivity activity,List<User> userList){
        this.userList = userList;
        return new ExpandableTableAdapter(activity, getListDataHeader(userList), getListDataChild(userList));
    }
}
