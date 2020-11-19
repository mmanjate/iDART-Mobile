package mz.org.fgh.idartlite.model;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Objects;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.dao.clinic.PharmacyTypeDaoImpl;

@DatabaseTable(tableName = "pharmacy_type", daoClass = PharmacyTypeDaoImpl.class)
public class PharmacyType extends BaseModel {


	public static final String COLUMN_ID = "id";
	public static final String COLUMN_DESCRIPTION = "description";

	@DatabaseField(columnName = COLUMN_ID, generatedId = true)
	private int id;

	@DatabaseField(columnName = COLUMN_DESCRIPTION)
	private String description;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PharmacyType that = (PharmacyType) o;
		return description.equals(that.description);
	}

	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	@Override
	public int hashCode() {
		return Objects.hash(description);
	}

	@Override
	public String toString() {
		return "PharmacyType{" +
				"description='" + description + '\'' +
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
}
