package vn.app.android.ordermanager.order.v001.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.order.ordermanager.R;

import java.util.HashMap;

import vn.app.android.ordermanager.MainActivity;
import vn.app.android.ordermanager.base.FragmentHelper;
import vn.app.android.ordermanager.extra.FragmentTransitionInfo;
import vn.app.android.ordermanager.mode.Register;
import vn.app.android.ordermanager.mode.User;
import vn.app.android.ordermanager.order.v001.common.V001CommonConstant;
import vn.app.android.ordermanager.order.v101.fragment.V101MainFragment;
import vn.app.android.ordermanager.popup.SimpleLoadingPopUp;
import vn.app.android.ordermanager.state.AppState;
import vn.app.android.ordermanager.util.LogUtil;
import vn.app.android.ordermanager.util.ResultUtil;
import vn.app.android.ordermanager.util.StringUtil;
import vn.app.android.ordermanager.util.TaskUtil;
import vn.app.android.ordermanager.worker.RestAsyncTaskCallback;

/**
 * Created by ASUS on 3/12/2017.
 */
public class V001RegisterScreenFragment extends Fragment {

    public static final String TAG = V001RegisterScreenFragment.class.getName();
    private SimpleLoadingPopUp mLoadingPopUp;
    private View mFragmentLayout;

    private EditText txtCompCode;
    private EditText txtCompName;
    private EditText txtEmail;
    private EditText txtPhone;
    private EditText txtAddress;
    private EditText txtUserName;
    private EditText txtPassWord;
    private EditText txtRepeatPassWord;
    private LinearLayout btnRegister;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentLayout = inflater.inflate(R.layout.v001_register_acount_fragment, container, false);
        getMandatory();
        buidRegisterButton();
        return mFragmentLayout;
    }
    private void getMandatory(){
        txtCompCode = (EditText) mFragmentLayout.findViewById(R.id.v001_register_compCode);
        txtCompName = (EditText) mFragmentLayout.findViewById(R.id.v001_register_compName);
        txtEmail = (EditText) mFragmentLayout.findViewById(R.id.v001_register_Email);
        txtPhone = (EditText) mFragmentLayout.findViewById(R.id.v001_register_Phone);
        txtAddress = (EditText) mFragmentLayout.findViewById(R.id.v001_register_Address);
        txtUserName = (EditText) mFragmentLayout.findViewById(R.id.v001_register_UserName);
        txtPassWord = (EditText) mFragmentLayout.findViewById(R.id.v001_register_PassWord);
        txtRepeatPassWord = (EditText) mFragmentLayout.findViewById(R.id.v001_register_Repeat_PassWord);
        btnRegister = (LinearLayout) mFragmentLayout.findViewById(R.id.v001_register_btn_Register);
    }
    private void buidRegisterButton(){
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register register = new Register();
                register.setUsername(txtUserName.getText().toString());
                register.setCompCode(txtCompCode.getText().toString());
                register.setCompName(txtCompName.getText().toString());
                register.setEmail(txtEmail.getText().toString());
                register.setAddress(txtAddress.getText().toString());
                register.setPhone(txtPhone.getText().toString());
                register.setPassword(txtPassWord.getText().toString());
                register.setUsingTime(1);
                registerAccount(register);
            }
        });
    }
    /**
     * Login Member By Email
     * @param register
     */
    private void registerAccount(Register register) {
        RestAsyncTaskCallback callback = new RestAsyncTaskCallback() {
            //PreExecute
            @Override
            public void doPreExcute() {
                showLoadingPopUp();
            }
            //Cancel
            @Override
            public void doCancelled() {
                hideLoadingPopUp();
            }
            //PostExecute
            @Override
            public void doPostExecute(HashMap<String, Object> result) {
                hideLoadingPopUp();
                String error = ResultUtil.getError(result);
                if(StringUtil.isEmpty(error)) {	//No errors
                    User user = (User) result.get("user");
                    LogUtil.d(TAG, "ClientUser (After LoggedIn->" + user.toString());
                    AppState.getInstance().putUser(user);
                    passLoginFragment();
                }
            }
        };
        TaskUtil.registerAccount(callback, register);
//        TaskUtil.loginUser(callback,txtCompCode.getText().toString(), txtUserName.getText().toString(),txtPassWord.getText().toString());
        }
    /**
     * Show Loading Pop Up
     */
    protected void showLoadingPopUp() {
        if(mLoadingPopUp == null) {
            mLoadingPopUp = new SimpleLoadingPopUp(getActivity(), mFragmentLayout);
        }
        mLoadingPopUp.show();
    }

    /**
     * Hide Loading Pop Up.
     */
    protected void hideLoadingPopUp() {
        if(mLoadingPopUp != null && mLoadingPopUp.isShowing()) {
            mLoadingPopUp.dismiss();
        }
    }
    /**
     * pass to V101MainFragment
     */
    private void passLoginFragment(){
        MainActivity activity = (MainActivity) getActivity();
        FragmentTransitionInfo transaction = TaskUtil.getLeftToRightTransition();
        Bundle bundle = new Bundle();
        bundle.putString(V001CommonConstant.V001_CALL_FIRT_MAINFRAGMENT,V001CommonConstant.V001_CALL_FIRT_MAINFRAGMENT);
        FragmentHelper.replaceFragment(activity,activity.getFragmentContainerId(), V101MainFragment.TAG,true,bundle,transaction);

    }
}
