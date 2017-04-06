package vn.app.android.ordermanager.order.v001.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.order.ordermanager.R;

import vn.app.android.ordermanager.MainActivity;
import vn.app.android.ordermanager.base.FragmentHelper;
import vn.app.android.ordermanager.extra.FragmentTransitionInfo;
import vn.app.android.ordermanager.util.LogUtil;
import vn.app.android.ordermanager.util.TaskUtil;

/**
 * Created by MrVuong on 12/27/2016.
 */
public class V001BeginScreenFragment extends Fragment implements View.OnClickListener{

    public static String TAG = V001BeginScreenFragment.class.getName();
    private View mFragmentLayout;
    private LinearLayout btnSingup;
    private LinearLayout btnLogin;

    public View getmFragmentLayout(){
        return  mFragmentLayout;
    }

    @Override
    public void onAttach(Activity activity) {
        LogUtil.d(TAG, "onAttach");
        super.onAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtil.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.d(TAG, "onCreateView");
        mFragmentLayout = inflater.inflate(R.layout.v001_begin_screen_fragment,container,false);
        getMandatoryView ();
        return mFragmentLayout;
    }

    @Override
    public void setTargetFragment(Fragment fragment, int requestCode) {
        super.setTargetFragment(fragment, requestCode);
    }

    @Override
    public void onStart() {
        LogUtil.d(TAG, "onStart");
        super.onStart();
    }

    @Override
    public void onPause() {
        LogUtil.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        LogUtil.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        LogUtil.d(TAG, "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        LogUtil.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onResume() {
        LogUtil.d(TAG, "onResume");
        super.onResume();
    }
    /**
     * Get mandatory views. This should be called first after layout inflated.
     */
    private void  getMandatoryView (){
        btnSingup = (LinearLayout) mFragmentLayout.findViewById(R.id.v001_begin_screen_ln_singup);
        btnLogin = (LinearLayout) mFragmentLayout.findViewById(R.id.v001_begin_screen_ln_login);
        btnLogin.setOnClickListener(this);
        btnSingup.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.v001_begin_screen_ln_login:
                passLoginFragment();
                break;
            case R.id.v001_begin_screen_ln_singup:
                passSingUpFragment();
                break;
        }
    }

    /**
     * pass to V101MainFragment
     */
    private void passLoginFragment(){
        MainActivity activity = (MainActivity) getActivity();
        FragmentTransitionInfo transaction = TaskUtil.getLeftToRightTransition();
//        Bundle bundle = new Bundle();
//        bundle.putString(V001CommonConstant.V001_CALL_FIRT_MAINFRAGMENT,V001CommonConstant.V001_CALL_FIRT_MAINFRAGMENT);
        FragmentHelper.replaceFragment(activity,activity.getFragmentContainerId(), V001LoginScreenFragment.TAG,true,null,transaction);

    }
    /**
     * pass to V101MainFragment
     */
    private void passSingUpFragment(){
        MainActivity activity = (MainActivity) getActivity();
        FragmentTransitionInfo transaction = TaskUtil.getLeftToRightTransition();
        FragmentHelper.replaceFragment(activity,activity.getFragmentContainerId(), V001RegisterScreenFragment.TAG,true,null,transaction);

    }

}
