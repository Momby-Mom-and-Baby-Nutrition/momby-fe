package com.example.momby.model

data class DaftarMenuMakanan(
    val makanPagi:List<MenuMakan>,
    val snackPagi:List<MenuMakan>,
    val makanSiang:List<MenuMakan>,
    val snackSore:List<MenuMakan>,
    val makanMalam:List<MenuMakan>
)
