This program will eventually fetch LoL data using Riot's API using CLI. 

If you have ideas, e-mail me at ehamilton13@gmail.com

Current commands: 
	1. clear - clears window of text.
	2. getchampid <name> - Returns the LoL ID of a given champion.
	3. setdefaultregion <region> - Sets the default region for summoner data commands.
	4. getdefaultregion - Returns the default region set in 'regions.txt'.
	5. summid <name> <region> - Gets summID of summoner name and region, region not needed if set with CMD(3).
	6. getrank <name> - Gets rank of summoner name from default region.
		-TODO: Test with different region, only tested command with default region.
	7. lolking <name> <region> - Opens lolking URL of summoner/region.
	8. opgg <name> <region> - Opens opgg URL of summoner/region.
	9. help - Lists all commands and descriptions.
	10. help <command> - Lists usage and description for given command.
	
Planned features: 
	-getchamp <name>
		-Will open League wiki page associated with <name> champion.
	-getstat <champ name> <stat>
		-Will display given stat (armor/l, base armor, base AD, etc.) to output.
	-opgg currentgame <Username> <Region>
		-Opens up op.gg's live game page.
	-abilitystats <champion> <button>
		-Returns useful information based on champ spell (Cooldowns at certain ranks, scalings, mana cost)
	-summspellstats <spell>
		-Returns base CD and when applicable, damage dealt/healed/sheilded.
	-matchhistory <Username> <Region>
		-Returns stats based on users match history (common champ, gamemode, etc.)
	-update
		-Checks for program updates 
	-goldeff <ItemName>
		-Calculates gold efficiency of a certain item
	-Ideas are very welcome!
