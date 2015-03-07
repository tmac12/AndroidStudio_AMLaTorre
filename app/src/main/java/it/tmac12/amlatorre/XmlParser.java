package it.tmac12.amlatorre;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Parse a team calendar XML file.
 */
public final class XmlParser {
    private static final String TAG_LOG = XmlParser.class.getName();
    private static final String XML_TAG_ROOT = "amlatorre";
    private static final String XML_TAG_EVENT = "evento";
    private static final String XML_TAG_DATE = "data";
    private static final String XML_TAG_TIME = "ora";
    private static final String XML_TAG_LOCATION = "luogo";
    private static final String XML_TAG_OPPONENT = "avversario";
    private static final String XML_TAG_WINNER = "vincitore";

    private XmlParser() {
        // not instantiable
    }

    public static List<Game> parse(Context context, int dataResId) {
        InputStream inputStream = null;
        try {
            inputStream = context.getResources().openRawResource(dataResId);
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);
            return XmlParser.readDocument(parser);
        } catch (Resources.NotFoundException | XmlPullParserException | IOException e) {
            Log.e(TAG_LOG, "Unable to parse XML file (res " + dataResId + ")", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
        return Collections.emptyList();
    }

    private static List<Game> readDocument(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<Game> games = new ArrayList<>();
        Game.Builder gameBuilder = null;
        String tagValue = null;
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String tagName = parser.getName();
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (XML_TAG_EVENT.equals(tagName)) {
                        gameBuilder = new Game.Builder();
                    } else if (!XML_TAG_OPPONENT.equals(tagName) && !XML_TAG_DATE.equals(tagName) && !XML_TAG_TIME.equals(tagName) &&
                            !XML_TAG_LOCATION.equals(tagName) && !XML_TAG_WINNER.equals(tagName) && !XML_TAG_ROOT.equals(tagName)) {
                        XmlParser.skipTag(parser);
                    }
                    break;
                case XmlPullParser.TEXT:
                    tagValue = parser.getText();
                    break;
                case XmlPullParser.END_TAG:
                    if (XML_TAG_EVENT.equals(tagName)) {
                        try {
                            games.add(gameBuilder.build());
                        } catch (NullPointerException e) {
                            // ignore game
                        }
                    } else if (XML_TAG_OPPONENT.equals(tagName)) {
                        gameBuilder.setOpponent(tagValue);
                    } else if (XML_TAG_DATE.equals(tagName)) {
                        gameBuilder.setDate(tagValue);
                    } else if (XML_TAG_TIME.equals(tagName)) {
                        gameBuilder.setTime(tagValue);
                    } else if (XML_TAG_LOCATION.equals(tagName)) {
                        gameBuilder.setLocation(tagValue);
                    } else if (XML_TAG_WINNER.equals(tagName)) {
                        gameBuilder.setWinner(tagValue);
                    }
                    break;
                default:
                    // skip
            }
            eventType = parser.next();
        }
        return games;
    }

    private static void skipTag(XmlPullParser parser) throws XmlPullParserException, IOException {
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
                default:
                    // ignore
            }
        }
    }
}