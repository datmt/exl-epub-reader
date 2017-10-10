package com.folioreader.model.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import com.folioreader.Constants;
import com.folioreader.model.HighlightImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class HighLightTable {
    public static String TABLE_NAME = "highlight_table";

    public static String ID = "_id";
    public static String COL_BOOK_ID = "bookId";
    private static String COL_CONTENT = "content";
    private static String COL_DATE = "date";
    private static String COL_TYPE = "type";
    private static String COL_PAGE_NUMBER = "page_number";
    private static String COL_PAGE_ID = "pageId";
    private static String COL_RANGY = "rangy";
    private static String COL_NOTE = "note";
    private static String COL_UUID = "uuid";

    public static String SQL_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " + ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT" + ","
            + COL_BOOK_ID + " TEXT" + ","
            + COL_CONTENT + " TEXT" + ","
            + COL_DATE + " TEXT" + ","
            + COL_TYPE + " TEXT" + ","
            + COL_PAGE_NUMBER + " INTEGER" + ","
            + COL_PAGE_ID + " TEXT" + ","
            + COL_RANGY + " TEXT" + ","
            + COL_UUID + " TEXT" + ","
            + COL_NOTE + " TEXT" + ")";

    public static String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static String TAG = HighLightTable.class.getSimpleName();

    public static ContentValues getHighlightContentValues(HighlightImpl highlightImpl) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_BOOK_ID, highlightImpl.getBookId());
        contentValues.put(COL_CONTENT, highlightImpl.getContent());
        contentValues.put(COL_DATE, getDateTimeString(highlightImpl.getDate()));
        contentValues.put(COL_TYPE, highlightImpl.getType());
        contentValues.put(COL_PAGE_NUMBER, highlightImpl.getPageNumber());
        contentValues.put(COL_PAGE_ID, highlightImpl.getPageId());
        contentValues.put(COL_RANGY, highlightImpl.getRangy());
        contentValues.put(COL_NOTE, highlightImpl.getNote());
        contentValues.put(COL_UUID, highlightImpl.getUUID());
        return contentValues;
    }


    public static ArrayList<HighlightImpl> getAllHighlights(String bookId) {
        ArrayList<HighlightImpl> highlights = new ArrayList<>();
        Cursor highlightCursor = DbAdapter.getHighLightsForBookId(bookId);
        while (highlightCursor.moveToNext()) {
            highlights.add(new HighlightImpl(highlightCursor.getInt(highlightCursor.getColumnIndex(ID)),
                    highlightCursor.getString(highlightCursor.getColumnIndex(COL_BOOK_ID)),
                    highlightCursor.getString(highlightCursor.getColumnIndex(COL_CONTENT)),
                    getDateTime(highlightCursor.getString(highlightCursor.getColumnIndex(COL_DATE))),
                    highlightCursor.getString(highlightCursor.getColumnIndex(COL_TYPE)),
                    highlightCursor.getInt(highlightCursor.getColumnIndex(COL_PAGE_NUMBER)),
                    highlightCursor.getString(highlightCursor.getColumnIndex(COL_PAGE_ID)),
                    highlightCursor.getString(highlightCursor.getColumnIndex(COL_RANGY)),
                    highlightCursor.getString(highlightCursor.getColumnIndex(COL_NOTE)),
                    highlightCursor.getString(highlightCursor.getColumnIndex(COL_UUID))));
        }
        return highlights;
    }

    public static HighlightImpl getHighlightId(int id) {
        Cursor highlightCursor = DbAdapter.getHighlightsForId(id);
        HighlightImpl highlightImpl = new HighlightImpl();
        while (highlightCursor.moveToNext()) {
            highlightImpl = new HighlightImpl(highlightCursor.getInt(highlightCursor.getColumnIndex(ID)),
                    highlightCursor.getString(highlightCursor.getColumnIndex(COL_BOOK_ID)),
                    highlightCursor.getString(highlightCursor.getColumnIndex(COL_CONTENT)),
                    getDateTime(highlightCursor.getString(highlightCursor.getColumnIndex(COL_DATE))),
                    highlightCursor.getString(highlightCursor.getColumnIndex(COL_TYPE)),
                    highlightCursor.getInt(highlightCursor.getColumnIndex(COL_PAGE_NUMBER)),
                    highlightCursor.getString(highlightCursor.getColumnIndex(COL_PAGE_ID)),
                    highlightCursor.getString(highlightCursor.getColumnIndex(COL_RANGY)),
                    highlightCursor.getString(highlightCursor.getColumnIndex(COL_NOTE)),
                    highlightCursor.getString(highlightCursor.getColumnIndex(COL_UUID)));

        }
        return highlightImpl;
    }

    public static long insertHighlight(HighlightImpl highlightImpl) {
        highlightImpl.setUUID(UUID.randomUUID().toString());
        return DbAdapter.saveHighLight(getHighlightContentValues(highlightImpl));
    }

    public static boolean deleteHighlight(String rangy) {
        String query = "SELECT " + ID + " FROM " + TABLE_NAME + " WHERE " + COL_RANGY + " = '" + rangy + "'";
        int id = DbAdapter.getIdForQuery(query);
        return id != -1 && deleteHighlight(id);
    }

    public static boolean deleteHighlight(int highlightId) {
        return DbAdapter.deleteById(TABLE_NAME, ID, String.valueOf(highlightId));
    }

    public static List<String> getHighlightsForPageId(String pageId) {
        String query = "SELECT " + COL_RANGY + " FROM " + TABLE_NAME + " WHERE " + COL_PAGE_ID + " = '" + pageId + "'";
        Cursor c = DbAdapter.getHighlightsForPageId(query, pageId);
        List<String> rangyList = new ArrayList<>();
        while (c.moveToNext()) {
            rangyList.add(c.getString(c.getColumnIndex(COL_RANGY)));
        }
        c.close();
        return rangyList;
    }

    public static boolean updateHighlight(HighlightImpl highlightImpl) {
        return DbAdapter.updateHighLight(getHighlightContentValues(highlightImpl), String.valueOf(highlightImpl.getId()));
    }

    public static String getDateTimeString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                Constants.DATE_FORMAT, Locale.getDefault());
        return dateFormat.format(date);
    }

    public static Date getDateTime(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                Constants.DATE_FORMAT, Locale.getDefault());
        Date date1 = new Date();
        try {
            date1 = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    public static HighlightImpl updateHighlightStyle(String rangy, String style) {
        String query = "SELECT " + ID + " FROM " + TABLE_NAME + " WHERE " + COL_RANGY + " = '" + rangy + "'";
        int id = DbAdapter.getIdForQuery(query);
        if (id != -1) {
            if (update(id, updateRangy(rangy, style), style.replace("highlight_", ""))) {
                return getHighlightId(id);
            }
        }
        return null;
    }

    public static HighlightImpl getHighlightForRangy(String rangy) {
        String query = "SELECT " + ID + " FROM " + TABLE_NAME + " WHERE " + COL_RANGY + " = '" + rangy + "'";
        return getHighlightId(DbAdapter.getIdForQuery(query));
    }

    private static String updateRangy(String rangy, String style) {
        /*Pattern p = Pattern.compile("\\highlight_\\w+");
        Matcher m = p.matcher(rangy);
        return m.replaceAll(style);*/
        String[] s = rangy.split("\\$");
        StringBuilder builder = new StringBuilder();
        for (String p : s) {
            if (TextUtils.isDigitsOnly(p)) {
                builder.append(p);
                builder.append("$");
            } else {
                builder.append(style);
                builder.append("$");
            }
        }
        return builder.toString();
    }

    private static boolean update(int id, String s, String color) {
        HighlightImpl highlightImpl = getHighlightId(id);
        highlightImpl.setRangy(s);
        highlightImpl.setType(color);
        return DbAdapter.updateHighLight(getHighlightContentValues(highlightImpl), String.valueOf(id));
    }

    public static void saveHighlightIfNotExists(HighlightImpl highlight) {
        String query = "SELECT " + ID + " FROM " + TABLE_NAME + " WHERE " + COL_UUID + " = '" + highlight.getUUID() + "'";
        int id = DbAdapter.getIdForQuery(query);
        if (id == -1) {
            DbAdapter.saveHighLight(getHighlightContentValues(highlight));
        }
    }
}



