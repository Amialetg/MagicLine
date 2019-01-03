package com.obrasocialsjd.magicline.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.obrasocialsjd.magicline.data.models.teams.TeamsDBModel
import com.obrasocialsjd.magicline.data.models.teams.TotalParticipantsDBModel

@Dao interface TeamsMarkersDAO : BaseDao<TeamsDBModel> {

    @Query("select * from teamsMarkers")
    fun getTeams() : LiveData<List<TeamsDBModel>>

    @Query("select * from teamsMarkers where city = :city")
    fun getTeamsByCity(city: String) : LiveData<List<TeamsDBModel>>

    @Query("select SUM(particulars) as totalParticulars, SUM(companies) as totalCompanies, SUM(particulars) + SUM(companies) as total from teamsMarkers where city = :city")
    fun getParticipantsByCity(city: String) : LiveData<TotalParticipantsDBModel>

    @Query("DELETE FROM teamsMarkers")
    fun nukeTable()
}