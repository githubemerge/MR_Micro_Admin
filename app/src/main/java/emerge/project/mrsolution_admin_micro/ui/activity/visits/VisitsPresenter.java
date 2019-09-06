package emerge.project.mrsolution_admin_micro.ui.activity.visits;


import android.content.Context;

import java.util.ArrayList;

import emerge.project.mrsolution_admin_micro.utils.entittes.Doctor;
import emerge.project.mrsolution_admin_micro.utils.entittes.LocationEntitie;
import emerge.project.mrsolution_admin_micro.utils.entittes.Products;
import emerge.project.mrsolution_admin_micro.utils.entittes.Rep;
import emerge.project.mrsolution_admin_micro.utils.entittes.Visit;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface VisitsPresenter {
    void getVisits(Context context,int mrID,int locationID,int doctorID,String date);

    void setNavigation( );


    void showVisitDetails(Visit visit);



    void addDoctorsToVisitsFilterStart(Doctor doctor, boolean addOrRemove);
    void addDoctorsToVisitsFilter(ArrayList<Doctor> docArrayListToFilterGlobal, Doctor doctor, boolean addOrRemove );
    void showAddDoctorsToFilter(ArrayList<Doctor> addedDocListGlobal,ArrayList<Doctor> allFilterDocList);
    void setSelectedFilterDoctors(ArrayList<Doctor> totalDocList,ArrayList<Doctor> selectedDocList );
    void searchDocForFilter(ArrayList<Doctor> docArrayList,String docName);


    void addLocationToVisitsFilterStart(LocationEntitie locationEntitie, boolean addOrRemove);
    void addLocationToVisitsFilter(ArrayList<LocationEntitie> locArrayListToFilterGlobal,LocationEntitie locationEntitie,boolean addOrRemove );
    void showAddLocationToFilter(ArrayList<LocationEntitie> addedLocListGlobal,ArrayList<LocationEntitie> allFilterLocList);
    void setSelectedFilterLocation(ArrayList<LocationEntitie> totalLocList,ArrayList<LocationEntitie> selectedLocList );
    void searchLocForFilter(ArrayList<LocationEntitie> locArrayList,String locName);



    void addProductsToVisitsFilterStart(Products products, boolean addOrRemove);
    void addProductsToVisitsFilter(ArrayList<Products> ProductsArrayListToFilterGlobal,Products products,boolean addOrRemove );
    void showAddProductsToFilter(ArrayList<Products> addedLocListGlobal,ArrayList<Products> allFilterProductsList );
    void setSelectedFilterProducts(ArrayList<Products> totalProList,ArrayList<Products> selectedProList  );
    void searchProductsForFilter(ArrayList<Products> proArrayList,String productsName);




    void addRepToVisitsFilterStart(Rep rep, boolean addOrRemove);
    void addRepToVisitsFilter(ArrayList<Rep> repArrayListToFilterGlobal,Rep rep,boolean addOrRemove );
    void showAddRepToFilter(ArrayList<Rep> addedLocListGlobal,ArrayList<Rep> allFilterRepList);
    void setSelectedFilterRep(ArrayList<Rep> totalRepList,ArrayList<Rep> selectedRepList);
    void searchRepForFilter(ArrayList<Rep> repArrayList,String RepName);



    void visitsFilter(ArrayList<Visit> visitArrayList,String startDate,String endDate,ArrayList<Doctor> docList,ArrayList<LocationEntitie> locList,
                      ArrayList<Products> proList,ArrayList<Rep> repList );





}
