package emerge.project.mrsolution_admin_micro.ui.activity.products.assign_products;


import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pddstudio.preferences.encrypted.EncryptedPreferences;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import emerge.project.mrsolution_admin_micro.BuildConfig;
import emerge.project.mrsolution_admin_micro.services.api.ApiClient;
import emerge.project.mrsolution_admin_micro.services.api.ApiInterface;
import emerge.project.mrsolution_admin_micro.services.network.NetworkAvailability;
import emerge.project.mrsolution_admin_micro.utils.entittes.Navigation;
import emerge.project.mrsolution_admin_micro.utils.entittes.Principles;
import emerge.project.mrsolution_admin_micro.utils.entittes.Products;
import emerge.project.mrsolution_admin_micro.utils.entittes.Rep;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Himanshu on 4/5/2017.
 */

public class AssignProductsInteractorImpil implements AssignProductsInteractor {


    public static final String tokenID = BuildConfig.API_TOKEN_ID;

    EncryptedPreferences encryptedPreferences;
    private static final String USER_ID = "userID";
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


    ArrayList<Products> addedproductsList = new ArrayList<Products>();

    ArrayList<Rep> repList;
    ArrayList<Principles> principlesList;
    ArrayList<Products> productsList;


    ArrayList<Rep> productAssignRepList;

    Boolean productAssign;

    @Override
    public void setNavigation(OnsetNavigationFinishedListener onsetNavigationFinishedListener) {
        ArrayList<Navigation> navigationItems = new ArrayList<Navigation>();
       // navigationItems.add(new Navigation("Location Assing", R.drawable.ic_product_defult_small));

        onsetNavigationFinishedListener.navigationItems(navigationItems);
    }

    @Override
    public void getProductsAssignToReps(Context context, final OnProductsAssignToRepsFinishedListener onProductsAssignToRepsFinishedListener) {

        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onProductsAssignToRepsFinishedListener.productsAssignToRepsNetworkFail();
        } else {
            try {
                encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
                int userId = encryptedPreferences.getInt(USER_ID, 0);
                final ArrayList<Rep> repArrayList = new ArrayList<Rep>();
                apiService.getMRByAdminProductsAssigned(userId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<Rep>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(ArrayList<Rep> rep) {
                                productAssignRepList = rep;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onProductsAssignToRepsFinishedListener.productsAssignToRepsGetingFail("Something went wrong, Please try again");
                            }

                            @Override
                            public void onComplete() {
                                if (productAssignRepList.isEmpty()) {
                                    onProductsAssignToRepsFinishedListener.productsAssignToRepsEmpty();
                                } else {
                                    for(Rep r : productAssignRepList){
                                        ArrayList<Products> productsArrayList = new ArrayList<Products>();
                                        for(Products products : r.getRepProducts()){
                                            productsArrayList.add(new Products(products.getId(),products.getName(),products.getPrinciple()));
                                        }
                                        repArrayList.add(new Rep(r.getId(),r.getName(),r.getImageUrl(),productsArrayList));
                                    }
                                    onProductsAssignToRepsFinishedListener.productsAssignToReps(repArrayList);
                                }
                            }
                        });

            } catch (Exception ex) {
                onProductsAssignToRepsFinishedListener.productsAssignToRepsGetingFail("Something went wrong, Please try again");
            }

        }

    }

    @Override
    public void getProductAssignFullDetais(ArrayList<Products> productsArrayList, OnGetProductAssignFulDetailsFinishedListener onGetProductAssignFulDetailsFinishedListener) {
        onGetProductAssignFulDetailsFinishedListener.productAssignFullDetais(productsArrayList);
    }

    @Override
    public void getReps(Context context, final int rep, final OnRepsFinishedListener onRepsFinishedListener) {

        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onRepsFinishedListener.repsGetingNetworkFail();
        } else {
            try {
                encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
                int userId = encryptedPreferences.getInt(USER_ID, 0);
                final ArrayList<Rep> repArrayList = new ArrayList<Rep>();
                final ArrayList<String> repNames = new ArrayList<String>();

                apiService.getMRByAdmin(userId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<Rep>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(ArrayList<Rep> rep) {
                                repList = rep;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onRepsFinishedListener.repsGetingFail("Something went wrong, Please try again",rep);
                            }

                            @Override
                            public void onComplete() {
                                if (repList.isEmpty()) {
                                    onRepsFinishedListener.repsEmpty();
                                } else {
                                    for(Rep r : repList){
                                        if(r.getId()==rep){
                                            repArrayList.add(new Rep(r.getId(),r.getName(),r.getCode(),r.getImageUrl(),r.getRepProducts() ,true));
                                        }else {
                                            repArrayList.add(new Rep(r.getId(), r.getName(),r.getCode(),r.getImageUrl(),r.getRepProducts() ,false));
                                        }
                                        repNames.add(r.getName());
                                    }
                                    onRepsFinishedListener.repList(repArrayList, repNames);
                                }

                            }
                        });
            } catch (Exception ex) {
                onRepsFinishedListener.repsGetingFail("Something went wrong, Please try again",rep);
            }

        }

    }

    @Override
    public void getSelectedReps(Rep rep, OnSelectedRepsFinishedListener onSelectedRepsFinishedListener) {
        onSelectedRepsFinishedListener.selectedRep(rep);
    }

    @Override
    public void getPrinciples(Context context, final OnPrinciplesFinishedListener onPrinciplesFinishedListener) {
        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onPrinciplesFinishedListener.principlesGetingNetworkFail();
        } else {
            try {

                encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
                int userId = encryptedPreferences.getInt(USER_ID, 0);

                final ArrayList<Principles> principlesArrayList = new ArrayList<Principles>();
                apiService.getAllPrinciples(userId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<Principles>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(ArrayList<Principles> principles) {
                                principlesList = principles;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onPrinciplesFinishedListener.principlesGetingFail("Something went wrong, Please try again");
                            }

                            @Override
                            public void onComplete() {
                                if (principlesList.isEmpty()) {
                                    onPrinciplesFinishedListener.principlesEmpty();
                                } else {
                                    for(Principles pri : principlesList){
                                        principlesArrayList.add(new Principles(pri.getId(),pri.getName(), false));
                                    }
                                    onPrinciplesFinishedListener.principlesList(principlesArrayList);
                                }

                            }
                        });
            } catch (Exception ex) {
                onPrinciplesFinishedListener.principlesGetingFail("Something went wrong, Please try again");
            }

        }

    }

    @Override
    public void getSelectedPrinciple(Principles principles, OnSelectedPrincipleFinishedListener onSelectedPrincipleFinishedListener) {
        onSelectedPrincipleFinishedListener.selectedPrinciple(principles);
    }

    @Override
    public void getProductsByPrinciple(Context context, final int principleId, final OnProductsByPrincipleFinishedListener onProductsByPrincipleFinishedListener) {
        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onProductsByPrincipleFinishedListener.productsByPrincipleGetingNetworkFail();
        } else {
            try {
                final ArrayList<Products> productsArrayList = new ArrayList<Products>();
                apiService.getAllProductsForPrinciple(principleId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ArrayList<Products>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(ArrayList<Products> products) {
                                productsList = products;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onProductsByPrincipleFinishedListener.productsByPrincipleGetingFail("Something went wrong, Please try again", principleId);
                            }

                            @Override
                            public void onComplete() {
                                if (productsList.isEmpty()) {
                                    onProductsByPrincipleFinishedListener.productsByPrincipleEmpty();
                                } else {
                                    for(Products pro : productsList){
                                        productsArrayList.add(new Products(pro.getId(),pro.getName(),pro.getCode(),pro.getImageUrl(), false));
                                    }
                                    onProductsByPrincipleFinishedListener.productsByPrincipleList(productsArrayList);
                                }

                            }
                        });
            } catch (Exception ex) {
                onProductsByPrincipleFinishedListener.productsByPrincipleGetingFail("Something went wrong, Please try again", principleId);
            }
        }


    }

    @Override
    public void addProductToRep(ArrayList<Products> productListGlobal,Products product, boolean addOrRemove, OnAddProductToRepFinishedListener onAddProductToRepFinishedListener) {

        if (addOrRemove) {
            if (productListGlobal.isEmpty()) {
                productListGlobal.add(new Products(product.getId(), product.getName()));
            } else {
                boolean isFound = false;
                for (int i = 0; i < productListGlobal.size(); i++) {
                    if (productListGlobal.get(i).getId() == product.getId()) {
                        isFound = true;
                        productListGlobal.remove(i);
                        break;
                    }
                }
                if (!isFound){
                    productListGlobal.add(new Products(product.getId(), product.getName()));
                }else {

                }

            }
        } else {
            if (productListGlobal.isEmpty()) {
            } else {
                for (int i = 0; i < productListGlobal.size(); i++) {
                    if (productListGlobal.get(i).getId() == product.getId()) {
                        productListGlobal.remove(i);
                    }
                }
            }
        }

        onAddProductToRepFinishedListener.addedProduct(productListGlobal);

    }

    @Override
    public void searchRep(ArrayList<Rep> repArrayList, String repName, OnSearchRepFinishedListener onSearchRepFinishedListener) {
        final ArrayList<Rep> searchArrayList = new ArrayList<Rep>();
        if (repArrayList.isEmpty()) {
        } else {
            for(Rep rep :repArrayList){
                if (rep.getName().equals(repName)) {
                    searchArrayList.add(new Rep(rep.getId(), rep.getName(), rep.getCode(), rep.getImageUrl(),rep.getRepProducts() ,false));
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
                        searchArrayList.add(new Rep(rep.getId(), rep.getName(), rep.getCode(), rep.getImageUrl(),rep.getRepProducts() ,false));
                    } else {
                    }

                }

            }
        } else {
        }

        onSearchRepFinishedListener.searchRepList(searchArrayList);

    }

    @Override
    public void editAddedProduct(ArrayList<Products> productArrayList, OnEditAddedProductFinishedListener onEditAddedProductFinishedListener) {
        onEditAddedProductFinishedListener.editedProductList(productArrayList);
    }

    @Override
    public void assignProducttoRep(Context context, final int repid, final ArrayList<Products> productArrayList, final OnAssignProducttoRepFinishedListener onAssignProducttoRepFinishedListener) {


        if (!NetworkAvailability.isNetworkAvailable(context)) {
            onAssignProducttoRepFinishedListener.assignProducttoRepGetingNetworkFail();
        } else if (repid == 0) {
            onAssignProducttoRepFinishedListener.assignProducttoRepEmpty("Please select Medical Rep");
        } else if (productArrayList.isEmpty()) {
            onAssignProducttoRepFinishedListener.assignProducttoRepEmpty("Please select Products");
        } else {

            encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
            int userId = encryptedPreferences.getInt(USER_ID, 0);

            final JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("UserID", repid);
            jsonObject.addProperty("AssignedByID", userId);


            JsonArray cartJsonArr = new JsonArray();

            for(Products pro : productArrayList){
                JsonObject ob = new JsonObject();
                ob.addProperty("ID", pro.getId());
                cartJsonArr.add(ob);
            }


            jsonObject.add("Products", cartJsonArr);
            System.out.println("aaa :" + jsonObject);

            onAssignProducttoRepFinishedListener.assignProducttoRepSuccessful();

           try {
                apiService.assignRepProduct(jsonObject)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(Boolean respond) { productAssign = respond; }

                            @Override
                            public void onError(Throwable e) {
                                onAssignProducttoRepFinishedListener.assignProducttoRepGetingFail("Something went wrong, Please try again",repid,productArrayList);
                            }

                            @Override
                            public void onComplete() {
                                if(productAssign){
                                    onAssignProducttoRepFinishedListener.assignProducttoRepSuccessful();
                                }else {
                                    onAssignProducttoRepFinishedListener.assignProducttoRepGetingFail("Something went wrong, Please try again",repid,productArrayList);
                                }

                            }
                        });

            } catch (Exception ex) {
                onAssignProducttoRepFinishedListener.assignProducttoRepGetingFail("Something went wrong, Please try again",repid,productArrayList);
            }

        }

    }
    @Override
    public void addProductToRepStart(Products product, boolean addOrRemove, OnAddProductToRepStartFinishedListener onAddProductToRepStartFinishedListener) {
        onAddProductToRepStartFinishedListener.addedProductStart(product,addOrRemove);
    }


}


