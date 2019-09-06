package emerge.project.mrsolution_admin_micro.ui.activity.locations;


import android.content.Context;

import java.util.ArrayList;

import emerge.project.mrsolution_admin_micro.utils.entittes.LocationEntitie;
import emerge.project.mrsolution_admin_micro.utils.entittes.Rep;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface LocationPresenter {


    void setNavigation();

    void getPendingLocation(Context context);
    void getApprovedLocation(Context context);
    void getSelectedLocationPending(LocationEntitie LocationEntitie);
    void getSelectedLocationApproved(LocationEntitie LocationEntitie);
    void getDistrictList(Context context,int districtid,String district);
    void postApproveRejectStatus(Context context, LocationEntitie LocationEntitie, boolean status);



    void searchLocPendingForFilter(ArrayList<LocationEntitie> locationArrayList,String locationName);



    void addRepToLocationFilterStart(Rep rep, boolean addOrRemove );
    void addRepToLocationFilter(ArrayList<Rep> repArrayListToFilterGlobal,Rep rep,boolean addOrRemove);
    void showAddRepFilter(ArrayList<Rep> addedRepListGlobal,ArrayList<Rep> allFilterRepList);
    void setSelectedFilterRep(ArrayList<Rep> totalRepList,ArrayList<Rep> selectedLocList);


    void searchRepForFilter(ArrayList<Rep> repArrayList,String repName);


    void locationFilter(ArrayList<LocationEntitie> locationArrayList,ArrayList<Rep> repList);

}
