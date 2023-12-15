package td.wallet.models;

public class Category {
    private long id;
    private String name;
    private String display_name;

    public Category(long id, String name, String display_name) {
        this.id = id;
        this.name = name;
        this.display_name = display_name;
    }

    public Category( String name, String display_name) {
        this.id = 0;
        this.name = name;
        this.display_name = display_name;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", display_name='" + display_name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }
}
