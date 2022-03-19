package com.example.appstar.services;


import com.example.appstar.beans.Team;
import com.example.appstar.dao.IDao;

import java.util.ArrayList;
import java.util.List;

public class ServiceTeam implements IDao<Team> {

    private List<Team> teams;
    private static ServiceTeam instance;

    public ServiceTeam() {
        teams=new ArrayList<Team>();
    }

    public static ServiceTeam getInstance() {
        if(instance == null)
            instance = new ServiceTeam();
        return instance;
    }



    @Override
    public boolean create(Team o) {
        return teams.add(o);
    }

    @Override
    public boolean update(Team o) {
        for(Team e:teams) {
            if(o.getId()==e.getId()) {
                e.setLeague(o.getLeague());
                e.setName(o.getName());
                e.setHisory(o.getHisory());
                e.setImage(o.getImage());
                e.setStar(o.getStar());

            }
        }
        return true;
    }


    @Override
    public boolean delete(Team o) {
        return teams.remove(o);
    }

    @Override
    public Team findById(int id) {
        for(Team e:teams) {
            if(e.getId()==id)
                return e;
        }
        return null;
    }

    @Override
    public List<Team> findAll() {
        return teams;
    }



}
