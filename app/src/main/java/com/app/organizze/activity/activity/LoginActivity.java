package com.app.organizze.activity.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.organizze.R;
import com.app.organizze.activity.config.ConfiguracaoFirebase;
import com.app.organizze.activity.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {

    private EditText campoEmail, campoSenha;
    private Button botaoEntrar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        campoEmail = findViewById(R.id.editEmailLogin);
        campoSenha = findViewById(R.id.editSenhaLogin);
        botaoEntrar = findViewById(R.id.buttonLogin);

        botaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textEmail = campoEmail.getText().toString();
                String textSenha = campoSenha.getText().toString();

                if ( !textEmail.isEmpty() ){
                    if ( !textSenha.isEmpty() ){

                        usuario = new Usuario();
                        usuario.setEmail(textEmail);
                        usuario.setSenha(textSenha);
                        validarLogin();

                    }else{
                        Toast.makeText(getApplicationContext(), "Por favor, insira sua senha!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Por favor, insira seu e-mail!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void validarLogin(){

        autenticacao = ConfiguracaoFirebase.getFireBaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    abrirTelaPrincipal();

                }else{

                    String exececao;
                    try {
                        throw task.getException();
                    }catch ( FirebaseAuthInvalidUserException e){
                        exececao = "Usuário não está cadastrado.";
                    }catch ( FirebaseAuthInvalidCredentialsException e){
                        exececao = "E-mail e senha não correspodem a um usuário cadastrado.";
                    }catch (Exception e){
                        exececao = "Erro ao cadastrar usuário: " + e.getMessage();
                        e.getStackTrace();
                    }

                    Toast.makeText(getApplicationContext(), exececao, Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void abrirTelaPrincipal(){
        startActivity(new Intent(getApplicationContext(), PrincipalActivity.class));
        finish();
    }

}
