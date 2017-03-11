package my.shopping.app.my.shopping.app.fragement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.List;

import my.shopping.app.R;
import my.shopping.app.my.shopping.app.domain.BackupUserPopulator;
import my.shopping.app.my.shopping.app.domain.BaseExpandableListPopulator;
import my.shopping.app.my.shopping.app.domain.User;
import my.shopping.app.my.shopping.app.domain.UserPopulator;

/**
 * A placeholder fragment containing a simple view.
 */
public class BackupUserListFragment extends BackUpBaseFragment {
    private List<User> users;
    public BackupUserListFragment() {
    }

    @Override
    public View inflate(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_user_list, container, false);
    }

    @Override
    public BaseExpandableListPopulator getListPopulator() {
        return new BackupUserPopulator();
    }

    @Override
    public ExpandableListView getExpandableListView(View rootView) {
        return (ExpandableListView) rootView.findViewById(R.id.lvUserExp);
    }

    @Override
    public String getTitle() {
        return "Java";
    }


}
