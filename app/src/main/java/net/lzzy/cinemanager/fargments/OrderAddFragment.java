package net.lzzy.cinemanager.fargments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.lzzy.cinemanager.R;

/**
 * Created by lzzy_gxy on 2019/3/27.
 * Description:
 */
public class OrderAddFragment extends BaseFragment {

    @Override
    protected void populate() {
  TextView tv=find(R.id.fragment_add_order_tv);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.cinemas_add_order;
    }
}

