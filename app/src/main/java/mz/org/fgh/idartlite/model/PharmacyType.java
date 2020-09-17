package mz.org.fgh.idartlite.model;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import mz.org.fgh.idartlite.base.BaseModel;
import mz.org.fgh.idartlite.dao.PharmacyTypeDaoImpl;

@DatabaseTable(tableName = "pharmacyType", daoClass = PharmacyTypeDaoImpl.class)
public class PharmacyType extends BaseModel {


	public static final String COLUMN_ID = "id";
	public static final String COLUMN_DESCRIPTION = "description";

	@DatabaseField(columnName = "id", generatedId = true)
	private int id;

	@DatabaseField(columnName = "description")
	private String description;

	public PharmacyType() {
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


}
