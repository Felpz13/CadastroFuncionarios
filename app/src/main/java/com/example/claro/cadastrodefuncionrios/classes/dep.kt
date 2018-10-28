package com.example.claro.cadastrodefuncionrios.classes

data class dep(var nome:String, var img:Int, var sigla : String, var id : Int)
{
    init
    {
        val regex = Regex(".*\\d+.*")

        require(!nome.matches(regex)) { "O Nome não pode conter numeros" }

    }
}