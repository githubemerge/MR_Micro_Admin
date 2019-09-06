package emerge.project.mrsolution_admin_micro.ui.adapters.location;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import emerge.project.mrsolution_admin_micro.R;
import emerge.project.mrsolution_admin_micro.utils.entittes.District;


/**
 * Created by Himanshu on 4/9/2015.
 */
public class LocationDistrictAdapter extends BaseAdapter {


    @BindView(R.id.textView)
    TextView textViewDistrict;

    Context mContext;

    ArrayList<District> districtArrayList;


    public LocationDistrictAdapter(Context context, ArrayList<District> item) {
        mContext = context;
        districtArrayList = item;

    }

    @Override
    public int getCount() {
        return districtArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return districtArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        View view;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.spinner_distric_item, null);


        } else {
            view = convertView;

        }

        ButterKnife.bind(this, view);

        textViewDistrict.setText(districtArrayList.get(position).getName());



        return view;
    }





}
