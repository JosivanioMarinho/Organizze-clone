package com.app.organizze.activity.model;

import com.app.organizze.activity.config.ConfiguracaoFirebase;
import com.app.organizze.activity.helper.Base64Custom;
import com.app.organizze.activity.helper.DateCustom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Movimentacao {

    private String data;
    private String categoria;
    private String descricao;
    private String tipo;
    private double valor;
    private String key;

    public Movimentacao() {
    }

    public void salvar(String dataEscolhida){

        FirebaseAuth auth = ConfiguracaoFirebase.getFireBaseAutenticacao();//para recuperar o email
        String idUsuario = Base64Custom.codificarBase64(auth.getCurrentUser().getEmail());//ainda recuperando email
        String data = DateCustom.mesAnoDataEscolhida(dataEscolhida);//recuperando a data para o nó de datas
        DatabaseReference reference = ConfiguracaoFirebase.getFirebaseDatabase();
        reference.child("movimentacao")
                .child(idUsuario)
                .child(data)
                .push()
                .setValue(this);

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
