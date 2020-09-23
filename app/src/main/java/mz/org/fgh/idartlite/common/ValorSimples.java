package mz.org.fgh.idartlite.common;

public class ValorSimples {

    private int id;

    private String description;

    private String code;

    public ValorSimples(String description) {
        this.description = description;
    }

    public ValorSimples() {
    }

    public ValorSimples(int id, String description) {
        this.id = id;
        this.description = description;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static ValorSimples fastCreate(String description){
        return new ValorSimples(description);
    }

    public static ValorSimples fastCreate(int id, String description){
        return new ValorSimples(id, description);
    }

    @Override
    public String toString() {
        return description;
    }
}
