package emerge.project.mrsolution_admin_micro.ui.activity.doctors;


import android.content.Context;

import java.util.ArrayList;

import emerge.project.mrsolution_admin_micro.utils.entittes.Doctor;
import emerge.project.mrsolution_admin_micro.utils.entittes.LocationEntitie;
import emerge.project.mrsolution_admin_micro.utils.entittes.Navigation;
import emerge.project.mrsolution_admin_micro.utils.entittes.Rep;
import emerge.project.mrsolution_admin_micro.utils.entittes.Specialization;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface DoctorsInteractor {


    interface OnsetNavigationFinishedListener {
        void navigationItems(ArrayList<Navigation> navigationArrayList);
    }
    void setNavigation(OnsetNavigationFinishedListener onsetNavigationFinishedListener);



    interface OnGetPendingDoctorFinishedListener {
        void pendingDoctorsList(ArrayList<Doctor> doctorArrayList);
        void pendingDoctorsEmpty();
        void pendingDoctorsGetingFail(String failMsg);
        void pendingDoctorsGetingNetworkFail();


        void pendingDoctorsNameFilterList( ArrayList<String> pendingDoctorsFilter);
        void pendingDoctorsPhoneNumberFilterList( ArrayList<String> pendingDoctorsPhoneNumberFilter);
        void pendingDoctorsRegNumberFilterList( ArrayList<String> pendingDoctorsRegNumberFilter);

        void pendingDoctorsRepsFilterList( ArrayList<Rep> pendingDoctorsRepFilter);
        void pendingDoctorsRepNamesFilterList( ArrayList<String> pendingDoctorsRepNamesFilter);


    }
    void getPendingDoctor(Context context, OnGetPendingDoctorFinishedListener onGetPendingDoctorFinishedListener);




    interface OnGetApprovedDoctorFinishedListener {
        void approvedDoctorsList(ArrayList<Doctor> doctorArrayList);
        void approvedDoctorsEmpty();
        void approvedDoctorsGetingFail(String failMsg);
        void approvedDoctorsGetingNetworkFail();



        void approvedDoctorsNameFilterList( ArrayList<String> approvedDoctorsFilter);
        void approvedDoctorsPhoneNumberFilterList( ArrayList<String> approvedDoctorsPhoneNumberFilter);
        void approvedDoctorsRegNumberFilterList( ArrayList<String> approvedDoctorsRegNumberFilter);

        void approvedDoctorsRepsFilterList( ArrayList<Rep> approvedDoctorsRepFilter);
        void approvedDoctorsRepNamesFilterList( ArrayList<String> approvedDoctorsRepNamesFilter);

    }
    void getApprovedDoctor(Context context, OnGetApprovedDoctorFinishedListener onGetApprovedDoctorFinishedListener);

    interface OnPostApproveRejectStatusFinishedListener {
        void  approveRejectStatusStart();
        void  approveRejectDetailsError(String msg);
        void  approveRejectStatusSuccess();
        void  approveRejectStatusFail(String failMsg,Doctor doc,boolean status);
        void  approveRejectStatusNetworkFail();
    }
    void postApproveRejectStatus(Context context,Doctor doctor,boolean status, OnPostApproveRejectStatusFinishedListener onPostApproveRejectStatusFinishedListener);



    interface OnGetApproveDoctorFullDetailsFinishedListener {
        void  doctorApproveFullDetais(Doctor doctor);
    }
    void getDoctorApproveFullDetais(Doctor doctor,OnGetApproveDoctorFullDetailsFinishedListener onGetApproveDoctorFullDetailsFinishedListener);



    interface OnGetDoctorPendingFullDetailsFinishedListener {
        void  doctorPendingFullDetais(Doctor doctor);
    }
    void getDoctorPendingFullDetais(Doctor doctor,OnGetDoctorPendingFullDetailsFinishedListener onGetDoctorPendingFullDetailsFinishedListener);




    interface OnGetDoctorFinishedListener {
        void doctorsList(ArrayList<Doctor> doctorArrayList,ArrayList<String> doctorsNames);
        void doctorsEmpty();
        void doctorsGetingFail(String failMsg);
        void doctorsGetingNetworkFail();
    }
    void getDoctors(Context context, OnGetDoctorFinishedListener onGetDoctorFinishedListener);



    interface OnSearchDoctorFinishedListener {
        void searchDoctorList(ArrayList<Doctor> doctorArrayList);
    }
    void searchDoctor(ArrayList<Doctor> repArrayList,String doctorName, OnSearchDoctorFinishedListener onSearchDoctorFinishedListener);


    interface OnGetLocationFinishedListener {
        void locationList(ArrayList<LocationEntitie> locationEntitieArrayList, ArrayList<String> locationNameList);
        void locationListEmpty();
        void locationFail(String failMsg);
        void locationNetworkFail();
    }
    void getLocation(Context context,OnGetLocationFinishedListener onGetLocationFinishedListener);


    interface OnSearchLocationFinishedListener {
        void searchLocationList(ArrayList<LocationEntitie> locationEntitieArrayList);
    }
    void searchLocation(ArrayList<LocationEntitie> locArrayList, String locationName, OnSearchLocationFinishedListener onSearchLocationFinishedListener);


    interface OnSelectedDoctorsFinishedListener {
        void selectedDoc(Doctor doc);
    }
    void getSelectedDoc(Doctor doc, OnSelectedDoctorsFinishedListener onSelectedDoctorsFinishedListener);



    interface OnAssignLocationToDocStartFinishedListener {
        void locationToDocStart(LocationEntitie LocationEntitie, boolean addOrRemove);
    }
    void assignLocationToDocStart(LocationEntitie LocationEntitie, boolean addOrRemove, OnAssignLocationToDocStartFinishedListener onAssignLocationToDocStartFinishedListener);


    interface OnAssignLocationToDocFinishedListener {
        void locationToDoc(ArrayList<LocationEntitie> locationEntitieArrayList);
    }
    void assignLocationToDoc(ArrayList<LocationEntitie> locationEntitieListGlobal, LocationEntitie loc, boolean addOrRemove, OnAssignLocationToDocFinishedListener onAssignLocationToDocFinishedListener);



    interface OnShowAssignLocationFinishedListener {
        void assignLocation(ArrayList<LocationEntitie> locationEntitieArrayList);
    }
    void showAssignLocation(ArrayList<LocationEntitie> addedlocationListGlobal, ArrayList<LocationEntitie> alllocationList, OnShowAssignLocationFinishedListener onShowAssignLocationFinishedListener );


    interface OnGetSpecializationFinishedListener {
        void specializationList(ArrayList<Specialization> specArrayList, ArrayList<String> specNameList);
        void specializationEmpty();
        void specializationFail(String failMsg);
        void specializationNetworkFail();
    }
    void getSpecialization(Context context, OnGetSpecializationFinishedListener onGetSpecializationFinishedListener);


    interface OnSearchSpecializationnFinishedListener {
        void searchSpecializationList(ArrayList<Specialization> specializationArrayList);
    }
    void searchSpecialization(ArrayList<Specialization> specArrayList,String specializationName, OnSearchSpecializationnFinishedListener onSearchSpecializationnFinishedListener);



    interface OnAssignSpecializationToDocStartFinishedListener {
        void specializationToDocStart(Specialization specialization,boolean addOrRemove);
    }
    void assignSpecializationToDocStart(Specialization specialization,boolean addOrRemove, OnAssignSpecializationToDocStartFinishedListener onAssignSpecializationToDocStartFinishedListener);



    interface OnAssignSpecializationToDocFinishedListener {
        void specializationToDoc(ArrayList<Specialization> specializationArrayList);
    }
    void assignSpecializationToDoc(ArrayList<Specialization> specializationListGlobal,Specialization spec,boolean addOrRemove, OnAssignSpecializationToDocFinishedListener onAssignSpecializationToDocFinishedListener);




    interface OnShowAssignSpecFinishedListener {
        void assignSpec(ArrayList<Specialization> specializationArrayList);
    }
    void showAssignSpec(ArrayList<Specialization> specializationListGlobal,ArrayList<Specialization> allSpecList, OnShowAssignSpecFinishedListener onShowAssignSpecFinishedListener  );



    interface OnPostLocationToDoctorsFinishedListener {
        void postLocationToDoctorsSuccessful();
        void postLocationToDoctorsEmpty(String msg);
        void postLocationToDoctorsFail(String failMsg, int docid, ArrayList<LocationEntitie> productArrayList, ArrayList<Specialization> specializationArrayList);
        void postLocationToDoctorsNetworkFail();
    }
    void postLocationToDoctors(Context context, int docid, ArrayList<LocationEntitie> locationsArrayList, ArrayList<Specialization> specializationArrayList, OnPostLocationToDoctorsFinishedListener onPostLocationToDoctorsFinishedListener);




    interface getLocationWithDoctorLocation {
        void locationListWithDoctorLocation(ArrayList<LocationEntitie> allLocationEntitieArrayList, ArrayList<LocationEntitie> doctorLocationEntitieArrayList);
    }
    void getLocationWithDoctorLocation(ArrayList<LocationEntitie> allLocationEntitieArrayList, ArrayList<LocationEntitie> doctorLocationEntitieArrayList, getLocationWithDoctorLocation getLocationWithDoctorLocation);




    interface getSpecWithDoctorSpec {
        void specListWithDoctorSpec(ArrayList<Specialization> allSpecArrayList,ArrayList<Specialization> doctorSpecArrayList);
    }
    void getSpecWithDoctorSpec(ArrayList<Specialization> allSpecArrayList,ArrayList<Specialization> doctorSpecArrayList ,getSpecWithDoctorSpec getSpecWithDoctorSpec);



    interface OnDoctorsFilterFinishedListener {
        void doctorsFilterListEmpty(String msg,ArrayList<Doctor> docArrayList);
        void doctorsFilterList(ArrayList<Doctor> docArrayList);
    }
    void doctorsFilter(ArrayList<Doctor> docArrayList,String docName,String docTPName,String docRegName,ArrayList<Rep> repList,OnDoctorsFilterFinishedListener onDoctorsFilterFinishedListener );



    interface OnAddRepToDoctorFilterStartFinishedListener {
        void repToDoctorFilterStart(Rep rep,boolean addOrRemove);
    }
    void addRepToDoctorFilterStart(Rep rep,boolean addOrRemove, OnAddRepToDoctorFilterStartFinishedListener onAddRepToDoctorFilterStartFinishedListener  );

    interface OnAddRepToDoctorFilterFinishedListener {
        void repToDoctorFilter(ArrayList<Rep> repArrayListToFilter);
    }
    void addRepToDoctorFilter(ArrayList<Rep> repArrayListToFilterGlobal,Rep rep,boolean addOrRemove, OnAddRepToDoctorFilterFinishedListener onAddRepToDoctorFilterFinishedListener  );

    interface OnAddRepToFilterFinishedListener {
        void addRepToFilter(ArrayList<Rep> addedArrayListToFilter);
    }
    void showAddRepFilter(ArrayList<Rep> addedRepListGlobal,ArrayList<Rep> allFilterRepList,OnAddRepToFilterFinishedListener onAddRepToFilterFinishedListener  );

    interface OnSetSelectedFilterDoctorFinishedListener {
        void showSelectedFilterDoctor(ArrayList<Rep> filterList);
    }
    void setSelectedFilterRep(ArrayList<Rep> totalRepList,ArrayList<Rep> selectedLocList,  OnSetSelectedFilterDoctorFinishedListener onSetSelectedFilterDoctorFinishedListener );


    interface OnSearchRepForFilterFinishedListener {
        void repListForFilter(ArrayList<Rep> repArrayList);
    }
    void searchRepForFilter(ArrayList<Rep> repArrayList,String repName, OnSearchRepForFilterFinishedListener  onSearchRepForFilterFinishedListener);





}
