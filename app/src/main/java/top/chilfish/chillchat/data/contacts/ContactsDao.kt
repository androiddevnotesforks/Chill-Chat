package top.chilfish.chillchat.data.contacts

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import top.chilfish.chillchat.provider.curCid

@Dao
interface ContactsDao {
    @Query("SELECT * FROM $User_Table ORDER BY cid ASC")
    fun getAll(): Flow<MutableList<Profile>>

    @Query("SELECT * FROM $User_Table WHERE id = :id")
    suspend fun getById(id: String): Profile?

    @Query("SELECT * FROM $User_Table WHERE cid LIKE :name")
    suspend fun getByName(name: String): MutableList<Profile>

    @Query("SELECT * FROM $User_Table WHERE id = :id")
    fun getUser(id: String = curCid): Flow<Profile>

    @Insert
    suspend fun insert(profile: Profile)

    @Query("DELETE FROM $User_Table")
    suspend fun deleteAll()

    @Query("DELETE FROM $User_Table WHERE id = :id")
    suspend fun deleteById(id: String): Int

    @Update
    suspend fun update(profile: Profile): Int

    @Transaction
    suspend fun insertOrUpdate(contacts: List<Profile>) {
        contacts.forEach {
            val contact = getById(it.id)
            if (contact == null) {
                insert(it)
            } else {
                update(it)
            }
        }
    }
}