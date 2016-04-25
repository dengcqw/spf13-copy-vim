package com.example.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MyDataProvider extends ContentProvider
{

    public static final Uri CONTENT_URI = Uri.parse("content://com.example.database.my_data_provider/peoples");

    private static final String TABLE_NAME = "peoples";

    // 注册和解析路径时的返回码
    private static final int PEOPLE = 1;

    private static final int PEOPLE_ID = 2;

    private static final UriMatcher mather = new UriMatcher(UriMatcher.NO_MATCH);
    static
    {
        mather.addURI(CONTENT_URI.getAuthority(), "peoples", PEOPLE);
        mather.addURI(CONTENT_URI.getAuthority(), "peoples/#", PEOPLE_ID);
    }

    private MyDBHelper helper;

    @Override
    public boolean onCreate()
    {
        this.helper = new MyDBHelper(this.getContext());
        return helper != null;
    }

    /**
     * 启动时涉及到的MIME类型值： vnd.android.cursor.dir代表值的集合
     * vnd.android.cursor.item代表一项值 最好不要默认返回空
     */
    @Override
    public String getType(Uri uri)
    {
        switch (mather.match(uri))
        {
            case PEOPLE:
                return "vnd.android.cursor.dir/peoplelist";
            case PEOPLE_ID:
                return "vnd.android.cursor.item/peopleitem";
            default:
                throw new IllegalArgumentException("uri Illegal" + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        switch (mather.match(uri))
        {
            case PEOPLE:
                return db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
            case PEOPLE_ID:
                long id = ContentUris.parseId(uri);
                String where = "_id=" + id;
                if ((selection != null) && (!"".equals(selection)))
                {
                    where = where + " and " + selection;
                }
                return db.query(TABLE_NAME, projection, where, selectionArgs, null, null, sortOrder);
            default:
                throw new IllegalArgumentException("uri Illegal" + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        long id;
        switch (mather.match(uri))
        {
            case PEOPLE:
                id = db.insert(TABLE_NAME, "_id", values);
                return ContentUris.withAppendedId(uri, id);
            case PEOPLE_ID:
                id = db.insert(TABLE_NAME, "_id", values);
                String uriPath = uri.toString();
                String path = uriPath.substring(0, uriPath.lastIndexOf("/")) + id;
                return Uri.parse(path);
            default:
                throw new IllegalArgumentException("uri Illegal" + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        switch (mather.match(uri))
        {
            case PEOPLE:
                return db.delete(TABLE_NAME, selection, selectionArgs);
            case PEOPLE_ID:
                long id = ContentUris.parseId(uri);
                String where = "_id=" + id;
                if ((selection != null) && (!"".equals(selection)))
                {
                    where = where + " and " + selection;
                }
                return db.delete(TABLE_NAME, where, selectionArgs);
            default:
                throw new IllegalArgumentException("uri Illegal" + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        switch (mather.match(uri))
        {
            case PEOPLE:
                return db.update(TABLE_NAME, values, selection, selectionArgs);
            case PEOPLE_ID:
                long id = ContentUris.parseId(uri);
                String where = "_id=" + id;
                if ((selection != null) && (!"".equals(selection)))
                {
                    where = where + " and " + selection;
                }
                return db.update(TABLE_NAME, values, where, selectionArgs);
            default:
                throw new IllegalArgumentException("uri Illegal" + uri);
        }
    }
}
