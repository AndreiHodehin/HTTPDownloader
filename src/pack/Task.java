package pack;

public class Task  {
    private String path;
    private String nameOfFile;

    public Task(String path, String nameOfFile) {
        this.path = path;
        this.nameOfFile = nameOfFile;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNameOfFile() {
        return nameOfFile;
    }

    public void setNameOfFile(String nameOfFile) {
        this.nameOfFile = nameOfFile;
    }



}
