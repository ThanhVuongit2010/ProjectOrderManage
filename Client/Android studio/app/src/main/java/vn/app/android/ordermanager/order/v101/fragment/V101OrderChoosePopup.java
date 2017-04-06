package vn.app.android.ordermanager.order.v101.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.order.ordermanager.R;

import vn.app.android.ordermanager.order.v101.common.V101OrderChooseClickListenner;

/**
 * Created by ASUS on 12/10/2016.
 */
@SuppressLint("ValidFragment")
public class V101OrderChoosePopup extends DialogFragment implements View.OnClickListener {

    public static String TAG = V101OrderChoosePopup.class.getName();
    private View mFragmentLayout;
    private TextView txtTitle;
    private LinearLayout btnOrder;
    private LinearLayout btnOrderChage;
    private LinearLayout btnInfo;
    private LinearLayout btnPay;
    private ImageView btnClose;
    private V101OrderChooseClickListenner mV101OrderChooseClickListenner;

    @SuppressLint("ValidFragment")
    public V101OrderChoosePopup(V101OrderChooseClickListenner chooseClickListenner){
        mV101OrderChooseClickListenner = chooseClickListenner;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.DialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentLayout = inflater.inflate(R.layout.v101_order_choose_popup, container, false);
        getMadatoryView();
        buildTitle();
        return mFragmentLayout;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        mV101OrderChooseClickListenner.chooseOrder(v.getId());
        dismiss();
    }

    /**
     * Get mandatory views. This should be called first after layout inflated.
     */
    public void getMadatoryView() {
        txtTitle = (TextView) mFragmentLayout.findViewById(R.id.v101_order_choose_txt_title);
        btnOrder = (LinearLayout) mFragmentLayout.findViewById(R.id.v101_order_choose_ln_order);
        btnOrderChage = (LinearLayout) mFragmentLayout.findViewById(R.id.v101_order_choose_ln_order_change);
        btnInfo = (LinearLayout) mFragmentLayout.findViewById(R.id.v101_order_choose_ln_view_info);
        btnPay = (LinearLayout) mFragmentLayout.findViewById(R.id.v101_order_choose_ln_order_payment);
        btnClose = (ImageView) mFragmentLayout.findViewById(R.id.v101_order_choose_img_close);

        btnOrder.setOnClickListener(this);
        btnOrderChage.setOnClickListener(this);
        btnPay.setOnClickListener(this);
        btnInfo.setOnClickListener(this);
        btnClose.setOnClickListener(this);


    }

    /**
     * Set title
     */
    private void buildTitle() {
        Bundle bundle = getArguments();
        String title = bundle.getString("NameTable");
        txtTitle.setText(title);

    }

}
