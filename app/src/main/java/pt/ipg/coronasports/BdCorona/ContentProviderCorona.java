package pt.ipg.coronasports.BdCorona;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ContentProviderCorona extends ContentProvider {

    public static final String AUTHORITY = "pt.ipg.equipasfutebol.Bdcorona";
    public static final String PAISES = "paises";
    public static final String EQUIPAS = "equipas";
    public static final String ATLETAS = "atletas";


    private static final Uri ENDERECO_BASE = Uri.parse("content://" + AUTHORITY);
    public static final Uri ENDERECO_PAISES = Uri.withAppendedPath(ENDERECO_BASE, PAISES);
    public static final Uri ENDERECO_EQUIPAS = Uri.withAppendedPath(ENDERECO_BASE, EQUIPAS);
    public static final Uri ENDERECO_ATLETAS = Uri.withAppendedPath(ENDERECO_BASE, ATLETAS);

    public static final int URI_PAIS = 100;
    public static final int URI_PAIS_ESPECÍFICO = 101;
    public static final int URI_EQUIPAS = 200;
    public static final int URI_EQUIPAS_ESPECÍFICO = 201;
    public static final int URI_ATLETAS = 300;
    public static final int URI_ATLETAS_ESPECIFICA = 301;

    public static final String UNICO_ITEM = "vnd.android.cursor.item/";
    public static final String MULTIPLOS_ITEMS = "vnd.android.cursor.dir/";

    private BdCoronaOpenHelper bdCoronaOpenHelper;

    private UriMatcher getUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(AUTHORITY, PAISES, URI_PAIS);
        uriMatcher.addURI(AUTHORITY, PAISES + "/#", URI_PAIS_ESPECÍFICO);
        uriMatcher.addURI(AUTHORITY, EQUIPAS, URI_EQUIPAS);
        uriMatcher.addURI(AUTHORITY, EQUIPAS + "/#", URI_EQUIPAS_ESPECÍFICO);
        uriMatcher.addURI(AUTHORITY, ATLETAS, URI_ATLETAS);
        uriMatcher.addURI(AUTHORITY, ATLETAS + "/#", URI_ATLETAS_ESPECIFICA);


        return uriMatcher;
    }


    @Override
    public boolean onCreate() {
        bdCoronaOpenHelper = new BdCoronaOpenHelper(getContext());

        return true;
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase bd = bdCoronaOpenHelper.getReadableDatabase();

        String id = uri.getLastPathSegment();

        switch (getUriMatcher().match(uri)) {
            case URI_PAIS:
                return new BdTablePaises(bd).query(projection, selection, selectionArgs, null, null, sortOrder);

            case URI_PAIS_ESPECÍFICO:
                return new BdTablePaises(bd).query(projection, BdTablePaises._ID + "=?", new String[] { id }, null, null, null);
            case URI_EQUIPAS:
                return new BdTableEquipa(bd).query(projection, selection, selectionArgs, null, null, sortOrder);

            case URI_EQUIPAS_ESPECÍFICO:
                return  new BdTableEquipa(bd).query(projection, BdTableEquipa.NOME_TABELA + "." + BdTableEquipa._ID + "=?", new String[] { id }, null, null, null);
            case URI_ATLETAS:
                return new BdTableAtletas(bd).query(projection, selection, selectionArgs, null, null, sortOrder);

            case URI_ATLETAS_ESPECIFICA:
                return  new BdTableAtletas(bd).query(projection, BdTableAtletas.NOME_TABELA + "." + BdTableAtletas._ID + "=?", new String[] { id }, null, null, null);
            default:
                throw new UnsupportedOperationException("URI inválida (QUERY): " + uri.toString());
        }
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (getUriMatcher().match(uri)) {
            case URI_PAIS:
                return MULTIPLOS_ITEMS + PAISES;
            case URI_PAIS_ESPECÍFICO:
                return UNICO_ITEM + PAISES;
            case URI_EQUIPAS:
                return MULTIPLOS_ITEMS + EQUIPAS;
            case URI_EQUIPAS_ESPECÍFICO:
                return UNICO_ITEM + EQUIPAS;
            case URI_ATLETAS:
                return MULTIPLOS_ITEMS + ATLETAS;
            case URI_ATLETAS_ESPECIFICA:
                return UNICO_ITEM + ATLETAS;
            default:
                return null;
        }
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase bd = bdCoronaOpenHelper.getWritableDatabase();

        long id = -1;

        switch (getUriMatcher().match(uri)) {
            case URI_PAIS:
                id = new BdTablePaises(bd).insert(values);
                break;
            case URI_EQUIPAS:
                id = new BdTableEquipa(bd).insert(values);
                break;
            case URI_ATLETAS:
                id = new BdTableAtletas(bd).insert(values);
                break;

            default:
                throw new UnsupportedOperationException("URI inválida (INSERT):" + uri.toString());
        }

        if (id == -1) {
            throw new SQLException("Não foi possível inserir o registo");
        }

        return Uri.withAppendedPath(uri, String.valueOf(id));
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase bd = bdCoronaOpenHelper.getWritableDatabase();

        String id = uri.getLastPathSegment();

        switch (getUriMatcher().match(uri)) {
            case URI_PAIS_ESPECÍFICO:
                return new BdTablePaises(bd).delete( BdTablePaises._ID + "=?", new String[] {id});
            case URI_EQUIPAS_ESPECÍFICO:
                return new BdTableEquipa(bd).delete(BdTableEquipa._ID + "=?", new String[] {id});
            case URI_ATLETAS_ESPECIFICA:
                return new BdTableAtletas(bd).delete(BdTableAtletas._ID + "=?", new String[] {id});
            default:
                throw new UnsupportedOperationException("URI inválida (DELETE): " + uri.toString());
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase bd = bdCoronaOpenHelper.getWritableDatabase();

        String id = uri.getLastPathSegment();

        switch (getUriMatcher().match(uri)) {
            case URI_PAIS_ESPECÍFICO:
                return new BdTablePaises(bd).update(values, BdTablePaises._ID + "=?", new String[] {id});
            case URI_EQUIPAS_ESPECÍFICO:
                return new BdTableEquipa(bd).update(values, BdTableEquipa._ID + "=?", new String[] {id});
            case URI_ATLETAS_ESPECIFICA:
                return new BdTableAtletas(bd).update(values,BdTableAtletas._ID + "=?", new String[] {id});
            default:
                throw new UnsupportedOperationException("URI inválida (UPDATE): " + uri.toString());
        }
    }
}
