package com.example.darre.cis357_project.helper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Andrew Prins on 11/30/2017.
 */

public class QueryBuilder {

    /**
     * Example end results:
     * {"$query":{"$and":[{"lang":"eng"}]}}
     * {"$query":{"$and":[{"sourceUri":{"$and":["nytimes.com","washingtonpost.com"]}},{"keyword":{"$and":["keyword"]}},{"lang":"eng"}]}}
     */

    private List<String> sources;
    private String keyword;
    private String language = "eng";

    public QueryBuilder withSources(List<String> sources) {
        this.sources = sources;
        return this;
    }

    public QueryBuilder withKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    public QueryBuilder withLanguage(String language) {
        this.language = language;
        return this;
    }

    public String build() {
        Boolean first = true;
        StringBuilder result = new StringBuilder("{\"$query\":{\"$and\":[");

        if(sources != null && sources.size() > 0) {
            if(!first) {
                result.append(",");
            }
            result.append("{\"sourceUri\":{\"$and\":[");
            Boolean firstSource = true;
            for (Integer i = 0; i < sources.size(); i++) {
                if(!firstSource) {
                    result.append(",");
                }
                result.append("\"").append(sources.get(i)).append("\"");
                firstSource = false;
            }
            result.append("]}}");

            first = false;
        }
        if(keyword != null && !keyword.trim().equals("")) {
            if(!first) {
                result.append(",");
            }
            result.append("{\"keyword\":{\"$and\":[\"").append(keyword).append("\"]}}");

            first = false;
        }
        if(language != null && !language.trim().equals("")) {
            if(!first) {
                result.append(",");
            }
            result.append("{\"lang\":\"").append(language).append("\"}");
        }

        result.append("]}}");

        return result.toString();
    }
}
