package mz.org.fgh.idartlite.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Objects;

import mz.org.fgh.idartlite.adapter.recyclerview.listable.Listble;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.dao.drug.TherapeuticLineDaoImpl;
import mz.org.fgh.idartlite.util.Utilities;

@DatabaseTable(tableName = "therapeutic_line", daoClass = TherapeuticLineDaoImpl.class)
public class TherapeuticLine extends BaseModel implements Listble {

	public static final String COLUMN_ID = "id";
	public static final String COLUMN_CODE = "code";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_REST_ID = "restid";

	@DatabaseField(columnName = COLUMN_ID, generatedId = true)
	private int id;

	@DatabaseField(columnName = COLUMN_CODE)
	private String code;

	@DatabaseField(columnName = COLUMN_DESCRIPTION)
	private String description;

	@DatabaseField(columnName = COLUMN_REST_ID)
	private int restid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public int getDrawable() {
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
		TherapeuticLine that = (TherapeuticLine) o;
		if (!Utilities.stringHasValue(code) && id <= 0) return false;
		return code.equals(that.code) &&
				description.equals(that.description);
	}

	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	@Override
	public int hashCode() {
		return Objects.hash(code, description);
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
