import android.net.Uri
import android.provider.BaseColumns

object DBContract {

    const val AUTHORITY = "com.kharozim.githubuser"
    const val SCHEME = "content"

    internal class FavUserColums : BaseColumns {
        companion object {
            private const val TABLE_NAME = "user_entity"
            const val ID = "ID"
            const val NAME = "NAME"
            const val LOGIN = "LOGIN"
            const val AVATAR_URL = "AVATAR_URL"
            const val EMAIL = "EMAIL"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()

        }
    }
}