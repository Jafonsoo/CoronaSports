package pt.ipg.coronasports.Modelos;

import android.content.ContentValues;
import android.database.Cursor;

import pt.ipg.coronasports.Bdcorona.BdTableEquipa;

public class Equipa {

    private long id;
    private String nome_equipa;
    private String modalidade;
    private String data_fundacao;
    private long nome_pais;
    private String nomePais;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome_equipa() {
        return nome_equipa;
    }

    public void setNome_equipa(String nome_equipa) {
        this.nome_equipa = nome_equipa;
    }

    public String getData_fundacao() {
        return data_fundacao;
    }

    public void setData_fundacao(String data_fundacao) {
        this.data_fundacao = data_fundacao;
    }

    public long getNome_pais() {
        return nome_pais;
    }

    public void setNome_pais(long nome_pais) {
        this.nome_pais = nome_pais;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    public String getNomePais() {
        return nomePais;
    }

    public void setNomePais(String nomePais) {
        this.nomePais = nomePais;
    }

    public ContentValues getContentValues(){
        ContentValues valores = new ContentValues();

        valores.put(BdTableEquipa.CAMPO_NOME,nome_equipa);
        valores.put(BdTableEquipa.CAMPO_MODALIDADE,modalidade);
        valores.put(BdTableEquipa.CAMPO_FUNDACAO, data_fundacao);
        valores.put(BdTableEquipa.CAMPO_PAIS,nome_pais);

        return valores;
    }

    public static Equipa fromCursor(Cursor cursor){
        long id = cursor.getLong(
                cursor.getColumnIndex(BdTableEquipa._ID)
        );
        String nome = cursor.getString(
                cursor.getColumnIndex(BdTableEquipa.CAMPO_NOME)
        );
        String data = cursor.getString(
                cursor.getColumnIndex(BdTableEquipa.CAMPO_FUNDACAO)
        );
        String modalidade = cursor.getString(
                cursor.getColumnIndex(BdTableEquipa.CAMPO_MODALIDADE)
        );
        long pais = cursor.getLong(
                cursor.getColumnIndex(BdTableEquipa.CAMPO_PAIS)
        );
        String nomePais = cursor.getString(
                cursor.getColumnIndex(BdTableEquipa.ALIAS_NOME_PAIS)
        );
        
        Equipa equipa = new Equipa();
        equipa.setId(id);
        equipa.setNome_equipa(nome);
        equipa.setData_fundacao(data);
        equipa.setModalidade(modalidade);
        equipa.setNome_pais(pais);

        equipa.nomePais=nomePais;

        return equipa;

    }
}
