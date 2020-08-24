package com.app.organizze.activity.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.organizze.R;
import com.app.organizze.activity.config.ConfiguracaoFirebase;
import com.app.organizze.activity.helper.Base64Custom;
import com.app.organizze.activity.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastroActivity extends AppCompatActivity {

    private EditText campoNome, campoEmail, campoSenha;
    private Button botaoCadastrar;
    private FirebaseAuth autenticacao;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        campoNome = findViewById(R.id.editNome);
        campoEmail = findViewById(R.id.editEmail);
        campoSenha = findViewById(R.id.editSenha);
        botaoCadastrar = findViewById(R.id.buttonCadastrar);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textNome = campoNome.getText().toString();
                String textEmail = campoEmail.getText().toString();
                String textSenha = campoSenha.getText().toString();

                //Validar se os campos foram preenchidos
                if ( !textNome.isEmpty() ){
                    if ( !textEmail.isEmpty() ){
                        if ( !textSenha.isEmpty() ) {

                            usuario = new Usuario();
                            usuario.setNome(textNome);
                            usuario.setEmail(textEmail);
                            usuario.setSenha(textSenha);
                            cadastrarUsuario();

                        }else{
                            Toast.makeText(getApplicationContext(), "Preencha a senha!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Preencha o email!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Preencha o nome!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void cadastrarUsuario(){
        autenticacao = ConfiguracaoFirebase.getFireBaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
        .addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    String idUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                    usuario.setIdUsuario(idUsuario);
                    usuario.salvar();

                   finish();

                }else{

                    String excecao ;
                    try {
                        throw task.getException();
                    }catch ( FirebaseAuthWeakPasswordException e){
                        excecao = "Por favor, digite uma senha  mais forte!";
                    }catch ( FirebaseAuthInvalidCredentialsException e){
                        excecao = "Por favor, digite um e-mail válido!";
                    }catch ( FirebaseAuthUserCollisionException e){
                        excecao = "Essa conta já foi cadastrada!";
                    }catch ( Exception e ){
                        excecao = "Erro ao cadastrar usuário: " + e.getMessage();
                        e.getStackTrace();
                    }

                    Toast.makeText(getApplicationContext(), excecao, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
