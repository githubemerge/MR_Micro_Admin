package emerge.project.mrsolution_admin_micro.ui.activity.locations;


import android.content.Context;

import emerge.project.mrsolution_admin_micro.utils.entittes.District;
import emerge.project.mrsolution_admin_micro.utils.entittes.LocationEntitie;
import emerge.project.mrsolution_admin_micro.utils.entittes.Navigation;
import emerge.project.mrsolution_admin_micro.utils.entittes.Rep;

import java.util.ArrayList;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface LocationInteractor {

    interface OnsetNavigationFinishedListener {
        void navigationItems(ArrayList<Navigation> navigationArrayList);
    }
    void setNavigation(OnsetNavigationFinishedListener onsetNavigationFinishedListener);




    interface OnGetPendingLocationFinishedListener {
        void pendingLocationList(ArrayList<LocationEntitie> locationEntitieArrayList);
        void noPendingLocation();
        void pendingLocationFail(String failMsg);
        void pendingLocationNetworkFail();

        void pendingLocationNameList( ArrayList<String> pendingLocationNameForFilter);
        void pendingLocationMRList( ArrayList<Rep>pendingLocationMRForFilter);
        void pendingLocationMRNameList( ArrayList<String>pendingLocationMRNameForFilter);


    }
    void getPendingLocation(Context context, OnGetPendingLocationFinishedListener onGetPendingLocationFinishedListener);


    interface OnGetApprovedLocationFinishedListener {
        void approvedLocationList(ArrayList<LocationEntitie> locationEntitieArrayList);
        void noApprovedLocation();
        void approvedLocationFail(String failMsg);
        void approvedLocationNetworkFail();




        void approvedLocationNameList( ArrayList<String> approvedLocationNameForFilter);
        void approvedLocationMRList( ArrayList<Rep> approvedLocationMRForFilter);
        void approvedLocationMRNameList( ArrayList<String> approvedLocationMRNameForFilter);


    }
    void getApprovedLocation(Context context, OnGetApprovedLocationFinishedListener onGetApprovedLocationFinishedListener);


    interface OnSelectedLocationPendingFinishedListener {
        void selectedLocationPending(LocationEntitie LocationEntitie);
    }
    void getSelectedLocationPending(LocationEntitie LocationEntitie, OnSelectedLocationPendingFinishedListener onSelectedLocationPendingFinishedListener);

    interface OnSelectedLocationApprovedFinishedListener {
        void selectedLocationApproved(LocationEntitie LocationEntitie);
    }
    void getSelectedLocationApproved(LocationEntitie LocationEntitie, OnSelectedLocationApprovedFinishedListener onSelectedLocationApprovedFinishedListener);


    interface OnDistrictListFinishedListener {
        void districtList(ArrayList<District> districtArrayList);
        void districtListEmpty();
        void districtListFail(String failMsg,int districtid,String district);
        void districtListNetworkFail();
    }
    void getDistrictList(Context context,int districtid,String district ,OnDistrictListFinishedListener onDistrictListFinishedListener);


    interface OnPostApproveRejectStatusFinishedListener {
        void  approveRejectStatusStart();
        void  approveRejectDetailsError(String msg);
        void  approveRejectStatusSuccess();
        void  approveRejectStatusFail(String failMsg, LocationEntitie LocationEntitie, boolean status);
        void  approveRejectStatusNetworkFail();
    }
    void postApproveRejectStatus(Context context, LocationEntitie LocationEntitie, boolean status, OnPostApproveRejectStatusFinishedListener onPostApproveRejectStatusFinishedListener);





    interface OnSearchLocPendingForFilterFinishedListener {
        void locPendingListForFilter(ArrayList<LocationEntitie> locationArrayList);
    }
    void searchLocPendingForFilter(ArrayList<LocationEntitie> locationArrayList,String locationName, OnSearchLocPendingForFilterFinishedListener  onSearchLocPendingForFilterFinishedListener);




    interface OnAddRepToLocationFilterStartFinishedListener {
        void repToLocationFilterStart(Rep rep,boolean addOrRemove);
    }
    void addRepToLocationFilterStart(Rep rep,boolean addOrRemove, OnAddRepToLocationFilterStartFinishedListener onAddRepToLocationFilterStartFinishedListener  );
    interface OnAddRepToLocationFilterFinishedListener {
        void repToLocationFilter(ArrayList<Rep> repArrayListToFilter);
    }
    void addRepToLocationFilter(ArrayList<Rep> repArrayListToFilterGlobal,Rep rep,boolean addOrRemove, OnAddRepToLocationFilterFinishedListener onAddRepToLocationFilterFinishedListener  );
    interface OnAddRepToFilterFinishedListener {
        void addRepToFilter(ArrayList<Rep> addedArrayListToFilter);
    }
    void showAddRepFilter(ArrayList<Rep> addedRepListGlobal,ArrayList<Rep> allFilterRepList,OnAddRepToFilterFinishedListener onAddRepToFilterFinishedListener  );
    interface OnSetSelectedFilterLocationFinishedListener {
        void showSelectedFilterLocation(ArrayList<Rep> filterList);
    }
    void setSelectedFilterRep(ArrayList<Rep> totalRepList,ArrayList<Rep> selectedLocList,  OnSetSelectedFilterLocationFinishedListener onSetSelectedFilterLocationFinishedListener );



    interface OnSearchRepForFilterFinishedListener {
        void repListForFilter(ArrayList<Rep> repArrayList);
    }
    void searchRepForFilter(ArrayList<Rep> repArrayList,String repName, OnSearchRepForFilterFinishedListener  onSearchRepForFilterFinishedListener);


    interface OnLocationFilterFinishedListener {
        void locationFilterListEmpty(String msg,ArrayList<LocationEntitie> locationArrayList);
        void locationFilterList(ArrayList<LocationEntitie> locationArrayList);
    }
    void locationFilter(ArrayList<LocationEntitie> locationArrayList,ArrayList<Rep> repList,OnLocationFilterFinishedListener  onLocationFilterFinishedListener);




}
