package emerge.project.mrsolution_admin_micro.ui.activity.products.assign_products;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import emerge.project.mrsolution_admin_micro.R;
import emerge.project.mrsolution_admin_micro.ui.activity.doctors.DoctorsActivity;
import emerge.project.mrsolution_admin_micro.ui.activity.locations.LocationActivity;
import emerge.project.mrsolution_admin_micro.ui.activity.visits.VisitsActivity;
import emerge.project.mrsolution_admin_micro.ui.adapters.navigation.NavigationAdapter;
import emerge.project.mrsolution_admin_micro.ui.adapters.assing_products.AssigendPrinciplesAdapter;
import emerge.project.mrsolution_admin_micro.ui.adapters.assing_products.AssigendProductByUserAdapter;
import emerge.project.mrsolution_admin_micro.ui.adapters.assing_products.AssigendRepSearchAdapter;
import emerge.project.mrsolution_admin_micro.ui.adapters.assing_products.AssigendRepsAdapter;
import emerge.project.mrsolution_admin_micro.ui.adapters.assing_products.AssignedProductAdapter;
import emerge.project.mrsolution_admin_micro.ui.adapters.assing_products.ProductAdapter;
import emerge.project.mrsolution_admin_micro.utils.entittes.Navigation;
import emerge.project.mrsolution_admin_micro.utils.entittes.Principles;
import emerge.project.mrsolution_admin_micro.utils.entittes.Products;
import emerge.project.mrsolution_admin_micro.utils.entittes.Rep;

public class AssignProductsActivity extends Activity implements AssignProductsView {


    @BindView(R.id.drawer_layout)
    DrawerLayout dLayout;
    @BindView(R.id.recyclerView_assinged_products)
    RecyclerView recyclerViewAssingedProducts;
    @BindView(R.id.recyclerview_rep)
    RecyclerView recyclerviewRep;
    @BindView(R.id.recyclerview_principles)
    RecyclerView recyclerviewPrinciples;
    @BindView(R.id.recyclerview_products)
    RecyclerView recyclerviewProducts;

    @BindView(R.id.recyclerview_user_assign_products)
    RecyclerView recyclerviewUserAssignProducts;


    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationView bottomNavigationBar;


    @BindView(R.id.relativelayout_assigned)
    RelativeLayout relativelayoutAssigned;

    @BindView(R.id.relativelayout_assing)
    RelativeLayout relativelayoutAssing;


    @BindView(R.id.scrollView_assing_products)
    ScrollView scrollViewAssingProducts;


    @BindView(R.id.relativeLayout_assinged_products)
    RelativeLayout relativeLayoutAssingedProducts;


    @BindView(R.id.autoCompleteTextView_rep)
    AutoCompleteTextView textViewRep;

    @BindView(R.id.listview_navigation)
    ListView listViewNavigation;

    @BindView(R.id.include_progres)
    View includeProgres;

    @BindView(R.id.include_nointernt)
    View includeNointernt;


    AssignProductsPresenter assignProductsPresenter;

    AssignedProductAdapter assignedProductAdapter;
    AssigendRepsAdapter assigendRepsAdapter;
    AssigendPrinciplesAdapter assigendPrinciplesAdapter;
    AssigendRepSearchAdapter assigendRepSearchAdapter;
    AssigendProductByUserAdapter assigendProductByUserAdapter;

    ProductAdapter productAdapter;
    NavigationAdapter navigationAdapter;


    int selectedRepId=0;


    ArrayList<Rep> repArrayList;
    ArrayList<Products> addedproductArrayList ;


    int sdk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_products);
        ButterKnife.bind(this);
        sdk = android.os.Build.VERSION.SDK_INT;


        setAssindProductsRecycalView();
        setRepRecycalView();
        setPrinciplesRecycalView();
        setProductsRecycalView();
        setUserAssignProductsRecycalView();

        addedproductArrayList = new ArrayList<Products>();

        bottomNavigationBar.setSelectedItemId(R.id.navigation_products);
        bottomNavigationBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        assignProductsPresenter = new AssignProductsPresenterImpli(this);



        bloackUserInteraction();
        includeProgres.setVisibility(View.VISIBLE);
        assignProductsPresenter.getProductsAssignToReps(this);
        assignProductsPresenter.setNavigation();

        textViewRep.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                String selectedRep = parent.getItemAtPosition(pos).toString();
                assignProductsPresenter.searchRep(repArrayList, selectedRep);
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }






    @OnClick(R.id.imageView2)
    public void onClickSliderMenue(View view) {
        dLayout.openDrawer(Gravity.LEFT);
    }

    @OnClick(R.id.btn_assign)
    public void onClickroductAssign(View view) {
        bloackUserInteraction();
        includeProgres.setVisibility(View.VISIBLE);
        assignProductsPresenter.assignProducttoRep(this,selectedRepId,addedproductArrayList);
    }


    @OnItemClick(R.id.listview_navigation) void onItemClick(int position) {
        Toast.makeText(this, "You clicked: " + position, Toast.LENGTH_LONG).show();
    }


    @OnClick(R.id.btn_tryagin)
    public void onTryAgian(View view) {
        includeNointernt.setVisibility(View.GONE);

    }

    @OnClick(R.id.imageView_rep_search)
    public void onClickRepSearch(View view) {
        if (textViewRep.getVisibility() == View.VISIBLE) {
            assignProductsPresenter.searchRep(repArrayList, textViewRep.getText().toString());
        } else {
            textViewRep.setVisibility(View.VISIBLE);
        }

        if (recyclerviewRep.getVisibility() == View.VISIBLE) {
        } else {
            recyclerviewRep.setVisibility(View.VISIBLE);
        }
    }


    @OnClick(R.id.relativelayout_assigned)
    public void onClickAssigned(View view) {


        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            relativelayoutAssigned.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_left_primary));
            relativelayoutAssing.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_left_primary));
        } else {
            relativelayoutAssigned.setBackground(getResources().getDrawable(R.drawable.bg_left_primary));
            relativelayoutAssing.setBackground(getResources().getDrawable(R.drawable.bg_right_white));
        }

        scrollViewAssingProducts.setVisibility(View.GONE);
        relativeLayoutAssingedProducts.setVisibility(View.VISIBLE);
        assignProductsPresenter.getProductsAssignToReps(this);


    }


    @OnClick(R.id.relativelayout_assing)
    public void onClickAssing(View view) {
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            relativelayoutAssing.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_right_primary));
            relativelayoutAssigned.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_left_white));

        } else {
            relativelayoutAssing.setBackground(getResources().getDrawable(R.drawable.bg_right_primary));
            relativelayoutAssigned.setBackground(getResources().getDrawable(R.drawable.bg_left_white));

        }



        bloackUserInteraction();
        includeProgres.setVisibility(View.VISIBLE);

        relativeLayoutAssingedProducts.setVisibility(View.GONE);
        scrollViewAssingProducts.setVisibility(View.VISIBLE);
        assignProductsPresenter.getReps(this,selectedRepId);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_visits:
                    Intent intentVisits = new Intent(AssignProductsActivity.this, VisitsActivity.class);
                    Bundle bndlanimationVisits = ActivityOptions.makeCustomAnimation(AssignProductsActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intentVisits, bndlanimationVisits);
                    finish();
                    return true;
                case R.id.navigation_location:
                    Intent intentLocation = new Intent(AssignProductsActivity.this, LocationActivity.class);
                    Bundle bndlanimationLocation = ActivityOptions.makeCustomAnimation(AssignProductsActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intentLocation, bndlanimationLocation);
                    finish();
                    return true;
                case R.id.navigation_doctors:
                    Intent intentDoc = new Intent(AssignProductsActivity.this, DoctorsActivity.class);
                    Bundle bndlanimationDoc = ActivityOptions.makeCustomAnimation(AssignProductsActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                    startActivity(intentDoc, bndlanimationDoc);
                    finish();

                    return true;
                case R.id.navigation_products:

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
    public void productsAssignToReps(ArrayList<Rep> repArrayList) {

        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        includeNointernt.setVisibility(View.GONE);
        recyclerViewAssingedProducts.setVisibility(View.VISIBLE);

        assignedProductAdapter = new AssignedProductAdapter(this, repArrayList, this);
        recyclerViewAssingedProducts.setAdapter(assignedProductAdapter);

    }

    public void setUserAssignProductsRecycalView() {
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerviewUserAssignProducts.setLayoutManager(layoutManager);
        recyclerviewUserAssignProducts.setItemAnimator(new DefaultItemAnimator());
        recyclerviewUserAssignProducts.setNestedScrollingEnabled(false);

    }

    @Override
    public void productsAssignToRepsEmpty() {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        includeNointernt.setVisibility(View.GONE);


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No product assign to reps");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

        alertDialogBuilder.show();


    }

    @Override
    public void productsAssignToRepsGetingFail(String failMsg) {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        includeNointernt.setVisibility(View.GONE);
        recyclerViewAssingedProducts.setVisibility(View.GONE);
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(failMsg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            bloackUserInteraction();
                            includeProgres.setVisibility(View.VISIBLE);
                            assignProductsPresenter.getProductsAssignToReps(AssignProductsActivity.this);
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
    public void productsAssignToRepsNetworkFail() {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        includeNointernt.setVisibility(View.VISIBLE);
        recyclerViewAssingedProducts.setVisibility(View.GONE);


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
    public void productAssignFullDetais(ArrayList<Products> productsArrayList) {

       /* final Dialog dialogRepProduct = new Dialog(this);
        dialogRepProduct.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogRepProduct.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogRepProduct.setContentView(R.layout.dialog_product_to_rep);
        dialogRepProduct.setCancelable(true);

        RecyclerView recyclerViewDialogRepProduct = dialogRepProduct.findViewById(R.id.recyclerview_dialog_assign_products);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerViewDialogRepProduct.setLayoutManager(mLayoutManager);
        recyclerViewDialogRepProduct.setItemAnimator(new DefaultItemAnimator());
        recyclerViewDialogRepProduct.setNestedScrollingEnabled(true);

        AssignedProductDialogAdapter assignedProductDialogAdapter = new AssignedProductDialogAdapter(this, productsArrayList);
        recyclerViewDialogRepProduct.setAdapter(assignedProductDialogAdapter);


        dialogRepProduct.show();*/


    }

    @Override
    public void repList(ArrayList<Rep> reparraylist, ArrayList<String> repnames) {


        repArrayList = reparraylist;
        assigendRepsAdapter = new AssigendRepsAdapter(this, reparraylist, this);
        recyclerviewRep.setAdapter(assigendRepsAdapter);

        ArrayAdapter<String> productAdapterList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, repnames);
        textViewRep.setAdapter(productAdapterList);
        assignProductsPresenter.getPrinciples(this);
    }

    @Override
    public void repsEmpty() {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No Medical Reps approved or added yet");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

        alertDialogBuilder.show();

    }

    @Override
    public void repsGetingFail(String failMsg, final int repID) {
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
                            assignProductsPresenter.getReps(AssignProductsActivity.this,repID);
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
    public void repsGetingNetworkFail() {
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
    public void selectedRep(Rep rep) {
        selectedRepId = rep.getId();
        addedproductArrayList=rep.getRepProducts();

        if (addedproductArrayList.isEmpty()) {
            recyclerviewUserAssignProducts.setAdapter(null);
        } else {
            AssigendProductByUserAdapter  assigendProductByUserAdapter = new AssigendProductByUserAdapter(this, addedproductArrayList, this);
            recyclerviewUserAssignProducts.setAdapter(assigendProductByUserAdapter);
        }

    }

    @Override
    public void principlesList(ArrayList<Principles> principlesepArrayList) {

        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        assigendPrinciplesAdapter = new AssigendPrinciplesAdapter(this, principlesepArrayList, this);
        recyclerviewPrinciples.setAdapter(assigendPrinciplesAdapter);

    }

    @Override
    public void principlesEmpty() {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No Principles approved or added yet");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

        alertDialogBuilder.show();

    }

    @Override
    public void principlesGetingFail(String failMsg) {
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
                            assignProductsPresenter.getPrinciples(AssignProductsActivity.this);
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
    public void principlesGetingNetworkFail() {
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
    public void productsByPrincipleList(ArrayList<Products> productArrayList) {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        productAdapter = new ProductAdapter(this, productArrayList, this);
        recyclerviewProducts.setAdapter(productAdapter);
    }

    @Override
    public void productsByPrincipleEmpty() {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No products for this principles");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

        alertDialogBuilder.show();


    }

    @Override
    public void productsByPrincipleGetingFail(String failMsg, final int principleId) {
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
                            assignProductsPresenter.getProductsByPrinciple(AssignProductsActivity.this, principleId);
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
    public void productsByPrincipleGetingNetworkFail() {

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
    public void selectedPrinciple(Principles principles) {
          bloackUserInteraction();
         includeProgres.setVisibility(View.VISIBLE);
        assignProductsPresenter.getProductsByPrinciple(this, principles.getId());
    }

    @Override
    public void addedProduct(ArrayList<Products> productArrayList) {
        if (productArrayList.isEmpty()) {
        } else {
            assigendProductByUserAdapter = new AssigendProductByUserAdapter(this, productArrayList, this);
            recyclerviewUserAssignProducts.setAdapter(assigendProductByUserAdapter);
        }

    }

    @Override
    public void searchRepList(ArrayList<Rep> repArrayList) {
        assigendRepsAdapter = new AssigendRepsAdapter(this, repArrayList, this);
        recyclerviewRep.setAdapter(assigendRepsAdapter);
    }

    @Override
    public void editedProductList(ArrayList<Products> productList) {
        addedproductArrayList=productList;
    }

    @Override
    public void assignProducttoRepEmpty(String failMsg) {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        Toast.makeText(this, failMsg, Toast.LENGTH_LONG).show();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(failMsg);
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

        alertDialogBuilder.show();
    }

    @Override
    public void assignProducttoRepSuccessful() {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Assign products successful");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

        alertDialogBuilder.show();

    }

    @Override
    public void assignProducttoRepGetingFail(String failMsg, final int repid, final ArrayList<Products> productArrayList) {
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
                            assignProductsPresenter.assignProducttoRep(AssignProductsActivity.this,repid,productArrayList);
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
    public void assignProducttoRepGetingNetworkFail() {
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
    public void addedProductStart(Products product, boolean addOrRemove) {
        assignProductsPresenter.addProductToRep(addedproductArrayList,product,addOrRemove);

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

    public void setAssindProductsRecycalView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewAssingedProducts.setLayoutManager(layoutManager);
        recyclerViewAssingedProducts.setItemAnimator(new DefaultItemAnimator());
        recyclerViewAssingedProducts.setNestedScrollingEnabled(false);
    }

    public void setRepRecycalView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerviewRep.setLayoutManager(layoutManager);
        recyclerviewRep.setItemAnimator(new DefaultItemAnimator());
        recyclerviewRep.setNestedScrollingEnabled(false);

    }


    public void setPrinciplesRecycalView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerviewPrinciples.setLayoutManager(layoutManager);
        recyclerviewPrinciples.setItemAnimator(new DefaultItemAnimator());
        recyclerviewPrinciples.setNestedScrollingEnabled(false);

    }

    public void setProductsRecycalView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerviewProducts.setLayoutManager(layoutManager);
        recyclerviewProducts.setItemAnimator(new DefaultItemAnimator());
        recyclerviewProducts.setNestedScrollingEnabled(false);

    }


}
