package emerge.project.mrsolution_admin_micro.ui.activity.visits;


import android.content.Context;

import java.util.ArrayList;

import emerge.project.mrsolution_admin_micro.utils.entittes.Doctor;
import emerge.project.mrsolution_admin_micro.utils.entittes.Navigation;
import emerge.project.mrsolution_admin_micro.utils.entittes.Products;
import emerge.project.mrsolution_admin_micro.utils.entittes.Rep;
import emerge.project.mrsolution_admin_micro.utils.entittes.Visit;
import emerge.project.mrsolution_admin_micro.utils.entittes.LocationEntitie;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface VisitsInteractor {


    interface OnsetNavigationFinishedListener {
        void navigationItems(ArrayList<Navigation> navigationArrayList);
    }
    void setNavigation(OnsetNavigationFinishedListener onsetNavigationFinishedListener);


    interface OnGetVisitsFinishedListener {
        void visitsList(ArrayList<Visit> visitArrayList);
        void visitsListNoItems();
        void visitsListFail(String failMsg,int mrID,int locationID,int doctorID,String date);
        void visitsListNetworkFail();


        void visitsDoctorsList( ArrayList<Doctor> docListForFilter);
        void visitsDoctorsNameList( ArrayList<String> docNameListForFilter);

        void visitsLocationList( ArrayList<LocationEntitie> locListForFilter);
        void visitsLocationNameList( ArrayList<String> locNameListForFilter);

        void visitsProductsList( ArrayList<Products> productsListForFilter);
        void visitsProductsNameList( ArrayList<String> productsNameListForFilter);

        void visitsRepList( ArrayList<Rep> repListForFilter);
        void visitsRepNameList( ArrayList<String> repNameListForFilter);

    }
    void getVisits(Context context,int mrID,int locationID,int doctorID,String date,OnGetVisitsFinishedListener onGetVisitsFinishedListener);


    interface OnShowVisitDetailsListener {
        void visitDetails(Visit visit);
    }
    void showVisitDetails(Visit visit,OnShowVisitDetailsListener onShowVisitDetailsListener);





    interface OnAddDoctorsToVisitsStartFilterFinishedListener {
        void doctorsToVisitsFilterStart(Doctor doctor,boolean addOrRemove);
    }
    void addDoctorsToVisitsFilterStart(Doctor doctor,boolean addOrRemove, OnAddDoctorsToVisitsStartFilterFinishedListener onAddDoctorsToVisitsStartFilterFinishedListener);

    interface OnAddDoctorsToVisitsFilterFinishedListener {
        void doctorsToVisitsFilter(ArrayList<Doctor> docArrayListToFilter);
    }
    void addDoctorsToVisitsFilter(ArrayList<Doctor> docArrayListToFilterGlobal,Doctor doctor,boolean addOrRemove, OnAddDoctorsToVisitsFilterFinishedListener onAddDoctorsToVisitsFilterFinishedListener );

    interface OnAddDoctorsToFilterFinishedListener {
        void addDoctorsToFilter(ArrayList<Doctor> addedArrayListToFilter);
    }
    void showAddDoctorsToFilter(ArrayList<Doctor> addedDocListGlobal,ArrayList<Doctor> allFilterDocList, OnAddDoctorsToFilterFinishedListener onAddDoctorsToFilterFinishedListener);

    interface OnSetSelectedFilterDoctorsFinishedListener {
        void showSelectedFilterDoctors(ArrayList<Doctor> filterList);
    }
    void setSelectedFilterDoctors(ArrayList<Doctor> totalDocList,ArrayList<Doctor> selectedDocList, OnSetSelectedFilterDoctorsFinishedListener onSetSelectedFilterDoctorsFinishedListener );

    interface OnSearchDocForFilterFinishedListener {
        void docListForFilter(ArrayList<Doctor> docArrayList);
    }
    void searchDocForFilter(ArrayList<Doctor> docArrayList,String docName, OnSearchDocForFilterFinishedListener  onSearchDocForFilterFinishedListener);






    interface OnAddLocationToVisitsStartFilterFinishedListener {
        void locationToVisitsFilterStart(LocationEntitie locationEntitie,boolean addOrRemove);
    }
    void addLocationToVisitsFilterStart(LocationEntitie locationEntitie,boolean addOrRemove, OnAddLocationToVisitsStartFilterFinishedListener onAddLocationToVisitsStartFilterFinishedListener);

    interface OnAddLocationToVisitsFilterFinishedListener {
        void locationToVisitsFilter(ArrayList<LocationEntitie> locArrayListToFilter);
    }
    void addLocationToVisitsFilter(ArrayList<LocationEntitie> locArrayListToFilterGlobal,LocationEntitie locationEntitie,boolean addOrRemove, OnAddLocationToVisitsFilterFinishedListener onAddLocationToVisitsFilterFinishedListener );

    interface OnAddLocationToFilterFinishedListener {
        void addLocationToFilter(ArrayList<LocationEntitie> addedArrayListToFilter);
    }
    void showAddLocationToFilter(ArrayList<LocationEntitie> addedLocListGlobal,ArrayList<LocationEntitie> allFilterLocList, OnAddLocationToFilterFinishedListener onAddLocationToFilterFinishedListener);
    interface OnSetSelectedFilterLocationFinishedListener {
        void showSelectedFilterLocation(ArrayList<LocationEntitie> filterList);
    }
    void setSelectedFilterLocation(ArrayList<LocationEntitie> totalLocList,ArrayList<LocationEntitie> selectedLocList,  OnSetSelectedFilterLocationFinishedListener onSetSelectedFilterLocationFinishedListener );
    interface OnSearchLocForFilterFinishedListener {
        void locListForFilter(ArrayList<LocationEntitie> locArrayList);
    }
    void searchLocForFilter(ArrayList<LocationEntitie> locArrayList,String locName, OnSearchLocForFilterFinishedListener  onSearchLocForFilterFinishedListener);



    interface OnAddProductsToVisitsStartFilterFinishedListener {
        void productsToVisitsFilterStart(Products products,boolean addOrRemove);
    }
    void addProductsToVisitsFilterStart(Products products,boolean addOrRemove, OnAddProductsToVisitsStartFilterFinishedListener onAddProductsToVisitsStartFilterFinishedListener);
    interface OnAddProductsToVisitsFilterFinishedListener {
        void productsToVisitsFilter(ArrayList<Products> productsArrayListToFilter);
    }
    void addProductsToVisitsFilter(ArrayList<Products> ProductsArrayListToFilterGlobal,Products products,boolean addOrRemove, OnAddProductsToVisitsFilterFinishedListener onAddProductsToVisitsFilterFinishedListener );

    interface OnAddProductsToFilterFinishedListener {
        void addProductsToFilter(ArrayList<Products> addedArrayListToFilter);
    }
    void showAddProductsToFilter(ArrayList<Products> addedLocListGlobal,ArrayList<Products> allFilterProductsList, OnAddProductsToFilterFinishedListener onAddProductsToFilterFinishedListener );
    interface OnSetSelectedFilterProductsFinishedListener {
        void showSelectedFilterProducts(ArrayList<Products> filterList);
    }
    void setSelectedFilterProducts(ArrayList<Products> totalProList,ArrayList<Products> selectedProList,OnSetSelectedFilterProductsFinishedListener onSetSelectedFilterProductsFinishedListener  );
    interface OnSearchProductsForFilterFinishedListener {
        void productsListForFilter(ArrayList<Products> proArrayList);
    }
    void searchProductsForFilter(ArrayList<Products> proArrayList,String productsName,   OnSearchProductsForFilterFinishedListener onSearchProductsForFilterFinishedListener);




    interface OnAddRepToVisitsStartFilterFinishedListener {
        void repToVisitsFilterStart(Rep rep,boolean addOrRemove);
    }
    void addRepToVisitsFilterStart(Rep rep,boolean addOrRemove, OnAddRepToVisitsStartFilterFinishedListener onAddRepToVisitsStartFilterFinishedListener);

    interface OnAddRepToVisitsFilterFinishedListener {
        void repToVisitsFilter(ArrayList<Rep> repArrayListToFilter);
    }
    void addRepToVisitsFilter(ArrayList<Rep> repArrayListToFilterGlobal,Rep rep,boolean addOrRemove, OnAddRepToVisitsFilterFinishedListener onAddRepToVisitsFilterFinishedListener );

    interface OnAddRepToFilterFinishedListener {
        void addRepToFilter(ArrayList<Rep> addedArrayListToFilter);
    }
    void showAddRepToFilter(ArrayList<Rep> addedLocListGlobal,ArrayList<Rep> allFilterRepList, OnAddRepToFilterFinishedListener onAddRepToFilterFinishedListener );


    interface OnSetSelectedFilterRepFinishedListener {
        void showSelectedFilterRep(ArrayList<Rep> filterList);
    }
    void setSelectedFilterRep(ArrayList<Rep> totalRepList,ArrayList<Rep> selectedRepList,OnSetSelectedFilterRepFinishedListener onSetSelectedFilterRepFinishedListener  );


    interface OnSearchRepForFilterFinishedListener {
        void repListForFilter(ArrayList<Rep> repArrayList);
    }
    void searchRepForFilter(ArrayList<Rep> repArrayList,String RepName,   OnSearchRepForFilterFinishedListener onSearchRepForFilterFinishedListener);


    interface OnVisitsFilterFinishedListener {
        void visitsFilterListEmpty(String msg,ArrayList<Visit> visitArrayList);
        void visitsFilterList(ArrayList<Visit> visitArrayList);
    }
    void visitsFilter(ArrayList<Visit> visitArrayList,String startDate,String endDate,ArrayList<Doctor> docList,ArrayList<LocationEntitie> locList,
                      ArrayList<Products> proList,ArrayList<Rep> repList,OnVisitsFilterFinishedListener onVisitsFilterFinishedListener);





}
