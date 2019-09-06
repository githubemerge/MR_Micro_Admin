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

public class AssignProductsPresenterImpli implements AssignProductsPresenter,
        AssignProductsInteractor.OnProductsAssignToRepsFinishedListener,
        AssignProductsInteractor.OnGetProductAssignFulDetailsFinishedListener,
        AssignProductsInteractor.OnRepsFinishedListener,
        AssignProductsInteractor.OnSelectedRepsFinishedListener,
        AssignProductsInteractor.OnPrinciplesFinishedListener,
        AssignProductsInteractor.OnProductsByPrincipleFinishedListener,
        AssignProductsInteractor.OnSelectedPrincipleFinishedListener,
        AssignProductsInteractor.OnAddProductToRepFinishedListener,
        AssignProductsInteractor.OnSearchRepFinishedListener,
        AssignProductsInteractor.OnEditAddedProductFinishedListener,
        AssignProductsInteractor.OnAssignProducttoRepFinishedListener,
AssignProductsInteractor.OnAddProductToRepStartFinishedListener,
AssignProductsInteractor.OnsetNavigationFinishedListener{


    private AssignProductsView assignProductsView;
    AssignProductsInteractor assignProductsInteractor;


    public AssignProductsPresenterImpli(AssignProductsView assignproductsview) {
        this.assignProductsView = assignproductsview;
        this.assignProductsInteractor = new AssignProductsInteractorImpil();

    }


    @Override
    public void productsAssignToReps(ArrayList<Rep> repArrayList) {
        assignProductsView.productsAssignToReps(repArrayList);
    }

    @Override
    public void productsAssignToRepsEmpty() {
        assignProductsView.productsAssignToRepsEmpty();
    }

    @Override
    public void productsAssignToRepsGetingFail(String failMsg) {
        assignProductsView.productsAssignToRepsGetingFail(failMsg);
    }

    @Override
    public void productsAssignToRepsNetworkFail() {
        assignProductsView.productsAssignToRepsNetworkFail();
    }



    @Override
    public void getProductsAssignToReps(Context context) {
        assignProductsInteractor.getProductsAssignToReps(context, this);
    }


    @Override
    public void getProductAssignFullDetais(ArrayList<Products> productsArrayList) {
        assignProductsInteractor.getProductAssignFullDetais(productsArrayList, this);
    }


    @Override
    public void productAssignFullDetais(ArrayList<Products> productsArrayList) {
        assignProductsView.productAssignFullDetais(productsArrayList);
    }


    @Override
    public void getReps(Context context,int rep) {
        assignProductsInteractor.getReps(context, rep, this);
    }


    @Override
    public void repList(ArrayList<Rep> repArrayList, ArrayList<String> repnames) {
        assignProductsView.repList(repArrayList, repnames);
    }

    @Override
    public void repsEmpty() {
        assignProductsView.repsEmpty();
    }

    @Override
    public void repsGetingFail(String failMsg,int rep) {
        assignProductsView.repsGetingFail(failMsg, rep);
    }

    @Override
    public void repsGetingNetworkFail() {
        assignProductsView.repsGetingNetworkFail();
    }


    @Override
    public void getSelectedReps(Rep rep) {
        assignProductsInteractor.getSelectedReps(rep, this);
    }


    @Override
    public void selectedRep(Rep rep) {
        assignProductsView.selectedRep(rep);
    }


    @Override
    public void getPrinciples(Context context) {
        assignProductsInteractor.getPrinciples(context, this);
    }


    @Override
    public void principlesList(ArrayList<Principles> principlesepArrayList) {
        assignProductsView.principlesList(principlesepArrayList);
    }

    @Override
    public void principlesEmpty() {
        assignProductsView.principlesEmpty();
    }

    @Override
    public void principlesGetingFail(String failMsg) {
        assignProductsView.principlesGetingFail(failMsg);
    }

    @Override
    public void principlesGetingNetworkFail() {
        assignProductsView.principlesGetingNetworkFail();
    }


    @Override
    public void getProductsByPrinciple(Context context, int principleId) {
        assignProductsInteractor.getProductsByPrinciple(context, principleId, this);
    }


    @Override
    public void productsByPrincipleList(ArrayList<Products> productArrayList) {
        assignProductsView.productsByPrincipleList(productArrayList);
    }

    @Override
    public void productsByPrincipleEmpty() {
        assignProductsView.productsByPrincipleEmpty();
    }

    @Override
    public void productsByPrincipleGetingFail(String failMsg, int principleId) {
        assignProductsView.productsByPrincipleGetingFail(failMsg, principleId);
    }

    @Override
    public void productsByPrincipleGetingNetworkFail() {
        assignProductsView.productsByPrincipleGetingNetworkFail();
    }


    @Override
    public void getSelectedPrinciple(Principles principles) {
        assignProductsInteractor.getSelectedPrinciple(principles, this);
    }


    @Override
    public void selectedPrinciple(Principles principles) {
        assignProductsView.selectedPrinciple(principles);
    }


    @Override
    public void addProductToRep(ArrayList<Products> productListGlobal,Products product, boolean addOrRemove) {
        assignProductsInteractor.addProductToRep( productListGlobal,product, addOrRemove, this);
    }




    @Override
    public void addedProduct(ArrayList<Products> productArrayList) {
        assignProductsView.addedProduct(productArrayList);
    }




    @Override
    public void searchRep(ArrayList<Rep> repArrayList, String repName) {
        assignProductsInteractor.searchRep(repArrayList,repName,this);
    }



    @Override
    public void searchRepList(ArrayList<Rep> repArrayList) {
        assignProductsView.searchRepList(repArrayList);
    }



    @Override
    public void editAddedProduct(ArrayList<Products> productArrayList) {
        assignProductsInteractor.editAddedProduct(productArrayList,this);
    }



    @Override
    public void editedProductList(ArrayList<Products> productList) {
        assignProductsView.editedProductList(productList);
    }






    @Override
    public void assignProducttoRep(Context context, int repid, ArrayList<Products> productArrayList) {
        assignProductsInteractor.assignProducttoRep(context,repid,productArrayList,this);
    }


    @Override
    public void assignProducttoRepEmpty(String failMsg) {
        assignProductsView.assignProducttoRepEmpty(failMsg);
    }

    @Override
    public void assignProducttoRepSuccessful() {
        assignProductsView.assignProducttoRepSuccessful();
    }

    @Override
    public void assignProducttoRepGetingFail(String failMsg, int repid, ArrayList<Products> productArrayList) {
        assignProductsView.assignProducttoRepGetingFail(failMsg,repid,productArrayList);
    }

    @Override
    public void assignProducttoRepGetingNetworkFail() {
        assignProductsView.assignProducttoRepGetingNetworkFail();
    }



    @Override
    public void addProductToRepStart(Products product, boolean addOrRemove) {
        assignProductsInteractor.addProductToRepStart(product,addOrRemove,this);
    }

    @Override
    public void addedProductStart(Products product, boolean addOrRemove) {
        assignProductsView.addedProductStart(product,addOrRemove);
    }



    @Override
    public void setNavigation() {
        assignProductsInteractor.setNavigation(this);
    }

    @Override
    public void navigationItems(ArrayList<Navigation> navigationArrayList) {
        assignProductsView.navigationItems(navigationArrayList);
    }
}
