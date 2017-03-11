package my.shopping.app.my.shopping.app.fragement;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import my.shopping.app.R;
import my.shopping.app.my.shopping.app.domain.User;

/**
 * A placeholder fragment containing a simple view.
 */
public class BackupCreateUserFragment extends Fragment implements AdapterView.OnItemSelectedListener,View.OnClickListener {
    private ProgressBar progress;
    private HttpRequestPostTask task;
    private CheckBox javaCheckBox,dotNetCheckBox;
    private String[] country = { "India", "USA", "China", "Japan", "Other",  };
    String[] stateArr ={"Delhi","Bihar","UP","AP","HP","Punjab","Haryana","MP"};
    private DatePickerDialog doBDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private EditText doBtxt;
    public BackupCreateUserFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_user_create, container, false);
        doBtxt = (EditText)rootView.findViewById(R.id.user_Dob);
        doBtxt.setInputType(InputType.TYPE_NULL);
        Button btnSaveUser = (Button) rootView.findViewById(R.id.btnSaveUser);
        progress = (ProgressBar)rootView.findViewById(R.id.progressBarSave);
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = (Spinner) rootView.findViewById(R.id.spinnerCountry);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
        initStateFiled(rootView);

        setDateTimeField();
        hideProgressBar();
        // Listening to Login Screen link
        btnSaveUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                TextView txtUserName = (TextView) rootView.findViewById(R.id.user_name);
                TextView txtAge = (TextView) rootView.findViewById(R.id.user_age);
                TextView txtSalary = (TextView) rootView.findViewById(R.id.user_sal);
                final String userName = txtUserName.getText().toString();
                String strAge = txtAge.getText().toString();
                String strSalary = txtSalary.getText().toString();
                Integer age=0;
                double salary=0.0;
                if (!strAge.isEmpty()){
                    age = Integer.parseInt(strAge);
                }
                if(!strSalary.isEmpty()){
                    salary = Double.parseDouble(strSalary);
                }

                dotNetCheckBox = (CheckBox) rootView.findViewById(R.id.checkBoxDotNet);
                javaCheckBox = (CheckBox) rootView.findViewById(R.id.checkBoxJava);





                User newUser = new User();
                newUser.setName(userName);
                newUser.setAge(age);
                newUser.setSalary(salary);
                Boolean result = postData(newUser);

                if(isTaskRunning(task)) {
                    showProgressBar();
                }else {
                    hideProgressBar();
                }
                Toast.makeText(getActivity(),
                        "Saved successfully!"+" User "+userName+" age "+age+" salary "+salary+" dotNetCheckBox "+dotNetCheckBox.isChecked()+" dotJavaCheckBox "+javaCheckBox.isChecked(), Toast.LENGTH_SHORT).show();

            }
        });
       return rootView;
    }


    public ExpandableListView getExpandableListView(View rootView) {
        return (ExpandableListView) rootView.findViewById(R.id.lvUserExp);
    }
    private  void initStateFiled(View rootView){
        //Creating the instance of ArrayAdapter containing list of language names
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getActivity(),android.R.layout.select_dialog_item,stateArr);
        //Getting the instance of AutoCompleteTextView
        AutoCompleteTextView actv= (AutoCompleteTextView)rootView.findViewById(R.id.autoCompleteTextState);
        actv.setThreshold(1);//will start working from first character
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        actv.setTextColor(Color.BLUE);
    }
    private void setDateTimeField() {
        doBtxt.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        doBDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                doBtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        Toast.makeText(getActivity(),country[position] ,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        if(view == doBtxt) {
            doBDatePickerDialog.show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    public boolean postData(User user){

        task = new HttpRequestPostTask(this,user);
        task.execute();
        return true;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Sync UI state to current fragment and task state
        super.onActivityCreated(savedInstanceState);
    }

    protected boolean isTaskRunning(BackupCreateUserFragment.HttpRequestPostTask task) {
        if(task==null ) {
            return false;
        } else if(task.getStatus() == HttpRequestPostTask.Status.FINISHED){
            return false;
        } else {
            return true;
        }
    }

    public void showProgressBar() {
        progress.setVisibility(View.VISIBLE);
        progress.setIndeterminate(true);
    }

    public void hideProgressBar() {
        progress.setVisibility(View.GONE);

    }

    protected class HttpRequestPostTask extends AsyncTask<Void, Void, Boolean> {
        private BackupCreateUserFragment container;
        private User newUser;

        public HttpRequestPostTask(BackupCreateUserFragment container, User newUser){
            this.container = container;
            this.newUser = newUser;
        }


        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                final String url = "http://10.0.2.2:8080/spring-boot-ng-crud/api/user/";


                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                User res = restTemplate.postForObject(url, newUser, User.class);
                return true;
            } catch (Exception e) {
                Log.e("Error Creating User ", e.getMessage(), e);
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            container.showProgressBar();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if(container!=null && container.getActivity()!=null) {
                this.container = null;
            }
            progress.setVisibility(View.GONE);
        }
    }

}
