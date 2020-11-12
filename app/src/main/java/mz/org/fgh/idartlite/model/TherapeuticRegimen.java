package mz.org.fgh.idartlite.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Objects;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.common.Listble;
import mz.org.fgh.idartlite.dao.TherapeuticRegimenDaoImpl;
import mz.org.fgh.idartlite.util.Utilities;

@DatabaseTable(tableName = "therapeutic_regimen", daoClass = TherapeuticRegimenDaoImpl.class)
public class TherapeuticRegimen extends BaseModel implements Listble {

	public static final String COLUMN_ID = "id";
	public static final String COLUMN_REGIMEN_CODE = "regimen_code";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_REST_ID = "restid";

	@DatabaseField(columnName = COLUMN_ID, generatedId = true)
	private int id;

	@DatabaseField(columnName = COLUMN_REGIMEN_CODE)
	private String regimenCode;

	@DatabaseField(columnName = COLUMN_DESCRIPTION)
	private String description;

	@DatabaseField(columnName = COLUMN_REST_ID)
	private int restid;

	public int getId() {
		return id;
	}

	@Override
	public int getPosition() {
		return 0;
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

	@Override
	public int getQuantity() {
		return 0;
	}

	@Override
	public int getDrawable() {
		return 0;
	}

	@Override
	public String getCode() {
		return regimenCode;
	}

	@Override
	public int compareTo(BaseModel baseModel) {
		return 0;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getRestId() { return restid; }

	public void setRestId(int restid) { this.restid = restid; }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TherapeuticRegimen that = (TherapeuticRegimen) o;
		if (!Utilities.stringHasValue(regimenCode) && id <= 0) return false;
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
		return description;
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}
}
