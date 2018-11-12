package cn.sd.jrz.swagger.annotations;

public enum PrimitiveType {
    DATE_STRING("Date"),
    STRING("String"),
    INTEGER("Integer"),
    FLOAT("Float"),
    MAP("Map"),
    LIST_SINGLE("List"),
    LIST_DATE("List<Date>"),
    LIST_STRING("List<String>"),
    LIST_INTEGER("List<Integer>"),
    LIST_FLOAT("List<Float>"),
    LIST_MAP("List<Map>");
    private String description;

    PrimitiveType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
