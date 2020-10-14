package com.example.effortkotlin.Model

class LevelSpeak {
    internal var Id : Int? = 0
    internal var Title : String? = ""
    internal var Description : String? = ""
    internal var Imagelevel : String? = ""
    internal var Idlevel : Int? = 0


    constructor(id: Int, title: String, description: String,  imagelevel: String, idlevel: Int){
        Id = id
        Title = title
        Description = description
        Imagelevel = imagelevel
        Idlevel = idlevel
    }

    constructor()


}