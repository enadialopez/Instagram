package model

import org.uqbar.commons.model.annotations.Observable

@Observable
class PostModel(var id :String,
                var description : String,
                var landscape : String,
                var portrait : String)