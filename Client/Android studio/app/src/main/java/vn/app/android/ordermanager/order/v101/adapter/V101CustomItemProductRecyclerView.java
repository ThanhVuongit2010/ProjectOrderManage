package vn.app.android.ordermanager.order.v101.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.order.ordermanager.R;

import java.util.List;

import vn.app.android.ordermanager.mode.Product;

/**
 * Created by ASUS on 12/11/2016.
 */
public class V101CustomItemProductRecyclerView extends RecyclerView.Adapter<V101CustomItemProductRecyclerView.RecyclerViewHolder> {

    private List<Product> productList;
    private Context mContext;

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.v101_item_product_recyclerview, parent, false);
        return new RecyclerViewHolder(itemview);
    }
    public V101CustomItemProductRecyclerView(Context context, List<Product> list){
        this.mContext =context;
        this.productList = list;

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.txtNameProduct.setText(product.getmName());
        holder.txtMoneyProduct.setText(String.valueOf(product.getMoney()));
        holder.imgCheckProduct.setVisibility( product.getQuantity() == 0 ? View.VISIBLE : View.INVISIBLE);

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgProduct;
        private  ImageView imgCheckProduct;
        private TextView txtNameProduct;
        private  TextView txtMoneyProduct;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            imgCheckProduct = (ImageView) itemView.findViewById(R.id.v101_item_product_img_check_product);
            imgProduct = (ImageView) itemView.findViewById(R.id.v101_item_product_img_product);
            txtNameProduct = (TextView) itemView.findViewById(R.id.v101_item_product_txt_product_name);
            txtMoneyProduct = (TextView) itemView.findViewById(R.id.v101_item_product_txt_product_money_number);
        }
    }
}
