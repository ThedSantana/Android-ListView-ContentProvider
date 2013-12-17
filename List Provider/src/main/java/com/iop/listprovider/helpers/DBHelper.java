package com.iop.listprovider.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Rafael Iop
 *
 * Classe de conexão e criação do banco de dados
 */

public class DBHelper extends SQLiteOpenHelper {

    // Tag para Log/Debug
    private static String TAG = "banco";

    private static String DB_NAME ="banco.sqlite"; // Nome do arquivo SQLite
    private static int DB_VERSION = 1; // Versão do banco de dados

    public DBHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Cria a tabela "nota"
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuery = "CREATE TABLE nota (" +
                "_id INTEGER PRIMARY KEY  AUTOINCREMENT, " +
                "prioridade INTEGER DEFAULT '1' NOT NULL, " +
                "data INTEGER NOT NULL, " +
                "titulo VARCHAR(60) NOT NULL UNIQUE, " +
                "descricao VARCHAR(1000));";

        db.execSQL(createQuery);

        //Log
        Log.i(TAG, "Banco de dados criado");
    }

    // Atualiza o banco de dados caso tenha uma nova versão
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS nota");
        onCreate(db);

        //Log
        Log.i(TAG, "Atualizado: "+oldVersion+" para "+newVersion);
    }
}
