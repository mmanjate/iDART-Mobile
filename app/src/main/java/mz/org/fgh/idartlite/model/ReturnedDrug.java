package mz.org.fgh.idartlite.model;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.recyclerview.listable.Listble;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.dao.dispense.ReturnedDrugDaoImpl;
import mz.org.fgh.idartlite.util.DateUtilities;

@DatabaseTable(tableName = "Returned_drug", daoClass = ReturnedDrugDaoImpl.class)
public class ReturnedDrug extends BaseModel implements Listble {

    public static final String COLUMN_QUANTITY_RETURNED = "quantity_returned";
    public static final String COLUMN_DATE_RETURNED = "date_returned";
    public static final String COLUMN_DISPENSED_DRUG = "dispensed_drug_id";
    public static final String COLUMN_NOTES = "notes";


    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = COLUMN_QUANTITY_RETURNED)
    private int quantityReturned;

    @DatabaseField(columnName = COLUMN_DATE_RETURNED)
    private Date dateReturned;

    @DatabaseField(columnName = COLUMN_DISPENSED_DRUG ,canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private DispensedDrug dispensedDrug;

    @DatabaseField(columnName = COLUMN_NOTES)
    private String notes;

    public ReturnedDrug() {
    }

    public int getId() {
        return id;
    }

    @Override
    public String getDescription() {
        return dispensedDrug.getStock().getDrug().getDescription();
    }

    @Override
    public int getQuantity() {
        return dispensedDrug.getQuantitySupplied();
    }

    @Override
    public void setQtyToModify(int quantityReturned) {
        this.quantityReturned = quantityReturned;
    }

    @Override
    public int getQtyToModify() {
        return quantityReturned;
    }



    @Override
    public int getDrawable() {
        return 0;
    }

    @Override
    public String getCode() {
        return null;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantityReturned() {
        return quantityReturned;
    }

    public void setQuantityReturned(int quantityReturned) {
        this.quantityReturned = quantityReturned;
    }

    public Date getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(Date dateReturned) {
        this.dateReturned = dateReturned;
    }

    public DispensedDrug getDispensedDrug() {
        return dispensedDrug;
    }

    public void setDispensedDrug(DispensedDrug dispensedDrug) {
        this.dispensedDrug = dispensedDrug;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReturnedDrug that = (ReturnedDrug) o;
        return id == that.id &&
                dispensedDrug.equals(that.dispensedDrug);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(id, dispensedDrug);
    }

    @Override
    public String toString() {
        return "DispensedDrug{" +
                "id=" + id +
                ", dispense=" + dispensedDrug +
                '}';
    }

    @Override
    public String isValid(Context context) {
        return null;
    }

    @Override
    public String canBeEdited(Context context) {
        return null;
    }

    @Override
    public String canBeRemoved(Context context) {
        return null;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public boolean isReturnDispenseListing() {
        return listType.equals(Listble.RETURN_DRUG_LISTING);
    }

    public String validate(Context context) {

        if(this.dateReturned == null) return context.getString(R.string.return_date_mandatory);
        if(DateUtilities.dateDiff(this.dateReturned, DateUtilities.getCurrentDate(), DateUtilities.DAY_FORMAT) > 0) {
            return context.getString(R.string.return_date_date_not_correct );
        }
        if(DateUtilities.dateDiff( this.dateReturned,dispensedDrug.getDispense().getPickupDate(), DateUtilities.DAY_FORMAT) < 0) {
            return context.getString(R.string.return_date_date_cant_be_lower_than_dispense_date );
        }
        if(quantityReturned<0) return context.getString(R.string.quantity_return_cant_be_lower_zero);
        if(quantityReturned>dispensedDrug.getQuantitySupplied()) return context.getString(R.string.quantity_returned_cant_be_higher_than_taked);
        return "";
    }
}
