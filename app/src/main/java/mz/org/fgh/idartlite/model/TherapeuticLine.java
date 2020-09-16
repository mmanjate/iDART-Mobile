package mz.org.fgh.idartlite.model;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.j256.ormlite.field.DatabaseField;
import mz.org.fgh.idartlite.base.BaseModel;

import java.util.Objects;

public class TherapeuticLine extends BaseModel {

	public static final String COLUMN_ID = "id";
	public static final String COLUMN_CODE = "code";
	public static final String COLUMN_DESCRIPTION = "description";

	@DatabaseField(columnName = "id", id = true)
	private int id;

	@DatabaseField(columnName = "code")
	private String code;

	@DatabaseField(columnName = "description")
	private String description;

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

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TherapeuticLine that = (TherapeuticLine) o;
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
		return "TherapeuticLine{" +
				"code='" + code + '\'' +
				", description='" + description + '\'' +
				'}';
	}
}
