package emerge.project.mrsolution_admin_micro.ui.activity.products.assign_products;


import android.content.Context;

import java.util.ArrayList;

import emerge.project.mrsolution_admin_micro.utils.entittes.Principles;
import emerge.project.mrsolution_admin_micro.utils.entittes.Products;
import emerge.project.mrsolution_admin_micro.utils.entittes.Rep;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface AssignProductsPresenter {

    void setNavigation();

    void getProductsAssignToReps(Context context);
    void getProductAssignFullDetais(ArrayList<Products> productsArrayList);
    void getReps(Context context,int rep);
    void getSelectedReps(Rep rep);
    void getPrinciples(Context context);
    void getProductsByPrinciple(Context context, int principleId);
    void getSelectedPrinciple(Principles principles);
    void addProductToRep(ArrayList<Products> productListGlobal,Products product,boolean addOrRemove );
    void searchRep(ArrayList<Rep> repArrayList,String repName);

    void editAddedProduct(ArrayList<Products> productArrayList);
    void assignProducttoRep(Context context, int repid,ArrayList<Products> productArrayList);



    void addProductToRepStart(Products product,boolean addOrRemove);






}
