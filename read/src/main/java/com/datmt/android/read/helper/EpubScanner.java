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
import java.util.Locale;

import kotlin.KotlinNullPointerException;

public class EpubScanner {

    private static final List<Book> files = new ArrayList<>();

    private static final String LOG_TAG = EpubScanner.class.getName();
    public EpubScanner(String rootDir) {
        Log.i(LOG_TAG, "Start scanning dir: " + rootDir);
        scanForEpub(rootDir);
    }

    public EpubScanner() {
        String sdCard = Environment.getExternalStorageDirectory().toString();
        Log.i(LOG_TAG, "Start scanning sdCard: " + sdCard);
        scanForEpub(sdCard);
    }

    public List<Book> getFiles() {
        return files;
    }

    public void scanForEpub(String directoryPath) {
        File dir = new File(directoryPath);

        if (!dir.isDirectory()) {

            Log.i(LOG_TAG, "Not a dir: " + directoryPath);
            return;
        }

        File[] files = dir.listFiles();

        if (files == null) {
            Log.i(LOG_TAG, "No files in " + directoryPath);
            return;
        }

        Log.i(LOG_TAG, "Found " + files.length + " files in : " + directoryPath);
        for (File f : files) {
            if (f.isDirectory()) {
                scanForEpub(f.getAbsolutePath());
            } else if (f.isFile() && f.getAbsolutePath().toLowerCase(Locale.ROOT).endsWith(".epub")) {
                try {
                    Log.i(LOG_TAG, "Found file: " + f.getAbsolutePath());
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


                    EpubScanner.files.add(book);

                } catch (Exception ex) {
                    Log.e(LOG_TAG, "NUll of null OMG");
                }
            }

        }
    }
}
