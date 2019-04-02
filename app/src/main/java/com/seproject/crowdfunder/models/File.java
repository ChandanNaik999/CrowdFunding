package com.seproject.crowdfunder.models;

import android.net.Uri;
/**  Chandan - 17CO212 */
public class File {
    String filename;
    Uri path;

    public File(String filename) {
        this.filename = filename;
    }

    public File() {
    }


    public File(String filename, Uri path) {
        this.filename = filename;
        this.path = path;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Uri getPath() {
        return path;
    }

    public void setPath(Uri path) {
        this.path = path;
    }
}
