package com.example.claro.cadastrodefuncionrios.fragments

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.claro.cadastrodefuncionrios.R
import com.example.claro.cadastrodefuncionrios.auxiliares.dbHelper
import com.example.claro.cadastrodefuncionrios.classes.dep
import kotlinx.android.synthetic.main.departamentos_cadastro.*
import kotlinx.android.synthetic.main.departamentos_cadastro.view.*

class departamentos_cadastro : Fragment()
{

    lateinit var db : dbHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        var fgView = inflater.inflate(R.layout.departamentos_cadastro, container, false)

        Log.d("CURA", "DPTO CADASTRO")

        db = dbHelper(activity!!.applicationContext)

        val imgs : IntArray = intArrayOf (R.drawable.d1, R.drawable.d2, R.drawable.d3, R.drawable.d4, R.drawable.d5)
        var i = 0


        fgView.bt_cad_voltar.setOnClickListener { view ->

            fragmentManager?.beginTransaction()?.replace(
                R.id.container,
                departamentos()
            )?.remove(this)?.commit()

        }

        fgView.bt_cad_inserir.setOnClickListener { view ->


            var nome : String = nome_dpto.text.toString()
            var sigla : String = sigla_dpto.text.toString()
            var img = imgs[i]
            val regex = Regex(".*\\d+.*")

            Log.d("CURA", "NOME: $nome")
            Log.d("CURA", "SIGLA: $sigla")
            Log.d("CURA", "IMG: $img")

            if (nome.matches(regex))
            {
                Toast.makeText(activity, "Nome Inserido Inválido", Toast.LENGTH_SHORT).show()
            }

            else
            {
                db.inserirDpto(nome, sigla, img)
                fragmentManager?.beginTransaction()?.replace(R.id.container,
                    departamentos()
                )?.remove(this)?.commit()
            }

            fgView.hideKeyboard()



        }

        fgView.bt_cad_img_ant.setOnClickListener { view ->

            i--

            if(i == -1) i = 4

            img_dpto.setImageResource(imgs[i])

        }

        fgView.bt_cad_img_prox.setOnClickListener{

            i++

            if(i == 5) i = 0

            img_dpto.setImageResource(imgs[i])

        }

        return fgView
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}
