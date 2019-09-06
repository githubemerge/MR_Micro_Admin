package emerge.project.mrsolution_admin_micro.ui.activity.locations;


import android.content.Context;

import java.util.ArrayList;

import emerge.project.mrsolution_admin_micro.utils.entittes.District;
import emerge.project.mrsolution_admin_micro.utils.entittes.LocationEntitie;
import emerge.project.mrsolution_admin_micro.utils.entittes.Navigation;
import emerge.project.mrsolution_admin_micro.utils.entittes.Rep;

/**
 * Created by Himanshu on 4/4/2017.
 */

public class LocationPresenterImpli implements LocationPresenter,
        LocationInteractor.OnGetPendingLocationFinishedListener,
        LocationInteractor.OnGetApprovedLocationFinishedListener,
        LocationInteractor.OnSelectedLocationApprovedFinishedListener,
        LocationInteractor.OnSelectedLocationPendingFinishedListener,
        LocationInteractor.OnDistrictListFinishedListener,
        LocationInteractor.OnPostApproveRejectStatusFinishedListener,
        LocationInteractor.OnsetNavigationFinishedListener,
        LocationInteractor.OnSearchLocPendingForFilterFinishedListener,
        LocationInteractor.OnAddRepToLocationFilterStartFinishedListener,
        LocationInteractor.OnAddRepToLocationFilterFinishedListener,
        LocationInteractor.OnAddRepToFilterFinishedListener,
        LocationInteractor.OnSetSelectedFilterLocationFinishedListener,
LocationInteractor.OnSearchRepForFilterFinishedListener,
LocationInteractor.OnLocationFilterFinishedListener{


    private LocationView locationView;
    LocationInteractor locationInteractor;


    public LocationPresenterImpli(LocationView vocationview) {
        this.locationView = vocationview;
        this.locationInteractor = new LocationInteractorImpil();

    }


    @Override
    public void pendingLocationList(ArrayList<LocationEntitie> locationEntitieArrayList) {
        locationView.pendingLocationList(locationEntitieArrayList);
    }

    @Override
    public void noPendingLocation() {
        locationView.noPendingLocation();
    }

    @Override
    public void pendingLocationFail(String failMsg) {
        locationView.pendingLocationFail(failMsg);
    }

    @Override
    public void pendingLocationNetworkFail() {
        locationView.pendingLocationNetworkFail();
    }

    @Override
    public void pendingLocationNameList(ArrayList<String> pendingLocationNameForFilter) {
        locationView.pendingLocationNameList(pendingLocationNameForFilter);
    }

    @Override
    public void pendingLocationMRList(ArrayList<Rep> pendingLocationMRForFilter) {
        locationView.pendingLocationMRList(pendingLocationMRForFilter);
    }

    @Override
    public void pendingLocationMRNameList(ArrayList<String> pendingLocationMRNameForFilter) {
        locationView.pendingLocationMRNameList(pendingLocationMRNameForFilter);
    }


    @Override
    public void getPendingLocation(Context context) {
        locationInteractor.getPendingLocation(context, this);
    }


    @Override
    public void approvedLocationList(ArrayList<LocationEntitie> locationEntitieArrayList) {
        locationView.approvedLocationList(locationEntitieArrayList);
    }

    @Override
    public void noApprovedLocation() {
        locationView.noApprovedLocation();
    }

    @Override
    public void approvedLocationFail(String failMsg) {
        locationView.approvedLocationFail(failMsg);
    }

    @Override
    public void approvedLocationNetworkFail() {
        locationView.approvedLocationNetworkFail();
    }

    @Override
    public void approvedLocationNameList(ArrayList<String> approvedLocationNameForFilter) {
        locationView.approvedLocationNameList(approvedLocationNameForFilter);
    }

    @Override
    public void approvedLocationMRList(ArrayList<Rep> approvedLocationMRForFilter) {
        locationView.approvedLocationMRList(approvedLocationMRForFilter);
    }

    @Override
    public void approvedLocationMRNameList(ArrayList<String> approvedLocationMRNameForFilter) {
        locationView.approvedLocationMRNameList(approvedLocationMRNameForFilter);
    }


    @Override
    public void getApprovedLocation(Context context) {
        locationInteractor.getApprovedLocation(context, this);
    }


    @Override
    public void getSelectedLocationPending(LocationEntitie LocationEntitie) {
        locationInteractor.getSelectedLocationPending(LocationEntitie, this);
    }

    @Override
    public void selectedLocationPending(LocationEntitie LocationEntitie) {
        locationView.selectedLocationPending(LocationEntitie);
    }


    @Override
    public void getSelectedLocationApproved(LocationEntitie LocationEntitie) {
        locationInteractor.getSelectedLocationApproved(LocationEntitie, this);
    }


    @Override
    public void selectedLocationApproved(LocationEntitie LocationEntitie) {
        locationView.selectedLocationApproved(LocationEntitie);
    }


    @Override
    public void getDistrictList(Context context, int districtid, String district) {
        locationInteractor.getDistrictList(context, districtid, district, this);
    }

    @Override
    public void districtList(ArrayList<District> districtArrayList) {
        locationView.districtList(districtArrayList);
    }

    @Override
    public void districtListEmpty() {
        locationView.districtListEmpty();
    }

    @Override
    public void districtListFail(String failMsg, int districtid, String district) {
        locationView.districtListFail(failMsg, districtid, district);
    }

    @Override
    public void districtListNetworkFail() {
        locationView.districtListNetworkFail();
    }


    @Override
    public void postApproveRejectStatus(Context context, LocationEntitie LocationEntitie, boolean status) {
        locationInteractor.postApproveRejectStatus(context, LocationEntitie, status, this);
    }


    @Override
    public void approveRejectStatusStart() {
        locationView.approveRejectStatusStart();
    }

    @Override
    public void approveRejectDetailsError(String msg) {
        locationView.approveRejectDetailsError(msg);
    }

    @Override
    public void approveRejectStatusSuccess() {
        locationView.approveRejectStatusSuccess();
    }

    @Override
    public void approveRejectStatusFail(String failMsg, LocationEntitie LocationEntitie, boolean status) {
        locationView.approveRejectStatusFail(failMsg, LocationEntitie, status);
    }

    @Override
    public void approveRejectStatusNetworkFail() {
        locationView.approveRejectStatusNetworkFail();
    }


    @Override
    public void setNavigation() {
        locationInteractor.setNavigation(this);
    }


    @Override
    public void navigationItems(ArrayList<Navigation> navigationArrayList) {
        locationView.navigationItems(navigationArrayList);
    }


    @Override
    public void searchLocPendingForFilter(ArrayList<LocationEntitie> locationArrayList, String locationName) {
        locationInteractor.searchLocPendingForFilter(locationArrayList, locationName, this);
    }

    @Override
    public void locPendingListForFilter(ArrayList<LocationEntitie> locationArrayList) {
        locationView.locPendingListForFilter(locationArrayList);
    }







    @Override
    public void addRepToLocationFilterStart(Rep rep, boolean addOrRemove) {
        locationInteractor.addRepToLocationFilterStart(rep, addOrRemove, this);
    }
    @Override
    public void repToLocationFilterStart(Rep rep, boolean addOrRemove) {
        locationView.repToLocationFilterStart(rep,addOrRemove);
    }


    @Override
    public void addRepToLocationFilter(ArrayList<Rep> repArrayListToFilterGlobal, Rep rep, boolean addOrRemove) {
        locationInteractor.addRepToLocationFilter(repArrayListToFilterGlobal,rep ,addOrRemove, this);
    }

    @Override
    public void repToLocationFilter(ArrayList<Rep> repArrayListToFilter) {
        locationView.repToLocationFilter(repArrayListToFilter);
    }



    @Override
    public void showAddRepFilter(ArrayList<Rep> addedRepListGlobal, ArrayList<Rep> allFilterRepList) {
        locationInteractor.showAddRepFilter(addedRepListGlobal,allFilterRepList, this);
    }
    @Override
    public void addRepToFilter(ArrayList<Rep> addedArrayListToFilter) {
        locationView.addRepToFilter(addedArrayListToFilter);
    }



    @Override
    public void setSelectedFilterRep(ArrayList<Rep> totalRepList, ArrayList<Rep> selectedLocList) {
        locationInteractor.setSelectedFilterRep(totalRepList,selectedLocList, this);
    }

    @Override
    public void showSelectedFilterLocation(ArrayList<Rep> filterList) {
        locationView.showSelectedFilterLocation(filterList);
    }



    @Override
    public void searchRepForFilter(ArrayList<Rep> repArrayList, String repName) {
        locationInteractor.searchRepForFilter(repArrayList,repName, this);
    }

    @Override
    public void repListForFilter(ArrayList<Rep> repArrayList) {
        locationView.repListForFilter(repArrayList);
    }




    @Override
    public void locationFilter(ArrayList<LocationEntitie> locationArrayList, ArrayList<Rep> repList) {
        locationInteractor.locationFilter(locationArrayList,repList, this);
    }

    @Override
    public void locationFilterListEmpty(String msg, ArrayList<LocationEntitie> locationArrayList) {
        locationView.locationFilterListEmpty(msg,locationArrayList);
    }

    @Override
    public void locationFilterList(ArrayList<LocationEntitie> locationArrayList) {
        locationView.locationFilterList(locationArrayList);
    }
}
