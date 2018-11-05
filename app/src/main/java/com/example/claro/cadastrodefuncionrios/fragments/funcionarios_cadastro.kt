package com.example.claro.cadastrodefuncionrios.fragments

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.example.claro.cadastrodefuncionrios.R
import kotlinx.android.synthetic.main.funcionarios_cadastro.*
import kotlinx.android.synthetic.main.funcionarios_cadastro.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.example.claro.cadastrodefuncionrios.MainActivity
import com.example.claro.cadastrodefuncionrios.R.id.content
import com.example.claro.cadastrodefuncionrios.R.id.genero
import com.example.claro.cadastrodefuncionrios.auxiliares.dbHelper
import com.example.claro.cadastrodefuncionrios.classes.funci


class funcionarios_cadastro : Fragment()
{
    lateinit var db : dbHelper
    var  img: Int = 0
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

        fgView.bt_fun_inserir.setOnClickListener { view ->

            var nome : String = func_nome.text.toString()
            var rg : String = func_rg.text.toString()

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

            if (fgView.feminino.isChecked) img = R.drawable.female
            else if (fgView.masculino.isChecked) img = R.drawable.male

            Log.d("CURA", "IMG: $img")

            if (nome.matches(regex)) Toast.makeText(activity, "O Nome não pode conter numeros", Toast.LENGTH_SHORT).show()

            else if (nome == "") Toast.makeText(activity, "O campo Nome não pode ser vazio", Toast.LENGTH_SHORT).show()

            else if (rg == "") Toast.makeText(activity, "O campo RG não pode ser vazio", Toast.LENGTH_SHORT).show()

            else if (duplicado == 1) Toast.makeText(activity, "numero do RG já consta no cadastro", Toast.LENGTH_SHORT).show()

            else if (img == 0) Toast.makeText(activity, "Gênero não selecionado", Toast.LENGTH_SHORT).show()

            else
            {
                db.inserirFun(nome,rg,dptoAtual,img)

                fragmentManager?.beginTransaction()?.replace(R.id.container,
                    funcionarios()
                )?.remove(this)?.commit()
            }

            fgView.hideKeyboard()
        }

        fgView.bt_fun_voltar.setOnClickListener { view ->

            fgView.hideKeyboard()

            getFragmentManager()?.beginTransaction()?.replace(
                R.id.container,
                funcionarios()
            )?.remove(this)?.commit()

        }

        return fgView
    }

    fun View.hideKeyboard()
    {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        //imm.hideSoftInputFromWindow(view?.windowToken, 0)
        imm.hideSoftInputFromWindow(applicationWindowToken, 0)
    }
}
