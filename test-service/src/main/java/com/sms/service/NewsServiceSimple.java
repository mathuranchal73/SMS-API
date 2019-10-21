package com.sms.service;

import org.springframework.stereotype.Service;
import com.sms.reporting.RequestCorrelation;

/**
 * NewsServiceSimple
 */
@Service
public class NewsServiceSimple implements NewsService {

    @Override
    public String getNews() {
        //you probably wouldn't pollute service code with correlation Ids (only external calls)
        //but it is included here as an example
        return String.format("No news is good news (with correlation Id '%s')", RequestCorrelation.getId());
    }

}
