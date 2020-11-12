package mz.org.fgh.idartlite.model;


import android.os.Build;

import androidx.annotation.RequiresApi;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.dao.dispense.DispenseDaoImpl;
import mz.org.fgh.idartlite.util.DateUtilities;
import mz.org.fgh.idartlite.util.Utilities;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@DatabaseTable(tableName = "Dispense", daoClass = DispenseDaoImpl.class)
public class Dispense extends BaseModel {

    public static final String COLUMN_PICKUP_DATE = "pickup_date";
    public static final String COLUMN_SUPPLY = "supply";
    public static final String COLUMN_NEXT_PICKUP_DATE = "next_pickup_date";
    public static final String COLUMN_PRESCRIPTION = "prescription_id";
    public static final String COLUMN_UUID = "uuid";
    public static final String COLUMN_SYNC_STATUS = "sync_status";

    public static final int DURATION_TWO_WEEKS = 15;
    public static final int DURATION_ONE_MONTH = 30;
    public static final int DURATION_TWO_MONTHS = 60;
    public static final int DURATION_THREE_MONTHS = 90;
    public static final int DURATION_FOUR_MONTHS = 120;
    public static final int DURATION_FIVE_MONTHS = 150;
    public static final int DURATION_SIX_MONTHS = 180;

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = COLUMN_PICKUP_DATE)
    private Date pickupDate;

    @DatabaseField(columnName =COLUMN_SUPPLY)
    private int supply;

    @DatabaseField(columnName = COLUMN_NEXT_PICKUP_DATE)
    private Date nextPickupDate;

    @DatabaseField(columnName = COLUMN_PRESCRIPTION, canBeNull = false, foreign = true, foreignAutoRefresh = true )
    private Prescription prescription;

    @DatabaseField(columnName = COLUMN_UUID)
    private String uuid;

    @DatabaseField(columnName = COLUMN_SYNC_STATUS)
    private String syncStatus;

    private List<DispensedDrug> dispensedDrugs = new ArrayList<>();

    public Dispense() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(Date pickupDate) {
        this.pickupDate = pickupDate;
    }

    public int getSupply() {
        return supply;
    }

    public void setSupply(int supply) {
        this.supply = supply;
    }

    public Date getNextPickupDate() {
        return nextPickupDate;
    }

    public void setNextPickupDate(Date nextPickupDate) {
        this.nextPickupDate = nextPickupDate;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public List<DispensedDrug> getDispensedDrugs() {
        return dispensedDrugs;
    }

    public void setDispensedDrugs(List<DispensedDrug> dispensedDrugs) {
        this.dispensedDrugs = dispensedDrugs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dispense dispense = (Dispense) o;
        return uuid.equals(dispense.uuid);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "Dispense{" +
                "id=" + id +
                ", pickupDate=" + pickupDate +
                ", supply=" + supply +
                ", nextPickupDate=" + nextPickupDate +
                ", prescription=" + prescription +
                ", uuid='" + uuid + '\'' +
                ", syncStatus='" + syncStatus + '\'' +
                '}';
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int getDuration(Date pickupDate, Date nextPickupDate){
        //Parsing the date
        LocalDate pickupLocalDate = pickupDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        LocalDate nextPickupLocalDate  = nextPickupDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        //calculating number of days in between
        long noOfDaysBetween = ChronoUnit.DAYS.between(pickupLocalDate, nextPickupLocalDate);

       return (int) noOfDaysBetween;

    }

    public String validate() {
        if (this.pickupDate == null) return "A data da dispensa é obrigatória";
        if (this.nextPickupDate == null) return "A data do próximo levantamento é obrigatória";
        if(DateUtilities.dateDiff( this.pickupDate, DateUtilities.getCurrentDate(), DateUtilities.DAY_FORMAT) > 0) {
            return "A data do levantamento não pode ser maior que a data corrente.";
        }
        if(DateUtilities.dateDiff(this.pickupDate, this.nextPickupDate, DateUtilities.DAY_FORMAT) > 0) {
            return "A data do levantamento não pode ser maior que a data do próximo levantamento.";
        }
        if(DateUtilities.dateDiff(this.prescription.getPrescriptionDate(), this.pickupDate, DateUtilities.DAY_FORMAT) > 0) {
            return "A data da prescrição não pode ser maior que a data do levantamento.";
        }
        if(this.supply <= 0) return "A duração da prescrição deve ser indicada.";
        if (!Utilities.listHasElements(this.dispensedDrugs)) return "Por favor indique os medicamentos para esta dispensa.";

        return "";
    }

}
