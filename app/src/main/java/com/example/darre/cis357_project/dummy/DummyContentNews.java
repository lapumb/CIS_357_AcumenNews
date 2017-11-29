package com.example.darre.cis357_project.dummy;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContentNews {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyNews> ITEMS = new ArrayList<DummyNews>();

    private static final int COUNT = 5;

    static {
        DateTime today = DateTime.now();
        DummyNews dn;

        dn = new DummyNews("News Article1", "Fox", today);
        ITEMS.add(dn);
        dn = new DummyNews("News Article2", "CNN", today);
        ITEMS.add(dn);
        dn = new DummyNews("News Article3", "BBC", today);
        ITEMS.add(dn);
        dn = new DummyNews("News Article4", "Yahoo", today);
        ITEMS.add(dn);
        dn = new DummyNews("News Article5", "MSNBC", today);
        ITEMS.add(dn);
    }


    /**
     * A dummy item representing a piece of content.
     */

    public static class DummyNews {
        public final String title;
        public final DateTime date;
        public final String source;
        public DummyNews(String title, String source, DateTime date) {
            this.title = title;
            this.source = source;
            this.date = date;
        }
        @Override
        public String toString() {
            return title;
        }
    }
}

