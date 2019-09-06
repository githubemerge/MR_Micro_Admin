package emerge.project.mrsolution_admin_micro.ui.activity.visits;


import android.content.Context;

import com.pddstudio.preferences.encrypted.EncryptedPreferences;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import emerge.project.mrsolution_admin_micro.BuildConfig;
import emerge.project.mrsolution_admin_micro.services.api.ApiClient;
import emerge.project.mrsolution_admin_micro.services.api.ApiInterface;
import emerge.project.mrsolution_admin_micro.services.network.NetworkAvailability;
import emerge.project.mrsolution_admin_micro.utils.entittes.Doctor;
import emerge.project.mrsolution_admin_micro.utils.entittes.Navigation;
import emerge.project.mrsolution_admin_micro.utils.entittes.Products;
import emerge.project.mrsolution_admin_micro.utils.entittes.Rep;
import emerge.project.mrsolution_admin_micro.utils.entittes.Visit;
import emerge.project.mrsolution_admin_micro.utils.entittes.VisitProducts;
import emerge.project.mrsolution_admin_micro.utils.entittes.LocationEntitie;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Himanshu on 4/5/2017.
 */

public class VisitsInteractorImpil implements VisitsInteractor {

    public static final String tokenID = BuildConfig.API_TOKEN_ID;

    EncryptedPreferences encryptedPreferences;
    private static final String USER_ID = "userID";
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


    ArrayList<Visit> visitList;

    @Override
    public void setNavigation(OnsetNavigationFinishedListener onsetNavigationFinishedListener) {

        ArrayList<Navigation> navigationItems = new ArrayList<Navigation>();
        // navigationItems.add(new Navigation("Location Assing", R.drawable.ic_product_defult_small));

        onsetNavigationFinishedListener.navigationItems(navigationItems);
    }

    @Override
    public void getVisits(Context context, final int mrID, final int locationID, final int doctorID, final String date, final OnGetVisitsFinishedListener onGetVisitsFinishedListener) {

        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onGetVisitsFinishedListener.visitsListNetworkFail();
        } else {
            try {
                encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
                int userId = encryptedPreferences.getInt(USER_ID, 0);

                final ArrayList<Visit> visitItems = new ArrayList<Visit>();

                final ArrayList<Doctor> docListForFilter = new ArrayList<Doctor>();
                final ArrayList<LocationEntitie> locListForFilter = new ArrayList<LocationEntitie>();
                final ArrayList<Products> productsListForFilter = new ArrayList<Products>();
                final ArrayList<Rep> repListForFilter = new ArrayList<Rep>();


                final ArrayList<String> docNameListForFilter = new ArrayList<String>();
                final ArrayList<String> locNameListForFilter = new ArrayList<String>();
                final ArrayList<String> proNameListForFilter = new ArrayList<String>();
                final ArrayList<String> repNameListForFilter = new ArrayList<String>();


                apiService.getAllVisitsByAdmin(userId, mrID, locationID, doctorID, date)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<Visit>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(ArrayList<Visit> visit) {
                                visitList = visit;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onGetVisitsFinishedListener.visitsListFail("Something went wrong, Please try again", mrID, locationID, doctorID, date);
                            }

                            @Override
                            public void onComplete() {
                                if (visitList.isEmpty()) {
                                    onGetVisitsFinishedListener.visitsListNoItems();
                                } else {
                                    for (Visit visit : visitList) {
                                        ArrayList<VisitProducts> visitProductsList = new ArrayList<VisitProducts>();
                                        for (VisitProducts visitProducts : visit.getVisitsProduct()) {
                                            visitProductsList.add(new VisitProducts(visitProducts.getId(), visitProducts.getProductID(), visitProducts.getVisitID(), visitProducts.getProductName(),
                                                    visitProducts.getImageUrl()));

                                        }
                                        visitItems.add(new Visit(visit.getId(), visit.getCode(), visit.getVisitNumber(), visit.getLocationID(), visit.getLocation(), visit.getDoctorID(), visit.getDoctorName(),
                                                visit.getRepID(), visit.getRepName(), visit.getComment(), visit.getVisitDate(), visit.getLatitude(),
                                                visit.getLongitude(), visit.getVisitsProduct(), false, visit.getImageUrl()));
                                    }

                                    onGetVisitsFinishedListener.visitsList(visitItems);


                                    for (Visit visit : visitList) {

                                        if (docListForFilter.isEmpty()) {
                                            docListForFilter.add(new Doctor(visit.getDoctorID(), visit.getDoctorName(), false));
                                            docNameListForFilter.add(visit.getDoctorName());
                                        } else {
                                            boolean status = false;
                                            for (Doctor d : docListForFilter) {
                                                if (d.getId() == visit.getDoctorID()) {
                                                    status = true;
                                                    break;
                                                } else {
                                                }
                                            }
                                            if (!status) {
                                                docListForFilter.add(new Doctor(visit.getDoctorID(), visit.getDoctorName(), false));
                                                docNameListForFilter.add(visit.getDoctorName());
                                            } else {
                                            }
                                        }


                                        if (locListForFilter.isEmpty()) {
                                            locListForFilter.add(new LocationEntitie(visit.getLocationID(), visit.getLocation(), false));
                                            locNameListForFilter.add(visit.getLocation());
                                        } else {
                                            boolean status = false;
                                            for (LocationEntitie loc : locListForFilter) {
                                                if (loc.getId() == visit.getLocationID()) {
                                                    status = true;
                                                    break;
                                                } else {
                                                }
                                            }
                                            if (!status) {
                                                locListForFilter.add(new LocationEntitie(visit.getLocationID(), visit.getLocation(), false));
                                                locNameListForFilter.add(visit.getLocation());
                                            } else {
                                            }
                                        }


                                        for (VisitProducts visitProducts : visit.getVisitsProduct()) {
                                            if (productsListForFilter.isEmpty()) {
                                                productsListForFilter.add(new Products(visitProducts.getProductID(), visitProducts.getProductName(), false));
                                                proNameListForFilter.add(visitProducts.getProductName());
                                            } else {
                                                boolean status = false;
                                                for (Products pro : productsListForFilter) {
                                                    if (pro.getId() == visitProducts.getProductID()) {
                                                        status = true;
                                                        break;
                                                    } else {
                                                    }
                                                }
                                                if (!status) {
                                                    productsListForFilter.add(new Products(visitProducts.getProductID(), visitProducts.getProductName(), false));
                                                    proNameListForFilter.add(visitProducts.getProductName());
                                                } else {
                                                }
                                            }

                                        }


                                      if (repListForFilter.isEmpty()) {
                                          repListForFilter.add(new Rep(visit.getRepID(), visit.getRepName(), false));
                                          repNameListForFilter.add(visit.getRepName());
                                        } else {
                                            boolean status = false;
                                            for (Rep rep : repListForFilter) {
                                                if (rep.getId() == visit.getRepID()) {
                                                    status = true;
                                                    break;
                                                } else {
                                                }
                                            }
                                            if (!status) {
                                                repListForFilter.add(new Rep(visit.getRepID(), visit.getRepName(), false));
                                                repNameListForFilter.add(visit.getRepName());
                                            } else {
                                            }
                                        }

                                    }


                                    onGetVisitsFinishedListener.visitsDoctorsList(docListForFilter);
                                    onGetVisitsFinishedListener.visitsLocationList(locListForFilter);
                                    onGetVisitsFinishedListener.visitsProductsList(productsListForFilter);
                                    onGetVisitsFinishedListener.visitsRepList(repListForFilter);


                                    onGetVisitsFinishedListener.visitsDoctorsNameList(docNameListForFilter);
                                    onGetVisitsFinishedListener.visitsLocationNameList(locNameListForFilter);
                                    onGetVisitsFinishedListener.visitsProductsNameList(proNameListForFilter);
                                    onGetVisitsFinishedListener.visitsRepNameList(repNameListForFilter);


                                }

                            }
                        });
            } catch (Exception ex) {
                onGetVisitsFinishedListener.visitsListFail("Something went wrong, Please try again", mrID, locationID, doctorID, date);
            }

        }


    }

    @Override
    public void showVisitDetails(Visit visit, OnShowVisitDetailsListener onShowVisitDetailsListener) {
        onShowVisitDetailsListener.visitDetails(visit);
    }

    @Override
    public void addDoctorsToVisitsFilterStart(Doctor doctor, boolean addOrRemove, OnAddDoctorsToVisitsStartFilterFinishedListener onAddDoctorsToVisitsStartFilterFinishedListener) {
        onAddDoctorsToVisitsStartFilterFinishedListener.doctorsToVisitsFilterStart(doctor, addOrRemove);
    }

    @Override
    public void addDoctorsToVisitsFilter(ArrayList<Doctor> docArrayListToFilterGlobal, Doctor doctor, boolean addOrRemove, OnAddDoctorsToVisitsFilterFinishedListener onAddDoctorsToVisitsFilterFinishedListener) {
        if (addOrRemove) {
            if (docArrayListToFilterGlobal.isEmpty()) {
                docArrayListToFilterGlobal.add(new Doctor(doctor.getId(), doctor.getName(), false));

            } else {
                boolean isFound = false;

                for (Doctor doct : docArrayListToFilterGlobal) {
                    if (doct.getId() == doctor.getId()) {
                        isFound = true;
                        break;
                    }

                }
                if (!isFound)
                    docArrayListToFilterGlobal.add(new Doctor(doctor.getId(), doctor.getName(), false));
            }
        } else {
            if (docArrayListToFilterGlobal.isEmpty()) {
            } else {
                for (int i = 0; i < docArrayListToFilterGlobal.size(); i++) {
                    if (docArrayListToFilterGlobal.get(i).getId() == doctor.getId()) {
                        docArrayListToFilterGlobal.remove(i);
                    }
                }
            }
        }
        onAddDoctorsToVisitsFilterFinishedListener.doctorsToVisitsFilter(docArrayListToFilterGlobal);
    }

    @Override
    public void showAddDoctorsToFilter(ArrayList<Doctor> addedDocListGlobal, ArrayList<Doctor> allFilterDocList, OnAddDoctorsToFilterFinishedListener onAddDoctorsToFilterFinishedListener) {
        final ArrayList<Doctor> docArrayList = new ArrayList<Doctor>();

        for (Doctor doc : addedDocListGlobal) {
            docArrayList.add(new Doctor(doc.getId(), doc.getName(), true));
        }

        for (Doctor docAll : allFilterDocList) {
            boolean status = false;
            for (Doctor doc : addedDocListGlobal) {
                if (docAll.getId() == doc.getId()) {
                    status = true;
                    break;
                }
            }
            if (status) {
            } else {
                docArrayList.add(new Doctor(docAll.getId(), docAll.getName(), false));

            }
        }
        onAddDoctorsToFilterFinishedListener.addDoctorsToFilter(docArrayList);

    }

    @Override
    public void setSelectedFilterDoctors(ArrayList<Doctor> totalDocList, ArrayList<Doctor> selectedDocList, OnSetSelectedFilterDoctorsFinishedListener onSetSelectedFilterDoctorsFinishedListener) {
        ArrayList<Doctor> newDocList = new ArrayList<Doctor>();

        if (selectedDocList.isEmpty()) {
            for (Doctor d : totalDocList) {
                d.setSelect(false);
            }
            newDocList = totalDocList;
        } else {
            for (Doctor added : selectedDocList) {
                newDocList.add(new Doctor(added.getId(), added.getName(), true));
            }

            for (Doctor added : totalDocList) {
                newDocList.add(new Doctor(added.getId(), added.getName(), false));
            }

            for (int i = 0; i < newDocList.size(); i++) {
                for (int j = i + 1; j < newDocList.size(); j++) {
                    if (newDocList.get(i).getId() == newDocList.get(j).getId()) {
                        newDocList.remove(j);
                        j--;
                    }
                }
            }

        }

        onSetSelectedFilterDoctorsFinishedListener.showSelectedFilterDoctors(newDocList);
    }

    @Override
    public void searchDocForFilter(ArrayList<Doctor> docArrayList, String docName, OnSearchDocForFilterFinishedListener onSearchDocForFilterFinishedListener) {
        final ArrayList<Doctor> searchArrayList = new ArrayList<Doctor>();
        if (docArrayList.isEmpty()) {
        } else {
            for (Doctor doc : docArrayList) {
                if (doc.getName().equals(docName)) {
                    searchArrayList.add(new Doctor(doc.getId(), doc.getName(), false));
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
                        searchArrayList.add(new Doctor(doc.getId(), doc.getName(), false));
                    } else {
                    }
                }

            }
        } else {
        }
        onSearchDocForFilterFinishedListener.docListForFilter(searchArrayList);
    }

    @Override
    public void addLocationToVisitsFilterStart(LocationEntitie locationEntitie, boolean addOrRemove, OnAddLocationToVisitsStartFilterFinishedListener onAddLocationToVisitsStartFilterFinishedListener) {
        onAddLocationToVisitsStartFilterFinishedListener.locationToVisitsFilterStart(locationEntitie, addOrRemove);
    }

    @Override
    public void addLocationToVisitsFilter(ArrayList<LocationEntitie> locArrayListToFilterGlobal, LocationEntitie locationEntitie, boolean addOrRemove, OnAddLocationToVisitsFilterFinishedListener onAddLocationToVisitsFilterFinishedListener) {
        if (addOrRemove) {
            if (locArrayListToFilterGlobal.isEmpty()) {
                locArrayListToFilterGlobal.add(new LocationEntitie(locationEntitie.getId(), locationEntitie.getName(), false));
            } else {
                boolean isFound = false;

                for (LocationEntitie loca : locArrayListToFilterGlobal) {
                    if (loca.getId() == locationEntitie.getId()) {
                        isFound = true;
                        break;
                    }
                }

                if (!isFound)
                    locArrayListToFilterGlobal.add(new LocationEntitie(locationEntitie.getId(), locationEntitie.getName(), false));
            }
        } else {
            if (locArrayListToFilterGlobal.isEmpty()) {
            } else {
                for (int i = 0; i < locArrayListToFilterGlobal.size(); i++) {
                    if (locArrayListToFilterGlobal.get(i).getId() == locationEntitie.getId()) {
                        locArrayListToFilterGlobal.remove(i);
                    }
                }
            }
        }
        onAddLocationToVisitsFilterFinishedListener.locationToVisitsFilter(locArrayListToFilterGlobal);
    }

    @Override
    public void showAddLocationToFilter(ArrayList<LocationEntitie> addedLocListGlobal, ArrayList<LocationEntitie> allFilterLocList, OnAddLocationToFilterFinishedListener onAddLocationToFilterFinishedListener) {
        final ArrayList<LocationEntitie> locArrayList = new ArrayList<LocationEntitie>();

        for (LocationEntitie loc : addedLocListGlobal) {
            locArrayList.add(new LocationEntitie(loc.getId(), loc.getName(), true));
        }

        for (LocationEntitie locAll : allFilterLocList) {
            boolean status = false;
            for (LocationEntitie loc : addedLocListGlobal) {
                if (locAll.getId() == loc.getId()) {
                    status = true;
                    break;
                }
            }
            if (status) {
            } else {
                locArrayList.add(new LocationEntitie(locAll.getId(), locAll.getName(), false));

            }
        }

        onAddLocationToFilterFinishedListener.addLocationToFilter(locArrayList);
    }

    @Override
    public void setSelectedFilterLocation(ArrayList<LocationEntitie> totalLocList, ArrayList<LocationEntitie> selectedLocList, OnSetSelectedFilterLocationFinishedListener onSetSelectedFilterLocationFinishedListener) {
        ArrayList<LocationEntitie> newLocList = new ArrayList<LocationEntitie>();


        if (selectedLocList.isEmpty()) {
            for (LocationEntitie l : totalLocList) {
                l.setSelect(false);
            }
            newLocList = totalLocList;
        } else {
            for (LocationEntitie added : selectedLocList) {
                newLocList.add(new LocationEntitie(added.getId(), added.getName(), true));
            }
            for (LocationEntitie added : totalLocList) {
                newLocList.add(new LocationEntitie(added.getId(), added.getName(), false));
            }

            for (int i = 0; i < newLocList.size(); i++) {
                for (int j = i + 1; j < newLocList.size(); j++) {
                    if (newLocList.get(i).getId() == newLocList.get(j).getId()) {
                        newLocList.remove(j);
                        j--;
                    }
                }
            }


        }

        onSetSelectedFilterLocationFinishedListener.showSelectedFilterLocation(newLocList);
    }

    @Override
    public void searchLocForFilter(ArrayList<LocationEntitie> locArrayList, String locName, OnSearchLocForFilterFinishedListener onSearchLocForFilterFinishedListener) {
        final ArrayList<LocationEntitie> searchArrayList = new ArrayList<LocationEntitie>();
        if (locArrayList.isEmpty()) {
        } else {
            for (LocationEntitie loc : locArrayList) {
                if (loc.getName().equals(locName)) {
                    searchArrayList.add(new LocationEntitie(loc.getId(), loc.getName(), false));
                } else {
                }
            }
        }

        if ((searchArrayList.isEmpty()) && (!locArrayList.isEmpty())) {
            if (locName.isEmpty() || locName.equals("") || locName.equalsIgnoreCase("all")) {
                searchArrayList.addAll(locArrayList);
            } else {
                for (LocationEntitie loc : locArrayList) {
                    String text = loc.getName();
                    String patternString = locName;

                    Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(text);
                    if (matcher.lookingAt()) {
                        searchArrayList.add(new LocationEntitie(loc.getId(), loc.getName(), false));
                    } else {
                    }
                }

            }
        } else {
        }
        onSearchLocForFilterFinishedListener.locListForFilter(searchArrayList);
    }

    @Override
    public void addProductsToVisitsFilterStart(Products products, boolean addOrRemove, OnAddProductsToVisitsStartFilterFinishedListener onAddProductsToVisitsStartFilterFinishedListener) {
        onAddProductsToVisitsStartFilterFinishedListener.productsToVisitsFilterStart(products, addOrRemove);
    }

    @Override
    public void addProductsToVisitsFilter(ArrayList<Products> productsArrayListToFilterGlobal, Products products, boolean addOrRemove, OnAddProductsToVisitsFilterFinishedListener onAddProductsToVisitsFilterFinishedListener) {
        if (addOrRemove) {
            if (productsArrayListToFilterGlobal.isEmpty()) {
                productsArrayListToFilterGlobal.add(new Products(products.getId(), products.getName(), false));
            } else {
                boolean isFound = false;

                for (Products loca : productsArrayListToFilterGlobal) {
                    if (loca.getId() == products.getId()) {
                        isFound = true;
                        break;
                    }
                }

                if (!isFound)
                    productsArrayListToFilterGlobal.add(new Products(products.getId(), products.getName(), false));
            }
        } else {
            if (productsArrayListToFilterGlobal.isEmpty()) {
            } else {
                for (int i = 0; i < productsArrayListToFilterGlobal.size(); i++) {
                    if (productsArrayListToFilterGlobal.get(i).getId() == products.getId()) {
                        productsArrayListToFilterGlobal.remove(i);
                    }
                }
            }
        }

        onAddProductsToVisitsFilterFinishedListener.productsToVisitsFilter(productsArrayListToFilterGlobal);


    }

    @Override
    public void showAddProductsToFilter(ArrayList<Products> addedLocListGlobal, ArrayList<Products> allFilterProductsList, OnAddProductsToFilterFinishedListener onAddProductsToFilterFinishedListener) {
        final ArrayList<Products> productsArrayList = new ArrayList<Products>();

        for (Products products : addedLocListGlobal) {
            productsArrayList.add(new Products(products.getId(), products.getName(), true));
        }

        for (Products locAll : allFilterProductsList) {
            boolean status = false;
            for (Products loc : addedLocListGlobal) {
                if (locAll.getId() == loc.getId()) {
                    status = true;
                    break;
                }
            }
            if (status) {
            } else {
                productsArrayList.add(new Products(locAll.getId(), locAll.getName(), false));

            }
        }

        onAddProductsToFilterFinishedListener.addProductsToFilter(productsArrayList);
    }

    @Override
    public void setSelectedFilterProducts(ArrayList<Products> totalProList, ArrayList<Products> selectedProList, OnSetSelectedFilterProductsFinishedListener onSetSelectedFilterProductsFinishedListener) {
        ArrayList<Products> newProList = new ArrayList<Products>();
        if (selectedProList.isEmpty()) {
            for (Products p : totalProList) {
                p.setSelect(false);
            }
            newProList = totalProList;
        } else {
            for (Products added : selectedProList) {
                newProList.add(new Products(added.getId(), added.getName(), true));
            }
            for (Products added : totalProList) {
                newProList.add(new Products(added.getId(), added.getName(), false));
            }

            for (int i = 0; i < newProList.size(); i++) {
                for (int j = i + 1; j < newProList.size(); j++) {
                    if (newProList.get(i).getId() == newProList.get(j).getId()) {
                        newProList.remove(j);
                        j--;
                    }
                }
            }


        }
        onSetSelectedFilterProductsFinishedListener.showSelectedFilterProducts(newProList);
    }

    @Override
    public void searchProductsForFilter(ArrayList<Products> proArrayList, String productsName, OnSearchProductsForFilterFinishedListener onSearchProductsForFilterFinishedListener) {
        final ArrayList<Products> searchArrayList = new ArrayList<Products>();
        if (proArrayList.isEmpty()) {
        } else {
            for (Products pro : proArrayList) {
                if (pro.getName().equals(productsName)) {
                    searchArrayList.add(new Products(pro.getId(), pro.getName(), false));
                } else {
                }
            }
        }


        if ((searchArrayList.isEmpty()) && (!proArrayList.isEmpty())) {
            if (productsName.isEmpty() || productsName.equals("") || productsName.equalsIgnoreCase("all")) {
                searchArrayList.addAll(proArrayList);
            } else {
                for (Products pro : proArrayList) {
                    String text = pro.getName();
                    String patternString = productsName;

                    Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(text);
                    if (matcher.lookingAt()) {
                        searchArrayList.add(new Products(pro.getId(), pro.getName(), false));
                    } else {
                    }
                }

            }
        } else {
        }
        onSearchProductsForFilterFinishedListener.productsListForFilter(searchArrayList);
    }

    @Override
    public void addRepToVisitsFilterStart(Rep rep, boolean addOrRemove, OnAddRepToVisitsStartFilterFinishedListener onAddRepToVisitsStartFilterFinishedListener) {
        onAddRepToVisitsStartFilterFinishedListener.repToVisitsFilterStart(rep,addOrRemove);
    }

    @Override
    public void addRepToVisitsFilter(ArrayList<Rep> repArrayListToFilterGlobal, Rep rep, boolean addOrRemove, OnAddRepToVisitsFilterFinishedListener onAddRepToVisitsFilterFinishedListener) {


        if (addOrRemove) {
            if (repArrayListToFilterGlobal.isEmpty()) {
                repArrayListToFilterGlobal.add(new Rep(rep.getId(), rep.getName(), false));
            } else {
                boolean isFound = false;

                for (Rep repa : repArrayListToFilterGlobal) {
                    if (repa.getId() == rep.getId()) {
                        isFound = true;
                        break;
                    }
                }

                if (!isFound){
                    repArrayListToFilterGlobal.add(new Rep(rep.getId(), rep.getName(), false));
                }

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
        onAddRepToVisitsFilterFinishedListener.repToVisitsFilter(repArrayListToFilterGlobal);
    }

    @Override
    public void showAddRepToFilter(ArrayList<Rep> addedLocListGlobal, ArrayList<Rep> allFilterRepList, OnAddRepToFilterFinishedListener onAddRepToFilterFinishedListener) {
        final ArrayList<Rep> repArrayList = new ArrayList<Rep>();
        for (Rep rep : addedLocListGlobal) {
            repArrayList.add(new Rep(rep.getId(), rep.getName(), true));
        }
        for (Rep repAll : allFilterRepList) {
            boolean status = false;
            for (Rep rep : addedLocListGlobal) {
                if (repAll.getId() == rep.getId()) {
                    status = true;
                    break;
                }
            }
            if (status) {
            } else {
                repArrayList.add(new Rep(repAll.getId(), repAll.getName(), false));
            }
        }
        onAddRepToFilterFinishedListener.addRepToFilter(repArrayList);

    }

    @Override
    public void setSelectedFilterRep(ArrayList<Rep> totalRepList, ArrayList<Rep> selectedRepList, OnSetSelectedFilterRepFinishedListener onSetSelectedFilterRepFinishedListener) {

        ArrayList<Rep> newProList = new ArrayList<Rep>();
        if (selectedRepList.isEmpty()) {
            for (Rep p : totalRepList) {
                p.setSelect(false);
            }
            newProList = totalRepList;
        } else {
            for (Rep added : selectedRepList) {
                newProList.add(new Rep(added.getId(), added.getName(), false));
            }
            for (Rep added : totalRepList) {
                newProList.add(new Rep(added.getId(), added.getName(), false));
            }

            for (int i = 0; i < newProList.size(); i++) {
                for (int j = i + 1; j < newProList.size(); j++) {
                    if (newProList.get(i).getId() == newProList.get(j).getId()) {
                        newProList.remove(j);
                        j--;
                    }
                }
            }


        }
        onSetSelectedFilterRepFinishedListener.showSelectedFilterRep(newProList);

    }

    @Override
    public void searchRepForFilter(ArrayList<Rep> repArrayList, String repName, OnSearchRepForFilterFinishedListener onSearchRepForFilterFinishedListener) {

        final ArrayList<Rep> searchArrayList = new ArrayList<Rep>();
        if (repArrayList.isEmpty()) {
        } else {
            for (Rep rep : repArrayList) {
                if (rep.getName().equals(repName)) {
                    searchArrayList.add(new Rep(rep.getId(), rep.getName(), false));
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
                        searchArrayList.add(new Rep(rep.getId(), rep.getName(), false));
                    } else {
                    }
                }

            }
        } else {
        }
        onSearchRepForFilterFinishedListener.repListForFilter(searchArrayList);

    }

    @Override
    public void visitsFilter(ArrayList<Visit> visitArrayList, String startDate, String endDate, ArrayList<Doctor> docList, ArrayList<LocationEntitie> locList, ArrayList<Products> proList, ArrayList<Rep> repList, OnVisitsFilterFinishedListener onVisitsFilterFinishedListener) {

        ArrayList<Visit> filterdVisitList = new ArrayList<Visit>();
        //filter date - only start date
        if (!startDate.equals("") && endDate.equals("")) {

            for (Visit visit : visitArrayList) {
                if (visit.getVisitDate().substring(0, 10).equals(startDate)) {
                    ArrayList<VisitProducts> visitProductsList = new ArrayList<VisitProducts>();
                    for (VisitProducts visitProducts : visit.getVisitsProduct()) {
                        visitProductsList.add(new VisitProducts(visitProducts.getId(), visitProducts.getProductID(), visitProducts.getVisitID(), visitProducts.getProductName(),
                                visitProducts.getImageUrl()));

                    }
                    filterdVisitList.add(new Visit(visit.getId(), visit.getCode(), visit.getVisitNumber(), visit.getLocationID(), visit.getLocation(), visit.getDoctorID(), visit.getDoctorName(),
                            visit.getRepID(), visit.getRepName(), visit.getComment(), visit.getVisitDate(), visit.getLatitude(), visit.getLongitude(), visit.getVisitsProduct(), false,visit.getImageUrl()));

                }

            }

            //filter date - start date and end date
        } else if (!startDate.equals("") && !endDate.equals("")) {
            SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dateStart = null;
            Date dateEnd = null;
            try {
                dateStart = oldFormat.parse(startDate);
                dateEnd = oldFormat.parse(endDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar calendar = new GregorianCalendar();
            calendar.setTime(dateStart);

            Calendar endCalendar = new GregorianCalendar();
            endCalendar.setTime(dateEnd);

            List<Date> datesInRange = new ArrayList<>();

            while (endCalendar.after(calendar)) {
                Date result = calendar.getTime();
                datesInRange.add(result);
                for (Visit visit : visitArrayList) {
                    if (visit.getVisitDate().substring(0, 10).equals(oldFormat.format(result))) {
                        ArrayList<VisitProducts> visitProductsList = new ArrayList<VisitProducts>();
                        for (VisitProducts visitProducts : visit.getVisitsProduct()) {
                            visitProductsList.add(new VisitProducts(visitProducts.getId(), visitProducts.getProductID(), visitProducts.getVisitID(), visitProducts.getProductName(),
                                    visitProducts.getImageUrl()));

                        }
                        filterdVisitList.add(new Visit(visit.getId(), visit.getCode(), visit.getVisitNumber(), visit.getLocationID(), visit.getLocation(), visit.getDoctorID(), visit.getDoctorName(),
                                visit.getRepID(), visit.getRepName(), visit.getComment(), visit.getVisitDate(), visit.getLatitude(), visit.getLongitude(), visit.getVisitsProduct(), false,visit.getImageUrl()));

                    }

                }
                calendar.add(Calendar.DATE, 1);
            }
        } else {
        }


        //filter doctor - wihout filter dates
        if (filterdVisitList.isEmpty()) {

            if (docList.isEmpty()) {

            } else {
                for (Visit visit : visitArrayList) {

                    for (Doctor doc : docList) {
                        if (visit.getDoctorID() == doc.getId()) {
                            ArrayList<VisitProducts> visitProductsList = new ArrayList<VisitProducts>();
                            for (VisitProducts visitProducts : visit.getVisitsProduct()) {
                                visitProductsList.add(new VisitProducts(visitProducts.getId(), visitProducts.getProductID(), visitProducts.getVisitID(), visitProducts.getProductName(),
                                        visitProducts.getImageUrl()));

                            }
                            filterdVisitList.add(new Visit(visit.getId(), visit.getCode(), visit.getVisitNumber(), visit.getLocationID(), visit.getLocation(), visit.getDoctorID(), visit.getDoctorName(),
                                    visit.getRepID(), visit.getRepName(), visit.getComment(), visit.getVisitDate(), visit.getLatitude(), visit.getLongitude(), visit.getVisitsProduct(), false,visit.getImageUrl()));

                        } else {

                        }
                    }
                }


                for (int i = 0; i < filterdVisitList.size(); i++) {
                    for (int j = i + 1; j < filterdVisitList.size(); j++) {
                        if (filterdVisitList.get(i).getId() == filterdVisitList.get(j).getId()) {
                            filterdVisitList.remove(j);
                            j--;
                        }
                    }
                }

            }


            //filter doctor - wight filter dates
        } else {
            if (docList.isEmpty()) {

            } else {

                for (int i = 0; i < filterdVisitList.size(); i++) {
                    boolean status = false;
                    for (int j = 0; j < docList.size(); j++) {
                        if (filterdVisitList.get(i).getDoctorID() == docList.get(j).getId()) {
                            status = true;
                            break;
                        } else {
                            status = false;
                        }
                    }
                    if (!status) {
                        filterdVisitList.remove(i);
                        i--;
                    }
                }

            }


        }


        //filter doctor - wihout filter dates

        if (filterdVisitList.isEmpty()) {

            if (locList.isEmpty()) {
            } else {
                for (Visit visit : visitArrayList) {


                    for (LocationEntitie loc : locList) {
                        if (visit.getLocationID() == loc.getId()) {
                            ArrayList<VisitProducts> visitProductsList = new ArrayList<VisitProducts>();
                            for (VisitProducts visitProducts : visit.getVisitsProduct()) {
                                visitProductsList.add(new VisitProducts(visitProducts.getId(), visitProducts.getProductID(), visitProducts.getVisitID(), visitProducts.getProductName(),
                                        visitProducts.getImageUrl()));

                            }
                            filterdVisitList.add(new Visit(visit.getId(), visit.getCode(), visit.getVisitNumber(), visit.getLocationID(), visit.getLocation(), visit.getDoctorID(), visit.getDoctorName(),
                                    visit.getRepID(), visit.getRepName(), visit.getComment(), visit.getVisitDate(), visit.getLatitude(), visit.getLongitude(), visit.getVisitsProduct(), false,visit.getImageUrl()));


                        } else {


                        }
                    }
                }
                for (int i = 0; i < filterdVisitList.size(); i++) {
                    for (int j = i + 1; j < filterdVisitList.size(); j++) {
                        if (filterdVisitList.get(i).getId() == filterdVisitList.get(j).getId()) {
                            filterdVisitList.remove(j);
                            j--;
                        }
                    }
                }

            }

            //filter location - wight filter dates
        } else {

            if (locList.isEmpty()) {
            } else {
                for (int i = 0; i < filterdVisitList.size(); i++) {
                    boolean status = false;
                    for (int j = 0; j < locList.size(); j++) {
                        if (filterdVisitList.get(i).getLocationID() == locList.get(j).getId()) {
                            status = true;
                            break;
                        } else {
                            status = false;
                        }
                    }
                    if (!status) {
                        filterdVisitList.remove(i);
                        i--;
                    }
                }

            }
        }


        //filter Products - wihout filter dates

        if (filterdVisitList.isEmpty()) {
            if (proList.isEmpty()) {
            } else {
                for (Visit visit : visitArrayList) {
                    boolean status = false;
                    ArrayList<VisitProducts> visitProductsList = new ArrayList<VisitProducts>();
                    for (VisitProducts visitProducts : visit.getVisitsProduct()) {
                        if (status) {
                        } else {
                            for (Products pro : proList) {
                                int aa =pro.getId();

                                int bb =visitProducts.getProductID();

                                if (pro.getId() == visitProducts.getProductID()) {
                                    status = true;
                                    break;
                                } else {
                                    status = false;
                                }
                            }
                        }

                        visitProductsList.add(new VisitProducts(visitProducts.getId(), visitProducts.getProductID(), visitProducts.getVisitID(), visitProducts.getProductName(),
                                visitProducts.getImageUrl()));


                    }

                    if (status) {
                        filterdVisitList.add(new Visit(visit.getId(), visit.getCode(), visit.getVisitNumber(), visit.getLocationID(), visit.getLocation(), visit.getDoctorID(), visit.getDoctorName(),
                                visit.getRepID(), visit.getRepName(), visit.getComment(), visit.getVisitDate(), visit.getLatitude(), visit.getLongitude(), visit.getVisitsProduct(), false,visit.getImageUrl()));
                    } else {

                    }


                }


            }

            //filter product- wight filter dates
        } else {
            if (proList.isEmpty()) {
            } else {
                for (int i = 0; i < filterdVisitList.size(); i++) {
                    boolean status = false;
                    for (VisitProducts visitProducts : filterdVisitList.get(i).getVisitsProduct()) {
                        if (status) {
                        } else {
                            for (int j = 0; j < proList.size(); j++) {
                                if (visitProducts.getProductID() == proList.get(j).getId()) {
                                    status = true;
                                    break;
                                } else {
                                    status = false;
                                }
                            }
                        }
                    }
                    if (!status) {
                        filterdVisitList.remove(i);
                        i--;
                    }
                }
            }
        }



        //filter Rep - wihout filter dates
        if (filterdVisitList.isEmpty()) {
            if (repList.isEmpty()) {

            } else {
                for (Visit visit : visitArrayList) {
                    for (Rep rep : repList) {
                        if (visit.getRepID() == rep.getId()) {
                            ArrayList<VisitProducts> visitProductsList = new ArrayList<VisitProducts>();
                            for (VisitProducts visitProducts : visit.getVisitsProduct()) {
                                visitProductsList.add(new VisitProducts(visitProducts.getId(), visitProducts.getProductID(), visitProducts.getVisitID(), visitProducts.getProductName(),
                                        visitProducts.getImageUrl()));

                            }
                            filterdVisitList.add(new Visit(visit.getId(), visit.getCode(), visit.getVisitNumber(), visit.getLocationID(), visit.getLocation(), visit.getDoctorID(), visit.getDoctorName(),
                                    visit.getRepID(), visit.getRepName(), visit.getComment(), visit.getVisitDate(), visit.getLatitude(), visit.getLongitude(), visit.getVisitsProduct(), false,visit.getImageUrl()));

                        } else {

                        }
                    }
                }


                for (int i = 0; i < filterdVisitList.size(); i++) {
                    for (int j = i + 1; j < filterdVisitList.size(); j++) {
                        if (filterdVisitList.get(i).getId() == filterdVisitList.get(j).getId()) {
                            filterdVisitList.remove(j);
                            j--;
                        }
                    }
                }

            }


            //filter rep - wight filter dates
        } else {
            if (repList.isEmpty()) {

            } else {

                for (int i = 0; i < filterdVisitList.size(); i++) {
                    boolean status = false;
                    for (int j = 0; j < repList.size(); j++) {
                        if (filterdVisitList.get(i).getRepID() == repList.get(j).getId()) {
                            status = true;
                            break;
                        } else {
                            status = false;
                        }
                    }
                    if (!status) {
                        filterdVisitList.remove(i);
                        i--;
                    }
                }

            }


        }


        if ((startDate.equals("")) & (endDate.equals("")) & (docList.isEmpty()) & (locList.isEmpty()) & (proList.isEmpty() & (repList.isEmpty())) ) {
            filterdVisitList = visitArrayList;
        } else {
        }

        onVisitsFilterFinishedListener.visitsFilterList(filterdVisitList);


    }
}


