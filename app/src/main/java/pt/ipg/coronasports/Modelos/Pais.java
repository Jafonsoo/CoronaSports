package pt.ipg.coronasports.Modelos;

import android.content.ContentValues;
import android.database.Cursor;

import pt.ipg.coronasports.Bdcorona.BdTablePaises;

public class Pais {

    private long id;
    private String nome_pais;
    private int num_suspeito;
    private int num_obitos;
    private int num_infetados;
    private int num_recuperados;



    public int getNum_suspeito() {
        return num_suspeito;
    }

    public void setNum_suspeito(int num_suspeito) {
        this.num_suspeito = num_suspeito;
    }

    public int getNum_infetados() {
        return num_infetados;
    }

    public void setNum_infetados(int num_infetados) {
        this.num_infetados = num_infetados;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome_pais() {
        return nome_pais;
    }

    public void setNome_pais(String nome_pais) {
        this.nome_pais = nome_pais;
    }

    public int getNum_obitos() {
        return num_obitos;
    }

    public void setNum_obitos(int num_obitos) {
        this.num_obitos = num_obitos;
    }

    public int getNum_recuperados() {
        return num_recuperados;
    }

    public void setNum_recuperados(int num_recuperados) {
        this.num_recuperados = num_recuperados;
    }

    public ContentValues getContentValues(){
        ContentValues valores = new ContentValues();

        valores.put(BdTablePaises.CAMPO_NOME, nome_pais);
        valores.put(BdTablePaises.CAMPO_MORTOS, num_obitos);
        valores.put(BdTablePaises.CAMPO_INFETADOS, num_infetados);
        valores.put(BdTablePaises.CAMPO_SUSPEITOS,num_suspeito);
        valores.put(BdTablePaises.CAMPO_RECUPERADOS,num_recuperados);



        return valores;
    }


    public static Pais fromCursor(Cursor cursor) {
        long id = cursor.getLong(
                cursor.getColumnIndex(BdTablePaises._ID)
        );

        String nome = cursor.getString(
                cursor.getColumnIndex(BdTablePaises.CAMPO_NOME)
        );

        int mortos = cursor.getInt(
                cursor.getColumnIndex(BdTablePaises.CAMPO_MORTOS)
        );


        int infetados = cursor.getInt(
                cursor.getColumnIndex(BdTablePaises.CAMPO_INFETADOS)
        );

        int suspeitos = cursor.getInt(
                cursor.getColumnIndex(BdTablePaises.CAMPO_SUSPEITOS)
        );
        int recuperados = cursor.getInt(
                cursor.getColumnIndex(BdTablePaises.CAMPO_RECUPERADOS)
        );



        Pais pais = new Pais();

        pais.setId(id);
        pais.setNome_pais(nome);
        pais.setNum_obitos(mortos);
        pais.setNum_infetados(infetados);
        pais.setNum_recuperados(recuperados);
        pais.setNum_suspeito(suspeitos);


        return pais;
    }
}
