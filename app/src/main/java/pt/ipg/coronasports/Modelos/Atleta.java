package pt.ipg.coronasports.Modelos;


import android.content.ContentValues;
import android.database.Cursor;

import pt.ipg.coronasports.BdCorona.BdTableAtletas;

public class Atleta {

    private long id_atleta;
    private String nome_atleta;
    private int idade_atleta;
    private String funcao;
    private String data_corona;
    private String dados_atleta;

    private long estado_atleta;
    private long equipa_atleta;
    private long pais_atleta;


    private String nomePais;
    private String nomeEquipa;
    private String nomeEstado;

    public long getId_atleta() {
        return id_atleta;
    }

    public void setId_atleta(long id_atleta) {
        this.id_atleta = id_atleta;
    }

    public String getNome_atleta() {
        return nome_atleta;
    }

    public void setNome_atleta(String nome_atleta) {
        this.nome_atleta = nome_atleta;
    }

    public int getIdade_atleta() {
        return idade_atleta;
    }

    public void setIdade_atleta(int idade_atleta) {
        this.idade_atleta = idade_atleta;
    }

    public String getData_corona() {
        return data_corona;
    }

    public void setData_corona(String data_corona) {
        this.data_corona = data_corona;
    }

    public String getDados_atleta() {
        return dados_atleta;
    }

    public void setDados_atleta(String dados_atleta) {
        this.dados_atleta = dados_atleta;
    }

    public long getEstado_atleta() {
        return estado_atleta;
    }

    public void setEstado_atleta(long estado_atleta) {
        this.estado_atleta = estado_atleta;
    }

    public long getEquipa_atleta() {
        return equipa_atleta;
    }

    public void setEquipa_atleta(long equipa_atleta) {
        this.equipa_atleta = equipa_atleta;
    }

    public long getPais_atleta() {
        return pais_atleta;
    }

    public void setPais_atleta(long pais_atleta) {
        this.pais_atleta = pais_atleta;
    }

    public String getNomePaisAtleta() {
        return nomePais;
    }

    public void setNomePaisAtelta(String nomePais) {
        this.nomePais = nomePais;
    }

    public String getNomeEquipaAtleta() {
        return nomeEquipa;
    }

    public void setNomeEquipaAtelta(String nomeEquipa) {
        this.nomeEquipa = nomeEquipa;
    }

    public String getNomeEstado() {
        return nomeEstado;
    }

    public void setNomeEstado(String nomeEstado) {
        this.nomeEstado = nomeEstado;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public ContentValues getContentValues() {
        ContentValues valores = new ContentValues();

        valores.put(BdTableAtletas.CAMPO_NOME, nome_atleta);
        valores.put(BdTableAtletas.CAMPO_IDADE, idade_atleta);
        valores.put(BdTableAtletas.CAMPO_DATA, data_corona);
        valores.put(BdTableAtletas.CAMPO_DADOS, dados_atleta);
        valores.put(BdTableAtletas.CAMPO_ESTADO, estado_atleta);
        valores.put(BdTableAtletas.CAMPO_EQUIPA,equipa_atleta);
        valores.put(BdTableAtletas.CAMPO_PAIS,pais_atleta);
        valores.put(BdTableAtletas.CAMPO_FUNCAO, funcao);

        return valores;
    }

    public static Atleta fromCursor(Cursor cursor) {
        long id = cursor.getLong(
                cursor.getColumnIndex(BdTableAtletas._ID)
        );

        String nome = cursor.getString(
                cursor.getColumnIndex(BdTableAtletas.CAMPO_NOME)
        );

        int idade = cursor.getInt(
                cursor.getColumnIndex(BdTableAtletas.CAMPO_IDADE)
        );

        String data = cursor.getString(
                cursor.getColumnIndex(BdTableAtletas.CAMPO_DATA)
        );

        String dados = cursor.getString(
                cursor.getColumnIndex(BdTableAtletas.CAMPO_DADOS)
        );

        long estado = cursor.getLong(
                cursor.getColumnIndex(BdTableAtletas.CAMPO_ESTADO)
        );

        long equipa = cursor.getLong(
                cursor.getColumnIndex(BdTableAtletas.CAMPO_EQUIPA)
        );

        long pais = cursor.getLong(
                cursor.getColumnIndex(BdTableAtletas.CAMPO_PAIS)
        );

        String funcao = cursor.getString(
                cursor.getColumnIndex(BdTableAtletas.CAMPO_FUNCAO)
        );

        String nomePais = cursor.getString(
                cursor.getColumnIndex(BdTableAtletas.ALIAS_NOME_PAIS)
        );
        String nomeEquipa = cursor.getString(
                cursor.getColumnIndex(BdTableAtletas.ALIAS_NOME_EQUIPA)
        );



        Atleta atleta = new Atleta();

        atleta.setId_atleta(id);
        atleta.setNome_atleta(nome);
        atleta.setData_corona(data);
        atleta.setIdade_atleta(idade);
        atleta.setDados_atleta(dados);
        atleta.setEstado_atleta(estado);
        atleta.setEquipa_atleta(equipa);
        atleta.setPais_atleta(pais);
        atleta.setFuncao(funcao);

        atleta.nomePais = nomePais;
        atleta.nomeEquipa = nomeEquipa;


        return atleta;
    }




}





