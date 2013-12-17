package com.iop.listprovider;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.iop.listprovider.model.Nota;
import com.iop.listprovider.model.NotaCP;

/**
 * Created by Rafael Iop
 *
 * Activity com a ListView e o Cursor Loader
 */

public class MainActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    // Define um identificador para o Loader
    private static int LOADER_ID = 1;

    // ListView e CursorAdapter
    private ListView mListView;
    private NotaLVAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.lv_notas);
        mListView.setEmptyView(findViewById(R.id.iv_vazio));

        // Ação de quando alguma nota for selecionada no listView
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), NotaActivity.class);

                // Sinaliza que não é uma nova nota, ou seja, é visualização/edição
                intent.putExtra("nova", false);
                intent.putExtra("id_nota", (int) id);

                // Abre a NotaActivity
                startActivity(intent);
            }
        });

        preencherDados();
    }

    public void preencherDados(){
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        mAdapter = new NotaLVAdapter(this);
        mListView.setAdapter(mAdapter);
    }

    /**
     * LOADER
     * Override dos métodos responsáveis por fazer o Cursor Loader funcionar
     * de forma assíncrona (em background, fora da UI Thread)
     */

    // Ao criar o Loader
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Colunas que deseja selecionar no banco de dados
        String[] colunas = { Nota.ID, Nota.DATA, Nota.PRIORIDADE,
                Nota.TITULO, Nota.DESCRICAO_RESUMIDA };

        // Cria um Loader com a URI do ContentProvider (NotaCP)
        // As notas serão ordenadas conforme a data de edição/criação
        // A URI é: content://com.iop.listprovider.model.NotaCP/notas/ (todas as notas)
        return new CursorLoader(this, NotaCP.CONTENT_URI, colunas, null, null, "data DESC");
    }

    // Quando terminar de carregar o Loader
    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        // Passa os dados do cursor para o adaper
        mAdapter.swapCursor(cursor);
    }

    // Ao resetar o Loader
    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        // Limpa o adapter
        mAdapter.swapCursor(null);
    }

    /**
     * MENU (onCreateOptionsMenu) e (onOptionsItemSelected)
     * Define o menu da Activity/ActionBar e suas ações
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_new_note) {
            Intent intent = new Intent(this, NotaActivity.class);
            // Sinaliza que é uma nova nota
            intent.putExtra("nova", true);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
