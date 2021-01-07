package model

interface ParameterIsEmpty {
    fun parameterIsEmpty(param: String): Boolean {
        return param == ""
    }
}