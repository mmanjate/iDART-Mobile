package mz.org.fgh.idartlite.common;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

import mz.org.fgh.idartlite.base.BaseModel;
import mz.org.fgh.idartlite.util.Utilities;

public class ValorSimples extends BaseModel implements Listble{

    private int id;

    private String description;

    private String code;

    private String qty;

    public ValorSimples(String description) {
        this.description = description;
    }

    public ValorSimples() {
    }

    public ValorSimples(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public ValorSimples(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static ValorSimples fastCreate(String description){
        return new ValorSimples(description);
    }

    public static ValorSimples fastCreate(int id, String description){
        return new ValorSimples(id, description);
    }

    public static ValorSimples fastCreate(int id){
        return new ValorSimples(id);
    }

    @Override
    public boolean equals(Object o) {

        if (!(o instanceof ValorSimples)) return false;
        ValorSimples that = (ValorSimples) o;

        if (that == null || (that.id <= 0 && !Utilities.stringHasValue(that.description))) return false;

        if (id > 0 && that.id > 0) return id == that.id;

        if (Utilities.stringHasValue(this.description)) return this.description.equals(that.description);

        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public int compareTo(Object o) {
        return compareTo((BaseModel) o);
    }

    @Override
    public int getPosition() {
        return listPosition;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public void setListPosition(int listPosition) {
        super.setListPosition(listPosition);
    }

    @Override
    public int getQuantity() {
        return 0;
    }

    @Override
    public int getDrawable() {
        return 0;
    }

    @Override
    public int compareTo(BaseModel baseModel) {
        if (this.getPosition() == 0 || ((ValorSimples) baseModel).getPosition() == 0) return 0;

        return this.getPosition() - ((ValorSimples) baseModel).getPosition();
    }
}
