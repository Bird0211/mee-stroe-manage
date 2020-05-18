package com.mee.manage.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.mee.manage.mapper.ICityMapper;
import com.mee.manage.po.City;
import com.mee.manage.service.GuavaCache;
import com.mee.manage.service.ICityService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * CityServiceImpl
 */
@Service
public class CityServiceImpl extends ServiceImpl<ICityMapper, City> implements ICityService {

    public static final Logger logger = LoggerFactory.getLogger(ICityService.class);

    @Autowired
    GuavaCache guavaCache;

    @Override
    public List<String> searchCity(String text, int limit) {
        if (text == null || text.isEmpty())
            return null;

        logger.info("SearchCity: {}" ,text);

        List<String> result = null;
        List<City> city = searchCityData(text, limit);
        if(city != null && city.size() > 0) {
            result = city.stream().map(item -> item.getCity()).collect(Collectors.toList());
        }
        return result;
    }

    public List<City> searchCityData(String text, int limit) {
        if (text == null || text.isEmpty())
            return null;

        logger.info("SearchCity: {}" ,text);

        List<City> result = null;
        try {
            List<City> city = (List<City>) guavaCache.getValue(GuavaCache.CITY_KEY);
            String[] texts = text.split(" ");
            Stream<City> c = city.stream();
            for(String t : texts) {
                c = c.filter(item -> item.getCity().toLowerCase().indexOf(t.toLowerCase()) >= 0);
            }
            result = c.sorted(Comparator.comparing(City::getSort)).limit(limit).collect(Collectors.toList());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

	@Override
	public List<String> searchSuburb(String selectCity, String text) {
		if (text == null || text.isEmpty())
            return null;

        logger.info("SearchSuburb: {}" ,text);

        List<City> cities = searchCityData(selectCity, 1);
        if(cities == null || cities.size() <= 0)
            return null;

        List<String> result = null;
        City city = cities.get(0);
        String[] suburbs = city.getSuburb().split(",");
        List<String> subList = Lists.newArrayList(suburbs);
        Stream<String> c = subList.stream();
        String[] texts = text.split(" ");
            for(String t : texts) {
                c = c.filter(item -> item.toLowerCase().indexOf(t.toLowerCase()) >= 0);
            }
            result = c.limit(8).collect(Collectors.toList());

        return result;
	}

    
}