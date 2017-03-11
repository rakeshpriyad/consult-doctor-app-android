package my.shopping.app.my.shopping.app.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import my.shopping.app.R;
import my.shopping.app.my.shopping.app.domain.User;

public class ExpandableTableAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<User> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;

    public ExpandableTableAdapter(Context context, List<User> listDataHeader,
                                  HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }
 
    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }
 
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
 
    @Override
    public View getChildView(int groupPosition, final int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
 
        final String childText = (String) getChild(groupPosition, childPosition);
 
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.user_list_item, parent,false);
        }

        convertView.setBackgroundColor(Color.rgb(184,189,223));
        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);
 
        txtListChild.setText(childText);
        txtListChild.setTextColor(Color.YELLOW);
        return convertView;
    }
 
    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }
 
    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }
 
    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }
 
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
 
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
        User user = (User) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.user_table_group, parent,false);
        }

        if(groupPosition % 2 == 0) {
            convertView.setBackgroundColor(Color.rgb(3,192,60)); // use this when you want to use hexa value of colors
        } else {
            convertView.setBackgroundColor(Color.rgb(0,150,136)); // use this when you want to use hexa value of colors
        }
        TextView lblUserId = (TextView) convertView
                .findViewById(R.id.lblUserId);
        lblUserId.setTypeface(null, Typeface.BOLD);
        lblUserId.setText(""+user.getId());
        TextView lblUserName = (TextView) convertView
                .findViewById(R.id.lblUserName);
        lblUserName.setTypeface(null, Typeface.BOLD);
        lblUserName.setText(""+user.getName());
        TextView lblUserAge = (TextView) convertView
                .findViewById(R.id.lblUserAge);
        lblUserAge.setTypeface(null, Typeface.BOLD);
        lblUserAge.setText(""+user.getAge());
        TextView lblUserSal = (TextView) convertView
                .findViewById(R.id.lblUserSalary);
        lblUserSal.setTypeface(null, Typeface.BOLD);
        lblUserSal.setText(""+user.getSalary());

        return convertView;
    }
 
    @Override
    public boolean hasStableIds() {
        return false;
    }
 
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}