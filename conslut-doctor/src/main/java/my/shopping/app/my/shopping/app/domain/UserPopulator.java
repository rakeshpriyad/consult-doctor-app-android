package my.shopping.app.my.shopping.app.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by aayushraj
 */

public class UserPopulator extends BaseExpandableTablePopulator {

    public List<User> getListDataHeader(List<User> userList){
        /*List<String> listDataHeader = new ArrayList<String>();
        for(User u : userList) {
            listDataHeader.add(""+u.getId());
        }*/
        return userList;
    }
    /*
        * Preparing the list data
        */
    public HashMap<String, List<String>>  getListDataChild(List<User> userList) {
        HashMap<String, List<String>> listDataChild = new HashMap<String, List<String>>();
        List<User> listDataHeader = getListDataHeader(userList);
        for(User u : userList){
            List<String> uName = new ArrayList<>();
            uName.add(u.getName());
            listDataChild.put(""+u.getId(),uName);
        }
     return listDataChild;
    }
}
