package emerge.project.mrsolution_admin_micro.ui.activity.doctors;


import java.util.ArrayList;

import emerge.project.mrsolution_admin_micro.utils.entittes.Doctor;
import emerge.project.mrsolution_admin_micro.utils.entittes.LocationEntitie;
import emerge.project.mrsolution_admin_micro.utils.entittes.Navigation;
import emerge.project.mrsolution_admin_micro.utils.entittes.Rep;
import emerge.project.mrsolution_admin_micro.utils.entittes.Specialization;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface DoctorsView {


    void navigationItems(ArrayList<Navigation> navigationArrayList);


    void pendingDoctorsList(ArrayList<Doctor> doctorArrayList);
    void pendingDoctorsEmpty();
    void pendingDoctorsGetingFail(String failMsg);
    void pendingDoctorsGetingNetworkFail();

    void pendingDoctorsNameFilterList( ArrayList<String> pendingDoctorsFilter);
    void pendingDoctorsPhoneNumberFilterList( ArrayList<String> pendingDoctorsPhoneNumberFilter);
    void pendingDoctorsRegNumberFilterList( ArrayList<String> pendingDoctorsRegNumberFilter);

    void pendingDoctorsRepsFilterList( ArrayList<Rep> pendingDoctorsRepFilter);
    void pendingDoctorsRepNamesFilterList( ArrayList<String> pendingDoctorsRepNamesFilter);





    void approvedDoctorsList(ArrayList<Doctor> doctorArrayList);
    void approvedDoctorsEmpty();
    void approvedDoctorsGetingFail(String failMsg);
    void approvedDoctorsGetingNetworkFail();

    void approvedDoctorsNameFilterList( ArrayList<String> approvedDoctorsFilter);
    void approvedDoctorsPhoneNumberFilterList( ArrayList<String> approvedDoctorsPhoneNumberFilter);
    void approvedDoctorsRegNumberFilterList( ArrayList<String> approvedDoctorsRegNumberFilter);

    void approvedDoctorsRepsFilterList( ArrayList<Rep> approvedDoctorsRepFilter);
    void approvedDoctorsRepNamesFilterList( ArrayList<String> approvedDoctorsRepNamesFilter);





    void approveRejectStatusStart();

    void approveRejectDetailsError(String msg);

    void approveRejectStatusSuccess();

    void approveRejectStatusFail(String failMsg, Doctor doc, boolean status);

    void approveRejectStatusNetworkFail();

    void doctorApproveFullDetais(Doctor doctor);

    void doctorPendingFullDetais(Doctor doctor);

    void doctorsList(ArrayList<Doctor> doctorArrayList, ArrayList<String> doctorsNames);

    void doctorsEmpty();

    void doctorsGetingFail(String failMsg);

    void doctorsGetingNetworkFail();

    void searchDoctorList(ArrayList<Doctor> doctorArrayList);

    void locationList(ArrayList<LocationEntitie> locationEntitieArrayList, ArrayList<String> locationNameList);

    void locationListEmpty();

    void locationFail(String failMsg);

    void locationNetworkFail();




    void searchLocationList(ArrayList<LocationEntitie> locationEntitieArrayList);

    void selectedDoc(Doctor doc);

    void locationToDocStart(LocationEntitie LocationEntitie, boolean addOrRemove);

    void locationToDoc(ArrayList<LocationEntitie> locationEntitieArrayList);


    void specializationList(ArrayList<Specialization> specArrayList, ArrayList<String> specNameList);

    void specializationEmpty();

    void specializationFail(String failMsg);

    void specializationNetworkFail();

    void searchSpecializationList(ArrayList<Specialization> specializationArrayList);

    void specializationToDocStart(Specialization specialization, boolean addOrRemove);


    void specializationToDoc(ArrayList<Specialization> specializationArrayList);


    void postLocationToDoctorsSuccessful();

    void postLocationToDoctorsEmpty(String msg);

    void postLocationToDoctorsFail(String failMsg, int docid, ArrayList<LocationEntitie> productArrayList, ArrayList<Specialization> specializationArrayList);

    void postLocationToDoctorsNetworkFail();


    void assignLocation(ArrayList<LocationEntitie> locationEntitieArrayList);


    void assignSpec(ArrayList<Specialization> specializationArrayList);


    void locationListWithDoctorLocation(ArrayList<LocationEntitie> allLocationEntitieArrayList, ArrayList<LocationEntitie> doctorLocationEntitieArrayList);


    void specListWithDoctorSpec(ArrayList<Specialization> allSpecArrayList,ArrayList<Specialization> doctorSpecArrayList);




    void doctorsFilterListEmpty(String msg,ArrayList<Doctor> docArrayList);
    void doctorsFilterList(ArrayList<Doctor> docArrayList);



    void repToDoctorFilterStart(Rep rep,boolean addOrRemove);
    void repToDoctorFilter(ArrayList<Rep> repArrayListToFilter);
    void addRepToFilter(ArrayList<Rep> addedArrayListToFilter);
    void showSelectedFilterDoctor(ArrayList<Rep> filterList);

    void repListForFilter(ArrayList<Rep> repArrayList);





}
