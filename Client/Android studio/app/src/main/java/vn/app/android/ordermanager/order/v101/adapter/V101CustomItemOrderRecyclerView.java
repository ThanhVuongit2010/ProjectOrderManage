package vn.app.android.ordermanager.order.v101.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.order.ordermanager.R;

import java.util.ArrayList;
import java.util.List;

import vn.app.android.ordermanager.mode.TableOrder;
import vn.app.android.ordermanager.order.v101.common.V101OrderRecyclerViewClickListener;

/**
 * Created by ASUS on 12/4/2016.
 */

public class V101CustomItemOrderRecyclerView extends RecyclerView.Adapter<V101CustomItemOrderRecyclerView.RecyclerViewHolder> {

    private List<TableOrder> listTableOrder = new ArrayList<>();
    private V101OrderRecyclerViewClickListener mItemListener;

    public V101CustomItemOrderRecyclerView(List<TableOrder> listData,V101OrderRecyclerViewClickListener itemListener) {
        this.listTableOrder = listData;
        this.mItemListener = itemListener;
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.v101_item_order_recyclerview, parent, false);
        return new RecyclerViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        TableOrder tableOrder = listTableOrder.get(position);
        holder.txtTableName.setText(tableOrder.getmName());
        holder.imgTable.setImageResource((tableOrder.getIsUsing()==0 ? R.drawable.icon_table: R.drawable.icon_table_using));
    }
    @Override
    public int getItemCount() {
        return listTableOrder.size();
    }
    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        TextView txtTableName;
        ImageView imgTable;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txtTableName = (TextView) itemView.findViewById(R.id.v101_item_order_txt_table_name);
            imgTable = (ImageView) itemView.findViewById(R.id.v101_item_order_img_table);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mItemListener.recyclerViewListClicked(v,this.getLayoutPosition());

        }
    }
}
