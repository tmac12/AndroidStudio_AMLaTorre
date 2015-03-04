package it.tmac12.amlatorre;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marco on 30/11/14.
 */
public class XmlParser implements Serializable {
    private static final String ns = null;


    private List<Partita> readAMLaTorre(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<Partita> entries = new ArrayList<Partita>();

        parser.require(XmlPullParser.START_TAG, ns, "amlatorre");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("evento")) {
                entries.add(readPartita(parser));
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    public List<Partita> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readAMLaTorre(parser);
        } finally {
            in.close();
        }
    }

    // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them
    // off
    // to their respective &quot;read&quot; methods for processing. Otherwise, skips the tag.
    private Partita readPartita(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "evento");
        String data = null;
        String ora = null;
        String avversario = null;
        String luogo = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("data")) {
                data = readData(parser);
            } else if (name.equals("ora")) {
                ora = readOra(parser);
            } else if (name.equals("avversario")) {
                avversario = readAvversario(parser);
            } else if (name.equals("luogo")) {
                luogo = readLuogo(parser);
            } else {
                skip(parser);
            }
        }
        return new Partita(avversario, data, ora, luogo);
    }

    // Processes data tags in the feed.
    private String readData(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "data");
        String data = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "data");
        return data;
    }

    // Processes data tags in the feed.
    private String readOra(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "ora");
        String ora = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "ora");
        return ora;
    }

    // Processes data tags in the feed.
    private String readAvversario(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "avversario");
        String avversario = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "avversario");
        return avversario;
    }

    // Processes data tags in the feed.
    private String readLuogo(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "luogo");
        String luogo = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "luogo");
        return luogo;
    }

    // For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    // Skips tags the parser isn't interested in. Uses depth to handle nested tags. i.e.,
    // if the next tag after a START_TAG isn't a matching END_TAG, it keeps going until it
    // finds the matching END_TAG (as indicated by the value of "depth" being 0).
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    public static class Partita implements Serializable {
        public final String avversario;
        public final String data;
        public final String ora;
        public final String luogo;

        private Partita(String avversario, String data, String ora, String luogo) {
            this.avversario = avversario;
            this.data = data;
            this.ora = ora;
            this.luogo = luogo;
        }

    }
}
