package my.shopping.app.my.shopping.app.fragement;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;


import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import my.shopping.app.R;
import my.shopping.app.my.shopping.app.domain.BaseExpandableListPopulator;
import my.shopping.app.my.shopping.app.domain.BaseExpandableTablePopulator;
import my.shopping.app.my.shopping.app.domain.User;
import my.shopping.app.my.shopping.app.util.ExpandableTableAdapter;

//http://www.mobiledeveloperguide.com/android/using-asynctask-and-fragments.html
/**
 * A placeholder fragment containing a simple view.
 */
public abstract class BaseFragment extends Fragment {
    //protected ExpandableUserListAdapter listAdapter;
    protected ExpandableTableAdapter listAdapter;
    protected ExpandableListView expListView;
    private HttpRequestTask task;
    //private List<User> userList = new ArrayList<>();
    private ProgressBar progress;
    public BaseFragment() {
    }


    protected void fetchData() {
        task = new HttpRequestTask(this);
        task.execute();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Sync UI state to current fragment and task state
        if(isTaskRunning(task)) {
            showProgressBar();
        }else {
            hideProgressBar();
        }
        /*if(userList!=null) {
            populateResult(userList);
        }*/
        super.onActivityCreated(savedInstanceState);
    }

    public void showProgressBar() {
        progress.setVisibility(View.VISIBLE);
        progress.setIndeterminate(true);
    }

    public void hideProgressBar() {
        progress.setVisibility(View.GONE);

    }

    public void populateResult(List<User> users) {
       // BaseExpandableListPopulator baseExpandableListPopulator = getListPopulator();
        BaseExpandableTablePopulator baseExpandableTablePopulator = getListPopulator();
        listAdapter = baseExpandableTablePopulator.getExpandableListAdapter(getActivity(),users);
        expListView.setAdapter(listAdapter);
    }

    protected boolean isTaskRunning(HttpRequestTask task) {
        if(task==null ) {
            return false;
        } else if(task.getStatus() == HttpRequestTask.Status.FINISHED){
            return false;
        } else {
            return true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflate(inflater,container);
        expListView = getExpandableListView(rootView);
        progress = (ProgressBar)rootView.findViewById(R.id.progressBarFetch);
        // setting list adapter
        fetchData();
        setRetainInstance(true);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(getTitle());
    }


    public abstract View inflate(LayoutInflater inflater, ViewGroup container);
    public abstract BaseExpandableTablePopulator getListPopulator();
    public abstract ExpandableListView getExpandableListView(View rootView);
    public abstract String getTitle();
    protected class HttpRequestTask extends AsyncTask<Void, Void, List<User>> {
        private  BaseFragment container;

        public HttpRequestTask(BaseFragment container){
            this.container = container;
        }


        @Override
        protected List<User> doInBackground(Void... params) {
            try {
                final String url = "http://10.0.2.2:8080/spring-boot-ng-crud/api/user/";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ResponseEntity<List<User>> rateResponse =
                        restTemplate.exchange(url,
                                HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
                                });
                List<User> userList = rateResponse.getBody();
               // List<User> userList = //restTemplate.getForObject(url, List.class);
                return userList;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            container.showProgressBar();
        }

        @Override
        protected void onPostExecute(List<User> users) {
            super.onPostExecute(users);

            if(container!=null && container.getActivity()!=null) {
                container.populateResult(users);
                this.container = null;
            }
            progress.setVisibility(View.GONE);
        }
    }

}
