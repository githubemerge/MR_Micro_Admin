package emerge.project.mrsolution_admin_micro.ui.activity.products.assign_products;


import android.content.Context;

import java.util.ArrayList;

import emerge.project.mrsolution_admin_micro.utils.entittes.Navigation;
import emerge.project.mrsolution_admin_micro.utils.entittes.Principles;
import emerge.project.mrsolution_admin_micro.utils.entittes.Products;
import emerge.project.mrsolution_admin_micro.utils.entittes.Rep;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface AssignProductsInteractor {


    interface OnsetNavigationFinishedListener {
        void navigationItems(ArrayList<Navigation> navigationArrayList);
    }
    void setNavigation(OnsetNavigationFinishedListener onsetNavigationFinishedListener);



    interface OnProductsAssignToRepsFinishedListener {
        void productsAssignToReps(ArrayList<Rep> repArrayList);
        void productsAssignToRepsEmpty();
        void productsAssignToRepsGetingFail(String failMsg);
        void productsAssignToRepsNetworkFail();
    }
    void getProductsAssignToReps(Context context, OnProductsAssignToRepsFinishedListener onProductsAssignToRepsFinishedListener);


    interface OnGetProductAssignFulDetailsFinishedListener {
        void  productAssignFullDetais(ArrayList<Products> productsArrayList);
    }
    void getProductAssignFullDetais( ArrayList<Products> productsArrayList, OnGetProductAssignFulDetailsFinishedListener onGetProductAssignFulDetailsFinishedListener);


    interface OnRepsFinishedListener {
        void repList(ArrayList<Rep> repArrayList,ArrayList<String> repnames);
        void repsEmpty();
        void repsGetingFail(String failMsg, int repID);
        void repsGetingNetworkFail();
    }
    void getReps(Context context, int rep,OnRepsFinishedListener onRepsFinishedListener);

    interface OnSelectedRepsFinishedListener {
        void selectedRep(Rep rep);
    }
    void getSelectedReps(Rep rep, OnSelectedRepsFinishedListener onSelectedRepsFinishedListener);


    interface OnPrinciplesFinishedListener {
        void principlesList(ArrayList<Principles> principlesepArrayList);
        void principlesEmpty();
        void principlesGetingFail(String failMsg);
        void principlesGetingNetworkFail();
    }
    void getPrinciples(Context context, OnPrinciplesFinishedListener onPrinciplesFinishedListener);


    interface OnSelectedPrincipleFinishedListener {
        void selectedPrinciple(Principles principles);
    }
    void getSelectedPrinciple(Principles principles, OnSelectedPrincipleFinishedListener onSelectedPrincipleFinishedListener);



    interface OnProductsByPrincipleFinishedListener {
        void productsByPrincipleList(ArrayList<Products> productArrayList);
        void productsByPrincipleEmpty();
        void productsByPrincipleGetingFail(String failMsg,int principleId);
        void productsByPrincipleGetingNetworkFail();
    }
    void getProductsByPrinciple(Context context, int principleId,OnProductsByPrincipleFinishedListener onProductsByPrincipleFinishedListener);





    interface OnSearchRepFinishedListener {
        void searchRepList(ArrayList<Rep> repArrayList);
    }
    void searchRep(ArrayList<Rep> repArrayList,String repName, OnSearchRepFinishedListener onSearchRepFinishedListener);



    interface OnEditAddedProductFinishedListener {
        void editedProductList(ArrayList<Products> productList);
    }
    void editAddedProduct(ArrayList<Products> productArrayList, OnEditAddedProductFinishedListener onEditAddedProductFinishedListener);


    interface OnAssignProducttoRepFinishedListener {
        void assignProducttoRepEmpty(String failMsg);
        void assignProducttoRepSuccessful();
        void assignProducttoRepGetingFail(String failMsg,int repid,ArrayList<Products> productArrayList);
        void assignProducttoRepGetingNetworkFail();
    }
    void assignProducttoRep(Context context, int repid,ArrayList<Products> productArrayList,OnAssignProducttoRepFinishedListener onAssignProducttoRepFinishedListener);


    interface OnAddProductToRepStartFinishedListener {
        void addedProductStart(Products product,boolean addOrRemove);
    }
    void addProductToRepStart(Products product,boolean addOrRemove, OnAddProductToRepStartFinishedListener onAddProductToRepStartFinishedListener);

    interface OnAddProductToRepFinishedListener {
        void addedProduct(ArrayList<Products> productArrayList);
    }
    void addProductToRep(ArrayList<Products> productListGlobal,Products product,boolean addOrRemove, OnAddProductToRepFinishedListener onAddProductToRepFinishedListener);



}
