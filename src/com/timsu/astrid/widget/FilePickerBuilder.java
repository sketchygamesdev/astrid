package com.timsu.astrid.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FilePickerBuilder extends AlertDialog.Builder implements DialogInterface.OnClickListener {

    public interface OnFilePickedListener {
        void onFilePicked(String filePath);
    }

    private OnFilePickedListener callback;
    private String[] files;
    private String path;
    private FilenameFilter filter;

    public FilePickerBuilder(Context ctx, String title, File path, OnFilePickedListener callback) {
        super(ctx);
        filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String s) {
                File file = new File(dir, s);
                return file.isFile();
            }
        };
        setTitle(title);
        setPath(path);
        this.callback = callback;
    }

    public void setFilter(FilenameFilter filter) {
        this.filter = filter;
    }

    private void setPath(File path) {
        this.path = path.getAbsolutePath();
        // Reverse the order of the file list so newest timestamped file is first.
        List<String> fileList = Arrays.asList(path.list(filter));
        Collections.sort(fileList);
        Collections.reverse(fileList);
        files = (String[])fileList.toArray();
        setItems(files, this);
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if (callback != null) {
            callback.onFilePicked(path + "/" + files[i]);
        }
    }
}
