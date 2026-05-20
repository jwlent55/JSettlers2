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
        // TODO retrieve these from resources
        sb.append("<H4>2.6.10 (2022-07-05)</H4>\n  <ul>\n"
                + "<li> Server:\n"
                + "   <ul>\n"
                + "        <li> At end of game, also show players their resource trade <tt>*STATS*</tt> if client is v2.6.00 or newer\n"
                + "  <!--  Other changes: see a href=\"https://github.com/jdmonin/JSettlers2/blob/release-2.6.10/doc/Versions.md\" -->\n"
                + "  </ul>\n"
                + "<li> No changes to client.\n"
                + "  </ul>\n"
                + "\n"
                + "<H4>2.6.00 (2022-06-12)</H4>\n"
                + "  <ul>\n"
                + "<li> I18N: Added Polish translation (thank you KotCzarny)\n"
                + "<li> Client:\n"
                + "   <ul>\n"
                + "        <li> Game window:\n"
                + "           <ul>\n"
                + "                <li> Moving robber: If hex is desert, don't ask \"are you sure\" when you have an adjacent settlement/city\n"
                + "                <li> Less flicker while resizing window\n"
                + "           </ul>\n"
                + "        <li> More consistent sound quality on Windows 10\n"
                + "   </ul>\n"
                + "  <LI> Game <tt>*STATS*</tt>: Show player's resource totals given/received with ports, bank, other players\n"
                + "          if client and server are v2.6.00 or newer\n"
                + "  <LI> Other changes: see <a href=\"https://github.com/jdmonin/JSettlers2/blob/release-2.6.00/doc/Versions.md\"><tt>Versions.md</tt></a>\n"
                + "  </ul>\n"
                + "\n"
                + " <H4>2.5.00 (2021-12-30)</H4>\n"
                + "  <ul>\n"
                + "<li> Gameplay:\n"
                + "   <ul>\n"
                + "        <li> Road Building: If player cancels placement or ends turn before placing the first free road or ship, the dev card is returned to their hand\n"
                + "        <li> When a trade is offered to bots and humans, bots wait longer before responding. Was 3 seconds, is now 8, changeable with server property <tt>jsettlers.bot.human.pause</tt> (thank you Lee Passey)\n"
                + "        <li> Recalc Longest Route when building coastal settlement to connect a player's roads to ships (thanks kotc for reporting issue #95)\n"
                + "        <li> Pirate Islands scenario: Ship placement: Fix client bug where placing a coastal ship next to a road would prevent any further ship building, based on \"no branches in route\" rule\n"
                + "   </ul>\n"
                + "<li> I18N:\n"
                + "   <ul>\n"
                + "        <li> Added French translation (thank you Lee Passey)\n"
                + "   </ul>\n"
                + "<li> Client:\n"
                + "   <ul>\n"
                + "     <li> Game window:\n"
                + "        <ul>\n"
                + "        <li> Added hotkey Ctrl-B/Alt-B/Cmd-B to ask to Special Build in 6-player game\n"
                + "        <li> Discards: List resources you discarded, not just total amount, in game action textarea\n"
                + "        <li> Forgotten Tribe scenario: Much less flicker while placing gift ports\n"
                + "        </ul>\n"
                + "     <li> New Game dialog: \n"
                + "        <ul>\n"
                + "        <li> If server has increased default VP to win, use that as minimum when picking a scenario\n"
                + "        </ul> \n"
                + "     <li> If client starts a TCP server, keep it running; previous versions timed out after being idle an hour (thanks kotc for reporting issue #81)\n"
                + "     <li> Linux/Unix: Use sub-pixel font antialiasing if available (thanks kotc for issue #92)\n"
                + "   </ul>\n"
                + "<li> Bots/AI:\n"
                + "   <ul>\n"
                + "        <li> Shorten pause after bot requests a bank trade\n"
                + "   </ul>\n"
                + "<li> Server:\n"
                + "   <ul>\n"
                + "        <li> During game reset, don't send chat recap: Chat text is still in clients' game windows\n"
                + "        <li> If default VP is set on command line or properties, will also be minimum VP for any scenario\n"
                + "        <li> Other server changes: see <a href=\"https://github.com/jdmonin/JSettlers2/blob/main/doc/Versions.md\"><tt>Versions.md</tt></a>\n"
                + "   </ul>\n"
                + "  <LI> Other changes: see <a href=\"https://github.com/jdmonin/JSettlers2/blob/main/doc/Versions.md\"><tt>Versions.md</tt></a>\n"
                + "  </ul>\n"
                + "<P>&nbsp;<P>For earlier versions, see <a href=\"https://github.com/jdmonin/JSettlers2/releases\">Releases at GitHub</a>."
                );

        return sb.toString();
    }

}
