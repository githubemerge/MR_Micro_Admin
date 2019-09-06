package emerge.project.mrsolution_admin_micro.ui.activity.locations;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import emerge.project.mrsolution_admin_micro.R;
import emerge.project.mrsolution_admin_micro.ui.activity.doctors.DoctorsActivity;
import emerge.project.mrsolution_admin_micro.ui.activity.products.assign_products.AssignProductsActivity;
import emerge.project.mrsolution_admin_micro.ui.activity.visits.VisitsActivity;
import emerge.project.mrsolution_admin_micro.ui.adapters.location.LocationApprovedAdapter;
import emerge.project.mrsolution_admin_micro.ui.adapters.location.LocationDistrictAdapter;
import emerge.project.mrsolution_admin_micro.ui.adapters.location.LocationPendingAdapter;
import emerge.project.mrsolution_admin_micro.ui.adapters.location.LocationPendingMRFilterAdapter;
import emerge.project.mrsolution_admin_micro.ui.adapters.navigation.NavigationAdapter;
import emerge.project.mrsolution_admin_micro.utils.entittes.District;
import emerge.project.mrsolution_admin_micro.utils.entittes.LocationEntitie;
import emerge.project.mrsolution_admin_micro.utils.entittes.Navigation;
import emerge.project.mrsolution_admin_micro.utils.entittes.Rep;

public class LocationActivity extends Activity implements LocationView, OnMapReadyCallback {


    @BindView(R.id.drawer_layout)
    DrawerLayout dLayout;
    @BindView(R.id.recyclerView_locations)
    RecyclerView recyclerViewLocations;

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationView bottomNavigationBar;

    @BindView(R.id.relativelayout_pending)
    RelativeLayout relativelayoutPending;

    @BindView(R.id.relativelayout_approved)
    RelativeLayout relativelayoutApproved;


    @BindView(R.id.listview_navigation)
    ListView listViewNavigation;


    @BindView(R.id.include_progres)
    View includeProgres;

    @BindView(R.id.include_nointernt)
    View includeNointernt;

    Dialog dialogPending;
    Dialog dialogFilter;


    RecyclerView recyclerViewLocationsFilterRep;

    Spinner spinnerDistrict;

    LocationPendingAdapter locationPendingAdapter;
    LocationApprovedAdapter locationApprovedAdapter;
    LocationDistrictAdapter locationDistrictAdapter;
    NavigationAdapter navigationAdapter;


    ArrayList<LocationEntitie> pendingLocationList;

    ArrayList<District> districtList;

    ArrayList<String> pendingLocationNameFilterList;

    ArrayList<Rep> pendingLocationMRFilterList;
    ArrayList<String> pendingLocationMRNameFilterList;
    ArrayList<Rep> addedRepToPendingLocationFilterList;


    ArrayList<LocationEntitie> approvedLocationList;

    ArrayList<String> approvedLocationNameFilterList;

    ArrayList<Rep> approvedLocationMRFilterList;
    ArrayList<String> approvedLocationMRNameFilterList;
    ArrayList<Rep> addedRepToapprovedLocationFilterList;





    LocationPresenter locationPresenter;

    boolean isPendingVisibale = false;
    Double latitude, longitude;
    int sdk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);

        sdk = android.os.Build.VERSION.SDK_INT;


        pendingLocationNameFilterList = new ArrayList<String>();
        approvedLocationNameFilterList =  new ArrayList<String>();

        pendingLocationMRFilterList = new ArrayList<Rep>();
        pendingLocationMRNameFilterList = new ArrayList<String>();

        approvedLocationMRFilterList = new ArrayList<Rep>();
        approvedLocationMRNameFilterList = new ArrayList<String>();


        addedRepToPendingLocationFilterList = new ArrayList<Rep>();
        addedRepToapprovedLocationFilterList= new ArrayList<Rep>();



        setLocationsRecycalView();

        bottomNavigationBar.setSelectedItemId(R.id.navigation_location);
        bottomNavigationBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        locationPresenter = new LocationPresenterImpli(this);

        bloackUserInteraction();
        includeProgres.setVisibility(View.VISIBLE);
        locationPresenter.getPendingLocation(this);

        isPendingVisibale = true;


    }


    @OnItemClick(R.id.listview_navigation)
    void onItemClick(int position) {
        Toast.makeText(this, "You clicked: " + position, Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.imageView2)
    public void onClickSliderMenue(View view) {
        dLayout.openDrawer(Gravity.LEFT);
    }

    @OnClick(R.id.btn_tryagin)
    public void onTryAgian(View view) {
        includeNointernt.setVisibility(View.GONE);
        if (isPendingVisibale) {
            bloackUserInteraction();
            includeProgres.setVisibility(View.VISIBLE);
            locationPresenter.getPendingLocation(this);
        } else {
            bloackUserInteraction();
            includeProgres.setVisibility(View.VISIBLE);
            locationPresenter.getApprovedLocation(this);
        }

    }


    @OnClick(R.id.relativelayout_pending)
    public void onClickPending(View view) {
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            relativelayoutPending.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_left_primary));
            relativelayoutApproved.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_left_primary));
        } else {
            relativelayoutPending.setBackground(getResources().getDrawable(R.drawable.bg_left_primary));
            relativelayoutApproved.setBackground(getResources().getDrawable(R.drawable.bg_right_white));
        }
        bloackUserInteraction();
        includeProgres.setVisibility(View.VISIBLE);
        includeNointernt.setVisibility(View.GONE);
        locationPresenter.getPendingLocation(this);
        isPendingVisibale = true;

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
        bloackUserInteraction();
        includeProgres.setVisibility(View.VISIBLE);
        includeNointernt.setVisibility(View.GONE);
        locationPresenter.getApprovedLocation(this);
        isPendingVisibale = false;

    }


    @OnClick(R.id.imageView_filter)
    public void onClickTackFilter(View view) {
        showFilterDialog();

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_visits:
                    Intent intentVists = new Intent(LocationActivity.this, VisitsActivity.class);
                    Bundle bndlanimationVisits = ActivityOptions.makeCustomAnimation(LocationActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intentVists, bndlanimationVisits);
                    finish();
                    return true;
                case R.id.navigation_location:


                    return true;
                case R.id.navigation_doctors:

                    Intent intentDoctors = new Intent(LocationActivity.this, DoctorsActivity.class);
                    Bundle bndlanimationDoctors = ActivityOptions.makeCustomAnimation(LocationActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intentDoctors, bndlanimationDoctors);
                    finish();
                    return true;
                case R.id.navigation_products:
                    Intent intentPro = new Intent(LocationActivity.this, AssignProductsActivity.class);
                    Bundle bndlanimationPro = ActivityOptions.makeCustomAnimation(LocationActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intentPro, bndlanimationPro);
                    finish();


                    return true;
            }
            return false;
        }
    };




    @Override
    public void navigationItems(ArrayList<Navigation> navigationArrayList) {
        navigationAdapter = new NavigationAdapter(this, navigationArrayList);
        listViewNavigation.setAdapter(navigationAdapter);
    }

    @Override
    public void pendingLocationList(ArrayList<LocationEntitie> locationEntitieArrayList) {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        includeNointernt.setVisibility(View.GONE);
        recyclerViewLocations.setVisibility(View.VISIBLE);

        pendingLocationList = locationEntitieArrayList;

        locationPendingAdapter = new LocationPendingAdapter(this, locationEntitieArrayList, this);
        recyclerViewLocations.setAdapter(locationPendingAdapter);

    }

    @Override
    public void noPendingLocation() {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        includeNointernt.setVisibility(View.GONE);
        recyclerViewLocations.setAdapter(null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No Pending Location available");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

        alertDialogBuilder.show();


    }

    @Override
    public void pendingLocationFail(String failMsg) {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        includeNointernt.setVisibility(View.GONE);
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(failMsg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            bloackUserInteraction();
                            includeProgres.setVisibility(View.VISIBLE);
                            locationPresenter.getPendingLocation(LocationActivity.this);
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
    public void pendingLocationNetworkFail() {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        includeNointernt.setVisibility(View.VISIBLE);
        recyclerViewLocations.setVisibility(View.GONE);
    }


    @Override
    public void pendingLocationNameList(ArrayList<String> pendingLocationNameForFilter) {
        pendingLocationNameFilterList = pendingLocationNameForFilter;
    }

    @Override
    public void pendingLocationMRList(ArrayList<Rep> pendingLocationMRForFilter) {
        pendingLocationMRFilterList = pendingLocationMRForFilter;
    }

    @Override
    public void pendingLocationMRNameList(ArrayList<String> pendingLocationMRNameForFilter) {
        pendingLocationMRNameFilterList = pendingLocationMRNameForFilter;
    }

    @Override
    public void approvedLocationList(ArrayList<LocationEntitie> locationEntitieArrayList) {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        includeNointernt.setVisibility(View.GONE);
        recyclerViewLocations.setVisibility(View.VISIBLE);

        approvedLocationList = locationEntitieArrayList;

        locationApprovedAdapter = new LocationApprovedAdapter(this, locationEntitieArrayList, this);
        recyclerViewLocations.setAdapter(locationApprovedAdapter);
    }

    @Override
    public void noApprovedLocation() {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        includeNointernt.setVisibility(View.GONE);
        recyclerViewLocations.setVisibility(View.GONE);
        recyclerViewLocations.setAdapter(null);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No Approved Location available");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

        alertDialogBuilder.show();

    }

    @Override
    public void approvedLocationFail(String failMsg) {

        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        includeNointernt.setVisibility(View.GONE);
        recyclerViewLocations.setVisibility(View.GONE);

        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(failMsg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            bloackUserInteraction();
                            includeProgres.setVisibility(View.VISIBLE);
                            locationPresenter.getApprovedLocation(LocationActivity.this);
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
    public void approvedLocationNetworkFail() {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        includeNointernt.setVisibility(View.VISIBLE);
        recyclerViewLocations.setVisibility(View.GONE);
    }

    @Override
    public void approvedLocationNameList(ArrayList<String> approvedLocationNameForFilter) {
        approvedLocationNameFilterList = approvedLocationNameForFilter;
    }

    @Override
    public void approvedLocationMRList(ArrayList<Rep> approvedLocationMRForFilter) {
        pendingLocationMRFilterList = approvedLocationMRForFilter;
    }

    @Override
    public void approvedLocationMRNameList(ArrayList<String> approvedLocationMRNameForFilter) {
        approvedLocationMRNameFilterList = approvedLocationMRNameForFilter;
    }


    @Override
    public void selectedLocationPending(final LocationEntitie LocationEntitie) {

        dialogPending = new Dialog(this);
        dialogPending.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogPending.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogPending.setContentView(R.layout.dialog_location_pending);
        dialogPending.setCancelable(true);

        MapView mapView = dialogPending.findViewById(R.id.mapView);
        final EditText editTextName = dialogPending.findViewById(R.id.editText_name);
        final EditText editTextAddress = dialogPending.findViewById(R.id.editText_address);
        final EditText editTextArea = dialogPending.findViewById(R.id.editText_area);
        final EditText editTextTown = dialogPending.findViewById(R.id.editText_town);

        TextView textBtnReject = dialogPending.findViewById(R.id.text_btn_reject);
        TextView textBtnApprove = dialogPending.findViewById(R.id.text_btn_approve);


        spinnerDistrict = dialogPending.findViewById(R.id.spinner_district);


        bloackUserInteraction();
        includeProgres.setVisibility(View.VISIBLE);
        locationPresenter.getDistrictList(LocationActivity.this, LocationEntitie.getDistrictID(), LocationEntitie.getDistrict());

        editTextName.setText(LocationEntitie.getName());
        editTextAddress.setText(LocationEntitie.getAddress());
        editTextArea.setText(LocationEntitie.getArea());
        editTextTown.setText(LocationEntitie.getTown());

        final LocationEntitie newLocationEntitie = new LocationEntitie();


        newLocationEntitie.setDistrict(LocationEntitie.getDistrict());
        newLocationEntitie.setDistrictID(LocationEntitie.getDistrictID());


        spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (districtList.isEmpty()) {
                } else {
                    newLocationEntitie.setDistrict(districtList.get(position).getName());
                    newLocationEntitie.setDistrictID(districtList.get(position).getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                newLocationEntitie.setDistrict(LocationEntitie.getDistrict());
                newLocationEntitie.setDistrictID(LocationEntitie.getDistrictID());

            }
        });


        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

        latitude = LocationEntitie.getLatitude();
        longitude = LocationEntitie.getLongitude();


        textBtnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newLocationEntitie.setId(LocationEntitie.getId());
                newLocationEntitie.setName(editTextName.getText().toString().trim());
                newLocationEntitie.setAddress(editTextAddress.getText().toString().trim());
                newLocationEntitie.setArea(editTextArea.getText().toString().trim());
                newLocationEntitie.setTown(editTextTown.getText().toString().trim());
                newLocationEntitie.setLatitude(LocationEntitie.getLatitude());
                newLocationEntitie.setLongitude(LocationEntitie.getLongitude());
                newLocationEntitie.setLocationTypeID(LocationEntitie.getLocationTypeID());
                newLocationEntitie.setLocationType(LocationEntitie.getLocationType());

                dialogPending.dismiss();
                locationPresenter.postApproveRejectStatus(LocationActivity.this, newLocationEntitie, false);
            }
        });

        textBtnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newLocationEntitie.setId(LocationEntitie.getId());
                newLocationEntitie.setName(editTextName.getText().toString().trim());
                newLocationEntitie.setAddress(editTextAddress.getText().toString().trim());
                newLocationEntitie.setArea(editTextArea.getText().toString().trim());
                newLocationEntitie.setTown(editTextTown.getText().toString().trim());
                newLocationEntitie.setLatitude(LocationEntitie.getLatitude());
                newLocationEntitie.setLongitude(LocationEntitie.getLongitude());
                newLocationEntitie.setLocationTypeID(LocationEntitie.getLocationTypeID());
                newLocationEntitie.setLocationType(LocationEntitie.getLocationType());

                dialogPending.dismiss();

                locationPresenter.postApproveRejectStatus(LocationActivity.this, newLocationEntitie, true);
            }
        });

        dialogPending.show();

    }

    @Override
    public void selectedLocationApproved(LocationEntitie LocationEntitie) {
        final Dialog dialogApproved = new Dialog(this);
        dialogApproved.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogApproved.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogApproved.setContentView(R.layout.dialog_location_approved);
        dialogApproved.setCancelable(true);

        MapView mapView = dialogApproved.findViewById(R.id.mapView);
        TextView textviewName = dialogApproved.findViewById(R.id.textview_name);
        TextView textviewAddress = dialogApproved.findViewById(R.id.textview_address);
        TextView textviewArea = dialogApproved.findViewById(R.id.textview_area);
        TextView textviewTown = dialogApproved.findViewById(R.id.textview_town);
        TextView textviewDistrict = dialogApproved.findViewById(R.id.textview_district);


        textviewName.setText(LocationEntitie.getName());
        textviewAddress.setText(LocationEntitie.getAddress());
        textviewArea.setText(LocationEntitie.getArea());
        textviewTown.setText(LocationEntitie.getTown());
        textviewDistrict.setText(LocationEntitie.getDistrict());


        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);

        }

        latitude = LocationEntitie.getLatitude();
        longitude = LocationEntitie.getLongitude();


        dialogApproved.show();

    }

    @Override
    public void districtList(ArrayList<District> districtArrayList) {

        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);

        districtList = districtArrayList;

        locationDistrictAdapter = new LocationDistrictAdapter(this, districtArrayList);
        spinnerDistrict.setAdapter(locationDistrictAdapter);

    }

    @Override
    public void districtListEmpty() {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No District available");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

        alertDialogBuilder.show();
    }

    @Override
    public void districtListFail(String failMsg, final int districtid, final String district) {
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
                            locationPresenter.getDistrictList(LocationActivity.this, districtid, district);
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
    public void districtListNetworkFail() {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);

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
    public void approveRejectStatusStart() {
        bloackUserInteraction();
        includeProgres.setVisibility(View.VISIBLE);
    }

    @Override
    public void approveRejectDetailsError(String msg) {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        locationPresenter.getPendingLocation(this);

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
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        locationPresenter.getPendingLocation(LocationActivity.this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Location status update Successfully");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
        alertDialogBuilder.show();

    }

    @Override
    public void approveRejectStatusFail(String failMsg, final LocationEntitie LocationEntitie, final boolean status) {
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
                            locationPresenter.postApproveRejectStatus(LocationActivity.this, LocationEntitie, status);
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
    public void locPendingListForFilter(ArrayList<LocationEntitie> locationArrayList) {

      if(isPendingVisibale){
          locationPendingAdapter = new LocationPendingAdapter(this, locationArrayList, this);
          recyclerViewLocations.setAdapter(locationPendingAdapter);
      }else {
          locationApprovedAdapter = new LocationApprovedAdapter(this, locationArrayList, this);
          recyclerViewLocations.setAdapter(locationApprovedAdapter);
      }




        dialogFilter.dismiss();
    }

    @Override
    public void repToLocationFilterStart(Rep rep, boolean addOrRemove) {
        if (isPendingVisibale) {
            locationPresenter.addRepToLocationFilter(addedRepToPendingLocationFilterList, rep, addOrRemove);
        }else {
            locationPresenter.addRepToLocationFilter(addedRepToapprovedLocationFilterList, rep, addOrRemove);
        }


    }

    @Override
    public void repToLocationFilter(ArrayList<Rep> repArrayListToFilter) {
        // textviewSelectedSpecCount.setText(String.valueOf(addedDoctorsSpecFilterList.size()));
        if (isPendingVisibale) {
            addedRepToPendingLocationFilterList =repArrayListToFilter;
            locationPresenter.showAddRepFilter(repArrayListToFilter,pendingLocationMRFilterList);
        }else {
            addedRepToapprovedLocationFilterList=repArrayListToFilter;
            locationPresenter.showAddRepFilter(repArrayListToFilter,approvedLocationMRFilterList);
        }

    }

    @Override
    public void addRepToFilter(ArrayList<Rep> addedArrayListToFilter) {
        LocationPendingMRFilterAdapter locationPendingMRFilterAdapter = new LocationPendingMRFilterAdapter(this,addedArrayListToFilter,this);
        recyclerViewLocationsFilterRep.setAdapter(locationPendingMRFilterAdapter);
    }

    @Override
    public void showSelectedFilterLocation(ArrayList<Rep> filterList) {
        LocationPendingMRFilterAdapter locationPendingMRFilterAdapter = new LocationPendingMRFilterAdapter(this,filterList,this);
        recyclerViewLocationsFilterRep.setAdapter(locationPendingMRFilterAdapter);
    }

    @Override
    public void repListForFilter(ArrayList<Rep> repArrayList) {
        LocationPendingMRFilterAdapter locationPendingMRFilterAdapter = new LocationPendingMRFilterAdapter(this,repArrayList,this);
        recyclerViewLocationsFilterRep.setAdapter(locationPendingMRFilterAdapter);
    }


    @Override
    public void locationFilterListEmpty(String msg, ArrayList<LocationEntitie> locationArrayList) {

    }

    @Override
    public void locationFilterList(ArrayList<LocationEntitie> locationArrayList) {

        if (isPendingVisibale) {
            locationPendingAdapter = new LocationPendingAdapter(this, locationArrayList, this);
            recyclerViewLocations.setAdapter(locationPendingAdapter);
        } else {
            locationApprovedAdapter = new LocationApprovedAdapter(this, locationArrayList, this);
            recyclerViewLocations.setAdapter(locationApprovedAdapter);

        }

        dialogFilter.dismiss();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(this);
        LatLng locationMap;

        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.getUiSettings().setAllGesturesEnabled(true);
        googleMap.getUiSettings().setMapToolbarEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        boolean success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map));
        locationMap = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions().position(locationMap).title(""));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationMap, 15));

    }


    private void setFilterLocationRepRecycalView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewLocationsFilterRep.setLayoutManager(layoutManager);
        recyclerViewLocationsFilterRep.setItemAnimator(new DefaultItemAnimator());
        recyclerViewLocationsFilterRep.setNestedScrollingEnabled(false);

    }

    public void setLocationsRecycalView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewLocations.setLayoutManager(layoutManager);
        recyclerViewLocations.setItemAnimator(new DefaultItemAnimator());
        recyclerViewLocations.setNestedScrollingEnabled(false);
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

    private void showFilterDialog() {

        dialogFilter = new Dialog(this);
        dialogFilter.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogFilter.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogFilter.setContentView(R.layout.dialog_location_filter);
        dialogFilter.setCancelable(true);


        final AutoCompleteTextView autoTextViewLocation = dialogFilter.findViewById(R.id.autoCompleteTextView_loc);
        final ImageView imageViewLocationSerach = dialogFilter.findViewById(R.id.imageView_loc_search);

        Button btnFilter = dialogFilter.findViewById(R.id.btn_filter);
        Button btnReset = dialogFilter.findViewById(R.id.btn_reset);

        recyclerViewLocationsFilterRep = dialogFilter.findViewById(R.id.recyclerView_rep);


        setFilterLocationRepRecycalView();



        autoTextViewLocation.setAdapter(null);
        ArrayAdapter<String> locationAdapterList;
        if (isPendingVisibale) {

            locationPresenter.setSelectedFilterRep(pendingLocationMRFilterList,addedRepToPendingLocationFilterList);
            locationAdapterList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, pendingLocationNameFilterList);
            autoTextViewLocation.setAdapter(locationAdapterList);
        } else {

            locationPresenter.setSelectedFilterRep(approvedLocationMRFilterList,addedRepToapprovedLocationFilterList);
            locationAdapterList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, approvedLocationNameFilterList);
            autoTextViewLocation.setAdapter(locationAdapterList);

        }

        autoTextViewLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                String selectedLoc = parent.getItemAtPosition(pos).toString();
                if (isPendingVisibale) {
                    locationPresenter.searchLocPendingForFilter(pendingLocationList, selectedLoc);
                } else {
                    locationPresenter.searchLocPendingForFilter(approvedLocationList, selectedLoc);
                }

            }
        });

        imageViewLocationSerach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPendingVisibale) {
                    locationPresenter.searchLocPendingForFilter(pendingLocationList, autoTextViewLocation.getText().toString());
                } else {
                    locationPresenter.searchLocPendingForFilter(approvedLocationList, autoTextViewLocation.getText().toString());
                }

            }
        });






        RelativeLayout layoutRepsBar = dialogFilter.findViewById(R.id.relativelayout_rep_bar_image);
        final ImageView imageViewLayoutRepsImage = dialogFilter.findViewById(R.id.imageView_layout_rep_image);
        final RelativeLayout layoutRecyclerViewRrep = dialogFilter.findViewById(R.id.layout_recyclerView_rep);


        final AutoCompleteTextView autoTextViewReps = dialogFilter.findViewById(R.id.autoCompleteTextView_rep);
        final ImageView imageViewRepSerach = dialogFilter.findViewById(R.id.imageView_rep_search);



        ArrayAdapter<String> repAdapterList;
        if (isPendingVisibale) {
            repAdapterList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, pendingLocationMRNameFilterList);
            autoTextViewReps.setAdapter(repAdapterList);
        } else {

            repAdapterList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, approvedLocationMRNameFilterList);
            autoTextViewReps.setAdapter(repAdapterList);
        }



        autoTextViewReps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                String selectedLoc = parent.getItemAtPosition(pos).toString();
                if (isPendingVisibale) {
                    locationPresenter.searchRepForFilter(pendingLocationMRFilterList, selectedLoc);
                } else {
                    locationPresenter.searchRepForFilter(approvedLocationMRFilterList, selectedLoc);
                }

            }
        });
        imageViewRepSerach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPendingVisibale) {
                    locationPresenter.searchRepForFilter(pendingLocationMRFilterList, autoTextViewReps.getText().toString());
                } else {
                    locationPresenter.searchRepForFilter(approvedLocationMRFilterList, autoTextViewReps.getText().toString());
                }

            }
        });



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



        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPendingVisibale) {
                    locationPresenter.locationFilter(pendingLocationList,addedRepToPendingLocationFilterList);
                } else {
                    locationPresenter.locationFilter(approvedLocationList,addedRepToapprovedLocationFilterList);
                }



            }
        });


        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isPendingVisibale) {
                    addedRepToPendingLocationFilterList.clear();
                    locationPresenter.locationFilter(pendingLocationList,addedRepToPendingLocationFilterList);
                } else {
                    addedRepToapprovedLocationFilterList.clear();
                    locationPresenter.locationFilter(approvedLocationList,addedRepToapprovedLocationFilterList);
                }


            }
        });



        dialogFilter.show();


    }

}
