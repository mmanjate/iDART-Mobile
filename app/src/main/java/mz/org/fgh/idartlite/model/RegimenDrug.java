package mz.org.fgh.idartlite.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Regimen_Drug")
public class RegimenDrug {


    @DatabaseField(columnName = "id", id = true)
    private int id;

    //@DatabaseField(canBeNull = false, foreign = true)
    //private TherapeuticRegimen regimen;


    @DatabaseField(canBeNull = false, foreign = true)
    private Drug drug;

}
