package net.lzzy.cinemanager.fargments;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.Nullable;

import net.lzzy.cinemanager.R;
import net.lzzy.cinemanager.models.Order;
import net.lzzy.cinemanager.models.OrderFactory;
import net.lzzy.sqllib.GenericAdapter;
import net.lzzy.sqllib.ViewHolder;

import java.util.List;

/**
 * Created by lzzy_gxy on 2019/4/9.
 * Description:
 */
public class FragmentCinemtOrder extends BaseFragment  {
    public static final String ARG_CINEMA_ID = "argCinemaId";
    private String cinemaId;
    //region静态工厂方法传递数据
    public static FragmentCinemtOrder newInstance(String cinemaId ){
        FragmentCinemtOrder fragment =new FragmentCinemtOrder();
        Bundle args=new Bundle();
        args.putString(ARG_CINEMA_ID,cinemaId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            cinemaId=getArguments().getString(ARG_CINEMA_ID);
        }
    }
    @Override
    protected void populate() {
        ListView lv = find(R.id.activity_cinema_orders_lv);
        View empty = find(R.id.activity_cinemas_orders_tv_none);
        lv.setEmptyView(empty);
        List<Order> orders= OrderFactory.getInstance().getOrdersByCinema(cinemaId);
        GenericAdapter<Order> adapter=new GenericAdapter<Order>(getActivity(),R.layout.order_item,orders) {
            @Override
            public void populate(ViewHolder viewHolder, Order order) {
                viewHolder.setTextView(R.id.order_items_name, order.getMovie())
                        .setTextView(R.id.order_items_location, order.getMovieTime());
            }

            @Override
            public boolean persistInsert(Order order) {
                return false;
            }

            @Override
            public boolean persistDelete(Order order) {
                return false;
            }
        };
        lv.setAdapter(adapter);

    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_cinemas_order;
    }

    @Override
    public void search(String kw) {

    }
}
