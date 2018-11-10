package cn.sd.jrz.swagger.annotations;

public enum PrimitiveType {
    DATE_STRING("date"),
    STRING("string"),
    INTEGER("int"),
    FLOAT("float"),
    MAP("map"),
    LIST_MAP("list<map>"),
    LIST_SINGLE("list");
    private String description;

    PrimitiveType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
