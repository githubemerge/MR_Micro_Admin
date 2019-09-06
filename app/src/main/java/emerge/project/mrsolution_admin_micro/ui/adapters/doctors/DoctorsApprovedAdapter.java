package emerge.project.mrsolution_admin_micro.ui.adapters.doctors;


import android.content.Context;
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
import emerge.project.mrsolution_admin_micro.utils.entittes.Doctor;


/**
 * Created by Himanshu on 4/10/2015.
 */
public class DoctorsApprovedAdapter extends RecyclerView.Adapter<DoctorsApprovedAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<Doctor> doctorItems;

    DoctorsPresenter doctorsPresenter;

    public DoctorsApprovedAdapter(Context mContext, ArrayList<Doctor> item, DoctorsView doctorsView) {
        this.mContext = mContext;
        this.doctorItems = item;
        doctorsPresenter = new DoctorsPresenterImpli(doctorsView);

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_doctors_approved, parent, false);
        return new MyViewHolder(itemView);


    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final Doctor doctor = doctorItems.get(position);


        holder.textviewDoctorsName.setText(doctor.getName());
        holder.textviewRep.setText(doctor.getCreatedByName());
        holder.textviewSpec.setText(doctor.getSpecialization());
        holder.textviewLocation.setText(doctor.getLocation());


        holder.relativeLayoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doctorsPresenter.getDoctorApproveFullDetais(doctor);
            }
        });



    }


    @Override
    public int getItemCount() {
        return doctorItems.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.relativeLayout_header_main)
        RelativeLayout relativeLayoutMain;

        @BindView(R.id.textview_doctors_name)
        TextView textviewDoctorsName;

        @BindView(R.id.textview_doctors_spec)
        TextView textviewSpec;

        @BindView(R.id.textview_rep)
        TextView textviewRep;

        @BindView(R.id.textview_location)
        TextView textviewLocation;




        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

    }


}
