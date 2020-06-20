package pt.ipg.coronasports.BdCorona;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

public class BdTableAtletas implements BaseColumns {

    public static final String NOME_TABELA = "atletas";

    public static final String CAMPO_NOME = "nome_atleta";
    public static final String CAMPO_IDADE = "idade_atleta";
    public static final String CAMPO_DATA = "data_corona";
    public static final String CAMPO_DADOS = "dados_atleta";
    public static final String CAMPO_ESTADO = "estado_atleta";
    public static final String CAMPO_EQUIPA = "equipa_atleta";
    public static final String CAMPO_PAIS = "pais_atleta";
    public static final String CAMPO_FUNCAO = "funcao";

    public static final String ALIAS_NOME_PAIS = "nome_pais";
    public static final String CAMPO_NOME_PAIS = BdTablePaises.NOME_TABELA + "." + BdTablePaises.CAMPO_NOME + " AS " + ALIAS_NOME_PAIS; // tabela de categorias (só de leitura)

    public static final String ALIAS_NOME_EQUIPA = "nome_equipa";
    public static final String CAMPO_NOME_EQUIPA = BdTableEquipa.NOME_TABELA + "." + BdTableEquipa.CAMPO_NOME + " AS " + ALIAS_NOME_EQUIPA; // tabela de categorias (só de leitura)


    public static final String[] TODAS_COLUNAS = new String[] { NOME_TABELA + "." + _ID, CAMPO_NOME, CAMPO_IDADE, CAMPO_DATA, CAMPO_NOME_PAIS, CAMPO_DADOS, CAMPO_ESTADO, CAMPO_NOME_EQUIPA, CAMPO_EQUIPA, CAMPO_PAIS, CAMPO_FUNCAO };


    private SQLiteDatabase db;

    public BdTableAtletas(SQLiteDatabase db) {
        this.db = db;
    }

    public void cria() {
        db.execSQL(
                "CREATE TABLE " + NOME_TABELA + "(" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        CAMPO_NOME + " TEXT NOT NULL," +
                        CAMPO_IDADE + " INTEGER NOT NULL," +
                        CAMPO_DATA + " DATE NOT NULL," +
                        CAMPO_DADOS + " TEXT NOT NULL," +
                        CAMPO_ESTADO + " TEXT," +
                        CAMPO_EQUIPA + " TEXT NOT NULL," +
                        CAMPO_PAIS + " TEXT NOT NULL," +
                        CAMPO_FUNCAO + " TEXT NOT NULL," +
                        " FOREIGN KEY (" + CAMPO_EQUIPA + ") REFERENCES " + BdTableEquipa.NOME_TABELA + "(" + BdTableEquipa._ID + ")," +
                        " FOREIGN KEY (" + CAMPO_PAIS + ") REFERENCES " + BdTablePaises.NOME_TABELA + "(" + BdTablePaises._ID + ")" +

                        ")"
        );
    }

    public Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        String colunasSelect = TextUtils.join(",", columns);

                String sql = "SELECT " + colunasSelect + " FROM " +
                        NOME_TABELA + " INNER JOIN " + BdTablePaises.NOME_TABELA +
                        " ON " + NOME_TABELA + "." + CAMPO_PAIS +
                        " = " + BdTablePaises.NOME_TABELA + "." + BdTablePaises._ID +
                        " INNER JOIN " + BdTableEquipa.NOME_TABELA +
                        " ON " + NOME_TABELA + "." + CAMPO_EQUIPA +
                        " = " + BdTableEquipa.NOME_TABELA + "." + BdTableEquipa._ID
                ;
                if (selection != null) {
                    sql += " AND " + selection;
                }
                Log.d("Tabela Equipas", "query: " + sql);


        return db.rawQuery(sql,selectionArgs);
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
