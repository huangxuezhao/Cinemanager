package net.lzzy.cinemanager.fargments;

import android.content.Context;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.lzzy.cinemanager.R;
import net.lzzy.cinemanager.models.Cinema;
import net.lzzy.cinemanager.models.CinemaFactory;
import net.lzzy.sqllib.GenericAdapter;
import net.lzzy.sqllib.ViewHolder;

import java.util.List;

/**
 * Created by lzzy_gxy on 2019/3/26.
 * Description:
 */
public class CinemasFragment extends BaseFragment {
    private ListView lv;

    private List<Cinema> cinemas;
    private CinemaFactory factory = CinemaFactory.getInstance();
  private GenericAdapter<Cinema>adapter;
    private OnCinemaSelectedListener listener;

    public static CinemaFactory getInstance() {
        return null;
    }

    @Override
    protected void populate() {
        lv = find(R.id.activity_cinema_lv);
        View empty = find(R.id.activity_cinemas_tv_none);
        lv.setEmptyView(empty);
        cinemas = factory.get();
        adapter = new GenericAdapter<Cinema>(getActivity(),
                R.layout.cinemas_item, cinemas) {
            @Override
            public void populate(ViewHolder holder, Cinema cinema) {
                holder.setTextView(R.id.cinemas_items_name, cinema.getName())
                        .setTextView(R.id.cinemas_items_location, cinema.getLocation());
            }

            @Override
            public boolean persistInsert(Cinema cinema) {
                return factory.addCinema(cinema);
            }

            @Override
            public boolean persistDelete(Cinema cinema) {
                return factory.deleteCinema(cinema);
            }

        };
        lv.setAdapter(adapter);

    }
    public void save(Cinema cinema){
        adapter.add(cinema);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_cinemas;
    }

    @Override
    public void search(String kw) {
        cinemas.clear();
        if(TextUtils.isEmpty(kw)){
            cinemas.addAll(factory.get());
        }else {
            cinemas.addAll(factory.searchCinemas(kw));

        }
        adapter.notifyDataSetChanged();
    }
    public interface OnCinemaSelectedListener{
        void onCinemaSelected(String cinemaId);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCinemaSelectedListener){
            listener=(OnCinemaSelectedListener) context;

        }else {
            throw new ClassCastException(context.toString()+"必需实现OnCinemaSelectedListener");

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
    }

    }


