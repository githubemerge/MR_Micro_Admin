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

public class VisitsPresenterImpli implements VisitsPresenter,
        VisitsInteractor.OnGetVisitsFinishedListener,
        VisitsInteractor.OnsetNavigationFinishedListener,
        VisitsInteractor.OnShowVisitDetailsListener,
        VisitsInteractor.OnAddDoctorsToVisitsStartFilterFinishedListener,
        VisitsInteractor.OnAddDoctorsToVisitsFilterFinishedListener,
        VisitsInteractor.OnAddDoctorsToFilterFinishedListener,
        VisitsInteractor.OnSetSelectedFilterDoctorsFinishedListener,
        VisitsInteractor.OnSearchDocForFilterFinishedListener,
        VisitsInteractor.OnAddLocationToVisitsStartFilterFinishedListener,
        VisitsInteractor.OnAddLocationToVisitsFilterFinishedListener,
        VisitsInteractor.OnAddLocationToFilterFinishedListener,
        VisitsInteractor.OnSetSelectedFilterLocationFinishedListener,
        VisitsInteractor.OnSearchLocForFilterFinishedListener,
        VisitsInteractor.OnAddProductsToVisitsStartFilterFinishedListener,
        VisitsInteractor.OnAddProductsToVisitsFilterFinishedListener,
        VisitsInteractor.OnAddProductsToFilterFinishedListener,
        VisitsInteractor.OnSetSelectedFilterProductsFinishedListener,
        VisitsInteractor.OnSearchProductsForFilterFinishedListener,
        VisitsInteractor.OnAddRepToFilterFinishedListener,
        VisitsInteractor.OnSetSelectedFilterRepFinishedListener,
        VisitsInteractor.OnSearchRepForFilterFinishedListener,
        VisitsInteractor.OnAddRepToVisitsStartFilterFinishedListener,
        VisitsInteractor.OnAddRepToVisitsFilterFinishedListener,
VisitsInteractor.OnVisitsFilterFinishedListener{


    private VisitsView visitsView;
    VisitsInteractor visitsInteractor;


    public VisitsPresenterImpli(VisitsView visitsview) {
        this.visitsView = visitsview;
        this.visitsInteractor = new VisitsInteractorImpil();

    }


    @Override
    public void visitsList(ArrayList<Visit> visitArrayList) {
        visitsView.visitsList(visitArrayList);
    }

    @Override
    public void visitsListNoItems() {
        visitsView.visitsListNoItems();
    }

    @Override
    public void visitsListFail(String failMsg, int mrID, int locationID, int doctorID, String date) {
        visitsView.visitsListFail(failMsg, mrID, locationID, doctorID, date);
    }

    @Override
    public void visitsListNetworkFail() {
        visitsView.visitsListNetworkFail();
    }

    @Override
    public void visitsDoctorsList(ArrayList<Doctor> docListForFilter) {
        visitsView.visitsDoctorsList(docListForFilter);
    }

    @Override
    public void visitsDoctorsNameList(ArrayList<String> docNameListForFilter) {
        visitsView.visitsDoctorsNameList(docNameListForFilter);
    }

    @Override
    public void visitsLocationList(ArrayList<LocationEntitie> locListForFilter) {
        visitsView.visitsLocationList(locListForFilter);
    }

    @Override
    public void visitsLocationNameList(ArrayList<String> locNameListForFilter) {
        visitsView.visitsLocationNameList(locNameListForFilter);
    }

    @Override
    public void visitsProductsList(ArrayList<Products> productsListForFilter) {
        visitsView.visitsProductsList(productsListForFilter);
    }

    @Override
    public void visitsProductsNameList(ArrayList<String> productsNameListForFilter) {
        visitsView.visitsProductsNameList(productsNameListForFilter);
    }

    @Override
    public void visitsRepList(ArrayList<Rep> repListForFilter) {
        visitsView.visitsRepList(repListForFilter);
    }

    @Override
    public void visitsRepNameList(ArrayList<String> repNameListForFilter) {
        visitsView.visitsRepNameList(repNameListForFilter);
    }

    @Override
    public void getVisits(Context context, int mrID, int locationID, int doctorID, String date) {
        visitsInteractor.getVisits(context, mrID, locationID, doctorID, date, this);
    }


    @Override
    public void setNavigation() {
        visitsInteractor.setNavigation(this);
    }


    @Override
    public void navigationItems(ArrayList<Navigation> navigationArrayList) {
        visitsView.navigationItems(navigationArrayList);
    }


    @Override
    public void showVisitDetails(Visit visit) {
        visitsInteractor.showVisitDetails(visit, this);
    }


    @Override
    public void visitDetails(Visit visit) {
        visitsView.visitDetails(visit);
    }


    @Override
    public void addDoctorsToVisitsFilterStart(Doctor doctor, boolean addOrRemove) {
        visitsInteractor.addDoctorsToVisitsFilterStart(doctor, addOrRemove, this);
    }

    @Override
    public void doctorsToVisitsFilterStart(Doctor doctor, boolean addOrRemove) {
        visitsView.doctorsToVisitsFilterStart(doctor, addOrRemove);
    }


    @Override
    public void addDoctorsToVisitsFilter(ArrayList<Doctor> docArrayListToFilterGlobal, Doctor doctor, boolean addOrRemove) {
        visitsInteractor.addDoctorsToVisitsFilter(docArrayListToFilterGlobal, doctor, addOrRemove, this);
    }

    @Override
    public void doctorsToVisitsFilter(ArrayList<Doctor> docArrayListToFilter) {
        visitsView.doctorsToVisitsFilter(docArrayListToFilter);
    }


    @Override
    public void showAddDoctorsToFilter(ArrayList<Doctor> addedDocListGlobal, ArrayList<Doctor> allFilterDocList) {
        visitsInteractor.showAddDoctorsToFilter(addedDocListGlobal, allFilterDocList, this);
    }


    @Override
    public void addDoctorsToFilter(ArrayList<Doctor> addedArrayListToFilter) {
        visitsView.addDoctorsToFilter(addedArrayListToFilter);
    }


    @Override
    public void setSelectedFilterDoctors(ArrayList<Doctor> totalDocList, ArrayList<Doctor> selectedDocList) {
        visitsInteractor.setSelectedFilterDoctors(totalDocList, selectedDocList, this);
    }

    @Override
    public void showSelectedFilterDoctors(ArrayList<Doctor> filterList) {
        visitsView.showSelectedFilterDoctors(filterList);
    }


    @Override
    public void searchDocForFilter(ArrayList<Doctor> docArrayList, String docName) {
        visitsInteractor.searchDocForFilter(docArrayList, docName, this);
    }


    @Override
    public void docListForFilter(ArrayList<Doctor> docArrayList) {
        visitsView.docListForFilter(docArrayList);
    }


    @Override
    public void addLocationToVisitsFilterStart(LocationEntitie locationEntitie, boolean addOrRemove) {
        visitsInteractor.addLocationToVisitsFilterStart(locationEntitie, addOrRemove, this);
    }

    @Override
    public void locationToVisitsFilterStart(LocationEntitie locationEntitie, boolean addOrRemove) {
        visitsView.locationToVisitsFilterStart(locationEntitie, addOrRemove);
    }


    @Override
    public void addLocationToVisitsFilter(ArrayList<LocationEntitie> locArrayListToFilterGlobal, LocationEntitie locationEntitie, boolean addOrRemove) {
        visitsInteractor.addLocationToVisitsFilter(locArrayListToFilterGlobal, locationEntitie, addOrRemove, this);
    }

    @Override
    public void locationToVisitsFilter(ArrayList<LocationEntitie> locArrayListToFilter) {
        visitsView.locationToVisitsFilter(locArrayListToFilter);
    }


    @Override
    public void showAddLocationToFilter(ArrayList<LocationEntitie> addedLocListGlobal, ArrayList<LocationEntitie> allFilterLocList) {
        visitsInteractor.showAddLocationToFilter(addedLocListGlobal, allFilterLocList, this);
    }

    @Override
    public void addLocationToFilter(ArrayList<LocationEntitie> addedArrayListToFilter) {
        visitsView.addLocationToFilter(addedArrayListToFilter);
    }


    @Override
    public void setSelectedFilterLocation(ArrayList<LocationEntitie> totalLocList, ArrayList<LocationEntitie> selectedLocList) {
        visitsInteractor.setSelectedFilterLocation(totalLocList, selectedLocList, this);
    }

    @Override
    public void showSelectedFilterLocation(ArrayList<LocationEntitie> filterList) {
        visitsView.showSelectedFilterLocation(filterList);
    }


    @Override
    public void searchLocForFilter(ArrayList<LocationEntitie> locArrayList, String locName) {
        visitsInteractor.searchLocForFilter(locArrayList, locName, this);
    }

    @Override
    public void locListForFilter(ArrayList<LocationEntitie> locArrayList) {
        visitsView.locListForFilter(locArrayList);
    }


    @Override
    public void addProductsToVisitsFilterStart(Products products, boolean addOrRemove) {
        visitsInteractor.addProductsToVisitsFilterStart(products, addOrRemove, this);
    }

    @Override
    public void productsToVisitsFilterStart(Products products, boolean addOrRemove) {
        visitsView.productsToVisitsFilterStart(products, addOrRemove);
    }


    @Override
    public void addProductsToVisitsFilter(ArrayList<Products> ProductsArrayListToFilterGlobal, Products products, boolean addOrRemove) {
        visitsInteractor.addProductsToVisitsFilter(ProductsArrayListToFilterGlobal, products, addOrRemove, this);
    }

    @Override
    public void productsToVisitsFilter(ArrayList<Products> productsArrayListToFilter) {
        visitsView.productsToVisitsFilter(productsArrayListToFilter);
    }


    @Override
    public void showAddProductsToFilter(ArrayList<Products> addedLocListGlobal, ArrayList<Products> allFilterProductsList) {
        visitsInteractor.showAddProductsToFilter(addedLocListGlobal, allFilterProductsList, this);
    }

    @Override
    public void addProductsToFilter(ArrayList<Products> addedArrayListToFilter) {
        visitsView.addProductsToFilter(addedArrayListToFilter);
    }


    @Override
    public void setSelectedFilterProducts(ArrayList<Products> totalProList, ArrayList<Products> selectedProList) {
        visitsInteractor.setSelectedFilterProducts(totalProList, selectedProList, this);
    }

    @Override
    public void showSelectedFilterProducts(ArrayList<Products> filterList) {
        visitsView.showSelectedFilterProducts(filterList);
    }


    @Override
    public void searchProductsForFilter(ArrayList<Products> proArrayList, String productsName) {
        visitsInteractor.searchProductsForFilter(proArrayList, productsName, this);
    }

    @Override
    public void productsListForFilter(ArrayList<Products> proArrayList) {
        visitsView.productsListForFilter(proArrayList);
    }






    @Override
    public void addRepToVisitsFilterStart(Rep rep, boolean addOrRemove) {
        visitsInteractor.addRepToVisitsFilterStart(rep, addOrRemove, this);
    }
    @Override
    public void repToVisitsFilterStart(Rep rep, boolean addOrRemove) {
        visitsView.repToVisitsFilterStart(rep,addOrRemove);
    }



    @Override
    public void addRepToVisitsFilter(ArrayList<Rep> repArrayListToFilterGlobal, Rep rep, boolean addOrRemove) {
        visitsInteractor.addRepToVisitsFilter(repArrayListToFilterGlobal, rep,addOrRemove, this);
    }
    @Override
    public void repToVisitsFilter(ArrayList<Rep> repArrayListToFilter) {
        visitsView.repToVisitsFilter(repArrayListToFilter);
    }




    @Override
    public void showAddRepToFilter(ArrayList<Rep> addedLocListGlobal, ArrayList<Rep> allFilterRepList) {
        visitsInteractor.showAddRepToFilter(addedLocListGlobal, allFilterRepList, this);
    }
    @Override
    public void addRepToFilter(ArrayList<Rep> addedArrayListToFilter) {
        visitsView.addRepToFilter(addedArrayListToFilter);
    }



    @Override
    public void setSelectedFilterRep(ArrayList<Rep> totalRepList, ArrayList<Rep> selectedRepList) {
        visitsInteractor.setSelectedFilterRep(totalRepList, selectedRepList, this);
    }
    @Override
    public void showSelectedFilterRep(ArrayList<Rep> filterList) {
        visitsView.showSelectedFilterRep(filterList);
    }



    @Override
    public void searchRepForFilter(ArrayList<Rep> repArrayList, String RepName) {
        visitsInteractor.searchRepForFilter(repArrayList, RepName, this);
    }



    @Override
    public void repListForFilter(ArrayList<Rep> repArrayList) {
        visitsView.repListForFilter(repArrayList);
    }




    @Override
    public void visitsFilter(ArrayList<Visit> visitArrayList, String startDate, String endDate, ArrayList<Doctor> docList, ArrayList<LocationEntitie> locList, ArrayList<Products> proList, ArrayList<Rep> repList) {
        visitsInteractor.visitsFilter(visitArrayList, startDate,endDate,docList,locList,proList,repList,this);
    }

    @Override
    public void visitsFilterListEmpty(String msg, ArrayList<Visit> visitArrayList) {
        visitsView.visitsFilterListEmpty(msg,visitArrayList);
    }

    @Override
    public void visitsFilterList(ArrayList<Visit> visitArrayList) {
        visitsView.visitsFilterList(visitArrayList);
    }
}
