package com.datmt.android.read.helper;

import android.os.Environment;
import android.util.Log;

import com.datmt.android.read.model.Book;
import com.folioreader.FolioReader;

import org.readium.r2.shared.Metadata;
import org.readium.r2.streamer.parser.EpubParser;
import org.readium.r2.streamer.parser.PubBox;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import kotlin.KotlinNullPointerException;

public class EpubScanner {

    private List<Book> files = new ArrayList<>();

    private static String LOG_TAG = EpubParser.class.getName();
    public EpubScanner() {
        String sdCard = Environment.getExternalStorageDirectory().toString();
        scanForEpub(sdCard);

    }

    public List<Book> getFiles() {
        return files;
    }

    public void scanForEpub(String DirectoryPath) {
        File dir = new File(DirectoryPath);

        if (!dir.isDirectory()) {
            return;
        }

        File[] files = dir.listFiles();

        if (files == null) {
            return;
        }

        for (File f : files) {
            if (f.isDirectory()) {
                scanForEpub(f.getAbsolutePath());
            } else if (f.isFile() && f.getAbsolutePath().endsWith(".epub")) {
                try {
                    EpubParser parser = new EpubParser();
                    PubBox box = parser.parse(f.getAbsolutePath(), "");

                    Book book = new Book();

                    if (box == null || box.getPublication() == null) {
                        return;
                    }

                    Metadata meta = box.getPublication().getMetadata();
                    book.setBookTitle(meta.getTitle());
                    meta.getAuthors();
                    book.setBookAuthor(meta.getAuthors().size() > 0 ? meta.getAuthors().get(0).getName() : "");
                    book.setLocationOnDisk(f.getAbsolutePath());


                    this.files.add(book);

                } catch (Exception ex) {
                    Log.e(LOG_TAG, "NUll of null OMG");
                }
           }

        }
    }
}
