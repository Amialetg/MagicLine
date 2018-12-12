package com.voluntariat.android.magicline.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.voluntariat.android.magicline.db.dao.PostDao
import android.arch.persistence.room.Room
import com.voluntariat.android.magicline.data.apimodels.Post
import android.arch.persistence.db.SupportSQLiteDatabase
import android.support.annotation.NonNull
import android.os.AsyncTask
import com.voluntariat.android.magicline.data.apimodels.PostImageItem
import com.voluntariat.android.magicline.data.apimodels.PostsItem

@Database(entities = [PostsItem::class], version = 1, exportSchema = false)
abstract class MagicLineDB : RoomDatabase() {
    abstract fun postDao(): PostDao

    companion object {
        @Volatile
        private lateinit var INSTANCE: MagicLineDB

        fun getDatabase(context: Context): MagicLineDB? {
            synchronized(MagicLineDB::class.java) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                        MagicLineDB::class.java, "magicLine.db")
                        .addCallback(sRoomDatabaseCallback)
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

    override fun doInBackground(vararg params: Void): Void? {
        var post = PostsItem(title = "Noticia 1")
        mPostDao.insert(post)
        return null
    }
}
