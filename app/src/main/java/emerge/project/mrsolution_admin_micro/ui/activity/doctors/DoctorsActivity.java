package emerge.project.mrsolution_admin_micro.ui.activity.doctors;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import emerge.project.mrsolution_admin_micro.R;
import emerge.project.mrsolution_admin_micro.ui.activity.locations.LocationActivity;
import emerge.project.mrsolution_admin_micro.ui.activity.products.assign_products.AssignProductsActivity;
import emerge.project.mrsolution_admin_micro.ui.activity.visits.VisitsActivity;
import emerge.project.mrsolution_admin_micro.ui.adapters.doctors.DoctorMRFilterAdapter;
import emerge.project.mrsolution_admin_micro.ui.adapters.doctors.DoctorsApprovedAdapter;
import emerge.project.mrsolution_admin_micro.ui.adapters.doctors.DoctorsPendingAdapter;
import emerge.project.mrsolution_admin_micro.ui.adapters.doctors.DocLocationDoctorsAdapter;
import emerge.project.mrsolution_admin_micro.ui.adapters.doctors.DocLocationLocationAdapter;
import emerge.project.mrsolution_admin_micro.ui.adapters.doctors.SpecializationAdapter;
import emerge.project.mrsolution_admin_micro.ui.adapters.navigation.NavigationAdapter;
import emerge.project.mrsolution_admin_micro.utils.entittes.Doctor;
import emerge.project.mrsolution_admin_micro.utils.entittes.LocationEntitie;
import emerge.project.mrsolution_admin_micro.utils.entittes.Navigation;
import emerge.project.mrsolution_admin_micro.utils.entittes.Rep;
import emerge.project.mrsolution_admin_micro.utils.entittes.Specialization;

public class DoctorsActivity extends Activity implements DoctorsView {


    @BindView(R.id.drawer_layout)
    DrawerLayout dLayout;


    @BindView(R.id.recyclerView_doctors)
    RecyclerView recyclerViewDoctors;

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationView bottomNavigationBar;

    @BindView(R.id.relativelayout_pending)
    RelativeLayout relativelayoutPending;

    @BindView(R.id.relativelayout_approved)
    RelativeLayout relativelayoutApproved;

    @BindView(R.id.relativelayout_assign)
    RelativeLayout relativelayoutAssign;



    @BindView(R.id.recyclerview_doc)
    RecyclerView recyclerviewDocforAssign;


    @BindView(R.id.recyclerview_locations)
    RecyclerView recyclerviewLocations;

    @BindView(R.id.recyclerview_spec)
    RecyclerView recyclerviewSpec;





    @BindView(R.id.relativeLayout_assigndetails)
    RelativeLayout relativeLayoutAssigndetails;


    @BindView(R.id.autoCompleteTextView_loc)
    AutoCompleteTextView textViewLoc;

    @BindView(R.id.autoCompleteTextView_doc)
    AutoCompleteTextView textViewDoc;

    @BindView(R.id.autoCompleteTextView_spec)
    AutoCompleteTextView textViewSpec;


    RecyclerView recyclerViewDocFilterRep;



    Dialog dialogPendingDoctor;
    Dialog dialogFilter;




    @BindView(R.id.include_progres)
    View includeProgres;

    @BindView(R.id.include_nointernt)
    View includeNointernt;

    @BindView(R.id.listview_navigation)
    ListView listViewNavigation;





    DoctorsPendingAdapter doctorsPendingAdapter;
    DoctorsApprovedAdapter doctorsApprovedAdapter;
    SpecializationAdapter specializationAdapter;
    DocLocationDoctorsAdapter docLocationDoctorsAdapter;
    DocLocationLocationAdapter docLocationLocationAdapter;


    NavigationAdapter navigationAdapter;


    int selectDoctorID;
    ArrayList<LocationEntitie> addedlocationsArrayList;
    ArrayList<Specialization> addedSpecArrayList;


    ArrayList<Doctor> doctorsList;

    ArrayList<LocationEntitie> locationEntitieList;
    ArrayList<Specialization> specializationList;



    ArrayList<Doctor> pendingDoctorsList;
    ArrayList<Doctor> approvedDoctorsList;

    ArrayList<String> pendingDoctorsNameFilterList;
    ArrayList<String> pendingDoctorsPhoneNumbersFilterList;
    ArrayList<String> pendingDoctorsRegNumbersFilterList;


    ArrayList<String> approvedDoctorsNameFilterList;
    ArrayList<String> approvedDoctorsPhoneNumbersFilterList;
    ArrayList<String> approvedDoctorsRegNumbersFilterList;



    ArrayList<Rep> addedRepToPendingDoctorsFilterList;
    ArrayList<Rep> addedRepToapprovedDoctorsFilterList;


    ArrayList<Rep> pendingDoctorsMRFilterList;
    ArrayList<String> pendingDoctorsnMRNameFilterList;

    ArrayList<Rep> approvedDoctorsMRFilterList;
    ArrayList<String> approvedDoctorsnMRNameFilterList;





    boolean isPending=true;

    int sdk;

    DoctorsPresenter doctorsPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);
        ButterKnife.bind(this);

        sdk = android.os.Build.VERSION.SDK_INT;

        setDoctorsRecycalView();
        setDoctorsForAssigRecycalView();
        setLocationsRecycalView();
        setSpecRecycalView();


        addedlocationsArrayList = new ArrayList<LocationEntitie>();
        addedSpecArrayList = new ArrayList<Specialization>();

        pendingDoctorsNameFilterList = new ArrayList<>();
        pendingDoctorsPhoneNumbersFilterList = new ArrayList<>();
        pendingDoctorsRegNumbersFilterList = new ArrayList<>();


        addedRepToPendingDoctorsFilterList = new ArrayList<>();
        addedRepToapprovedDoctorsFilterList = new ArrayList<>();


        pendingDoctorsMRFilterList = new ArrayList<>();
        pendingDoctorsnMRNameFilterList = new ArrayList<>();

        approvedDoctorsMRFilterList = new ArrayList<>();
        approvedDoctorsnMRNameFilterList = new ArrayList<>();


        approvedDoctorsList = new ArrayList<>();
        approvedDoctorsPhoneNumbersFilterList = new ArrayList<>();
        approvedDoctorsRegNumbersFilterList=new ArrayList<>();

        bottomNavigationBar.setSelectedItemId(R.id.navigation_doctors);
        bottomNavigationBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);




        bloackUserInteraction();
        includeProgres.setVisibility(View.VISIBLE);
        doctorsPresenter = new DoctorsPresenterImpli(this);
        doctorsPresenter.getPendingDoctor(this);




        textViewDoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                String selectedDoc = parent.getItemAtPosition(pos).toString();
                doctorsPresenter.searchDoctor(doctorsList, selectedDoc);
            }
        });


        textViewLoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                String selectedLoc = parent.getItemAtPosition(pos).toString();
                doctorsPresenter.searchLocation(locationEntitieList, selectedLoc);

            }
        });


        textViewSpec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                String selectedSpec = parent.getItemAtPosition(pos).toString();
                doctorsPresenter.searchSpecialization(specializationList,selectedSpec);

            }
        });


    }

    @OnItemClick(R.id.listview_navigation) void onItemClick(int position) {
        Toast.makeText(this, "You clicked: " + position, Toast.LENGTH_LONG).show();
    }


    @OnClick(R.id.imageView_filter)
    public void onClickTackFilter(View view) {
        showFilterDialog();

    }


    @OnClick(R.id.btn_assign)
    public void onClickAssignDoctors(View view) {
        bloackUserInteraction();
        includeProgres.setVisibility(View.VISIBLE);
        doctorsPresenter.postLocationToDoctors(this,selectDoctorID,addedlocationsArrayList,addedSpecArrayList);

    }


    @OnClick(R.id.imageView2)
    public void onClickSliderMenue(View view) {
        dLayout.openDrawer(Gravity.LEFT);
    }

    @OnClick(R.id.btn_tryagin)
    public void onTryAgian(View view) {
        includeNointernt.setVisibility(View.GONE);
    }


    @OnClick(R.id.imageView_doc_search)
    public void onClickDocSearch(View view) {
        if (textViewDoc.getVisibility() == View.VISIBLE) {
            doctorsPresenter.searchDoctor(doctorsList, textViewDoc.getText().toString());
        } else {
            textViewDoc.setVisibility(View.VISIBLE);
        }
        if (recyclerviewDocforAssign.getVisibility() == View.VISIBLE) {
        } else {
            recyclerviewDocforAssign.setVisibility(View.VISIBLE);
        }
    }


    @OnClick(R.id.imageView_loc_search)
    public void onClickLocSearch(View view) {
        if (textViewLoc.getVisibility() == View.VISIBLE) {
            doctorsPresenter.searchLocation(locationEntitieList, textViewLoc.getText().toString());
        } else {
            textViewLoc.setVisibility(View.VISIBLE);
        }
        if (recyclerviewLocations.getVisibility() == View.VISIBLE) {
        } else {
            recyclerviewLocations.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.imageView_spec_search)
    public void onClickSpecSearch(View view) {
        if (textViewSpec.getVisibility() == View.VISIBLE) {
            doctorsPresenter.searchSpecialization(specializationList,textViewSpec.getText().toString());
        } else {
            textViewSpec.setVisibility(View.VISIBLE);
        }
        if (recyclerviewSpec.getVisibility() == View.VISIBLE) {
        } else {
            recyclerviewSpec.setVisibility(View.VISIBLE);
        }
    }



    @OnClick(R.id.relativelayout_pending)
    public void onClickPending(View view) {
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            relativelayoutPending.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_left_primary));
            relativelayoutApproved.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_right_white));
        } else {
            relativelayoutPending.setBackground(getResources().getDrawable(R.drawable.bg_left_primary));
            relativelayoutApproved.setBackground(getResources().getDrawable(R.drawable.bg_right_white));
        }
        relativelayoutAssign.setBackgroundColor(getResources().getColor(R.color.colorash));
        recyclerViewDoctors.setVisibility(View.VISIBLE);
        relativeLayoutAssigndetails.setVisibility(View.GONE);

        isPending=true;

        bloackUserInteraction();
        includeProgres.setVisibility(View.VISIBLE);
        includeNointernt.setVisibility(View.GONE);

        doctorsPresenter.getPendingDoctor(this);
    }

    @OnClick(R.id.relativelayout_assign)
    public void onClickAssign(View view) {
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            relativelayoutPending.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_left_white));
            relativelayoutApproved.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_right_white));
        } else {
            relativelayoutPending.setBackground(getResources().getDrawable(R.drawable.bg_left_white));
            relativelayoutApproved.setBackground(getResources().getDrawable(R.drawable.bg_right_white));
        }
        relativelayoutAssign.setBackgroundColor(getResources().getColor(R.color.colorgold));

        recyclerViewDoctors.setVisibility(View.GONE);
        relativeLayoutAssigndetails.setVisibility(View.VISIBLE);

         addedlocationsArrayList.clear();
         addedSpecArrayList.clear();

        bloackUserInteraction();
        includeProgres.setVisibility(View.VISIBLE);
        includeNointernt.setVisibility(View.GONE);
        doctorsPresenter.getDoctors(this);
    }

    @OnClick(R.id.relativelayout_approved)
    public void onClickApproved(View view) {
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            relativelayoutApproved.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_right_primary));
            relativelayoutPending.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_left_white));

        } else {
            relativelayoutApproved.setBackground(getResources().getDrawable(R.drawable.bg_right_primary));
            relativelayoutPending.setBackground(getResources().getDrawable(R.drawable.bg_left_white));

        }
        relativelayoutAssign.setBackgroundColor(getResources().getColor(R.color.colorash));

        recyclerViewDoctors.setVisibility(View.VISIBLE);
        relativeLayoutAssigndetails.setVisibility(View.GONE);

        isPending=false;

        bloackUserInteraction();
        includeProgres.setVisibility(View.VISIBLE);
        includeNointernt.setVisibility(View.GONE);

        doctorsPresenter.getApprovedDoctor(this);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_visits:
                    Intent intentVisits = new Intent(DoctorsActivity.this, VisitsActivity.class);
                    Bundle bndlanimationVisits = ActivityOptions.makeCustomAnimation(DoctorsActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intentVisits, bndlanimationVisits);
                    finish();
                    return true;
                case R.id.navigation_location:
                    Intent intentLocation = new Intent(DoctorsActivity.this, LocationActivity.class);
                    Bundle bndlanimationLocation = ActivityOptions.makeCustomAnimation(DoctorsActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intentLocation, bndlanimationLocation);
                    finish();
                    return true;
                case R.id.navigation_doctors:
                    return true;
                case R.id.navigation_products:
                    Intent intentPro = new Intent(DoctorsActivity.this, AssignProductsActivity.class);
                    Bundle bndlanimationPro = ActivityOptions.makeCustomAnimation(DoctorsActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intentPro, bndlanimationPro);
                    finish();

                    return true;
            }
            return false;
        }
    };



    @Override
    public void navigationItems(ArrayList<Navigation> navigationArrayList) {
        navigationAdapter = new NavigationAdapter(this,navigationArrayList);
        listViewNavigation.setAdapter(navigationAdapter);
    }

    @Override
    public void pendingDoctorsList(ArrayList<Doctor> doctorArrayList) {

        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        includeNointernt.setVisibility(View.GONE);
        recyclerViewDoctors.setVisibility(View.VISIBLE);

        pendingDoctorsList =doctorArrayList;


        doctorsPendingAdapter = new DoctorsPendingAdapter(this,doctorArrayList,this);
        recyclerViewDoctors.setAdapter(doctorsPendingAdapter);
    }

    @Override
    public void pendingDoctorsEmpty() {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        includeNointernt.setVisibility(View.GONE);
        recyclerViewDoctors.setVisibility(View.GONE);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No Pending Doctors available");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

        alertDialogBuilder.show();

    }

    @Override
    public void pendingDoctorsGetingFail(String failMsg) {

        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        includeNointernt.setVisibility(View.GONE);
        recyclerViewDoctors.setVisibility(View.GONE);


        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(failMsg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            bloackUserInteraction();
                            includeProgres.setVisibility(View.VISIBLE);
                            includeNointernt.setVisibility(View.GONE);
                          doctorsPresenter.getPendingDoctor(DoctorsActivity.this);
                        }
                    });
            alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });
            alertDialogBuilder.show();
        } catch (WindowManager.BadTokenException e) {
            Toast.makeText(this, failMsg, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void pendingDoctorsGetingNetworkFail() {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        includeNointernt.setVisibility(View.VISIBLE);
        recyclerViewDoctors.setVisibility(View.GONE);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No Internet Access, Please try again");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

        alertDialogBuilder.show();


    }

    @Override
    public void pendingDoctorsNameFilterList(ArrayList<String> pendingDoctorsFilter) {
        pendingDoctorsNameFilterList=pendingDoctorsFilter;
    }

    @Override
    public void pendingDoctorsPhoneNumberFilterList(ArrayList<String> pendingDoctorsPhoneNumberFilter) {
        pendingDoctorsPhoneNumbersFilterList =pendingDoctorsPhoneNumberFilter;
    }

    @Override
    public void pendingDoctorsRegNumberFilterList(ArrayList<String> pendingDoctorsRegNumberFilter) {
        pendingDoctorsRegNumbersFilterList = pendingDoctorsRegNumberFilter;
    }

    @Override
    public void pendingDoctorsRepsFilterList(ArrayList<Rep> pendingDoctorsRepFilter) {
        pendingDoctorsMRFilterList = pendingDoctorsRepFilter;
    }

    @Override
    public void pendingDoctorsRepNamesFilterList(ArrayList<String> pendingDoctorsRepNamesFilter) {
        pendingDoctorsnMRNameFilterList = pendingDoctorsRepNamesFilter;
    }

    @Override
    public void approvedDoctorsList(ArrayList<Doctor> doctorArrayList) {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        includeNointernt.setVisibility(View.GONE);
        recyclerViewDoctors.setVisibility(View.VISIBLE);

        approvedDoctorsList =doctorArrayList;

        doctorsApprovedAdapter = new DoctorsApprovedAdapter(this,doctorArrayList,this);
        recyclerViewDoctors.setAdapter(doctorsApprovedAdapter);
    }

    @Override
    public void approvedDoctorsEmpty() {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        includeNointernt.setVisibility(View.GONE);
        recyclerViewDoctors.setVisibility(View.GONE);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No Approved Doctors available");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

        alertDialogBuilder.show();

    }

    @Override
    public void approvedDoctorsGetingFail(String failMsg) {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        includeNointernt.setVisibility(View.GONE);
        recyclerViewDoctors.setVisibility(View.GONE);
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(failMsg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            bloackUserInteraction();
                            includeProgres.setVisibility(View.VISIBLE);
                            includeNointernt.setVisibility(View.GONE);
                            doctorsPresenter.getApprovedDoctor(DoctorsActivity.this);
                        }
                    });
            alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });
            alertDialogBuilder.show();
        } catch (WindowManager.BadTokenException e) {
            Toast.makeText(this, failMsg, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void approvedDoctorsGetingNetworkFail() {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        includeNointernt.setVisibility(View.VISIBLE);
        recyclerViewDoctors.setVisibility(View.GONE);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No Internet Access, Please try again");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

        alertDialogBuilder.show();
    }

    @Override
    public void approvedDoctorsNameFilterList(ArrayList<String> approvedDoctorsFilter) {
        approvedDoctorsNameFilterList=approvedDoctorsFilter;
    }

    @Override
    public void approvedDoctorsPhoneNumberFilterList(ArrayList<String> approvedDoctorsPhoneNumberFilter) {
        approvedDoctorsPhoneNumbersFilterList =approvedDoctorsPhoneNumberFilter;
    }

    @Override
    public void approvedDoctorsRegNumberFilterList(ArrayList<String> approvedDoctorsRegNumberFilter) {
        approvedDoctorsRegNumbersFilterList = approvedDoctorsRegNumberFilter;
    }

    @Override
    public void approvedDoctorsRepsFilterList(ArrayList<Rep> approvedDoctorsRepFilter) {
        approvedDoctorsMRFilterList = approvedDoctorsRepFilter;
    }

    @Override
    public void approvedDoctorsRepNamesFilterList(ArrayList<String> approvedDoctorsRepNamesFilter) {
        approvedDoctorsnMRNameFilterList = approvedDoctorsRepNamesFilter;
    }

    @Override
    public void approveRejectStatusStart() {
        bloackUserInteraction();
        includeProgres.setVisibility(View.VISIBLE);
        includeNointernt.setVisibility(View.GONE);

    }

    @Override
    public void approveRejectDetailsError(String msg) {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        doctorsPresenter.getPendingDoctor(this);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
        alertDialogBuilder.show();


    }

    @Override
    public void approveRejectStatusSuccess() {
        bloackUserInteraction();
        includeProgres.setVisibility(View.VISIBLE);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Doctors status update Successfully");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
        alertDialogBuilder.show();

        try {
            dialogPendingDoctor.dismiss();
        }catch (Exception ex){

        }

        doctorsPresenter.getPendingDoctor(this);

    }

    @Override
    public void approveRejectStatusFail(String failMsg, final Doctor doc, final boolean status) {

        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        includeNointernt.setVisibility(View.GONE);
        recyclerViewDoctors.setVisibility(View.GONE);

        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(failMsg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            doctorsPresenter.postApproveRejectStatus(DoctorsActivity.this,doc,status);
                        }
                    });
            alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });
            alertDialogBuilder.show();
        } catch (WindowManager.BadTokenException e) {
            Toast.makeText(this, failMsg, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void approveRejectStatusNetworkFail() {

        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        includeNointernt.setVisibility(View.GONE);
        recyclerViewDoctors.setVisibility(View.GONE);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No Internet Access, Please try again");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

        alertDialogBuilder.show();
    }

    @Override
    public void doctorApproveFullDetais(Doctor doctor) {
        final Dialog dialogApproveDoctor = new Dialog(this);
        dialogApproveDoctor.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogApproveDoctor.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogApproveDoctor.setContentView(R.layout.dialog_doctor);
        dialogApproveDoctor.setCancelable(true);

        TextView textName = dialogApproveDoctor.findViewById(R.id.textview_name);
        TextView textviewSpec = dialogApproveDoctor.findViewById(R.id.textview_spec);
        TextView textviewRegnumber = dialogApproveDoctor.findViewById(R.id.textview_regnumber);
        TextView textviewNumber = dialogApproveDoctor.findViewById(R.id.textview_contactnumber);
        TextView textviewDate = dialogApproveDoctor.findViewById(R.id.textview_date);
        TextView textviewRep = dialogApproveDoctor.findViewById(R.id.textview_rep);
        TextView textviewQualification = dialogApproveDoctor.findViewById(R.id.textview_qualification);

        TextView textviewLocation = dialogApproveDoctor.findViewById(R.id.textview_location);




        textName.setText(doctor.getName());
        textviewSpec.setText(doctor.getSpecialization());
        textviewRegnumber.setText(doctor.getRegistrationNo());
        textviewNumber.setText(doctor.getContactNo());
        textviewDate.setText(doctor.getCreatedDate().substring(0,10));
        textviewRep.setText(doctor.getCreatedByName());
        textviewQualification.setText(doctor.getQualification());
        textviewLocation.setText(doctor.getLocation());


        dialogApproveDoctor.show();
    }

    @Override
    public void doctorPendingFullDetais(final Doctor doctor) {

        dialogPendingDoctor = new Dialog(this);
        dialogPendingDoctor.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogPendingDoctor.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogPendingDoctor.setContentView(R.layout.dialog_doctor_pending);
        dialogPendingDoctor.setCancelable(true);


        final EditText editTextName =dialogPendingDoctor.findViewById(R.id.editText_name);
        final EditText editTextRegNumber =dialogPendingDoctor.findViewById(R.id.editText_reg_number);
        final EditText editTextContactNumber =dialogPendingDoctor.findViewById(R.id.editText_contact_number);
        final EditText editTextQualification =dialogPendingDoctor.findViewById(R.id.editText_qualification);

        TextView textBtnReject = dialogPendingDoctor.findViewById(R.id.text_btn_reject);
        final TextView textBtnApprove = dialogPendingDoctor.findViewById(R.id.text_btn_approve);


        editTextName.setText(doctor.getName());
        editTextRegNumber.setText(doctor.getRegistrationNo());
        editTextContactNumber.setText(doctor.getContactNo());
        editTextQualification.setText(doctor.getQualification());


        final Doctor newdoctor = new Doctor();


        textBtnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newdoctor.setId(doctor.getId());
                newdoctor.setName(editTextName.getText().toString().trim());
                newdoctor.setCode(doctor.getCode());
                newdoctor.setContactNo(editTextContactNumber.getText().toString().trim());
                newdoctor.setRegistrationNo(editTextRegNumber.getText().toString().trim());
                newdoctor.setQualification(editTextQualification.getText().toString().trim());

                doctorsPresenter.postApproveRejectStatus(DoctorsActivity.this,newdoctor,false);

            }
        });

        textBtnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newdoctor.setId(doctor.getId());
                newdoctor.setName(editTextName.getText().toString().trim());
                newdoctor.setCode(doctor.getCode());
                newdoctor.setContactNo(editTextContactNumber.getText().toString().trim());
                newdoctor.setRegistrationNo(editTextRegNumber.getText().toString().trim());
                newdoctor.setQualification(editTextQualification.getText().toString().trim());

                doctorsPresenter.postApproveRejectStatus(DoctorsActivity.this,newdoctor,true);

            }
        });

        dialogPendingDoctor.show();

    }
    @Override
    public void doctorsList(ArrayList<Doctor> doctorArrayList, ArrayList<String> doctorsNames) {

        docLocationDoctorsAdapter = new DocLocationDoctorsAdapter(this, doctorArrayList, this);
        recyclerviewDocforAssign.setAdapter(docLocationDoctorsAdapter);


        doctorsList = doctorArrayList;

        ArrayAdapter<String> doctorsAdapterList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, doctorsNames);
        textViewDoc.setAdapter(doctorsAdapterList);

        doctorsPresenter.getLocation(this);

    }

    @Override
    public void doctorsEmpty() {
        includeProgres.setVisibility(View.GONE);
        unBloackUserInteraction();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No Approved doctors");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
        alertDialogBuilder.show();


    }

    @Override
    public void doctorsGetingFail(String failMsg) {
        includeProgres.setVisibility(View.GONE);
        unBloackUserInteraction();
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(failMsg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            bloackUserInteraction();
                            includeProgres.setVisibility(View.VISIBLE);
                            doctorsPresenter.getDoctors(DoctorsActivity.this);

                        }
                    });
            alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });
            alertDialogBuilder.show();
        } catch (WindowManager.BadTokenException e) {
            Toast.makeText(this, failMsg, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    @Override
    public void doctorsGetingNetworkFail() {
        includeProgres.setVisibility(View.GONE);
        unBloackUserInteraction();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No Internet Access, Please try again");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

        alertDialogBuilder.show();
    }

    @Override
    public void searchDoctorList(ArrayList<Doctor> doctorArrayList) {
        docLocationDoctorsAdapter = new DocLocationDoctorsAdapter(this, doctorArrayList, this);
        recyclerviewDocforAssign.setAdapter(docLocationDoctorsAdapter);
    }
    @Override
    public void locationList(ArrayList<LocationEntitie> locationEntitieArrayList, ArrayList<String> locationNameList) {


        docLocationLocationAdapter = new DocLocationLocationAdapter(this, locationEntitieArrayList, this);
        recyclerviewLocations.setAdapter(docLocationLocationAdapter);


        locationEntitieList = locationEntitieArrayList;

        ArrayAdapter<String> locationAdapterList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, locationNameList);
        textViewLoc.setAdapter(locationAdapterList);

        doctorsPresenter.getSpecialization(this);



    }

    @Override
    public void locationListEmpty() {
        includeProgres.setVisibility(View.GONE);
        unBloackUserInteraction();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No Approved Location");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

        alertDialogBuilder.show();

    }

    @Override
    public void locationFail(String failMsg) {
        includeProgres.setVisibility(View.GONE);
        unBloackUserInteraction();
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(failMsg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            bloackUserInteraction();
                            includeProgres.setVisibility(View.VISIBLE);
                            doctorsPresenter.getLocation(DoctorsActivity.this);
                        }
                    });
            alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });
            alertDialogBuilder.show();
        } catch (WindowManager.BadTokenException e) {
            Toast.makeText(this, failMsg, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


    }

    @Override
    public void locationNetworkFail() {
        includeProgres.setVisibility(View.GONE);
        unBloackUserInteraction();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No Internet Access, Please try again");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

        alertDialogBuilder.show();
    }


    @Override
    public void searchLocationList(ArrayList<LocationEntitie> locationEntitieArrayList) {
        docLocationLocationAdapter = new DocLocationLocationAdapter(this, locationEntitieArrayList, this);
        recyclerviewLocations.setAdapter(docLocationLocationAdapter);
    }

    @Override
    public void selectedDoc(Doctor doc) {
        selectDoctorID = doc.getId();

        if(locationEntitieList== null){
            Toast.makeText(this, "No Approved Location", Toast.LENGTH_SHORT).show();
        }else {
            doctorsPresenter.getLocationWithDoctorLocation(locationEntitieList,doc.getLocationEntitieList());
            addedlocationsArrayList=doc.getLocationEntitieList();
            doctorsPresenter.getSpecWithDoctorSpec(specializationList,doc.getSpecializationList());
            addedSpecArrayList=doc.getSpecializationList();
        }





    }

    @Override
    public void locationToDocStart(LocationEntitie LocationEntitie, boolean addOrRemove) {
        doctorsPresenter.assignLocationToDoc(addedlocationsArrayList, LocationEntitie,addOrRemove);
    }

    @Override
    public void locationToDoc(ArrayList<LocationEntitie> locationEntitieArrayList) {
        addedlocationsArrayList= locationEntitieArrayList;
        doctorsPresenter.showAssignLocation(locationEntitieArrayList, locationEntitieList);
    }


    @Override
    public void specializationList(ArrayList<Specialization> specArrayList, ArrayList<String> specNameList) {
        includeProgres.setVisibility(View.GONE);
        unBloackUserInteraction();


        specializationAdapter = new SpecializationAdapter(this,specArrayList,this);
        recyclerviewSpec.setAdapter(specializationAdapter);

        specializationList = specArrayList;


        ArrayAdapter<String> specAdapterList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, specNameList);
        textViewSpec.setAdapter(specAdapterList);


    }

    @Override
    public void specializationEmpty() {
        includeProgres.setVisibility(View.GONE);
        unBloackUserInteraction();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No any Specialization approved or added yet");
        alertDialogBuilder.setPositiveButton("OKn",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

        alertDialogBuilder.show();

    }

    @Override
    public void specializationFail(String failMsg) {
        includeProgres.setVisibility(View.GONE);
        unBloackUserInteraction();

        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(failMsg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            bloackUserInteraction();
                            includeProgres.setVisibility(View.VISIBLE);
                            doctorsPresenter.getSpecialization(DoctorsActivity.this);
                        }
                    });
            alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });
            alertDialogBuilder.show();
        } catch (WindowManager.BadTokenException e) {
            Toast.makeText(this, failMsg, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    @Override
    public void specializationNetworkFail() {
        includeProgres.setVisibility(View.GONE);
        unBloackUserInteraction();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No Internet Access, Please try again");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

        alertDialogBuilder.show();
    }


    @Override
    public void searchSpecializationList(ArrayList<Specialization> specializationArrayList) {
        specializationAdapter = new SpecializationAdapter(this,specializationArrayList,this);
        recyclerviewSpec.setAdapter(specializationAdapter);
    }


    @Override
    public void specializationToDocStart(Specialization specialization, boolean addOrRemove) {
        doctorsPresenter.assignSpecializationToDoc(addedSpecArrayList,specialization,addOrRemove);




    }

    @Override
    public void specializationToDoc(ArrayList<Specialization> specializationArrayList) {
        addedSpecArrayList = specializationArrayList;
        doctorsPresenter.showAssignSpec(specializationArrayList,specializationList);


    }


    @Override
    public void postLocationToDoctorsSuccessful() {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Assign doctor's details successful");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

        alertDialogBuilder.show();




    }

    @Override
    public void postLocationToDoctorsEmpty(String msg) {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

        alertDialogBuilder.show();
    }

    @Override
    public void postLocationToDoctorsFail(String failMsg, final int docid, final ArrayList<LocationEntitie> productArrayList, final ArrayList<Specialization> specializationArrayList) {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(failMsg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            bloackUserInteraction();
                            includeProgres.setVisibility(View.VISIBLE);
                            doctorsPresenter.postLocationToDoctors(DoctorsActivity.this,docid,productArrayList,specializationArrayList);
                        }
                    });
            alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });
            alertDialogBuilder.show();
        } catch (WindowManager.BadTokenException e) {
            Toast.makeText(this, failMsg, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }



    }

    @Override
    public void postLocationToDoctorsNetworkFail() {

        includeProgres.setVisibility(View.GONE);
        unBloackUserInteraction();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No Internet Access, Please try again");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

        alertDialogBuilder.show();

    }

    @Override
    public void assignLocation(ArrayList<LocationEntitie> locationEntitieArrayList) {
        docLocationLocationAdapter = new DocLocationLocationAdapter(this, locationEntitieArrayList, this);
        recyclerviewLocations.setAdapter(docLocationLocationAdapter);
    }

    @Override
    public void assignSpec(ArrayList<Specialization> specializationArrayList) {
        specializationAdapter = new SpecializationAdapter(this,specializationArrayList,this);
        recyclerviewSpec.setAdapter(specializationAdapter);
    }



    @Override
    public void locationListWithDoctorLocation(ArrayList<LocationEntitie> allLocationEntitieArrayList, ArrayList<LocationEntitie> doctorLocationEntitieArrayList) {

        docLocationLocationAdapter = new DocLocationLocationAdapter(this, allLocationEntitieArrayList, this);
        recyclerviewLocations.setAdapter(docLocationLocationAdapter);

    }

    @Override
    public void specListWithDoctorSpec(ArrayList<Specialization> allSpecArrayList, ArrayList<Specialization> doctorSpecArrayList) {

        specializationAdapter = new SpecializationAdapter(this,allSpecArrayList,this);
        recyclerviewSpec.setAdapter(specializationAdapter);


    }

    @Override
    public void doctorsFilterListEmpty(String msg, ArrayList<Doctor> docArrayList) {

    }

    @Override
    public void doctorsFilterList(ArrayList<Doctor> docArrayList) {
        if(isPending){
            doctorsPendingAdapter = new DoctorsPendingAdapter(this,docArrayList,this);
            recyclerViewDoctors.setAdapter(doctorsPendingAdapter);
        }else {

        }

        dialogFilter.dismiss();
    }

    @Override
    public void repToDoctorFilterStart(Rep rep, boolean addOrRemove) {
        if (isPending) {
            doctorsPresenter.addRepToDoctorFilter(addedRepToPendingDoctorsFilterList, rep, addOrRemove);
        }else {
            doctorsPresenter.addRepToDoctorFilter(addedRepToapprovedDoctorsFilterList, rep, addOrRemove);
        }

    }

    @Override
    public void repToDoctorFilter(ArrayList<Rep> repArrayListToFilter) {
        if (isPending) {
            addedRepToPendingDoctorsFilterList =repArrayListToFilter;
            doctorsPresenter.showAddRepFilter(repArrayListToFilter,pendingDoctorsMRFilterList);
        }else {
            addedRepToapprovedDoctorsFilterList=repArrayListToFilter;
            doctorsPresenter.showAddRepFilter(repArrayListToFilter,approvedDoctorsMRFilterList);
        }

    }

    @Override
    public void addRepToFilter(ArrayList<Rep> addedArrayListToFilter) {
        DoctorMRFilterAdapter doctorMRFilterAdapter = new DoctorMRFilterAdapter(this,addedArrayListToFilter,this);
        recyclerViewDocFilterRep.setAdapter(doctorMRFilterAdapter);
    }

    @Override
    public void showSelectedFilterDoctor(ArrayList<Rep> filterList) {
        DoctorMRFilterAdapter doctorMRFilterAdapter = new DoctorMRFilterAdapter(this,filterList,this);
        recyclerViewDocFilterRep.setAdapter(doctorMRFilterAdapter);
    }

    @Override
    public void repListForFilter(ArrayList<Rep> repArrayList) {
        DoctorMRFilterAdapter doctorMRFilterAdapter = new DoctorMRFilterAdapter(this,repArrayList,this);
        recyclerViewDocFilterRep.setAdapter(doctorMRFilterAdapter);
    }

    private void bloackUserInteraction() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void unBloackUserInteraction() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit!");
        alertDialogBuilder.setMessage("Do you really want to exit ?");
        alertDialogBuilder.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                });

        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });
        alertDialogBuilder.show();


    }


    public void setDoctorsRecycalView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewDoctors.setLayoutManager(layoutManager);
        recyclerViewDoctors.setItemAnimator(new DefaultItemAnimator());
        recyclerViewDoctors.setNestedScrollingEnabled(false);
    }

    public void setDoctorsForAssigRecycalView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerviewDocforAssign.setLayoutManager(layoutManager);
        recyclerviewDocforAssign.setItemAnimator(new DefaultItemAnimator());
        recyclerviewDocforAssign.setNestedScrollingEnabled(false);

    }

    public void setLocationsRecycalView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerviewLocations.setLayoutManager(layoutManager);
        recyclerviewLocations.setItemAnimator(new DefaultItemAnimator());
        recyclerviewLocations.setNestedScrollingEnabled(false);

    }

    public void setSpecRecycalView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerviewSpec.setLayoutManager(layoutManager);
        recyclerviewSpec.setItemAnimator(new DefaultItemAnimator());
        recyclerviewSpec.setNestedScrollingEnabled(false);

    }


    private void setFilterDocRepRecycalView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewDocFilterRep.setLayoutManager(layoutManager);
        recyclerViewDocFilterRep.setItemAnimator(new DefaultItemAnimator());
        recyclerViewDocFilterRep.setNestedScrollingEnabled(false);

    }

    private void showFilterDialog() {

        dialogFilter = new Dialog(this);
        dialogFilter.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogFilter.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogFilter.setContentView(R.layout.dialog_doctors_filter);
        dialogFilter.setCancelable(true);


        final AutoCompleteTextView autoTextViewDoctors = dialogFilter.findViewById(R.id.autoCompleteTextView_doc);
        final ImageView imageViewDoctorsSerach = dialogFilter.findViewById(R.id.imageView_doc_search);

        final AutoCompleteTextView autoTextViewContactNumber = dialogFilter.findViewById(R.id.autoCompleteTextView_contactnumber);
        final ImageView imageViewContactNumberSerach = dialogFilter.findViewById(R.id.imageView_contactnumber_search);


        final AutoCompleteTextView autoTextViewRegNumber = dialogFilter.findViewById(R.id.autoCompleteTextView_regnumber);
        final ImageView imageViewRegNumberSerach = dialogFilter.findViewById(R.id.imageView_regnumber_search);



        final AutoCompleteTextView autoTextViewRepNames = dialogFilter.findViewById(R.id.autoCompleteTextView_rep);
        final ImageView imageViewRepSerach = dialogFilter.findViewById(R.id.imageView_rep_search);




        recyclerViewDocFilterRep = dialogFilter.findViewById(R.id.recyclerView_rep);
        setFilterDocRepRecycalView();


        ArrayAdapter<String> doctorsAdapterList;
        ArrayAdapter<String> doctorsContactNumberAdapterList;
        ArrayAdapter<String> doctorsRegNumberAdapterList;


        ArrayAdapter<String> doctorsRepNameAdapterList;


        if (isPending) {
            doctorsAdapterList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, pendingDoctorsNameFilterList);
            autoTextViewDoctors.setAdapter(doctorsAdapterList);

            doctorsContactNumberAdapterList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, pendingDoctorsPhoneNumbersFilterList);
            autoTextViewContactNumber.setAdapter(doctorsContactNumberAdapterList);

            doctorsRegNumberAdapterList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, pendingDoctorsRegNumbersFilterList);
            autoTextViewRegNumber.setAdapter(doctorsRegNumberAdapterList);

            doctorsRepNameAdapterList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, pendingDoctorsnMRNameFilterList);
            autoTextViewRepNames.setAdapter(doctorsRepNameAdapterList);


            doctorsPresenter.setSelectedFilterRep(pendingDoctorsMRFilterList,addedRepToPendingDoctorsFilterList);

        } else {


            doctorsAdapterList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, approvedDoctorsNameFilterList);
            autoTextViewDoctors.setAdapter(doctorsAdapterList);

            doctorsContactNumberAdapterList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, approvedDoctorsPhoneNumbersFilterList);
            autoTextViewContactNumber.setAdapter(doctorsContactNumberAdapterList);

            doctorsRegNumberAdapterList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, approvedDoctorsRegNumbersFilterList);
            autoTextViewRegNumber.setAdapter(doctorsRegNumberAdapterList);

            doctorsRepNameAdapterList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, approvedDoctorsnMRNameFilterList);
            autoTextViewRepNames.setAdapter(doctorsRepNameAdapterList);


            doctorsPresenter.setSelectedFilterRep(approvedDoctorsMRFilterList,addedRepToapprovedDoctorsFilterList);




        }



        autoTextViewDoctors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                String selectedLoc = parent.getItemAtPosition(pos).toString();
                if (isPending) {
                    doctorsPresenter.doctorsFilter(pendingDoctorsList,selectedLoc,"","",new ArrayList<Rep>());
                } else {
                    doctorsPresenter.doctorsFilter(approvedDoctorsList,selectedLoc,"","",new ArrayList<Rep>());
                }

            }
        });
        imageViewDoctorsSerach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPending) {
                    doctorsPresenter.doctorsFilter(pendingDoctorsList,autoTextViewDoctors.getText().toString(),"","",new ArrayList<Rep>());
                } else {
                    doctorsPresenter.doctorsFilter(approvedDoctorsList,autoTextViewDoctors.getText().toString(),"","",new ArrayList<Rep>());
                }
            }
        });


        autoTextViewContactNumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                String selectedLoc = parent.getItemAtPosition(pos).toString();
                if (isPending) {
                    doctorsPresenter.doctorsFilter(pendingDoctorsList,"",selectedLoc,"",new ArrayList<Rep>());
                } else {
                    doctorsPresenter.doctorsFilter(approvedDoctorsList,"",selectedLoc,"",new ArrayList<Rep>());
                }

            }
        });
        imageViewContactNumberSerach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPending) {
                    doctorsPresenter.doctorsFilter(pendingDoctorsList,"",autoTextViewContactNumber.getText().toString(),"",new ArrayList<Rep>());
                } else {
                    doctorsPresenter.doctorsFilter(approvedDoctorsList,"",autoTextViewContactNumber.getText().toString(),"",new ArrayList<Rep>());
                }
            }
        });


        autoTextViewRegNumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                String selectedLoc = parent.getItemAtPosition(pos).toString();
                if (isPending) {
                    doctorsPresenter.doctorsFilter(pendingDoctorsList,"","",selectedLoc,new ArrayList<Rep>());
                } else {
                    doctorsPresenter.doctorsFilter(approvedDoctorsList,"","",selectedLoc,new ArrayList<Rep>());
                }

            }
        });
        imageViewRegNumberSerach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPending) {
                    doctorsPresenter.doctorsFilter(pendingDoctorsList,"","",autoTextViewRegNumber.getText().toString(),new ArrayList<Rep>());
                } else {
                    doctorsPresenter.doctorsFilter(approvedDoctorsList,"","",autoTextViewRegNumber.getText().toString(),new ArrayList<Rep>());
                }
            }
        });




        autoTextViewRepNames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                String selectedMR = parent.getItemAtPosition(pos).toString();
                if (isPending) {
                    doctorsPresenter.searchRepForFilter(pendingDoctorsMRFilterList,selectedMR);
                } else {
                    doctorsPresenter.searchRepForFilter(approvedDoctorsMRFilterList,selectedMR);
                    //doctorsPresenter.searchRepForFilter(,selectedMR);
                }

            }
        });
        imageViewRepSerach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPending) {
                    doctorsPresenter.searchRepForFilter(pendingDoctorsMRFilterList,autoTextViewRepNames.getText().toString());
                } else {
                    doctorsPresenter.searchRepForFilter(approvedDoctorsMRFilterList,autoTextViewRepNames.getText().toString());
                }
            }
        });



        RelativeLayout layoutRepsBar = dialogFilter.findViewById(R.id.relativelayout_rep_bar_image);
        final ImageView imageViewLayoutRepsImage = dialogFilter.findViewById(R.id.imageView_layout_rep_image);
        final RelativeLayout layoutRecyclerViewRrep = dialogFilter.findViewById(R.id.layout_recyclerView_rep);


        layoutRepsBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutRecyclerViewRrep.getVisibility() == View.VISIBLE) {
                    layoutRecyclerViewRrep.setVisibility(View.GONE);
                    imageViewLayoutRepsImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_control_point_black_24dp));
                } else {
                    layoutRecyclerViewRrep.setVisibility(View.VISIBLE);
                    imageViewLayoutRepsImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove_circle_outline_black_24dp));
                }

            }
        });




        Button btnFilter = dialogFilter.findViewById(R.id.btn_filter);
        Button btnReset = dialogFilter.findViewById(R.id.btn_reset);


        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doctorsPresenter.doctorsFilter(pendingDoctorsList,"","","",addedRepToPendingDoctorsFilterList);
            }
        });




        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doctorsPresenter.doctorsFilter(pendingDoctorsList,"","","",new ArrayList<Rep>());
            }
        });



        dialogFilter.show();


    }


}
