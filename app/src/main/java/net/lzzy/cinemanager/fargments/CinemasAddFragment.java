package net.lzzy.cinemanager.fargments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.style.cityjd.JDCityPicker;

import net.lzzy.cinemanager.R;
import net.lzzy.cinemanager.models.Cinema;

/**
 * Created by lzzy_gxy on 2019/3/27.
 * Description:
 */
public class CinemasAddFragment extends BaseFragment {
private String province="广西壮族自治区";
    private String city="柳州市";
    private String area="鱼峰区";
    @Override
    protected void populate() {
      TextView tvArea=find(R.id.dialog_add_tv_area);
        EditText edtName=find(R.id.dialog_add_cinema_edt_name);
        find(R.id.dialog_add_cinema_layout_area).setOnClickListener(v->{
            JDCityPicker cityPicker=new JDCityPicker();
            cityPicker.init(getActivity());
            cityPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
                @Override
                public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                    CinemasAddFragment.this.province = province.getName();
                    CinemasAddFragment.this.city = city.getName();
                    CinemasAddFragment.this.area = district.getName();
                    String loc = province.getName() + city.getName() + district.getName();
                    tvArea.setText(loc);
                }

                @Override
                public void onCancel() {
                }
            });
            cityPicker.showCityPicker();
        });
        find(R.id.dialog_add_cinema_btn_save).setOnClickListener(v -> {
            String name = edtName.getText().toString();
            Cinema cinema = new Cinema();
            cinema.setName(name);
            cinema.setArea(area);
            cinema.setCity(city);
            cinema.setProvince(province);
            cinema.setLocation(tvArea.getText().toString());
            edtName.setText("");
        });

    }

    @Override
    public int getLayoutRes() {
        return R.layout.cinemas_add_cinemas;
    }
}

