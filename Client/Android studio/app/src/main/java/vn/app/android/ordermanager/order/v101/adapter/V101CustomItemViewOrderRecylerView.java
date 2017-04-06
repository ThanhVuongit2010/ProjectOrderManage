package vn.app.android.ordermanager.order.v101.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.order.ordermanager.R;

import java.util.List;

import vn.app.android.ordermanager.mode.Product;

/**
 * Created by ASUS on 1/8/2017.
 */
public class V101CustomItemViewOrderRecylerView extends RecyclerView.Adapter<V101CustomItemViewOrderRecylerView.RecyclerViewHolder> {

    private List<Product> productList;
    private Context mContext;
    private long totalMoney;

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.v101_item_view_order_recelerview, parent, false);
        return new RecyclerViewHolder(itemview);
    }
    public V101CustomItemViewOrderRecylerView(Context context, List<Product> list){
        this.mContext =context;
        this.productList = list;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
//        if(position == 0){
//            holder.txtProductName.setText("Sản phẩm");
//            holder.txtMoneyProduct.setText("Số lượng");
//            holder.txtProductNumber.setText("Đơn giá");
//            holder.txtTotalMoney.setText("Thành tiền");
//        }else if(position == productList.size()-1){
//            holder.txtProductName.setText("Tổng tiền");
//            holder.txtProductName.setTextSize(24);
//            holder.txtProductName.setTypeface(null, Typeface.BOLD);
//            holder.txtMoneyProduct.setVisibility(View.GONE);
//            holder.txtProductNumber.setVisibility(View.GONE);
//            holder.txtTotalMoney.setText(String.valueOf(totalMoney));
//            holder.txtTotalMoney.setTextSize(24);
//            holder.txtTotalMoney.setTypeface(null, Typeface.BOLD);
//        }else{
//            Product product = productList.get(position);
//            holder.txtProductName.setText(product.getmName());
//            holder.txtMoneyProduct.setText(String.valueOf(product.getMoney()));
//            holder.txtProductNumber.setText(String.valueOf(product.getNumberOrder()));
//            holder.txtTotalMoney.setText(String.valueOf(product.getMoney()* product.getNumberOrder()));
//            totalMoney += product.getMoney()* product.getNumberOrder();
//        }
        Product product = productList.get(position);
        holder.txtProductName.setText(position+" . "+product.getmName());
        holder.txtMoneyProduct.setText(String.valueOf(product.getMoney()));
        holder.txtProductNumber.setText(String.valueOf(product.getNumberOrder()));
        holder.txtTotalMoney.setText(String.valueOf(product.getMoney()* product.getNumberOrder()));
        totalMoney += product.getMoney()* product.getNumberOrder();

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        private TextView txtProductName;
        private TextView txtProductNumber;
        private TextView txtMoneyProduct;
        private  TextView txtTotalMoney;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txtProductName = (TextView) itemView.findViewById(R.id.v101_item_view_order_product_name);
            txtProductNumber = (TextView) itemView.findViewById(R.id.v101_item_view_order_product_number);
            txtMoneyProduct = (TextView) itemView.findViewById(R.id.v101_item_view_order_product_money);
            txtTotalMoney = (TextView) itemView.findViewById(R.id.v101_item_view_order_total_money);
        }
    }
}
