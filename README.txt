Fetches data from the Riot and DDragon API using instructions from User's Commands. The program mimics the look and usability of Command Line, in order to promote efficiency and data retrieval speeds.

If you have ideas, e-mail me at ehamilton13@gmail.com

Current commands: 
	1. clear - clears window of text.
	2. getchampid <name> - Returns the LoL ID of a given champion.
	3. setdefaultregion <region> - Sets the default region for summoner data commands.
	4. getdefaultregion - Returns the default region set in 'regions.txt'.
	5. getsummid <name> <region> - Gets summID of summoner name and region, region not needed if set with CMD(3).
	6. getrank <name> - Gets rank of summoner name from default region.
		-TODO: Test with different region, only tested command with default region.
	7. lolking <name> <region> - Opens lolking URL of summoner/region.
	8. opgg <name> <region> - Opens opgg URL of summoner/region.
	9. help - Lists all commands and descriptions.
	10. help <command> - Lists usage and description for given command.
	11. champwiki <champion> - Opens the lolwiki of given champion.
	12. note <Note> - Allows to keep track of notes. (Ex. Don't dive under tower lvl 1).
	13. clearnotes - Clears all notes.
	14. getlevel <name> <region> - Gets the summoner level of the given name.
	15. getability <champion> <q|w|e|r> <CDR (0 - 40) - Gets statistics of champion and champion abilities
	
Planned features: 
	-opgg currentgame <Username> <Region>
		-Opens up op.gg's live game page.
	-summspellstats <spell>
		-Returns base CD and when applicable, damage dealt/healed/sheilded.
	-matchhistory <Username> <Region>
		-Returns stats based on users match history (common champ, gamemode, etc.)
	-update
		-Checks for program updates 
	-goldeff <ItemName>
		-Calculates gold efficiency of a certain item
	-Ideas are very welcome!
