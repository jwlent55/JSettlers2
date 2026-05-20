/**
 * Java Settlers - An online multiplayer version of the game Settlers of Catan
 * This file Copyright (C) 2026 Jeremy D Monin <jeremy@nand.net>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * The maintainer of this program can be reached at jsettlers@nand.net
 **/
package soc.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

/**
 * Show recent releases and their brief release notes, based on github releases and doc/Versions.md.
 * @since 2.7.00
 */
@SuppressWarnings("serial")
public class RecentVersionsInfoDialog extends NotifyDialog
{
    // TODO adjust size and wrapping
    // TODO allow text selecton
    // TODO can we make URLs clickable?

    /**
     * Release notes directory path within our jar: {@code "/resources/recentVersions"}.
     */
    public final static String RELNOTES_RSRC_DIR_PATH = "/resources/recentVersions";

    /**
     * Creates and shows a new RecentVersionsInfoDialog.
     *<P>
     * Assumes currently running on AWT event thread.
     *
     * @param cli  Player client; not null, {@link SOCPlayerClient#getMainDisplay() cli.getMainDisplay()} not null
     * @throws NullPointerException  if cli is null
     * @throws IllegalArgumentException  if {@link SOCPlayerClient#getMainDisplay()} is null
     */
    public static void createAndShow(SOCPlayerClient cli)
        throws NullPointerException, IllegalArgumentException
    {
        new RecentVersionsInfoDialog(cli).setVisible(true);  // constructor checks for null cli, mainDisplay
    }

    /**
     * Creates a new RecentVersionsInfoDialog.
     *
     * @param cli  Player client; not null, {@link SOCPlayerClient#getMainDisplay() cli.getMainDisplay()} not null
     * @throws NullPointerException  if cli is null
     * @throws IllegalArgumentException  if {@link SOCPlayerClient#getMainDisplay()} is null
     */
    private RecentVersionsInfoDialog(SOCPlayerClient cli)
        throws NullPointerException, IllegalArgumentException
    {
        super(cli.getMainDisplay(), null, buildHTML(cli), null, true);  // super checks for null mainDisplay
        setModal(false);
        setTitle(strings.get("dialog.recent.title"));  // "Recent Versions of JSettlers"
    }

    /**
     * Build the body text HTML; called by constructor.
     * Embeds some newlines so our superclass sets up to show a multi-line string.
     * 
     * @param cli  Player client, to retrieve info; not null
     * @return text to show
     */
    private static String buildHTML(final SOCPlayerClient cli)
    {
        StringBuilder sb = new StringBuilder("<html><body>\n<H3>");
        sb.append(strings.get("dialog.recent.title"));  // "Recent Versions of JSettlers"
        sb.append("</H3>\n");

        try
        {
            ReleaseNotesFromDirectory rv = new ReleaseNotesFromDirectory(RELNOTES_RSRC_DIR_PATH);

            for (String fname : rv.notesHTML.descendingKeySet())
            {
                if (fname.charAt(0) != 'v')
                    continue;
                sb.append(rv.notesHTML.get(fname));
            }
            if (rv.notesHTML.containsKey("footer.html"))
                sb.append(rv.notesHTML.get("footer.html"));

        } catch (IOException e) {
            sb.append("Unexpected error:<BR>Cannot read release notes within JAR:<BR>");
            sb.append(e.toString());
        }

        return sb.toString();
    }

    /**
     * Reads all notes files in a given resource directory.
     * Constructor collects them into {@link #notesHTML}.
     */
    public static class ReleaseNotesFromDirectory
    {
        /**
         * HTML text resource contents read during constructor.
         * Keys are {@code *.html} filenames in the constructor's dirPath: {@code "v2610.html"}, {@code "footer.html"}, etc.
         * To iterate from newest to oldest versions, use {@link TreeMap#descendingKeySet()}.
         * Values are the file contents, read as UTF-8.
         */
        public final TreeMap<String, String> notesHTML = new TreeMap<>();

        /**
         * Read resource contents into a new ReleaseNotesFromDirectory.
         * Reads into {@link #notesHTML}; see that field for structure of loaded data.
         * @param resDirPath  Resource directory path to read, within our jar or classpath
         * @throws IOException if ...
         */
        public ReleaseNotesFromDirectory(final String resDirPath)
            throws IOException
        {
            // TODO actually scan; method may differ between jar and filesystem/IDE runs
            notesHTML.put("footer.html", "");
            notesHTML.put("v2500.html", "");
            notesHTML.put("v2600.html", "");
            notesHTML.put("v2610.html", "");

            final char[] buffer = new char[2048];
            final StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : notesHTML.entrySet())
            {
                final String fname = entry.getKey();

                try (InputStream ins = getClass().getResourceAsStream(resDirPath + "/" + fname))
                {
                    if (ins == null)
                        entry.setValue("(cannot read " + fname + ")");
                    else
                        try (InputStreamReader insr = new InputStreamReader(ins, StandardCharsets.UTF_8))
                        {
                            sb.delete(0, sb.length());
                            for (int numRead; (numRead = insr.read(buffer, 0, buffer.length)) > 0; )
                                sb.append(buffer, 0, numRead);

                            entry.setValue(sb.toString());
                        }
                }
            }
        }
    }

}
