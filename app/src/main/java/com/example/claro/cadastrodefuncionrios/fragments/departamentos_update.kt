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
import com.example.claro.cadastrodefuncionrios.classes.dep
import kotlinx.android.synthetic.main.departamentos_cadastro.*
import kotlinx.android.synthetic.main.departamentos_cadastro.view.*
import kotlinx.android.synthetic.main.departamentos_update.*
import kotlinx.android.synthetic.main.departamentos_update.view.*

class departamentos_update : Fragment()
{
    lateinit var db : dbHelper
    private lateinit var model: MainActivity.Compartilhado

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        super.onCreate(savedInstanceState)
        model = activity?.run {
            ViewModelProviders.of(this).get(MainActivity.Compartilhado::class.java)
        } ?: throw Exception("Invalid Activity")

        var fgView = inflater.inflate(R.layout.departamentos_update, container, false)

        db = dbHelper(activity!!.applicationContext)

        val imgs : IntArray = intArrayOf (R.drawable.d1, R.drawable.d2, R.drawable.d3, R.drawable.d4, R.drawable.d5)
        var i = 0
        val dptoAtual = model.selecionado.value

        Log.d("CURA", "DPTO CADASTRO")

        fgView.nome_dpto_up.setText(dptoAtual?.nome)
        fgView.sigla_dpto_up.setText(dptoAtual?.sigla)
        fgView.img_dpto_up.setImageResource(dptoAtual!!.img)


        fgView.bt_up_voltar.setOnClickListener { view ->

            getFragmentManager()?.beginTransaction()?.replace(
                R.id.container,
                departamentos()
            )?.remove(this)?.commit()

        }

        fgView.bt_up_inserir.setOnClickListener { view ->

            val name = nome_dpto_up.text.toString()
            val sigla = sigla_dpto_up.text.toString()
            val img = imgs[i]
            val regex = Regex(".*\\d+.*")

            if (name.matches(regex) || name == "" || sigla == "")
            {
                Toast.makeText(activity, "Dados Inseridos Inválidos", Toast.LENGTH_SHORT).show()
            }

            else
            {
                db.atualizarDep(dptoAtual.id, name, sigla, img)
                fragmentManager?.beginTransaction()?.replace(R.id.container,
                    departamentos()
                )?.remove(this)?.commit()
            }

            fgView.hideKeyboard()
        }

        fgView.bt_up_img_ant.setOnClickListener { view ->

            i--

            if(i == -1) i = 4

            img_dpto_up.setImageResource(imgs[i])

        }

        fgView.bt_up_img_prox.setOnClickListener{

            i++

            if(i == 5) i = 0

            img_dpto_up.setImageResource(imgs[i])

        }

        return fgView
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}
