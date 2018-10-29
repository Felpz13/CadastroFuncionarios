package com.example.claro.cadastrodefuncionrios.classes

import com.example.claro.cadastrodefuncionrios.R

data class funci(var nome : String, var id : Int, var rg : String, var idDpto : Int, var img : Int)
{
    init
    {
        val regex = Regex(".*\\d+.*")

        require(!nome.matches(regex)) { "O Nome não pode conter numeros" }

    }
}