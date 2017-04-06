package vn.app.android.ordermanager.order.v001.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.order.ordermanager.R;

import java.util.HashMap;

import vn.app.android.ordermanager.MainActivity;
import vn.app.android.ordermanager.base.FragmentHelper;
import vn.app.android.ordermanager.extra.FragmentTransitionInfo;
import vn.app.android.ordermanager.mode.User;
import vn.app.android.ordermanager.order.v001.common.V001CommonConstant;
import vn.app.android.ordermanager.order.v101.fragment.V101MainFragment;
import vn.app.android.ordermanager.popup.SimpleLoadingPopUp;
import vn.app.android.ordermanager.preference.CachePreference;
import vn.app.android.ordermanager.state.AppState;
import vn.app.android.ordermanager.util.LogUtil;
import vn.app.android.ordermanager.util.ResultUtil;
import vn.app.android.ordermanager.util.StringUtil;
import vn.app.android.ordermanager.util.TaskUtil;
import vn.app.android.ordermanager.worker.RestAsyncTaskCallback;

/**
 * Created by Mr Vuong on 3/30/2017.
 */
public class V001LoginScreenFragment extends Fragment{
    public static final String TAG = V001LoginScreenFragment.class.getName();
    private View mFragmentLayout;
    private SimpleLoadingPopUp mLoadingPopUp;
    private LinearLayout btnLogin;
    private EditText txtCompCode;
    private EditText txtUserName;
    private EditText txtPass;
    private TextView txtForgetPass;
    private CheckBox chkSavePass;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentLayout = inflater.inflate(R.layout.v001_login_screen_fragment,container,false);
        getMandatoryView ();
        buidLoginButton();
        return mFragmentLayout;
    }
    /**
     * Get mandatory views. This should be called first after layout inflated.
     */
    private void  getMandatoryView (){
        txtCompCode = (EditText) mFragmentLayout.findViewById(R.id.v001_txt_login_compcode);
        txtUserName = (EditText) mFragmentLayout.findViewById(R.id.v001_txt_login_username);
        txtPass = (EditText) mFragmentLayout.findViewById(R.id.v001_txt_login_pass);
        btnLogin = (LinearLayout) mFragmentLayout.findViewById(R.id.v001_login_btn_login);
        txtForgetPass = (TextView) mFragmentLayout.findViewById(R.id.v001_login_Forget_pass);
        chkSavePass = (CheckBox) mFragmentLayout.findViewById(R.id.v001_login_chk_savePass);
        btnLogin = (LinearLayout) mFragmentLayout.findViewById(R.id.v001_login_btn_login);

    }
    private void buidLoginButton(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String errorMessage = checkValidate();
                if(!StringUtil.isEmpty(errorMessage)){
                    String title = getResources().getString(R.string.pop_up_error_input_title);
                    TaskUtil.showDialogFragmentErrorPopUp(getActivity(),title,errorMessage);
                }else{
                    loginAccount();
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    /**
     * check input Login
     * @return
     */
    private String checkValidate(){
        String message = null;
        if(StringUtil.isEmpty(txtCompCode.getText().toString())){
            message = getResources().getString(R.string.pop_up_error_input_compcode_message);
        }else if(StringUtil.isEmpty(txtUserName.getText().toString())){
            message = getResources().getString(R.string.pop_up_error_input_username_message);
        }else if(StringUtil.isEmpty(txtPass.getText().toString())){
            message = getResources().getString(R.string.pop_up_error_input_pass_message);
        }
        return  message;

    }

    /**
     * Login Member By Email
     */
    private void loginAccount() {
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
                }else{
                    String title = getResources().getString(R.string.pop_up_error_login_title);
                    String message = getResources().getString(R.string.pop_up_error_login_message);
                    AppState.getInstance().showErrorDiaLogFragmentPopUp(getActivity(),title,message);
                }
            }
        };
        String deviceToken = CachePreference.getDeviceToken(getContext());
        // call rest api
        TaskUtil.loginUser(callback,txtCompCode.getText().toString(), txtUserName.getText().toString(),txtPass.getText().toString(),deviceToken);
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
