This program will eventually fetch LoL data using Riot's API using a command line interface. 

If you have ideas, e-mail me at ehamilton13@gmail.com

Current commands: 
	1. clear - clears window of text.
	2. getchampid <name> - Returns the LoL ID of a given champion
	3. setdefaultregion <region> - Sets the default region for summoner data commands.
	
Planned features: 
	-help
		-Lists all commands and usage
	-help <command>
		-Lists usage for specific command
	-getchamp <name>
		-Will open League wiki page associated with <name> champion.
	-getstat <champ name> <stat>
		-Will display given stat (armor/l, base armor, base AD, etc.) to output.
	-opgg <Username> <Region>
		-Opens the op.gg webpage associated with given data. Region defaults to NA
	-opgg currentgame <Username> <Region>
		-Opens up op.gg's live game page.
	-lolking <Username> <Region>
		-Same as command opgg, but returns lolking page.
	-abilitystats <champion> <button>
		-Returns useful information based on champ spell (Cooldowns at certain ranks, scalings, mana cost
	-summspellstats <spell>
		-Returns base CD and when applicable, damage dealt/healed/sheilded.
	-matchhistory <Username> <Region>
		-Returns stats based on users match history (common champ, gamemode, etc.)
	-getrank <Summoner> <Region>
		-Returns solo queue rank of summoner. Region defaults to NA
	-summid <Username> <Region>
		-Returns the summoner's ID.
	-update
		-Checks for program updates 
	-goldeff <ItemName>
		-Calculates gold efficiency of a certain item
	-Ideas are very welcome!
