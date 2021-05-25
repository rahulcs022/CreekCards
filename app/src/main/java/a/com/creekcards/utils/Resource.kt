package a.com.creekcards.utils

data class Resource<out String>(val status: Status, val data: String?, val message: String?) {
    companion object {
        fun <String> success(data: String): Resource<String> =
            Resource(status = Status.SUCCESS, data = data, message = null)

        fun <String> error(data: String?, message: String): Resource<String> =
            Resource(status = Status.ERROR, data = data, message = message)

        fun <String> loading(data: String?): Resource<String> =
            Resource(status = Status.LOADING, data = data, message = null)
    }
}