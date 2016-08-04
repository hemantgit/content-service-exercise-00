package com.backbase.training.cs;

import org.apache.chemistry.opencmis.client.api.*;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.PropertyData;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by marcio on 03/08/16.
 */
public class SimpleClient {

    public static void main(String[] args) {

        Map<String, String> connProps = new HashMap<>();

        connProps.put(SessionParameter.BINDING_TYPE,"atompub");
        connProps.put(SessionParameter.ATOMPUB_URL, "http://localhost:7777/portalserver/content/atom");
        connProps.put(SessionParameter.USER, "admin");
        connProps.put(SessionParameter.PASSWORD, "admin");
        connProps.put(SessionParameter.REPOSITORY_ID,"contentRepository");

        SessionFactory factory = SessionFactoryImpl.newInstance();
        Session session = factory.createSession(connProps);

        QueryStatement qs = session.createQueryStatement("SELECT * FROM cmis:document");

        ItemIterable<QueryResult> results = qs.query(false);

        System.out.println("results: " + results.getTotalNumItems());

        for(QueryResult hit: results) {
            for(PropertyData<?> property: hit.getProperties()) {

                String queryName = property.getQueryName();
                Object value = property.getFirstValue();

                System.out.println(queryName + ": " + value);
            }
            System.out.println("--------------------------------------");
        }
    }
}
