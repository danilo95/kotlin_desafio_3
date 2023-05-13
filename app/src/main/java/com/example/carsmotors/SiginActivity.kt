package com.example.carsmotors

import android.content.Intent
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.carsmotors.databinding.ActivitySiginBinding
import com.example.sqliteapp.db.HelperDB
import com.example.sqliteapp.model.Usuarios

class SiginActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySiginBinding
    private var managerUsuarios: Usuarios? = null
    private var dbHelper: HelperDB? = null
    private var db: SQLiteDatabase? = null
    private var cursor: Cursor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySiginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbHelper = HelperDB(this)
        db = dbHelper!!.writableDatabase


        binding.textView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        fun validarDatos(): Boolean {
            //datos
            val email = binding.emailEt.text.toString();
            val pass = binding.passET.text.toString();
            var esValido: Boolean = true;

            if (email.isEmpty()) {
                binding.emailEt.setError("Este campo es requerido")
                esValido = false
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailEt.setError("El formato no coincide con la forma xxx@proveedordeemail.com")
                esValido = false
            }

            if (pass.isEmpty()) {
                binding.passET.setError("Este campo es requerido")
                esValido = false
            }


            return esValido;
        }

        binding.button.setOnClickListener {
            //datos
            val email = binding.emailEt.text.toString();
            val pass = binding.passET.text.toString();
            if (validarDatos()) {
                if (db != null) {
                    managerUsuarios = Usuarios(this)
                    cursor = managerUsuarios!!.login(email)
                    if (cursor != null && cursor!!.count > 0) {
                        //Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(cursor))

                        cursor!!.moveToFirst()
                        /*Log.d("cursor--->", cursor!!.getString(2)) //email
                        Log.d("cursor--->", cursor!!.getString(1)) //nombre
                        Log.d("cursor--->", cursor!!.getString(4)) //tipode usuario
                        Log.d("cursor--->", cursor!!.getString(3)) //usuario
                        Log.d("cursor--->", cursor!!.getString(5)) //Password*/

                        if (cursor!!.getString(5).toString() == pass) {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                this,
                                "Verifica que los datos sean correctos",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                    } else {
                        Toast.makeText(
                            this,
                            "El usuario no existe ",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }
            }
        }
    }
}