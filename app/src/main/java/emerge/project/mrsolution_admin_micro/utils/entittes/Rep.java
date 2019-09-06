package emerge.project.mrsolution_admin_micro.utils.entittes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Rep implements Serializable {

    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    @SerializedName("code")
    String code;

    @SerializedName("imageUrl")
    String imageUrl;



    @SerializedName("productsList")
    ArrayList<Products> repProducts;


    boolean isSelect;

    public Rep(int id, String name, boolean isSelect) {
        this.id = id;
        this.name = name;
        this.isSelect = isSelect;
    }



    public Rep(int id, String name, String imageUrl, ArrayList<Products> repProducts) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.repProducts = repProducts;
    }

    public Rep(int id, String name, String code, String img, ArrayList<Products> repProducts) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.repProducts = repProducts;
        this.imageUrl = img;
    }

    public Rep(int id, String name, String code, String img,ArrayList<Products> pro,boolean isSelect) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.isSelect = isSelect;
        this.repProducts = pro;
        this.imageUrl = img;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<Products> getRepProducts() {
        return repProducts;
    }

    public void setRepProducts(ArrayList<Products> repProducts) {
        this.repProducts = repProducts;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
