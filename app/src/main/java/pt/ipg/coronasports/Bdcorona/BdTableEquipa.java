package pt.ipg.coronasports.Bdcorona;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

public class BdTableEquipa implements BaseColumns {
    public static final String NOME_TABELA = "equipa";

    public static final String CAMPO_NOME = "nome_equipa";
    public static final String CAMPO_MODALIDADE = "modalidade_equipa";
    public static final String CAMPO_FUNDACAO = "fundacao_equipa";
    public static final String CAMPO_PAIS = "pais_equipa";


    public static final String ALIAS_NOME_PAIS = "nome_pais";
    public static final String CAMPO_NOME_PAIS = BdTablePaises.NOME_TABELA + "." + BdTablePaises.CAMPO_NOME + " AS " + ALIAS_NOME_PAIS; // tabela de categorias (só de leitura)

    public static final String[] TODAS_COLUNAS = new String[] { NOME_TABELA + "." + _ID, CAMPO_NOME, CAMPO_MODALIDADE, CAMPO_PAIS, CAMPO_NOME_PAIS, CAMPO_FUNDACAO};


    private SQLiteDatabase db;

    public BdTableEquipa(SQLiteDatabase db) {
        this.db = db;
    }

    public void cria() {
        db.execSQL(
                "CREATE TABLE " + NOME_TABELA + "(" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        CAMPO_NOME + " TEXT NOT NULL," +
                        CAMPO_MODALIDADE + " TEXT NOT NULL," +
                        CAMPO_FUNDACAO + " TEXT NOT NULL," +
                        CAMPO_PAIS + " TEXT NOT NULL," +
                        " FOREIGN KEY (" + CAMPO_PAIS + ") REFERENCES " + BdTablePaises.NOME_TABELA + "(" + BdTablePaises._ID + ")" +

                        ")"
        );
    }



    public Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {

        String colunasSelect = TextUtils.join(",", columns);

        String sql = "SELECT " + colunasSelect + " FROM " +
                NOME_TABELA + " INNER JOIN " + BdTablePaises.NOME_TABELA +
                " WHERE " + NOME_TABELA + "." + CAMPO_PAIS + " = " + BdTablePaises.NOME_TABELA +
                "." + BdTablePaises._ID
                ;


        if (selection != null) {
            sql += " AND " + selection;
        }

        Log.d("Tabela Equipas", "query: " + sql);

        return db.rawQuery(sql, selectionArgs);
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
