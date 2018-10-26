package com.example.claro.cadastrodefuncionrios.auxiliares

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.claro.cadastrodefuncionrios.R
import com.example.claro.cadastrodefuncionrios.classes.dep
import com.example.claro.cadastrodefuncionrios.classes.funci

class adapterFun (var context: Context, var dataSource: ArrayList<funci>) : BaseAdapter()
{

    private class ViewHolder(row: View?) {
        var txtName: TextView? = null
        var rg: TextView? = null
        var imgAvatar: ImageView? = null

        init {
            this.txtName = row?.findViewById(R.id.nome_fun) as TextView
            this.rg = row?.findViewById(R.id.rg) as TextView
            this.imgAvatar = row?.findViewById(R.id.foto) as ImageView
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View
    {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            var layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.fun_item, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder

        }

        else
        {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var elemento : funci = getItem(position) as funci
        viewHolder.txtName?.text = elemento.nome
        viewHolder.rg?.text = elemento.rg
        viewHolder.imgAvatar?.setImageResource(elemento.img)

        return view as View
    }

    override fun getItem(position: Int): Any
    {
        return dataSource.get(position)
    }

    override fun getItemId(position: Int): Long
    {
        return position.toLong()
    }

    override fun getCount(): Int
    {
        return dataSource.count()
    }
}