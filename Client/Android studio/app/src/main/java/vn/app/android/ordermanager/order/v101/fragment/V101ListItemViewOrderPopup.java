package vn.app.android.ordermanager.order.v101.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.order.ordermanager.R;

import java.util.ArrayList;
import java.util.List;

import vn.app.android.ordermanager.mode.Product;
import vn.app.android.ordermanager.order.v101.adapter.V101CustomItemViewOrderRecylerView;
import vn.app.android.ordermanager.util.LogUtil;

/**
 * Created by ASUS on 1/8/2017.
 */
public class V101ListItemViewOrderPopup extends DialogFragment {

    public static String TAG = V101ListItemViewOrderPopup.class.getName();
    private View mFragmentLayout;
    private TextView txtTitle;
    private TextView txtToalMoney;
    private RecyclerView recyclerView;
    private List<Product> productList;
    private V101CustomItemViewOrderRecylerView adapter;

    public V101ListItemViewOrderPopup(){

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.DialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentLayout = inflater.inflate(R.layout.v101_list_item_view_order_popup, container, false);
        getMadatoryView();
        buildTitle();
        buldItemRecyclerView();
        buildTotalMoney();
        return mFragmentLayout;
    }
    /**
     * Get mandatory views. This should be called first after layout inflated.
     */
    public void getMadatoryView() {
        txtTitle = (TextView) mFragmentLayout.findViewById(R.id.v101_list_item_view_order_popup_table_name);
        recyclerView = (RecyclerView) mFragmentLayout.findViewById(R.id.v101_list_item_view_order_popup_list_order);
        txtToalMoney = (TextView) mFragmentLayout.findViewById(R.id.v101_list_item_view_order_total_money_sum);
    }

    /**
     *
     */
    private void buldItemRecyclerView(){
        LogUtil.d(TAG,createData().toString());
        adapter = new V101CustomItemViewOrderRecylerView(getContext(),createData());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Set title
     */
    private void buildTitle() {
        Bundle bundle = getArguments();
        String title = bundle.getString("NameTable");
        txtTitle.setText(title);

    }
    private  void buildTotalMoney(){
        long totalMoney = 0;
        for(Product product : productList){
            totalMoney += product.getMoney() * product.getNumberOrder();
        }
        txtToalMoney.setText(String.valueOf(totalMoney));
    }
    private List<Product> createData() {
        productList = new ArrayList<Product>();
        productList.add(new Product("001", "Caffe đen", 100, 16000, 1));
        productList.add(new Product("002", "Caffe sữa", 100, 20000, 3));
        productList.add(new Product("003", "Sữa đá đập", 100, 36000, 5));
        productList.add(new Product("004", "NumberOne", 0, 10000, 4));
        productList.add(new Product("005", "Bạc xĩu", 100, 25000, 2));
        productList.add(new Product("006", "Caffe đen", 100, 16000, 4));
        productList.add(new Product("007", "Caffe đen", 100, 16000, 1));
        productList.add(new Product("008", " Trà gừng", 100, 15000, 3));
        productList.add(new Product("101", "Caffe đen", 100, 16000, 4));
        productList.add(new Product("102", "Caffe sữa", 100, 20000, 3));
        productList.add(new Product("103", "Sữa đá đập", 100, 36000, 10));
        productList.add(new Product("104", "NumberOne", 0, 10000, 15));
        productList.add(new Product("105", "Bạc xĩu", 100, 25000, 8));
        productList.add(new Product("106", "Caffe đen", 100, 16000, 9));
        productList.add(new Product("107", "Caffe đen", 100, 16000, 3));
        productList.add(new Product("108", " Trà gừng", 100, 15000, 1));
        return  productList;
    }

}
