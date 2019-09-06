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

public class DoctorsPresenterImpli implements DoctorsPresenter,
        DoctorsInteractor.OnGetPendingDoctorFinishedListener,
        DoctorsInteractor.OnGetApprovedDoctorFinishedListener,
        DoctorsInteractor.OnPostApproveRejectStatusFinishedListener,
        DoctorsInteractor.OnGetApproveDoctorFullDetailsFinishedListener,
        DoctorsInteractor.OnGetDoctorPendingFullDetailsFinishedListener,
        DoctorsInteractor.OnsetNavigationFinishedListener,
        DoctorsInteractor.OnGetDoctorFinishedListener,
        DoctorsInteractor.OnSearchDoctorFinishedListener,
        DoctorsInteractor.OnGetLocationFinishedListener,
        DoctorsInteractor.OnSearchLocationFinishedListener,
        DoctorsInteractor.OnSelectedDoctorsFinishedListener,
        DoctorsInteractor.OnAssignLocationToDocStartFinishedListener,
        DoctorsInteractor.OnAssignLocationToDocFinishedListener,
        DoctorsInteractor.OnGetSpecializationFinishedListener,
        DoctorsInteractor.OnSearchSpecializationnFinishedListener,
        DoctorsInteractor.OnAssignSpecializationToDocStartFinishedListener,
        DoctorsInteractor.OnAssignSpecializationToDocFinishedListener,
        DoctorsInteractor.OnPostLocationToDoctorsFinishedListener,
        DoctorsInteractor.OnShowAssignLocationFinishedListener,
        DoctorsInteractor.OnShowAssignSpecFinishedListener,
        DoctorsInteractor.getLocationWithDoctorLocation,
        DoctorsInteractor.getSpecWithDoctorSpec,
        DoctorsInteractor.OnDoctorsFilterFinishedListener,
DoctorsInteractor.OnAddRepToDoctorFilterStartFinishedListener,
        DoctorsInteractor.OnAddRepToDoctorFilterFinishedListener,
        DoctorsInteractor.OnAddRepToFilterFinishedListener,
        DoctorsInteractor.OnSetSelectedFilterDoctorFinishedListener,
        DoctorsInteractor.OnSearchRepForFilterFinishedListener
{


    private DoctorsView doctorsView;
    DoctorsInteractor doctorsInteractor;


    public DoctorsPresenterImpli(DoctorsView doctorsview) {
        this.doctorsView = doctorsview;
        this.doctorsInteractor = new DoctorsInteractorImpil();

    }


    @Override
    public void pendingDoctorsList(ArrayList<Doctor> doctorArrayList) {
        doctorsView.pendingDoctorsList(doctorArrayList);
    }

    @Override
    public void pendingDoctorsEmpty() {
        doctorsView.pendingDoctorsEmpty();
    }

    @Override
    public void pendingDoctorsGetingFail(String failMsg) {
        doctorsView.pendingDoctorsGetingFail(failMsg);
    }

    @Override
    public void pendingDoctorsGetingNetworkFail() {
        doctorsView.pendingDoctorsGetingNetworkFail();
    }

    @Override
    public void pendingDoctorsNameFilterList(ArrayList<String> pendingDoctorsFilter) {
        doctorsView.pendingDoctorsNameFilterList(pendingDoctorsFilter);
    }

    @Override
    public void pendingDoctorsPhoneNumberFilterList(ArrayList<String> pendingDoctorsPhoneNumberFilter) {
        doctorsView.pendingDoctorsPhoneNumberFilterList(pendingDoctorsPhoneNumberFilter);
    }

    @Override
    public void pendingDoctorsRegNumberFilterList(ArrayList<String> pendingDoctorsRegNumberFilter) {
        doctorsView.pendingDoctorsRegNumberFilterList(pendingDoctorsRegNumberFilter);
    }

    @Override
    public void pendingDoctorsRepsFilterList(ArrayList<Rep> pendingDoctorsRepFilter) {
        doctorsView.pendingDoctorsRepsFilterList(pendingDoctorsRepFilter);
    }

    @Override
    public void pendingDoctorsRepNamesFilterList(ArrayList<String> pendingDoctorsRepNamesFilter) {
        doctorsView.pendingDoctorsRepNamesFilterList(pendingDoctorsRepNamesFilter);
    }

    @Override
    public void getPendingDoctor(Context context) {
        doctorsInteractor.getPendingDoctor(context, this);
    }


    @Override
    public void getApprovedDoctor(Context context) {
        doctorsInteractor.getApprovedDoctor(context, this);
    }


    @Override
    public void approvedDoctorsList(ArrayList<Doctor> doctorArrayList) {
        doctorsView.approvedDoctorsList(doctorArrayList);
    }

    @Override
    public void approvedDoctorsEmpty() {
        doctorsView.approvedDoctorsEmpty();
    }

    @Override
    public void approvedDoctorsGetingFail(String failMsg) {
        doctorsView.approvedDoctorsGetingFail(failMsg);
    }

    @Override
    public void approvedDoctorsGetingNetworkFail() {
        doctorsView.approvedDoctorsGetingNetworkFail();
    }


    @Override
    public void approvedDoctorsNameFilterList(ArrayList<String> approvedDoctorsFilter) {
        doctorsView.approvedDoctorsNameFilterList(approvedDoctorsFilter);
    }

    @Override
    public void approvedDoctorsPhoneNumberFilterList(ArrayList<String> approvedDoctorsPhoneNumberFilter) {
        doctorsView.approvedDoctorsPhoneNumberFilterList(approvedDoctorsPhoneNumberFilter);
    }

    @Override
    public void approvedDoctorsRegNumberFilterList(ArrayList<String> approvedDoctorsRegNumberFilter) {
        doctorsView.approvedDoctorsRegNumberFilterList(approvedDoctorsRegNumberFilter);
    }

    @Override
    public void approvedDoctorsRepsFilterList(ArrayList<Rep> approvedDoctorsRepFilter) {
        doctorsView.approvedDoctorsRepsFilterList(approvedDoctorsRepFilter);
    }

    @Override
    public void approvedDoctorsRepNamesFilterList(ArrayList<String> approvedDoctorsRepNamesFilter) {
        doctorsView.approvedDoctorsRepNamesFilterList(approvedDoctorsRepNamesFilter);
    }


    @Override
    public void postApproveRejectStatus(Context context, Doctor doctor, boolean status) {
        doctorsInteractor.postApproveRejectStatus(context, doctor, status, this);
    }

    @Override
    public void approveRejectStatusStart() {
        doctorsView.approveRejectStatusStart();
    }

    @Override
    public void approveRejectDetailsError(String msg) {
        doctorsView.approveRejectDetailsError(msg);
    }

    @Override
    public void approveRejectStatusSuccess() {
        doctorsView.approveRejectStatusSuccess();
    }

    @Override
    public void approveRejectStatusFail(String failMsg, Doctor doc, boolean status) {
        doctorsView.approveRejectStatusFail(failMsg, doc, status);
    }

    @Override
    public void approveRejectStatusNetworkFail() {
        doctorsView.approveRejectStatusNetworkFail();
    }


    @Override
    public void getDoctorPendingFullDetais(Doctor doctor) {
        doctorsInteractor.getDoctorPendingFullDetais(doctor, this);
    }


    @Override
    public void doctorPendingFullDetais(Doctor doctor) {
        doctorsView.doctorPendingFullDetais(doctor);
    }


    @Override
    public void getDoctorApproveFullDetais(Doctor doctor) {
        doctorsInteractor.getDoctorApproveFullDetais(doctor, this);
    }

    @Override
    public void doctorApproveFullDetais(Doctor doctor) {
        doctorsView.doctorApproveFullDetais(doctor);
    }


    @Override
    public void setNavigation() {
        doctorsInteractor.setNavigation(this);
    }


    @Override
    public void navigationItems(ArrayList<Navigation> navigationArrayList) {
        doctorsView.navigationItems(navigationArrayList);
    }


    @Override
    public void getDoctors(Context context) {
        doctorsInteractor.getDoctors(context, this);
    }


    @Override
    public void doctorsList(ArrayList<Doctor> doctorArrayList, ArrayList<String> doctorsNames) {
        doctorsView.doctorsList(doctorArrayList, doctorsNames);
    }

    @Override
    public void doctorsEmpty() {
        doctorsView.doctorsEmpty();
    }

    @Override
    public void doctorsGetingFail(String failMsg) {
        doctorsView.doctorsGetingFail(failMsg);
    }

    @Override
    public void doctorsGetingNetworkFail() {
        doctorsView.doctorsGetingNetworkFail();
    }


    @Override
    public void searchDoctor(ArrayList<Doctor> repArrayList, String doctorName) {
        doctorsInteractor.searchDoctor(repArrayList, doctorName, this);
    }


    @Override
    public void searchDoctorList(ArrayList<Doctor> doctorArrayList) {
        doctorsView.searchDoctorList(doctorArrayList);
    }


    @Override
    public void getLocation(Context context) {
        doctorsInteractor.getLocation(context, this);
    }


    @Override
    public void locationList(ArrayList<LocationEntitie> locationEntitieArrayList, ArrayList<String> locationNameList) {
        doctorsView.locationList(locationEntitieArrayList, locationNameList);
    }

    @Override
    public void locationListEmpty() {
        doctorsView.locationListEmpty();
    }

    @Override
    public void locationFail(String failMsg) {
        doctorsView.locationFail(failMsg);
    }

    @Override
    public void locationNetworkFail() {
        doctorsView.locationNetworkFail();
    }


    @Override
    public void searchLocation(ArrayList<LocationEntitie> locArrayList, String locationName) {
        doctorsInteractor.searchLocation(locArrayList, locationName, this);
    }


    @Override
    public void searchLocationList(ArrayList<LocationEntitie> locationEntitieArrayList) {
        doctorsView.searchLocationList(locationEntitieArrayList);
    }

    @Override
    public void getSelectedDoc(Doctor doc) {
        doctorsInteractor.getSelectedDoc(doc, this);
    }


    @Override
    public void selectedDoc(Doctor doc) {
        doctorsView.selectedDoc(doc);
    }


    @Override
    public void assignLocationToDocStart(LocationEntitie LocationEntitie, boolean addOrRemove) {
        doctorsInteractor.assignLocationToDocStart(LocationEntitie, addOrRemove, this);
    }

    @Override
    public void locationToDocStart(LocationEntitie LocationEntitie, boolean addOrRemove) {
        doctorsView.locationToDocStart(LocationEntitie, addOrRemove);
    }


    @Override
    public void assignLocationToDoc(ArrayList<LocationEntitie> locationEntitieListGlobal, LocationEntitie loc, boolean addOrRemove) {
        doctorsInteractor.assignLocationToDoc(locationEntitieListGlobal, loc, addOrRemove, this);
    }


    @Override
    public void locationToDoc(ArrayList<LocationEntitie> locationEntitieArrayList) {
        doctorsView.locationToDoc(locationEntitieArrayList);
    }


    @Override
    public void getSpecialization(Context context) {
        doctorsInteractor.getSpecialization(context, this);
    }


    @Override
    public void specializationList(ArrayList<Specialization> specArrayList, ArrayList<String> specNameList) {
        doctorsView.specializationList(specArrayList, specNameList);
    }

    @Override
    public void specializationEmpty() {
        doctorsView.specializationEmpty();
    }

    @Override
    public void specializationFail(String failMsg) {
        doctorsView.specializationFail(failMsg);
    }

    @Override
    public void specializationNetworkFail() {
        doctorsView.specializationNetworkFail();
    }


    @Override
    public void searchSpecialization(ArrayList<Specialization> specArrayList, String specializationName) {
        doctorsInteractor.searchSpecialization(specArrayList, specializationName, this);
    }

    @Override
    public void searchSpecializationList(ArrayList<Specialization> specializationArrayList) {
        doctorsView.searchSpecializationList(specializationArrayList);
    }


    @Override
    public void assignSpecializationToDocStart(Specialization specialization, boolean addOrRemove) {
        doctorsInteractor.assignSpecializationToDocStart(specialization, addOrRemove, this);
    }


    @Override
    public void specializationToDocStart(Specialization specialization, boolean addOrRemove) {
        doctorsView.specializationToDocStart(specialization, addOrRemove);
    }


    @Override
    public void assignSpecializationToDoc(ArrayList<Specialization> specializationListGlobal, Specialization spec, boolean addOrRemove) {
        doctorsInteractor.assignSpecializationToDoc(specializationListGlobal, spec, addOrRemove, this);
    }


    @Override
    public void specializationToDoc(ArrayList<Specialization> specializationArrayList) {
        doctorsView.specializationToDoc(specializationArrayList);
    }


    @Override
    public void postLocationToDoctors(Context context, int docid, ArrayList<LocationEntitie> locationsArrayList, ArrayList<Specialization> specializationArrayList) {
        doctorsInteractor.postLocationToDoctors(context, docid, locationsArrayList, specializationArrayList, this);
    }


    @Override
    public void postLocationToDoctorsSuccessful() {
        doctorsView.postLocationToDoctorsSuccessful();
    }

    @Override
    public void postLocationToDoctorsEmpty(String msg) {
        doctorsView.postLocationToDoctorsEmpty(msg);
    }

    @Override
    public void postLocationToDoctorsFail(String failMsg, int docid, ArrayList<LocationEntitie> productArrayList, ArrayList<Specialization> specializationArrayList) {
        doctorsView.postLocationToDoctorsFail(failMsg, docid, productArrayList, specializationArrayList);
    }

    @Override
    public void postLocationToDoctorsNetworkFail() {
        doctorsView.postLocationToDoctorsNetworkFail();
    }


    @Override
    public void showAssignLocation(ArrayList<LocationEntitie> addedlocationListGlobal, ArrayList<LocationEntitie> alllocationList) {
        doctorsInteractor.showAssignLocation(addedlocationListGlobal, alllocationList, this);
    }


    @Override
    public void assignLocation(ArrayList<LocationEntitie> locationEntitieArrayList) {
        doctorsView.assignLocation(locationEntitieArrayList);
    }


    @Override
    public void showAssignSpec(ArrayList<Specialization> specializationListGlobal, ArrayList<Specialization> allSpecList) {
        doctorsInteractor.showAssignSpec(specializationListGlobal, allSpecList, this);

    }


    @Override
    public void assignSpec(ArrayList<Specialization> specializationArrayList) {
        doctorsView.assignSpec(specializationArrayList);
    }


    @Override
    public void getLocationWithDoctorLocation(ArrayList<LocationEntitie> allLocationEntitieArrayList, ArrayList<LocationEntitie> doctorLocationEntitieArrayList) {
        doctorsInteractor.getLocationWithDoctorLocation(allLocationEntitieArrayList, doctorLocationEntitieArrayList, this);
    }


    @Override
    public void locationListWithDoctorLocation(ArrayList<LocationEntitie> allLocationEntitieArrayList, ArrayList<LocationEntitie> doctorLocationEntitieArrayList) {
        doctorsView.locationListWithDoctorLocation(allLocationEntitieArrayList, doctorLocationEntitieArrayList);
    }


    @Override
    public void getSpecWithDoctorSpec(ArrayList<Specialization> allSpecArrayList, ArrayList<Specialization> doctorSpecArrayList) {
        doctorsInteractor.getSpecWithDoctorSpec(allSpecArrayList, doctorSpecArrayList, this);
    }

    @Override
    public void specListWithDoctorSpec(ArrayList<Specialization> allSpecArrayList, ArrayList<Specialization> doctorSpecArrayList) {
        doctorsView.specListWithDoctorSpec(allSpecArrayList, doctorSpecArrayList);
    }


    @Override
    public void doctorsFilter(ArrayList<Doctor> docArrayList, String docName, String docTPName, String docRegName,ArrayList<Rep> repList) {
        doctorsInteractor.doctorsFilter(docArrayList, docName, docTPName, docRegName, repList,this);
    }



    @Override
    public void doctorsFilterListEmpty(String msg, ArrayList<Doctor> docArrayList) {
        doctorsView.doctorsFilterListEmpty(msg, docArrayList);
    }

    @Override
    public void doctorsFilterList(ArrayList<Doctor> docArrayList) {
        doctorsView.doctorsFilterList(docArrayList);
    }




    @Override
    public void addRepToDoctorFilterStart(Rep rep, boolean addOrRemove) {
        doctorsInteractor.addRepToDoctorFilterStart(rep, addOrRemove, this);
    }

    @Override
    public void repToDoctorFilterStart(Rep rep, boolean addOrRemove) {
        doctorsView.repToDoctorFilterStart(rep,addOrRemove);
    }



    @Override
    public void addRepToDoctorFilter(ArrayList<Rep> repArrayListToFilterGlobal, Rep rep, boolean addOrRemove) {
        doctorsInteractor.addRepToDoctorFilter(repArrayListToFilterGlobal, rep, addOrRemove, this);
    }

    @Override
    public void repToDoctorFilter(ArrayList<Rep> repArrayListToFilter) {
        doctorsView.repToDoctorFilter(repArrayListToFilter);
    }



    @Override
    public void showAddRepFilter(ArrayList<Rep> addedRepListGlobal, ArrayList<Rep> allFilterRepList) {
        doctorsInteractor.showAddRepFilter(addedRepListGlobal, allFilterRepList, this);
    }

    @Override
    public void addRepToFilter(ArrayList<Rep> addedArrayListToFilter) {
        doctorsView.addRepToFilter(addedArrayListToFilter);
    }





    @Override
    public void setSelectedFilterRep(ArrayList<Rep> totalRepList, ArrayList<Rep> selectedLocList) {
        doctorsInteractor.setSelectedFilterRep(totalRepList, selectedLocList, this);
    }

    @Override
    public void showSelectedFilterDoctor(ArrayList<Rep> filterList) {
        doctorsView.showSelectedFilterDoctor(filterList);
    }



    @Override
    public void searchRepForFilter(ArrayList<Rep> repArrayList, String repName) {
        doctorsInteractor.searchRepForFilter(repArrayList, repName, this);
    }


    @Override
    public void repListForFilter(ArrayList<Rep> repArrayList) {
        doctorsView.repListForFilter(repArrayList);
    }
}
