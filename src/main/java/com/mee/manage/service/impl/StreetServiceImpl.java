package com.mee.manage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Lists;
import com.mee.manage.service.GuavaCache;
import com.mee.manage.service.IStreetService;
import com.mee.manage.vo.CityVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StreetServiceImpl implements IStreetService {

    @Autowired
    GuavaCache guavaCache;

    @Override
    @SuppressWarnings("unchecked")
    public List<String> getStreets(String name) {
        List<String> streets = null;
        List<String> result = null;
        try {
            streets = (List<String>) guavaCache.getValue(GuavaCache.STEET_KEY);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        
        if(streets != null) {
            String[] names = name.split(" ");
            Stream<String> stream = streets.stream();
            for (String key : names) {
                stream = stream.filter(item -> item.indexOf(key) >= 0);
            }
            result = stream.limit(10).collect(Collectors.toList());
        }

        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CityVo> getCities() {
        Set<String> citys = null;
        List<CityVo> result = null;
        try {
            citys = (Set<String>) guavaCache.getValue(GuavaCache.CITY_KEY);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(citys != null) {
            Map<String,List<String>> mapCity = new HashMap<>();
            for(String city : citys) {
                String[] strs = city.split(",");
                String c = strs[0];
                String s = strs[1];

                List<String> suburb = mapCity.get(c);
                if(suburb == null) {
                    suburb = Lists.newArrayList();
                } 
                suburb.add(s);
                mapCity.put(c, suburb);
            }

            result = Lists.newArrayList();
            for(String c : mapCity.keySet()) {
                CityVo cityVo = new CityVo();
                cityVo.setCity(c);
                cityVo.setSuburb(mapCity.get(c));
                result.add(cityVo);
            }
        }
        return result;
    }
    
}