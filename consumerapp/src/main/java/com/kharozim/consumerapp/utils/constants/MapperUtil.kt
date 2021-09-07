import android.database.Cursor
import com.kharozim.consumerapp.model.UserModel

object MapperUtil {
    fun cursorToArraylist(cursor: Cursor?): List<UserModel> {

        val list = ArrayList<UserModel>()

        while (cursor?.moveToNext() == true) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.FavUserColums.ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.FavUserColums.NAME))
            val login =
                cursor.getString(cursor.getColumnIndexOrThrow(DBContract.FavUserColums.LOGIN))
            val avatar =
                cursor.getString(cursor.getColumnIndexOrThrow(DBContract.FavUserColums.AVATAR_URL))
            val email =
                cursor.getString(cursor.getColumnIndexOrThrow(DBContract.FavUserColums.EMAIL))

            list.add(
                UserModel(
                    id = id,
                    name = name,
                    login = login,
                    avatarUrl = avatar,
                    email = email
                )
            )
        }
        return list
    }
}