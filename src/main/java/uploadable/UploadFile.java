package uploadable;

import java.io.File;

import retrofit.mime.TypedFile;

public class UploadFile extends TypedFile {
    /* 
        This is just a proxy class so clients of this
        library don't have to use retrofit.mime.TypedFile
        directly
    */

    public UploadFile(String mimeType, File file) {
        super(mimeType, file);
    }


}