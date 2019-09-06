package emerge.project.mrsolution_admin_micro.ui.activity.locations;


import android.content.Context;

import com.google.gson.JsonObject;
import com.pddstudio.preferences.encrypted.EncryptedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import emerge.project.mrsolution_admin_micro.BuildConfig;
import emerge.project.mrsolution_admin_micro.services.api.ApiClient;
import emerge.project.mrsolution_admin_micro.services.api.ApiInterface;
import emerge.project.mrsolution_admin_micro.services.network.NetworkAvailability;
import emerge.project.mrsolution_admin_micro.utils.entittes.District;
import emerge.project.mrsolution_admin_micro.utils.entittes.LocationEntitie;
import emerge.project.mrsolution_admin_micro.utils.entittes.Navigation;
import emerge.project.mrsolution_admin_micro.utils.entittes.Rep;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Himanshu on 4/5/2017.
 */

public class LocationInteractorImpil implements LocationInteractor {


    public static final String tokenID = BuildConfig.API_TOKEN_ID;

    EncryptedPreferences encryptedPreferences;
    private static final String USER_ID = "userID";
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);



    ArrayList<LocationEntitie> pendingLocationEntitie;
    ArrayList<LocationEntitie> approvedLocationEntitie;
    ArrayList<District> districtList;
    JsonObject updateLocation;

    @Override
    public void setNavigation(OnsetNavigationFinishedListener onsetNavigationFinishedListener) {
        ArrayList<Navigation> navigationItems = new ArrayList<Navigation>();

        onsetNavigationFinishedListener.navigationItems(navigationItems);
    }

    @Override
    public void getPendingLocation(Context context, final OnGetPendingLocationFinishedListener onGetPendingLocationFinishedListener) {

            if (!NetworkAvailability.isNetworkAvailable(context)) {
                onGetPendingLocationFinishedListener.pendingLocationNetworkFail();
            } else {
                try {
                encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
                int userId = encryptedPreferences.getInt(USER_ID,0);

                final ArrayList<LocationEntitie> locationEntitiePendingArrayList = new ArrayList<LocationEntitie>();

                    final ArrayList<String> pendingLocationNameForFilter = new ArrayList<String>();

                    final ArrayList<Rep>pendingLocationMRForFilter = new ArrayList<Rep>();
                    final ArrayList<String>pendingLocationMRNameForFilter = new ArrayList<String>();

                apiService.getLocationsUnapprovedByAdmin(tokenID,userId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer< ArrayList<LocationEntitie>>() {
                                @Override
                                public void onSubscribe(Disposable d) { }
                                @Override
                                public void onNext( ArrayList<LocationEntitie> loginuser) {
                                    pendingLocationEntitie = loginuser;
                                }
                                @Override
                                public void onError(Throwable e) {
                                    onGetPendingLocationFinishedListener.pendingLocationFail("Something went wrong, Please try again");
                                }
                                @Override
                                public void onComplete() {
                                    if(pendingLocationEntitie.isEmpty()){
                                        onGetPendingLocationFinishedListener.noPendingLocation();
                                    }else {
                                        for (LocationEntitie loc: pendingLocationEntitie) {
                                            locationEntitiePendingArrayList.add(new LocationEntitie(loc.getId(),loc.getName(),loc.getAddress(),loc.getArea(),loc.getTown(),loc.getDistrictID(),loc.getDistrict(),loc.getLatitude(),loc.getLongitude(),loc.getCreatedByID(),
                                                    loc.getCreatedByName(),loc.getLocationTypeID(),loc.getLocationType()));


                                        }
                                        onGetPendingLocationFinishedListener.pendingLocationList(locationEntitiePendingArrayList);


                                        for (LocationEntitie loc: pendingLocationEntitie) {

                                            pendingLocationNameForFilter.add(loc.getName());

                                            if (pendingLocationMRForFilter.isEmpty()) {
                                                pendingLocationMRForFilter.add(new Rep(loc.getCreatedByID(),loc.getCreatedByName(),false));
                                                pendingLocationMRNameForFilter.add(loc.getCreatedByName());

                                            } else {
                                                boolean status = false;
                                                for (Rep rep : pendingLocationMRForFilter) {
                                                    if (rep.getId() == loc.getCreatedByID()) {
                                                        status = true;
                                                        break;
                                                    } else {
                                                    }
                                                }
                                                if (!status) {
                                                    pendingLocationMRForFilter.add(new Rep(loc.getCreatedByID(),loc.getCreatedByName(),false));
                                                    pendingLocationMRNameForFilter.add(loc.getCreatedByName());
                                                } else {
                                                }
                                            }


                                        }


                                        onGetPendingLocationFinishedListener.pendingLocationNameList(pendingLocationNameForFilter);
                                        onGetPendingLocationFinishedListener.pendingLocationMRList(pendingLocationMRForFilter);
                                        onGetPendingLocationFinishedListener.pendingLocationMRNameList(pendingLocationMRNameForFilter);


                                    }

                                }
                            });
                }catch (Exception ex){
                    onGetPendingLocationFinishedListener.pendingLocationFail("Something went wrong, Please try again");
                }

            }


    }

    @Override
    public void getApprovedLocation(Context context, final OnGetApprovedLocationFinishedListener onGetApprovedLocationFinishedListener) {

        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onGetApprovedLocationFinishedListener.approvedLocationNetworkFail();
        } else {
             try {
                encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
                int userId = encryptedPreferences.getInt(USER_ID,0);

                final ArrayList<LocationEntitie> locationEntitieApprovedArrayList = new ArrayList<LocationEntitie>();


                 final ArrayList<String> approvedLocationNameForFilter = new ArrayList<String>();

                 final ArrayList<Rep>approvedLocationMRForFilter = new ArrayList<Rep>();
                 final ArrayList<String>approvedLocationMRNameForFilter = new ArrayList<String>();





                apiService.getLocationsApprovedByAdmin("5050",userId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer< ArrayList<LocationEntitie>>() {
                            @Override
                            public void onSubscribe(Disposable d) { }
                            @Override
                            public void onNext( ArrayList<LocationEntitie> loginuser) {
                                approvedLocationEntitie = loginuser;
                            }
                            @Override
                            public void onError(Throwable e) {
                                onGetApprovedLocationFinishedListener.approvedLocationFail("Something went wrong, Please try again");
                            }
                            @Override
                            public void onComplete() {
                                if(approvedLocationEntitie.isEmpty()){
                                    onGetApprovedLocationFinishedListener.noApprovedLocation();
                                }else {
                                    for (LocationEntitie loc: approvedLocationEntitie) {
                                        locationEntitieApprovedArrayList.add(new LocationEntitie(loc.getId(),loc.getName(),loc.getAddress(), loc.getArea(),loc.getTown(),loc.getDistrictID(),loc.getDistrict(),loc.getLatitude(),loc.getLongitude(),loc.getCreatedByID(),
                                                loc.getCreatedByName(),loc.getLocationTypeID(),loc.getLocationType()));
                                    }

                                    onGetApprovedLocationFinishedListener.approvedLocationList(locationEntitieApprovedArrayList);


                                    for (LocationEntitie loc: approvedLocationEntitie) {

                                        approvedLocationNameForFilter.add(loc.getName());

                                        if (approvedLocationMRForFilter.isEmpty()) {
                                            approvedLocationMRForFilter.add(new Rep(loc.getCreatedByID(),loc.getCreatedByName(),false));
                                            approvedLocationMRNameForFilter.add(loc.getCreatedByName());

                                        } else {
                                            boolean status = false;
                                            for (Rep rep : approvedLocationMRForFilter) {
                                                if (rep.getId() == loc.getCreatedByID()) {
                                                    status = true;
                                                    break;
                                                } else {
                                                }
                                            }
                                            if (!status) {
                                                approvedLocationMRForFilter.add(new Rep(loc.getCreatedByID(),loc.getCreatedByName(),false));
                                                approvedLocationMRNameForFilter.add(loc.getCreatedByName());
                                            } else {
                                            }
                                        }

                                    }


                                    onGetApprovedLocationFinishedListener.approvedLocationNameList(approvedLocationNameForFilter);
                                    onGetApprovedLocationFinishedListener.approvedLocationMRList(approvedLocationMRForFilter);
                                    onGetApprovedLocationFinishedListener.approvedLocationMRNameList(approvedLocationMRNameForFilter);
                                }

                            }
                        });
            }catch (Exception ex){
                onGetApprovedLocationFinishedListener.approvedLocationFail("Something went wrong, Please try again");
            }



        }
    }



    @Override
    public void getSelectedLocationPending(LocationEntitie LocationEntitie, OnSelectedLocationPendingFinishedListener onSelectedLocationPendingFinishedListener) {
        onSelectedLocationPendingFinishedListener.selectedLocationPending(LocationEntitie);
    }

    @Override
    public void getSelectedLocationApproved(LocationEntitie LocationEntitie, OnSelectedLocationApprovedFinishedListener onSelectedLocationApprovedFinishedListener) {
        onSelectedLocationApprovedFinishedListener.selectedLocationApproved(LocationEntitie);
    }

    @Override
    public void getDistrictList(Context context, final int districtid, final String district, final OnDistrictListFinishedListener onDistrictListFinishedListener) {


        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onDistrictListFinishedListener.districtListNetworkFail();
        } else {
            try {
                final ArrayList<District> districtArrayList = new ArrayList<District>();
                apiService.getAllDistricts(tokenID)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer< ArrayList<District>>() {
                            @Override
                            public void onSubscribe(Disposable d) { }
                            @Override
                            public void onNext( ArrayList<District> district) {
                                districtList = district;
                            }
                            @Override
                            public void onError(Throwable e) {
                                onDistrictListFinishedListener.districtListFail("Something went wrong, Please try again",districtid,district);
                            }
                            @Override
                            public void onComplete() {
                                if(districtList.isEmpty()){
                                    onDistrictListFinishedListener.districtListEmpty();
                                }else {
                                    districtArrayList.add(new District(districtid,district));
                                    for(District dis :districtList ){
                                        if(dis.getId()==districtid){ }else {
                                            districtArrayList.add(new District(dis.getId(),dis.getName()));
                                        }

                                    }
                                    onDistrictListFinishedListener.districtList(districtArrayList);
                                }
                            }
                        });
            }catch (Exception ex){
                onDistrictListFinishedListener.districtListFail("Something went wrong, Please try again",districtid,district);
            }

        }

    }

    @Override
    public void postApproveRejectStatus(Context context, final LocationEntitie LocationEntitie, final boolean status, final OnPostApproveRejectStatusFinishedListener onPostApproveRejectStatusFinishedListener) {


        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onPostApproveRejectStatusFinishedListener.approveRejectStatusNetworkFail();
        } else if(LocationEntitie.getName().isEmpty() || LocationEntitie.getName().equals("") || LocationEntitie.getName() == null){
            onPostApproveRejectStatusFinishedListener.approveRejectDetailsError("Location name is empty");
        }else if(LocationEntitie.getAddress().isEmpty() || LocationEntitie.getAddress().equals("") || LocationEntitie.getAddress() == null){
            onPostApproveRejectStatusFinishedListener.approveRejectDetailsError("Location address is empty");
        }else if(LocationEntitie.getTown().isEmpty() || LocationEntitie.getTown().equals("") || LocationEntitie.getTown() == null){
            onPostApproveRejectStatusFinishedListener.approveRejectDetailsError("Location town is empty");
        }else if(LocationEntitie.getArea().isEmpty() || LocationEntitie.getArea().equals("") || LocationEntitie.getArea() == null){
            onPostApproveRejectStatusFinishedListener.approveRejectDetailsError("Location area is empty");
        }else if(LocationEntitie.getDistrict().isEmpty() || LocationEntitie.getDistrict().equals("") || LocationEntitie.getDistrict() == null){
            onPostApproveRejectStatusFinishedListener.approveRejectDetailsError("Location district is empty");
        }else {
            encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
            int userId = encryptedPreferences.getInt(USER_ID,0);

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("ID", LocationEntitie.getId());
            jsonObject.addProperty("name", LocationEntitie.getName() );
            jsonObject.addProperty("LocationTypeID", LocationEntitie.getLocationTypeID() );
            jsonObject.addProperty("Address", LocationEntitie.getAddress() );
            jsonObject.addProperty("Town", LocationEntitie.getTown() );
            jsonObject.addProperty("DistrictID", LocationEntitie.getDistrictID() );
            jsonObject.addProperty("Area", LocationEntitie.getArea() );
            jsonObject.addProperty("Latitude", LocationEntitie.getLatitude() );
            jsonObject.addProperty("Longitude", LocationEntitie.getLongitude() );
            jsonObject.addProperty("ApprovedByID",userId );
            jsonObject.addProperty("IsApproved",status);

            try {
                apiService.updateLocation(jsonObject)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<JsonObject>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(JsonObject response) {
                                updateLocation = response;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onPostApproveRejectStatusFinishedListener.approveRejectStatusFail("Something went wrong, Please try again", LocationEntitie,status);
                            }
                            @Override
                            public void onComplete() {
                                if (updateLocation != null) {
                                    try {
                                        JSONObject updateRespons = null;
                                        updateRespons = new JSONObject(updateLocation.toString());
                                        if(updateRespons.getString("status").equals("Fail")){
                                            onPostApproveRejectStatusFinishedListener.approveRejectDetailsError(updateRespons.getJSONObject("error").getString("description"));
                                        }else {
                                            onPostApproveRejectStatusFinishedListener.approveRejectStatusSuccess();
                                        }
                                    } catch (NullPointerException exNull) {
                                        onPostApproveRejectStatusFinishedListener.approveRejectStatusFail("Something went wrong, Please try again", LocationEntitie,status);
                                    } catch (JSONException e) {
                                        onPostApproveRejectStatusFinishedListener.approveRejectStatusFail("Something went wrong, Please try again", LocationEntitie,status);
                                    }
                                } else {
                                    onPostApproveRejectStatusFinishedListener.approveRejectStatusFail("Something went wrong, Please try again", LocationEntitie,status);
                                }
                            }
                        });

            } catch (Exception ex) {
                onPostApproveRejectStatusFinishedListener.approveRejectStatusFail("Something went wrong, Please try again", LocationEntitie,status);
            }
        }
    }

    @Override
    public void searchLocPendingForFilter(ArrayList<LocationEntitie> locationArrayList, String locationName, OnSearchLocPendingForFilterFinishedListener onSearchLocPendingForFilterFinishedListener) {

        final ArrayList<LocationEntitie> searchArrayList = new ArrayList<LocationEntitie>();

        if (locationArrayList.isEmpty()) {
        } else {

            for (LocationEntitie loc : locationArrayList) {
                if (loc.getName().equals(locationName)) {
                    searchArrayList.add(new LocationEntitie(loc.getId(), loc.getName(), loc.getAddress(),loc.getArea(),loc.getTown(),loc.getDistrictID(),loc.getDistrict(),loc.getLatitude(),loc.getLongitude()
                    ,loc.getCreatedByID(),loc.getCreatedByName(),loc.getLocationTypeID(),loc.getLocationType()));
                } else {
                }
            }

        }

        if ((searchArrayList.isEmpty()) && (!locationArrayList.isEmpty())) {
            if (locationName.isEmpty() || locationName.equals("") || locationName.equalsIgnoreCase("all")) {
                searchArrayList.addAll(locationArrayList);
            } else {
                for (LocationEntitie loc : locationArrayList) {
                    String text = loc.getName();
                    String patternString = locationName;

                    Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(text);
                    if (matcher.lookingAt()) {
                        searchArrayList.add(new LocationEntitie(loc.getId(), loc.getName(), loc.getAddress(),loc.getArea(),loc.getTown(),loc.getDistrictID(),loc.getDistrict(),loc.getLatitude(),loc.getLongitude()
                                ,loc.getCreatedByID(),loc.getCreatedByName(),loc.getLocationTypeID(),loc.getLocationType()));
                    } else {
                    }

                }

            }
        } else {
        }

        onSearchLocPendingForFilterFinishedListener.locPendingListForFilter(searchArrayList);


    }

    @Override
    public void addRepToLocationFilterStart(Rep rep, boolean addOrRemove, OnAddRepToLocationFilterStartFinishedListener onAddRepToLocationFilterStartFinishedListener) {
        onAddRepToLocationFilterStartFinishedListener.repToLocationFilterStart(rep,addOrRemove);
    }

    @Override
    public void addRepToLocationFilter(ArrayList<Rep> repArrayListToFilterGlobal, Rep rep, boolean addOrRemove, OnAddRepToLocationFilterFinishedListener onAddRepToLocationFilterFinishedListener) {
        if (addOrRemove) {
            if (repArrayListToFilterGlobal.isEmpty()) {
                repArrayListToFilterGlobal.add(new Rep(rep.getId(),rep.getName(),false));
            } else {
                boolean isFound = false;

                for (Rep r: repArrayListToFilterGlobal) {
                    if (r.getId() == rep.getId()) {
                        isFound = true;
                        break;
                    }
                }
                if (!isFound)
                    repArrayListToFilterGlobal.add(new Rep(rep.getId(),rep.getName(),false));
            }
        } else {
            if (repArrayListToFilterGlobal.isEmpty()) {
            } else {
                for (int i = 0; i < repArrayListToFilterGlobal.size(); i++) {
                    if (repArrayListToFilterGlobal.get(i).getId() == rep.getId()) {
                        repArrayListToFilterGlobal.remove(i);
                    }
                }
            }
        }
        onAddRepToLocationFilterFinishedListener.repToLocationFilter(repArrayListToFilterGlobal);


    }

    @Override
    public void showAddRepFilter(ArrayList<Rep> addedRepListGlobal, ArrayList<Rep> allFilterRepList, OnAddRepToFilterFinishedListener onAddRepToFilterFinishedListener) {
        final ArrayList<Rep> repArrayList = new ArrayList<Rep>();
        for (Rep r : addedRepListGlobal) {
            repArrayList.add(new Rep(r.getId(),r.getName(),true));
        }

        for (Rep repAll : allFilterRepList) {
            boolean status = false;
            for (Rep s : addedRepListGlobal) {
                if (repAll.getId() == s.getId()) {
                    status = true;
                    break;
                }
            }
            if (status) {
            } else {
                repArrayList.add(new Rep(repAll.getId(),repAll.getName(),false));
            }
        }
        onAddRepToFilterFinishedListener.addRepToFilter(repArrayList);

    }

    @Override
    public void setSelectedFilterRep(ArrayList<Rep> totalRepList, ArrayList<Rep> selectedLocList, OnSetSelectedFilterLocationFinishedListener onSetSelectedFilterLocationFinishedListener) {

        ArrayList<Rep> newRepList = new ArrayList<Rep>();
        if (selectedLocList.isEmpty()) {
            for (Rep r : totalRepList) {
                r.setSelect(false);
            }
            newRepList = totalRepList;
        } else {

            for (Rep added : selectedLocList) {
                newRepList.add(new Rep(added.getId(),added.getName(),true));
            }

            for (Rep added : totalRepList) {
                newRepList.add(new Rep(added.getId(),added.getName(),false));
            }

            for (int i = 0; i < newRepList.size(); i++) {
                for (int j = i + 1; j < newRepList.size(); j++) {
                    if (newRepList.get(i).getId() == newRepList.get(j).getId()) {
                        newRepList.remove(j);
                        j--;
                    }
                }
            }

        }
        onSetSelectedFilterLocationFinishedListener.showSelectedFilterLocation(newRepList);
    }




    @Override
    public void searchRepForFilter(ArrayList<Rep> repArrayList, String repName, OnSearchRepForFilterFinishedListener onSearchRepForFilterFinishedListener) {
        final ArrayList<Rep> searchArrayList = new ArrayList<Rep>();
        if (repArrayList.isEmpty()) {
        } else {
            for (Rep r : repArrayList) {
                if (r.getName().equals(repName)) {
                    searchArrayList.add(new Rep(r.getId(),r.getName(),false));
                } else {
                }
            }
        }

        if ((searchArrayList.isEmpty()) && (!repArrayList.isEmpty())) {
            if (repName.isEmpty() || repName.equals("") || repName.equalsIgnoreCase("all")) {
                searchArrayList.addAll(repArrayList);
            } else {
                for (Rep rep : repArrayList) {
                    String text = rep.getName();
                    String patternString = repName;

                    Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(text);
                    if (matcher.lookingAt()) {
                        searchArrayList.add(new Rep(rep.getId(),rep.getName(),false));
                    } else {
                    }
                }

            }
        } else {
        }
        onSearchRepForFilterFinishedListener.repListForFilter(searchArrayList);
    }

    @Override
    public void locationFilter(ArrayList<LocationEntitie> locationArrayList, ArrayList<Rep> repList, OnLocationFilterFinishedListener onLocationFilterFinishedListener) {

        ArrayList<LocationEntitie> newArrayList=new ArrayList<>();
        newArrayList.addAll(locationArrayList);

        if(repList.isEmpty()){ }else {

            for (int i = 0; i < newArrayList.size(); i++) {
                boolean status = false;
                for (Rep r : repList) {
                    if (r.getId() == newArrayList.get(i).getCreatedByID()) {
                        status = true;
                        break;
                    } else {
                        status = false;
                    }

                }
                if (!status) {
                    newArrayList.remove(i);
                    i--;
                }
            }
        }


        onLocationFilterFinishedListener.locationFilterList(newArrayList);




    }


}


