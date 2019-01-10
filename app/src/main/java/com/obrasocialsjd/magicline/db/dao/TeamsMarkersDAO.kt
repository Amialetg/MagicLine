package com.obrasocialsjd.magicline.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.obrasocialsjd.magicline.data.models.teams.TeamsDBModel
import com.obrasocialsjd.magicline.data.models.teams.TotalParticipantsDBModel
import com.obrasocialsjd.magicline.utils.TEAM_MARKERS_QUERY

@Dao interface TeamsMarkersDAO : BaseDao<TeamsDBModel> {

    @Query("select * from teamsMarkers")
    fun getTeams() : LiveData<List<TeamsDBModel>>

    @Query("select * from teamsMarkers where city = :city")
    fun getTeamsByCity(city: String) : LiveData<List<TeamsDBModel>>

    @Query(TEAM_MARKERS_QUERY)
    fun getParticipantsByCity(city: String) : LiveData<TotalParticipantsDBModel>

    @Query("DELETE FROM teamsMarkers")
    fun nukeTable()
}