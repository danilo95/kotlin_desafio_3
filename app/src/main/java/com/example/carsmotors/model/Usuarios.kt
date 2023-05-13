package com.example.sqliteapp.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.sqliteapp.db.HelperDB

class Usuarios(context: Context?) {

    private var helper: HelperDB? = null
    private var db: SQLiteDatabase? = null

    init {
        helper = HelperDB(context)
        db = helper!!.getWritableDatabase()
    }

    companion object {
        //TABLA USUARIOS
        val TABLE_NAME = "usuarios"

        //nombre de los campos de la tabla contactos
        val COL_ID = "idUsuario"
        val COL_NOMBRES = "nombres"
        val COL_APPELLIDOS = "appellidos"
        val COL_EMAIL = "email"
        val COL_USER = "user"
        val COL_PASSWORD = "password"
        val COL_TIPO = "tipo"


        //sentencia SQL para crear la tabla.
        val CREATE_TABLE_PRODUCTOS = (
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                        + COL_ID + " integer primary key autoincrement,"
                        + COL_NOMBRES + " varchar(150) NOT NULL,"
                        + COL_APPELLIDOS + " varchar(150) NOT NULL,"
                        + COL_EMAIL + " varchar(150) NOT NULL,"
                        + COL_USER + " varchar(150) NOT NULL,"
                        + COL_PASSWORD + " varchar(150) NOT NULL,"
                        + COL_TIPO + " integer);"

                )
    }

    fun generarContentValues(
        nombres: String?,
        appellidos: String?,
        email: String?,
        user: String?,
        password: String?,
        tipo: Int?
    ): ContentValues? {
        val valores = ContentValues()
        valores.put(Usuarios.COL_NOMBRES, nombres)
        valores.put(Usuarios.COL_APPELLIDOS, appellidos)
        valores.put(Usuarios.COL_EMAIL, email)
        valores.put(Usuarios.COL_USER, user)
        valores.put(Usuarios.COL_PASSWORD, password)
        valores.put(Usuarios.COL_TIPO, tipo)
        return valores
    }


    //Agregar un nuevo usuario
    fun addNewUser(
        nombres: String?,
        appellidos: String?,
        email: String?,
        user: String?,
        password: String?,
        tipo: Int?
    ) {
        db!!.insert(
            TABLE_NAME,
            null,
            generarContentValues(nombres, appellidos, email, user, password, tipo)
        )
    }


    // Mostrar un registro particular
    fun login(email: String?): Cursor? {
        val columns = arrayOf(COL_ID, COL_NOMBRES, COL_EMAIL, COL_USER, COL_TIPO, COL_PASSWORD)
        return db!!.query(
            TABLE_NAME, columns,
            "$COL_EMAIL=?", arrayOf(email), null, null, null
        )
    }


}