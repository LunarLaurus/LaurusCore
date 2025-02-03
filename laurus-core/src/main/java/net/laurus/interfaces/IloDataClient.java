package net.laurus.interfaces;

import java.util.List;

import net.laurus.ilo.UnauthenticatedEndpoint.IloNicObject;
import net.laurus.network.IPv4Address;

public interface IloDataClient {

    String getSerialNumber();
    String getServerModel();
    String getServerId();
    String getServerUuid();
    String getProductId();
    IPv4Address getIloAddress();
    String getIloVersion();
    String getIloText();
    String getIloFwBuildDate();
    String getIloSerialNumber();
    String getIloUuid();
    int getHealthStatus();
    List<IloNicObject> getNics();
    long getLastUpdateTime();

    int getTimeBetweenUpdates();
    void update();
    
}

