package com.example.claro.cadastrodefuncionrios.classes

import com.example.claro.cadastrodefuncionrios.R

data class dep(var nome:String, var img:Int, var sigla : String, var id : Int)
{
    init
    {
        val regex = Regex(".*\\d+.*")

        require(!nome.matches(regex)) { "O Nome não pode conter numeros" }

        if (img == 30) img = R.drawable.ic_launcher_background
    }
}