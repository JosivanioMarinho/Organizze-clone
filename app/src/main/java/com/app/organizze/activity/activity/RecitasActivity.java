package com.app.organizze.activity.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.app.organizze.R;
import com.app.organizze.activity.config.ConfiguracaoFirebase;
import com.app.organizze.activity.helper.Base64Custom;
import com.app.organizze.activity.helper.DateCustom;
import com.app.organizze.activity.model.Movimentacao;
import com.app.organizze.activity.model.Usuario;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class RecitasActivity extends AppCompatActivity {

    private EditText campoValor;
    private TextInputEditText campoData, campoCategoria, campoDescricao;
    private Movimentacao movimentacao;
    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFireBaseAutenticacao();
    private Double receitaTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recitas);

        campoValor = findViewById(R.id.editValor);
        campoData = findViewById(R.id.editDataReceita);
        campoCategoria = findViewById(R.id.editCategoriaReceita);
        campoDescricao = findViewById(R.id.editDescricaoReceita);

        //Recupera a data atual
        campoData.setText(DateCustom.dataAtual());

        recuperarDespesaTotal();
    }

    public void salvarReceita(View view){

        if (validarCampos()){
            movimentacao = new Movimentacao();
            Double valorRecuperado = Double.parseDouble(campoValor.getText().toString());
            String data = campoData.getText().toString();

            movimentacao.setValor(valorRecuperado);
            movimentacao.setData(data);
            movimentacao.setCategoria(campoCategoria.getText().toString());
            movimentacao.setDescricao(campoDescricao.getText().toString());
            movimentacao.setTipo("r");

            Double receitaAtualizada = receitaTotal + valorRecuperado;
            atualizarReceitaTotal(receitaAtualizada);

            movimentacao.salvar(data);

            finish();
        }

    }

    public boolean validarCampos(){

        String textValor = campoValor.getText().toString();
        String textData = campoData.getText().toString();
        String textCategoria = campoCategoria.getText().toString();
        String textDescricao = campoDescricao.getText().toString();

        if (!textValor.isEmpty()){
            if (!textData.isEmpty()){
                if (!textCategoria.isEmpty()){
                    if (!textDescricao.isEmpty()){
                        return true;
                    }else{
                        Toast.makeText(RecitasActivity.this, "Descrição não preenchido!", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }else{
                    Toast.makeText(RecitasActivity.this, "Categoria não preenchido!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }else{
                Toast.makeText(RecitasActivity.this, "Data não preenchido!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }else{
            Toast.makeText(RecitasActivity.this, "Valor não preenchido!", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public void recuperarDespesaTotal(){

        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(idUsuario);

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                receitaTotal = usuario.getReceitaTotal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void atualizarReceitaTotal(Double receita){

        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(idUsuario);
        usuarioRef.child("receitaTotal").setValue(receita);

    }
}
