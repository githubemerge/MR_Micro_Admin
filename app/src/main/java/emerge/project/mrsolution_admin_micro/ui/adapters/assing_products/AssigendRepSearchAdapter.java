package emerge.project.mrsolution_admin_micro.ui.adapters.assing_products;


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
import emerge.project.mrsolution_admin_micro.utils.entittes.Rep;


/**
 * Created by Himanshu on 4/9/2015.
 */
public class AssigendRepSearchAdapter extends BaseAdapter {


    @BindView(R.id.textView)
    TextView textViewDistrict;

    Context mContext;

    ArrayList<Rep> repList;


    public AssigendRepSearchAdapter(Context context, ArrayList<Rep> item) {
        mContext = context;
        repList = item;

    }

    @Override
    public int getCount() {
        return repList.size();
    }

    @Override
    public Object getItem(int i) {
        return repList.get(i);
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
            view = inflater.inflate(R.layout.adaptr_assignproduct_rep_item, null);
        } else {
            view = convertView;
        }

        ButterKnife.bind(this, view);

        textViewDistrict.setText(repList.get(position).getName());

        return view;
    }





}
