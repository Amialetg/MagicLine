package com.obrasocialsjd.magicline.db

import android.content.Context
import android.os.AsyncTask
import androidx.annotation.NonNull
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.obrasocialsjd.magicline.data.models.donations.DonationsDBModel
import com.obrasocialsjd.magicline.data.models.posts.Converters
import com.obrasocialsjd.magicline.data.models.posts.PostsItem
import com.obrasocialsjd.magicline.data.models.teams.TeamsDBModel
import com.obrasocialsjd.magicline.db.dao.DonationsDAO
import com.obrasocialsjd.magicline.db.dao.PostDao
import com.obrasocialsjd.magicline.db.dao.TeamsMarkersDAO

@Database(entities = [PostsItem::class, DonationsDBModel::class, TeamsDBModel::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MagicLineDB : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun donationsDAO(): DonationsDAO
    abstract fun teamsMarkersDAO(): TeamsMarkersDAO

    companion object {
        @Volatile
        private lateinit var INSTANCE: MagicLineDB

        fun getDatabase(context: Context): MagicLineDB? {
            synchronized(MagicLineDB::class.java) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                        MagicLineDB::class.java, "magicLine.db")
                        .addCallback(sRoomDatabaseCallback)
                        .allowMainThreadQueries()
                        .build()
            }
            return INSTANCE
        }

        private val sRoomDatabaseCallback = object : RoomDatabase.Callback() {

            override fun onOpen(@NonNull db: SupportSQLiteDatabase) {
                super.onOpen(db)
                PopulateDbAsync(INSTANCE).execute()
            }
        }

    }

}

private class PopulateDbAsync internal constructor(db: MagicLineDB) : AsyncTask<Void, Void, Void>() {

    private val mPostDao: PostDao = db.postDao()
    private val donationsDAO: DonationsDAO = db.donationsDAO()
    private val teamsMarkersDAO: TeamsMarkersDAO = db.teamsMarkersDAO()

    override fun doInBackground(vararg params: Void): Void? {
        // Where to insert temp data
        return null
    }
}
