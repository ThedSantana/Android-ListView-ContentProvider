package com.iop.listprovider;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.iop.listprovider.helpers.DateHelper;
import com.iop.listprovider.helpers.UIHelper;
import com.iop.listprovider.model.Nota;
import com.iop.listprovider.model.NotaCP;

/**
 * Created by Rafael Iop
 *
 * Activity responsável por adicionar OU editar uma nota
 *
 */

public class NotaActivity extends ActionBarActivity{

    private UIHelper uiHelper;

    private boolean novaNota;
    private int idNota;

    // Views
    private ImageView ivPrioridade;
    private Spinner spPrioridade;
    private EditText etTitulo;
    private EditText etDescricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota);

        uiHelper = new UIHelper(this);

        ivPrioridade = (ImageView) findViewById(R.id.iv_prioridade);
        spPrioridade = (Spinner) findViewById(R.id.sp_prioridade);
        etTitulo = (EditText) findViewById(R.id.et_titulo);
        etDescricao = (EditText) findViewById(R.id.et_descricao);

        // Troca a cor da ImageView "ivPrioridade"
        // de acordo com a prioridade selecionada no Spinner "spPrioridade"

        spPrioridade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                uiHelper.setPrioridades(ivPrioridade, (int) id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Pega as informações do intent, enviadas pela Activity anterior
        Intent intent = getIntent();
        novaNota = intent.getBooleanExtra("nova", true);

        // Se a nota já existe, carrega os dados da mesma
        if (!novaNota){
            idNota = intent.getIntExtra("id_nota", -1);

            // Busca a nota com o Content Provider
            String[] colunas = { Nota.ID, Nota.PRIORIDADE,
                    Nota.TITULO, Nota.DESCRICAO };

            Cursor c = getContentResolver().query(Uri.parse(NotaCP.CONTENT_URI+"/"+idNota), colunas, null, null, null);
            if (c != null) {
                c.moveToLast();

                idNota = c.getInt(0);
                int idPrioridade = c.getInt(1);
                spPrioridade.setSelection(idPrioridade);

                etTitulo.setText(c.getString(2));
                etDescricao.setText(c.getString(3));

                uiHelper.setPrioridades(ivPrioridade, idPrioridade);
            } else{
                Toast.makeText(this, getResources().getString(R.string.edit_note_request_fail),
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    /**
     * Métodos responsáveis por adicionar ou editar uma nota
     */

    private void addNota(){
        // Log
        Log.e("NOTA", "addNota()");

        // Utilizado para converter a data atual para milisegundos
        DateHelper tempo = new DateHelper();

        // Cria um objeto ContentValues e adiciona valores no mesmo
        // Exemplo: valores.put(COLUNA, CONTEUDO)
        // Exemplo: valores.put("titulo", "Nome da minha nota")

        ContentValues valores = new ContentValues();
        valores.put(Nota.PRIORIDADE, spPrioridade.getSelectedItemPosition());
        valores.put(Nota.DATA, tempo.getHorarioAgora());
        valores.put(Nota.TITULO, etTitulo.getText().toString());
        valores.put(Nota.DESCRICAO, etDescricao.getText().toString());

        // Insere os valores utilizando o Content Provider
        // URI passada como parâmetro: URI://com.iop.listprovider.model.NotaCP/notas/
        Uri resultado = getContentResolver().insert(NotaCP.CONTENT_URI, valores);

        // O método insert do Content Provider retorna o URI com o ID da nota inserida
        // Caso encontre algum problema ao inserir, o URI retornado será "notas/-1"
        // getLastPathSegment() é usado para pegar o último elemento do URI, que é o ID
        idNota = Integer.parseInt(resultado.getLastPathSegment());

        if (idNota == -1){
            Toast.makeText(this, getResources().getString(R.string.new_note_fail),
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getResources().getString(R.string.new_note_success),
                    Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void editaNota(){
        // Log
        Log.e("NOTA", "editaNota()");

        // Utilizado para converter a data atual para milisegundos
        DateHelper tempo = new DateHelper();

        // Cria um objeto ContentValues e adiciona valores no mesmo
        // Exemplo: valores.put(COLUNA, CONTEUDO)
        // Exemplo: valores.put("titulo", "Nome da minha nota")

        ContentValues valores = new ContentValues();
        valores.put(Nota.PRIORIDADE, spPrioridade.getSelectedItemPosition());
        valores.put(Nota.DATA, tempo.getHorarioAgora());
        valores.put(Nota.TITULO, etTitulo.getText().toString());
        valores.put(Nota.DESCRICAO, etDescricao.getText().toString());

        // Atualiza os valores utilizando o Content Provider
        // URI passada como parâmetro: URI://com.iop.listprovider.model.NotaCP/notas/ID

        int resultado = getContentResolver().update(Uri.parse(NotaCP.CONTENT_URI+"/"+idNota), valores, null, null);
        if (resultado == -1){
            Toast.makeText(this, getResources().getString(R.string.edit_note_fail),
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getResources().getString(R.string.edit_note_success),
                    Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /**
     * MENU (onCreateOptionsMenu) e (onOptionsItemSelected)
     * Define o menu da Activity/ActionBar e suas ações
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nova_nota, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save_note) {
            if (novaNota) addNota();
            else editaNota();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
