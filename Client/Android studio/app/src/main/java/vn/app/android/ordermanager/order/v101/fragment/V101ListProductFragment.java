package vn.app.android.ordermanager.order.v101.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.order.ordermanager.R;

import java.util.ArrayList;
import java.util.List;

import vn.app.android.ordermanager.mode.Product;
import vn.app.android.ordermanager.order.v101.adapter.V101CustomItemProductRecyclerView;
import vn.app.android.ordermanager.util.LogUtil;

/**
 * Created by ASUS on 12/11/2016.
 */
public class V101ListProductFragment extends Fragment {

    private View mFragmentLayout;
    private RecyclerView recyclerView;
    private V101CustomItemProductRecyclerView adapter;
    private List<Product> productList;

    public static String TAG = V101ListProductFragment.class.getName();

    public View getFragmentLayout(){
        return mFragmentLayout;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentLayout = inflater.inflate(R.layout.v101_list_product_fragment,container,false);
        getMandatoryViews();
        buldItemRecyclerView();
        return mFragmentLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * Get mandatory views. This should be called first after layout inflated.
     */
    private void getMandatoryViews() {
        recyclerView = (RecyclerView) mFragmentLayout.findViewById(R.id.v101_gv_list_product);
    }
    private void buldItemRecyclerView(){
        LogUtil.d(TAG,createData().toString());
        adapter = new V101CustomItemProductRecyclerView(getContext(),createData());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private List<Product> createData(){
        productList = new ArrayList<Product>() ;
        productList.add(new Product("001","Caffe đen",100,16000,1));
        productList.add(new Product("002","Caffe sữa",100,20000,3));
        productList.add(new Product("003","Sữa đá đập",100,36000,5));
        productList.add(new Product("004","NumberOne",0,10000,4));
        productList.add(new Product("005","Bạc xĩu",100,25000,2));
        productList.add(new Product("006","Caffe đen",100,16000,4));
        productList.add(new Product("007","Caffe đen",100,16000,1));
        productList.add(new Product("008"," Trà gừng",100,15000,3));
        productList.add(new Product("101","Caffe đen",100,16000,4));
        productList.add(new Product("102","Caffe sữa",100,20000,3));
        productList.add(new Product("103","Sữa đá đập",100,36000,10));
        productList.add(new Product("104","NumberOne",0,10000,15));
        productList.add(new Product("105","Bạc xĩu",100,25000,8));
        productList.add(new Product("106","Caffe đen",100,16000,9));
        productList.add(new Product("107","Caffe đen",100,16000,3));
        productList.add(new Product("108"," Trà gừng",100,15000,1));


        return productList;
    }
}
