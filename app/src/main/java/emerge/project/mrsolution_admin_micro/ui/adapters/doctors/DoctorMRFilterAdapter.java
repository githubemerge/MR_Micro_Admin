package emerge.project.mrsolution_admin_micro.ui.adapters.doctors;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import emerge.project.mrsolution_admin_micro.R;
import emerge.project.mrsolution_admin_micro.ui.activity.doctors.DoctorsPresenter;
import emerge.project.mrsolution_admin_micro.ui.activity.doctors.DoctorsPresenterImpli;
import emerge.project.mrsolution_admin_micro.ui.activity.doctors.DoctorsView;

import emerge.project.mrsolution_admin_micro.utils.entittes.Rep;


/**
 * Created by Himanshu on 4/10/2015.
 */
public class DoctorMRFilterAdapter extends RecyclerView.Adapter<DoctorMRFilterAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<Rep> repItems;

    DoctorsPresenter doctorsPresenter;



    public DoctorMRFilterAdapter(Context mContext, ArrayList<Rep> item, DoctorsView doctorsView) {
        this.mContext = mContext;
        this.repItems = item;
        doctorsPresenter = new DoctorsPresenterImpli(doctorsView);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_visits_filter_dialog, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final  Rep rep =repItems.get(position);

        holder.textvieDocname.setText(rep.getName());

        if (rep.isSelect()) {
            holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorgold));
        } else {
            holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
        }

        holder.relativeLayoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rep.isSelect()){
                    repItems.get(position).setSelect(false);
                    doctorsPresenter.addRepToDoctorFilterStart(rep,false);
                }else {
                    repItems.get(position).setSelect(true);
                    doctorsPresenter.addRepToDoctorFilterStart(rep,true);
                }
                notifyDataSetChanged();
            }
        });



    }

    @Override
    public int getItemCount() {
        return repItems.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textview_doctor_name)
        TextView textvieDocname;

        @BindView(R.id.relativeLayout_header_main)
        RelativeLayout relativeLayoutMain;

        @BindView(R.id.card_view)
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


    }


}
