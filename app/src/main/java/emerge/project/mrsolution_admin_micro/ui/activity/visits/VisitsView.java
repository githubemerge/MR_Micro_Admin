package emerge.project.mrsolution_admin_micro.ui.activity.visits;


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

public interface VisitsView {
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




    void navigationItems(ArrayList<Navigation> navigationArrayList);

    void visitDetails(Visit visit);




    void doctorsToVisitsFilterStart(Doctor doctor,boolean addOrRemove);
    void doctorsToVisitsFilter(ArrayList<Doctor> docArrayListToFilter);
    void addDoctorsToFilter(ArrayList<Doctor> addedArrayListToFilter);
    void showSelectedFilterDoctors(ArrayList<Doctor> filterList);
    void docListForFilter(ArrayList<Doctor> docArrayList);


    void locationToVisitsFilterStart(LocationEntitie locationEntitie,boolean addOrRemove);
    void locationToVisitsFilter(ArrayList<LocationEntitie> locArrayListToFilter);
    void addLocationToFilter(ArrayList<LocationEntitie> addedArrayListToFilter);
    void showSelectedFilterLocation(ArrayList<LocationEntitie> filterList);
    void locListForFilter(ArrayList<LocationEntitie> locArrayList);



    void productsToVisitsFilterStart(Products products,boolean addOrRemove);
    void productsToVisitsFilter(ArrayList<Products> productsArrayListToFilter);
    void addProductsToFilter(ArrayList<Products> addedArrayListToFilter);
    void showSelectedFilterProducts(ArrayList<Products> filterList);
    void productsListForFilter(ArrayList<Products> proArrayList);




    void repToVisitsFilterStart(Rep rep,boolean addOrRemove);
    void repToVisitsFilter(ArrayList<Rep> repArrayListToFilter);
    void addRepToFilter(ArrayList<Rep> addedArrayListToFilter);
    void showSelectedFilterRep(ArrayList<Rep> filterList);
    void repListForFilter(ArrayList<Rep> repArrayList);




    void visitsFilterListEmpty(String msg,ArrayList<Visit> visitArrayList);
    void visitsFilterList(ArrayList<Visit> visitArrayList);




}
