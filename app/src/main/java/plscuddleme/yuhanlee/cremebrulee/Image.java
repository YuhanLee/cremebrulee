package plscuddleme.yuhanlee.cremebrulee;

/**
 * Created by yuhanlee on 2018-03-23.
 */

public class Image {
    private String id;
    private String name;
    private String preference;

    public Image(String id, String name, String preference) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPreference() {
        return preference;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPreference(String preference) {
        this.preference = preference;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
