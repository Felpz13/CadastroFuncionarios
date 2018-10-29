package com.example.claro.cadastrodefuncionrios.fragments

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.claro.cadastrodefuncionrios.R
import kotlinx.android.synthetic.main.funcionarios_cadastro.*
import kotlinx.android.synthetic.main.funcionarios_cadastro.view.*
import android.view.inputmethod.InputMethodManager
import com.example.claro.cadastrodefuncionrios.MainActivity
import com.example.claro.cadastrodefuncionrios.auxiliares.dbHelper
import com.example.claro.cadastrodefuncionrios.classes.funci


class funcionarios_cadastro : Fragment()
{
    lateinit var db : dbHelper
    val cameraRequest = 0
    private lateinit var model: MainActivity.Compartilhado

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        super.onCreate(savedInstanceState)
        model = activity?.run {
            ViewModelProviders.of(this).get(MainActivity.Compartilhado::class.java)
        } ?: throw Exception("Invalid Activity")

        var fgView = inflater.inflate(R.layout.funcionarios_cadastro, container, false)

        db = dbHelper(activity!!.applicationContext)

        val dptoAtual : Int = model.selecionado.value!!.component4()

        Log.d("CURA", "FUNCIONARIO CADASTRO")


        fgView.bt_func_camera.setOnClickListener { view ->

            val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            startActivityForResult(camera, cameraRequest)

        }

        fgView.bt_fun_voltar.setOnClickListener { view ->

            getFragmentManager()?.beginTransaction()?.replace(
                R.id.container,
                funcionarios()
            )?.remove(this)?.commit()

        }

        fgView.bt_fun_inserir.setOnClickListener { view ->

            var nome : String = func_nome.text.toString()
            var rg : String = func_rg.text.toString()
            var img = R.drawable.icon_app

            val regex = Regex(".*\\d+.*")

            Log.d("CURA", "NOME: $nome")
            Log.d("CURA", "RG: $rg")
            Log.d("CURA", "IMG: $img")
            Log.d("CURA", "DPTO: $dptoAtual")

            var nomesFunc: ArrayList<funci> = ArrayList()

            nomesFunc = db.listarFun(-1)

            var duplicado : Int = 0

            for (i in 0 .. nomesFunc.count() - 1)
            {
                if (nomesFunc[i].component3() == rg) duplicado = 1
            }

            if (nome.matches(regex) || (duplicado == 1))
            {
                Toast.makeText(activity, "Dados Inseridos Inválidos", Toast.LENGTH_SHORT).show()
            }

            else
            {
                db.inserirFun(nome,rg,dptoAtual,img)

                fragmentManager?.beginTransaction()?.replace(R.id.container,
                    funcionarios()
                )?.remove(this)?.commit()
            }

            fgView.hideKeyboard()
        }

        return fgView
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}
