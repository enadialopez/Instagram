package model

import org.uqbar.commons.model.annotations.Observable

@Observable
class DraftPostModel(): ParameterIsEmpty {
    var portrait = ""
    var landscape = ""
    var description = ""

    constructor (postModel: PostModel): this() {
        portrait = postModel.portrait
        landscape = postModel.landscape
        description = postModel.description

    }
}