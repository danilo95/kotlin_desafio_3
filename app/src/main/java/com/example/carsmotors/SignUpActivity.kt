package com.example.carsmotors

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.carsmotors.databinding.ActivitySignUpBinding
import com.example.sqliteapp.db.HelperDB
import com.example.sqliteapp.model.Categoria
import com.example.sqliteapp.model.Usuarios

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private var managerUsuarios: Usuarios? = null
    private var dbHelper: HelperDB? = null
    private var db: SQLiteDatabase? = null

    val defaultSpinnerValues = arrayOf("Administrador", "Cliente")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        dbHelper = HelperDB(this)
        db = dbHelper!!.writableDatabase

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item, defaultSpinnerValues
        )
        binding.tipo.adapter = adapter

        binding.textView.setOnClickListener {
            val intent = Intent(this, SiginActivity::class.java)
            startActivity(intent)
        }


        fun validarDatos(): Boolean {
            //all values
            val nombres = binding.nombreslEt.text.toString();
            val appellidos = binding.aprllifodEt.text.toString();
            val email = binding.emailEt.text.toString();
            val user = binding.userEt.text.toString();
            val pass = binding.passET.text.toString();
            val confirmarPass = binding.confirmPassEt.text.toString()
            val tipo = binding.tipo.toString()
            var esValido: Boolean = true;
            if (nombres.isEmpty()) {
                binding.nombreslEt.setError("Este campo es requerido")
                esValido = false
            }
            if (appellidos.isEmpty()) {
                binding.aprllifodEt.setError("Este campo es requerido")
                esValido = false
            }

            if (email.isEmpty()) {
                binding.emailEt.setError("Este campo es requerido")
                esValido = false
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailEt.setError("El formato no coincide con la forma xxx@proveedordeemail.com")
                esValido = false
            }
            if (user.isEmpty()) {
                binding.userEt.setError("Este campo es requerido")
                esValido = false
            }
            if (pass.isEmpty()) {
                binding.passET.setError("Este campo es requerido")
                esValido = false
            }
            if (confirmarPass.isEmpty()) {
                binding.confirmPassEt.setError("Este campo es requerido")
                esValido = false
            }
            if (confirmarPass != pass) {
                binding.confirmPassEt.setError("Este campo es debe coincidir con la contraseña ingresada")
                esValido = false
            }

            return esValido;
        }

        binding.button.setOnClickListener {
            val nombres = binding.nombreslEt.text.toString();
            val appellidos = binding.aprllifodEt.text.toString();
            val email = binding.emailEt.text.toString();
            val user = binding.userEt.text.toString();
            val pass = binding.passET.text.toString();

            val tipo = binding.tipo.toString()
            managerUsuarios = Usuarios(this)
            if (validarDatos()) {
                if (db != null) {
                    var tipoDeUsuario = 0;
                    if (tipo === "Administrador") {
                        tipoDeUsuario = 1;
                    }
                    managerUsuarios!!.addNewUser(
                        nombres,
                        appellidos,
                        email,
                        user,
                        pass,
                        tipoDeUsuario
                    )
                    Toast.makeText(
                        this,
                        "USUARIO REGISTRADO, por favor iniciar sesion",
                        Toast.LENGTH_LONG
                    ).show()
                    val intent = Intent(this, SiginActivity::class.java)
                    startActivity(intent)
                }
            }

        }
    }
}