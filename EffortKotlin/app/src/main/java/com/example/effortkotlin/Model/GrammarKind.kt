package com.example.effortkotlin.Model

class GrammarKind {

    internal var grammar1: String? =""
    internal var grammarNote1: String? =""
    internal var pdf: Int = 0
    internal var cautu : Int = 0;

    constructor(grammar1: String, grammarNote1: String, pdf: Int,cautu : Int){
        this.grammar1 = grammar1
        this.grammarNote1 = grammarNote1
        this.pdf = pdf
        this.cautu = cautu
    }

    fun getGrammar1(): String? {
        return grammar1
    }

    fun setGrammar1(grammar1: String) {
        this.grammar1 = grammar1
    }

    fun getGrammarNote1(): String? {
        return grammarNote1
    }

    fun setGrammarNote1(grammarNote1: String) {
        this.grammarNote1 = grammarNote1
    }

    fun getPdf(): Int {
        return pdf
    }

    fun setPdf(request1: Int) {
        this.pdf = request1
    }

    fun getCautu(): Int {
        return cautu
    }

    fun setCautu(cautu: Int) {
        this.cautu = cautu
    }


}