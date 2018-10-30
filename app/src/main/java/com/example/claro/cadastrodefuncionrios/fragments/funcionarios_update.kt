package com.example.claro.cadastrodefuncionrios.fragments

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.claro.cadastrodefuncionrios.MainActivity
import com.example.claro.cadastrodefuncionrios.R
import com.example.claro.cadastrodefuncionrios.auxiliares.dbHelper
import kotlinx.android.synthetic.main.funcionarios_update.*
import kotlinx.android.synthetic.main.funcionarios_update.view.*

class funcionarios_update : Fragment()
{
    lateinit var db : dbHelper
    private lateinit var model: MainActivity.Compartilhado

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {

        super.onCreate(savedInstanceState)
        model = activity?.run {
            ViewModelProviders.of(this).get(MainActivity.Compartilhado::class.java)
        } ?: throw Exception("Invalid Activity")

        var fgView = inflater.inflate(R.layout.funcionarios_update, container, false)
        db = dbHelper(activity!!.applicationContext)

        Log.d("CURA", "FUNCIONARIO CADASTRO")

        val funcSelecionado = model.funSelecionado.value

        Log.d("CURA", "FUNCIONARIO ${funcSelecionado}")

        fgView.func_nome_up.setText(funcSelecionado?.component1())
        fgView.func_rg_up.setText(funcSelecionado?.component3())

        fgView.bt_fun_voltar_up.setOnClickListener { view ->

            fragmentManager?.beginTransaction()?.replace(
                R.id.container,
                funcionarios()
            )?.remove(this)?.commit()

        }

        fgView.bt_fun_inserir_up.setOnClickListener { view ->

            val regex = Regex(".*\\d+.*")
            val nome = func_nome_up.text.toString()
            val rg = func_rg_up.text.toString()
            val img : Int = funcSelecionado!!.component5()
            val id : Int = funcSelecionado.component2()

            if (nome.matches(regex) || nome == "" || rg == "")
            {
                Toast.makeText(activity, "Dados Inseridos Inválidos", Toast.LENGTH_SHORT).show()
            }

            else
            {
                db.atualizarFun(id, nome, rg, img)
                fragmentManager?.beginTransaction()?.replace(R.id.container,
                    funcionarios()
                )?.remove(this)?.commit()
            }

            fgView.hideKeyboard()

        }

        return fgView
    }

    fun View.hideKeyboard()
    {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}
