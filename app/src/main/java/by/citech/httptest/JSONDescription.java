package by.citech.httptest;



class JSONDescription {

    private String type;
    private String description;


    public JSONDescription(String type, String description) {
        this.type = type;
        this.description = description;
    }


    public JSONDescription() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
