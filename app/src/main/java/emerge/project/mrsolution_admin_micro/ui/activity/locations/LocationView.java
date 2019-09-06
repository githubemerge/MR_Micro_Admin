package emerge.project.mrsolution_admin_micro.ui.activity.locations;


import java.util.ArrayList;

import emerge.project.mrsolution_admin_micro.utils.entittes.District;
import emerge.project.mrsolution_admin_micro.utils.entittes.LocationEntitie;
import emerge.project.mrsolution_admin_micro.utils.entittes.Navigation;
import emerge.project.mrsolution_admin_micro.utils.entittes.Rep;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface LocationView {


        void navigationItems(ArrayList<Navigation> navigationArrayList);


        void pendingLocationList(ArrayList<LocationEntitie> locationEntitieArrayList);
        void noPendingLocation();
        void pendingLocationFail(String failMsg);
        void pendingLocationNetworkFail();

        void pendingLocationNameList( ArrayList<String> pendingLocationNameForFilter);
        void pendingLocationMRList( ArrayList<Rep>pendingLocationMRForFilter);
        void pendingLocationMRNameList( ArrayList<String>pendingLocationMRNameForFilter);





        void approvedLocationList(ArrayList<LocationEntitie> locationEntitieArrayList);
        void noApprovedLocation();
        void approvedLocationFail(String failMsg);
        void approvedLocationNetworkFail();


        void approvedLocationNameList( ArrayList<String> approvedLocationNameForFilter);
        void approvedLocationMRList( ArrayList<Rep>approvedLocationMRForFilter);
        void approvedLocationMRNameList( ArrayList<String> approvedLocationMRNameForFilter);






        void selectedLocationPending(LocationEntitie LocationEntitie);
        void selectedLocationApproved(LocationEntitie LocationEntitie);


        void districtList(ArrayList<District> districtArrayList);
        void districtListEmpty();
        void districtListFail(String failMsg,int districtid,String district);
        void districtListNetworkFail();



        void  approveRejectStatusStart();
        void  approveRejectDetailsError(String msg);
        void  approveRejectStatusSuccess();
        void  approveRejectStatusFail(String failMsg, LocationEntitie LocationEntitie, boolean status);
        void  approveRejectStatusNetworkFail();



        void locPendingListForFilter(ArrayList<LocationEntitie> locationArrayList);



        void repToLocationFilterStart(Rep rep,boolean addOrRemove);
        void repToLocationFilter(ArrayList<Rep> repArrayListToFilter);
        void addRepToFilter(ArrayList<Rep> addedArrayListToFilter);
        void showSelectedFilterLocation(ArrayList<Rep> filterList);


        void repListForFilter(ArrayList<Rep> repArrayList);



        void locationFilterListEmpty(String msg,ArrayList<LocationEntitie> locationArrayList);
        void locationFilterList(ArrayList<LocationEntitie> locationArrayList);

}
