package net.lzzy.cinemanager.fargments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.lzzy.cinemanager.R;
import net.lzzy.cinemanager.models.CinemaFactory;
import net.lzzy.cinemanager.models.Order;
import net.lzzy.cinemanager.models.OrderFactory;
import net.lzzy.sqllib.GenericAdapter;
import net.lzzy.sqllib.ViewHolder;

import java.util.List;

/**
 * Created by lzzy_gxy on 2019/3/26.
 * Description:
 */
public class OrdersFragment extends BaseFragment {
    private ListView lv;
    private List<Order> orders;
    private OrderFactory factory = OrderFactory.getInstance();
    private GenericAdapter<Order> adapter;
    private Order order;

    public OrdersFragment() {

    }
    public OrdersFragment(Order order){
        this.order=order;
    }


    @Override
    protected void populate() {
        lv = find(R.id.activity_main_lv);
        View empty=find(R.id.activity_main_tv_none);
        lv.setEmptyView(empty);
        orders=factory.get();

        adapter = new GenericAdapter<Order>(getContext(), R.layout.order_item, orders) {
            @Override
            public void populate(ViewHolder viewHolder, Order order) {
                String location = String.valueOf(CinemaFactory.getInstance().getById(order.getCinemaId().toString()));viewHolder.setTextView(R.id.order_items_name, order.getMovie())
                        .setTextView(R.id.order_items_location, location);
                Button btn = viewHolder.getView(R.id.main_item_btn);
                btn.setOnClickListener(v -> new AlertDialog.Builder(getContext())
                        .setTitle("删除确认")
                        .setMessage("确定删除吗？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确认", (dialog, which) ->
                                adapter.remove(order)).show());
                viewHolder.getConvertView().setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return false;
                    }
                });
            }

            @Override
            public boolean persistInsert(Order order) {
                return factory.addOrder(order);
            }

            @Override
            public boolean persistDelete(Order order) {
                return factory.delete(order);
            }
        };
        lv.setAdapter(adapter);
        if (order != null) {
            save(order);
        }

    }

    public void save(Order order) {
        adapter.add(order);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_orders;
    }

    @Override
    public void search(String kw) {

    }
}
