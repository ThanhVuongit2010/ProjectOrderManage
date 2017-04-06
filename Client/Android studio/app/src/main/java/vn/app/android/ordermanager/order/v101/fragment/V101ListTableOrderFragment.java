package vn.app.android.ordermanager.order.v101.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.order.ordermanager.R;

import java.util.ArrayList;
import java.util.List;

import vn.app.android.ordermanager.base.FragmentHelper;
import vn.app.android.ordermanager.extra.FragmentTransitionInfo;
import vn.app.android.ordermanager.mode.TableOrder;
import vn.app.android.ordermanager.order.v101.adapter.V101CustomItemOrderRecyclerView;
import vn.app.android.ordermanager.order.v101.common.V101OrderChooseClickListenner;
import vn.app.android.ordermanager.order.v101.common.V101OrderRecyclerViewClickListener;
import vn.app.android.ordermanager.util.LogUtil;
import vn.app.android.ordermanager.util.TaskUtil;

public class V101ListTableOrderFragment extends Fragment implements V101OrderRecyclerViewClickListener, V101OrderChooseClickListenner {

    public static String TAG = V101ListTableOrderFragment.class.getName();
    private View mFragmentLayout;
    private GridView gvTableOrder;
    private RecyclerView recyclerView;
    V101CustomItemOrderRecyclerView adapter;
    List<TableOrder> data;
    private TableOrder tableOrder;

    /**
     * Get fragment layout
     */
    public View getFragmentLayout() {
        return mFragmentLayout;
    }

    //--------------------------------------------------------------------------------------------------------------------
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentLayout = inflater.inflate(R.layout.v101_list_table_order_fragment, container, false);
        getMandatoryViews();
        builItemOrderRecyclerView();

        return mFragmentLayout;
    }

    /**
     * Get mandatory views. This should be called first after layout inflated.
     */
    private void getMandatoryViews() {
        recyclerView = (RecyclerView) mFragmentLayout.findViewById(R.id.v101_gv_list_table);
    }

    /**
     * add recerleView to fragment
     */
    private void builItemOrderRecyclerView() {
        adapter = new V101CustomItemOrderRecyclerView(createData(), this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        tableOrder = createData().get(position);
        LogUtil.d(TAG, tableOrder.toString());
        if (tableOrder.getIsUsing() == 1) {
            Toast.makeText(getActivity(), "Bàn đã có người dùng", Toast.LENGTH_SHORT).show();
            ;
        } else {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            V101OrderChoosePopup orderChoosePopup = new V101OrderChoosePopup(this);
            Bundle bundle = new Bundle();
            bundle.putString("NameTable", tableOrder.getmName());
            orderChoosePopup.setArguments(bundle);
            orderChoosePopup.show(fm, "V101OrderChoosePopup");

        }

    }

    @Override
    public void chooseOrder(int id) {
        switch (id) {
            case R.id.v101_order_choose_img_close:
                Toast.makeText(getActivity(), "order_choose_img_close--" + tableOrder.getmName(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.v101_order_choose_ln_order:
                Toast.makeText(getActivity(), "order_choose_ln_order--", Toast.LENGTH_SHORT).show();
                passingProductFragment();
                break;
            case R.id.v101_order_choose_ln_order_change:
                break;
            case R.id.v101_order_choose_ln_view_info:
                passingListItemViewOrderPopup();
                break;
            case R.id.v101_order_choose_ln_order_payment:
                break;
        }
    }

    /**
     * pass Product Fragment
     */
    private void passingProductFragment(){
        String fragmentTag = V101ListProductFragment.TAG;
        FragmentTransitionInfo transactionInfo = TaskUtil.getLeftToRightTransition();
        FragmentHelper.replaceChildFragment(getParentFragment(),new V101ListProductFragment(),R.id.v101_fr_all_funtion,fragmentTag,transactionInfo);
    }
    private void passingListItemViewOrderPopup(){
        V101ListItemViewOrderPopup v101ListItemViewOrderPopup = new V101ListItemViewOrderPopup();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putString("NameTable", tableOrder.getmName());
        v101ListItemViewOrderPopup.setArguments(bundle);
        v101ListItemViewOrderPopup.show(fm, V101ListItemViewOrderPopup.TAG);

    }


    public List<TableOrder> createData() {
        data = new ArrayList<>();
        data.add(new TableOrder("001", "Bàn 001", 0));
        data.add(new TableOrder("101", "Bàn 101", 0));
        data.add(new TableOrder("201", "Bàn 201", 0));
        data.add(new TableOrder("301", "Bàn 301", 1));
        data.add(new TableOrder("401", "Bàn 401", 0));
        data.add(new TableOrder("501", "Bàn 501", 1));
        data.add(new TableOrder("601", "Bàn 601", 0));
        data.add(new TableOrder("701", "Bàn 701", 1));
        data.add(new TableOrder("801", "Bàn 801", 0));
        data.add(new TableOrder("601", "Bàn 601", 0));
        data.add(new TableOrder("701", "Bàn 701", 1));
        data.add(new TableOrder("801", "Bàn 801", 0));
        data.add(new TableOrder("001", "Bàn 001", 0));
        data.add(new TableOrder("101", "Bàn 101", 0));
        data.add(new TableOrder("201", "Bàn 201", 0));
        data.add(new TableOrder("301", "Bàn 301", 1));
        data.add(new TableOrder("401", "Bàn 401", 0));
        data.add(new TableOrder("501", "Bàn 501", 1));
        data.add(new TableOrder("601", "Bàn 601", 0));
        data.add(new TableOrder("701", "Bàn 701", 1));
        data.add(new TableOrder("801", "Bàn 801", 0));
        data.add(new TableOrder("601", "Bàn 601", 0));
        data.add(new TableOrder("701", "Bàn 701", 1));
        data.add(new TableOrder("801", "Bàn 801", 0));

        return data;
    }


}
