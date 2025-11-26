package com.example.petmatch.util

//By: Paulo Linhares
//https://medium.com/@paulo_linhares/cpf-m%C3%A1scara-e-valida%C3%A7%C3%A3o-kotlin-975f1e394ecb

object CpfUtil {

    fun myValidateCPF(cpf: String): Boolean {
        val cpfClean = cpf.replace("[^0-9]".toRegex(), "")

        if (cpfClean.length != 11) return false

        // Elimina CPFs conhecidos inválidos (ex: 111.111.111-11)
        if (cpfClean.all { it == cpfClean[0] }) return false

        val cpfArray = IntArray(11) { cpfClean[it].toString().toInt() }

        // Cálculo do primeiro dígito verificador
        var sum = 0
        for (i in 0..8) {
            sum += cpfArray[i] * (10 - i)
        }
        var firstDigit = 11 - (sum % 11)
        if (firstDigit > 9) firstDigit = 0

        if (firstDigit != cpfArray[9]) return false

        // Cálculo do segundo dígito verificador
        sum = 0
        for (i in 0..9) {
            sum += cpfArray[i] * (11 - i)
        }
        var secondDigit = 11 - (sum % 11)
        if (secondDigit > 9) secondDigit = 0

        return secondDigit == cpfArray[10]
    }
}