package uploadable.models;

public class Status {

    public static final int UPLOADING = 0;
    public static final int PROCESSING = 1;
    public static final int COMPLETED = 2;
    public static final int ERROR = 3;

    private int status;
    private String message;
    private String url_root;
    private String[] formats;

    public int getStatusCode() {
        return status;
    }

    public String getErrorMessage() {
        return message;
    }

    public String getUrlRoot() {
        return url_root;
    }

    public String[] getFormats() {
        return formats;
    }

}