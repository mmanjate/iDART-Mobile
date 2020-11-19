package mz.org.fgh.idartlite.adapter.recyclerview.listable;

import java.util.Date;

import mz.org.fgh.idartlite.base.model.BaseModel;

public interface Listble<T extends BaseModel> extends Comparable<T>{

    public static final String STOCK_LISTING = "stock_listing";
    public static final String STOCK_DESTROY_LISTING = "stock_destroy_listing";
    public static final String PRESCRIPTION_DRUG_LISTING = "prescription_drug_listing";
    public static final String DISPENSE_DRUG_LISTING = "dispense_drug_listing";
    public static final String INVENTORY_LISTING = "inventory_listing";

    int getId();

    default int getPosition() {
        return 0;
    }

    String getDescription();

    void setListPosition(int listPosition);

    default int getQuantity() {
        return 0;
    }



    default String getLote() {
        return null;
    }

    default String getFnmcode() {
        return null;
    }

    default Date getValidate() {
        return null;
    }

    default int getSaldoActual() {
        return 0;
    }

    default int getQtyToModify() {
        return 0;
    }

    default void setQtyToModify(int qtyToDestroy) {
    }

    default boolean isStockListing() {
        return false;
    }

    default boolean isPrescriptionDrugListing() {
        return false;
    }

    default boolean isDispenseDrugListing() {
        return false;
    }

    default boolean isStockDestroyListing() {
        return false;
    }

    default boolean isIventoryListing() {
        return false;
    }

    int getDrawable();

    String getCode();

    @Override
    default int compareTo(T t) {
        return 0;
    }
}
