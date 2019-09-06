package emerge.project.mrsolution_admin_micro.ui.activity.doctors;


import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pddstudio.preferences.encrypted.EncryptedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import emerge.project.mrsolution_admin_micro.BuildConfig;
import emerge.project.mrsolution_admin_micro.R;
import emerge.project.mrsolution_admin_micro.services.api.ApiClient;
import emerge.project.mrsolution_admin_micro.services.api.ApiInterface;
import emerge.project.mrsolution_admin_micro.services.network.NetworkAvailability;
import emerge.project.mrsolution_admin_micro.utils.entittes.Doctor;
import emerge.project.mrsolution_admin_micro.utils.entittes.LocationEntitie;
import emerge.project.mrsolution_admin_micro.utils.entittes.Navigation;
import emerge.project.mrsolution_admin_micro.utils.entittes.Rep;
import emerge.project.mrsolution_admin_micro.utils.entittes.Specialization;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Himanshu on 4/5/2017.
 */

public class DoctorsInteractorImpil implements DoctorsInteractor {


    public static final String tokenID = BuildConfig.API_TOKEN_ID;

    EncryptedPreferences encryptedPreferences;
    private static final String USER_ID = "userID";
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


    ArrayList<Doctor> doctorPendingyList;
    JsonObject updateDoctor;
    ArrayList<Doctor> doctorsApprovedList;
    ArrayList<Doctor> doctorsList;
    ArrayList<LocationEntitie> approvedLocationEntitie;
    ArrayList<Specialization> specList;

    Boolean docAssingRespond;

    @Override
    public void setNavigation(OnsetNavigationFinishedListener onsetNavigationFinishedListener) {
        ArrayList<Navigation> navigationItems = new ArrayList<Navigation>();
        navigationItems.add(new Navigation("Location Assing", R.drawable.ic_product_defult_small));

        onsetNavigationFinishedListener.navigationItems(navigationItems);
    }

    @Override
    public void getPendingDoctor(Context context, final OnGetPendingDoctorFinishedListener onGetPendingDoctorFinishedListener) {
        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onGetPendingDoctorFinishedListener.pendingDoctorsGetingNetworkFail();
        } else {
            try {

                encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
                int userId = encryptedPreferences.getInt(USER_ID, 0);

                final ArrayList<Doctor> doctorPendingArrayList = new ArrayList<Doctor>();

                final ArrayList<String> doctorPendingNameForFilter = new ArrayList<String>();
                final ArrayList<String> doctorPendingPhoneNumberForFilter = new ArrayList<String>();
                final ArrayList<String> doctorPendingRegNumberForFilter = new ArrayList<String>();

                final ArrayList<Rep>doctorPendingRepsForFilter = new ArrayList<Rep>();
                final ArrayList<String>doctorPendingRepNameForFilter = new ArrayList<String>();


                apiService.getUnapprovedDoctors(tokenID, userId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<Doctor>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(ArrayList<Doctor> doctors) {
                                doctorPendingyList = doctors;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onGetPendingDoctorFinishedListener.pendingDoctorsGetingFail("Something went wrong, Please try again");
                            }

                            @Override
                            public void onComplete() {
                                if (doctorPendingyList.isEmpty()) {
                                    onGetPendingDoctorFinishedListener.pendingDoctorsEmpty();
                                } else {
                                    for(Doctor doc :doctorPendingyList){
                                        doctorPendingArrayList.add(new Doctor(doc.getId(),doc.getName(),doc.getCode(),doc.getContactNo(),doc.getRegistrationNo(),doc.getQualification(),doc.getCreatedDate(), doc.getCreatedByID(),doc.getCreatedByName(),doc.getImageUrl()));
                                    }
                                    onGetPendingDoctorFinishedListener.pendingDoctorsList(doctorPendingArrayList);

                                    for(Doctor doc :doctorPendingyList){

                                        doctorPendingNameForFilter.add(doc.getName());
                                        doctorPendingPhoneNumberForFilter.add(doc.getContactNo());
                                        doctorPendingRegNumberForFilter.add(doc.getRegistrationNo());


                                        if (doctorPendingRepsForFilter.isEmpty()) {
                                            doctorPendingRepsForFilter.add(new Rep(doc.getCreatedByID(),doc.getCreatedByName(),false));
                                            doctorPendingRepNameForFilter.add(doc.getCreatedByName());

                                        } else {
                                            boolean status = false;
                                            for (Rep rep : doctorPendingRepsForFilter) {
                                                if (rep.getId() == doc.getCreatedByID()) {
                                                    status = true;
                                                    break;
                                                } else {
                                                }
                                            }
                                            if (!status) {
                                                doctorPendingRepsForFilter.add(new Rep(doc.getCreatedByID(),doc.getCreatedByName(),false));
                                                doctorPendingRepNameForFilter.add(doc.getCreatedByName());
                                            } else {
                                            }
                                        }

                                    }
                                    onGetPendingDoctorFinishedListener.pendingDoctorsNameFilterList(doctorPendingNameForFilter);
                                    onGetPendingDoctorFinishedListener.pendingDoctorsPhoneNumberFilterList(doctorPendingPhoneNumberForFilter);
                                    onGetPendingDoctorFinishedListener.pendingDoctorsRegNumberFilterList(doctorPendingRegNumberForFilter);

                                    onGetPendingDoctorFinishedListener.pendingDoctorsRepsFilterList(doctorPendingRepsForFilter);
                                    onGetPendingDoctorFinishedListener.pendingDoctorsRepNamesFilterList(doctorPendingRepNameForFilter);


                                }
                            }
                        });
            } catch (Exception ex) {
                onGetPendingDoctorFinishedListener.pendingDoctorsGetingFail("Something went wrong, Please try again");
            }

        }
    }

    @Override
    public void getApprovedDoctor(Context context, final OnGetApprovedDoctorFinishedListener onGetApprovedDoctorFinishedListener) {

        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onGetApprovedDoctorFinishedListener.approvedDoctorsGetingNetworkFail();
        } else {
            try {
                encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
                int userId = encryptedPreferences.getInt(USER_ID, 0);
                final ArrayList<Doctor> doctorApprovedArrayList = new ArrayList<Doctor>();



                final ArrayList<String> doctorApprovedNameForFilter = new ArrayList<String>();
                final ArrayList<String> doctorApprovedPhoneNumberForFilter = new ArrayList<String>();
                final ArrayList<String> doctorApprovedRegNumberForFilter = new ArrayList<String>();

                final ArrayList<Rep>doctorApprovedRepsForFilter = new ArrayList<Rep>();
                final ArrayList<String>doctorApprovedRepNameForFilter = new ArrayList<String>();




                apiService.getApprovedDoctorsByAdmin(tokenID, userId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<Doctor>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(ArrayList<Doctor> doctors) {
                                doctorsApprovedList = doctors;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onGetApprovedDoctorFinishedListener.approvedDoctorsGetingFail("Something went wrong, Please try again");

                            }

                            @Override
                            public void onComplete() {
                                if (doctorsApprovedList.isEmpty()) {
                                    onGetApprovedDoctorFinishedListener.approvedDoctorsEmpty();
                                } else {
                                    for (Doctor doc : doctorsApprovedList) {
                                        String specialization = "";
                                        String loca = "";

                                        if (doc.getSpecializationList().isEmpty()) {
                                            specialization = "Specialization not assign ";
                                        } else {
                                            for (Specialization spc : doc.getSpecializationList()) {
                                                if (specialization.equals("")) {
                                                    specialization = spc.getName();
                                                } else {
                                                    specialization = specialization + "/" + spc.getName();
                                                }
                                            }
                                        }

                                        if (doc.getLocationEntitieList().isEmpty()) {
                                            specialization = "Locations not assign ";
                                        } else {
                                            for (LocationEntitie loc : doc.getLocationEntitieList()) {
                                                if (loca.equals("")) {
                                                    loca = loc.getName();
                                                } else {
                                                    loca = loca + "/" + loc.getName();
                                                }
                                            }
                                        }

                                        doctorApprovedArrayList.add(new Doctor(doc.getId(), doc.getName(), doc.getCode(), doc.getContactNo(), doc.getRegistrationNo(), doc.getQualification(), doc.getCreatedDate()
                                                , doc.getCreatedByID(), doc.getCreatedByName(), specialization, doc.getImageUrl(),loca));
                                    }
                                    onGetApprovedDoctorFinishedListener.approvedDoctorsList(doctorApprovedArrayList);




                                    for(Doctor doc :doctorsApprovedList){

                                        doctorApprovedNameForFilter.add(doc.getName());
                                        doctorApprovedPhoneNumberForFilter.add(doc.getContactNo());
                                        doctorApprovedRegNumberForFilter.add(doc.getRegistrationNo());


                                        if (doctorApprovedRepsForFilter.isEmpty()) {
                                            doctorApprovedRepsForFilter.add(new Rep(doc.getCreatedByID(),doc.getCreatedByName(),false));
                                            doctorApprovedRepNameForFilter.add(doc.getCreatedByName());

                                        } else {
                                            boolean status = false;
                                            for (Rep rep : doctorApprovedRepsForFilter) {
                                                if (rep.getId() == doc.getCreatedByID()) {
                                                    status = true;
                                                    break;
                                                } else {
                                                }
                                            }
                                            if (!status) {
                                                doctorApprovedRepsForFilter.add(new Rep(doc.getCreatedByID(),doc.getCreatedByName(),false));
                                                doctorApprovedRepNameForFilter.add(doc.getCreatedByName());
                                            } else {
                                            }
                                        }

                                    }
                                    onGetApprovedDoctorFinishedListener.approvedDoctorsNameFilterList(doctorApprovedNameForFilter);
                                    onGetApprovedDoctorFinishedListener.approvedDoctorsPhoneNumberFilterList(doctorApprovedPhoneNumberForFilter);
                                    onGetApprovedDoctorFinishedListener.approvedDoctorsRegNumberFilterList(doctorApprovedRegNumberForFilter);

                                    onGetApprovedDoctorFinishedListener.approvedDoctorsRepsFilterList(doctorApprovedRepsForFilter);
                                    onGetApprovedDoctorFinishedListener.approvedDoctorsRepNamesFilterList(doctorApprovedRepNameForFilter);



                                }

                            }
                        });
            } catch (Exception ex) {
                onGetApprovedDoctorFinishedListener.approvedDoctorsGetingFail("Something went wrong, Please try again");
            }
        }


    }

    @Override
    public void postApproveRejectStatus(Context context, final Doctor doctor, final boolean status, final OnPostApproveRejectStatusFinishedListener onPostApproveRejectStatusFinishedListener) {
        onPostApproveRejectStatusFinishedListener.approveRejectStatusStart();

        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onPostApproveRejectStatusFinishedListener.approveRejectStatusNetworkFail();
        } else if (doctor.getName().isEmpty() || doctor.getName().equals("") || doctor.getName() == null) {
            onPostApproveRejectStatusFinishedListener.approveRejectDetailsError("Doctor name is empty");
        } else if (doctor.getContactNo().isEmpty() || doctor.getContactNo().equals("") || doctor.getContactNo() == null) {
            onPostApproveRejectStatusFinishedListener.approveRejectDetailsError("Doctor contact number is empty");
        } else if (doctor.getQualification().isEmpty() || doctor.getQualification().equals("") || doctor.getQualification() == null) {
            onPostApproveRejectStatusFinishedListener.approveRejectDetailsError("Doctor qualification number is empty");
        } else {

            encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
            int userId = encryptedPreferences.getInt(USER_ID, 0);

            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("ID", doctor.getId());
            jsonObject.addProperty("name", doctor.getName());
            jsonObject.addProperty("ContactNo", doctor.getContactNo());
            jsonObject.addProperty("RegistrationNo", doctor.getRegistrationNo());
            jsonObject.addProperty("Qualification", doctor.getQualification());
            jsonObject.addProperty("ApprovedByID", userId);
            jsonObject.addProperty("IsApproved", status);


            System.out.println("cc jsonObject :"+jsonObject);

            try {
                apiService.updateDoctor(jsonObject)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<JsonObject>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(JsonObject response) {
                                updateDoctor = response;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onPostApproveRejectStatusFinishedListener.approveRejectStatusFail("Something went wrong, Please try again", doctor, status);
                            }

                            @Override
                            public void onComplete() {
                                if (updateDoctor != null) {
                                    try {
                                        JSONObject updateRespons = null;
                                        updateRespons = new JSONObject(updateDoctor.toString());
                                        if (updateRespons.getString("status").equals("Fail")) {
                                            onPostApproveRejectStatusFinishedListener.approveRejectDetailsError(updateRespons.getJSONObject("error").getString("description"));
                                        } else {
                                            onPostApproveRejectStatusFinishedListener.approveRejectStatusSuccess();
                                        }
                                    } catch (NullPointerException exNull) {
                                        onPostApproveRejectStatusFinishedListener.approveRejectStatusFail("Something went wrong, Please try again", doctor, status);
                                    } catch (JSONException e) {
                                        onPostApproveRejectStatusFinishedListener.approveRejectStatusFail("Something went wrong, Please try again", doctor, status);
                                    }
                                } else {
                                    onPostApproveRejectStatusFinishedListener.approveRejectStatusFail("Something went wrong, Please try again", doctor, status);
                                }
                            }
                        });

            } catch (Exception ex) {
                onPostApproveRejectStatusFinishedListener.approveRejectStatusFail("Something went wrong, Please try again", doctor, status);
            }

        }

    }

    @Override
    public void getDoctorApproveFullDetais(Doctor doctor, OnGetApproveDoctorFullDetailsFinishedListener onGetApproveDoctorFullDetailsFinishedListener) {
        onGetApproveDoctorFullDetailsFinishedListener.doctorApproveFullDetais(doctor);
    }


    @Override
    public void getDoctorPendingFullDetais(Doctor doctor, OnGetDoctorPendingFullDetailsFinishedListener onGetDoctorPendingFullDetailsFinishedListener) {
        onGetDoctorPendingFullDetailsFinishedListener.doctorPendingFullDetais(doctor);
    }

    @Override
    public void getDoctors(Context context, final OnGetDoctorFinishedListener onGetDoctorFinishedListener) {

        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onGetDoctorFinishedListener.doctorsGetingNetworkFail();
        } else {
            try {
                encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
                int userId = encryptedPreferences.getInt(USER_ID, 0);
                final ArrayList<Doctor> doctorApprovedArrayList = new ArrayList<Doctor>();
                final ArrayList<String> doctorsNames = new ArrayList<String>();


                apiService.getApprovedDoctorsByAdmin(tokenID, userId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<Doctor>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(ArrayList<Doctor> doctors) {
                                doctorsList = doctors;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onGetDoctorFinishedListener.doctorsGetingFail("Something went wrong, Please try again");
                            }

                            @Override
                            public void onComplete() {
                                if (doctorsList.isEmpty()) {
                                    onGetDoctorFinishedListener.doctorsEmpty();
                                } else {
                                    for (Doctor doc : doctorsList) {
                                        doctorApprovedArrayList.add(new Doctor(doc.getId(), doc.getName(), doc.getImageUrl(),doc.getLocationEntitieList(),doc.getSpecializationList() ,false));
                                        doctorsNames.add(doc.getName());
                                    }
                                    onGetDoctorFinishedListener.doctorsList(doctorApprovedArrayList, doctorsNames);

                                }
                            }
                        });
            } catch (Exception ex) {
                onGetDoctorFinishedListener.doctorsGetingFail("Something went wrong, Please try again");
            }
        }


    }

    @Override
    public void searchDoctor(ArrayList<Doctor> docArrayList, String doctorName, OnSearchDoctorFinishedListener onSearchDoctorFinishedListener) {
        final ArrayList<Doctor> searchArrayList = new ArrayList<Doctor>();
        if (docArrayList.isEmpty()) {
        } else {
            for (Doctor doc : docArrayList) {
                if (doc.getName().equals(doctorName)) {
                    searchArrayList.add(new Doctor(doc.getId(), doc.getName(), doc.getImageUrl(),doc.getLocationEntitieList(),doc.getSpecializationList() ,false));
                } else {
                }
            }
        }


        if ((searchArrayList.isEmpty()) && (!docArrayList.isEmpty())) {
            if (doctorName.isEmpty() || doctorName.equals("") || doctorName.equalsIgnoreCase("all")) {
                searchArrayList.addAll(docArrayList);
            } else {
                for (Doctor doc : docArrayList) {
                    String text = doc.getName();
                    String patternString = doctorName;

                    Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(text);
                    if (matcher.lookingAt()) {
                        searchArrayList.add(new Doctor(doc.getId(), doc.getName(), doc.getImageUrl(),doc.getLocationEntitieList(),doc.getSpecializationList() ,false));
                    } else {
                    }
                }

            }
        } else { }

        onSearchDoctorFinishedListener.searchDoctorList(searchArrayList);
    }

    @Override
    public void getLocation(Context context,final OnGetLocationFinishedListener onGetLocationFinishedListener) {
        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onGetLocationFinishedListener.locationNetworkFail();
        } else {
            try {
                encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
                int userId = encryptedPreferences.getInt(USER_ID, 0);

                final ArrayList<LocationEntitie> locationEntitieArrayList = new ArrayList<LocationEntitie>();

                final ArrayList<String> locationNameList = new ArrayList<String>();



                apiService.getLocationsApprovedByAdmin("5050", userId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<LocationEntitie>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(ArrayList<LocationEntitie> loginuser) {
                                approvedLocationEntitie = loginuser;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onGetLocationFinishedListener.locationFail("Something went wrong, Please try again");
                            }

                            @Override
                            public void onComplete() {
                                if (approvedLocationEntitie.isEmpty()) {
                                    onGetLocationFinishedListener.locationListEmpty();
                                } else {
                                    for (LocationEntitie loc : approvedLocationEntitie) {
                                        locationEntitieArrayList.add(new LocationEntitie(loc.getId(), loc.getName(), loc.getAddress(), false));
                                        locationNameList.add(loc.getName());

                                    }
                                    onGetLocationFinishedListener.locationList(locationEntitieArrayList, locationNameList);


                                }
                            }
                        });
            } catch (Exception ex) {
                onGetLocationFinishedListener.locationFail("Something went wrong, Please try again");
            }
        }
    }

    @Override
    public void searchLocation(ArrayList<LocationEntitie> locArrayList, String locationName, OnSearchLocationFinishedListener onSearchLocationFinishedListener) {
        final ArrayList<LocationEntitie> locationsArrayList = new ArrayList<LocationEntitie>();
        if (locArrayList.isEmpty()) {
        } else {
            for (LocationEntitie loc : locArrayList) {
                if (loc.getName().equals(locationName)) {
                    locationsArrayList.add(new LocationEntitie(loc.getId(), loc.getName(), loc.getAddress(), false));
                } else {
                }
            }
        }

        if ((locationsArrayList.isEmpty()) && (!locArrayList.isEmpty())) {
            if (locationName.isEmpty() || locationName.equals("") || locationName.equalsIgnoreCase("all")) {
                locationsArrayList.addAll(locArrayList);
            } else {
                for (LocationEntitie loc : locArrayList) {
                    String text = loc.getName();
                    String patternString = locationName;

                    Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(text);
                    if (matcher.lookingAt()) {
                        locationsArrayList.add(new LocationEntitie(loc.getId(), loc.getName(), loc.getAddress(), false));
                    } else {
                    }
                }

            }
        } else { }

        onSearchLocationFinishedListener.searchLocationList(locationsArrayList);
    }

    @Override
    public void getSelectedDoc(Doctor doc, OnSelectedDoctorsFinishedListener onSelectedDoctorsFinishedListener) {
        onSelectedDoctorsFinishedListener.selectedDoc(doc);
    }

    @Override
    public void assignLocationToDocStart(LocationEntitie LocationEntitie, boolean addOrRemove, OnAssignLocationToDocStartFinishedListener onAssignLocationToDocStartFinishedListener) {
        onAssignLocationToDocStartFinishedListener.locationToDocStart(LocationEntitie, addOrRemove);
    }

    @Override
    public void assignLocationToDoc(ArrayList<LocationEntitie> locationEntitieListGlobal, LocationEntitie loc, boolean addOrRemove, OnAssignLocationToDocFinishedListener onAssignLocationToDocFinishedListener) {

        if (addOrRemove) {
            if (locationEntitieListGlobal.isEmpty()) {
                locationEntitieListGlobal.add(new LocationEntitie(loc.getId(), loc.getName()));
            } else {
                boolean isFound = false;

                for(LocationEntitie lo: locationEntitieListGlobal){
                    if (lo.getId() == loc.getId()) {
                        isFound = true;
                        break;
                    }
                }
                if (!isFound)
                    locationEntitieListGlobal.add(new LocationEntitie(loc.getId(), loc.getName()));
            }
        } else {
            if (locationEntitieListGlobal.isEmpty()) {
            } else {
                for (int i = 0; i < locationEntitieListGlobal.size(); i++) {
                    if (locationEntitieListGlobal.get(i).getId() == loc.getId()) {
                        locationEntitieListGlobal.remove(i);
                    }
                }
            }
        }
        onAssignLocationToDocFinishedListener.locationToDoc(locationEntitieListGlobal);


    }

    @Override
    public void showAssignLocation(ArrayList<LocationEntitie> addedlocationListGlobal, ArrayList<LocationEntitie> alllocationList, OnShowAssignLocationFinishedListener onShowAssignLocationFinishedListener) {

        final ArrayList<LocationEntitie> locArrayList = new ArrayList<LocationEntitie>();



        for (LocationEntitie loc : addedlocationListGlobal) {
            locArrayList.add(new LocationEntitie(loc.getId(), loc.getName(),loc.getAddress(),true));
        }

        for (LocationEntitie locAll : alllocationList) {
            boolean status = false;
            for (LocationEntitie loca : addedlocationListGlobal) {
                if (locAll.getId() == loca.getId()) {
                    status = true;
                    break;
                }
            }
            if (status) {
            } else {
                locArrayList.add(new LocationEntitie(locAll.getId(), locAll.getName(),locAll.getAddress(),false));

            }
        }
        onShowAssignLocationFinishedListener.assignLocation(locArrayList);


    }

    @Override
    public void getSpecialization(Context context, final OnGetSpecializationFinishedListener onGetSpecializationFinishedListener) {
        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onGetSpecializationFinishedListener.specializationNetworkFail();
        } else {
            try {

                final ArrayList<Specialization> specializationArrayList = new ArrayList<Specialization>();
                final ArrayList<String> specializationNameList = new ArrayList<String>();


                apiService.getApprovedSpecializations("5050")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<Specialization>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(ArrayList<Specialization> specializations) {
                                specList = specializations;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onGetSpecializationFinishedListener.specializationFail("Something went wrong, Please try again");
                            }

                            @Override
                            public void onComplete() {
                                if (specList.isEmpty()) {
                                    onGetSpecializationFinishedListener.specializationEmpty();
                                } else {
                                    for (Specialization spec : specList) {
                                        specializationArrayList.add(new Specialization(spec.getId(), spec.getName(), false));
                                        specializationNameList.add(spec.getName());
                                    }
                                    onGetSpecializationFinishedListener.specializationList(specializationArrayList, specializationNameList);
                                }
                            }
                        });
            } catch (Exception ex) {
                onGetSpecializationFinishedListener.specializationFail("Something went wrong, Please try again");
            }
        }


    }

    @Override
    public void searchSpecialization(ArrayList<Specialization> specArrayList, String specializationName, OnSearchSpecializationnFinishedListener onSearchSpecializationnFinishedListener) {
        final ArrayList<Specialization> specializationArrayList = new ArrayList<Specialization>();
        if (specArrayList.isEmpty()) {
        } else {
            for (Specialization spec : specArrayList) {
                if (spec.getName().equals(specializationName)) {
                    specializationArrayList.add(new Specialization(spec.getId(), spec.getName(), false));
                } else {
                }
            }
        }
        if ((specializationArrayList.isEmpty()) && (!specArrayList.isEmpty())) {
            if (specializationName.isEmpty() || specializationName.equals("") || specializationName.equalsIgnoreCase("all")) {
                specializationArrayList.addAll(specArrayList);
            } else {
                for (Specialization spc : specArrayList) {
                    String text = spc.getName();
                    String patternString = specializationName;

                    Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(text);

                    if (matcher.lookingAt()) {
                        specializationArrayList.add(new Specialization(spc.getId(), spc.getName(), false));
                    } else {
                    }

                }

            }
        } else {
        }
        onSearchSpecializationnFinishedListener.searchSpecializationList(specializationArrayList);

    }

    @Override
    public void assignSpecializationToDocStart(Specialization specialization, boolean addOrRemove, OnAssignSpecializationToDocStartFinishedListener onAssignSpecializationToDocStartFinishedListener) {
        onAssignSpecializationToDocStartFinishedListener.specializationToDocStart(specialization, addOrRemove);
    }

    @Override
    public void assignSpecializationToDoc(ArrayList<Specialization> specializationListGlobal, Specialization spec, boolean addOrRemove, OnAssignSpecializationToDocFinishedListener onAssignSpecializationToDocFinishedListener) {
        if (addOrRemove) {
            if (specializationListGlobal.isEmpty()) {
                specializationListGlobal.add(new Specialization(spec.getId(), spec.getName()));
            } else {
                boolean isFound = false;

                for(Specialization sp:specializationListGlobal){
                    if (sp.getId() == spec.getId()) {
                        isFound = true;
                        break;
                    }
                }
                if (!isFound)
                    specializationListGlobal.add(new Specialization(spec.getId(), spec.getName()));
            }
        } else {
            if (specializationListGlobal.isEmpty()) {
            } else {
                for (int i = 0; i < specializationListGlobal.size(); i++) {
                    if (specializationListGlobal.get(i).getId() == spec.getId()) {
                        specializationListGlobal.remove(i);
                    }
                }
            }
        }
        onAssignSpecializationToDocFinishedListener.specializationToDoc(specializationListGlobal);
    }

    @Override
    public void showAssignSpec(ArrayList<Specialization> specializationListGlobal, ArrayList<Specialization> allSpecList, OnShowAssignSpecFinishedListener onShowAssignSpecFinishedListener) {
        final ArrayList<Specialization> specArrayList = new ArrayList<Specialization>();


        for (Specialization spc : specializationListGlobal) {
            specArrayList.add(new Specialization(spc.getId(), spc.getName(), true));
        }
        for (Specialization spcAll : allSpecList) {
            boolean status = false;
            for (Specialization spc : specializationListGlobal) {
                if (spcAll.getId() == spc.getId()) {
                    status = true;
                    break;
                }

            }
            if (status) {
            } else {
                specArrayList.add(new Specialization(spcAll.getId(), spcAll.getName(), false));
            }
        }
        onShowAssignSpecFinishedListener.assignSpec(specArrayList);



    }

    @Override
    public void postLocationToDoctors(Context context, final int docid, final ArrayList<LocationEntitie> locationsArrayList, final ArrayList<Specialization> specializationArrayList, final OnPostLocationToDoctorsFinishedListener onPostLocationToDoctorsFinishedListener) {

        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onPostLocationToDoctorsFinishedListener.postLocationToDoctorsNetworkFail();
        } else if (docid == 0) {
            onPostLocationToDoctorsFinishedListener.postLocationToDoctorsEmpty("Please select Doctor");
        } else if (locationsArrayList.isEmpty()) {
            onPostLocationToDoctorsFinishedListener.postLocationToDoctorsEmpty("Please select Locations");
        } else if (specializationArrayList.isEmpty()) {
            onPostLocationToDoctorsFinishedListener.postLocationToDoctorsEmpty("Please select Specialization");
        } else {

            encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
            int userId = encryptedPreferences.getInt(USER_ID, 0);

            final JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("ID", docid);
            jsonObject.addProperty("AssignedByID", userId);


            JsonArray locJsonArr = new JsonArray();
            JsonArray specJsonArr = new JsonArray();

            for(LocationEntitie loc:locationsArrayList){
                JsonObject ob = new JsonObject();
                ob.addProperty("ID", loc.getId());
                locJsonArr.add(ob);
            }


            for(Specialization spec:specializationArrayList){
                JsonObject ob = new JsonObject();
                ob.addProperty("ID", spec.getId());
                specJsonArr.add(ob);
            }
            jsonObject.add("LocationList", locJsonArr);
            jsonObject.add("SpecializationList", specJsonArr);

            System.out.println( "aaaaaa LocationList : "+locJsonArr);
            System.out.println( "aaaaaa SpecializationList : "+specJsonArr);

            onPostLocationToDoctorsFinishedListener.postLocationToDoctorsSuccessful();

            try {
                apiService.assignLocAndSpecToDoctors(jsonObject)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(Boolean respond) { docAssingRespond = respond; }

                            @Override
                            public void onError(Throwable e) {
                                onPostLocationToDoctorsFinishedListener.postLocationToDoctorsFail("Something went wrong, Please try again",docid,locationsArrayList,specializationArrayList);
                            }

                            @Override
                            public void onComplete() {
                                if(docAssingRespond){
                                    onPostLocationToDoctorsFinishedListener.postLocationToDoctorsSuccessful();
                                }else {
                                    onPostLocationToDoctorsFinishedListener.postLocationToDoctorsFail("Something went wrong, Please try again",docid,locationsArrayList,specializationArrayList);
                                }

                            }
                        });

            } catch (Exception ex) {
                onPostLocationToDoctorsFinishedListener.postLocationToDoctorsFail("Something went wrong, Please try again",docid,locationsArrayList,specializationArrayList);
            }

        }


    }

    @Override
    public void getLocationWithDoctorLocation(ArrayList<LocationEntitie> allLocationEntitieArrayList, ArrayList<LocationEntitie> doctorLocationEntitieArrayList, getLocationWithDoctorLocation getLocationWithDoctorLocation) {

        final ArrayList<LocationEntitie> locationEntitieArrayList = new ArrayList<LocationEntitie>();

        if(doctorLocationEntitieArrayList.isEmpty()){

        }else {
            for (LocationEntitie loc : doctorLocationEntitieArrayList) {

                locationEntitieArrayList.add(new LocationEntitie(loc.getId(), loc.getName(), loc.getAddress(), true));
            }
        }

        for(LocationEntitie allLoc : allLocationEntitieArrayList){
            boolean status = false;
            for (LocationEntitie loc : doctorLocationEntitieArrayList) {
                if (allLoc.getId() == loc.getId()) {
                    status = true;
                    break;
                }
            }
            if (status) {

            } else {
                locationEntitieArrayList.add(new LocationEntitie(allLoc.getId(), allLoc.getName(), allLoc.getAddress(), false));
            }
        }

        getLocationWithDoctorLocation.locationListWithDoctorLocation(locationEntitieArrayList, doctorLocationEntitieArrayList);


    }

    @Override
    public void getSpecWithDoctorSpec(ArrayList<Specialization> allSpecArrayList, ArrayList<Specialization> doctorSpecArrayList, getSpecWithDoctorSpec getSpecWithDoctorSpec) {

        final ArrayList<Specialization> specArrayList = new ArrayList<Specialization>();

        if(doctorSpecArrayList.isEmpty()){

        }else {
            for (Specialization spc : doctorSpecArrayList) {
                specArrayList.add(new Specialization(spc.getId(), spc.getName(), true));
            }
        }

        for(Specialization allSpec : allSpecArrayList){
            boolean status = false;
            for (Specialization spc : doctorSpecArrayList) {
                if (allSpec.getId() == spc.getId()) {
                    status = true;
                    break;
                }
            }
            if (status) {

            } else {
                specArrayList.add(new Specialization(allSpec.getId(), allSpec.getName(), false));
            }
        }

        getSpecWithDoctorSpec.specListWithDoctorSpec(specArrayList,doctorSpecArrayList);

    }

    @Override
    public void doctorsFilter(ArrayList<Doctor> docArrayList, String docName,String docTPName,String docRegName,ArrayList<Rep> repList, OnDoctorsFilterFinishedListener onDoctorsFilterFinishedListener) {

        ArrayList<Doctor> searchArrayList = new ArrayList<Doctor>();
        if(!docName.equals("")){
            if (docArrayList.isEmpty()) {
            } else {
                for (Doctor doc : docArrayList) {
                    if (doc.getName().equals(docName)) {
                        searchArrayList.add(new Doctor(doc.getId(),doc.getName(),doc.getCode(),doc.getContactNo(),doc.getRegistrationNo(),doc.getQualification(),doc.getCreatedDate(), doc.getCreatedByID(),doc.getCreatedByName(),doc.getImageUrl()));
                    } else {
                    }
                }
            }
            if ((searchArrayList.isEmpty()) && (!docArrayList.isEmpty())) {
                if (docName.isEmpty() || docName.equals("") || docName.equalsIgnoreCase("all")) {
                    searchArrayList.addAll(docArrayList);
                } else {
                    for (Doctor doc : docArrayList) {
                        String text = doc.getName();
                        String patternString = docName;

                        Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
                        Matcher matcher = pattern.matcher(text);
                        if (matcher.lookingAt()) {
                            searchArrayList.add(new Doctor(doc.getId(),doc.getName(),doc.getCode(),doc.getContactNo(),doc.getRegistrationNo(),doc.getQualification(),doc.getCreatedDate(), doc.getCreatedByID(),doc.getCreatedByName(),doc.getImageUrl()));
                        } else {
                        }
                    }
                }
            } else {
            }

        }else if(!docTPName.equals("")){

            if (docArrayList.isEmpty()) {
            } else {
                for (Doctor doc : docArrayList) {
                    if (doc.getContactNo().equals(docTPName)) {
                        searchArrayList.add(new Doctor(doc.getId(),doc.getName(),doc.getCode(),doc.getContactNo(),doc.getRegistrationNo(),doc.getQualification(),doc.getCreatedDate(), doc.getCreatedByID(),doc.getCreatedByName(),doc.getImageUrl()));
                    } else {
                    }
                }
            }

            if ((searchArrayList.isEmpty()) && (!docArrayList.isEmpty())) {
                if (docTPName.isEmpty() || docTPName.equals("") || docTPName.equalsIgnoreCase("all")) {
                    searchArrayList.addAll(docArrayList);
                } else {
                    for (Doctor doc : docArrayList) {
                        String text = doc.getContactNo();
                        String patternString = docTPName;

                        Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
                        Matcher matcher = pattern.matcher(text);
                        if (matcher.lookingAt()) {
                            searchArrayList.add(new Doctor(doc.getId(),doc.getName(),doc.getCode(),doc.getContactNo(),doc.getRegistrationNo(),doc.getQualification(),doc.getCreatedDate(), doc.getCreatedByID(),doc.getCreatedByName(),doc.getImageUrl()));
                        } else {
                        }
                    }
                }
            } else {
            }
        }else if(!docRegName.equals("")){
            if (docArrayList.isEmpty()) {
            } else {
                for (Doctor doc : docArrayList) {
                    if (doc.getRegistrationNo().equals(docRegName)) {
                        searchArrayList.add(new Doctor(doc.getId(),doc.getName(),doc.getCode(),doc.getContactNo(),doc.getRegistrationNo(),doc.getQualification(),doc.getCreatedDate(), doc.getCreatedByID(),doc.getCreatedByName(),doc.getImageUrl()));
                    } else {
                    }
                }
            }
            if ((searchArrayList.isEmpty()) && (!docArrayList.isEmpty())) {
                if (docRegName.isEmpty() || docRegName.equals("") || docRegName.equalsIgnoreCase("all")) {
                    searchArrayList.addAll(docArrayList);
                } else {
                    for (Doctor doc : docArrayList) {
                        String text = doc.getRegistrationNo();
                        String patternString = docRegName;

                        Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
                        Matcher matcher = pattern.matcher(text);
                        if (matcher.lookingAt()) {
                            searchArrayList.add(new Doctor(doc.getId(),doc.getName(),doc.getCode(),doc.getContactNo(),doc.getRegistrationNo(),doc.getQualification(),doc.getCreatedDate(), doc.getCreatedByID(),doc.getCreatedByName(),doc.getImageUrl()));
                        } else {
                        }
                    }
                }
            } else {
            }

        }else if(!repList.isEmpty()){
            searchArrayList.addAll(docArrayList);

            if(repList.isEmpty()){ }else {

                for (int i = 0; i < searchArrayList.size(); i++) {
                    boolean status = false;
                    for (Rep r : repList) {
                        if (r.getId() == searchArrayList.get(i).getCreatedByID()) {
                            status = true;
                            break;
                        } else {
                            status = false;
                        }

                    }
                    if (!status) {
                        searchArrayList.remove(i);
                        i--;
                    }
                }
            }

        }else {
            searchArrayList=docArrayList;
        }

        
        onDoctorsFilterFinishedListener.doctorsFilterList(searchArrayList);

    }

    @Override
    public void addRepToDoctorFilterStart(Rep rep, boolean addOrRemove, OnAddRepToDoctorFilterStartFinishedListener onAddRepToDoctorFilterStartFinishedListener) {
        onAddRepToDoctorFilterStartFinishedListener.repToDoctorFilterStart(rep,addOrRemove);
    }

    @Override
    public void addRepToDoctorFilter(ArrayList<Rep> repArrayListToFilterGlobal, Rep rep, boolean addOrRemove, OnAddRepToDoctorFilterFinishedListener onAddRepToDoctorFilterFinishedListener) {
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
        onAddRepToDoctorFilterFinishedListener.repToDoctorFilter(repArrayListToFilterGlobal);
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
    public void setSelectedFilterRep(ArrayList<Rep> totalRepList, ArrayList<Rep> selectedLocList, OnSetSelectedFilterDoctorFinishedListener onSetSelectedFilterDoctorFinishedListener) {
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
        onSetSelectedFilterDoctorFinishedListener.showSelectedFilterDoctor(newRepList);
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


}


