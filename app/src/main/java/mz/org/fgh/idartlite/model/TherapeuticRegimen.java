package mz.org.fgh.idartlite.model;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import mz.org.fgh.idartlite.base.BaseModel;
import mz.org.fgh.idartlite.dao.TherapeuticRegimenDaoImpl;

import java.util.Objects;

@DatabaseTable(tableName = "therapeuticRegimen", daoClass = TherapeuticRegimenDaoImpl.class)
public class TherapeuticRegimen extends BaseModel {

	public static final String COLUMN_ID = "id";
	public static final String COLUMN_REGIMEN_CODE = "regimen_code";
	public static final String COLUMN_DESCRIPTION = "description";

	@DatabaseField(columnName = "id", generatedId = true)
	private int id;

	@DatabaseField(columnName = "regimen_code")
	private String regimenCode;

	@DatabaseField(columnName = "description")
	private String description;

	public Drug drugList() {
		return null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRegimenCode() {
		return regimenCode;
	}

	public void setRegimenCode(String regimenCode) {
		this.regimenCode = regimenCode;
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
		TherapeuticRegimen that = (TherapeuticRegimen) o;
		return regimenCode.equals(that.regimenCode) &&
				description.equals(that.description);
	}

	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	@Override
	public int hashCode() {
		return Objects.hash(regimenCode, description);
	}

	@Override
	public String toString() {
		return "TherapeuticRegimen{" +
				"regimenCode='" + regimenCode + '\'' +
				", description='" + description + '\'' +
				'}';
	}
}
