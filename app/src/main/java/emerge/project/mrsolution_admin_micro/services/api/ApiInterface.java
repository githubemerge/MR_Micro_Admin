package emerge.project.mrsolution_admin_micro.services.api;



import com.google.gson.JsonObject;

import java.util.ArrayList;

import emerge.project.mrsolution_admin_micro.utils.entittes.District;
import emerge.project.mrsolution_admin_micro.utils.entittes.Doctor;
import emerge.project.mrsolution_admin_micro.utils.entittes.LocationEntitie;
import emerge.project.mrsolution_admin_micro.utils.entittes.LoginUser;
import emerge.project.mrsolution_admin_micro.utils.entittes.Principles;
import emerge.project.mrsolution_admin_micro.utils.entittes.Products;
import emerge.project.mrsolution_admin_micro.utils.entittes.Rep;
import emerge.project.mrsolution_admin_micro.utils.entittes.Specialization;
import emerge.project.mrsolution_admin_micro.utils.entittes.Visit;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

   /* @GET("User/ValidateUser")
    Call<LoginUser> validateUser(@Query("username") String username, @Query("password") String password, @Query("usertypeID") int usertypeID, @Query("pushtokenid") String pushtokenid);
*/

  /*  @GET("principles/GetTargetAchievement")
    Observable<UserTest> validateUser(@Query("mrid") int username);
*/

      @GET("User/ValidateUser")
    Observable<LoginUser> validateUser(@Query("username") String username, @Query("password") String password, @Query("usertypeID") int usertypeID, @Query("pushtokenid") String pushtokenid);

    @GET("User/GetMRByAdmin")
    Observable<ArrayList<Rep>> getMRByAdmin(@Query("adminID") int adminID);


    @GET("User/GetMRByAdminProductsAssigned")
    Observable<ArrayList<Rep>> getMRByAdminProductsAssigned(@Query("AdminID") int adminID);



    @GET("Location/GetLocationsApprovedByAdmin")
    Observable<ArrayList<LocationEntitie>> getLocationsApprovedByAdmin(@Query("TokenID") String tokenID, @Query("AdminID") int adminID);

    @GET("Location/GetLocationsUnapprovedByAdmin")
    Observable<ArrayList<LocationEntitie>> getLocationsUnapprovedByAdmin(@Query("TokenID") String tokenID, @Query("AdminID") int adminID);

    @POST("Location/UpdateLocation")
    Observable<JsonObject> updateLocation(@Body JsonObject locationInfo);



    @GET("District/GetAllDistricts")
    Observable<ArrayList<District>> getAllDistricts(@Query("TokenID") String tokenID);




    @GET("Principles/GetAllPrinciples")
    Observable<ArrayList<Principles>> getAllPrinciples(@Query("adminID") int adminID);




    @GET("Product/GetAllProductsForPrinciple")
    Observable<ArrayList<Products>> getAllProductsForPrinciple(@Query("PrincipleID") int principleID);



    @GET("Doctor/GetUnapprovedDoctors")
    Observable<ArrayList<Doctor>> getUnapprovedDoctors(@Query("TokenID") String tokenID, @Query("AdminID") int adminID);

    @POST("Doctor/UpdateDoctor")
    Observable<JsonObject> updateDoctor(@Body JsonObject doctorInfo);



    @GET("Doctor/GetApprovedDoctorsByAdmin")
    Observable<ArrayList<Doctor>> getApprovedDoctorsByAdmin(@Query("TokenID") String tokenID, @Query("AdminID") int adminID);




    @GET("Visit/GetAllVisitsByAdmin")
    Observable<ArrayList<Visit>> getAllVisitsByAdmin(@Query("AdminID") int adminID, @Query("MRID") int mrID, @Query("LocationID") int LocationID, @Query("DoctorID") int DoctorID, @Query("Date") String Date);



    @POST("User/SaveRepProduct")
    Observable<Boolean> assignRepProduct(@Body JsonObject doctorInfo);


    @GET("Specialization/GetApprovedSpecializations")
    Observable<ArrayList<Specialization>> getApprovedSpecializations(@Query("TokenID") String tokenID);


    @POST("Doctor/SaveDoctorDetails")
    Observable<Boolean> assignLocAndSpecToDoctors(@Body JsonObject doctorInfo);




}
