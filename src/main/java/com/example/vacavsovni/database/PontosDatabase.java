package com.example.vacavsovni.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class PontosDatabase extends SQLiteOpenHelper {

    private static final int VERSAO = 1;
    private static final String NOME_BD = "VacavsOvni";
    private static final String TB_PONTUACAO = "pontuacao";
    private static final String C_PONTOS = "pontos";
    public static Context contexto;

    public PontosDatabase(@Nullable Context context) {
        super(context, NOME_BD, null, VERSAO);
        contexto = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TB_PONTUACAO + "( " +
                C_PONTOS + " INTEGER)";
        db.execSQL(query);

    }

    public void addPontuacao(int pontos) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(C_PONTOS, pontos);
        db.insert(TB_PONTUACAO, null, values);
        db.close();
    }

    public int maiorPontuacao() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT MAX(" + C_PONTOS + ") FROM " + TB_PONTUACAO;
        Cursor cursor = db.rawQuery(query, null);
        int pontos = 0;
        if (cursor.moveToFirst()) {
            pontos = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return pontos;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
