package emerge.project.mrsolution_admin_micro.ui.activity.products.assign_products;


import java.util.ArrayList;

import emerge.project.mrsolution_admin_micro.utils.entittes.Navigation;
import emerge.project.mrsolution_admin_micro.utils.entittes.Principles;
import emerge.project.mrsolution_admin_micro.utils.entittes.Products;
import emerge.project.mrsolution_admin_micro.utils.entittes.Rep;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface AssignProductsView {


    void navigationItems(ArrayList<Navigation> navigationArrayList);



    void productsAssignToReps(ArrayList<Rep> repArrayList);

    void productsAssignToRepsEmpty();

    void productsAssignToRepsGetingFail(String failMsg);

    void productsAssignToRepsNetworkFail();

    void productAssignFullDetais(ArrayList<Products> productsArrayList);

    void repList(ArrayList<Rep> repArrayList, ArrayList<String> repnames);

    void repsEmpty();

    void repsGetingFail(String failMsg,int rep);

    void repsGetingNetworkFail();

    void selectedRep(Rep rep);


    void principlesList(ArrayList<Principles> principlesepArrayList);

    void principlesEmpty();

    void principlesGetingFail(String failMsg);

    void principlesGetingNetworkFail();


    void productsByPrincipleList(ArrayList<Products> productArrayList);

    void productsByPrincipleEmpty();

    void productsByPrincipleGetingFail(String failMsg, int principleId);

    void productsByPrincipleGetingNetworkFail();


    void selectedPrinciple(Principles principles);

    void addedProduct(ArrayList<Products> productArrayList);


    void searchRepList(ArrayList<Rep> repArrayList);

    void editedProductList(ArrayList<Products> productList);


    void assignProducttoRepEmpty(String failMsg);

    void assignProducttoRepSuccessful();

    void assignProducttoRepGetingFail(String failMsg, int repid, ArrayList<Products> productArrayList);

    void assignProducttoRepGetingNetworkFail();



    void addedProductStart(Products product,boolean addOrRemove);



}
