package com.app.organizze.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.organizze.R;
import com.app.organizze.activity.activity.CadastroActivity;
import com.app.organizze.activity.activity.LoginActivity;
import com.app.organizze.activity.config.ConfiguracaoFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;

public class MainActivity extends IntroActivity {

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        setButtonBackVisible(false);
        setButtonNextVisible(false);

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.slide_1)
                .build()
        );

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.slide_2)
                .build()
        );

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.slide_3)
                .build()
        );

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.slide_4)
                .build()
        );

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.slide_cadastro)
                .build()
        );

    }

    @Override
    protected void onStart() {
        super.onStart();
        verificarUsuarioLogado();
    }

    public void btEntrar(View view){
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void btCadastrar(View view){
        startActivity(new Intent(this, CadastroActivity.class));
    }

    public void verificarUsuarioLogado(){

        autenticacao = ConfiguracaoFirebase.getFireBaseAutenticacao();
        //autenticacao.signOut();
        if (autenticacao.getCurrentUser() != null){
            abrirTelaPrincipal();
        }
    }

    public void abrirTelaPrincipal(){
        startActivity(new Intent(getApplicationContext(), PrincipalActivity.class));
    }

}
