package net.lzzy.cinemanager.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import net.lzzy.cinemanager.R;
import net.lzzy.cinemanager.fargments.BaseFragment;
import net.lzzy.cinemanager.fargments.CinemasAddFragment;
import net.lzzy.cinemanager.fargments.CinemasFragment;
import net.lzzy.cinemanager.fargments.OnFragmentInteractiorlistener;
import net.lzzy.cinemanager.fargments.OrderAddFragment;
import net.lzzy.cinemanager.fargments.OrdersFragment;
import net.lzzy.cinemanager.models.Cinema;
import net.lzzy.cinemanager.models.Order;
import net.lzzy.cinemanager.utils.ViewUtils;
import net.lzzy.simpledatepicker.CustomDatePicker;

import java.util.List;

/**
 * @author Administrator
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnFragmentInteractiorlistener,
        CinemasAddFragment.OnCinemaCreatedListener, OrderAddFragment.OnOrderCreatedListener, CinemasFragment.OnCinemaSelectedListener {
    private static final String CINEMA_ID = "cinemaId";
    private FragmentManager manager = getSupportFragmentManager();
    private LinearLayout layoutMenu;
    private TextView tvTitle;
    private SearchView search;
    private CustomDatePicker picker;
    private List<Cinema> cinemas;
    private List<Order> orders;
    private Spinner spCinema;

    private SparseArray<String> titleArray = new SparseArray<>();
    private SparseArray<Fragment> fragmentArray = new SparseArray<>();
    private Fragment addCinemaFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        setTitleMenu();
        search.setOnQueryTextListener(new ViewUtils.AbstractQueryHandler() {
            @Override
            public boolean handleQuery(String kw) {
                Fragment fragment = manager.findFragmentById(R.id.fragment_container);
                if (fragment != null) {
                    if (fragment instanceof BaseFragment) {
                        ((BaseFragment) fragment).search(kw);
                    }
                }
                return true;
            }
        });
    }

    private void setTitleMenu() {
        titleArray.put(R.id.bar_title_tv_add_cinema, "添加影院");
        titleArray.put(R.id.bar_title_tv_view_cinema, "影院列表");
        titleArray.put(R.id.bar_title_tv_add_order, "添加订单");
        titleArray.put(R.id.bar_title_tv_view_order, "我的订单");

        layoutMenu = findViewById(R.id.bar_title_layout_menu);
        layoutMenu.setVisibility(View.GONE);
        findViewById(R.id.bar_title_img_menu).setOnClickListener(v -> {
            int visible = layoutMenu.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
            layoutMenu.setVisibility(visible);
        });
        tvTitle = findViewById(R.id.bar_title_tv_title);
        tvTitle.setText(R.string.bar_title_menu_orders);
        search = findViewById(R.id.bar_title_search);
        findViewById(R.id.bar_title_tv_add_cinema).setOnClickListener(this);
        findViewById(R.id.bar_title_tv_view_cinema).setOnClickListener(this);
        findViewById(R.id.bar_title_tv_add_order).setOnClickListener(this);
        findViewById(R.id.bar_title_tv_view_order).setOnClickListener(this);
        findViewById(R.id.bar_title_tv_exit).setOnClickListener(v -> {
            System.exit(0);
        });
    }

    @Override
    public void onClick(View v) {
        search.setVisibility(View.VISIBLE);
        layoutMenu.setVisibility(View.GONE);
        tvTitle.setText(titleArray.get(v.getId()));
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = fragmentArray.get(v.getId());
        if (fragment == null) {
            fragment = createFragment(v.getId());
            fragmentArray.put(v.getId(), fragment);
            transaction.add(R.id.fragment_container, fragment);
        }
        for (Fragment f : manager.getFragments()) {
            transaction.hide(f);
        }
        transaction.show(fragment).commit();


    }


    private Fragment createFragment(int id) {
        switch (id) {
            case R.id.bar_title_tv_add_cinema:
                return new CinemasAddFragment();

            case R.id.bar_title_tv_view_cinema:
                return new CinemasFragment();
            case R.id.bar_title_tv_add_order:
                return new OrderAddFragment();

            case R.id.bar_title_tv_view_order:
                return new OrdersFragment();
            default:
                break;

        }
        return null;
    }

    @Override
    public void hideSearch() {
        search.setVisibility(View.INVISIBLE);
    }

    @Override
    public void cancelAddCinema() {
        Fragment addCinemaFragment = fragmentArray.get(R.id.bar_title_tv_view_cinema);
        if (addCinemaFragment == null) {
            return;
        }
        Fragment cinemasFragment = fragmentArray.get(R.id.bar_title_tv_view_cinema);
        FragmentTransaction transaction = manager.beginTransaction();
        if (cinemasFragment == null) {
            cinemasFragment = new CinemasFragment();
            fragmentArray.put(R.id.bar_title_tv_view_cinema, cinemasFragment);
            transaction.add(R.id.fragment_container, cinemasFragment);
        }
        transaction.hide(addCinemaFragment).show(cinemasFragment).commit();
        tvTitle.setText(titleArray.get(R.id.bar_title_tv_view_cinema));
    }

    @Override
    public void saveCinema(Cinema cinema) {
        Fragment addCinemaFragment = fragmentArray.get(R.id.bar_title_tv_add_cinema);
        if (addCinemaFragment == null) {
            return;
        }
        Fragment cinemasFragment = fragmentArray.get(R.id.bar_title_tv_view_cinema);
        FragmentTransaction transaction = manager.beginTransaction();
        if (cinemasFragment == null) {
            cinemasFragment = new CinemasFragment();
            fragmentArray.put(R.id.bar_title_tv_view_cinema, cinemasFragment);//创建CinemasFragment
            transaction.add(R.id.fragment_container, cinemasFragment);
        } else {
            ((CinemasFragment) cinemasFragment).save(cinema);
        }
        transaction.hide(addCinemaFragment).show(cinemasFragment).commit();
        tvTitle.setText(titleArray.get(R.id.bar_title_tv_view_cinema));
    }

    @Override
    public void cancelAddOrder() {
        Fragment addOrderFragment=fragmentArray.get(R.id.bar_title_tv_add_order);
        if (addOrderFragment==null){
            return;
        }
        Fragment orderFragment=fragmentArray.get(R.id.bar_title_tv_view_order);
        FragmentTransaction transaction=manager.beginTransaction();
        if (orderFragment==null){
            orderFragment=new CinemasFragment();
            fragmentArray.put(R.id.bar_title_tv_view_order,orderFragment);
            transaction.add(R.id.fragment_container,orderFragment);
        }
        transaction.hide(addOrderFragment).show(orderFragment).commit();
        tvTitle.setText(titleArray.get(R.id.bar_title_tv_view_order));

    }

    @Override
    public void saveOrder(Order order) {
        Fragment addOrdersFragment=fragmentArray.get(R.id.bar_title_tv_add_order);
        if (addOrdersFragment==null){
            return;
        }
        Fragment orderFragment=fragmentArray.get(R.id.bar_title_tv_view_order);
        FragmentTransaction transaction=manager.beginTransaction();
        if (orderFragment==null){
            orderFragment=new OrdersFragment(order);
            fragmentArray.put(R.id.bar_title_tv_view_order,orderFragment);
            transaction.add(R.id.fragment_container,orderFragment);
        }else {
            ((OrdersFragment)orderFragment).save(order);

        }
        transaction.hide(addOrdersFragment).show(orderFragment).commit();
        tvTitle.setText(titleArray.get(R.id.bar_title_tv_view_order));
    }

    @Override
    public void onCinemaSelected(String cinemaId) {
        Intent intent = new Intent(this, CinemaOrdersactivity.class);
        intent.putExtra(CINEMA_ID, cinemaId);
        startActivity(intent);
    }
}

