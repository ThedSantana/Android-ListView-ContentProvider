package com.iop.listprovider.model;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.iop.listprovider.helpers.DBHelper;

/**
 * Created by Rafael Iop
 *
 * CRUD utilizando ContentProvider
 *
 * Documentação: http://developer.android.com/guide/topics/providers/content-providers.html
 * Link recomendado: http://www.vogella.com/articles/AndroidSQLite/article.html
 */

public class NotaCP extends ContentProvider {

    // Banco de dados
    private DBHelper notasDB;

    // Identificadores para o URI_MATCHER
    private static final int NOTAS = 1;
    private static final int NOTAS_ID = 2;

    // Especifica o URI
    // CONTENT_URI será: URI://com.iop.listprovider.model.NotaCP/notas/
    private static final String AUTHORITY = "com.iop.listprovider.model.NotaCP";
    private static final String BASE_PATH = "notas";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    // URI MATCHER
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        // URI://com.iop.listprovider.model.NotaCP/notas/1/
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, NOTAS);
        // URI://com.iop.listprovider.model.NotaCP/notas/2/#
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", NOTAS_ID);
    }

    @Override
    public boolean onCreate() {
        notasDB = new DBHelper(getContext());
        return false;
    }

    // Busca notas
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        // Construtor da query
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(Nota.TABELA);

        // Descobre o tipo da URI (pode ser NOTAS(1) ou NOTAS_ID(2)
        int uriTipo = sURIMatcher.match(uri);
        switch (uriTipo) {
            // Busca TODAS as notas (não é necessário especificar nada dentro do case)
            // Exemplo: URI://com.iop.listprovider.model.NotaCP/notas/
            case NOTAS:
                break;

            // Busca apenas a nota com o ID especificado
            // Exemplo: URI://com.iop.listprovider.model.NotaCP/notas/5 (5 é o ID da nota)
            case NOTAS_ID:
                queryBuilder.appendWhere(Nota.ID+"="+uri.getLastPathSegment());
                break;

            // Caso o tipo não seja válido
            default:
                throw new IllegalArgumentException("URI desconhecida: " + uri);
        }
        // Conexão e busca
        SQLiteDatabase database = notasDB.getWritableDatabase();

        Cursor cursor = queryBuilder.query(database, projection, selection,
                selectionArgs, null, null, sortOrder);

        // Notifica as mudanças
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    // Adiciona notas
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriTipo = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = notasDB.getWritableDatabase();

        long id = 0;
        Uri returnUri;
        switch (uriTipo) {
            case NOTAS:
                // Insere no banco de dados
                id = sqlDB.insert(Nota.TABELA, null, values);
                // Adiciona o ID da nova nota no final do URI
                returnUri = Uri.parse(BASE_PATH + "/" + id);
                break;

            // Caso o tipo não seja válido
            default:
                throw new IllegalArgumentException("URI desconhecida: " + uri);
        }
        // Notifica as mudanças
        getContext().getContentResolver().notifyChange(uri, null);

        // Retorna um URI com o ID da nota adicionada no final
        return returnUri;
    }

    // Deleta uma ou mais notas
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriTipo = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = notasDB.getWritableDatabase();

        int totalDeletadas = 0;
        switch (uriTipo) {
            case NOTAS:
                totalDeletadas = sqlDB.delete(Nota.TABELA, selection, selectionArgs);
                break;
            case NOTAS_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    totalDeletadas = sqlDB.delete(Nota.TABELA,"_id=" + id, selectionArgs);
                } else {
                    totalDeletadas = sqlDB.delete(Nota.TABELA,"_id=" + id + " and " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("URI desconhecida: " + uri);
        }
        // Notifica as mudanças
        getContext().getContentResolver().notifyChange(uri, null);

        // Retorna o número de notas que foram apagadas
        return totalDeletadas;
    }

    // Atualiza uma nota
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int uriTipo = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = notasDB.getWritableDatabase();

        int totalEditadas = 0;
        switch (uriTipo) {
            case NOTAS:
                totalEditadas = sqlDB.update(Nota.TABELA, values, selection, selectionArgs);
                break;
            case NOTAS_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    totalEditadas = sqlDB.update(Nota.TABELA, values, "_id=" + id, selectionArgs);
                } else {
                    totalEditadas = sqlDB.update(Nota.TABELA, values, "_id=" + id + " and " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("URI desconhecida: " + uri);
        }
        // Notifica as mudanças
        getContext().getContentResolver().notifyChange(uri, null);

        // Retorna o número de notas que foram editadas
        return totalEditadas;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }
}