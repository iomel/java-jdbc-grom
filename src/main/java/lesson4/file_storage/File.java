package lesson4.file_storage;

public class File {

    private long id;
    private String name;
    private String format;
    private long size;
    private long storageId;

    public File(long id, String name, String format, long size) {
        if (name != null && name.length() > 10)
            return;
        this.id = id;
        this.name = name;
        this.format = format;
        this.size = size;
    }

    public long getStorageId() {
        return storageId;
    }

    public void setStorageId(long storageId) {
        this.storageId = storageId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", format='" + format + '\'' +
                ", size=" + size +
                ", storageId=" + storageId +
                '}';
    }

    public boolean isEmpty ()
    {
        if (id==0 && name == null && format == null && size == 0)
            return true;
        return false;
    }

}
