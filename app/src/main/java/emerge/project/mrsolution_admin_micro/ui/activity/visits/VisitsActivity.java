package emerge.project.mrsolution_admin_micro.ui.activity.visits;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import emerge.project.mrsolution_admin_micro.R;
import emerge.project.mrsolution_admin_micro.ui.activity.doctors.DoctorsActivity;
import emerge.project.mrsolution_admin_micro.ui.activity.locations.LocationActivity;
import emerge.project.mrsolution_admin_micro.ui.activity.products.assign_products.AssignProductsActivity;
import emerge.project.mrsolution_admin_micro.ui.adapters.navigation.NavigationAdapter;
import emerge.project.mrsolution_admin_micro.ui.adapters.visits.VisitsAdapter;
import emerge.project.mrsolution_admin_micro.ui.adapters.visits.VisitsFilterDoctorsAdapter;
import emerge.project.mrsolution_admin_micro.ui.adapters.visits.VisitsFilterLocationAdapter;
import emerge.project.mrsolution_admin_micro.ui.adapters.visits.VisitsFilterProductAdapter;
import emerge.project.mrsolution_admin_micro.ui.adapters.visits.VisitsFilterRepAdapter;
import emerge.project.mrsolution_admin_micro.utils.entittes.Doctor;
import emerge.project.mrsolution_admin_micro.utils.entittes.Navigation;
import emerge.project.mrsolution_admin_micro.utils.entittes.Products;
import emerge.project.mrsolution_admin_micro.utils.entittes.Rep;
import emerge.project.mrsolution_admin_micro.utils.entittes.Visit;
import emerge.project.mrsolution_admin_micro.utils.entittes.LocationEntitie;


public class VisitsActivity extends Activity implements VisitsView {


    @BindView(R.id.drawer_layout)
    DrawerLayout dLayout;

    @BindView(R.id.recyclerView_visits)
    RecyclerView recyclerViewVisits;

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationView bottomNavigationBar;


    @BindView(R.id.listview_navigation)
    ListView listViewNavigation;

    @BindView(R.id.include_progres)
    View includeProgres;

    @BindView(R.id.include_nointernt)
    View includeNointernt;


    RecyclerView recyclerFilterDoctors;
    RecyclerView recyclerFilterLocation;
    RecyclerView recyclerFilterProduct;
    RecyclerView recyclerFilterRep;

    TextView textviewSelectedProCount;
    TextView textviewSelectedLocationCount;
    TextView textviewSelectedDocCount;
    TextView textviewSelectedRepCount;



    VisitsAdapter visitsAdapter;
    NavigationAdapter navigationAdapter;

    VisitsPresenter visitsPresenter;

    Dialog dialogFilter;


    ArrayList<Visit> visitsList;

    ArrayList<Doctor> visitsDoctorsFilterList;
    ArrayList<String> visitsDoctorsNameFilterList;


    ArrayList<LocationEntitie> visitsLocationFilterList;
    ArrayList<String> visitsLocationNameFilterList;


    ArrayList<Products> visitsProductsFilterList;
    ArrayList<String> visitsProductsNameFilterList;

    ArrayList<Rep> visitsRepFilterList;
    ArrayList<String> visitsRepNameFilterList;


    ArrayList<Doctor> addedvisitsDoctorsFilterList;
    ArrayList<LocationEntitie> addedvisitsLocationFilterList;
    ArrayList<Products> addedvisitsProductsFilterList;
    ArrayList<Rep> addedvisitsRepFilterList;


    int filterMrId = 0;
    int locationID = 0;
    int doctorID = 0;
    String date = "";

    String filterDateStart = "", filterDateEnd = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visits);
        ButterKnife.bind(this);
        setVisitsRecycalView();

        bottomNavigationBar.setSelectedItemId(R.id.navigation_visits);
        bottomNavigationBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        visitsList = new ArrayList<Visit>();

        addedvisitsDoctorsFilterList = new ArrayList<Doctor>();
        addedvisitsLocationFilterList = new ArrayList<LocationEntitie>();
        addedvisitsProductsFilterList = new ArrayList<Products>();
        addedvisitsRepFilterList = new ArrayList<Rep>();

        visitsDoctorsNameFilterList = new ArrayList<String>();
        visitsLocationNameFilterList = new ArrayList<String>();
        visitsProductsNameFilterList = new ArrayList<String>();
        visitsRepNameFilterList = new ArrayList<String>();

        visitsPresenter = new VisitsPresenterImpli(this);

        bloackUserInteraction();
        includeProgres.setVisibility(View.VISIBLE);
        visitsPresenter.getVisits(this, filterMrId, locationID, doctorID, date);


    }

    @OnItemClick(R.id.listview_navigation)
    void onItemClick(int position) {


    }


    @OnClick(R.id.btn_tryagin)
    public void onTryAgian(View view) {
        includeNointernt.setVisibility(View.GONE);
        bloackUserInteraction();
        includeProgres.setVisibility(View.VISIBLE);
        visitsPresenter.getVisits(this, filterMrId, locationID, doctorID, date);

    }

    @OnClick(R.id.imageView2)
    public void onClickSliderMenue(View view) {
        dLayout.openDrawer(Gravity.LEFT);
    }


    @OnClick(R.id.imageView)
    public void onClickFilter(View view) {

        if (visitsList.isEmpty()) {
            Toast.makeText(this, "No Visits to filter", Toast.LENGTH_SHORT).show();
        } else {
            showFilterDialog();
        }

    }


    private void showFilterDialog(){
        dialogFilter = new Dialog(this);
        dialogFilter.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogFilter.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogFilter.setContentView(R.layout.dialog_visit_filter);
        dialogFilter.setCancelable(true);

        final DateRangeCalendarView calendarView = (DateRangeCalendarView) dialogFilter.findViewById(R.id.calendarView);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Light.otf");
        calendarView.setFonts(typeface);


        RelativeLayout relativelayoutDateImage = dialogFilter.findViewById(R.id.relativelayout_date_bar_image);
        final ImageView imageViewLayoutDateImage = dialogFilter.findViewById(R.id.imageView_layout_date_image);

        RelativeLayout relativelayoutDoctorImage = dialogFilter.findViewById(R.id.relativelayout_doctor_bar_image);
        final ImageView imageViewLayoutDoctorImage = dialogFilter.findViewById(R.id.imageView_layout_doctor_image);


        RelativeLayout relativelayoutLocationImage = dialogFilter.findViewById(R.id.relativeLayout_location_bar);
        final ImageView imageViewLayoutLocationImage = dialogFilter.findViewById(R.id.imageView_layout_location_image);

        RelativeLayout relativelayoutProductBarImage = dialogFilter.findViewById(R.id.relativelayout_product_bar_image);
        final ImageView imageViewLayoutProductImage = dialogFilter.findViewById(R.id.imageView_layout_product_image);


        RelativeLayout relativelayoutRepBarImage = dialogFilter.findViewById(R.id.relativelayout_rep_bar_image);
        final ImageView imageViewLayoutRepImage = dialogFilter.findViewById(R.id.imageView_layout_rep_image);




        Button btnFilter = dialogFilter.findViewById(R.id.btn_filter);
        Button btnReset = dialogFilter.findViewById(R.id.btn_reset);


        recyclerFilterDoctors = dialogFilter.findViewById(R.id.recyclerView_doctos);
        recyclerFilterLocation = dialogFilter.findViewById(R.id.recyclerView_location);
        recyclerFilterProduct = dialogFilter.findViewById(R.id.recyclerView_product);
        recyclerFilterRep = dialogFilter.findViewById(R.id.recyclerView_rep);


        textviewSelectedProCount = dialogFilter.findViewById(R.id.textview_selected_pro_count);
        textviewSelectedLocationCount = dialogFilter.findViewById(R.id.textview_selected_location_count);
        textviewSelectedDocCount = dialogFilter.findViewById(R.id.textview_selected_doc_count);
        textviewSelectedRepCount = dialogFilter.findViewById(R.id.textview_selected_rep_count);



        if (addedvisitsProductsFilterList.size() == 0) {
        } else {
            textviewSelectedProCount.setText(String.valueOf(addedvisitsProductsFilterList.size()));
        }

        if (addedvisitsLocationFilterList.size() == 0) {
        } else {
            textviewSelectedLocationCount.setText(String.valueOf(addedvisitsLocationFilterList.size()));
        }

        if (addedvisitsDoctorsFilterList.size() == 0) {
        } else {
            textviewSelectedDocCount.setText(String.valueOf(addedvisitsDoctorsFilterList.size()));
        }


        if (addedvisitsRepFilterList.size() == 0) {
        } else {
            textviewSelectedRepCount.setText(String.valueOf(addedvisitsRepFilterList.size()));
        }


        final RelativeLayout layoutRecyclerViewDoctos = dialogFilter.findViewById(R.id.layout_recyclerView_doctos);
        final RelativeLayout layoutRecyclerViewLocation = dialogFilter.findViewById(R.id.layout_recyclerView_location);
        final RelativeLayout layoutRecyclerViewProduct = dialogFilter.findViewById(R.id.layout_recyclerView_product);
        final RelativeLayout layoutRecyclerViewRep = dialogFilter.findViewById(R.id.layout_recyclerView_rep);


        setVisitsFilterLocationRecycalView();
        setVisitsFilterDocRecycalView();
        setVisitsFilterProductRecycalView();
        setVisitsFilterReoRecycalView();


        visitsPresenter.setSelectedFilterDoctors(visitsDoctorsFilterList, addedvisitsDoctorsFilterList);
        visitsPresenter.setSelectedFilterLocation(visitsLocationFilterList, addedvisitsLocationFilterList);
        visitsPresenter.setSelectedFilterProducts(visitsProductsFilterList, addedvisitsProductsFilterList);
        visitsPresenter.setSelectedFilterRep(visitsRepFilterList, addedvisitsRepFilterList);


        final AutoCompleteTextView autoTextViewDoc = dialogFilter.findViewById(R.id.autoCompleteTextView_doc);
        final ImageView imageViewDocSearch = dialogFilter.findViewById(R.id.imageView_doc_search);

        ArrayAdapter<String> docAdapterList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, visitsDoctorsNameFilterList);
        autoTextViewDoc.setAdapter(docAdapterList);

        autoTextViewDoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                String selectedLoc = parent.getItemAtPosition(pos).toString();
                visitsPresenter.searchDocForFilter(visitsDoctorsFilterList, selectedLoc);
            }
        });

        imageViewDocSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visitsPresenter.searchDocForFilter(visitsDoctorsFilterList, autoTextViewDoc.getText().toString());
            }
        });


        final AutoCompleteTextView autoTextViewLocation = dialogFilter.findViewById(R.id.autoCompleteTextView_loc);
        final ImageView imageViewLocationSearch = dialogFilter.findViewById(R.id.imageView_loc_search);

        ArrayAdapter<String> locAdapterList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, visitsLocationNameFilterList);
        autoTextViewLocation.setAdapter(locAdapterList);

        autoTextViewLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                String selectedLoc = parent.getItemAtPosition(pos).toString();
                visitsPresenter.searchLocForFilter(visitsLocationFilterList, selectedLoc);
            }
        });

        imageViewLocationSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visitsPresenter.searchLocForFilter(visitsLocationFilterList, autoTextViewLocation.getText().toString());
            }
        });


        final AutoCompleteTextView autoTextViewProduct = dialogFilter.findViewById(R.id.autoCompleteTextView_pro);
        final ImageView imageViewProductSearch = dialogFilter.findViewById(R.id.imageView_pro_search);


        ArrayAdapter<String> proAdapterList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, visitsProductsNameFilterList);
        autoTextViewProduct.setAdapter(proAdapterList);

        autoTextViewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                String selectedLoc = parent.getItemAtPosition(pos).toString();
                visitsPresenter.searchProductsForFilter(visitsProductsFilterList, selectedLoc);
            }
        });

        imageViewProductSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visitsPresenter.searchProductsForFilter(visitsProductsFilterList, autoTextViewProduct.getText().toString());
            }
        });


        final AutoCompleteTextView autoTextViewRep = dialogFilter.findViewById(R.id.autoCompleteTextView_rep);
        final ImageView imageViewRepSearch = dialogFilter.findViewById(R.id.imageView_rep_search);


        ArrayAdapter<String> repAdapterList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, visitsRepNameFilterList);
        autoTextViewRep.setAdapter(repAdapterList);

        autoTextViewRep.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                String selectedLoc = parent.getItemAtPosition(pos).toString();

                visitsPresenter.searchRepForFilter(visitsRepFilterList, selectedLoc);
            }
        });

        imageViewRepSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visitsPresenter.searchRepForFilter(visitsRepFilterList, autoTextViewRep.getText().toString());
            }
        });


        relativelayoutRepBarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutRecyclerViewRep.getVisibility() == View.VISIBLE) {
                    layoutRecyclerViewRep.setVisibility(View.GONE);
                    imageViewLayoutRepImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_control_point_black_24dp));
                } else {
                    layoutRecyclerViewRep.setVisibility(View.VISIBLE);
                    imageViewLayoutRepImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove_circle_outline_black_24dp));
                }
            }
        });

        relativelayoutProductBarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutRecyclerViewProduct.getVisibility() == View.VISIBLE) {
                    layoutRecyclerViewProduct.setVisibility(View.GONE);
                    imageViewLayoutProductImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_control_point_black_24dp));
                } else {
                    layoutRecyclerViewProduct.setVisibility(View.VISIBLE);
                    imageViewLayoutProductImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove_circle_outline_black_24dp));
                }

            }
        });

        relativelayoutDoctorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutRecyclerViewDoctos.getVisibility() == View.VISIBLE) {
                    layoutRecyclerViewDoctos.setVisibility(View.GONE);
                    imageViewLayoutDoctorImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_control_point_black_24dp));
                } else {
                    layoutRecyclerViewDoctos.setVisibility(View.VISIBLE);
                    imageViewLayoutDoctorImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove_circle_outline_black_24dp));
                }

            }
        });

        relativelayoutDateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calendarView.getVisibility() == View.VISIBLE) {
                    calendarView.setVisibility(View.GONE);
                    imageViewLayoutDateImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_control_point_black_24dp));
                } else {
                    calendarView.setVisibility(View.VISIBLE);
                    imageViewLayoutDateImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove_circle_outline_black_24dp));
                }

            }
        });

        relativelayoutLocationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutRecyclerViewLocation.getVisibility() == View.VISIBLE) {
                    layoutRecyclerViewLocation.setVisibility(View.GONE);
                    imageViewLayoutLocationImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_control_point_black_24dp));
                } else {
                    layoutRecyclerViewLocation.setVisibility(View.VISIBLE);
                    imageViewLayoutLocationImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove_circle_outline_black_24dp));
                }

            }
        });

        final SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date dateS = null;
        Date dateE = null;

        try {
            dateS= targetFormat.parse(filterDateStart);
            dateE= targetFormat.parse(filterDateEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (filterDateStart.isEmpty()) {

        } else if (filterDateEnd.isEmpty()) {
            Calendar startSelectionDate = Calendar.getInstance();
            startSelectionDate.setTime(dateS);
            calendarView.setSelectedDateRange(startSelectionDate, startSelectionDate);

        } else {
            Calendar startSelectionDate = Calendar.getInstance();
            startSelectionDate.setTime(dateS);
            Calendar endSelectionDate = (Calendar) startSelectionDate.clone();
            endSelectionDate.setTime(dateE);
            calendarView.setSelectedDateRange(startSelectionDate, endSelectionDate);
        }

        calendarView.setCalendarListener(new DateRangeCalendarView.CalendarListener() {
            @Override
            public void onFirstDateSelected(Calendar startDate) {
                filterDateStart = targetFormat.format(startDate.getTime());

            }

            @Override
            public void onDateRangeSelected(Calendar startDate, Calendar endDate) {
                filterDateStart = targetFormat.format(startDate.getTime());
                filterDateEnd = targetFormat.format(endDate.getTime());

            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visitsPresenter.visitsFilter(visitsList, filterDateStart, filterDateEnd, addedvisitsDoctorsFilterList, addedvisitsLocationFilterList, addedvisitsProductsFilterList,addedvisitsRepFilterList);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                filterDateStart = "";
                filterDateEnd = "";

                addedvisitsDoctorsFilterList.clear();
                addedvisitsLocationFilterList.clear();
                addedvisitsProductsFilterList.clear();
                addedvisitsRepFilterList.clear();


                visitsPresenter.visitsFilter(visitsList, filterDateStart, filterDateEnd, addedvisitsDoctorsFilterList, addedvisitsLocationFilterList, addedvisitsProductsFilterList,addedvisitsRepFilterList);
            }
        });

        dialogFilter.show();

    }



    private void setVisitsRecycalView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewVisits.setLayoutManager(layoutManager);
        recyclerViewVisits.setItemAnimator(new DefaultItemAnimator());
        recyclerViewVisits.setNestedScrollingEnabled(false);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_visits:
                    return true;
                case R.id.navigation_location:
                    Intent intentLocation = new Intent(VisitsActivity.this, LocationActivity.class);
                    Bundle bndlanimationLocation = ActivityOptions.makeCustomAnimation(VisitsActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intentLocation, bndlanimationLocation);
                    finish();
                    return true;
                case R.id.navigation_doctors:
                    Intent intentDoctors = new Intent(VisitsActivity.this, DoctorsActivity.class);
                    Bundle bndlanimationDoctors = ActivityOptions.makeCustomAnimation(VisitsActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intentDoctors, bndlanimationDoctors);
                    finish();
                    return true;
                case R.id.navigation_products:
                    Intent intentPro = new Intent(VisitsActivity.this, AssignProductsActivity.class);
                    Bundle bndlanimationPro = ActivityOptions.makeCustomAnimation(VisitsActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intentPro, bndlanimationPro);
                    finish();
                    return true;
            }
            return false;
        }
    };

    @Override
    public void visitsList(ArrayList<Visit> visitArrayList) {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        recyclerViewVisits.setVisibility(View.VISIBLE);
        visitsList=visitArrayList;
        visitsAdapter = new VisitsAdapter(this, visitArrayList, this);
        recyclerViewVisits.setAdapter(visitsAdapter);
    }

    @Override
    public void visitsListNoItems() {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No Visits available");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

        alertDialogBuilder.show();
    }

    @Override
    public void visitsListFail(String failMsg, int mrID, final int locationID, final int doctorID, final String date) {
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

                            visitsPresenter.getVisits(VisitsActivity.this, filterMrId, locationID, doctorID, date);
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
    public void visitsListNetworkFail() {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        recyclerViewVisits.setVisibility(View.GONE);
        includeNointernt.setVisibility(View.VISIBLE);

    }

    @Override
    public void visitsDoctorsList(ArrayList<Doctor> docListForFilter) {
        visitsDoctorsFilterList = docListForFilter;
    }

    @Override
    public void visitsDoctorsNameList(ArrayList<String> docNameListForFilter) {
        visitsDoctorsNameFilterList = docNameListForFilter;
    }

    @Override
    public void visitsLocationList(ArrayList<LocationEntitie> locListForFilter) {
        visitsLocationFilterList = locListForFilter;
    }

    @Override
    public void visitsLocationNameList(ArrayList<String> locNameListForFilter) {
        visitsLocationNameFilterList = locNameListForFilter;
    }

    @Override
    public void visitsProductsList(ArrayList<Products> productsListForFilter) {
        visitsProductsFilterList = productsListForFilter;
    }

    @Override
    public void visitsProductsNameList(ArrayList<String> productsNameList) {
        visitsProductsNameFilterList = productsNameList;
    }

    @Override
    public void visitsRepList(ArrayList<Rep> repListForFilter) {
        visitsRepFilterList = repListForFilter;
    }

    @Override
    public void visitsRepNameList(ArrayList<String> repNameListForFilter) {
        visitsRepNameFilterList = repNameListForFilter;
    }

    @Override
    public void navigationItems(ArrayList<Navigation> navigationArrayList) {
        navigationAdapter = new NavigationAdapter(this, navigationArrayList);
        listViewNavigation.setAdapter(navigationAdapter);
    }

    @Override
    public void visitDetails(Visit visit) {
        Dialog dialogFullDetails = new Dialog(this);
        dialogFullDetails.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogFullDetails.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogFullDetails.setContentView(R.layout.dialog_visits_details);
        dialogFullDetails.setCancelable(true);


        ImageView image = dialogFullDetails.findViewById(R.id.imageView_dialog_photo);
        TextView noimage = dialogFullDetails.findViewById(R.id.textView_noimage);
        TextView comments = dialogFullDetails.findViewById(R.id.textView_comments);

        final ProgressBar progressBar = dialogFullDetails.findViewById(R.id.progressBar);


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_photo_camera);
        requestOptions.error(R.drawable.ic_photo_camera);


        RequestListener<Bitmap> requestListener = new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }
        };


        if (visit.getImageUrl().isEmpty() || visit.getImageUrl().equals("")) {
            image.setVisibility(View.GONE);
            noimage.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        } else {
            image.setVisibility(View.VISIBLE);
            noimage.setVisibility(View.GONE);


            Glide.with(this)
                    .asBitmap()
                    .load(visit.getImageUrl())
                    .apply(requestOptions)
                    .listener(requestListener)
                    .into(image);
        }


        if (visit.getComment().isEmpty() || visit.getComment().equals("")) {
            comments.setText("No Comments");
        } else {
            comments.setText(visit.getComment());
        }

        dialogFullDetails.show();

    }

    @Override
    public void doctorsToVisitsFilterStart(Doctor doctor, boolean addOrRemove) {
        visitsPresenter.addDoctorsToVisitsFilter(addedvisitsDoctorsFilterList, doctor, addOrRemove);
    }

    @Override
    public void doctorsToVisitsFilter(ArrayList<Doctor> docArrayListToFilter) {
        addedvisitsDoctorsFilterList = docArrayListToFilter;
        textviewSelectedDocCount.setText(String.valueOf(addedvisitsDoctorsFilterList.size()));
        visitsPresenter.showAddDoctorsToFilter(docArrayListToFilter, visitsDoctorsFilterList);
    }

    @Override
    public void addDoctorsToFilter(ArrayList<Doctor> addedArrayListToFilter) {
        VisitsFilterDoctorsAdapter visitsFilterDoctorsAdapter = new VisitsFilterDoctorsAdapter(this, addedArrayListToFilter, this);
        recyclerFilterDoctors.setAdapter(visitsFilterDoctorsAdapter);
    }

    @Override
    public void showSelectedFilterDoctors(ArrayList<Doctor> filterList) {
        VisitsFilterDoctorsAdapter visitsFilterDoctorsAdapter = new VisitsFilterDoctorsAdapter(this, filterList, this);
        recyclerFilterDoctors.setAdapter(visitsFilterDoctorsAdapter);
    }

    @Override
    public void docListForFilter(ArrayList<Doctor> docArrayList) {
        VisitsFilterDoctorsAdapter visitsFilterDoctorsAdapter = new VisitsFilterDoctorsAdapter(this, docArrayList, this);
        recyclerFilterDoctors.setAdapter(visitsFilterDoctorsAdapter);
    }

    @Override
    public void locationToVisitsFilterStart(LocationEntitie locationEntitie, boolean addOrRemove) {
        visitsPresenter.addLocationToVisitsFilter(addedvisitsLocationFilterList, locationEntitie, addOrRemove);

    }

    @Override
    public void locationToVisitsFilter(ArrayList<LocationEntitie> locArrayListToFilter) {
        addedvisitsLocationFilterList = locArrayListToFilter;
        textviewSelectedLocationCount.setText(String.valueOf(addedvisitsLocationFilterList.size()));
        visitsPresenter.showAddLocationToFilter(locArrayListToFilter, visitsLocationFilterList);
    }

    @Override
    public void addLocationToFilter(ArrayList<LocationEntitie> addedArrayListToFilter) {
        VisitsFilterLocationAdapter visitsFilterLocationAdapter = new VisitsFilterLocationAdapter(this, addedArrayListToFilter, this);
        recyclerFilterLocation.setAdapter(visitsFilterLocationAdapter);
    }

    @Override
    public void showSelectedFilterLocation(ArrayList<LocationEntitie> filterList) {
        VisitsFilterLocationAdapter visitsFilterLocationAdapter = new VisitsFilterLocationAdapter(this, filterList, this);
        recyclerFilterLocation.setAdapter(visitsFilterLocationAdapter);
    }

    @Override
    public void locListForFilter(ArrayList<LocationEntitie> locArrayList) {
        VisitsFilterLocationAdapter visitsFilterLocationAdapter = new VisitsFilterLocationAdapter(this, locArrayList, this);
        recyclerFilterLocation.setAdapter(visitsFilterLocationAdapter);
    }

    @Override
    public void productsToVisitsFilterStart(Products products, boolean addOrRemove) {
        visitsPresenter.addProductsToVisitsFilter(addedvisitsProductsFilterList, products, addOrRemove);
    }

    @Override
    public void productsToVisitsFilter(ArrayList<Products> productsArrayListToFilter) {
        addedvisitsProductsFilterList = productsArrayListToFilter;
        textviewSelectedProCount.setText(String.valueOf(addedvisitsProductsFilterList.size()));
        visitsPresenter.showAddProductsToFilter(productsArrayListToFilter, visitsProductsFilterList);
    }

    @Override
    public void addProductsToFilter(ArrayList<Products> addedArrayListToFilter) {
        VisitsFilterProductAdapter visitsFilterProductAdapter = new VisitsFilterProductAdapter(this, addedArrayListToFilter, this);
        recyclerFilterProduct.setAdapter(visitsFilterProductAdapter);
    }

    @Override
    public void showSelectedFilterProducts(ArrayList<Products> filterList) {
        VisitsFilterProductAdapter visitsFilterProductAdapter = new VisitsFilterProductAdapter(this, filterList, this);
        recyclerFilterProduct.setAdapter(visitsFilterProductAdapter);
    }

    @Override
    public void productsListForFilter(ArrayList<Products> proArrayList) {
        VisitsFilterProductAdapter visitsFilterProductAdapter = new VisitsFilterProductAdapter(this, proArrayList, this);
        recyclerFilterProduct.setAdapter(visitsFilterProductAdapter);
    }

    @Override
    public void repToVisitsFilterStart(Rep rep, boolean addOrRemove) {
        visitsPresenter.addRepToVisitsFilter(addedvisitsRepFilterList, rep, addOrRemove);
    }

    @Override
    public void repToVisitsFilter(ArrayList<Rep> repArrayListToFilter) {

        addedvisitsRepFilterList = repArrayListToFilter;
        textviewSelectedRepCount.setText(String.valueOf(addedvisitsRepFilterList.size()));
        visitsPresenter.showAddRepToFilter(repArrayListToFilter, visitsRepFilterList);
    }

    @Override
    public void addRepToFilter(ArrayList<Rep> addedArrayListToFilter) {
        VisitsFilterRepAdapter visitsFilterRepAdapter = new VisitsFilterRepAdapter(this, addedArrayListToFilter, this);
        recyclerFilterRep.setAdapter(visitsFilterRepAdapter);

    }

    @Override
    public void showSelectedFilterRep(ArrayList<Rep> filterList) {
        VisitsFilterRepAdapter visitsFilterRepAdapter = new VisitsFilterRepAdapter(this, filterList, this);
        recyclerFilterRep.setAdapter(visitsFilterRepAdapter);
    }

    @Override
    public void repListForFilter(ArrayList<Rep> repArrayList) {
        VisitsFilterRepAdapter visitsFilterRepAdapter = new VisitsFilterRepAdapter(this, repArrayList, this);
        recyclerFilterRep.setAdapter(visitsFilterRepAdapter);
    }

    @Override
    public void visitsFilterListEmpty(String msg, ArrayList<Visit> visitArrayList) {
        dialogFilter.dismiss();
    }

    @Override
    public void visitsFilterList(ArrayList<Visit> visitArrayList) {
        visitsAdapter = new VisitsAdapter(this, visitArrayList, this);
        recyclerViewVisits.setAdapter(visitsAdapter);
        dialogFilter.dismiss();
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

    private void bloackUserInteraction() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void unBloackUserInteraction() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void setVisitsFilterDocRecycalView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerFilterDoctors.setLayoutManager(layoutManager);
        recyclerFilterDoctors.setItemAnimator(new DefaultItemAnimator());
        recyclerFilterDoctors.setNestedScrollingEnabled(false);

    }


    private void setVisitsFilterProductRecycalView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerFilterProduct.setLayoutManager(layoutManager);
        recyclerFilterProduct.setItemAnimator(new DefaultItemAnimator());
        recyclerFilterProduct.setNestedScrollingEnabled(false);

    }

    private void setVisitsFilterReoRecycalView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerFilterRep.setLayoutManager(layoutManager);
        recyclerFilterRep.setItemAnimator(new DefaultItemAnimator());
        recyclerFilterRep.setNestedScrollingEnabled(false);

    }


    private void setVisitsFilterLocationRecycalView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerFilterLocation.setLayoutManager(layoutManager);
        recyclerFilterLocation.setItemAnimator(new DefaultItemAnimator());
        recyclerFilterLocation.setNestedScrollingEnabled(false);

    }

}
