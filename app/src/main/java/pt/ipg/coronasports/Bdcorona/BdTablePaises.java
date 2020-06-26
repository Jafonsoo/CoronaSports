package pt.ipg.coronasports.Bdcorona;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTablePaises implements BaseColumns {
    public static final String NOME_TABELA = "paises";

    public static final String CAMPO_NOME = "nome_pais";
    public static final String CAMPO_SUSPEITOS = "suspeitos_pais";
    public static final String CAMPO_INFETADOS = "infetados_pais";
    public static final String CAMPO_MORTOS = "mortos_pais";
    public static final String CAMPO_RECUPERADOS = "recuperados_pais";


    public static final String[] TODAS_COLUNAS = new String[] { _ID, CAMPO_NOME, CAMPO_SUSPEITOS, CAMPO_INFETADOS, CAMPO_MORTOS, CAMPO_RECUPERADOS};

    private SQLiteDatabase db;

    public BdTablePaises(SQLiteDatabase db) {
        this.db = db;
    }

    public void cria() {
        db.execSQL(
                "CREATE TABLE " + NOME_TABELA + "(" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        CAMPO_NOME + " TEXT NOT NULL, " +
                        CAMPO_SUSPEITOS + " INT NOT NULL, " +
                        CAMPO_INFETADOS + " INT NOT NULL, " +
                        CAMPO_MORTOS + " INT NOT NULL, " +
                        CAMPO_RECUPERADOS + " INT NOT NULL " +
                        ")"
        );
    }

    public Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, CAMPO_INFETADOS + " DESC");
    }

    public long insert(ContentValues values) {
        return db.insert(NOME_TABELA, null, values);
    }

    public int update(ContentValues values, String whereClause, String [] whereArgs) {
        return db.update(NOME_TABELA, values, whereClause, whereArgs);
    }

    public int delete(String whereClause, String[] whereArgs) {
        return db.delete(NOME_TABELA, whereClause, whereArgs);
    }
}
