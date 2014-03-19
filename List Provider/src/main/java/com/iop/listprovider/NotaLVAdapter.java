package com.iop.listprovider;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iop.listprovider.helpers.UIHelper;
import com.iop.listprovider.model.NotaCP;

/**
 * Created by Rafael Iop
 *
 * Responsável por "preencher" um ListView com os dados de um Cursor
 */

public class NotaLVAdapter extends CursorAdapter {

    // Não é necessário passar o cursor como parametro,
    // pois estamos utilizando o CursorLoader.
    public NotaLVAdapter(Context context) {
        super(context, null, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        // Define um layout xml para cada row (linha) da ListView
        return LayoutInflater.from(context).inflate(R.layout.item_nota, viewGroup, false);
    }

    // Vincula os dados do cursor com os dados de cada linha da ListView
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        final int idNota = cursor.getInt(0);
        long dataNota = cursor.getLong(1);
        int idPrioridade = cursor.getInt(2);
        String tituloNota = cursor.getString(3);
        String descricaoNota = cursor.getString(4);

        // Título e descrição da nota
        ((TextView) view.findViewById(R.id.tv_titulo_nota)).setText(tituloNota);
        ((TextView) view.findViewById(R.id.tv_descricao_nota)).setText(descricaoNota);

        // ImageView que mostra a cor da prioridade
        ImageView ivPrioridade = (ImageView) view.findViewById(R.id.iv_prioridade);
        UIHelper uiHelper = new UIHelper(context);
        uiHelper.setPrioridades(ivPrioridade, idPrioridade);

        // Botão responsável por apagar uma nota
        ImageButton ibDeletar = (ImageButton) view.findViewById(R.id.ib_deletar);
        ibDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Deleta de forma assíncrona
                deletaNotaAsync();

                // Deleta de forma síncrona
                //deletaNotaSync();
            }

            private void deletaNotaAsync(){
                Log.e("NOTA", "deletaNotaAsync()");

                AsyncQueryHandler mHandler = new AsyncQueryHandler(context.getContentResolver()) {};
                mHandler.startDelete(-1, null, Uri.parse(NotaCP.CONTENT_URI+"/"+idNota), null, null);
            }

            private void deletaNotaSync(){
                Log.e("NOTA", "deletaNotaSync()");

                int resultato = context.getContentResolver().delete(
                        Uri.parse(NotaCP.CONTENT_URI+"/"+idNota), null, null);

                if (resultato == -1){
                    Toast.makeText(context, context.getResources().
                            getString(R.string.delete_note_fail), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, context.getResources().
                            getString(R.string.delete_note_success), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
