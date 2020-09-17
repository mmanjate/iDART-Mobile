package mz.org.fgh.idartlite.model;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

import mz.org.fgh.idartlite.dao.DispenseDaoImpl;

@DatabaseTable(tableName = "Dispense", daoClass = DispenseDaoImpl.class)
public class Dispense {

    public static final String COLUMN_PICKUP_DATE = "pickup_date";
    public static final String COLUMN_SUPPLY = "supply";
    public static final String COLUMN_NEXT_PICKUP_DATE = "next_pickup_date";
    public static final String COLUMN_PRESCRIPTION = "prescription_id";
    public static final String COLUMN_UUID = "uuid";


    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = "pickup_date")
    private Date pickupDate;

    @DatabaseField(columnName = "supply")
    private int supply;

    @DatabaseField(columnName = "next_pickup_date")
    private Date nextPickupDate;

    //private Prescription prescription_id;

    @DatabaseField(columnName = "uuid")
    private String uuid;

}
