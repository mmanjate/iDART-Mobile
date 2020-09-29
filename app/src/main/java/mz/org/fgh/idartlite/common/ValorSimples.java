package mz.org.fgh.idartlite.common;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

import mz.org.fgh.idartlite.util.Utilities;

public class ValorSimples {

    private int id;

    private String description;

    private String code;

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

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
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
}
