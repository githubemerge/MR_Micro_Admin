package emerge.project.mrsolution_admin_micro.ui.activity.doctors;


import android.content.Context;

import java.util.ArrayList;

import emerge.project.mrsolution_admin_micro.utils.entittes.Doctor;
import emerge.project.mrsolution_admin_micro.utils.entittes.LocationEntitie;
import emerge.project.mrsolution_admin_micro.utils.entittes.Rep;
import emerge.project.mrsolution_admin_micro.utils.entittes.Specialization;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface DoctorsPresenter {



    void setNavigation( );
    void getPendingDoctor(Context context);
    void getApprovedDoctor(Context context);
    void postApproveRejectStatus(Context context,Doctor doctor,boolean status);
    void getDoctorApproveFullDetais(Doctor doctor);
    void getDoctorPendingFullDetais(Doctor doctor);
    void getDoctors(Context context);
    void searchDoctor(ArrayList<Doctor> repArrayList, String doctorName);
    void getLocation(Context context);
    void searchLocation(ArrayList<LocationEntitie> locArrayList, String locationName);
    void getSelectedDoc(Doctor doc);
    void assignLocationToDocStart(LocationEntitie LocationEntitie, boolean addOrRemove);
    void assignLocationToDoc(ArrayList<LocationEntitie> locationEntitieListGlobal, LocationEntitie loc, boolean addOrRemove);
    void getSpecialization(Context context);
    void searchSpecialization(ArrayList<Specialization> specArrayList, String specializationName);
    void assignSpecializationToDocStart(Specialization specialization,boolean addOrRemove);
    void assignSpecializationToDoc(ArrayList<Specialization> specializationListGlobal,Specialization spec,boolean addOrRemove);
    void postLocationToDoctors(Context context, int docid, ArrayList<LocationEntitie> locationsArrayList, ArrayList<Specialization> specializationArrayList);

    void showAssignLocation(ArrayList<LocationEntitie> addedlocationListGlobal, ArrayList<LocationEntitie> alllocationList);

    void showAssignSpec(ArrayList<Specialization> specializationListGlobal,ArrayList<Specialization> allSpecList);

    void getLocationWithDoctorLocation(ArrayList<LocationEntitie> allLocationEntitieArrayList, ArrayList<LocationEntitie> doctorLocationEntitieArrayList);

    void getSpecWithDoctorSpec(ArrayList<Specialization> allSpecArrayList,ArrayList<Specialization> doctorSpecArrayList );




    void doctorsFilter(ArrayList<Doctor> docArrayList,String docName,String docTPName,String docRegName,ArrayList<Rep> repList);



    void addRepToDoctorFilterStart(Rep rep, boolean addOrRemove  );

    void addRepToDoctorFilter(ArrayList<Rep> repArrayListToFilterGlobal,Rep rep,boolean addOrRemove  );


    void showAddRepFilter(ArrayList<Rep> addedRepListGlobal,ArrayList<Rep> allFilterRepList   );


    void setSelectedFilterRep(ArrayList<Rep> totalRepList,ArrayList<Rep> selectedLocList );

    void searchRepForFilter(ArrayList<Rep> repArrayList,String repName);




}
